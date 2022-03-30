package com.github.md.analysis.meta.aop;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 期望的能力:
 * 1. 一次注册,全局生效
 * 2. 拦截器之间的数据传递
 * 3. 调用过程中 中间的拦截器出现异常,需要有反馈机制通知同组其他切面
 * </pre>
 * TODO 2.3 应当支持某个元对象按需排除排除全局过滤器, 全局过滤器对特定元对象是否启用，应当设置到元对象配置中。
 *
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

    public static void registerGlobalPointCut(IPointCut pointCut) {
        if (pointCut != null && pointCut instanceof AddPointCut) {
            addPointCuts.add(pointCut);
        }
        if (pointCut != null && pointCut instanceof UpdatePointCut) {
            updatePointCuts.add(pointCut);
        }
        if (pointCut != null && pointCut instanceof DeletePointCut) {
            deletePointCuts.add(pointCut);
        }
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

        for (int i = 0; i < pointCuts.length; i++) {
            IPointCut addPointCut = pointCuts[i];
            if (addPointCut instanceof AddPointCut) {
                boolean s = ((AddPointCut) addPointCut).addBefore(invocation);
                if (s) {
                    continue;
                } else {
                    log.info("拦截器 {} 执行中断,调用链总长:{} ,当前:{}", addPointCut.getClass().getSimpleName(), pointCuts.length, i);
                    break;
                }
            }
        }
    }

    public static void addAfter(IPointCut[] pointCuts, AopInvocation invocation) {
        for (IPointCut addPointCut : addPointCuts) {
            if (addPointCut instanceof AddPointCut) {
                ((AddPointCut) addPointCut).addAfter(invocation);
            }
        }

        for (int i = 0; i < pointCuts.length; i++) {
            IPointCut addPointCut = pointCuts[i];
            if (addPointCut instanceof AddPointCut) {
                boolean s = ((AddPointCut) addPointCut).addAfter(invocation);
                if (s) {
                    continue;
                } else {
                    log.info("拦截器 {} 执行中断,调用链总长:{} ,当前:{}", addPointCut.getClass().getSimpleName(), pointCuts.length, i);
                    break;
                }
            }
        }
    }

    public static void updateBefore(IPointCut[] pointCuts, AopInvocation invocation) {
        for (IPointCut updatePointCut : updatePointCuts) {
            if (updatePointCut instanceof UpdatePointCut) {
                ((UpdatePointCut) updatePointCut).updateBefore(invocation);
            }
        }

        for (int i = 0; i < pointCuts.length; i++) {
            IPointCut updatePointCut = pointCuts[i];
            if (updatePointCut instanceof UpdatePointCut) {
                boolean s = ((UpdatePointCut) updatePointCut).updateBefore(invocation);
                if (s) {
                    continue;
                } else {
                    log.info("拦截器 {} 执行中断,调用链总长:{} ,当前:{}", updatePointCut.getClass().getSimpleName(), pointCuts.length, i);
                    break;
                }
            }
        }
    }

    public static void updateAfter(IPointCut[] pointCuts, AopInvocation invocation) {
        for (IPointCut updatePointCut : updatePointCuts) {
            if (updatePointCut instanceof UpdatePointCut) {
                ((UpdatePointCut) updatePointCut).updateAfter(invocation);
            }
        }

        for (int i = 0; i < pointCuts.length; i++) {
            IPointCut updatePointCut = pointCuts[i];
            if (updatePointCut instanceof UpdatePointCut) {
                boolean s = ((UpdatePointCut) updatePointCut).updateAfter(invocation);
                if (s) {
                    continue;
                } else {
                    log.info("拦截器 {} 执行中断,调用链总长:{} ,当前:{}", updatePointCut.getClass().getSimpleName(), pointCuts.length, i);
                    break;
                }
            }
        }
    }

    public static void deleteBefore(IPointCut[] pointCuts, AopInvocation invocation) {
        for (IPointCut deletePointCut : deletePointCuts) {
            if (deletePointCut instanceof DeletePointCut) {
                ((DeletePointCut) deletePointCut).deleteBefore(invocation);
            }
        }

        for (int i = 0; i < pointCuts.length; i++) {
            IPointCut deletePointCut = pointCuts[i];
            if (deletePointCut instanceof DeletePointCut) {
                boolean s = ((DeletePointCut) deletePointCut).deleteBefore(invocation);
                if (s) {
                    continue;
                } else {
                    log.info("拦截器 {} 执行中断,调用链总长:{} ,当前:{}", deletePointCut.getClass().getSimpleName(), pointCuts.length, i);
                    break;
                }
            }
        }
    }

    public static void deleteAfter(IPointCut[] pointCuts, AopInvocation invocation) {
        for (IPointCut deletePointCut : deletePointCuts) {
            if (deletePointCut instanceof DeletePointCut) {
                ((DeletePointCut) deletePointCut).deleteAfter(invocation);
            }
        }

        for (int i = 0; i < pointCuts.length; i++) {
            IPointCut deletePointCut = pointCuts[i];
            if (deletePointCut instanceof DeletePointCut) {
                boolean s = ((DeletePointCut) deletePointCut).deleteAfter(invocation);
                if (s) {
                    continue;
                } else {
                    log.info("拦截器 {} 执行中断,调用链总长:{} ,当前:{}", deletePointCut.getClass().getSimpleName(), pointCuts.length, i);
                    break;
                }
            }
        }
    }

    public static void viewBefore(IPointCut[] pointCuts, AopInvocation invocation) {
        for (IPointCut viewPointCut : viewPointCuts) {
            if (viewPointCut instanceof ViewPointCut) {
                ((ViewPointCut) viewPointCut).viewBefore(invocation);
            }
        }

        for (int i = 0; i < pointCuts.length; i++) {
            IPointCut viewPointCut = pointCuts[i];
            if (viewPointCut instanceof ViewPointCut) {
                boolean s = ((ViewPointCut) viewPointCut).viewBefore(invocation);
                if (s) {
                    continue;
                } else {
                    log.info("拦截器 {} 执行中断,调用链总长:{} ,当前:{}", viewPointCut.getClass().getSimpleName(), pointCuts.length, i);
                    break;
                }
            }
        }
    }

    public static void viewAfter(IPointCut[] pointCuts, AopInvocation invocation) {
        for (IPointCut viewPointCut : viewPointCuts) {
            if (viewPointCut instanceof ViewPointCut) {
                ((ViewPointCut) viewPointCut).viewAfter(invocation);
            }
        }

        for (int i = 0; i < pointCuts.length; i++) {
            IPointCut viewPointCut = pointCuts[i];
            if (viewPointCut instanceof ViewPointCut) {
                boolean s = ((ViewPointCut) viewPointCut).viewAfter(invocation);
                if (s) {
                    continue;
                } else {
                    log.info("拦截器 {} 执行中断,调用链总长:{} ,当前:{}", viewPointCut.getClass().getSimpleName(), pointCuts.length, i);
                    break;
                }
            }
        }
    }
}
