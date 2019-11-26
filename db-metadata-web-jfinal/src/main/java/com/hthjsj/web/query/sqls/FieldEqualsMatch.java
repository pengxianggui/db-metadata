package com.hthjsj.web.query.sqls;

import com.hthjsj.analysis.db.MetaDataTypeConvert;
import com.hthjsj.analysis.meta.IMetaField;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FieldEqualsMatch extends MetaSQLExtract {

    Map<String, Object> values = new HashMap<>(1);

    @Override
    public void init(IMetaField metaField, Map<String, Object> httpParams) {
        Object value = httpParams.get(metaField.en());

        //boolean 转义
        if (metaField.dbType().isBoolean(metaField.dbTypeLength().intValue())) {
            value = MetaDataTypeConvert.convert(metaField, value);
        }

        if (httpParams.get(metaField.en()) != null) {
            values.put(SQL_PREFIX + metaField.en() + "=?", value);
        }
    }

    @Override
    public Map<String, Object> result() {
        return values;
    }
}
