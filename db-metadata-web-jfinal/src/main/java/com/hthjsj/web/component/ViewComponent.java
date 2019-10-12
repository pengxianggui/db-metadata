package com.hthjsj.web.component;

import com.hthjsj.analysis.meta.Component;
import com.jfinal.kit.Kv;
import lombok.Getter;
import lombok.Setter;

/**
 * @author konbluesky
 */
public abstract class ViewComponent implements Component {

    protected String en;

    protected String cn;

    @Setter
    @Getter
    private Behavior showBehavior;

    public abstract String config();

    public abstract void config(String config);

    public abstract Kv renderMeta();

}
