package com.hthjsj.web.component.attr;

import com.jfinal.kit.Kv;

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
            config.putIfAbsent("conf", Kv.create());
            config.set("disabled", v);
            return this;
        }

        @Override
        public AttributeSteps resizeable(String v) {
            config.putIfAbsent("conf", Kv.create());
            //            none, both, horizontal, vertical
            ((Kv) config.getAs("conf")).set("resize", v);
            return this;
        }

        @Override
        public AttributeSteps clearable(boolean v) {
            config.putIfAbsent("conf", Kv.create());
            ((Kv) config.getAs("conf")).set("clearable", v);
            return this;
        }

        @Override
        public InputAttr defaultVal(String v) {
            config.putIfAbsent("conf", Kv.create());
            ((Kv) config.getAs("conf")).set("defaultVal", v);
            return this;
        }

        @Override
        public AttributeSteps maxlength(int v) {
            config.putIfAbsent("conf", Kv.create());
            ((Kv) config.getAs("conf")).set("maxlength", v);
            showWordLimit(true);
            return this;
        }

        @Override
        public AttributeSteps minlength(int v) {
            config.putIfAbsent("conf", Kv.create());
            ((Kv) config.getAs("conf")).set("minlength", v);
            showWordLimit(true);
            return this;
        }

        @Override
        public InputAttr showWordLimit(boolean v) {
            config.putIfAbsent("conf", Kv.create());
            ((Kv) config.getAs("conf")).set("show-word-limit", v);
            return this;
        }

        @Override
        public AttrRender build() {
            return this;
        }

        @Override
        public AttrCustomMeta componentName(String componentCode) {
            config.set("component_name", componentCode);
            return this;
        }

        @Override
        public AttrCustomMeta name(String name) {
            config.set("name", name);
            return this;
        }

        @Override
        public AttrCustomMeta label(String label) {
            config.set("label", label);
            return this;
        }

        @Override
        public TableAttr showOverflowTooltip(boolean flag) {
            config.putIfAbsent("conf", Kv.create());
            ((Kv) config.getAs("conf")).set("show-overflow-tooltip", flag);
            return this;
        }
    }
}
