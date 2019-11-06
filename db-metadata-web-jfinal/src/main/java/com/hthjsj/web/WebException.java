package com.hthjsj.web;

/**
 * <pre>
 *     自定义异常
 *     范围: controller,Intercepter,Query Module
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

        if (errorMsgTemplate.indexOf("{}") >= 0) {
            errorMsgTemplate = String.format(errorMsgTemplate.replaceAll("\\{\\}", "%s"));
        }

        if (errorMsgTemplate.indexOf("%s") >= 0) {
            errorMsgTemplate = String.format(errorMsgTemplate, args);
        }
        return errorMsgTemplate;
    }
}
