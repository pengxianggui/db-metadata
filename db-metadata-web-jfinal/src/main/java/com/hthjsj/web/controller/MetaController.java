package com.hthjsj.web.controller;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.component.Components;
import com.hthjsj.web.component.TableView;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.component.form.DropDownBox;
import com.hthjsj.web.component.form.FormView;
import com.hthjsj.web.component.form.TextBox;
import com.hthjsj.web.query.QueryHelper;
import com.hthjsj.web.ui.MetaObjectViewAdapter;
import com.hthjsj.web.ui.OptionsKit;
import com.hthjsj.web.ui.UIManager;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
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
public class MetaController extends FrontRestController {

    /**
     * <pre>
     *
     * param :
     *      objectCode
     *
     * </pre>
     */
    @Override
    public void index() {
        String metaObjectCode = getPara(0, getPara("objectCode"));
        IMetaObject metaObject = metaService().findByCode(metaObjectCode);
        renderJson(Ret.ok("data", metaObject));
    }

    /**
     * 新增导入元数据动作
     *
     * @return
     */
    @Override
    public void toAdd() {
        FormView formView = FormView.POST("/meta/doAdd", "meta_add");
        formView.getFields().add(new DropDownBox("schemaName", "数据源").dataUrl("/db/index"));
        formView.getFields().add(new DropDownBox("tableName", "数据表名").dataUrl("/db/tables?schemaName={schemaName}").dependency("schemaName"));
        formView.getFields().add(new TextBox("objectName", "元对象名称"));
        formView.getFields().add(new TextBox("objectCode", "元对象编码"));
        renderJson(Ret.ok("data", formView.toKv()));
    }

    /**
     * mock metas 数据
     * Fixme
     *
     * @deprecated
     */
    public void fields() {
        log.error("接口废弃 -> /table/meta");
        //        Preconditions.checkNotNull(null, "接口废弃 -> /table/meta");
        String objectCode = new QueryHelper(this).getObjectCode("meta_field");
        IMetaObject metaObject = metaService().findByCode(objectCode);
        TableView tableView = ViewFactory.tableView(metaObject).dataUrl("/table/list/" + metaObject.code());
        renderJson(Ret.ok("data", tableView.toKv()));
    }

    /**
     * 右键菜单使用,直接编辑元对象配置信息
     */
    public void editObject() {
        String objectCode = new QueryHelper(this).getObjectCode();
        Preconditions.checkArgument(StrKit.notBlank(objectCode), "元对象的更新动作,必须指定objectCode.");

        IMetaObject metaObject = metaService().findByCode("meta_object");

        Record data = metaService().findObjectRecordByCode(objectCode);

        FormView formView = ViewFactory.formView(metaObject).action("/form/doUpdate").updateForm();
        renderJson(Ret.ok("data", formView.toKv().set("record", data)));
    }

    /**
     * 右键菜单使用,直接编辑元字段配置信息
     * 成功更新后 -> 重新计算配置;
     */
    public void editField() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();
        Preconditions.checkArgument(StrKit.notBlank(objectCode), "元字段的更新动作,必须指定objectCode和fieldCode");

        IMetaObject metaObject = metaService().findByCode("meta_field");

        Record data = metaService().findFieldRecordByCode(objectCode, fieldCode);

        FormView formView = ViewFactory.formView(metaObject).action("/form/doUpdate").updateForm();
        renderJson(Ret.ok("data", formView.toKv().set("record", data)));
    }

    @Override
    public void doAdd() {
        String schemaName = getPara("schemaName");
        String tableName = getPara("tableName");
        String objectName = getPara("objectName");
        String objectCode = getPara("objectCode");
        DbMetaService dbMetaService = metaService();
        Preconditions.checkArgument(dbMetaService.isExists(objectCode), "元对象已存在");
        IMetaObject metaObject = dbMetaService.importFromTable(schemaName, tableName);
        metaObject.name(objectName);
        metaObject.code(objectCode);
        boolean status = dbMetaService.saveMetaObject(metaObject, true);

        renderJson(status ? Ret.ok() : Ret.fail());
    }

    @Override
    public void delete() {
        String objectCodess = new QueryHelper(this).getObjectCode();
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
        renderJson(Ret.ok());
    }

    /**
     * 重新导入元对象
     */
    @Before(Tx.class)
    public void resetImport() {
        /**
         * 1. 重新解析元对象对应的Table
         * 2. 删除原元对象
         *
         * ext:
         * 3. [可选]重新导入元对象
         * 4. [可选]对已经有的配置是否进行重新计算
         */
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        boolean deleteInstanceConfig = getParaToBoolean("deleteInstanceConfig", false);
        boolean autoImport = getParaToBoolean("autoImport", false);
        IMetaObject metaObject = metaService().findByCode(objectCode);
        //1. 删除元对象
        boolean deleteStatus = metaService().deleteMetaObject(metaObject.code());
        log.info("{} 元对象删除 {}", objectCode, deleteStatus);
        //2. 重新导入
        metaService().importFromTable(metaObject.schemaName(), metaObject.tableName());
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

        renderJson(Ret.ok());
    }

    /**
     * 返回某元对象的关联Component 实例
     */
    public void contact() {
        String objectCode = new QueryHelper(this).getObjectCode();
        boolean kv = getBoolean("kv", false);
        List<String> result = componentService().loadTypesByObjectCode(objectCode).stream().map(c -> c.getCode()).collect(Collectors.toList());

        if (kv) {
            renderJson(Ret.ok("data", OptionsKit.transKeyValue(result.toArray(new String[0]))));
            return;
        }

        renderJson(Ret.ok("data", result));
    }
}
