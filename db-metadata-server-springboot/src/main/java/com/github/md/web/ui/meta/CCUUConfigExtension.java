package com.github.md.web.ui.meta;

import com.github.md.web.component.attr.AttributeBuilder;
import com.google.common.collect.Lists;
import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.ConfigExtension;
import com.github.md.analysis.meta.IMetaField;

/**
 * Created_by,Updated_by,Created_time,Update_time配置 inline树型
 * <p> @Date : 2019/12/20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class CCUUConfigExtension implements ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> {

    @Override
    public void config(IMetaField metaObj, AttributeBuilder.FatAttributeBuilder config, ComponentType containerType) {
        if (containerType == ComponentType.FORMVIEW) {
            if ("created_by".equalsIgnoreCase(metaObj.fieldCode()) || "updated_by".equalsIgnoreCase(metaObj.fieldCode())
                    || "created_time".equalsIgnoreCase(metaObj.fieldCode()) || "updated_time".equalsIgnoreCase(metaObj.fieldCode())) {
                config.inline(true);
            }
        }

        //        if (containerType == ComponentType.TABLEVIEW) {
        //            MetaFieldConfigParse parse = metaObj.configParser();
        //            if ("updated_by".equalsIgnoreCase(metaObj.fieldCode()) || "updated_time".equalsIgnoreCase(metaObj.fieldCode())
        //                    || "remark".equalsIgnoreCase(metaObj.fieldCode())) {
        //                parse.isListShow(false);
        //            }
        //            if (metaObj.dbType().isText()) {
        //                if (metaObj.dbType().isBigText()) {
        //                    parse.isListShow(false);
        //                }
        //                if (metaObj.dbType().isJson()) {
        //                    parse.isListShow(false);
        //                }
        //            }
        //            metaObj.config(parse.toJson());
        //            ServiceManager.metaService().updateMetaField(metaObj);
        //        }
    }
}
