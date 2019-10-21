package com.hthjsj.web.component;

import com.hthjsj.analysis.meta.MetaObject;
import com.jfinal.kit.Kv;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.stream.Collectors;

/**
 * <p> Class title: 表格显示控件</p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Data
public class TableView extends ViewComponent {

    private String name;

    private String label;

    private Kv globalConfig = Kv.create();

    private Kv metaObjectConfig = Kv.create();

    @Setter
    @Accessors(chain = true)
    private MetaObject metaObject;

    public TableView() {
        setShowBehavior(new TableViewDefaultBehavior());
    }

    public TableView(String name, String label) {
        this();
        this.name = name;
        this.label = label;
    }

    public TableView(MetaObject metaObject) {
        this();
        if (metaObject == null) {
            throw new ComponentException("metaObject==null,必须传入有效的metaObject");
        }
        this.metaObject = metaObject;
        /**
         * 1. 初始化全局配置 from db;
         * 2. 允许实例层级覆盖配置
         */
        //        Record record = Aop.get(ComponentService.class).load(type());
        //        globalConfig.putAll(JSON.parseObject(record.getStr("config"), Map.class));

        //        metaObjectConfig.set("metaObjCode", metaObject.code());
        //        metaObjectConfig.set("fields", metaObject.fields());
    }

    @Override
    public String config() {
        return globalConfig.toJson();
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.TABLEVIEW;
    }

    public TableView dataUrl(String url) {
        globalConfig.setIfNotBlank("data_url", url);
        return this;
    }

    @Override
    public Kv toKv() {
        globalConfig.putAll(metaObjectConfig);
        globalConfig.setIfNotBlank("name", name);
        globalConfig.setIfNotBlank("label", label);
        globalConfig.setIfNotBlank("component_name", type());
        globalConfig.setIfNotBlank("conf", "");
        if (metaObject != null) {
            globalConfig.set("columns", metaObject.fields().stream().map(field -> {
                return Kv.create().set("component_name", "TextBox").set("name", field.en()).set("label", field.cn()).set("conf", getShowBehavior().getBehaviorRuleData());
            }).collect(Collectors.toList()));
        }
        //                kv.putAll(getShowBehavior().getBehaviorRuleData());
        return globalConfig;
    }

    class TableViewDefaultBehavior extends Behavior {

        public TableViewDefaultBehavior() {
            behaviorRuleData.set("show-overflow-tooltip", true);
            //            behaviorRuleData.set("selection", true);
            //            behaviorRuleData.set("singleSelected", true);
            //            behaviorRuleData.set("showRowNum", true);
        }

        public void load(Kv kv) {
            behaviorRuleData.putAll(kv);
        }
    }
}
