package com.github.md.web;

import cn.com.asoco.exception.UserVisibleException;
import lombok.Getter;

/**
 * 业务异常
 * <pre>
 *     自定义异常
 *     范围: controller,Intercepter,Query Feature
 * </pre>
 * <p> @Date : 2019/10/15 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Getter
public class WebException extends UserVisibleException {
    public WebException(Exception ex) {
        super(ex);
    }

    public WebException(String message) {
        super(message, 500);
    }

    public WebException(String messageTmpl, String... args) {
        super(resolveString(messageTmpl, args));
        set(500, resolveString(messageTmpl, args));
    }

    public WebException(String msg, int code) {
        super(msg, code);
    }

    private static String resolveString(String errorMsgTemplate, String... args) {
        if (errorMsgTemplate.contains("%s")) {
            errorMsgTemplate = String.format(errorMsgTemplate, args);
        }
        return errorMsgTemplate;
    }

    /**
     * 仅供子类使用设置code,msg
     *
     * @param code
     * @param msg
     */
    protected void set(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
