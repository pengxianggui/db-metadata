package com.hthjsj.analysis.db.registry;

import javax.sql.DataSource;

/**
 * <p> @Date : 2021/9/1 </p>
 * <p> @Project : MD</p>
 *
 * <p> @author konbluesky </p>
 */
public interface IDataSource {

    DataSource dataSource();

    DataSourceType dataSourceType();

    String schemaName();
}
