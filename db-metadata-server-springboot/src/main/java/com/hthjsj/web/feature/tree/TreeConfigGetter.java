package com.hthjsj.web.feature.tree;

import com.hthjsj.web.feature.FeatureIntercept;
import com.hthjsj.web.kit.tree.TreeConfig;

/**
 * @author pengxg
 * @date 2020/9/10 8:43 下午
 */
public interface TreeConfigGetter extends FeatureIntercept {

    TreeConfig getTreeConfig();

    FeatureIntercept getTreeFeatureIntercept();
}
