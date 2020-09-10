package com.hthjsj.web.feature.tree;

import com.alibaba.fastjson.JSON;
import com.hthjsj.web.feature.FeatureConfig;
import com.hthjsj.web.kit.tree.TreeConfig;

/**
 * @author pengxg
 * @date 2020/9/10 8:43 下午
 */
public abstract class DefaultTreeFeatureConfig extends FeatureConfig {

    protected TreeConfig treeConfig;

    public TreeConfig getTreeConfig() {
        if (treeConfig == null) {
            treeConfig = JSON.parseObject(toJson(), TreeConfig.class);
        }
        return treeConfig;
    }
}
