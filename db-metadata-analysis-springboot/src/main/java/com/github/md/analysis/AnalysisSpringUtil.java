package com.github.md.analysis;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> @Date : 2021/9/7 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
@Component
public class AnalysisSpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext; // Spring应用上下文环境

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AnalysisSpringUtil.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<?> clz) throws BeansException {
        return (T) applicationContext.getBean(clz);
    }

    public static <T> Map<String, T> getBeansOfTypes(Class<T> type) {
        Map<String, T> map = applicationContext.getBeansOfType(type);
        return CollectionUtils.isEmpty(map) ? new HashMap<>() : map;
    }
}