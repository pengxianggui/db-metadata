package com.github.md.web.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 内存缓存——默认的缓存装配
 */
@EnableCaching
@Configuration
public class MdCacheConfiguration {

    /**
     * ActiveRecord中的API用的什么缓存实现，取决于此处传入的CacheManager的具体实现
     *
     * @param cacheManager
     * @return
     */
    @Bean
    public MdCache mdCache(CacheManager cacheManager) {
        return new MdCache(cacheManager);
    }
}
