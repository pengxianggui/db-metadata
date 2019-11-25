package com.hthjsj.analysis.meta.aop;

/**
 * <p> @Date : 2019/11/25 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class PointCut implements AddPointCut, UpdatePointCut, DeletePointCut, ViewPointCut {

    @Override
    public boolean addBefore(AopInvocation invocation) {
        return false;
    }

    @Override
    public boolean addAfter(boolean result, AopInvocation invocation) {
        return false;
    }

    @Override
    public void deleteBefore(AopInvocation invocation) {

    }

    @Override
    public void deleteAfter(boolean result, AopInvocation invocation) {

    }

    @Override
    public void updateBefore(AopInvocation invocation) {

    }

    @Override
    public void updateAfter(boolean result, AopInvocation invocation) {

    }

    @Override
    public void viewBefore(AopInvocation invocation) {

    }

    @Override
    public void viewAfter(AopInvocation invocation) {

    }
}