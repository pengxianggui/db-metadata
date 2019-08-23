package com.hthjsj.web.widget;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface FrontWidget {
    
    String code();
    
    String cn();
    
    String en();
    
    void init();
    
    Object render();
    
}
