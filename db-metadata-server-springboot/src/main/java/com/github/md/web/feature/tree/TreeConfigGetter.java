package com.github.md.web.feature.tree;

import com.github.md.web.feature.FeatureIntercept;
import com.github.md.web.kit.tree.TreeConfig;

/**
 * @author pengxg
 * @date 2020/9/10 8:43 下午
 */
public interface TreeConfigGetter extends FeatureIntercept {

    TreeConfig getTreeConfig();

    FeatureIntercept getTreeFeatureIntercept();
}
