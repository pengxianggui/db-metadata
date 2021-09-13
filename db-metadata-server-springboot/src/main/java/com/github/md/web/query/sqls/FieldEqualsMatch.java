package com.github.md.web.query.sqls;

import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.MetaSqlKit;

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

//        //boolean 转义
//        if (metaField.dbType().isLooseBoolean(metaField.dbTypeLength().intValue())) {
//            value = MetaDataTypeConvert.convert(metaField, value);
//        }

        if (httpParams.get(metaField.en()) != null) {
            values.put(SQL_PREFIX + MetaSqlKit.discernColumns(metaField.fieldCode()) + "=?", value);
        }
    }

    @Override
    public Map<String, Object> result() {
        return values;
    }
}
