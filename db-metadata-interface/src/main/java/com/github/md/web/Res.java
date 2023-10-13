package com.github.md.web;

import com.github.md.web.ex.WebException;
import lombok.Getter;
import lombok.Setter;

/**
 * Rest接口响应类
 * @author pengxg
 * @date 2023/10/7 16:28
 */
@Getter
@Setter
public class Res<T> {
    /**
     * 状态: fail or ok
     * @Deprecated 2.5 改为基于code判断，code为0时表示成功，其他表示不成功
     */
    @Deprecated
    private String state;

    /**
     * 错误码
     */
    private String code;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 消息提醒 TODO 借鉴鹊桥丰富消息提醒
     */
    private String message;

    /**
     * 异常消息堆栈(开发模式下返回) TODO 非开发模式下的屏蔽在spring的messageConverter里做
     */
    private Exception ex;

    /**
     * 请求地址
     */
    private String requestUri;

    public Res(String code, T data, String message, Exception ex) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.ex = ex;

        // deprecated 2.5
        this.state = ("0".equals(code) ? "ok" : "fail");
    }

    public static Res ok() {
        return Res.ok(null);
    }

    public static <T> Res ok(T data) {
        return new Res("0", data, "响应成功", null);
    }

    public static <T> Res ok(T data, String message) {
        return new Res("0", data, message, null);
    }

    public static Res fail(String code, Exception e) {
        return new Res(code, null, e.getMessage(), e);
    }

    public static Res fail(String message) {
        return new Res("-1", null, message, null);
    }

    public static Res fail(String code, String message) {
        return new Res(code, null, message, null);
    }

    public static Res fail(WebException e) {
        return Res.fail(String.valueOf(e.getCode()), e);
    }
}
