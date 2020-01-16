package com.hthjsj.analysis.meta.aop;

/**
 * <p> @Date : 2019/11/25 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface AddPointCut extends IPointCut {

    /**
     * 两类匹配策略
     * 1. table match
     * 2. meta_object match;
     *
     * @return
     */
//    abstract boolean match();

    boolean addBefore(AopInvocation invocation);

//    @Override
//    public void intercept(Invocation inv) {
//        if (match()) {
//            addBefore();
//            inv.invoke();
//            addAfter();
//        } else {
//            inv.invoke();
//        }
//    }

    boolean addAfter(AopInvocation invocation);
}