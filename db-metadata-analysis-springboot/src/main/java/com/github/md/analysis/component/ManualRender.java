package com.github.md.analysis.component;

import com.github.md.analysis.kit.Kv;

/**
 * 默认的render行为,直接范围meta数据
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
