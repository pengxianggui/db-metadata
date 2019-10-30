package com.hthjsj.web.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.component.ComponentFactory;
import com.hthjsj.web.component.Components;
import com.hthjsj.web.component.ViewComponent;
import com.hthjsj.web.query.QueryHelper;
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
        List<Record> components = ServiceManager.componentService().listComponents();
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
         *
         */
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String compCode = queryHelper.getComponentCode();
        String fieldCode = getPara("fieldCode", "");
        /**
         * return
         * {
         *  config:{
         *  }
         * }
         */
        if (StrKit.notBlank(objectCode, compCode, fieldCode)) {
            renderJson(Ret.ok("data", ServiceManager.componentService().loadFieldConfig(compCode, objectCode, fieldCode).getStr("config")));
        } else if (StrKit.notBlank(objectCode, compCode)) {
            renderJson(Ret.ok("data", ServiceManager.componentService().loadObjectConfig(compCode, objectCode).getStr("config")));
        } else {
            renderJson(Ret.ok("data", ServiceManager.componentService().loadDefault(compCode).getStr("config")));
        }
    }

    @Override
    public void toAdd() {

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
        String configString = getPara("conf", "{}");


        //FIXME 属性配置,逐条保存,后期需改成 保存整个元对象配时级联保存属性配置
        String fieldCode = getPara("fieldCode", "");


        Kv config = JSON.parseObject(configString, Kv.class);

        ViewComponent component = ComponentFactory.createViewComponent(compCode);
        if (StrKit.notBlank(objectCode, compCode, fieldCode)) {
            ServiceManager.componentService().newFieldConfig(component, objectCode, fieldCode, config);
        } else if (StrKit.notBlank(objectCode, compCode)) {
            MetaObject metaObject = (MetaObject) ServiceManager.dbMetaService().findByCode(objectCode);
            ServiceManager.componentService().newObjectConfig(component, metaObject, config, false);
            renderJson(Ret.ok());
        } else {
            ServiceManager.componentService().newDefault(compCode, config);
            renderJson(Ret.ok());
        }
    }

    /**
     * set Component config
     */
    public void config() {
        /**
         * object code
         * component Type config string;
         */
    }

    /**
     * The component config default value;
     */
    public void tmpl() {
        /**
         * component Type
         */
        String component = new QueryHelper(this).getComponentType();
        renderJson(Ret.ok("data", ServiceManager.componentService().loadDefault(component)));
        /**
         * return
         * {
         *    field 1:"",
         *    field 2:"",
         *    field 3:"",
         *    field 4:"",
         *    field 5:"",
         *    base :""
         * }
         */
    }
}
