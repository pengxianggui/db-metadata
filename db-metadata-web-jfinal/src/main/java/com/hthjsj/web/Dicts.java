package com.hthjsj.web;

import com.jfinal.kit.Kv;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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
        loadTmplConfigFromFile();
    }

    private void loadTmplConfigFromFile() {
        String result = UtilKit.loadConfigByFile(DICT_JSON);
        dict.set(UtilKit.getKv(result));
    }

    public Kv dict() {
        return dict;
    }

    public Object[] keys() {
        return dict.keySet().toArray();
    }

    public List<Kv> getKvs(String key) {
        return UtilKit.getKvs(dict(), key);
    }
}
