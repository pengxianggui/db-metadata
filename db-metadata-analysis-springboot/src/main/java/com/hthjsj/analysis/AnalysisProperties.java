package com.hthjsj.analysis;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.hthjsj.analysis.AnalysisConstant.BIZ_DATA_SOURCE_PROPERTIES;

/**
 * <p> @Date : 2021/9/7 </p>
 * <p> @Project : db-metadata-analysis-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
@Configuration
@ConfigurationProperties("md.analysis")
public class AnalysisProperties {

    @Getter
    final Map<String, DataSourceProperties> sourceConfig;

    public AnalysisProperties(@Qualifier(BIZ_DATA_SOURCE_PROPERTIES) Map<String, DataSourceProperties> sourceConfig) {
        this.sourceConfig = sourceConfig;
    }

    @Data
    public static class Extension {

        private boolean metaObjectEnabled;

        private boolean metaFieldEnabled;

        private boolean componentInstanceEnabled;
    }
}
