package com.github.md.analysis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.github.md.analysis.AnalysisConstant.BIZ_DATA_SOURCE_PROPERTIES;

/**
 * <p> @Date : 2021/9/7 </p>
 * <p> @Project : db-metadata-analysis-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
@Configuration
@ConfigurationProperties("md.analysis")
public class AnalysisProperties {

    @Setter
    @Getter
    private boolean showSql = false;

    @Getter
    final Map<String, DataSourceProperties> sourceConfig;

    public AnalysisProperties(@Qualifier(BIZ_DATA_SOURCE_PROPERTIES) Map<String, DataSourceProperties> sourceConfig) {
        this.sourceConfig = sourceConfig;
    }
}
