package com.github.md.web.kit;

import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.analysis.db.MetaDataTypeConvert;
import com.github.md.analysis.kit.Kv;
import com.google.common.collect.Lists;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> @Date : 2019/11/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class Dicts {

    private static final Dicts me = new Dicts();

    public static final Dicts me() {
        return me;
    }

    /**
     * 获取所有字典项
     *
     * @return
     */
    public List<Kv> names() {
        List<Record> records = SpringAnalysisManager.me().dbMain().find("select * from meta_dict where p_value is null");

        if (CollectionUtils.isEmpty(records)) {
            return Lists.newArrayList();
        }
        return records.stream().map(r -> Kv.by("key", r.getStr("key")).set("value", r.get("value"))).collect(Collectors.toList());
    }

    /**
     * 获取指定字典下的k-v对。优先从数据库中获取，再从静态文件中获取
     *
     * @param value
     * @return
     */
    public List<Kv> getKvs(String value) {
        List<Record> records = SpringAnalysisManager.me().dbMain().find("select * from meta_dict where p_value = ?", value);
        return records.stream().map(r -> Kv.by("key", r.getStr("key"))
                .set("value", MetaDataTypeConvert.convert(r.getStr("value"), StrKit.defaultIfBlank(r.getStr("value_db_type"), "VARCHAR"))))
                .collect(Collectors.toList());
    }
}
