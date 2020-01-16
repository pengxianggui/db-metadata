package com.hthjsj.analysis.meta.aop;

import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.MetaData;
import com.jfinal.kit.Kv;
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

    private IMetaObject metaObject;

    private MetaData formData;

    private Ret ret;

    private Kv httpParams;

    private boolean preOperateStatus = false;

    public AopInvocation(IMetaObject metaObject, MetaData formData, Kv httpParams) {
        this.metaObject = metaObject;
        this.formData = formData;
        this.httpParams = httpParams;
    }

    public AopInvocation(IMetaObject metaObject, Kv httpParams) {
        this.metaObject = metaObject;
        this.httpParams = httpParams;
    }
}
