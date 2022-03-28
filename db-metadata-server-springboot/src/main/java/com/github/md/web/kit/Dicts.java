package com.github.md.web.kit;

import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.analysis.kit.Kv;
import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p> @Date : 2019/11/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class Dicts {

    private final static Kv dict = Kv.create();

    private static final Dicts me = new Dicts();

    private static final String DICT_JSON = "dict.json";

    public static final Dicts me() {
        return me;
    }

    public void init() {
        loadTmplConfigFromFile(); // 初始化静态
    }

    private void loadTmplConfigFromFile() {
        String result = UtilKit.loadConfigByFile(DICT_JSON);
        dict.set(UtilKit.getKv(result));
    }

    public Kv dict() {
        return dict;
    }

    /**
     * 获取所有字典项
     *
     * @return
     */
    public List<Kv> names() {
        List<Kv> result;

        List<Kv> kvOfNameInFile = ((Set<String>) dict.keySet()).stream().map(k -> Kv.by("key", k).set("value", k)).collect(Collectors.toList());
        List<Record> records = SpringAnalysisManager.me().dbMain().find("select * from meta_dict where p_value is null");

        if (CollectionUtils.isEmpty(records)) {
            result = Lists.newArrayList();
        } else {
            result = records.stream().map(r -> Kv.by("key", r.getStr("key")).set("value", r.get("value"))).collect(Collectors.toList());
        }

        for (Kv kvInFile : kvOfNameInFile) {
            if (result.stream().anyMatch(kv -> Objects.equals(kv.getStr("key"), kvInFile.getStr("key")))) { // 文件中的字典已经在库中被覆盖了
                continue;
            }
            result.add(kvInFile);
        }

        return result;
    }

    /**
     * 获取指定字典下的k-v对。优先从数据库中获取，再从静态文件中获取
     *
     * @param value
     * @return
     */
    public List<Kv> getKvs(String value) {
        List<Record> records = SpringAnalysisManager.me().dbMain().find("select * from meta_dict where p_value = ?", value);
        if (CollectionUtils.isEmpty(records)) {
            return getKvsFormFile(value);
        }

        return records.stream().map(r -> Kv.by("key", r.getStr("key"))
                .set("value", r.getObject("value"))).collect(Collectors.toList());
    }

    /**
     * 从静态文件dict.json中获取
     *
     * @param key
     * @return
     */
    private List<Kv> getKvsFormFile(String key) {
        return UtilKit.getKvs(dict(), key);
    }
}
