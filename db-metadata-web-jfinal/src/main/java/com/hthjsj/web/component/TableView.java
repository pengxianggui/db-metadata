package com.hthjsj.web.component;

import com.alibaba.fastjson.JSONObject;
import com.hthjsj.analysis.meta.MetaObject;
import com.jfinal.kit.Kv;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

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

    @Setter(AccessLevel.PACKAGE)
    @Accessors(chain = true)
    private String name;

    @Setter(AccessLevel.PACKAGE)
    @Accessors(chain = true)
    private String label;

    @Setter(AccessLevel.PACKAGE)
    @Accessors(chain = true)
    private MetaObject metaObject;

    private Kv meta = Kv.create();

    private Kv conf = Kv.create();

    List<Kv> fields = new ArrayList<>(0);

    public TableView() {
        //        setShowBehavior(new TableViewDefaultBehavior());
    }

    public TableView(String name, String label) {
        this();
        this.name = name;
        this.label = label;
    }

    @Override
    public String config() {
        return meta.toJson();
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.TABLEVIEW;
    }

    public TableView dataUrl(String url) {
        meta.setIfNotBlank("data_url", url);
        return this;
    }

    @Override
    public Kv toKv() {
        meta.setIfNotBlank("name", name);
        meta.setIfNotBlank("label", label);
        meta.setIfNotBlank("component_name", type());
        meta.setIfNotBlank("conf", "");
        getInject().inject(meta, conf);
        //        if (metaObject != null) {
        //            meta.set("columns", metaObject.fields().stream().map(field -> {
        //                return Kv.create().set("component_name", "TextBox").set("name", field.en()).set("label", field.cn()).set("conf", getShowBehavior().getBehaviorRuleData());
        //            }).collect(Collectors.toList()));
        //        }
        //                kv.putAll(getShowBehavior().getBehaviorRuleData());
        return meta;
    }

    /**
     * 将容器层的config下垂
     *
     * @param config
     */
    private void configPushDown(Kv config) {
        JSONObject jsonObject;
        //        jsonObject.putIfAbsent()
    }
    //
    //    class TableViewDefaultBehavior extends Behavior {
    //
    //        public TableViewDefaultBehavior() {
    //            behaviorRuleData.set("show-overflow-tooltip", true);
    //            //            behaviorRuleData.set("selection", true);
    //            //            behaviorRuleData.set("singleSelected", true);
    //            //            behaviorRuleData.set("showRowNum", true);
    //        }
    //
    //        public void load(Kv kv) {
    //            behaviorRuleData.putAll(kv);
    //        }
    //    }
}
