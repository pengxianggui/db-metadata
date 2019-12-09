package com.hthjsj.web.feature;

import com.hthjsj.web.WebException;

/**
 * <p> @Date : 2019/11/22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FeatureException extends WebException {

    public FeatureException(String message) {
        super(message);
    }

    public FeatureException(String messageTmpl, String... args) {
        super(messageTmpl, args);
    }
}
