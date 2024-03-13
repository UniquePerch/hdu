package com.hdu.hdufpga.entity;

import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Integer CODE = 0;

    private Integer code;

    private String msg;

    private Object result;

    public Result() {

    }

    public Result(Integer code) {
        this.code = code;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, Object result) {
        this.code = code;
        this.result = result;
    }

    public Result(Object result) {
        this.result = result;
    }

    public static Result error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static Result error(String msg) {
        return error(500, msg);
    }

    public static Result error(Integer code, String msg) {
        return new Result(code, msg);
    }

    public static Result ok(String msg) {
        return new Result(CODE, msg);
    }

    public static Result ok(Object result) {
        return new Result(CODE, result);
    }

    public static Result ok() {
        return new Result(CODE);
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
