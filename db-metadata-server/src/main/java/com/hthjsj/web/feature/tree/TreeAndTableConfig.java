package com.hthjsj.web.feature.tree;

import com.alibaba.fastjson.JSON;
import com.hthjsj.web.feature.FeatureConfig;
import com.hthjsj.web.kit.tree.TreeConfig;
import lombok.Data;

/**
 * <p> @Date : 2020/1/22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class TreeAndTableConfig extends FeatureConfig {

    private TreeConfig treeConfig;

    private TableConfig tableConfig;

    public TreeConfig getTreeConfig() {
        if (treeConfig == null) {
            treeConfig = JSON.parseObject(getStr("tree"), TreeConfig.class);
        }
        return treeConfig;
    }

    public TableConfig getTableConfig() {
        if (tableConfig == null) {
            tableConfig = JSON.parseObject(getStr("table"), TableConfig.class);
        }
        return tableConfig;
    }

    @Data
    public static class TableConfig {

        String objectCode;

        String primaryKey;

        String foreignFieldCode;
    }
}
