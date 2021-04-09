package com.hthjsj.web.controller;

import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.aop.QueryInvocation;
import com.jfinal.core.Controller;
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

    public FormQueryInvocation(IMetaObject metaObject, Controller controller) {
        super(metaObject, controller);
    }
}
