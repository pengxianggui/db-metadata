package com.hthjsj.web.controller;

import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.aop.QueryInvocation;
import com.hthjsj.web.jfinal.SqlParaExt;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.core.Controller;
import lombok.Data;

import java.util.Collection;

/**
 * <p> @Date : 2020/9/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Data
public class TableQueryInvocation extends QueryInvocation {

    QueryHelper queryHelper;

    SqlParaExt sqlParaExt;

    String compileWhere;

    Collection<IMetaField> filteredFields;

    boolean prevent;

    public TableQueryInvocation(IMetaObject metaObject, Controller controller, QueryHelper queryHelper) {
        super(metaObject, controller);
        this.queryHelper = queryHelper;
    }
}
