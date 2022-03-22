package com.github.md.web.controller;

import cn.com.asoco.util.AssertUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.md.analysis.component.Component;
import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.kit.Ret;
import com.github.md.web.ServiceManager;
import com.github.md.web.WebException;
import com.github.md.web.kit.UtilKit;
import com.github.md.web.user.auth.annotations.Type;
import com.github.md.web.user.auth.annotations.MetaAccess;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.web.component.AbstractComponent;
import com.github.md.web.component.ComponentException;
import com.github.md.web.component.ViewFactory;
import com.github.md.web.query.QueryHelper;
import com.github.md.web.ui.ComponentInstanceConfig;
import com.github.md.web.ui.MetaObjectViewAdapter;
import com.github.md.web.ui.OptionsKit;
import com.github.md.web.ui.UIManager;
import com.google.common.collect.Lists;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 从组件视角触发提供的访问方法,主要是对配置信息的操作
 *
 * <p> @Date : 2019/12/26 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@RestController
@RequestMapping("/component")
public class ComponentController extends ControllerAdapter {

    /**
     * @return
     * @deprecated 逐步使用 {@link #metaByIc()} 替代
     */
    @Deprecated
    @MetaAccess(value = Type.API_WITH_META_OBJECT)
    @GetMapping("meta")
    public Ret meta() {
        QueryHelper queryHelper = queryHelper();
        String objectCode = queryHelper.getObjectCode();
        String compCode = queryHelper.getComponentCode();

        IMetaObject metaObject = metaService().findByCode(objectCode);
        MetaObjectViewAdapter metaObjectViewAdapter = UIManager.getView(metaObject, ComponentType.V(compCode));

        return Ret.ok("data", metaObjectViewAdapter.getComponent().toKv());
    }

    @MetaAccess(Type.API_WITH_META_INSTANCE)
    @GetMapping("meta/ic")
    public Ret metaByIc() {
        String ic = queryHelper().getInstanceCode();
        AssertUtil.isTrue(StrKit.notBlank(ic), "实例编码(ic)未指定");

        MetaObjectViewAdapter metaObjectViewAdapter = UIManager.getView(ic);
        return Ret.ok("data", metaObjectViewAdapter.getComponent().toKv());
    }

    /**
     * 获取指定元对象+容器下的实例配置列表
     */
    @MetaAccess(value = Type.API_WITH_META_OBJECT)
    @GetMapping("contact")
    public Ret contact() {
        QueryHelper queryHelper = queryHelper();
        String componentCode = queryHelper.getComponentCode();
        String objectCode = queryHelper.getObjectCode();
        boolean kv = parameterHelper().getBoolean("kv", false);
        List<String> result = componentService().loadInstanceCodeByObjectCode(objectCode, ComponentType.V(componentCode));
        if (kv) {
            return Ret.ok("data", OptionsKit.transKeyValue(result.toArray(new String[0])));
        }
        return Ret.ok("data", result);
    }

    /**
     * 获取指定实例编码的简短信息。元对象和容器组件编码
     *
     * @return
     */
    @GetMapping(value = "instance/brief")
    public Ret componentInstanceBrief() {
        QueryHelper queryHelper = queryHelper();
        String instanceCode = queryHelper.getInstanceCode();
        AssertUtil.isTrue(StrKit.notBlank(instanceCode), "实例编码不能为空");

        Record record = componentService().getComponentInstanceBrief(instanceCode);
        return Ret.ok("data", record);
    }

    @GetMapping("list")
    public Ret list() {
        Boolean view;
        try {
            view = parameterHelper().getBoolean("view");
        } catch (Exception e) {
            view = null;
        }
        List<Record> components = componentService().loadComponents();
        List<Kv> results = Lists.newArrayList();

        Boolean finalView = view;
        components.stream().filter(c -> {
            String code = c.getStr("code");
            if (finalView == null) {
                return true;
            } else {
                return finalView == ComponentType.V(code).isView();
            }
        }).forEach(r -> {
            results.add(Kv.create().set("key", r.getStr("cn")).set("value", r.getStr("en")));
        });
        return Ret.ok("data", results);
    }

