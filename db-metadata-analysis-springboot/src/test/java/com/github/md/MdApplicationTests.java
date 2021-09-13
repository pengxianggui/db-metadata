package com.github.md;

import com.github.md.analysis.db.registry.DataSourceInitializer;
import com.github.md.analysis.db.registry.DataSourceRegistrar;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.util.Map;

@SpringBootTest
@Import(DataSourceInitializer.class)
class MdApplicationTests {

    @Autowired
    DataSourceProperties dataSourceProperties;

    @Autowired
    @Qualifier("mainDataSource")
    DataSource dataSource;

    @Autowired
    DataSourceRegistrar dataSourceRegistrar;

    @Autowired
    @Qualifier("bizDataSourceProperties")
    Map bizDataSourceProperties;

    @Test
    void contextLoads() {
        System.out.println(dataSourceRegistrar.allSource());
        System.out.println(dataSourceRegistrar.allSource());
        System.out.println(dataSourceRegistrar.allSource());
    }

    @Test
    void loadBizDataSourceProperties() {
        System.out.println(bizDataSourceProperties);
    }
}
