package com.hthjsj.web.component;

import com.hthjsj.analysis.meta.Component;

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

    private final List<Component> pluginList = new ArrayList<Component>();

    public Widgets add(Component plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("plugin can not be null");
        }
        pluginList.add(plugin);
        return this;
    }

    public List<Component> getPluginList() {
        return pluginList;
    }
}

