package com.hthjsj.analysis.meta;

import com.alibaba.fastjson.JSON;
import com.jfinal.kit.StrKit;

import java.util.Map;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaConfigFactory {

    public static MetaFieldConfig createEmptyFieldConfig(String objectCode, String fieldCode) {
        return new MetaFieldConfig(objectCode, fieldCode);
    }

    public static MetaObjectConfig createEmptyObjectConfig(String objectCode) {
        return new MetaObjectConfig(objectCode);
    }

    public static MetaObjectConfig createV1ObjectConfig(String objectCode, String isUUIDPri) {
        MetaObjectConfig metaObjectConfig = createEmptyObjectConfig(objectCode);
        metaObjectConfig.set("isUUIDPrimary", Boolean.parseBoolean(isUUIDPri));
        //common config
        metaObjectConfig.set("isMultiple", false);
        return metaObjectConfig;
    }

    public static MetaFieldConfig createV1FieldConfig(String objectCode, String fieldCode, String defaultValue, String isNUll) {
        MetaFieldConfig metaFieldConfig = createEmptyFieldConfig(objectCode, fieldCode);
        metaFieldConfig.set("isNullable", "yes".equalsIgnoreCase(isNUll));
        metaFieldConfig.set("defaultValue", defaultValue == null ? "" : defaultValue);
        //common config
        metaFieldConfig.set("isMultiple", false);
        return metaFieldConfig;
    }

    public static class MetaFieldConfig extends MetaData implements IMetaConfig {

        public static final String VERSION_0_1 = "0.1";

        public MetaFieldConfig(String config, String objectCode, String fieldCode) {
            if (!StrKit.isBlank(config)) {
                set(JSON.parseObject(config));
            }
            set("objectCode", objectCode);
            set("fieldCode", fieldCode);
            set("version", VERSION_0_1);
        }

        public MetaFieldConfig(String objectCode, String fieldCode) {
            this("", objectCode, fieldCode);
        }

        @Override
        public String module() {
            return MODULE_FIELD;
        }

        @Override
        public String moduleCode() {
            return new StringBuilder(getStr("objectCode")).append(".").append(getStr("fieldCode")).toString();
        }

        @Override
        public String getVersion() {
            return getStr("version");
        }

        @Override
        public Map getConfig() {
            return this;
        }

        @Override
        public void setItem(Object key, Object value) {
            set(key, value);
        }

        @Override
        public <T> T getItem(Object key) {
            return getAs(key);
        }
    }

    public static class MetaObjectConfig extends MetaData implements IMetaConfig {

        public static final String VERSION_0_1 = "0.1";

        public MetaObjectConfig(String config, String objectCode) {
            if (!StrKit.isBlank(config)) {
                set(JSON.parseObject(config));
            }
            set("objectCode", objectCode);
        }

        public MetaObjectConfig(String objectCode) {
            this("", objectCode);
        }

        public boolean isUUIDPrimary() {
            return getBoolean("isUUIDPrimary");
        }

        @Override
        public String module() {
            return MODULE_OBJECT;
        }

        @Override
        public String moduleCode() {
            return getStr("objectCode");
        }

        @Override
        public String getVersion() {
            return getStr("version");
        }

        @Override
        public Map getConfig() {
            return this;
        }

        @Override
        public void setItem(Object key, Object value) {
            set(key, value);
        }

        @Override
        public <T> T getItem(Object key) {
            return getAs(key);
        }
    }
}
