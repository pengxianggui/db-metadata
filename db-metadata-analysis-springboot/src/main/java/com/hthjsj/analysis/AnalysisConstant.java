package com.hthjsj.analysis;

/**
 * <p> @Date : 2021/9/1 </p>
 * <p> @Project : MD</p>
 *
 * <p> @author konbluesky </p>
 */
public class AnalysisConstant {

    /**
     * The name of the main data source in the Spring container
     * <p>
     * Type : com.hthjsj.analysis.db.registry.IDataSource
     */
    public static final String MAIN_DATA_SOURCE_BEAN_NAME = "mainDataSource";

    /**
     * The name of the original main data source Bean
     * <p>
     * Type : e com.alibaba.druid.pool.DruidDataSource
     */
    public static final String MAIN_DATA_SOURCE_RAW_BEAN_NAME = "mainDataSourceRaw";

    public static final String BIZ_DATA_SOURCE = "bizDataSource";

    public static final String DEFAULT_DATA_SOURCE_PROPERTY_NAME = "spring.datasource";

    public static final String BIZ_DATA_SOURCE_PROPERTIES = "bizDataSourceProperties";
}
