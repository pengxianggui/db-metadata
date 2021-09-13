package com.github.md.web.config.register;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.util.Set;

/**
 * 对指定包下的Controller 批量按照adjust规则注册
 * <p> @Date : 2021/9/10 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class PrefixPathCustomizer implements PathCustomizer {

    private final Set<String> packageNames;

    private final String prefix;

    public PrefixPathCustomizer(String prefix, String... packageNames) {
        this.packageNames = Sets.newHashSet(packageNames);
        this.prefix = prefix;
    }

    @Override
    public String adJustUrl() {
        return this.prefix;
    }

    @Override
    public boolean isPattern(Class<?> clazz) {
        boolean status = false;
        if (null != clazz) {
            for (String packageName : packageNames) {
                status = clazz.getPackage().getName().startsWith(packageName);
                if (status) {
                    return status;
                }
            }
        }
        return status;
    }

    @Override
    public RequestMappingInfo combineToOriginal(RequestMappingInfo originalRequest) {
        RequestMappingInfo rmi = RequestMappingInfo.paths(adJustUrl()).build().combine(originalRequest);
        if (log.isInfoEnabled()) {
            log.info("will change {} onto {};", originalRequest, rmi);
        }
        return rmi;
    }
}
