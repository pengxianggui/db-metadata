package com.hthjsj.web.controller;

import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.aop.QueryInvocation;
import com.hthjsj.web.jfinal.SqlParaExt;
import com.hthjsj.web.query.QueryHelper;
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

    private QueryHelper queryHelper;

    private SqlParaExt sqlParaExt;

    private String compileWhere;

    private Collection<IMetaField> filteredFields;

    private boolean prevent;

    public TableQueryInvocation(IMetaObject object, QueryHelper queryHelper) {
        super(object);
        this.queryHelper = queryHelper;
    }
}
