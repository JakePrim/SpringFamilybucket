package com.prim.mall.pojo.bo;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

public class ResetPasswordBO {
    @NotBlank(message = "用户名不能为空")
    @Length(max = 12, message = "用户名限制不能超过12位")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String newPassword;

    @NotBlank(message = "token不能为空")
    private String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
