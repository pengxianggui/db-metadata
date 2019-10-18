package com.hthjsj.web.component;

import com.hthjsj.analysis.meta.Component;
import lombok.Getter;
import lombok.Setter;

/**
 * @author konbluesky
 */
public abstract class ViewComponent implements Component, ComponentRender {

    @Setter
    @Getter
    private Behavior showBehavior;

    /**
     * 用于获取Component最终的jsonString;
     * @return
     */
    public abstract String config();

    public void config(String config) {
        throw new RuntimeException("not finished!");
    }
}
