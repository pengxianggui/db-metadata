package com.github.md.analysis.meta.aop;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;

/**
 * 目前暂时只支持单表的查询
 * <p> @Date : 2020/9/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface TableQueryPointCut extends IPointCut {

    /**
     * 如prevent返回true,则使用GetResult作为查询请求的返回结果;
     *
     * @return
     */
    default boolean prevent() {
        return false;
    }

    /**
     * 配合prevent() 干预查询返回结果
     *
     * @param queryInvocation
     * @return
     */
    default Page<Record> getResult(QueryInvocation queryInvocation) {
        return new Page<>();
    }

    /**
     * 列表查询前干预查询参数。
     *
     * @param queryInvocation
     */
    default boolean queryBefore(QueryInvocation queryInvocation) { // TODO 这里应当是TableQueryInvocation, 但是由于包依赖关系，只能由实现类去强转了
        return true;
    }

    /**
     * 列表查询后干预返回结果
     *
     * @param queryInvocation
     */
    default boolean queryAfter(QueryInvocation queryInvocation) {
        return true;
    }

    /**
     * 配合prevent() 干预查询过程
     *
     * @param queryInvocation
     * @return
     */
    default SqlPara queryWrapper(QueryInvocation queryInvocation) {
        return null;
    }
}
