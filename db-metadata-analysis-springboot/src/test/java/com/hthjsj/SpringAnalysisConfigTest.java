package com.hthjsj;

import com.hthjsj.analysis.AnalysisProperties;
import com.hthjsj.analysis.db.registry.DataSourceInitializer;
import com.hthjsj.analysis.db.registry.JFinalActiveRecordPluginManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * <p> @Date : 2021/9/3 </p>
 * <p> @Project : db-metadata-analysis-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
@SpringBootTest
@Import({ DataSourceInitializer.class, JFinalActiveRecordPluginManager.class })
@Slf4j
class SpringAnalysisConfigTest {

    @Autowired
    SpringAnalysisManager springAnalysisConfig;

    @Autowired
    AnalysisProperties analysisProperties;

    @Test
    void setAnalysisProperties() {
        log.info(analysisProperties.getSourceConfig().toString());
    }

    @Test
    void me() {
        log.info(springAnalysisConfig.toString());
    }

    @Test
    void dbMain() {
        log.info(springAnalysisConfig.dbMain().getConfig().getName());
    }
}