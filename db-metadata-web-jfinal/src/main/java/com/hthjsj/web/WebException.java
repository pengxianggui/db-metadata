package com.hthjsj.web;

/**
 * <pre>
 *     自定义异常
 *     范围: controller,Intercepter,Query Feature
 * </pre>
 * <p> @Date : 2019/10/15 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class WebException extends RuntimeException {

    public WebException(String message) {
        super(message);
    }

    public WebException(String messageTmpl, String... args) {
        super(resolveString(messageTmpl, args));
    }

    private static String resolveString(String errorMsgTemplate, String... args) {
        if (errorMsgTemplate.contains("%s")) {
            errorMsgTemplate = String.format(errorMsgTemplate, args);
        }
        return errorMsgTemplate;
    }
}
