package com.hthjsj;

import com.hthjsj.analysis.db.registry.DataSourceInitializer;
import com.hthjsj.analysis.db.registry.JFinalActiveRecordInitializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * <p> @Date : 2021/9/7 </p>
 * <p> @Project : db-metadata-analysis-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
@Import({ DataSourceInitializer.class, JFinalActiveRecordInitializer.class })
@EnableConfigurationProperties
@ComponentScan
@ConditionalOnProperty(name = "md.analysis.registrar", havingValue = "default", matchIfMissing = true)
public class AnalysisAutoConfiguration {

}
