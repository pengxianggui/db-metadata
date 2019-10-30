package com.hthjsj.web.controller;

import com.google.common.base.Preconditions;
import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.component.TableView;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.component.form.DropDown;
import com.hthjsj.web.component.form.FormView;
import com.hthjsj.web.component.form.InputField;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.kit.Ret;
import lombok.extern.slf4j.Slf4j;

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
        IMetaObject metaObject = ServiceManager.dbMetaService().findByCode(metaObjectCode);
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
        formView.getFields().add(new DropDown("schemaName", "数据源").dataUrl("/db/index"));
        formView.getFields().add(new DropDown("tableName", "数据表名").dataUrl("/db/tables?schemaName={{schemaName}}").dependency("schemaName"));
        formView.getFields().add(new InputField("objectName", "元对象名称"));
        formView.getFields().add(new InputField("objectCode", "元对象编码"));
        renderJson(Ret.ok("data", formView.toKv()));
    }

    public void objs() {
        MetaObject metaObject = (MetaObject) ServiceManager.dbMetaService().findByCode("meta_object");
        TableView tableView = ViewFactory.createTableView("meta_object_table", "元对象", metaObject);
        tableView.dataUrl("/table/list/meta_object");
        renderJson(Ret.ok("data", tableView.toKv()));
    }

    /**
     * mock metas 数据
     * Fixme
     */
    public void fields() {
        MetaObject metaObject = (MetaObject) ServiceManager.dbMetaService().findByCode("meta_field");
        TableView tableView = ViewFactory.createTableView("meta_fields_table", "元字段", metaObject);
        renderJson(Ret.ok("data", tableView.toKv()));
    }

    @Override
    public void doAdd() {
        String schemaName = getPara("schemaName");
        String tableName = getPara("tableName");
        String objectName = getPara("objectName");
        String objectCode = getPara("objectCode");
        DbMetaService dbMetaService = ServiceManager.dbMetaService();
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
        DbMetaService dbMetaService = ServiceManager.dbMetaService();
        MetaObject metaObject = (MetaObject) dbMetaService.findByCode(objectCode);
        Preconditions.checkArgument(metaObject.isSystem(), "该对象属于系统元对象,不能删除");
        renderJson(dbMetaService.deleteMetaObject(metaObject.code()) ? Ret.ok() : Ret.fail());
    }
}
