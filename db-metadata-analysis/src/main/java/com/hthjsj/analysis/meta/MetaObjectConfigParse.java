package com.hthjsj.analysis.meta;

import com.alibaba.fastjson.JSON;
import com.hthjsj.analysis.meta.aop.*;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;

/**
 * 元对象配置解析器
 * <p> @Date : 2019/11/14 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class MetaObjectConfigParse extends MetaData {

    private static final PointCut EMPTY_POINT_CUT = new PointCut();

    MetaObjectConfigParse(Kv config) {
        set(config);
    }

    MetaObjectConfigParse(String config) {
        set(JSON.parseObject(config));
    }

    public boolean isUUIDPrimary() {
        return Boolean.parseBoolean(getStr("isUUIDPrimary"));
    }

    public boolean isNumberSequence() {
        return Boolean.parseBoolean(getStr("isNumberSequence"));
    }

    public boolean isAutoIncrement() {
        return Boolean.parseBoolean(getStr("isAutoIncrement"));
    }

    public void isNumberSequence(boolean v) {
        set("isNumberSequence", v);
    }

    public void isUUIDPrimary(boolean v) {
        set("isUUIDPrimary", v);
    }

    public void isAutoIncrement(boolean v) {
        set("isAutoIncrement", v);
    }

    /**
     * 排序
     */
    public String orderBy() {
        if (StrKit.notBlank(getStr("orderBy"))) {
            return getStr("orderBy").replaceFirst("order by", "");
        }
        return "";
    }

    /**
     * where 条件
     */
    public String where() {
        if (StrKit.notBlank(getStr("where"))) {
            return getStr("where").replaceFirst("where", "");
        }
        return "";
    }

    public <T extends IPointCut> T interceptor() {
        if (!StrKit.notBlank(getStr("bizInterceptor"))) {
            return (T) EMPTY_POINT_CUT;
        }

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
            log.error("元对象 {},未配置拦截器.", getStr("objectCode"));
            log.error(e.getMessage(), e);
        }
        log.warn("元对象{},使用默认拦截器{}", getStr("objectCode"), PointCut.class);
        return (T) EMPTY_POINT_CUT;
    }

    /**
     * 客户端可以按需选择实现add,update,delete,view切入点
     */
    public DeletePointCut deletePointCut() {
        Object o = interceptor();
        if (o instanceof DeletePointCut) {
            return (DeletePointCut) o;
        }
        return EMPTY_POINT_CUT;
    }

    public AddPointCut addPointCut() {
        Object o = interceptor();
        if (o instanceof AddPointCut) {
            return (AddPointCut) o;
        }
        return EMPTY_POINT_CUT;
    }

    public UpdatePointCut updatePointCut() {
        Object o = interceptor();
        if (o instanceof UpdatePointCut) {
            return (UpdatePointCut) o;
        }
        return EMPTY_POINT_CUT;
    }

    public ViewPointCut viewPointCut() {
        Object o = interceptor();
        if (o instanceof ViewPointCut) {
            return (ViewPointCut) o;
        }
        return EMPTY_POINT_CUT;
    }
}
