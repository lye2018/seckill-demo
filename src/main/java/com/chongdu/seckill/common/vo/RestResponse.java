package com.chongdu.seckill.common.vo;

import java.io.Serializable;

public class RestResponse<T> implements Serializable {

    private int code;

    private String msg;

    private T data;

    /**
     * Restful接口调用成功
     * @param data
     * @param <T>
     * @return RestResponse
     */
    public static <T> RestResponse<T> success(T data, String message) {
        return new RestResponse<>(data, message);
    }

    /**
     * Restful接口调用成功（无返回值）
     * @param <T>
     * @return
     */
    public static <T> RestResponse<T> success() {
        return new RestResponse<>();
    }

    /**
     * Restful接口调用失败
     * @param <T>
     * @return
     */
    public static <T> RestResponse<T> error(CodeMsg codeMsg) {
        return new RestResponse(codeMsg.getMsg(), codeMsg.getCode());
    }

    public RestResponse() {
    	this.code = 200;
    }

    public RestResponse(T data, String message) {
    	this.code = 200;
    	this.data = data;
    	this.msg = message;
    }

    public RestResponse(String msg, int code) {
    	this.msg = msg;
    	this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
