package com.hthjsj.web.component;

import com.alibaba.fastjson.JSON;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.analysis.meta.ui.ViewComponent;
import com.jfinal.aop.Aop;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Record;

import java.util.Map;

/**
 * <p> Class title: 表格显示控件</p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class TableView extends ViewComponent {

    final String cn = "表格控件";

    final String en = "TableView";

    private Kv globalConfig = Kv.create();

    private Kv metaObjectConfig = Kv.create();

    public TableView() {
    }

    public TableView(MetaObject metaObject) {
        /**
         * 1. 初始化全局配置 from db;
         * 2. 允许实例层级覆盖配置
         */
        Record record = Aop.get(ComponentService.class).load(code());
        globalConfig.putAll(JSON.parseObject(record.getStr("config"), Map.class));
        metaObjectConfig.set("metaObjCode", metaObject.code());
    }

    public void setGlobal(Object key, Object value) {
        globalConfig.set(key, value);
    }

    @Override public String config() {
        return renderMeta().toJson();
    }

    @Override public void config(String config) {
    }

    public Kv renderMeta() {
        globalConfig.putAll(metaObjectConfig);
        return globalConfig;
    }

    @Override public String name() {
        return cn;
    }

    @Override public String code() {
        return en;
    }

    @Override public String type() {
        return this.getClass().getSimpleName();
    }
}
