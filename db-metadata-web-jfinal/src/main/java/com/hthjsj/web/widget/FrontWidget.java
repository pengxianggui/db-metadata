package com.hthjsj.web.widget;

import com.hthjsj.analysis.meta.ui.ShowType;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface FrontWidget extends ShowType {
    
    void init();
    
    Object render();
    
}
