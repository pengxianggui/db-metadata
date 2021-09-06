package com.hthjsj.web.ui.meta;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.ConfigExtension;
import com.hthjsj.analysis.meta.IMetaField;
import com.jfinal.kit.Kv;

/**
 * 元子段配置扩展
 * <p> @Date : 2019/12/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaFieldConfigExtension implements ConfigExtension<IMetaField, Kv, ComponentType> {

    @Override
    public void config(IMetaField metaField, Kv config, ComponentType containerType) {

        // attention: 初始化field配置时 , 名称包含file的 并且 数据库字段类型为json的 默认设定isFile = true
        if (metaField.fieldCode().contains("file") && metaField.dbType().isJson()) {
            config.set("isFile", true);
            config.set("seats", "[]");
        }
    }
}
