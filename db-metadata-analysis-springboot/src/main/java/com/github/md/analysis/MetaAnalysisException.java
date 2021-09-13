package com.github.md.analysis;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaAnalysisException extends RuntimeException {

    public MetaAnalysisException() {
    }

    public MetaAnalysisException(String messageTmpl, String... args) {
        super(resolveString(messageTmpl, args));
    }

    public MetaAnalysisException(String message) {
        super(message);
    }

    public MetaAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetaAnalysisException(Throwable cause) {
        super(cause);
    }

    public MetaAnalysisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    static String resolveString(String errorMsgTemplate, String... args) {

        if (errorMsgTemplate.contains("%s")) {
            errorMsgTemplate = String.format(errorMsgTemplate, args);
        }
        return errorMsgTemplate;
    }
}
