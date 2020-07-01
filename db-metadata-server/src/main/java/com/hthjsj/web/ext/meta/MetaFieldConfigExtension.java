package com.hthjsj.web.ext.meta;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.ConfigExtension;
import com.hthjsj.analysis.meta.IMetaField;
import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/12/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaFieldConfigExtension implements ConfigExtension<IMetaField, Kv, ComponentType> {

    @Override
    public void config(IMetaField metaField, Kv config, ComponentType containerType) {

        //TODO 初始化field配置时 , 名称包含file的 默认设定isFile = true
        if (metaField.fieldCode().contains("file")) {
            config.set("isFile", true);
        }
    }
}
