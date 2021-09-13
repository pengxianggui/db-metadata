package com.github.md.analysis.db;

import com.github.md.analysis.db.registry.DataSourceInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * <p> @Date : 2021/9/1 </p>
 * <p> @Project : MD</p>
 *
 * <p> @author konbluesky </p>
 */
@SpringBootTest
@Import(DataSourceInitializer.class)
@Slf4j
class MysqlServiceByJdbcTmplTest {

    //    @Autowired
    //    JdbcMysqlService jdbcMysqlService;
    //
    //    @Autowired
    //    DataSourceManager dataSourceManager;
    //
    //    @Test
    //    void showTables() {
    //        Assert.isTrue(jdbcMysqlService.showSchema().contains(dataSourceManager.mainSource().schemaName()));
    //        log.info("schema : {}", dataSourceManager.mainSource().schemaName());
    //        log.info("{}", jdbcMysqlService.showTables(dataSourceManager.mainSource().schemaName()));
    //    }
    //
    //    @Test
    //    void getTable(){
    //        String schema="metadata";
    //        String tableName="meta_object";
    //        log.info("{}", JSON.toJSONString(jdbcMysqlService.getColumns(schema, tableName)));
    //    }
}