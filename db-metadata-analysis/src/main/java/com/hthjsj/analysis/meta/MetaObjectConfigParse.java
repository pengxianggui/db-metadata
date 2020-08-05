package com.hthjsj.analysis.meta;

import com.alibaba.fastjson.JSON;
import com.hthjsj.analysis.meta.aop.*;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

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

    public String[] interceptors() {
        String ss = getStr("bizInterceptor");
        if (StrKit.isBlank(ss)) {
            return new String[0];
        } else {
            if (ss.contains(",")) {
                return ss.split(",");
            } else {
                return new String[] { ss };
            }
        }
    }

    private <T extends IPointCut> T interceptor(String interceptorStr) {
        if (!StrKit.notBlank(interceptorStr)) {
            return (T) EMPTY_POINT_CUT;
        }

        T clazz = null;
        try {
            clazz = (T) Class.forName(interceptorStr).newInstance();
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
    public DeletePointCut[] deletePointCut() {
        List<IPointCut> iPointCuts = new ArrayList<>();
        String[] interceptors = interceptors();
        for (String i : interceptors) {
            Object o = interceptor(i);
            if (o instanceof DeletePointCut) {
                iPointCuts.add((DeletePointCut) o);
            }
        }
        return iPointCuts.toArray(new DeletePointCut[iPointCuts.size()]);
    }

    public AddPointCut[] addPointCut() {
        List<IPointCut> iPointCuts = new ArrayList<>();
        String[] interceptors = interceptors();
        for (String i : interceptors) {
            Object o = interceptor(i);
            if (o instanceof AddPointCut) {
                iPointCuts.add((AddPointCut) o);
            }
        }
        return iPointCuts.toArray(new AddPointCut[iPointCuts.size()]);
    }

    public UpdatePointCut[] updatePointCut() {
        List<IPointCut> iPointCuts = new ArrayList<>();
        String[] interceptors = interceptors();
        for (String i : interceptors) {
            Object o = interceptor(i);
            if (o instanceof UpdatePointCut) {
                iPointCuts.add((UpdatePointCut) o);
            }
        }
        return iPointCuts.toArray(new UpdatePointCut[iPointCuts.size()]);
    }

    public ViewPointCut[] viewPointCut() {
        List<IPointCut> iPointCuts = new ArrayList<>();
        String[] interceptors = interceptors();
        for (String i : interceptors) {
            Object o = interceptor(i);
            if (o instanceof ViewPointCut) {
                iPointCuts.add((ViewPointCut) o);
            }
        }
        return iPointCuts.toArray(new ViewPointCut[iPointCuts.size()]);
    }

    public boolean isTreeStructure() {
        if (StrKit.notBlank(getStr("structure"))) {
            return Boolean.valueOf(getStr("structure"));
        }
        return false;
    }

    /**
     * TODO 前端直传,但是使用com.hthjsj.web.kit.tree.TreeConfig数据结构
     *
     * @return
     *
     * @see com.hthjsj.web.kit.tree.TreeConfig
     */
    public String treeConfig() {
        if (StrKit.notBlank(getStr("structureConfig"))) {
            return getStr("structureConfig");
        }
        return "";
    }
}
