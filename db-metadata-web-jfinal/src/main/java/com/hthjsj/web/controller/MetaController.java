package com.hthjsj.web.controller;

import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.component.form.DropDown;
import com.hthjsj.web.component.form.FormView;
import com.hthjsj.web.component.form.InputField;
import com.jfinal.aop.Aop;
import com.jfinal.kit.Ret;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/9/24 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
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
    public Ret index() {
        String metaObjectCode = getPara(0, getPara("objectCode"));
        DbMetaService dbMetaService = Aop.get(DbMetaService.class);
        IMetaObject metaObject = dbMetaService.findByCode(metaObjectCode);
        renderJson(Ret.ok("data", metaObject));
        return null;
    }

    /**
     * 新增导入元数据动作
     *
     * @return
     */
    @Override
    public Ret toAdd() {
        FormView formView = FormView.POST("/meta/doAdd", "meta_add");
        formView.getFields().add(new DropDown("schemaName", "数据源", "{}"));
        formView.getFields().add(new InputField("objectName", "元对象名称", "{}"));
        formView.getFields().add(new InputField("objectCode", "元对象编码", "{}"));
        renderJson(Ret.ok("data", formView.renderMeta()));
        return null;
    }

    @Override
    public Ret doAdd() {




        return null;
    }
}
