package com.hthjsj.web.component.attr;

import com.hthjsj.analysis.component.ComponentType;
import com.jfinal.kit.Kv;

import java.util.List;

/**
 * 属性构建工具,主要用于构建Element控件的自定属性
 * <p> @Date : 2019/11/6 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class AttributeBuilder {

    private AttributeBuilder() {
    }

    public static FatAttributeBuilder newBuilder() {
        return new FatAttributeBuilder();
    }

    public static FatAttributeBuilder newBuilder(Kv config) {
        return new FatAttributeBuilder(config);
    }

    interface AttrCustomMeta {

        AttrCustomMeta componentName(ComponentType componentType);

        AttrCustomMeta name(String name);

        AttrCustomMeta label(String label);

        AttrCustomMeta options(List<Kv> options);

        AttrCustomMeta dataUrl(String url);

        AttrCustomMeta inline(boolean v);
    }

    interface AttrAbility {

        AttrAbility disabled(boolean v);

        AttrAbility resizeable(String v);

        AttrAbility clearable(boolean v);

        AttrAbility multiple(boolean v);
    }

    interface InputAttr {

        InputAttr defaultVal(String v);

        InputAttr maxlength(int v);

        InputAttr minlength(int v);

        InputAttr showWordLimit(boolean v);

        InputAttr readOnly(boolean v);

        AttrRender build();
    }

    interface TableAttr {

        TableAttr showOverflowTooltip(boolean flag);
    }

    interface FileUploadAttr {

        FileUploadAttr autoUpload(boolean v);

        FileUploadAttr actionUrl(String url);
    }

    interface AttrRender {

        Kv render();
    }

    public static class FatAttributeBuilder implements AttrAbility, InputAttr, AttrRender, AttrCustomMeta, TableAttr, FileUploadAttr {

        private Kv config = Kv.create();

        public FatAttributeBuilder() {
        }

        public FatAttributeBuilder(Kv config) {
            this.config = config;
        }

        @Override
        public Kv render() {
            return config;
        }

        @Override
        public FatAttributeBuilder disabled(boolean v) {
            return setConf("disabled", v);
        }

        @Override
        public FatAttributeBuilder resizeable(String v) {
            return setConf("resize", v);
        }

        @Override
        public FatAttributeBuilder clearable(boolean v) {
            return setConf("clearable", v);
        }

        @Override
        public AttrAbility multiple(boolean v) {
            return setConf("multiple", v);
        }

        @Override
        public InputAttr defaultVal(String v) {
            return set("default_value", v);
        }

        @Override
        public FatAttributeBuilder maxlength(int v) {
            return setConf("maxlength", v);
        }

        @Override
        public FatAttributeBuilder minlength(int v) {
            return setConf("minlength", v);
        }

        @Override
        public InputAttr showWordLimit(boolean v) {
            return setConf("show-word-limit", v);
        }

        @Override
        public InputAttr readOnly(boolean v) {
            return setConf("readonly", v);
        }

        @Override
        public AttrRender build() {
            return this;
        }

        @Override
        public AttrCustomMeta componentName(ComponentType componentType) {
            config.setIfNotBlank("component_name", componentType.getCode());
            return this;
        }

        @Override
        public AttrCustomMeta name(String name) {
            config.setIfNotBlank("name", name);
            return this;
        }

        @Override
        public AttrCustomMeta label(String label) {
            config.setIfNotBlank("label", label);
            return this;
        }

        @Override
        public FatAttributeBuilder options(List<Kv> options) {
            if (options != null && options.size() > 0) {
                set("options", options);
            }
            return this;
        }

        @Override
        public FatAttributeBuilder dataUrl(String url) {
            config.setIfNotBlank("data_url", url);
            return this;
        }

        @Override
        public AttrCustomMeta inline(boolean v) {
            return set("inline", v);
        }

        @Override
        public TableAttr showOverflowTooltip(boolean flag) {
            return setConf("show-overflow-tooltip", flag);
        }

        /**
         * 扩展
         */
        public FatAttributeBuilder set(String key, Object value) {
            config.setIfNotNull(key, value);
            return this;
        }

        public FatAttributeBuilder setConf(String key, Object value) {
            config.putIfAbsent("conf", Kv.create());
            ((Kv) config.getAs("conf")).setIfNotNull(key, value);
            return this;
        }

        @Override
        public FileUploadAttr autoUpload(boolean v) {
            return setConf("auto-upload", v);
        }

        @Override
        public FileUploadAttr actionUrl(String url) {
            return setConf("action", url);
        }
    }
}
