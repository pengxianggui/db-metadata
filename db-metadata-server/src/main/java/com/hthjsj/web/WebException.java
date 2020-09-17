package com.hthjsj.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
@Getter
public class WebException extends RuntimeException {

    protected int code;

    protected String msg;

    public WebException(String message) {
        super(message);
        set(500, message);
    }

    public WebException(String messageTmpl, String... args) {
        super(resolveString(messageTmpl, args));
        set(500, resolveString(messageTmpl, args));
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
    private void set(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public void setError(IErrorMsg error) {
        set(error.code(), error.msg());
    }

    /**
     * 使用enum类型,确保系统中存在的Code,msg都是已知的
     */
    @AllArgsConstructor
    protected enum WEB_ERROR implements IErrorMsg {
        UNKNOWN(500, "系统发生错误");

        private final int code;

        private final String msg;

        @Override
        public int code() {
            return this.code;
        }

        @Override
        public String msg() {
            return this.msg;
        }
    }

    public interface IErrorMsg {

        int code();

        String msg();
    }
}