    /**
     * TODO 自动计算入口。应当可以支持 根据元字段 + 字段组件类型 来自动计算。实例配置界面切换字段组件类型时，触发该自动的重新自动计算。另外应当支持ui实例配置界面，先为每个字段
     * 选定组件，然后一键自动计算。这样得到的自动计算配置会更精确。而且，就无需前端去和组件的默认配置做一个 $merge 了!
     * 获取实例配置,2种方式
     * 1. objectCode + componentCode
     * 2. instanceCode
     */
    @MetaAccess(value = Type.API_WITH_META_OBJECT)
    @GetMapping("load")
    public Ret load() {
        QueryHelper queryHelper = queryHelper();
        String instanceCode = queryHelper.getInstanceCode();
        String compCode = queryHelper.getComponentCode();

        if (StrKit.notBlank(instanceCode)) { // 有instanceCode 则获取UI实例配置
            if (componentService().hasObjectConfig(instanceCode)) {
                return Ret.ok("data", componentService().loadObjectConfig(instanceCode));
            } else {
                return Ret.fail("msg", "请先为此实例编码生成UI配置!");
            }
        } else if (StrKit.notBlank(compCode)) { // 加载组件全局配置
            return Ret.ok("data", Kv.by(compCode, componentService().loadDefault(compCode).getStr("config")));
        } else {
            return Ret.fail("msg", "请指定实例编码或组件编码");
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

    /**
     * 一键自动计算
     */
    @MetaAccess(value = Type.API_WITH_META_OBJECT)
    @PostMapping("import-auto-computed")
    public Ret oneKeyAutoComputed() {
        QueryHelper queryHelper = queryHelper();
        String objectCode = queryHelper.getObjectCode();
        String compCodes = parameterHelper().getPara("componentCodes");
        String instanceCodes = parameterHelper().getPara("instanceCodes");

        String[] compCodeArr = compCodes.split(",");
        String[] instanceCodeArr = instanceCodes.split(",");
        AssertUtil.isTrue(compCodeArr.length == instanceCodeArr.length, "组件和实例编码长度不匹配");

        Db.tx(() -> {
            for (int i = 0; i < instanceCodeArr.length; i++) {
                String instanceCode = instanceCodeArr[i];
                String compCode = compCodeArr[i];

                AssertUtil.isTrue(StrKit.notBlank(objectCode, compCode, instanceCode), "参数错误");
                if (componentService().hasObjectConfig(instanceCode)) {
                    throw new ComponentException("默认的instanceCode:%s已经存在!", instanceCode);
                }

                IMetaObject metaObject = metaService().findByCode(objectCode);
                MetaObjectViewAdapter metaObjectViewAdapter = UIManager.getSmartAutoView(metaObject, ComponentType.V(compCode));
                Kv config = metaObjectViewAdapter.getInstanceConfig();
                config.set(objectCode, JSONObject.toJSONString(config.get(objectCode)));
                config.set("fieldsMap", JSONObject.toJSONString(config.get("fieldsMap")));

                // 保存配置
                Component component = ViewFactory.createEmptyViewComponent(compCode);

                ComponentInstanceConfig componentInstanceConfig = ComponentInstanceConfig.New(config,
                        metaObject.code(),
                        instanceCode,
                        "系统智能计算",
                        component.componentType());
                componentService().newObjectConfig(component, metaObject, componentInstanceConfig);
            }

            return true;
        });

        return Ret.ok();
    }

    @MetaAccess(value = Type.API_WITH_META_OBJECT)
    @PostMapping("doUpdate")
    public Ret doUpdate() {
        /**
         * object Code
         * component Type
         */
        QueryHelper queryHelper = queryHelper();
        String instanceCode = queryHelper.getInstanceCode();
        String instanceName = queryHelper.getInstanceName();
        Kv config = parameterHelper().getKv();

        String compCode = queryHelper.getComponentCode();

        // 全面拥抱instanceCode，那么组件实例的更新只需要入参instanceCode即可！同样，InstanceConfEdit也需要调整
        if (StrKit.notBlank(instanceCode)) {
            ComponentInstanceConfig oldComponentInstanceConfig = ServiceManager.componentService().loadObjectConfig(instanceCode);
            IMetaObject metaObject = metaService().findByCode(oldComponentInstanceConfig.getObjectCode());
            Component component = AbstractComponent.newInstance(oldComponentInstanceConfig.getContainerType().getCode());

            ComponentInstanceConfig newComponentInstanceConfig = ComponentInstanceConfig.New(config,
                    metaObject.code(),
                    instanceCode,
                    instanceName,
                    component.componentType());
            componentService().updateObjectConfig(component, metaObject, newComponentInstanceConfig);
            return Ret.ok();
        } else if (StrKit.notBlank(compCode)) {
            componentService().updateDefault(compCode, UtilKit.getKv(config.getStr(compCode)));
            return Ret.ok();
        } else {
            return Ret.fail("msg", "参数不正确");
        }
    }

    @MetaAccess(value = Type.API_WITH_META_OBJECT)
    @GetMapping("delete")
    public Ret delete() {
        QueryHelper queryHelper = queryHelper();
        String instanceCode = queryHelper.getInstanceCode();
        AssertUtil.isTrue(StrKit.notBlank(instanceCode), new WebException("请指定要删除的实例编码"));
        componentService().deleteObjectConfig(instanceCode);
        return Ret.ok("msg", "UI实例删除成功, 实例编码:" + instanceCode);
    }
}
