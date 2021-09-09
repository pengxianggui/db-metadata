package com.hthjsj.web.config.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.hthjsj.web.kit.HttpKit;
import io.undertow.servlet.util.IteratorEnumeration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * WebMvcConfigurationSupport 中对requestMappingHandlerAdapter有注入动作
 * 是否可以通过改写这部分内容
 *
 * <p> @Date : 2021/9/9 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
//RequestMappingHandlerAdapter
public class JsonParameterToMapHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 1. 根据请求头预判json
         * 2. 分解json,写入paramterMap
         */
        WriteHttpServletRequestWrapper httpServletRequestWrapper = new WriteHttpServletRequestWrapper(request);
        if (request.getMethod().equalsIgnoreCase("post") && request.getContentType().contains("application/json")) {
            Map<String, String> jsonParams = JSON.parseObject(HttpKit.readData(request), new TypeReference<Map<String, String>>() {

            });
            httpServletRequestWrapper.init(jsonParams);
        }
        /**
         * TODO 由于ControllerAdapter中使用RequestContextHolder 来获取的Request,利用此处的时机改写request;
         * 方法并不优雅,非RequestContextHolder方式取到的requets会出现问题;
         * 比如:通过方法签名注入的request;
         */

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(httpServletRequestWrapper));
        return true;
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
