package com.github.md.web.controller;

import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.web.ServiceManager;
import com.github.md.web.query.QueryHelper;
import com.github.md.analysis.kit.Ret;
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