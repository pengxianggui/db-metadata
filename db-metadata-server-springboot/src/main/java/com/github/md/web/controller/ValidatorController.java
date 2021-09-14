package com.github.md.web.controller;

import com.github.md.analysis.kit.Ret;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.web.ServiceManager;
import com.github.md.web.query.QueryHelper;
import com.github.md.web.ui.SqlAnalysis;
import com.google.common.base.Preconditions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> @Date : 2019/10/23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@RestController
@RequestMapping("check")
public class ValidatorController extends ControllerAdapter {

    @GetMapping("sql")
    public Ret sql() {
        String exeSql = parameterHelper().getPara("sql", "");
        Preconditions.checkArgument(SqlAnalysis.check(exeSql), "无效SQL [%s]", exeSql);
        // TODO 由于sql中可能含有变量 #(), 而标量语法的"#" 恰好被CodeMirror当做了注释, 导致服务端获取的sql始终是有问题的。这里先放开校验， 解决这个问题后再打开
        return Ret.ok();
    }

    /**
     * 验证唯一性
     */
    @GetMapping("one")
    public Ret one() {
        QueryHelper queryHelper = queryHelper();
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();
        ParameterHelper parameterHelper = parameterHelper();
        String valueString = parameterHelper.getPara("v", parameterHelper.getPara("val", parameterHelper.getPara("value")));

        IMetaObject metaObject = ServiceManager.metaService().findByCode(objectCode);
        IMetaField metaField = metaObject.getField(fieldCode);
        boolean status = ServiceManager.metaService().isExists(metaObject, metaField, valueString);

        return (status ? Ret.ok() : Ret.fail());
    }
}
