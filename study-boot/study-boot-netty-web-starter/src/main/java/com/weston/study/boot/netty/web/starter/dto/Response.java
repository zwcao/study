package com.weston.study.boot.netty.web.starter.dto;

import com.alibaba.fastjson.JSONObject;

public final class Response<T> {
    private int code;
    private String message;
    private T data;


    public Response(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public String toJSONString(){
        return JSONObject.toJSONString(this);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static <T> Response<T> ok(T data) {
        return new Response<>(200, "ok", data);
    }
    public static  Response<Void> ok() {
        return new Response(200, "ok", null);
    }

    public static <T> Response<T> ok(String msg, T data) {
        return new Response<>(200, msg, data);
    }

    public static Response fail(String msg) {
        return new Response<String>(500, msg, null);
    }
}

