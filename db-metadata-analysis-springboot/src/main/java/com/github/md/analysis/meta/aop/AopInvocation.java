package com.github.md.analysis.meta.aop;

import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.MetaData;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.kit.Ret;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

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

    private Ret ret = new Ret().setOk();

    private HttpServletRequest request;

    private Kv httpParams;

    /**
     * 用来标记前置操作状态是否完成,如上一个动作是save or update;
     */
    private boolean preOperateStatus = false;

    public AopInvocation(IMetaObject metaObject, Kv httpParams, HttpServletRequest request) {
        this.metaObject = metaObject;
        this.httpParams = httpParams;
        this.request = request;
    }
}
