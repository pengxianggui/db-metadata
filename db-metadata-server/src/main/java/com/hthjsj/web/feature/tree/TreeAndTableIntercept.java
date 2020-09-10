package com.hthjsj.web.feature.tree;

import com.hthjsj.analysis.meta.aop.QueryPointCut;
import com.hthjsj.web.feature.FeatureIntercept;
import lombok.Getter;

/**
 * <p> @Date : 2020/9/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Getter
public class TreeAndTableIntercept implements FeatureIntercept {

    FeatureIntercept tree;

    QueryPointCut table;

    public TreeAndTableIntercept(FeatureIntercept tree, QueryPointCut table) {
        this.tree = tree;
        this.table = table;
    }
}
