package com.hthjsj.web.feature;

/**
 * <p> @Date : 2020/9/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface FeatureIntercept {

    default void init() {
    }

    default void exec() {
    }
}
