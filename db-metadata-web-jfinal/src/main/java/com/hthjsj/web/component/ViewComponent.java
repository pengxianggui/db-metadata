package com.hthjsj.web.component;

import com.hthjsj.analysis.meta.Component;
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

    @Override
    public String name() {
        return cn;
    }

    @Override
    public String code() {
        return en;
    }

    public abstract String config();

    public abstract void config(String config);

}
