package com.hthjsj.web.component;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.component.ViewContainer;

/**
 * <p> @Date : 2019/11/26 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class SearchView extends ViewContainer {

    public SearchView(String name, String label) {
        super(name, label);
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.SEARCHPANEL;
    }
}
