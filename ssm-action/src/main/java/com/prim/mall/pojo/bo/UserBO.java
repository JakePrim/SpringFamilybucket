package com.prim.mall.pojo.bo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 前端传递过来的数据结构类型：注册用户的数据结构
 */
public class UserBO {
    @NotBlank(message = "用户名不能为空")
    @Length(max = 12, message = "用户昵称不能超过12位")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @Email
    private String email;

    @Pattern(regexp = "^(((13[0-9]{1})|(15[0-9]{1})|(16[5-6]{1})|(17[0-8]{1})|(18[0-9]{1})|(19[0-9]{1}))+\\d{8})$", message = "手机号格式不正确")
    private String phone;

    @NotBlank(message = "安全问题不能为空")
    private String question;

    @NotBlank(message = "安全问题答案不能为空")
    private String answer;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
