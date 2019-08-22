package com.hthjsj.web;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface FrontRest {
    
    WebResult toAdd();
    
    WebResult doAdd();
    
    WebResult toUpdate();
    
    WebResult doUpdate();
    
    WebResult detail();
    
    WebResult delete();
    
    WebResult list();
}
