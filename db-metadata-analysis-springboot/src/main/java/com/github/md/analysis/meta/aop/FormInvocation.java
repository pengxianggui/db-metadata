package com.github.md.analysis.meta.aop;

import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.MetaData;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pengxg
 * @date 2022/4/1 3:18 下午
 */
public class FormInvocation extends AopInvocation {

    /**
     * 表单数据
     */
    @Getter
    private MetaData formData;

    public FormInvocation(IMetaObject metaObject, Kv httpParams, HttpServletRequest request, MetaData formData) {
        super(metaObject, httpParams, request);
        this.formData = formData;
    }
}
