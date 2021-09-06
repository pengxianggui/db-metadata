package com.hthjsj.web.feature.tree;

import com.hthjsj.analysis.meta.aop.IPointCut;
import com.hthjsj.analysis.meta.aop.PointCut;
import com.hthjsj.web.feature.FeatureIntercept;

/**
 * <p> @Date : 2020/9/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface TreeAndTableIntercept extends FeatureIntercept {

    default IPointCut treeIntercept() {
        return new PointCut();
    }

    default IPointCut tableIntercept() {
        return new PointCut();
    }
}
