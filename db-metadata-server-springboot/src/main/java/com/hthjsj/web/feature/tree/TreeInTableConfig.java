package com.hthjsj.web.feature.tree;

import com.alibaba.fastjson.JSON;
import com.hthjsj.web.feature.FeatureConfig;
import com.hthjsj.web.feature.FeatureException;
import com.hthjsj.web.feature.FeatureIntercept;
import com.hthjsj.web.kit.tree.TreeConfig;
import lombok.Data;

/**
 * @author pengxg
 * @date
 */
@Data
public class TreeInTableConfig extends FeatureConfig implements TreeConfigGetter {

    private String objectCode;

    private String instanceCode;

    private TreeConfig treeConfig;

    @Override
    public TreeConfig getTreeConfig() {
        if (treeConfig == null) {
            treeConfig = JSON.parseObject(getStr("table"), TreeConfig.class);
        }
        return treeConfig;
    }

    @Override
    public FeatureIntercept getTreeFeatureIntercept() {
        throw new FeatureException("not finished!");
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
