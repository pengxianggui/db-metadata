package com.hthjsj.web.feature;

import com.hthjsj.analysis.meta.MetaData;
import com.hthjsj.web.feature.ms.MasterSlaveConfig;
import com.hthjsj.web.feature.single.SingleGridConfig;
import com.hthjsj.web.feature.tree.TreeAndTableConfig;
import com.hthjsj.web.feature.tree.TreeInTableConfig;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;

/**
 * 此类修改后,需前端FeatureAdd.vue 同步变更
 * <p> @Date : 2019/12/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public enum FeatureType {
    MasterSlaveGrid("主子表", "MasterSlaveGrid", MasterSlaveConfig.class),
    SingleGrid("单表", "SingleGrid", SingleGridConfig.class),
    TreeInTable("树型表", "TreeInTable", TreeInTableConfig.class),
    TreeAndTable("树和表", "TreeAndTable", TreeAndTableConfig.class),
    UNKNOWN("未知的功能模板", "unknown", Kv.class);

    String name;

    String code;

    Class<? extends MetaData> configEntity;

    FeatureType(String name, String code, Class configEntity) {
        this.name = name;
        this.code = code;
        this.configEntity = configEntity;
    }

    public static FeatureType V(String s) {
        if (StrKit.isBlank(s))
            return UNKNOWN;
        for (FeatureType t : FeatureType.values()) {
            if (s.equalsIgnoreCase(t.code.toLowerCase()) || s.equalsIgnoreCase(t.name.toLowerCase())) {
                return t;
            }
        }
        return UNKNOWN;
    }
}