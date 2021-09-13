package com.github.md.web.query.dynamic;

import java.util.Map;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface Context {

    default void set(String name, Object value) {
    }

    Map data();
}
