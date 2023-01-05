package com.github.md.analysis.db.registry;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

import static com.github.md.analysis.AnalysisConstant.*;

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

    @Bean(BIZ_DATA_SOURCE_PROPERTIES)
    @ConfigurationProperties(DATA_SOURCE_KEY)
    public Map<String, DataSourceProperties> otherSourcesProperties() {
        return new HashMap<>();
    }

//    /**
//     * 1. 自动初始化Spring source 的 bean
//     * 2. 不满足时从业务库中取第一个
//     */
////    @ConditionalOnMissingBean(DataSource.class)
////    @Bean(MAIN_DATA_SOURCE_RAW_BEAN_NAME)
////    @ConfigurationProperties(DEFAULT_DATA_SOURCE_PROPERTY_NAME)
//    //    @ConditionalOnProperty(name = DEFAULT_DATA_SOURCE_PROPERTY_NAME)
//    public DruidDataSource mainDataSource(DataSourceProperties dataSourceProperties) {
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setUrl(dataSourceProperties.getUrl());
//        druidDataSource.setUsername(dataSourceProperties.getUsername());
//        druidDataSource.setPassword(dataSourceProperties.getPassword());
//        druidDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
//        return druidDataSource;
//    }

    @Bean(MAIN_DATA_SOURCE_BEAN_NAME)
    public IDataSource mainSource(DataSourceProperties dataSourceProperties, @Qualifier(BIZ_DATA_SOURCE_PROPERTIES) Map<String, DataSourceProperties> otherSourcesProperties) {
        DataSourceProperties mainDataSourceProperties;
        if (otherSourcesProperties.containsKey(MAIN_DATA_SOURCE_KEY)) {
            mainDataSourceProperties = otherSourcesProperties.remove(MAIN_DATA_SOURCE_KEY);
        } else {
            mainDataSourceProperties = dataSourceProperties;
        }

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(mainDataSourceProperties.getUrl());
        druidDataSource.setUsername(mainDataSourceProperties.getUsername());
        druidDataSource.setPassword(mainDataSourceProperties.getPassword());
        druidDataSource.setDriverClassName(mainDataSourceProperties.getDriverClassName());
        return MDDataSource.Main(druidDataSource);
    }

    @Bean
    public DataSourceRegistrar dataSourceRegistrar(@Qualifier(MAIN_DATA_SOURCE_BEAN_NAME) IDataSource mainSource,
                                                   @Qualifier(BIZ_DATA_SOURCE) List<IDataSource> bizDataSources) {
        return new DefaultDataSourceRegistrar(mainSource, bizDataSources);
    }

    @Bean(BIZ_DATA_SOURCE)
    public List<IDataSource> bizDataSources(@Qualifier(BIZ_DATA_SOURCE_PROPERTIES) Map<String, DataSourceProperties> otherSourcesProperties) {
        List<IDataSource> bizDataSources = new ArrayList<>(4);
        otherSourcesProperties.forEach((k, v) -> {
            bizDataSources.add(MDDataSource.Biz(v.initializeDataSourceBuilder().build()));
        });
        return bizDataSources;
    }

    @Bean
    public DataSourceManager dataSourceManager(DataSourceRegistrar dataSourceRegistrar) {
        return new DataSourceManager(dataSourceRegistrar);
    }
}
