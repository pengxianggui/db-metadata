package com.hthjsj.web.user;

import com.hthjsj.web.WebException;
import lombok.AllArgsConstructor;

/**
 * <p> @Date : 2019/12/17 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UserException extends WebException {

    public UserException(String messageTmpl, String... args) {
        super(messageTmpl, args);
    }

    public UserException(String message) {
        super(message);
    }

    public UserException loginError() {
        setError(USER.USER_LOGIN_FAILED);
        return this;
    }

    @AllArgsConstructor
    enum USER implements IErrorMsg {
        USER_LOGIN_FAILED(40000001, "登录用户名错误");

        int code;

        String msg;

        @Override
        public int code() {
            return this.code;
        }

        @Override
        public String msg() {
            return this.msg;
        }
    }
}
