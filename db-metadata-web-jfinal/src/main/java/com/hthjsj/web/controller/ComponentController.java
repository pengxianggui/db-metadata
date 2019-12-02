package com.hthjsj.web.controller;

import com.google.common.collect.Lists;
import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.UtilKit;
import com.hthjsj.web.component.Components;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.query.QueryHelper;
import com.hthjsj.web.ui.MetaObjectViewAdapter;
import com.hthjsj.web.ui.RenderHelper;
import com.hthjsj.web.ui.UIManager;
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

    public void meta() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String compCode = queryHelper.getComponentCode();

        MetaObject metaObject = (MetaObject) metaService().findByCode(objectCode);
        MetaObjectViewAdapter metaObjectViewAdapter = UIManager.getView(metaObject, ComponentType.V(compCode));

        renderJson(Ret.ok("data", metaObjectViewAdapter.getComponent().toKv()));
    }

    /**
     * 返回某Component的关联元对象实例
     */
    public void contact() {
        String componentCode = new QueryHelper(this).getComponentCode();
        renderJson(componentService().loadObjectsByType(componentCode));
    }

    @Override
    public void list() {
        List<Record> components = componentService().loadComponents();
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
            MetaObject metaObject = (MetaObject) metaService().findByCode(objectCode);
            //已存在的配置
            if (componentService().hasObjectConfig(compCode, objectCode)) {

                MetaObjectViewAdapter metaObjectViewAdapter = UIManager.getView(metaObject, ComponentType.V(compCode));
                renderJson(Ret.ok("data", RenderHelper.renderObjectFieldsMap(metaObjectViewAdapter)));
            }
            //自动计算的配置
            else {

                MetaObjectViewAdapter metaObjectViewAdapter = UIManager.getSmartAutoView(metaObject, ComponentType.V(compCode));
                renderJson(Ret.ok("data", RenderHelper.renderObjectFieldsMap(metaObjectViewAdapter)).set("msg", "自动计算首次配置"));
            }
        } else {
            renderJson(Ret.ok("data", Kv.by(compCode, componentService().loadDefault(compCode).getStr("config"))));
        }


        /**t
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
         */
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String compCode = queryHelper.getComponentCode();

        Kv config = Kv.create().set(UtilKit.toObjectFlat(getRequest().getParameterMap()));
        Component component = ViewFactory.createEmptyViewComponent(compCode);
        if (StrKit.notBlank(compCode, objectCode)) {
            if (componentService().hasObjectConfig(compCode, objectCode)) {
                renderJson(Ret.fail("msg", String.format("%s-%s配置信息已存在,先执行删除操作;", compCode, objectCode)));
                return;
            }
            MetaObject metaObject = (MetaObject) metaService().findByCode(objectCode);
            componentService().newObjectConfig(component, metaObject, config);
        } else {
            componentService().newDefault(compCode, UtilKit.getKv(config.getStr(compCode)));
        }
        renderJson(Ret.ok());
    }

    @Override
    public void doUpdate() {
        /**
         * object Code
         * component Type
         */
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String compCode = queryHelper.getComponentCode();

        Kv config = Kv.create().set(UtilKit.toObjectFlat(getRequest().getParameterMap()));
        Component component = ViewFactory.createEmptyViewComponent(compCode);
        if (StrKit.notBlank(compCode, objectCode)) {
            MetaObject metaObject = (MetaObject) metaService().findByCode(objectCode);
            componentService().updateObjectConfig(component, metaObject, config);
        } else {
            componentService().updateDefault(compCode, UtilKit.getKv(config.getStr(compCode)));
        }
        renderJson(Ret.ok());
    }

    @Override
    public void delete() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String compCode = queryHelper.getComponentCode();

        if (StrKit.notBlank(objectCode, compCode)) {
            componentService().deleteObjectConfig(compCode, objectCode, false);
            renderJson(Ret.ok());
        } else {
            componentService().deleteDefault(compCode);
            renderJson(Ret.ok());
        }
    }
}
