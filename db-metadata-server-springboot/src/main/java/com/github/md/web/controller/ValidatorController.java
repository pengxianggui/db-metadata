package com.github.md.web.controller;

import cn.hutool.core.util.URLUtil;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.web.ServiceManager;
import com.github.md.web.query.QueryHelper;
import com.github.md.web.res.Res;
import com.github.md.web.ui.OptionsKit;
import com.github.md.web.ui.SqlAnalysis;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.util.List;

/**
 * <p> @Date : 2019/10/23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@RestController
@RequestMapping("check")
public class ValidatorController extends ControllerAdapter {
    private static List<String> blackList = Lists.newArrayList("/component/options");

    @GetMapping("sql")
    public Res sql() {
        String exeSql = parameterHelper().getPara("sql", "");

        Preconditions.checkArgument(SqlAnalysis.check(exeSql), "无效SQL [%s]", exeSql);
        Preconditions.checkArgument(SqlAnalysis.checkIdCn(exeSql), "SQL必须以id和cn作为返回的结果集 [%s]", exeSql);

        // TODO 2.3 由于sql中可能含有变量 #(), 而变量语法的"#" 恰好被CodeMirror当做了注释, 导致服务端获取的sql始终是有问题的， 需要解决
        return Res.ok();
    }

    @GetMapping("url")
    public Res url() {
        String url = null;
        try {
            url = URLDecoder.decode(parameterHelper().getPara("url", ""), "utf-8");
            Preconditions.checkArgument(StrKit.notBlank(url), "无效的url: [%s]", url);
            Preconditions.checkArgument(!blackList.contains(URLUtil.getPath(url)), "不允许使用此url!可能导致死循环");
            OptionsKit.transKeyValueByUrl(url);
            return Res.ok(null, "校验通过");
        } catch (Exception e) {
            log.error("校验失败。url: {}, errMsg: {}", url, e.getMessage());
            return Res.fail(String.format("校验失败。url: %s, errMsg: %s", url, e.getMessage()));
        }
    }

    /**
     * 验证唯一性
     */
    @GetMapping("one")
    public Res one() {
        QueryHelper queryHelper = queryHelper();
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();
        ParameterHelper parameterHelper = parameterHelper();
        String valueString = parameterHelper.getPara("v", parameterHelper.getPara("val", parameterHelper.getPara("value")));

        IMetaObject metaObject = ServiceManager.metaService().findByCode(objectCode);
        IMetaField metaField = metaObject.getField(fieldCode);
        boolean status = ServiceManager.metaService().isExists(metaObject, metaField, valueString);

        return (status ? Res.ok() : Res.fail("值已存在"));
    }
}
