package com.hthjsj.web.controller;

import com.google.common.collect.Lists;
import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.component.AbstractComponent;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.kit.UtilKit;
import com.hthjsj.web.query.QueryHelper;
import com.hthjsj.web.ui.ComponentInstanceConfig;
import com.hthjsj.web.ui.MetaObjectViewAdapter;
import com.hthjsj.web.ui.OptionsKit;
import com.hthjsj.web.ui.UIManager;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * 从组件视角触发提供的访问方法,主要是对配置信息的操作
 *
 * <p> @Date : 2019/12/26 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ComponentController extends FrontRestController {

    public void meta() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String compCode = queryHelper.getComponentCode();

        IMetaObject metaObject = metaService().findByCode(objectCode);
        MetaObjectViewAdapter metaObjectViewAdapter = UIManager.getView(metaObject, ComponentType.V(compCode));

        renderJson(Ret.ok("data", metaObjectViewAdapter.getComponent().toKv()));
    }

    /**
     * 返回某Component的关联元对象实例
     */
    public void contact() {
        QueryHelper queryHelper = new QueryHelper(this);
        String componentCode = queryHelper.getComponentCode();
        String objectCode = queryHelper.getObjectCode();
        boolean kv = getBoolean("kv", false);
        List<String> result = componentService().loadInstanceCodeByObjectCode(objectCode, ComponentType.V(componentCode));
        if (kv) {
            renderJson(Ret.ok("data", OptionsKit.transKeyValue(result.toArray(new String[0]))));
            return;
        }
        renderJson(Ret.ok("data", result));
    }

    @Override
    public void list() {
        List<Record> components = componentService().loadComponents();
        List<Kv> results = Lists.newArrayList();
        components.forEach(r -> {
            results.add(Kv.create().set("key", r.getStr("cn")).set("value", r.getStr("en")));
        });
        renderJson(Ret.ok("data", results));
    }

    /**
     * TODO 自动计算入口。应当可以支持 根据元字段 + 字段组件类型 来自动计算。实例配置界面切换字段组件类型时，触发该自动的重新自动计算。另外应当支持ui实例配置界面，先为每个字段
     * 选定组件，然后一键自动计算。这样得到的自动计算配置会更精确。而且，就无需前端去和组件的默认配置做一个 $merge 了!
     * 获取实例配置,2种方式
     * 1. objectCode + componentCode
     * 2. instanceCode
     */
    public void load() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String compCode = queryHelper.getComponentCode();
        String instanceCode = queryHelper.getInstanceCode();

        // instanceCode 获取
        if (StrKit.notBlank(instanceCode)) {
            if (componentService().hasObjectConfig(instanceCode)) {
                renderJson(Ret.ok("data", componentService().loadObjectConfig(instanceCode)));
            } else {
                IMetaObject metaObject = metaService().findByCode(objectCode);
                MetaObjectViewAdapter metaObjectViewAdapter = UIManager.getSmartAutoView(metaObject, ComponentType.V(compCode));
                renderJson(Ret.ok("data", metaObjectViewAdapter.getInstanceConfig().set("isAutoComputed", true)));
            }
            return;
        }

        // objectCode + componentCode
        if (StrKit.notBlank(compCode, objectCode)) {
            IMetaObject metaObject = metaService().findByCode(objectCode);
//            //自动计算的配置
            MetaObjectViewAdapter metaObjectViewAdapter = UIManager.getSmartAutoView(metaObject, ComponentType.V(compCode));
            renderJson(Ret.ok("data", metaObjectViewAdapter.getInstanceConfig().set("isAutoComputed", true)));
//            }
        } else {
            renderJson(Ret.ok("data", Kv.by(compCode, componentService().loadDefault(compCode).getStr("config"))));
        }


        /**t
         * {
         *   "data": {
         *     "test_table": "{\"component_name\":\"FormView\",\"name\":\"test_table_form\",\"conf\":{\"size\":\"medium\",\"label-width\":\"100px\"},\"label\":\"测试表\"}",
         *     "fieldsMap": {
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

        String instanceCode = queryHelper.getInstanceCode();
        String instanceName = queryHelper.getInstanceName();
        addInstanceConf(objectCode, compCode, instanceCode, instanceName);
        renderJson(Ret.ok());
    }

    /**
     * 添加实例配置
     *
     * @param objectCode
     * @param compCode
     * @param instanceCode
     * @param instanceName
     * @return
     */
    private boolean addInstanceConf(String objectCode, String compCode, String instanceCode, String instanceName) {
        Kv config = getKv();
        Component component = ViewFactory.createEmptyViewComponent(compCode);

        if (StrKit.notBlank(compCode, objectCode, instanceCode)) {
            if (componentService().hasObjectConfig(instanceCode)) {
                throw new RuntimeException(String.format("%s配置信息已存在,请重新输入唯一编码", instanceCode));
            }

            // TODO 需要整体统一更改为instanceCode唯一, compCode + objectCode 可多套
            if (componentService().hasObjectConfig(compCode, objectCode)) {
                throw new RuntimeException(String.format("%s+%s的配置已经存在, 目前暂未全面支持多套objectCode + componentCode配置。敬请期待!", objectCode, compCode));
            }

            IMetaObject metaObject = metaService().findByCode(objectCode);
            ComponentInstanceConfig componentInstanceConfig = ComponentInstanceConfig.New(config,
                    metaObject.code(),
                    instanceCode,
                    instanceName,
                    component.componentType());
            componentService().newObjectConfig(component, metaObject, componentInstanceConfig);
        } else {
            componentService().newDefault(compCode, UtilKit.getKv(config.getStr(compCode)));
        }
        return true;
    }

    /**
     * 一键自动计算
     */
    @ActionKey("component/import-auto-computed")
    public void oneKeyAutoComputed() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String compCodes = getPara("componentCodes");

        Db.tx(() -> {
            for (String compCode : compCodes.split(",")) {
                boolean r = addInstanceConf(objectCode, compCode, objectCode + "." + compCode, "系统自动计算");
                if (!r) {
                    return false;
                }
            }
            return true;
        });

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
        String instanceCode = queryHelper.getInstanceCode();
        String instanceName = queryHelper.getInstanceName();
        Kv config = getKv();

        Component component = AbstractComponent.newInstance(compCode);
        if (StrKit.notBlank(compCode, objectCode, instanceCode)) {
            IMetaObject metaObject = metaService().findByCode(objectCode);
            ComponentInstanceConfig componentInstanceConfig = ComponentInstanceConfig.New(config,
                    metaObject.code(),
                    instanceCode,
                    instanceName,
                    component.componentType());
            componentService().updateObjectConfig(component, metaObject, componentInstanceConfig);
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
