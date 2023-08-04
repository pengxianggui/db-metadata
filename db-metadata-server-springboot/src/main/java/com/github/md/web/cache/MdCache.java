package com.github.md.web.cache;

import com.github.md.web.kit.AssertKit;
import com.jfinal.plugin.activerecord.cache.ICache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Optional;

/**
 * 装饰器模式?
 */
@Slf4j
public class MdCache implements ICache {
    private CacheManager cacheManager;

    public MdCache(CacheManager cacheManager) {
        AssertKit.isTrue(cacheManager != null, "请正确配置cacheManager实现类");
        this.cacheManager = cacheManager;
    }

    @Override
    public <T> T get(String cacheName, Object key) {
        Cache cache = this.cacheManager.getCache(cacheName);
        if (cache == null) {
            log.warn("未配置 cacheName: {}", cacheName);
            return null;
        }
        return (T) Optional.ofNullable(cache.get(key))
                .map(Cache.ValueWrapper::get)
                .orElse(null);
    }

    @Override
    public void put(String cacheName, Object key, Object value) {
        Cache cache = this.cacheManager.getCache(cacheName);
        if (cache == null) {
            log.warn("未配置 cacheName: {}", cacheName);
            return;
        }
        cache.put(key, value);
    }

    @Override
    public void remove(String cacheName, Object key) {
        Cache cache = this.cacheManager.getCache(cacheName);
        if (cache == null) {
            log.warn("未配置 cacheName: {}", cacheName);
            return;
        }
        cache.evictIfPresent(key);
    }

    @Override
    public void removeAll(String cacheName) {
        Cache cache = this.cacheManager.getCache(cacheName);
        if (cache == null) {
            log.warn("未配置 cacheName: {}", cacheName);
            return;
        }
        cache.invalidate();
    }
}
