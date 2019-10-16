package com.hthjsj.web.query.sqls;

import com.hthjsj.analysis.meta.IMetaField;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class SortCondition extends MetaSQLBuilder {

    Map<String, Object> values = new HashMap<>();

    @Override
    public void init(IMetaField metaField, Map<String, Object> httpParams) {

        String sort_suffix = "st";
        String sortByDesc = "desc";
        Object value = httpParams.get("st");
    }

    @Override
    public Map<String, Object> result() {
        return null;
    }
}
