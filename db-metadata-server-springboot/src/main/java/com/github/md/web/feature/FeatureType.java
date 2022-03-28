package com.github.md.web.feature;

import com.github.md.web.feature.tree.TreeAndTableConfig;
import com.github.md.web.feature.tree.TreeInTableConfig;
import com.github.md.analysis.meta.MetaData;
import com.github.md.web.feature.ms.MasterSlaveConfig;
import com.github.md.web.feature.single.SingleGridConfig;
import com.github.md.analysis.kit.Kv;
import com.jfinal.kit.StrKit;
import lombok.Getter;

/**
 * 此类修改后,需前端FeatureAdd.vue 同步变更
 * <p> @Date : 2019/12/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Getter
public enum FeatureType {
    SingleGrid("单表", "SingleGrid", "SingleGridTmpl", SingleGridConfig.class),
    TreeSingleGrid("树型表", "TreeSingleGrid", "TreeSingleGridTmpl", TreeInTableConfig.class),
    MasterSlaveGrid("主子表", "MasterSlaveGrid", "MasterSlaveTableTmpl", MasterSlaveConfig.class),
    TreeTable("树和表", "TreeTable", "TreeTableTmpl", TreeAndTableConfig.class),
    UNKNOWN("未知的功能模板", "unknown", "unknown", Kv.class);

    String name;

    String code;

    String componentName;

    Class<? extends MetaData> configEntity;

    FeatureType(String name, String code, String componentName, Class configEntity) {
        this.name = name;
        this.code = code;
        this.componentName = componentName;
        this.configEntity = configEntity;
    }

    public static FeatureType V(String s) {
        if (StrKit.isBlank(s))
            return UNKNOWN;
        for (FeatureType t : FeatureType.values()) {
            if (s.equalsIgnoreCase(t.code.toLowerCase()) || s.equalsIgnoreCase(t.name.toLowerCase()) || s.equalsIgnoreCase(t.componentName.toLowerCase())) {
                return t;
            }
        }
        return UNKNOWN;
    }
}
