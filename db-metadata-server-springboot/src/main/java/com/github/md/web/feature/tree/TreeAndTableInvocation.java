package com.github.md.web.feature.tree;

import com.github.md.web.controller.TableQueryInvocation;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.web.query.QueryHelper;
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
