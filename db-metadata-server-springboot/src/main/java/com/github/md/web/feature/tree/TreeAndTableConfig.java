package com.github.md.web.feature.tree;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.md.analysis.component.ComponentType;
import com.github.md.web.feature.FeatureConfig;
import com.github.md.web.kit.tree.TreeConfig;
import com.jfinal.kit.StrKit;
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

    private JSONObject treeInstanceCodes;

    private TableConfig tableConfig;

    private JSONObject tableInstanceCodes;

    private TreeAndTableIntercept intercept;

    @Override
    public TreeConfig getTreeConfig() {
        if (treeConfig == null) {
            JSONObject json = JSON.parseObject(getStr("tree"));
            treeConfig = JSON.parseObject(json.getString("config"), TreeConfig.class);
        }
        return treeConfig;
    }

    @Override
    public TreeAndTableIntercept getTreeFeatureIntercept() {
        if (intercept == null) {
            intercept = new TreeAndTableIntercept() {
            };

            String bizInterceptor = getStr("bizInterceptor");
            if (StrKit.isBlank(bizInterceptor)) {
                log.warn("树+表功能业务拦截器(bizInterceptor)未配置: {}");
                return intercept;
            }

            try {
                intercept = (TreeAndTableIntercept) Thread.currentThread().getContextClassLoader().loadClass(bizInterceptor).newInstance();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                log.error(e.getMessage(), e);
            } catch (NullPointerException e) {
                log.error(e.getMessage(), e);
            }
        }
        return intercept;
    }

    public String getInstanceCodeInTree(ComponentType componentType) {
        if (treeInstanceCodes == null) {
            JSONObject json = JSON.parseObject(getStr("tree"));
            treeInstanceCodes = JSON.parseObject(json.getString("instanceCodes"));
        }
        return treeInstanceCodes.getString(componentType.getCode());
    }

    public String getInstanceCodeInTable(ComponentType componentType) {
        if (tableInstanceCodes == null) {
            JSONObject json = JSON.parseObject(getStr("table"));
            tableInstanceCodes = JSON.parseObject(json.getString("instanceCodes"));
        }
        return tableInstanceCodes.getString(componentType.getCode());
    }

    public TableConfig getTableConfig() {
        if (tableConfig == null) {
            JSONObject json = JSON.parseObject(getStr("table"));
            tableConfig = JSON.parseObject(json.getString("config"), TableConfig.class);
        }
        return tableConfig;
    }

    @Data
    public static class TableConfig {
        private String objectCode;
        private String foreignPrimaryKey;
    }
}
