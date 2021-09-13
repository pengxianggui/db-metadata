package com.github.md.web.controller;

import com.github.md.web.ui.*;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.DbMetaService;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.web.ServiceManager;
import com.github.md.web.component.Components;
import com.github.md.web.component.TableView;
import com.github.md.web.component.ViewFactory;
import com.github.md.web.component.form.DropDownBox;
import com.github.md.web.component.form.FormView;
import com.github.md.web.component.form.TextBox;
import com.github.md.web.kit.UtilKit;
import com.github.md.web.query.QueryHelper;
import com.github.md.analysis.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/9/24 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@RestController
@RequestMapping("meta")
public class MetaController extends ControllerAdapter {

    @GetMapping
    public Ret index() {
        String metaObjectCode = queryHelper().getObjectCode();
        IMetaObject metaObject = metaService().findByCode(metaObjectCode);
        return Ret.ok("data", metaObject);
    }

    /**
     * 新增导入元数据动作
     *
     * @return
     */
    @GetMapping("toAdd")
    public Ret toAdd() {
        FormView formView = FormView.POST("/meta/doAdd", "meta_add");
        formView.getFields().add(new DropDownBox("schemaName", "数据源").dataUrl("/db/index"));
        formView.getFields().add(new DropDownBox("tableName", "数据表名").dataUrl("/db/tables?schemaName={schemaName}").dependency("schemaName"));
        formView.getFields().add(new TextBox("objectName", "元对象名称"));
        formView.getFields().add(new TextBox("objectCode", "元对象编码"));
        return Ret.ok("data", formView.toKv());
    }

    /**
     * mock metas 数据
     * Fixme
     *
     * @deprecated
     */
    @GetMapping("fields")
    public Ret fields() {
        log.error("接口废弃 -> /table/meta");
        //        Preconditions.checkNotNull(null, "接口废弃 -> /table/meta");
        String objectCode = queryHelper().getObjectCode("meta_field");
        IMetaObject metaObject = metaService().findByCode(objectCode);
        TableView tableView = ViewFactory.tableView(metaObject).dataUrl("/table/list?objectCode=" + metaObject.code());
        return Ret.ok("data", tableView.toKv());
    }

    /**
     * 右键菜单使用,直接编辑元对象配置信息
     */
    @GetMapping("editObject")
    public Ret editObject() {
        String objectCode = queryHelper().getObjectCode();
        Preconditions.checkArgument(StrKit.notBlank(objectCode), "元对象的更新动作,必须指定objectCode.");

        IMetaObject metaObject = metaService().findByCode("meta_object");

        Record data = metaService().findObjectRecordByCode(objectCode);

        FormView formView = ViewFactory.formView(metaObject).action("/form/doUpdate").updateForm();
        return Ret.ok("data", formView.toKv().set("record", data));
    }

    /**
     * 右键菜单使用,直接编辑元字段配置信息
     * 成功更新后 -> 重新计算配置;
     */
    @GetMapping("editField")
    public Ret editField() {
        QueryHelper queryHelper = queryHelper();
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();
        Preconditions.checkArgument(StrKit.notBlank(objectCode), "元字段的更新动作,必须指定objectCode和fieldCode");

        IMetaObject metaObject = metaService().findByCode("meta_field");

        Record data = metaService().findFieldRecordByCode(objectCode, fieldCode);

        FormView formView = ViewFactory.formView(metaObject).action("/form/doUpdate").updateForm();
        return Ret.ok("data", formView.toKv().set("record", data));
    }

    @PostMapping("doAdd")
    public Ret doAdd() {
        ParameterHelper parameterHelper = parameterHelper();
        String schemaName = parameterHelper.getPara("schemaName");
        String tableName = parameterHelper.getPara("tableName");
        String objectName = parameterHelper.getPara("objectName");
        String objectCode = parameterHelper.getPara("objectCode");
        DbMetaService dbMetaService = metaService();
        Preconditions.checkArgument(dbMetaService.isExists(objectCode), "元对象已存在");
        IMetaObject metaObject = dbMetaService.importFromTable(schemaName, tableName);
        metaObject.name(objectName);
        metaObject.code(objectCode);
        UtilKit.setCreateUser(metaObject.dataMap());  //set createBy,createTime
        boolean status = dbMetaService.saveMetaObject(metaObject, true);
        return status ? Ret.ok() : Ret.fail();
    }

    @DeleteMapping("delete")
    public Ret delete() {
        String objectCodess = queryHelper().getObjectCode();
        DbMetaService dbMetaService = metaService();
        String[] objectCodes = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(objectCodess).toArray(new String[0]);
        for (String objectCode : objectCodes) {
            IMetaObject metaObject = dbMetaService.findByCode(objectCode);
            Preconditions.checkArgument(!metaObject.isSystem(), "该对象属于系统元对象,不能删除");

            log.info("删除元对象{}数据", metaObject.code());
            dbMetaService.deleteMetaObject(metaObject.code());

            log.info("删除元对象{}实例配置", metaObject.code());
            componentService().deleteObjectAll(objectCode);
        }
        return Ret.ok();
    }

