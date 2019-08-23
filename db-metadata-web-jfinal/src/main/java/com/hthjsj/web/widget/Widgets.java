package com.hthjsj.web.widget;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
final public class Widgets {
    
    private final List<FrontWidget> pluginList = new ArrayList<FrontWidget>();
    
    public Widgets add(FrontWidget plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("plugin can not be null");
        }
        pluginList.add(plugin);
        return this;
    }
    
    public List<FrontWidget> getPluginList() {
        return pluginList;
    }
    
}

