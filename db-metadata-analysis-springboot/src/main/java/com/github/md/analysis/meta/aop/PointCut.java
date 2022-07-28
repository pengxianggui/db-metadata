package com.github.md.analysis.meta.aop;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> @Date : 2019/11/25 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class PointCut implements AddPointCut, UpdatePointCut, DeletePointCut, ViewPointCut, TableQueryPointCut {

    @Override
    public boolean addBefore(FormInvocation invocation) {
        log.error("[PointCut] default operation : this is addBefore!");
        return false;
    }

    @Override
    public boolean addAfter(FormInvocation invocation) {
        log.error("[PointCut] default operation :this is addAfter!");
        return false;
    }

    @Override
    public boolean deleteBefore(DeleteInvocation invocation) {
        log.error("[PointCut] default operation : this is deleteBefore!");
        return false;
    }

    @Override
    public boolean deleteAfter(DeleteInvocation invocation) {
        log.error("[PointCut] default operation : this is deleteAfter!");
        return false;
    }

    @Override
    public boolean updateBefore(FormInvocation invocation) {
        log.error("[PointCut] default operation : this is updateBefore!");
        return false;
    }

    @Override
    public boolean updateAfter(FormInvocation invocation) {
        log.error("[PointCut] default operation : this is updateAfter!");
        return false;
    }

    @Override
    public boolean viewBefore(AopInvocation invocation) {
        log.error("[PointCut] default operation : this is viewBefore!");
        return false;
    }

    @Override
    public boolean viewAfter(AopInvocation invocation) {
        log.error("[PointCut] default operation : this is viewAfter!");
        return false;
    }

    @Override
    public boolean prevent() {
        return false;
    }

    @Override
    public Page<Record> getResult(QueryInvocation queryInvocation) {
        return null;
    }
}
