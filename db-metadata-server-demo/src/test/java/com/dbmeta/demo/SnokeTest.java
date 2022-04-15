package com.dbmeta.demo;

import com.github.md.analysis.db.SnowFlake;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author pengxg
 * @date 2022/4/15 9:18 上午
 */
@SpringBootTest
public class SnokeTest {

    @Test
    public void test() {
        int count = 1;
        int i = 0;
        while (i < count) {
            System.out.println(SnowFlake.me().nextId());
            i++;
        }
    }
}
