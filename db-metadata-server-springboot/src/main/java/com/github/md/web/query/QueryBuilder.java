package com.github.md.web.query;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.github.md.analysis.kit.Kv;

import java.util.List;

/**
 * <p> @Date : 2021/9/7 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
public class QueryBuilder {

    private final Kv params = Kv.create();

    public QueryBuilder builder(String key, String value) {
        params.setIfNotBlank(key, value);
        return this;
    }

    public String buildQueryString(boolean questionMark) {
        StringBuilder sb = new StringBuilder();
        if (questionMark) {
            sb.append("?");
        }
        List<String> ss = Lists.newArrayList();
        params.forEach((key, value) -> {
            ss.add(key + "=" + value);
        });
        return Joiner.on("&").appendTo(sb, ss).toString();
    }
}
