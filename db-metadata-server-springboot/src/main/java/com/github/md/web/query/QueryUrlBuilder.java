package com.github.md.web.query;

import com.github.md.analysis.kit.Kv;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.jfinal.kit.StrKit;

import java.util.List;

/**
 * 灵活构建带参数的url工具
 * <p> @Date : 2021/9/7 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
public class QueryUrlBuilder {

    private final Kv params = Kv.create();

    private String basePath;

    public QueryUrlBuilder base(String basePath) {
        this.basePath = basePath;
        return this;
    }

    public QueryUrlBuilder param(String key, String value) {
        params.setIfNotBlank(key, value);
        return this;
    }

    public String toQueryString(boolean questionMark) {
        StringBuilder sb = new StringBuilder();
        sb.append(StrKit.defaultIfBlank(basePath, ""));

        if (questionMark) {
            sb.append("?");
        }
        if (params.isEmpty()) {
            return sb.toString();
        }

        List<String> ss = Lists.newArrayList();
        params.forEach((key, value) -> {
            ss.add(key + "=" + value);
        });
        return Joiner.on("&").appendTo(sb, ss).toString();
    }
}
