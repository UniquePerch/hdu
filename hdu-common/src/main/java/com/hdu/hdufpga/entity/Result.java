package com.hdu.hdufpga.entity;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    public static final Integer CODE = 0;
    private static final long serialVersionUID = 1L;
    private Integer code;

    private String msg;

    private T result;


    public Result(Integer code) {
        this.code = code;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, T result) {
        this.code = code;
        this.result = result;
    }

    public Result(T result) {
        this.result = result;
    }

    public static <T> Result error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static <T> Result error(String msg) {
        return error(500, msg);
    }

    public static <T> Result error(Integer code, String msg) {
        return new Result(code, msg);
    }

    public static <T> Result ok(String msg, T data) {
        return new Result(CODE, msg, data);
    }

    public static <T> Result ok(T data) {
        return new Result(CODE, "success", data);
    }

    public static <T> Result ok() {
        return new Result(CODE, "success");
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
