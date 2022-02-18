package com.github.md.analysis.meta.aop;

import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.MetaData;
import com.github.md.analysis.kit.Kv;

import javax.servlet.http.HttpServletRequest;

/**
 * 查询拦截用的Invocation,因为最终Invocation中的数据结构是需要根据具体场景来确定的,所以此处只定义成抽象类
 * <p> @Date : 2020/9/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public abstract class QueryInvocation extends AopInvocation {

    private QueryInvocation(IMetaObject metaObject, MetaData formData, Kv httpParams, HttpServletRequest request) {
        super(metaObject, formData, httpParams, request);
    }

    private QueryInvocation(IMetaObject metaObject, Kv httpParams, HttpServletRequest request) {
        super(metaObject, httpParams, request);
    }

    public QueryInvocation(IMetaObject metaObject) {
        super(metaObject, null, null);
    }

    @Override
    public MetaData getFormData() {
        throw new RuntimeException("错误的使用方式: QueryInvocation 中不能获取 FormData");
    }
}
