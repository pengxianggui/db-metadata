package com.github.md.analysis.meta;

import com.github.md.analysis.MetaAnalysisException;

/**
 * 用来表示元对象操作时引起的异常;
 * <p> @Date : 2019/10/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaOperateException extends MetaAnalysisException {

    public MetaOperateException() {
    }

    public MetaOperateException(String messageTmpl, String... args) {
        super(messageTmpl, args);
    }

    public MetaOperateException(String message) {
        super(message);
    }

    public MetaOperateException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetaOperateException(Throwable cause) {
        super(cause);
    }

    public MetaOperateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
