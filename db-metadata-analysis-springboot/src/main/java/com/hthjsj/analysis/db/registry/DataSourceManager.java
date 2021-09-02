package com.hthjsj.analysis.db.registry;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Getter;

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
    DataSourceRegistrar dataSourceRegistrar;

    public DataSourceManager(DataSourceRegistrar dataSourceRegistrar) {
        this.dataSourceRegistrar = dataSourceRegistrar;
    }

    public List<String> sourceNameList() {
        return dataSourceRegistrar.allSource().keySet().stream().collect(Collectors.toList());
    }

    public IDataSource mainSource() {
        return dataSourceRegistrar.mainSource();
    }

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
