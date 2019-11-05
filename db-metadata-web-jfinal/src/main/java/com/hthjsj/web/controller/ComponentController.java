package com.hthjsj.web.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.Utils;
import com.hthjsj.web.component.ComponentType;
import com.hthjsj.web.component.Components;
import com.hthjsj.web.component.ViewComponent;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.query.QueryHelper;
import com.hthjsj.web.ui.IViewAdapter;
import com.hthjsj.web.ui.SmartAssemble;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class ComponentController extends FrontRestController {

    @Override
    public void api() {
        renderJson(Components.me().getRegistry());
    }

    public void init() {
        Components.me().init();
        renderJson(Ret.ok());
    }

    @Override
    public void list() {
        List<Record> components = ServiceManager.componentService().loadComponents();
        List<Kv> results = Lists.newArrayList();
        components.forEach(r -> {
            results.add(Kv.create().set("key", r.getStr("cn")).set("value", r.getStr("en")));
            //            results.add(r.getStr("code"));
        });
        renderJson(Ret.ok("data", results));
    }

    public void load() {
        /**
         * object Code
         * component Type
         */
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String compCode = queryHelper.getComponentCode();


        if (StrKit.notBlank(compCode, objectCode)) {
            Kv objectConfig = Kv.create();
            if (ServiceManager.componentService().hasObjectConfig(compCode, objectCode)) {
                objectConfig.set(objectCode, ServiceManager.componentService().loadObjectConfig(compCode, objectCode));
                objectConfig.set("fields", ServiceManager.componentService().loadFieldsConfigMap(compCode, objectCode));
                renderJson(Ret.ok("data", objectConfig));
            } else {
                IMetaObject metaObject = ServiceManager.dbMetaService().findByCode(objectCode);
                //TODO bad small 此部分逻辑不应在Controller来做,前端统一接收JSONString 并集中处理;
                IViewAdapter<IMetaObject> metaObjectIViewAdapter = SmartAssemble.analysisObject(metaObject, ComponentType.V(compCode));
                Kv fields = Kv.create();
                metaObjectIViewAdapter.fields().forEach(m -> {
                    fields.set(m.getMeta().fieldCode(), m.instanceConfig().toJson());
                });
                objectConfig.set(objectCode, metaObjectIViewAdapter.instanceConfig().toJson());
                objectConfig.set("fields", fields);
                renderJson(Ret.ok("data", objectConfig));
            }
        } else {
            renderJson(Ret.ok("data", Kv.by(compCode, ServiceManager.componentService().loadDefault(compCode).getStr("config"))));
        }


        /**
         * {
         *   "data": {
         *     "test_table": "{\"component_name\":\"FormTmpl\",\"name\":\"test_table_form\",\"conf\":{\"size\":\"medium\",\"label-width\":\"100px\"},\"label\":\"测试表\"}",
         *     "fields": {
         *       "col_int": "{\"component_name\":\"NumBox\",\"name\":\"col_int\",\"conf\":{\"clearable\":\"true\"},\"label\":\"列1\"}",
         *       "created_time": "{\"component_name\":\"DateTimeBox\",\"name\":\"col_created_time\",\"conf\":{\"clearable\":\"true\"},\"label\":\"列9\"}",
         *       "updated_time": "{\"component_name\":\"DateTimeBox\",\"name\":\"col_updated_time\",\"conf\":{\"clearable\":\"true\"},\"label\":\"列11\"}",
         *       "col_nor_str": "{\"component_name\":\"DropDownBox\",\"name\":\"col_nor_str\",\"conf\":{\"clearable\":\"true\"},\"label\":\"列7\"}",
         *       "col_time": "{\"component_name\":\"DateTimeBox\",\"name\":\"col_time\",\"conf\":{\"clearable\":\"true\"},\"label\":\"列5\"}",
         *       "remark": "{\"component_name\":\"TextAreaBox\",\"name\":\"remark\",\"conf\":{\"clearable\":\"true\"},\"label\":\"列12\"}",
         *       "created_by": "{\"component_name\":\"TextBox\",\"name\":\"col_created_by\",\"conf\":{\"clearable\":\"true\"},\"label\":\"列8\"}",
         *       "col_date": "{\"component_name\":\"DateBox\",\"name\":\"col_date\",\"conf\":{\"clearable\":\"true\"},\"label\":\"列5\"}",
         *       "col_bool": "{\"component_name\":\"RadioBox\",\"name\":\"col_bool\",\"data_url\":\"/dict/sex\",\"conf\":{\"clearable\":\"true\"},\"label\":\"列3\"}",
         *       "col_decimal": "{\"component_name\":\"TextBox\",\"name\":\"col_decimal\",\"conf\":{\"clearable\":\"true\"},\"label\":\"列6\"}",
         *       "updated_by": "{\"component_name\":\"TextBox\",\"name\":\"col_updated_by\",\"conf\":{\"clearable\":\"true\"},\"label\":\"列10\"}",
         *       "col_bigint": "{\"component_name\":\"TextBox\",\"name\":\"col_bigint\",\"conf\":{\"clearable\":\"true\"},\"label\":\"列2\"}",
         *       "id": "{\"component_name\":\"TextBox\",\"name\":\"id\",\"conf\":{\"disabled\":\"true\",\"clearable\":\"true\"},\"label\":\"ID\"}"
         *     }
         *   },
         *   "state": "ok"
         * }
         */
    }

    @Override
    public void doAdd() {
        /**
         * object Code
         * component Type
         *
         */
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String compCode = queryHelper.getComponentCode();

        Kv config = Kv.create().set(Utils.toObjectFlat(getRequest().getParameterMap()));
        ViewComponent component = ViewFactory.createViewComponent(compCode);
        if (StrKit.notBlank(compCode, objectCode)) {
            MetaObject metaObject = (MetaObject) ServiceManager.dbMetaService().findByCode(objectCode);
            ServiceManager.componentService().newObjectConfig(component, metaObject, config, false);
        } else {
            ServiceManager.componentService().newDefault(compCode, JSON.parseObject(config.getStr(compCode), Kv.class));
        }
        renderJson(Ret.ok());
    }

    @Override
    public void delete() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String compCode = queryHelper.getComponentCode();

        if (StrKit.notBlank(objectCode, compCode)) {
            ServiceManager.componentService().deleteObjectConfig(compCode, objectCode, false);
            renderJson(Ret.ok());
        } else {
            ServiceManager.componentService().deleteDefault(compCode);
            renderJson(Ret.ok());
        }
    }
}
