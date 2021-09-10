package com.hthjsj.web.config.render;

import com.hthjsj.web.WebException;

/**
 * <p> @Date : 2021/9/10 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
public class RenderException extends WebException {

    public RenderException(String message) {
        super(message);
    }

    public RenderException(String messageTmpl, String... args) {
        super(messageTmpl, args);
    }

}
