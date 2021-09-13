package com.github.md.analysis.db.registry;

import com.github.md.analysis.MetaAnalysisException;
import com.jfinal.kit.StrKit;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.md.analysis.AnalysisConstant.BIZ_DATA_SOURCE;

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

    public DefaultDataSourceRegistrar(IDataSource mainDataSource, @Qualifier(BIZ_DATA_SOURCE) List<IDataSource> bizDataSource) {
        if (mainDataSource.dataSourceType() == DataSourceType.MAIN) {
            cacheSchameKey = mainDataSource.schemaName();
            dataSourceMap.put(mainDataSource.schemaName(), mainDataSource);
        }
        bizDataSource.forEach(s -> {
            if (dataSourceMap.containsKey(s.schemaName())) {
                throw new MetaAnalysisException("数据源 schemaName [%s]重复", s.schemaName());
            }
            dataSourceMap.put(s.schemaName(), s);
        });
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

    @Override
    public IDataSource source(String sourceName) {
        if (!dataSourceMap.containsKey(sourceName)) {
            throw new MetaAnalysisException("[" + sourceName + "] not found");
        }
        return dataSourceMap.get(sourceName);
    }
}
