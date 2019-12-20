package com.hthjsj.web.feature.single;

import com.google.common.collect.Lists;
import com.hthjsj.analysis.meta.MetaData;
import com.hthjsj.web.feature.Feature;
import com.jfinal.kit.Kv;
import lombok.Data;

import java.util.List;

/**
 * @author pengxg
 * @date
 */
@Data
public class SingleGridConfig extends MetaData implements Feature {

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
