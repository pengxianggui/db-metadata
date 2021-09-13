package com.github.md.web.feature.tree;

import com.github.md.analysis.meta.aop.IPointCut;
import com.github.md.analysis.meta.aop.PointCut;
import com.github.md.web.feature.FeatureIntercept;

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