    /**
     * <pre>
     * 增量import新字段
     * 范围:
     *      指定元对象
     *      指定元对象 + 元对象所对应物理表的其他元对象 ( dependency)
     * 参数 :
     *      objectCode: ${objectCode}
     *      scope: vertical | single (default)
     * </pre>
     */
    @GetMapping("incrementImport")
    public Ret incrementImport() {
        String scope = parameterHelper().getPara("scope", "single");
        String objectCode = queryHelper().getObjectCode();
        IMetaObject seedMetaObject = metaService().findByCode(objectCode);

        List<IMetaObject> toBeUpdateObjects = Lists.newArrayList(seedMetaObject);
        //某元对象背后表关联的所有元对象更新
        if ("vertical".equalsIgnoreCase(scope)) {
            toBeUpdateObjects = metaService().seriesOfTable(seedMetaObject.schemaName(), seedMetaObject.tableName());
        }
        //获取新的元对象结构
        IMetaObject newMetaObject = metaService().importFromTable(seedMetaObject.schemaName(), seedMetaObject.tableName());
        Set<String> newFieldNames = newMetaObject.fields().parallelStream().map(m -> m.en()).collect(Collectors.toSet());

        for (IMetaObject oldMetaObject : toBeUpdateObjects) {
            Set<String> oldFieldNames = oldMetaObject.fields().parallelStream().map(m -> m.en()).collect(Collectors.toSet());
            //取差集newFieldNames中存在,oldFieldNames中不存在的字段
            Set<String> incrementFields = Sets.difference(newFieldNames, oldFieldNames);
            if (incrementFields.isEmpty()) {
                log.info("未检测到新字段");
                return Ret.ok().set("msg", "未检测到新字段，不会执行同步操作");
            } else {
                for (String fieldName : incrementFields) {
                    IMetaField metaField = newMetaObject.getField(fieldName);
                    oldMetaObject.addField(metaField);
                    log.info("元对象[{}] 新增[{}]字段", metaField.objectCode(), metaField.fieldCode());
                }
                //清理原metaObject
                metaService().deleteMetaObject(oldMetaObject.code());
                boolean addStatus = metaService().saveMetaObject(oldMetaObject, true);
                log.info("元对象[{}]增量导入[{}]", objectCode, addStatus);

                if (addStatus) {
                    log.info("元对象[{}]增量导入,UI配置{}", objectCode, addStatus);
                    List<ComponentInstanceConfig> existInstance = ServiceManager.componentService().loadInstanceByObjectCode(oldMetaObject.code());
                    for (ComponentInstanceConfig instanceConfig : existInstance) {
                        log.info("载入实例:{} - 类型:{}", instanceConfig.getInstanceCode(), instanceConfig.getContainerType());
                        /* 1. 利用自动计算功能,计算容器 */
                        /* 2. 从容器中获取单个字段配置 */
                        /* 3. 将改配置更新 */
                        MetaObjectViewAdapter metaObjectViewAdapter = UIManager.getSmartAutoView(oldMetaObject, instanceConfig.getContainerType());
                        for (String fieldName : incrementFields) {
                            log.info("增加字段 {} 在 {} 中的配置", fieldName, instanceConfig.getContainerType());
                            MetaFieldViewAdapter metaFieldViewAdapter = metaObjectViewAdapter.getFieldAdapter(fieldName);
                            boolean fs = UIManager.createField(metaFieldViewAdapter, instanceConfig.getInstanceCode(), instanceConfig.getInstanceName());
                            log.info("保存[{}]", fs);
                        }
                    }
                }
            }
        }
        return Ret.ok();
    }

    /**
     * 重新导入元对象
     */
    @Transactional
    @GetMapping("resetImport")
    public Ret resetImport() {
        /**
         * 1. 重新解析元对象对应的Table
         * 2. 删除原元对象
         *
         * ext:
         * 3. [可选]重新导入元对象
         * 4. [可选]对已经有的配置是否进行重新计算
         */
        boolean deleteInstanceConfig = parameterHelper().getParaToBoolean("deleteInstanceConfig", false);
        boolean autoImport = parameterHelper().getParaToBoolean("autoImport", false);

        QueryHelper queryHelper = queryHelper();
        String objectCode = queryHelper.getObjectCode();
        IMetaObject metaObject = metaService().findByCode(objectCode);
        //1. 删除元对象
        boolean deleteStatus = metaService().deleteMetaObject(metaObject.code());
        log.info("{} 元对象删除,元子段:{}个,结果:{}", objectCode, metaObject.fields().size(), deleteStatus);

        //2. 重新导入
        metaObject = metaService().importFromTable(metaObject.schemaName(), metaObject.tableName());
        boolean newInsertStatus = metaService().saveMetaObject(metaObject, true);
        log.info("重新插入{}元对象,元字段:{}个,结果:{}", metaObject.code(), metaObject.fields().size(), newInsertStatus);

        //3. 删除元对象配置
        if (deleteInstanceConfig) {
            log.info("{} 删除前端实例配置", objectCode);
            componentService().deleteObjectAll(metaObject.code());
        }

        if (autoImport) {
            log.info("{} 删除前端实例配置,并准备重新计算前端配置", objectCode);
            componentService().deleteObjectAll(metaObject.code());
            //自动初始化一些控件
            for (ComponentType type : Components.me().getAutoInitComponents()) {
                log.info("计算 {} - {} 配置", metaObject.code(), type.getCode());
                MetaObjectViewAdapter metaObjectIViewAdapter = UIManager.getSmartAutoView(metaObject, type);
                componentService().newObjectConfig(metaObjectIViewAdapter.getComponent(), metaObject, metaObjectIViewAdapter.getInstanceConfig());
            }
        }

        return Ret.ok();
    }

    /**
     * 返回某元对象的关联Component 实例
     */
    @GetMapping("contact")
    public Ret contact() {
        String objectCode = queryHelper().getObjectCode();
        boolean kv = parameterHelper().getBoolean("kv", false);
        List<String> result = componentService().loadTypesByObjectCode(objectCode).stream().map(c -> c.getCode()).collect(Collectors.toList());

        if (kv) {
            return Ret.ok("data", OptionsKit.transKeyValue(result.toArray(new String[0])));
        }
        return Ret.ok("data", result);
    }
}
