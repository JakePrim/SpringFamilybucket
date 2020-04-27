package com.prim.mall.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * 返回给前端的json通用类
 * Include.NON_NULL null的属性不显示在json中
 *
 * @param <T>
 */
//1.x 的老版本使用
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//2.x 的新版本使用
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JSONResult<T> implements Serializable {
    private Integer status;
    private String msg;
    private T data;

    private JSONResult(Integer status) {
        this.status = status;
    }

    private JSONResult(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    /**
     * 注意 T 和 String 如果T是String类型就会调用这个方法了
     *
     * @param status
     * @param msg
     */
    private JSONResult(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private JSONResult(Integer status, T data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    /**
     * 表示响应是否成功
     * JsonIgnore 不会显示到json序列划结果中
     *
     * @return
     */
    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResultCode.SUCCESS.getCode();
    }

    public Integer getStatus() {
        return this.status;
    }

    public T getData() {
        return this.data;
    }

    public String getMsg() {
        return this.msg;
    }

    /**
     * 只返回成功的状态码
     *
     * @param <T>
     * @return
     */
    public static <T> JSONResult<T> createBySuccess() {
        return new JSONResult<>(ResultCode.SUCCESS.getCode());
    }

    /**
     * 返回成功的状态码 和 msg 消息
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> JSONResult<T> createBySuccessMessage(String msg) {
        return new JSONResult<>(ResultCode.SUCCESS.getCode(), msg);
    }

    /**
     * 返回成功的状态码 和 响应数据
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> JSONResult<T> createBySuccess(T data) {
        return new JSONResult<>(ResultCode.SUCCESS.getCode(), data);
    }

    /**
     * 返回成功的状态 响应数据 消息
     *
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> JSONResult<T> createBySuccess(String msg, T data) {
        return new JSONResult<>(ResultCode.SUCCESS.getCode(), data, msg);
    }

    /**
     * 错误的响应
     *
     * @param <T>
     * @return
     */
    public static <T> JSONResult<T> createByError() {
        return new JSONResult<>(ResultCode.ERROR.getCode(), ResultCode.ERROR.getDesc());
    }

    /**
     * 错误的响应 自定义错误响应消息
     *
     * @param errorMsg
     * @param <T>
     * @return
     */
    public static <T> JSONResult<T> createByErrorMessage(String errorMsg) {
        return new JSONResult<>(ResultCode.ERROR.getCode(), errorMsg);
    }

    public static JSONResult<Object> createByError(Object data) {
        return new JSONResult<>(ResultCode.VALID_ERROR.getCode(), data, ResultCode.VALID_ERROR.getDesc());

    }

    public static <T> JSONResult<T> createByErrorCodeMessage(Integer errorCode, String errorMsg) {
        return new JSONResult<>(errorCode, errorMsg);
    }
}
