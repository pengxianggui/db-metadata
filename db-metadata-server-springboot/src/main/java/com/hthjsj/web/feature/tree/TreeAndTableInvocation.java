package com.hthjsj.web.feature.tree;

import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.controller.TableQueryInvocation;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.core.Controller;
import lombok.Data;

/**
 * <p> @Date : 2020/9/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Data
public class TreeAndTableInvocation extends TableQueryInvocation {

    private TreeAndTableConfig treeAndTableConfig;

    public TreeAndTableInvocation(IMetaObject metaObject, QueryHelper queryHelper) {
        super(metaObject, queryHelper);
    }
}
