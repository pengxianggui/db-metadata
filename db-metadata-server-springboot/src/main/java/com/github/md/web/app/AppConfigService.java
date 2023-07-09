package com.github.md.web.app;

import com.github.md.analysis.SpringAnalysisManager;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 获取最新版本的系统配置。若{@link AppConfigService#TABLE_NAME}中无数据，则按照表字段默认值初始化一条并返回。
     * <p>
     * TODO 引入缓存，避免每次查库
     *
     * @return
     */
    public AppConfig getLatest() {
        Record r = SpringAnalysisManager.me().dbMain().findFirst("select * from " + TABLE_NAME + " order by version desc limit 1");
        if (r == null) {
            r = new Record();
            r.set("id", "0");
            SpringAnalysisManager.me().dbMain().save(TABLE_NAME, r);
            r = SpringAnalysisManager.me().dbMain().findById(TABLE_NAME, "0");
        }
        return new AppConfig(r);
    }
}
