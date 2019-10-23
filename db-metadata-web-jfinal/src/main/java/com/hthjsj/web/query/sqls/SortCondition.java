package com.hthjsj.web.query.sqls;

import com.hthjsj.analysis.meta.IMetaField;
import com.jfinal.kit.StrKit;

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
        String sortByDesc = " desc ";
        //TODO 排序优化
        String value = (String) httpParams.get(sort_suffix);
        if (StrKit.notBlank(value)) {
            values.put(" order by ", value + sortByDesc);
        }
    }

    @Override
    public Map<String, Object> result() {
        return values;
    }
}
