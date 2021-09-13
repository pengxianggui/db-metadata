package com.github.md.web.query.sqls;

import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.MetaSqlKit;
import com.jfinal.kit.StrKit;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class SortMatch extends MetaSQLExtract {

    Map<String, Object> values = new HashMap<>();

    @Override
    public void init(IMetaField metaField, Map<String, Object> httpParams) {

        String sort_suffix = "_st";
        String fileCode = MetaSqlKit.discernColumns(metaField.fieldCode());
        String value = (String) httpParams.get(fileCode + sort_suffix);
        if (StrKit.notBlank(value) && ("desc".equalsIgnoreCase(value) || "asc".equalsIgnoreCase(value))) {
            setMutiple(true);
            values.put(fileCode, value);
        }
    }

    @Override
    public Map<String, Object> result() {
        return values;
    }
}
