package com.hthjsj.web.query.dynamic;

import com.hthjsj.web.WebException;

/**
 * <p> @Date : 2020/8/25 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class QueryCompileException extends WebException {

    public QueryCompileException(String message) {
        super(message);
    }

    public QueryCompileException(String messageTmpl, String... args) {
        super(messageTmpl, args);
    }
}
