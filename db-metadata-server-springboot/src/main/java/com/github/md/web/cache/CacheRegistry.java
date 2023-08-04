package com.github.md.web.cache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cache.CacheManager;

public class CacheRegistry {

    @Setter
    @Getter
    private CacheManager cacheManager;
}
