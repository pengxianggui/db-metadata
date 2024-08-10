package com.github.md.web.controller;

import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.MetaFieldConfigParse;
import com.github.md.web.res.Res;
import com.github.md.web.ex.WebException;
import com.github.md.web.kit.Dicts;
import com.github.md.web.query.QueryHelper;
import com.github.md.web.query.dynamic.CompileRuntime;
import com.github.md.web.ui.OptionsKit;
import com.jfinal.kit.StrKit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p> @Date : 2019/11/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@RestController
@RequestMapping("component/options")
public class OptionsController extends ControllerAdapter {

    /**
     * URL: /component/options/meta_object?f=field_code_abc
     */
    @GetMapping
    public Res index() {
        QueryHelper queryHelper = queryHelper();
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();
        IMetaField metaField = metaService().findFieldByCode(objectCode, fieldCode);
        MetaFieldConfigParse metaFieldConfigParse = metaField.configParser();

        if (!metaFieldConfigParse.hasTranslation()) {
            throw new WebException("[%s]元对象的[%s]元字段未配置转义逻辑", objectCode, fieldCode);
        }

        // 优先静态数组
        if (metaFieldConfigParse.isOptions()) {
            return buildByOptions(metaField, metaFieldConfigParse);
        }
        // 再字典
        if (metaFieldConfigParse.isDict()) {
            return buildByDict(metaField, metaFieldConfigParse);
        }
        // 再接口数据
        if (metaFieldConfigParse.isUrl()) {
            return buildByUrl(metaField, metaFieldConfigParse);
        }
        // 最后sql
        if (metaFieldConfigParse.isSql()) {
            return buildBySql(metaField, metaFieldConfigParse);
        }

        return Res.ok(null, "请检查原字段的数据源配置是否存在!");
    }

    private Res buildByOptions(IMetaField metaField, MetaFieldConfigParse metaFieldConfigParse) {
        return Res.ok(metaFieldConfigParse.options());
    }

    private Res buildByDict(IMetaField metaField, MetaFieldConfigParse metaFieldConfigParse) {
        return Res.ok(Dicts.me().getKvs(metaFieldConfigParse.getDictName()));
    }

    private Res buildByUrl(IMetaField metaField, MetaFieldConfigParse metaFieldConfigParse) {
        return Res.ok(OptionsKit.transKeyValueByUrl(metaFieldConfigParse.scopeUrl()));
    }

    private Res buildBySql(IMetaField metaField, MetaFieldConfigParse metaFieldConfigParse) {
        IMetaObject metaObject = metaField.getParent();
        String dbConfig = StrKit.defaultIfBlank(metaFieldConfigParse.dbConfig(), metaObject.schemaName());
        String compileSql = new CompileRuntime().compile(metaFieldConfigParse.scopeSql(), getRequest());
        List<Kv> options = OptionsKit.transKeyValueBySql(compileSql, dbConfig);
        return Res.ok(options);
    }

}
