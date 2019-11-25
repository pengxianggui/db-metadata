package com.hthjsj.web;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.jfinal.kit.Kv;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.InputStreamReader;
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
        String result = "";
        try (InputStream fis = getClass().getClassLoader().getResourceAsStream(DICT_JSON)) {
            log.info("load  dict.json file");
            result = CharStreams.toString(new InputStreamReader(fis, Charsets.UTF_8));
            log.info("file length : {}", result.length());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        dict.set(UtilKit.getKv(result));
    }

    public Kv dict() {
        return dict;
    }

    public List<Kv> getKvs(String key) {
        return UtilKit.getKvs(dict(), key);
    }

    public List<Kv> getGender() {
        return getKvs("gender");
    }

    public List<Kv> getYesNo() {
        return getKvs("yesno");
    }
}
