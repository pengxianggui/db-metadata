package com.hthjsj.analysis.meta;

import com.hthjsj.analysis.MetaAnalysisException;

/**
 * 元操作
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
