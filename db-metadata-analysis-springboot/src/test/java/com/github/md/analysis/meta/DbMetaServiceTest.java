package com.github.md.analysis.meta;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p> @Date : 2021/9/3 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@SpringBootTest
@Slf4j
class DbMetaServiceTest {

    @Autowired
    DbMetaService dbMetaService;
    @Test
    void importFromTable() {
        IMetaObject metaObject = dbMetaService.importFromTable("metadata","meta_object");
        log.info(metaObject.fields().stream().findFirst().get().cn());
        assertThat(dbMetaService.findAll().size()>0);

    }

    @Test
    void findAll() {
    }

    @Test
    void findByCode() {
    }
}