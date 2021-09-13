package com.github.md.web.user.auth;

import com.github.md.web.WebException;

/**
 * <p> @Date : 2019/12/17 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MRException extends WebException {

    public MRException(String message) {
        super(message);
    }

    public MRException(String messageTmpl, String... args) {
        super(messageTmpl, args);
    }
}
