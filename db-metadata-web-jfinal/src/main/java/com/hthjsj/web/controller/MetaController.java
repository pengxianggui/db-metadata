package com.hthjsj.web.controller;

import com.google.common.base.Preconditions;
import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.component.TableView;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.component.form.DropDownBox;
import com.hthjsj.web.component.form.FormView;
import com.hthjsj.web.component.form.TextBox;
import com.hthjsj.web.query.QueryHelper;
import com.hthjsj.web.ui.OptionsKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
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
        MetaObject metaObject = (MetaObject) metaService().findByCode(objectCode);
        TableView tableView = ViewFactory.tableView(metaObject).dataUrl("/table/list/" + metaObject.code());
        renderJson(Ret.ok("data", tableView.toKv()));
    }

    /**
     * 右键菜单使用,直接编辑元对象配置信息
     */
    public void editObject() {
        String objectCode = new QueryHelper(this).getObjectCode();
        Preconditions.checkArgument(StrKit.notBlank(objectCode), "元对象的更新动作,必须指定objectCode.");

        MetaObject metaObject = (MetaObject) metaService().findByCode("meta_object");

        Record data = metaService().findDataOfMetaObjectCode(metaObject.code());

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

        MetaObject metaObject = (MetaObject) metaService().findByCode("meta_field");

        Record data = metaService().findDataOfMetaFieldCode(objectCode, fieldCode);

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
        MetaObject metaObject = (MetaObject) dbMetaService.importFromTable(schemaName, tableName);
        metaObject.name(objectName);
        metaObject.code(objectCode);
        boolean status = dbMetaService.saveMetaObject(metaObject, true);

        renderJson(status ? Ret.ok() : Ret.fail());
    }

    @Override
    public void delete() {
        String objectCode = new QueryHelper(this).getObjectCode();
        DbMetaService dbMetaService = metaService();
        MetaObject metaObject = (MetaObject) dbMetaService.findByCode(objectCode);
        Preconditions.checkArgument(!metaObject.isSystem(), "该对象属于系统元对象,不能删除");

        log.info("删除元对象{}数据", metaObject.code());
        dbMetaService.deleteMetaObject(metaObject.code());

        log.info("删除元对象{}实例配置", metaObject.code());
        componentService().deleteObjectAll(objectCode);
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
