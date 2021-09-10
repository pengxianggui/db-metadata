package com.hthjsj.web.config;

import com.hthjsj.web.WebException;

/**
 * <p> @Date : 2021/9/7 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
public class NotFinishException extends WebException {

    public NotFinishException(String message) {
        super(message);
    }

    public NotFinishException(String messageTmpl, String... args) {
        super(messageTmpl, args);
    }
}