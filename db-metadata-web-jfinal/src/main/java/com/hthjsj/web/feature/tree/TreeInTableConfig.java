package com.hthjsj.web.feature.tree;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hthjsj.web.feature.FeatureConfig;
import com.hthjsj.web.kit.tree.TreeConfig;
import com.jfinal.kit.Kv;
import lombok.Data;

import java.util.List;

/**
 * @author pengxg
 * @date
 */
@Data
public class TreeInTableConfig extends FeatureConfig {

    private String objectCode;

    private TreeConfig treeConfig;

    @Override
    public List<String> metaObjects() {
        return Lists.newArrayList(this.objectCode);
    }

    @Override
    public Kv execute() {
        return this;
    }

    public TreeConfig getTreeConfig() {
        if (treeConfig == null) {
            treeConfig = JSON.parseObject(toJson(), TreeConfig.class);
        }
        return treeConfig;
    }

    public String getIdKey() {
        return getTreeConfig().getIdKey();
    }

    public String getPidKey() {
        return getTreeConfig().getPidKey();
    }

    public String getRootIdentify() {
        return getTreeConfig().getRootIdentify();
    }

    public String getLabel() {
        return getTreeConfig().getLabel();
    }

    public boolean isSync() {
        return getTreeConfig().isSync();
    }

    public String getObjectCode() {
        return getTreeConfig().getObjectCode();
    }
}
