package com.github.md.analysis.meta.aop;

import com.alibaba.fastjson.util.TypeUtils;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.kit.Ret;
import com.github.md.analysis.meta.IMetaObject;
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

    /**
     * pointcut作用的元对象
     */
    private IMetaObject metaObject;

    /**
     * @since 2.4 移除ret，请使用contextParams替代
     */
    @Deprecated
    private Ret ret = new Ret().setOk();

    /**
     * 上下文参数。借助此参数可传递一些数据
     */
    private ContextParam contextParams = new ContextParam();

    private HttpServletRequest request;

    /**
     * http请求参数
     */
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

    public void addContextParam(String key, Object value) {
        contextParams.set(key, value);
    }

    public <T> T getContextParam(String key, Class<T> clazz) {
        Object obj = contextParams.get(key);
        if (obj == null) {
            return null;
        }
        return TypeUtils.castToJavaBean(obj, clazz);
    }
}
