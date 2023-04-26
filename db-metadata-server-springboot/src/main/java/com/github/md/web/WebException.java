package com.github.md.web;

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
public class WebException extends RuntimeException {
    private Integer code;
    public WebException(Exception ex) {
        super(ex);
    }

    public WebException(String message) {
        super(message);
    }

    public WebException(String messageTmpl, String... args) {
        super(resolveString(messageTmpl, args));
    }

    public WebException(String msg, int code) {
        // TODO code 如何跟错误模板挂钩，要利用国际化的message.properties吗
        super(msg);
        this.code = code;
    }

    private static String resolveString(String errorMsgTemplate, String... args) {
        if (errorMsgTemplate.contains("%s")) {
            errorMsgTemplate = String.format(errorMsgTemplate, args);
        }
        return errorMsgTemplate;
    }

}
