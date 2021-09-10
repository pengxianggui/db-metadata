package com.hthjsj.web.config.register;

import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

/**
 * <p> @Date : 2021/9/10 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
public interface PathCustomizer {

    String adJustUrl();

    boolean isPattern(Class<?> clazz);

    RequestMappingInfo combineToOriginal(RequestMappingInfo requestMappingInfo);
}
