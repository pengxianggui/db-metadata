package com.hthjsj.web;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface WebResult {
    
    int code();
    
    String msg();
    
    Object data();
    
    Object gobalData();
}
