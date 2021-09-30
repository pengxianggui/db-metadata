package com.github.md.web.controller;

import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.aop.QueryInvocation;
import com.github.md.web.kit.SqlParaExt;
import com.github.md.web.query.QueryHelper;
import lombok.Data;

import java.util.Collection;

/**
 * TableView 场景下拦截器中用的上下文
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
