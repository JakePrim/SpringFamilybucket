package com.prim.mall.service.impl;

import com.prim.mall.common.enums.RoleEnum;
import com.prim.mall.common.enums.ValidType;
import com.prim.mall.dao.UserMapper;
import com.prim.mall.pojo.User;
import com.prim.mall.service.UserService;
import com.prim.mall.util.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author prim
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    @Override
    public User login(String username, String password) {
        User user = null;
        try {
            //需要将密码转为MD5格式
            String securityPassword = MD5Utils.getMD5Str(password);
            user = userMapper.selectLogin(username, securityPassword);
            //隐藏用户敏感信息
            //隐藏密码
            user.setPassword(StringUtils.EMPTY);
            //注意隐藏用户安全问题及答案 否则可能会利用安全问题和答案 恶意修改密码
            user.setQuestion(StringUtils.EMPTY);
            user.setAnswer(StringUtils.EMPTY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    @Override
    public Integer checkUsername(String username) {
        return userMapper.checkUsername(username);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    @Override
    public Integer checkEmail(String email) {
        return userMapper.checkEmail(email);
    }

    //注意事务管理使用REQUIRED 出现错误回滚事务
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean createUser(User createUser) {
        //密码加密
        try {
            createUser.setPassword(MD5Utils.getMD5Str(createUser.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置用户角色
        createUser.setRole(RoleEnum.ROLE_CUSTOMER.getCode());
        int insert = userMapper.insert(createUser);
        return insert > 0;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean checkValid(String validSource, ValidType type) {
        if (type != null) {
            if (type == ValidType.VALID_USERNAME) {
                int count = userMapper.checkUsername(validSource);
                return count > 0;
            }
            if (type == ValidType.VALID_EMAIL) {
                int count = userMapper.checkEmail(validSource);
                return count > 0;
            }
        }
        return false;
    }

    @Override
    public String forgetGetQuestion(String username) {
        return userMapper.selectQuestionByPrimaryKey(username);
    }

    @Override
    public boolean checkValidQuestionAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkValidQuestionAnswer(username, question, answer);
        return resultCount > 0;
    }

    @Override
    public void updatePassword(String username, String newPassword) {

    }
}
