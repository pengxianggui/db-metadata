package com.hthjsj.analysis.db.registry;

import com.alibaba.druid.pool.DruidDataSource;
import com.jfinal.kit.StrKit;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p> @Date : 2021/9/1 </p>
 * <p> @Project : MD</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class MDDataSource implements IDataSource {

    private final DataSource dataSource;

    private final DataSourceType dataSourceType;

    @Getter
    private boolean isAlibabaDruid;

    private String schemaName;

    private MDDataSource(DataSource dataSource, DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
        this.dataSource = dataSource;

        if (dataSource instanceof DruidDataSource) {
            isAlibabaDruid = true;
        }
        //resolve schemaName
        if (isAlibabaDruid) {
            this.schemaName = fetchSchemaFromJdbcUrl(getDruidDataSource().getUrl());
        } else {
            try {
                this.schemaName = dataSource.getConnection().getSchema();
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public static MDDataSource Main(DataSource dataSource) {
        return new MDDataSource(dataSource, DataSourceType.MAIN);
    }

    public static MDDataSource Biz(DataSource dataSource) {
        return new MDDataSource(dataSource, DataSourceType.BIZ);
    }

    private String fetchSchemaFromJdbcUrl(String jdbcUrl) {
        //FIXME bad smell
        Pattern pattern = Pattern.compile("jdbc:mysql://.*/(.*)\\?.*");
        Matcher matcher = pattern.matcher(jdbcUrl);
        String dbName = "";
        if (matcher.find()) {
            dbName = matcher.group(1);
        }
        return StrKit.defaultIfBlank(dbName, "");
    }

    private DruidDataSource getDruidDataSource() {
        return (DruidDataSource) dataSource;
    }

    @Override
    public DataSource dataSource() {
        return dataSource;
    }

    @Override
    public DataSourceType dataSourceType() {
        return dataSourceType;
    }

    @Override
    public String schemaName() {
        return schemaName;
    }
}
