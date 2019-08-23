package com.hthjsj.analysis.meta.validate;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface Validator<V> {
    
    boolean verify(V value);
}
