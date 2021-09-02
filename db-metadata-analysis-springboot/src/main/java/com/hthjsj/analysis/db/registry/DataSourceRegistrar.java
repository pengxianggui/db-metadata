package com.hthjsj.analysis.db.registry;

import java.util.Map;

/**
 * <p> @Date : 2021/8/31 </p>
 * <p> @Project : MD</p>
 *
 * <p> @author konbluesky </p>
 */
public interface DataSourceRegistrar {

    IDataSource mainSource();

    Map<String, IDataSource> allSource();

    IDataSource source(String sourceName);
}
