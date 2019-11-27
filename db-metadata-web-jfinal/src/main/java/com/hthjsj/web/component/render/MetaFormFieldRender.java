package com.hthjsj.web.component.render;

import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.component.ComponentRender;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.web.UtilKit;
import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/11/27 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaFormFieldRender<C extends Component> implements ComponentRender<C> {

    IMetaField metaField;

    C component;

    Kv fieldInstanceConfig;

    public MetaFormFieldRender(IMetaField metaField, C component, Kv fieldInstanceConfig) {
        this.metaField = metaField;
        this.component = component;
        this.fieldInstanceConfig = fieldInstanceConfig;
    }

    @Override
    public C component() {
        return component;
    }

    @Override
    public Kv render() {
        return UtilKit.mergeUseOld(component.getMeta(), fieldInstanceConfig);
    }
}
