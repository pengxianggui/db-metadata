package com.hthjsj.web.query.sqls;

import com.hthjsj.analysis.meta.IMetaField;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FieldEqualsMatch extends MetaSQLBuilder {

    Map<String, Object> value;

    @Override
    public void init(IMetaField metaField, Map<String, Object> httpParams) {
        value = new HashMap<>(1);
        if (httpParams.get(metaField.en()) != null) {
            value.put(SQL_PREFIX + metaField.en() + "=?", httpParams.get(metaField.en()));
        }
    }

    @Override
    public Map<String, Object> result() {
        return value;
    }
}
