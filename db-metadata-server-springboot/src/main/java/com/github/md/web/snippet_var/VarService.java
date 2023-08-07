package com.github.md.web.snippet_var;

import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.web.AppConst;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VarService {
    private static final String VAR_CACHE_NAME = "meta_var";

    /**
     * 获取所有的全局变量。
     *
     * @return
     */
    @Cacheable(value = VAR_CACHE_NAME, key = "'all'")
    public List<Record> getAllVars() {
        return SpringAnalysisManager.me().dbMain().findAll(AppConst.INNER_TABLE.META_VAR.getTableName());
    }

    @CacheEvict(value = VAR_CACHE_NAME, key = "'all'")
    public void clearVarCache() {
        log.debug("The cache of {} has been cleared", VAR_CACHE_NAME);
    }
}
