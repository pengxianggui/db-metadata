package com.hthjsj.web.component;

import com.hthjsj.analysis.MetaAnalysisException;

/**
 * <p> @Date : 2019/10/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ComponentException extends MetaAnalysisException {

    public ComponentException() {
    }

    public ComponentException(String messageTmpl, String... args) {
        super(messageTmpl, args);
    }

    public ComponentException(String message) {
        super(message);
    }

    public ComponentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComponentException(Throwable cause) {
        super(cause);
    }

    public ComponentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
