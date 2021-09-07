package com.hthjsj.analysis.db.registry;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hthjsj.analysis.AnalysisConstant.*;

/**
 * This class is used as the initialization entry under the db module
 *
 * <p> @Date : 2021/8/31 </p>
 * <p> @Project : MD</p>
 *
 * <p> @author konbluesky </p>
 *
 * @link https://newbedev.com/can-i-manually-load-configurationproperties-without-the-spring-appcontext
 */
@Configuration
public class DataSourceInitializer {

    /**
     * 1. 自动初始化Spring source 的 bean
     * 2. 不满足时从业务库中取第一个
     */
    @Bean(MAIN_DATA_SOURCE_RAW_BEAN_NAME)
    @ConfigurationProperties(DEFAULT_DATA_SOURCE_PROPERTY_NAME)
//    @ConditionalOnProperty(name = DEFAULT_DATA_SOURCE_PROPERTY_NAME)
    public DruidDataSource mainDataSource() {
        return new DruidDataSource();
    }

    @Bean(MAIN_DATA_SOURCE_BEAN_NAME)
//    @ConditionalOnProperty(name = DEFAULT_DATA_SOURCE_PROPERTY_NAME)
    public IDataSource mainSource(@Qualifier(MAIN_DATA_SOURCE_RAW_BEAN_NAME) DruidDataSource druidDataSource) {
        return MDDataSource.Main(druidDataSource);
    }

    @Bean
//    @ConditionalOnBean(IDataSource.class)
    public DataSourceRegistrar dataSourceRegistrar(@Qualifier(MAIN_DATA_SOURCE_BEAN_NAME) IDataSource dataSource) {
        return new DefaultDataSourceRegistrar(dataSource);
    }

    @Bean(BIZ_DATA_SOURCE_PROPERTIES)
    @ConfigurationProperties("md.analysis.db-source")
    public Map<String, DataSourceProperties> otherSourcesProperteis() {
        return new HashMap<>();
    }

    @Bean(BIZ_DATA_SOURCE)
    public List<IDataSource> otherSources(@Qualifier(BIZ_DATA_SOURCE_PROPERTIES) Map<String, DataSourceProperties> otherSourceProp) {
        List<IDataSource> bizDataSources = new ArrayList<>(4);
        otherSourceProp.forEach((k, v) -> {
            bizDataSources.add(MDDataSource.Biz(v.initializeDataSourceBuilder().build()));
        });
        return bizDataSources;
    }

    @Bean
    public DataSourceManager dataSourceManager(DataSourceRegistrar dataSourceRegistrar) {
        return new DataSourceManager(dataSourceRegistrar);
    }
}
