package com.hthjsj.web.ui;

import com.google.common.collect.Lists;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <p> @Date : 2019/11/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class OptionsKit {

    public static List<Kv> options(String[] values) {
        List<Kv> kvs = Lists.newArrayList();
        for (String value : values) {
            kvs.add(Kv.by("key", value).set("value", value));
        }
        return kvs;
    }

    /**
     * Record 为 "select x id,xx cn from xxx" 查询结果
     * 即:必须包含ID,cn 列
     *
     * @param records
     *
     * @return
     */
    public static List<Kv> trans(List<Record> records) {
        List<Kv> result = Lists.newArrayList();
        for (Record record : records) {
            if (record.getColumns().containsKey("id") && record.getColumns().containsKey("cn")) {
                log.debug("id-{},cn-{}", record.getStr("id"), record.getStr("cn"));
                result.add(Kv.by("value", record.getStr("id")).set("key", record.getStr("cn")));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(options(new String[] { "123", "12312", "fdsa" }));
    }
}
