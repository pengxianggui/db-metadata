package com.hthjsj.analysis.db.registry;

import com.hthjsj.analysis.MetaAnalysisException;
import com.jfinal.kit.StrKit;

import java.util.HashMap;
import java.util.Map;

/**
 * Data source registration tool, taking into account the following centralized situations：
 * 1. Satisfy the registration problem in the case of multiple data sources；
 * 2. In other projects, the data source may have been registered by other components；
 * <p>
 * In this case, you can freely obtain data sources by implementing Data Source Registrar
 *
 * <p> @Date : 2021/8/31 </p>
 * <p> @Project : MD</p>
 *
 * <p> @author konbluesky </p>
 */
public class DefaultDataSourceRegistrar implements DataSourceRegistrar {

    private final Map<String, IDataSource> dataSourceMap = new HashMap<>();

    private String cacheSchameKey;

    public DefaultDataSourceRegistrar(IDataSource dataSource) {
        if (dataSource.dataSourceType() == DataSourceType.MAIN) {
            cacheSchameKey = dataSource.schemaName();
        }
        dataSourceMap.put(dataSource.schemaName(), dataSource);
    }

    @Override
    public IDataSource mainSource() {
        if (StrKit.isBlank(cacheSchameKey)) {
            throw new MetaAnalysisException("mainDBSource not found");
        }
        return dataSourceMap.get(cacheSchameKey);
    }

    @Override
    public Map<String, IDataSource> allSource() {
        return dataSourceMap;
    }

    public IDataSource source(String sourceName) {
        if (!dataSourceMap.containsKey(sourceName)) {
            throw new MetaAnalysisException("[" + sourceName + "] not found");
        }
        return dataSourceMap.get(sourceName);
    }
}
