package com.github.md.web.cache;

import com.jfinal.plugin.activerecord.cache.ICache;
import org.springframework.cache.caffeine.CaffeineCacheManager;

/**
 * 默认的缓存管理器——【内存缓存】。
 * {@link CaffeineCacheManager} 的子类，同时实现JFinal阵营中的ActiveRecord的缓存接口{@link ICache}。
 * 以便同时支持 1.业务中使用SpringBoot方式(@Cacheable、@CacheEvict)使用缓存; 2. ActiveRecord API接口中使用缓存。
 */
public class DefaultMdCacheManager extends CaffeineCacheManager {
}
