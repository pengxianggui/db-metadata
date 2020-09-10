package com.hthjsj.web.feature;

import com.hthjsj.analysis.meta.MetaData;

/**
 * <pre>
 *     在反序列化时,因为是Map的子类,所以会先以Map接收JSON,在获取如TreeConfig时
 *     通过JSON.parseObject(toJson(), TreeConfig.class);再将实体拿出来
 * </pre>
 * <p> @Date : 2019/12/20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public abstract class FeatureConfig extends MetaData {

    protected String icon() {
        return getStr("icon");
    }

    /**
     * 前端Component默认按照FeatureType.code来路由
     * 如果有单独的需求需要对通过hasRouter & componentName
     *
     * @return
     */
    public boolean hasRouter() {
        return getBoolean("hasRouter") != null && getBoolean("hasRouter");
    }

    public String componentName() {
        return getStr("componentName");
    }
}
