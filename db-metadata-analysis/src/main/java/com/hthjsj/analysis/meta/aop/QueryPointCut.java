package com.hthjsj.analysis.meta.aop;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;

/**
 * <p> @Date : 2020/9/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface QueryPointCut extends IPointCut {

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
     *
     * @return
     */
    Page<Record> getResult(QueryInvocation queryInvocation);

    /**
     * 配合prevent() 干预查询过程
     *
     * @param queryInvocation
     *
     * @return
     */
    default SqlPara queryWrapper(QueryInvocation queryInvocation) {
        return null;
    }
}
