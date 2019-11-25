package com.hthjsj.analysis.meta.aop;

import com.hthjsj.analysis.meta.MetaData;
import com.hthjsj.analysis.meta.MetaObject;
import com.jfinal.kit.Ret;
import lombok.Data;

/**
 * 业务拦截器用 上下文
 * <p> @Date : 2019/11/25 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Data
public class AopInvocation {

    MetaObject metaObject;

    MetaData metaData;

    Ret ret;

    public AopInvocation(MetaObject metaObject, MetaData metaData) {
        this.metaObject = metaObject;
        this.metaData = metaData;
    }

    public AopInvocation(MetaObject metaObject) {
        this.metaObject = metaObject;
    }
}
