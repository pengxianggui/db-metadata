package com.hthjsj.analysis.meta.aop;

import com.hthjsj.analysis.meta.MetaData;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author konbluesky
 */
@Slf4j
public class PointCutFactory implements Serializable, Cloneable {

    private static final PointCut EMPTY_POINT_CUT = new PointCut();

    private final MetaData metaData;

    public PointCutFactory(MetaData metaData) {
        this.metaData = metaData;
    }

    public String[] interceptors() {
        String ss = metaData.getStr("bizInterceptor");
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

    <T extends IPointCut> T interceptor(String interceptorStr) {
        if (!StrKit.notBlank(interceptorStr)) {
            return (T) EMPTY_POINT_CUT;
        }

        T clazz = null;
        try {
            clazz = (T) Class.forName(interceptorStr).newInstance();
            return clazz;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            log.error(e.getMessage(), e);
        } catch (NullPointerException e) {
            log.error("元对象 {},未配置拦截器.", metaData.getStr("objectCode"));
            log.error(e.getMessage(), e);
        }
        log.warn("元对象{},使用默认拦截器{}", metaData.getStr("objectCode"), PointCut.class);
        return (T) EMPTY_POINT_CUT;
    }

    public QueryPointCut queryPointCut() {
        String[] interceptors = interceptors();
        Object o = null;
        for (String i : interceptors) {
            o = interceptor(i);
            if (o instanceof QueryPointCut) {
                return (QueryPointCut) o;
            }
        }
        return EMPTY_POINT_CUT;
    }

    /**
     * 客户端可以按需选择实现add,update,delete,view切入点
     */
    public DeletePointCut[] deletePointCut() {
        List<IPointCut> iPointCuts = new ArrayList<IPointCut>();
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
        List<IPointCut> iPointCuts = new ArrayList<IPointCut>();
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
        List<IPointCut> iPointCuts = new ArrayList<IPointCut>();
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
        List<IPointCut> iPointCuts = new ArrayList<IPointCut>();
        String[] interceptors = interceptors();
        for (String i : interceptors) {
            Object o = interceptor(i);
            if (o instanceof ViewPointCut) {
                iPointCuts.add((ViewPointCut) o);
            }
        }
        return iPointCuts.toArray(new ViewPointCut[iPointCuts.size()]);
    }
}