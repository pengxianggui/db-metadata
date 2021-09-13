package com.github.md.web.config.register;

import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * https://my.oschina.net/u/3101282/blog/3022154
 * <p> @Date : 2021/9/10 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
public class DynamicRegisterControllerHandlerMapping extends RequestMappingHandlerMapping {

    private Set<PathCustomizer> pathCustomizerSet;

    @Override
    public void afterPropertiesSet() {
        initPathCustomizer();
        super.afterPropertiesSet();
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
        if (info != null) {
            for (PathCustomizer pathCustomizer : pathCustomizerSet) {
                if (pathCustomizer.isPattern(handlerType)) {
                    return pathCustomizer.combineToOriginal(info);
                }
            }
        }
        return info;
    }

    protected void initPathCustomizer() {
        pathCustomizerSet = new HashSet<>(obtainApplicationContext().getBeansOfType(PathCustomizer.class).values());
    }
}
