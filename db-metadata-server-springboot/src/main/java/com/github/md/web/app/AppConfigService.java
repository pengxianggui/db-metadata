package com.github.md.web.app;

import com.github.md.analysis.SpringAnalysisManager;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统配置服务，对应表 meta_app_config
 */
@Slf4j
@Service
@Transactional
public class AppConfigService {
    public static final String TABLE_NAME = "meta_app_config";

    private static final String CACHE_NAME = TABLE_NAME;
    private static final String CACHE_KEY = "'latest'";

    /**
     * 获取最新版本的系统配置。若{@link AppConfigService#TABLE_NAME}中无数据，则按照表字段默认值初始化一条并返回。
     * <p>
     *
     * @param useCache 是否使用缓存。若为true, 当存在缓存时，优先返回缓存内容。
     * @return
     */
    @Cacheable(value = CACHE_NAME, key = CACHE_KEY, condition = "#useCache")
    public AppConfig getLatest(boolean useCache) {
        Record r = SpringAnalysisManager.me().dbMain().findFirst("select * from " + TABLE_NAME + " order by version desc limit 1");
        if (r == null) {
            r = new Record();
            r.set("id", "0");
            SpringAnalysisManager.me().dbMain().save(TABLE_NAME, r);
            r = SpringAnalysisManager.me().dbMain().findById(TABLE_NAME, "0");
        }
        return new AppConfig(r);
    }

    public AppConfig getById(String id) {
        Record r = SpringAnalysisManager.me().dbMain().findById(TABLE_NAME, id);
        return r != null ? new AppConfig(r) : null;
    }

    @CacheEvict(value = CACHE_NAME, key = CACHE_KEY)
    public void clearCache() {
        log.debug("The cache of meta_app_config has been cleared");
    }
}
