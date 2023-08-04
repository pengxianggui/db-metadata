package com.github.md.analysis.db.registry;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.cache.ICache;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> @Date : 2021/9/3 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class JFinalActiveRecordPluginManager {

    private static final Map<String, ActiveRecordPlugin> REGISTRY = new HashMap<>();

    public void add(String sourceName, ActiveRecordPlugin plugin) {
        if (REGISTRY.containsKey(sourceName)) {
            log.warn("The [{}] duplicate data source registration", sourceName);
        }
        REGISTRY.put(sourceName, plugin);
    }

    public void setCache(ICache cache) {
        REGISTRY.values().forEach(plugin -> plugin.setCache(cache));
    }

    public Collection<ActiveRecordPlugin> list() {
        return REGISTRY.values();
    }

    public ActiveRecordPlugin plugin(String sourceName) {
        return REGISTRY.get(sourceName);
    }

    public void start() {
        REGISTRY.values().forEach(ActiveRecordPlugin::start);
    }
}
