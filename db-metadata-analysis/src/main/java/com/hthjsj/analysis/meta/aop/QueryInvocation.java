package com.hthjsj.analysis.meta.aop;

import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.MetaData;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;

/**
 * 查询拦截用的Invocation,因为最终Invocation中的数据结构是需要根据具体场景来确定的,所以此处只定义成抽象类
 * <p> @Date : 2020/9/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public abstract class QueryInvocation extends AopInvocation {

    private QueryInvocation(IMetaObject metaObject, MetaData formData, Kv httpParams, Controller controller) {
        super(metaObject, formData, httpParams, controller);
    }

    private QueryInvocation(IMetaObject metaObject, MetaData formData, Kv httpParams) {
        super(metaObject, formData, httpParams);
    }

    private QueryInvocation(IMetaObject metaObject, Kv httpParams) {
        super(metaObject, httpParams);
    }

    public QueryInvocation(IMetaObject metaObject, Controller controller) {
        super(metaObject, null, controller.getKv(), controller);
    }

    @Override
    public MetaData getFormData() {
        throw new RuntimeException("错误的使用方式: QueryInvocation 中不能获取 FormData");
    }
}
