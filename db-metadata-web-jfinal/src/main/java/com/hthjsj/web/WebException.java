package com.hthjsj.web;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/15 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class WebException extends RuntimeException {

    public WebException() {
    }

    public WebException(String message) {
        super(message);
    }

    public WebException(String messageTmpl, String... args) {
        super(resolveString(messageTmpl, args));
    }

    public WebException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebException(Throwable cause) {
        super(cause);
    }

    public WebException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    static String resolveString(String errorMsgTemplate, String... args) {

        if (errorMsgTemplate.indexOf("{}") >= 0) {
            errorMsgTemplate = String.format(errorMsgTemplate.replaceAll("\\{\\}", "%s"));
        }

        if (errorMsgTemplate.indexOf("%s") >= 0) {
            errorMsgTemplate = String.format(errorMsgTemplate, args);
        }
        return errorMsgTemplate;
    }
}
