package com.hthjsj.web.feature.tree;

import com.google.common.collect.Lists;
import com.hthjsj.web.feature.FeatureConfig;
import com.hthjsj.web.kit.tree.TreeConfig;
import com.jfinal.kit.Kv;

import java.util.List;

/**
 * <p> @Date : 2020/1/22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class TreeAndTableConfig extends FeatureConfig {

    private String objectCode;

    @Override
    public List<String> metaObjects() {
        return Lists.newArrayList(this.objectCode);
    }

    @Override
    public Kv execute() {
        return this;
    }
}
