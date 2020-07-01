package com.hthjsj.web.jfinal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import io.undertow.servlet.util.IteratorEnumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 前后端分离时,使用json作为数据传输格式后,jfinal的getPara系列方法失效;
 * 此拦截器预处理,将json转为Map,写入getParamerterMap, 不影响已有的getParam方法;
 * <p> @Date : 2019/11/1 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class JsonParamIntercept implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        /**
         * 1. 根据请求头预判json
         * 2. 分解json,写入paramterMap
         */
        WriteHttpServletRequestWrapper request = new WriteHttpServletRequestWrapper(inv.getController().getRequest());
        if (request.getMethod().equalsIgnoreCase("post") && request.getContentType().contains("application/json")) {
            Map<String, String> jsonParams = JSON.parseObject(inv.getController().getRawData(), new TypeReference<Map<String, String>>() {

            });
            request.init(jsonParams);
            inv.getController().setHttpServletRequest(request);
        }
        inv.invoke();
    }

    class WriteHttpServletRequestWrapper extends HttpServletRequestWrapper {

        Map<String, String[]> params = Maps.newHashMap();

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request the {@link HttpServletRequest} to be wrapped.
         *
         * @throws IllegalArgumentException if the request is null
         */
        public WriteHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
            params.putAll(request.getParameterMap());
        }

        public void init(Map<String, String> flatParams) {
            for (Map.Entry<String, String> e : flatParams.entrySet()) {
                params.put(e.getKey(), new String[] { e.getValue() });
            }
        }

        @Override
        public String getParameter(String name) {
            String result = null;
            for (Map.Entry<String, String[]> e : params.entrySet()) {
                if (name.equalsIgnoreCase(e.getKey())) {
                    String[] values = e.getValue();
                    if (values.length == 1) {
                        result = values[0];
                    }
                }
            }
            return result;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return params;
        }

        @Override
        public Enumeration<String> getParameterNames() {
            final Set<String> parameterNames = new HashSet<>(params.keySet());
            return new IteratorEnumeration<>(parameterNames.iterator());
        }

        @Override
        public String[] getParameterValues(String name) {
            return params.get(name);
        }
    }
}
