package com.github.md.web.ui.meta;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.component.attr.AttributeBuilder;
import com.github.md.web.ui.meta.form.FieldComponentConfigExtension;

/**
 * Created_by,Updated_by,Created_time,Update_time配置 inline树型
 * <p> @Date : 2019/12/20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class CCUUFieldConfigExtension extends FieldComponentConfigExtension {

    @Override
    public void config(IMetaField metaObj, AttributeBuilder.FatAttributeBuilder config, ComponentType containerType) {
        if (containerType == ComponentType.FORMVIEW) {
            if ("created_by".equalsIgnoreCase(metaObj.fieldCode()) || "updated_by".equalsIgnoreCase(metaObj.fieldCode())
                    || "created_time".equalsIgnoreCase(metaObj.fieldCode()) || "updated_time".equalsIgnoreCase(metaObj.fieldCode())) {
                config.inline(true);
            }
        }
    }
}
