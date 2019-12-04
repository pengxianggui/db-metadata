package com.hthjsj.analysis.meta.aop;

import lombok.extern.slf4j.Slf4j;

/**
 * <p> @Date : 2019/11/25 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class PointCut implements AddPointCut, UpdatePointCut, DeletePointCut, ViewPointCut {

    @Override
    public boolean addBefore(AopInvocation invocation) {
        log.error("this is addBefore");
        return false;
    }

    @Override
    public boolean addAfter(boolean result, AopInvocation invocation) {
        log.error("this is addAfter");
        return false;
    }

    @Override
    public void deleteBefore(AopInvocation invocation) {
        log.error("this is deleteBefore!");
    }

    @Override
    public void deleteAfter(boolean result, AopInvocation invocation) {
        log.error("this is deleteAfter!");
    }

    @Override
    public boolean updateBefore(AopInvocation invocation) {
        log.error("this is updateBefore!");
        return false;
    }

    @Override
    public boolean updateAfter(boolean result, AopInvocation invocation) {
        log.error("this is updateAfter!");
        return false;
    }

    @Override
    public void viewBefore(AopInvocation invocation) {
        log.error("this is viewBefore!");
    }

    @Override
    public void viewAfter(AopInvocation invocation) {
        log.error("this is viewAfter!");
    }
}