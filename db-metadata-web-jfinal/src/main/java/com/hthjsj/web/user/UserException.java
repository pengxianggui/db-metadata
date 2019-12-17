package com.hthjsj.web.user;

import com.hthjsj.web.WebException;

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
}
