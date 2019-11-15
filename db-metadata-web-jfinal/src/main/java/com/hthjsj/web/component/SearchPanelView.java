package com.hthjsj.web.component;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.component.ViewContainer;

/**
 * <p> @Date : 2019/11/15 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class SearchPanelView extends ViewContainer {

    public SearchPanelView(String name, String label) {
        super(name, label);
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.SEARCHPANEL;
    }
}
