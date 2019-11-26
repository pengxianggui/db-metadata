package com.hthjsj.analysis.meta;

import com.hthjsj.analysis.meta.aop.PointCut;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> @Date : 2019/11/14 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class MetaObjectConfigParse extends MetaConfigFactory.MetaObjectConfig {

    public MetaObjectConfigParse(String config, String objectCode) {
        super(config, objectCode);
    }

    public MetaObjectConfigParse(String objectCode) {
        super(objectCode);
    }

    public boolean isUUIDPrimary() {
        return Boolean.parseBoolean(getStr("isUUIDPrimary"));
    }

    public boolean hasBizInterceptor() {
        return StrKit.notBlank(getStr("bizInterceptor"));
    }

    public <T> T interceptor() {
        T clazz = null;
        try {
            clazz = (T) Class.forName(getStr("bizInterceptor")).newInstance();
            return clazz;
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        } catch (InstantiationException e) {
            log.error(e.getMessage(), e);
        } catch (NullPointerException e) {
            log.error("元对象 {},未配置拦截器.", moduleCode());
        }
        log.warn("元对象{},使用默认拦截器{}", moduleCode(), PointCut.class);
        return (T) new PointCut();
    }
}
