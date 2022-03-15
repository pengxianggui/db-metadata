package com.github.md.web.config.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.github.md.web.kit.HttpKit;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * WebMvcConfigurationSupport 中对requestMappingHandlerAdapter有注入动作
 * 是否可以通过改写这部分内容
 *
 * <p> @Date : 2021/9/9 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
public class JsonParameterToMapHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
         * 1. 根据请求头预判json
         * 2. 分解json,写入parameterMap
         */
        if (request.getMethod().equalsIgnoreCase(HttpMethod.POST.toString().toLowerCase())
                && !Objects.isNull(request.getContentType()) && request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            WritableHttpServletRequestWrapper httpServletRequestWrapper = new WritableHttpServletRequestWrapper(request);
            Map<String, String> jsonParams = JSON.parseObject(HttpKit.readData(request), new TypeReference<Map<String, String>>() {

            });
            if (!CollectionUtils.isEmpty(jsonParams)) {
                httpServletRequestWrapper.init(jsonParams);
            }
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(httpServletRequestWrapper));
        }

        /*
         * TODO 由于ControllerAdapter中使用RequestContextHolder 来获取的Request,利用此处的时机改写request;
         * 方法并不优雅,非RequestContextHolder方式取到的request会出现问题;
         *
         * 比如:
         *  a. 通过方法签名注入到Controller.method 的request; ( 通过RequestMappingHandlerAdapter.handleInternal 方法可以干预)
         *  b. 通过Autowire注入到Controller的request;
         *  c. 等等
         *
         *  这些地方有可能取到RequestFacade对象,而Facade包装的并没有被改写,要多处干预来达到目的;
         */

        return true;
    }

    public static class WritableHttpServletRequestWrapper extends HttpServletRequestWrapper {
        private final String body; // 实现body可重读

        Map<String, String[]> params = Maps.newHashMap();

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request the {@link HttpServletRequest} to be wrapped.
         * @throws IllegalArgumentException if the request is null
         */
        public WritableHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            this.body = parseBody(request); // 转储body
            params.putAll(request.getParameterMap());
        }

        @Override
        public ServletInputStream getInputStream() {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                }

                @Override
                public int read() {
                    return byteArrayInputStream.read();
                }
            };

        }

        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }

        public String getBody() {
            return this.body;
        }

        /**
         * 转储body
         *
         * @param request
         * @throws IOException
         */
        private String parseBody(HttpServletRequest request) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = null;
            InputStream inputStream = null;
            try {
                inputStream = request.getInputStream();
                if (inputStream != null) {
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    char[] charBuffer = new char[128];
                    int bytesRead;
                    while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                        stringBuilder.append(charBuffer, 0, bytesRead);
                    }
                }
            } catch (IOException e) {
                throw e;
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return stringBuilder.toString();
        }

        public void init(Map<String, String> flatParams) {
            for (Map.Entry<String, String> e : flatParams.entrySet()) {
                params.put(e.getKey(), new String[]{e.getValue()});
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

    static class IteratorEnumeration<T> implements Enumeration<T> {

        private final Iterator<T> iterator;

        public IteratorEnumeration(final Iterator<T> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasMoreElements() {
            return iterator.hasNext();
        }

        @Override
        public T nextElement() {
            return iterator.next();
        }
    }
}
