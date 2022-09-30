package com.github.md.analysis.meta;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.db.MetaDataTypeConvert;
import com.github.md.analysis.kit.Kv;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> @Date : 2019-08-23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
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
        config.set("isUnique", false); // // todo: 如何将 isUnique信息也从数据字典里取出来呢? 涉及表的约束信息
//        config.set("defaultVal", convert(metaField, defaultValue));
        config.set("defaultVal", defaultValue);
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
        config.set("isSearch", false); // 默认都不加入搜索

        if (fieldExtensions != null) {
            for (ConfigExtension extension : fieldExtensions) {
                extension.config(metaField, config, ComponentType.UNKNOWN);
            }
        }
        return new MetaFieldConfigParse(config);
    }

    @Deprecated
    private static Object convert(IMetaField metaField, String defaultValue) {
        try {
            return MetaDataTypeConvert.convert(metaField, defaultValue);
        } catch (Exception e) {
            log.error("默认值转换失败, 将采用null作为默认值, objectCode: {}, fieldCode: {}, err: {}", metaField.objectCode(), metaField.fieldCode(), e.getMessage());
            return null;
        }
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
