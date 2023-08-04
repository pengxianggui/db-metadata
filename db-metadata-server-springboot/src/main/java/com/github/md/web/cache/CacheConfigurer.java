package com.github.md.web.cache;

/**
 * 缓存管理器配置
 */
public interface CacheConfigurer {

    /**
     * 装配缓存管理器
     *
     * @param cacheRegistry
     */
    default void configCacheManager(CacheRegistry cacheRegistry) {
        
    }
}
