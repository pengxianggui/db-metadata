package com.hthjsj.web.controller;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/15 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ControllerException extends RuntimeException {

    public ControllerException() {
    }

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(String messageTmpl, String... args) {
        super(resolveString(messageTmpl, args));
    }

    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerException(Throwable cause) {
        super(cause);
    }

    public ControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
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
