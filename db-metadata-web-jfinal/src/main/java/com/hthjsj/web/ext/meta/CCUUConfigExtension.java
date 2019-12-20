package com.hthjsj.web.ext.meta;

import com.hthjsj.analysis.meta.ConfigExtension;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.web.component.attr.AttributeBuilder;

/**
 * Created_by,Updated_by,Created_time,Update_time配置 inline树型
 * <p> @Date : 2019/12/20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class CCUUConfigExtension implements ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder> {

    @Override
    public void config(IMetaField metaObj, AttributeBuilder.FatAttributeBuilder config) {
        if ("created_by".equalsIgnoreCase(metaObj.fieldCode()) || "updated_by".equalsIgnoreCase(metaObj.fieldCode())
                || "created_time".equalsIgnoreCase(metaObj.fieldCode()) || "updated_time".equalsIgnoreCase(metaObj.fieldCode())) {
            config.inline(true);
        }
    }
}
