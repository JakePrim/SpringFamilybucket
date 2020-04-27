package com.prim.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BaseController {

    public static final String TOKEN_PREFIX = "token_";

    /**
     * 获取hibernate-valid 校验结果
     *
     * @param result
     * @return
     */
    protected Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            // 发生验证错误所对应的某一个属性
            String errorField = error.getField();
            // 验证错误的信息
            String errorMsg = error.getDefaultMessage();

            map.put(errorField, errorMsg);
        }
        return map;
    }
}
