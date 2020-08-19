package com.hthjsj.analysis.meta;

import com.hthjsj.analysis.component.ComponentType;
import com.jfinal.kit.Kv;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> @Date : 2019-08-23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaConfigFactory {

    @Getter
    private static List<ConfigExtension> fieldExtensions;

    @Getter
    private static List<ConfigExtension> objectExtensions;

    public static MetaObjectConfigParse createV1ObjectConfig(IMetaObject metaObject) {
        Kv config = Kv.create();
        config.set("isUUIDPrimary", false);     //UUID
        config.set("isNumberSequence", false);  //数字序列,雪花算法
        config.set("isAutoIncrement", false);   //数据库自增
        config.set("objectCode", metaObject.code());
        if (objectExtensions != null) {
            for (ConfigExtension extension : objectExtensions) {
                extension.config(metaObject, config, ComponentType.UNKNOWN);
            }
        }
        return new MetaObjectConfigParse(config);
    }

    public static MetaFieldConfigParse createV1FieldConfig(IMetaField metaField, String defaultValue, String isNUll) {
        Kv config = Kv.create();
        config.set("isNullable", "yes".equalsIgnoreCase(isNUll));
        config.set("defaultVal", defaultValue == null ? "" : defaultValue);
        config.set("objectCode", metaField.objectCode());
        config.set("fieldCode", metaField.fieldCode());
        config.set("isMultiple", false);

        //单一主键时,主键:新增不可见,更新只读
        if (metaField.isPrimary()) {
            config.set("addStatus", MetaFieldConfigParse.DISABLE);
            config.set("updateStatus", MetaFieldConfigParse.READONLY);
        } else {
            config.set("addStatus", MetaFieldConfigParse.NORMAL);
            config.set("updateStatus", MetaFieldConfigParse.NORMAL);
        }

        config.set("viewStatus", MetaFieldConfigParse.READONLY);
        config.set("isListShow", true);
        config.set("isSearch", true);

        if (fieldExtensions != null) {
            for (ConfigExtension extension : fieldExtensions) {
                extension.config(metaField, config, ComponentType.UNKNOWN);
            }
        }
        return new MetaFieldConfigParse(config);
    }

    public static void addFieldExtension(ConfigExtension extension) {
        if (fieldExtensions == null) {
            fieldExtensions = new ArrayList<>();
        }
        fieldExtensions.add(extension);
    }

    public static void addObjectExtension(ConfigExtension extension) {
        if (objectExtensions == null) {
            objectExtensions = new ArrayList<>();
        }
        objectExtensions.add(extension);
    }
}
