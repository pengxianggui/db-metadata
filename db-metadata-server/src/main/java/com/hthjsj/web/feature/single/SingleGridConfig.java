package com.hthjsj.web.feature.single;

import com.google.common.collect.Lists;
import com.hthjsj.web.feature.FeatureConfig;
import com.jfinal.kit.Kv;
import lombok.Data;

import java.util.List;

/**
 * @author pengxg
 * @date
 */
@Data
public class SingleGridConfig extends FeatureConfig {

    private String objectCode;

    private String instanceCode;

    @Override
    public List<String> metaObjects() {
        return Lists.newArrayList(this.objectCode);
    }

    @Override
    public Kv execute() {
        return this;
    }
}