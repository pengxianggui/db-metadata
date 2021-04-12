package com.hthjsj.web.query.dynamic;

import com.jfinal.plugin.activerecord.Record;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class DefaultContext extends HashMap implements Context {

    public DefaultContext() {
    }

    public DefaultContext(Map map) {
        putAll(map);
    }

    @Override
    public void set(String name, Object value) {
        put(name, value);
    }

    @Override
    public Map data() {
        return this;
    }
}
