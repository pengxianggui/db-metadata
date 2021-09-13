package com.github.md.analysis.db.registry;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * <p> @Date : 2021/9/2 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@SpringBootTest
@Import(JFinalActiveRecordInitializer.class)
@Slf4j
class JFinalActiveRecordInitializerTest {

    @Autowired
    ActiveRecordPlugin activeRecordPlugin;

    @Test
    void activeRecordPlugin() {
      log.info("activeRecordPlugin is [{}]",activeRecordPlugin.start());
      log.info("Show Tables", Db.use().find("select * from meta_object").stream().findFirst().get());
    }
}