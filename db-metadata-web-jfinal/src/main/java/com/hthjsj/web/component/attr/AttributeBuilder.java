package com.hthjsj.web.component.attr;

import com.jfinal.kit.Kv;

import java.util.List;

/**
 * <p> @Date : 2019/11/6 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class AttributeBuilder {

    public static AttributeSteps newBuilder() {
        return new AttributeSteps();
    }

    //        public static void main(String[] args) {
    //            AttributeSteps builder = AttributeBuilder.newBuilder();
    //            builder.componentName("TextBox");
    //            builder.clearable(true).disabled(true).maxlength(10).toKv();
    //            System.out.println(builder.toKv());
    //        }

    interface AttrCustomMeta {

        AttrCustomMeta componentName(String componentCode);

        AttrCustomMeta name(String name);

        AttrCustomMeta label(String label);

        AttributeSteps options(List<Kv> options);

        AttributeSteps dataUrl(String url);
    }

    interface AttrAbility {

        AttrAbility disabled(boolean v);

        AttrAbility resizeable(String v);

        AttrAbility clearable(boolean v);
    }

    interface InputAttr {

        InputAttr defaultVal(String v);

        InputAttr maxlength(int v);

        InputAttr minlength(int v);

        InputAttr showWordLimit(boolean v);

        AttrRender build();
    }

    interface TableAttr {

        TableAttr showOverflowTooltip(boolean flag);
    }

    interface AttrRender {

        Kv render();
    }

    public static class AttributeSteps implements AttrAbility, InputAttr, AttrRender, AttrCustomMeta, TableAttr {

        private Kv config = Kv.create();

        @Override
        public Kv render() {
            return config;
        }

        @Override
        public AttributeSteps disabled(boolean v) {
            setConf("disabled", v);
            return this;
        }

        @Override
        public AttributeSteps resizeable(String v) {
            setConf("resize", v);
            return this;
        }

        @Override
        public AttributeSteps clearable(boolean v) {
            setConf("clearable", v);
            return this;
        }

        @Override
        public InputAttr defaultVal(String v) {
            setConf("value", v);
            return this;
        }

        @Override
        public AttributeSteps maxlength(int v) {
            setConf("maxlength", v);
            return this;
        }

        @Override
        public AttributeSteps minlength(int v) {
            setConf("minlength", v);
            return this;
        }

        @Override
        public InputAttr showWordLimit(boolean v) {
            setConf("show-word-limit", v);
            return this;
        }

        @Override
        public AttrRender build() {
            return this;
        }

        @Override
        public AttrCustomMeta componentName(String componentCode) {
            config.setIfNotBlank("component_name", componentCode);
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
        public AttributeSteps options(List<Kv> options) {
            if (options != null && options.size() > 0) {
                set("options", options);
            }
            return this;
        }

        @Override
        public AttributeSteps dataUrl(String url) {
            config.setIfNotBlank("data_url", url);
            return this;
        }

        @Override
        public TableAttr showOverflowTooltip(boolean flag) {
            setConf("show-overflow-tooltip", flag);
            return this;
        }

        /**
         * 扩展
         */
        public AttributeSteps set(String key, Object value) {
            config.setIfNotNull(key, value);
            return this;
        }

        public AttributeSteps setConf(String key, Object value) {
            config.putIfAbsent("conf", Kv.create());
            ((Kv) config.getAs("conf")).setIfNotNull(key, value);
            return this;
        }
    }
}
