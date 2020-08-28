package com.hthjsj.web.jfinal;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> @Date : 2020/8/28 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class HttpRequestHolder implements Interceptor {

    public static ThreadLocal<HttpServletRequest> holder = new ThreadLocal<>();

    public static HttpServletRequest getRequest() {
        HttpServletRequest request = holder.get();
        if (request == null) {
            throw new RuntimeException("当前上下文中并不包含HttpServletRequest对象,检查HttpRequestHolder是否拦截");
        }
        return request;
    }

    @Override
    public void intercept(Invocation inv) {
        holder.set(inv.getController().getRequest());
        try {
            inv.invoke();
        } finally {
            holder.remove();
        }
    }
}
