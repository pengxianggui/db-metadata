package com.github.md.web.component.attr;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.kit.Kv;

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

        AttrCustomMeta defaultVal(Object defaultVal);

        AttrCustomMeta options(List<Kv> options);

        AttrCustomMeta dataUrl(String url);

        AttrCustomMeta deleteUrl(String url);

        AttrCustomMeta inline(boolean v);

        AttrCustomMeta hidden(boolean hidden);

        AttrCustomMeta sort(int sort);
    }

    interface AttrAbility {

        AttrAbility disabled(boolean v);

        AttrAbility resizeable(String v);

        AttrAbility clearable(boolean v);

        AttrAbility multiple(boolean v);
    }

    interface InputAttr {

        InputAttr maxlength(int v);

        InputAttr minlength(int v);

        InputAttr showWordLimit(boolean v);

        InputAttr readOnly(boolean v);

        AttrRender build();
    }

    interface TableAttr {

        TableAttr showOverflowTooltip(boolean flag);

        TableAttr fit(boolean fit);

        TableAttr width(String width);

        TableAttr minWidth(String minWidth);

        TableAttr fixed(String fixed);

        /**
         * @param align left/center/right
         * @return
         */
        TableAttr align(String align);

        /**
         * @param headerAlign left/center/right
         * @return
         */
        TableAttr headerAlign(String headerAlign);

        TableAttr className(String className);

        TableAttr labelClassName(String labelClassName);

        TableAttr renderFn(String fn);
    }

    interface TreeTableAttr {

        TreeTableAttr dataUrl(String dataUrl);

        TreeTableAttr multiSelect(boolean multi);

        TreeTableAttr editable(boolean editable);

        TreeTableAttr fit(boolean fit);

        TreeTableAttr rowKey(String rowKey);

        TreeTableAttr hasChildren(String functionName);

        TreeTableAttr children(String childPropertyName);

        //        初始展开的行, 数组中的内容为row-key指定属性对应的值
        TreeTableAttr expandRowKeys(String[] rowkeys);

        TreeTableAttr lazy(boolean lazy);

        TreeTableAttr highlightCurrentRow(boolean isHighlight);

        TreeTableAttr renderFn(String fn);
    }

    interface TreeAttr {
        TreeAttr props(String label, String children);
    }

    interface FileUploadAttr {

        FileUploadAttr autoUpload(boolean v);

        FileUploadAttr actionUrl(String url);

        FileUploadAttr seats(String[] seats);

        FileUploadAttr listType(String listType);
    }

    interface AttrRender {

        Kv render();
    }

    public static class FatAttributeBuilder implements AttrAbility, InputAttr, AttrRender, AttrCustomMeta, TableAttr, FileUploadAttr, TreeTableAttr, TreeAttr {

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
        public AttrCustomMeta defaultVal(Object defaultVal) {
            return set("default_value", defaultVal);
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
        public TreeTableAttr multiSelect(boolean multi) {
            return set("multi_select", multi);
        }

        @Override
        public TreeTableAttr editable(boolean editable) {
            return set("editable", editable);
        }

        @Override
        public TreeTableAttr rowKey(String rowKey) {
            return setConf("row-key", rowKey);
        }

        @Override
        public TreeTableAttr hasChildren(String functionName) {
            setConf("tree-props", Kv.create());
            ((Kv) ((Kv) config.getAs("conf")).getAs("tree-props")).setIfNotBlank("hasChildren", functionName);
            return this;
        }

        @Override
        public TreeTableAttr children(String childPropertyName) {
            setConf("tree-props", Kv.create());
            ((Kv) ((Kv) config.getAs("conf")).getAs("tree-props")).setIfNotBlank("children", childPropertyName);
            return this;
        }

        @Override
        public TreeTableAttr expandRowKeys(String[] rowkeys) {
            return setConf("expand-row-keys", rowkeys);
        }

        @Override
        public TreeTableAttr lazy(boolean lazy) {
            return setConf("lazy", lazy);
        }

        @Override
        public TreeTableAttr highlightCurrentRow(boolean isHighlight) {
            return setConf("highlight-current-row", isHighlight);
        }

        @Override
        public AttrCustomMeta deleteUrl(String url) {
            return set("delete_url", url);
        }

        @Override
        public AttrCustomMeta inline(boolean v) {
            return set("inline", v);
        }

        @Override
        public AttrCustomMeta hidden(boolean hidden) {
            set("hidden", hidden);
            return this;
        }

        @Override
        public AttrCustomMeta sort(int sort) {
            return set("sort", sort);
        }

        @Override
        public TableAttr showOverflowTooltip(boolean flag) {
            return setConf("show-overflow-tooltip", flag);
        }

        @Override
        public FatAttributeBuilder fit(boolean fit) {
            return setConf("fit", fit);
        }

        @Override
        public TableAttr width(String width) {
            return setConf("width", width);
        }

        @Override
        public TableAttr minWidth(String minWidth) {
            return setConf("min-width", minWidth);
        }

        @Override
        public TableAttr fixed(String fixed) {
            return setConf("fixed", fixed);
        }

        @Override
        public TableAttr align(String align) {
            return setConf("align", align);
        }

        @Override
        public TableAttr headerAlign(String headerAlign) {
            return setConf("header-align", headerAlign);
        }

        @Override
        public TableAttr className(String className) {
            return setConf("class-name", className);
        }

        @Override
        public TableAttr labelClassName(String labelClassName) {
            return setConf("label-class-name", labelClassName);
        }

        @Override
        public FatAttributeBuilder renderFn(String fn) {
            return set("render", fn);
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

        @Override
        public FileUploadAttr seats(String[] seats) {
            set("seats", seats);
            return this;
        }

        @Override
        public FileUploadAttr listType(String listType) {
            return setConf("list-type", listType);
        }


        @Override
        public TreeAttr props(String label, String children) {
            setConf("props", Kv.create().set("label", label).set("children", children));
            return this;
        }
    }
}
