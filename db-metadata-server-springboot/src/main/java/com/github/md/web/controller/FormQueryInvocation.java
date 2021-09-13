package com.github.md.web.controller;

import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.aop.QueryInvocation;
import com.jfinal.plugin.activerecord.Record;
import lombok.Getter;
import lombok.Setter;

/**
 * @author pengxg
 * @date 2021/4/9 10:56 上午
 */
public class FormQueryInvocation extends QueryInvocation {
    @Setter
    @Getter
    private Record data;

    public FormQueryInvocation(IMetaObject metaObject) {
        super(metaObject);
    }
}
