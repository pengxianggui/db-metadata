package com.hthjsj.web.controller;

import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.component.TableView;
import com.hthjsj.web.component.form.DropDown;
import com.hthjsj.web.component.form.FormView;
import com.hthjsj.web.component.form.InputField;
import com.jfinal.aop.Aop;
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
        IMetaObject metaObject = Aop.get(DbMetaService.class).findByCode(metaObjectCode);
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

    /**
     * mock metas 数据
     * Fixme
     */
    public void fields() {
        MetaObject metaObject = (MetaObject) Aop.get(DbMetaService.class).findByCode("meta_field");
        TableView tableView = new TableView("meta_fields_table", "元字段").setMetaObject(metaObject).dataUrl("/table/meta_field");
        renderJson(Ret.ok("data", tableView.toKv()));
    }

    @Override
    public void doAdd() {
        String param = getPara("schemaName");


        renderJson(Ret.ok("data", 1));
    }
}
