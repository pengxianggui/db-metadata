package com.github.md.analysis.db.registry;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.md.analysis.MetaAnalysisException;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> @Date : 2021/8/31 </p>
 * <p> @Project : MD</p>
 *
 * <p> @author konbluesky </p>
 */
public class DataSourceManager {

    @Getter
    private static String mainSourceName;

    @Getter
    DataSourceRegistrar dataSourceRegistrar;

    public DataSourceManager(DataSourceRegistrar dataSourceRegistrar) {
        this.dataSourceRegistrar = dataSourceRegistrar;
        DataSourceManager.mainSourceName = dataSourceRegistrar.mainSource().schemaName();
        if (!StringUtils.hasText(DataSourceManager.mainSourceName)) {
            throw new MetaAnalysisException("无法分析主数据库名");
        }
    }

    /**
     * 所有数据源名: schema
     *
     * @return
     */
    public List<String> sourceNameList() {
        return dataSourceRegistrar.allSource().keySet().stream().collect(Collectors.toList());
    }

    public IDataSource mainSource() {
        return dataSourceRegistrar.mainSource();
    }

    @Deprecated
    public DruidDataSource mainSourceOfDruid() {
        return (DruidDataSource) dataSourceRegistrar.mainSource();
    }

    public Map<String, IDataSource> allSource() {
        return dataSourceRegistrar.allSource();
    }

    public IDataSource source(String sourceName) {
        return dataSourceRegistrar.source(sourceName);
    }
}
