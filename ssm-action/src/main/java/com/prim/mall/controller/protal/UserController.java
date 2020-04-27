package com.prim.mall.controller.protal;

import com.prim.mall.common.Const;
import com.prim.mall.common.JSONResult;
import com.prim.mall.common.TokenCache;
import com.prim.mall.common.enums.ValidType;
import com.prim.mall.controller.BaseController;
import com.prim.mall.pojo.User;
import com.prim.mall.pojo.bo.ResetPasswordBO;
import com.prim.mall.pojo.bo.UserBO;
import com.prim.mall.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param session  用户信息保存在会话中，这是不好的做法 后续会修改为分布式会话
     * @return
     */
    @PostMapping("/login.do")
    public JSONResult<User> login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        //需要判断用户名和密码是否为空 如果为空则不对数据库进行操作 防止恶意刷接口
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return JSONResult.createByErrorMessage("用户名和密码为空");
        }
        //检查用户名是否存在 如果用户名不存在则不用在做其他操作
        Integer isExists = userService.checkUsername(username);
        if (isExists == 0) {
            return JSONResult.createByErrorMessage("用户名不存在");
        }
        //检查用户名和密码
        User loginUser = userService.login(username, password);
        if (null == loginUser) {
            return JSONResult.createByErrorMessage("用户名或密码错误");
        }
        //将用户信息存储到session中（也可以存储到cookie中）
        session.setAttribute(Const.CURRENT_USER, loginUser);
        return JSONResult.createBySuccess("登录成功", loginUser);
    }

    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @GetMapping("/logout.do")
    public JSONResult<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return JSONResult.createBySuccess();
    }

    /**
     * 用户注册
     * 使用hibernate-validator进行校验
     *
     * @param user   前端传递过来的数据结构
     * @param result 校验结果
     * @return
     */
    @PostMapping("/register.do")
    public JSONResult<Object> register(@RequestBody @Valid UserBO user, BindingResult result) {
        //校验用户名和密码不能为空 同时校验格式是否正确 虽然前端也会进行校验但是防止前端校验漏洞 后端需要严格的校验
        //不合格的不进行请求 减少数据库的压力
        if (result.hasErrors()) {
            Map<String, String> errors = getErrors(result);
            return JSONResult.createByError(errors);
        }
        String username = user.getUsername();
        String email = user.getEmail();
        //检查用户名是否存在
        if (userService.checkValid(username, ValidType.VALID_USERNAME)) {
            return JSONResult.createByErrorMessage("用户名已存在");
        }
        //检查邮箱是否存在
        if (userService.checkValid(email, ValidType.VALID_EMAIL)) {
            return JSONResult.createByErrorMessage("邮箱已存在");
        }
        //以上检查都没有问题 存入数据库
        User createUser = new User();
        BeanUtils.copyProperties(user, createUser);
        boolean isCreateUser = userService.createUser(createUser);
        if (!isCreateUser) {
            return JSONResult.createByErrorMessage("创建用户失败");
        }
        return JSONResult.createBySuccessMessage("注册成功");
    }

    /**
     * 获取用户信息，只有用户登录才可以获取
     *
     * @param session
     * @return
     */
    @GetMapping("/get_user_info.do")
    public JSONResult<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return JSONResult.createBySuccess(user);
        }
        return JSONResult.createByErrorMessage("用户未登录无法获取用户信息");
    }

    /**
     * 查询忘记密码，设置的安全问题
     *
     * @param username
     * @return
     */
    @PostMapping("/forget_get_question.do")
    public JSONResult<String> forgetGetQuestion(@RequestParam String username) {
        //检查用户名不能为空
        if (StringUtils.isBlank(username)) {
            return JSONResult.createByErrorMessage("用户名不能为空");
        }
        //校验用户名是否存在
        boolean checkValid = userService.checkValid(username, ValidType.VALID_USERNAME);
        if (!checkValid) {
            return JSONResult.createByErrorMessage("用户名不存在");
        }
        //查询用户设置的安全问题
        String question = userService.forgetGetQuestion(username);
        return JSONResult.createBySuccess(question);
    }

    /**
     * 校验用户问题答案
     *
     * @param question
     * @param answer
     * @return
     */
    @PostMapping("/valid_question.do")
    public JSONResult<String> validQuestionAnswer(
            @RequestParam String username,
            @RequestParam String question,
            @RequestParam String answer) {
        //检查空 防止不必要的请求数据库
        if (StringUtils.isBlank(username) || StringUtils.isBlank(question)
                || StringUtils.isBlank(answer)) {
            return JSONResult.createByErrorMessage("用户名或安全问题或答案不能为null");
        }
        boolean isValid = userService.checkValidQuestionAnswer(username, question, answer);
        if (isValid) {
            //返回token 并且token存在有效期 用户根据token修改密码
            String token = UUID.randomUUID().toString();
            //缓存token
            TokenCache.setCache(TOKEN_PREFIX + username, token);
            return JSONResult.createBySuccess(token);
        }
        return JSONResult.createByErrorMessage("安全问题答案不正确");
    }

    /**
     * 重置用户密码
     *
     * @param resetPasswordBO
     * @return
     */
    @PostMapping("/reset_password")
    public JSONResult<Object> resetPassword(@RequestBody @Valid ResetPasswordBO resetPasswordBO,
                                            BindingResult result) {
        if (result.hasErrors()) {
            return JSONResult.createByError(getErrors(result));
        }
        String username = resetPasswordBO.getUsername();
        String token = resetPasswordBO.getToken();
        String newPassword = resetPasswordBO.getNewPassword();

        //校验token是否准确
        String cacheToken = TokenCache.getCache(TOKEN_PREFIX + username);
        if (StringUtils.isBlank(cacheToken)) {
            return JSONResult.createByErrorMessage("token无效或过期");
        }
        if (cacheToken.equals(token)) {
            //token校验正确 允许修改密码
            userService.updatePassword(username, newPassword);
        }
        return JSONResult.createByErrorMessage("token校验错误");
    }
}
