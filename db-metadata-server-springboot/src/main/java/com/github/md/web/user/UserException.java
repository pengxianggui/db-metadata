package com.github.md.web.user;

import com.github.md.web.ex.WebException;

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

    public UserException(String msg, int code) {
        super(msg, code);
    }

    public UserException(String message) {
        super(message);
    }

    public UserException loginError() {
        return this;
    }
}
