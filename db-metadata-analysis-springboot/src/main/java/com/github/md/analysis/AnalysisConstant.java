package com.github.md.analysis;

/**
 * <p> @Date : 2021/9/1 </p>
 * <p> @Project : MD</p>
 *
 * <p> @author konbluesky </p>
 */
public class AnalysisConstant {

    /**
     * 如果希望主数据源配置在{@link #DATA_SOURCE_KEY}中，则标识其为主数据源的方法是其key为master。
     * 若{@link #DATA_SOURCE_KEY}中无master配置，则默认取spring.datasource进行主数据源的配置
     */
    public static final String MAIN_DATA_SOURCE_KEY = "master";

    /**
     * 数据源key
     */
    public static final String DATA_SOURCE_KEY = "md.analysis.db-source";

    /**
     * The name of the main data source in the Spring container
     * <p>
     * Type : IDataSource
     */
    public static final String MAIN_DATA_SOURCE_BEAN_NAME = "mainDataSource";

    public static final String BIZ_DATA_SOURCE = "bizDataSource";

    public static final String BIZ_DATA_SOURCE_PROPERTIES = "bizDataSourceProperties";
}
