package com.hthjsj.analysis.meta.aop;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 期望的能力:
 * 1. 一次注册,全局生效
 * 2. 拦截器之间的数据传递
 * </pre>
 * <p> @Date : 2020/6/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class PointCutChain {

    private static final List<IPointCut> addPointCuts = new ArrayList<>(0);

    private static final List<IPointCut> updatePointCuts = new ArrayList<>(0);

    private static final List<IPointCut> deletePointCuts = new ArrayList<>(0);

    private static final List<IPointCut> viewPointCuts = new ArrayList<>(0);

    public static void registerAddPointCut(IPointCut pointCut) {
        if (pointCut != null && pointCut instanceof AddPointCut) {
            addPointCuts.add(pointCut);
        }
    }

    public static void registerUpdatePointCut(IPointCut pointCut) {
        if (pointCut != null && pointCut instanceof UpdatePointCut) {
            updatePointCuts.add(pointCut);
        }
    }

    public static void registerDeletePointCut(IPointCut pointCut) {
        if (pointCut != null && pointCut instanceof DeletePointCut) {
            deletePointCuts.add(pointCut);
        }
    }

    public static void registerViewPointCut(IPointCut pointCut) {
        if (pointCut != null && pointCut instanceof ViewPointCut) {
            viewPointCuts.add(pointCut);
        }
    }

    public static void addBefore(IPointCut[] pointCuts, AopInvocation invocation) {

        for (IPointCut addPointCut : addPointCuts) {
            if (addPointCut instanceof AddPointCut) {
                ((AddPointCut) addPointCut).addBefore(invocation);
            }
        }

        for (IPointCut addPointCut : pointCuts) {
            if (addPointCut instanceof AddPointCut) {
                ((AddPointCut) addPointCut).addBefore(invocation);
            }
        }
    }

    public static void addAfter(IPointCut[] pointCuts, AopInvocation invocation) {
        for (IPointCut addPointCut : addPointCuts) {
            if (addPointCut instanceof AddPointCut) {
                ((AddPointCut) addPointCut).addAfter(invocation);
            }
        }

        for (IPointCut addPointCut : pointCuts) {
            if (addPointCut instanceof AddPointCut) {
                ((AddPointCut) addPointCut).addAfter(invocation);
            }
        }
    }
}
