package com.prim.mall.service;

import com.prim.mall.common.enums.ValidType;
import com.prim.mall.pojo.User;

public interface UserService {

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);

    /**
     * 检查用户名是否已经存在
     *
     * @param username
     * @return
     */
    Integer checkUsername(String username);

    /**
     * 检查邮箱是否存在
     *
     * @param email
     * @return
     */
    Integer checkEmail(String email);

    /**
     * 创建用户
     *
     * @param createUser
     * @return
     */
    boolean createUser(User createUser);

    /**
     * 校验通用方法
     *
     * @param validSource
     * @param type
     * @return
     */
    boolean checkValid(String validSource, ValidType type);

    /**
     * 查询用户设置的安全问题
     *
     * @param username
     * @return
     */
    String forgetGetQuestion(String username);

    /**
     * 校验安全问题答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    boolean checkValidQuestionAnswer(String username, String question, String answer);

    /**
     * 修改密码
     * @param username
     * @param newPassword
     */
    void updatePassword(String username, String newPassword);

}
