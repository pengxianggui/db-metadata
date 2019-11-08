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

        AttrAbility disabled(boolean i);

        AttrAbility resizeable(boolean i);

        AttrAbility clearable(boolean i);
    }

    interface AttrMaxMinDefault {

        AttrMaxMinDefault maxlength(int i);

        AttrMaxMinDefault minlength(int i);

        AttrRender build();
    }

    interface AttrRender {

        Kv render();
    }

    public static class AttributeSteps implements AttrAbility, AttrMaxMinDefault, AttrRender, AttrCustomMeta {

        private Kv config = Kv.create();

        @Override
        public Kv render() {
            return config;
        }

        @Override
        public AttributeSteps disabled(boolean i) {
            config.putIfAbsent("conf", Kv.create());
            config.set("disabled", i);
            return this;
        }

        @Override
        public AttributeSteps resizeable(boolean i) {
            config.putIfAbsent("conf", Kv.create());
            ((Kv) config.getAs("conf")).set("resize", i);
            return this;
        }

        @Override
        public AttributeSteps clearable(boolean i) {
            config.putIfAbsent("conf", Kv.create());
            ((Kv) config.getAs("conf")).set("clearable", i);
            return this;
        }

        @Override
        public AttributeSteps maxlength(int i) {
            config.putIfAbsent("conf", Kv.create());
            ((Kv) config.getAs("conf")).set("maxlength", i);
            return this;
        }

        @Override
        public AttributeSteps minlength(int i) {
            config.putIfAbsent("conf", Kv.create());
            ((Kv) config.getAs("conf")).set("minlength", i);
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
    }
}
