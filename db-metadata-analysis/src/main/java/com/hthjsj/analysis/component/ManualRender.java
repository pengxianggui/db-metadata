package com.hthjsj.analysis.component;

import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/11/27 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ManualRender<C extends Component> implements ComponentRender<C> {

    C component;

    public ManualRender(C component) {
        this.component = component;
    }

    @Override
    public C component() {
        return component;
    }

    @Override
    public Kv render() {
        return component.getMeta();
    }
}
