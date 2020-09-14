package com.hthjsj.web.feature.tree;

import com.alibaba.fastjson.JSON;
import com.hthjsj.web.feature.FeatureConfig;
import com.hthjsj.web.kit.tree.TreeConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> @Date : 2020/1/22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class TreeAndTableConfig extends FeatureConfig implements TreeConfigGetter {

    public static final String RELATE_ID_KEY = "_relate_id";

    private TreeConfig treeConfig;

    private TableConfig tableConfig;

    private TreeAndTableIntercept intercept;

    @Override
    public TreeConfig getTreeConfig() {
        if (treeConfig == null) {
            treeConfig = JSON.parseObject(getStr("tree"), TreeConfig.class);
        }
        return treeConfig;
    }

    @Override
    public TreeAndTableIntercept getTreeFeatureIntercept() {
        if (intercept == null) {

            try {
                intercept = (TreeAndTableIntercept) Class.forName(getStr("bizInterceptor")).newInstance();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                log.error(e.getMessage(), e);
            } catch (NullPointerException e) {
                log.error(e.getMessage(), e);
                intercept = new TreeAndTableIntercept() {

                };
            }
        }
        return intercept;
    }

    public TableConfig getTableConfig() {
        if (tableConfig == null) {
            tableConfig = JSON.parseObject(getStr("table"), TableConfig.class);
        }
        return tableConfig;
    }

    @Data
    public static class TableConfig {

        private String objectCode;

        private String primaryKey;

        private String foreignFieldCode;
    }
}
