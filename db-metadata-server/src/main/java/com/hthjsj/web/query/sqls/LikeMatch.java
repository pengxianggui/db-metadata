package com.hthjsj.web.query.sqls;

import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaSqlKit;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p> @Date : 2019/10/28 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class LikeMatch extends MetaSQLExtract {

    /***
     * 模糊规则:
     * field_lk_l=123
     * field_lk_r=131
     * field_lk=231
     */
    public static final String SUFFIX_LIKE = "_lk";

    public static final String SUFFIX_LIKE_RIGHT = "_lk_r";

    public static final String SUFFIX_LIKE_LEFT = "_lk_l";

    public static final String LIKE = " like concat('%',?,'%')";

    public static final String LIKE_LEFT = " like concat('%',?)";

    public static final String LIKE_RIGHT = " like concat(?,'%')";

    Map<String, Object> values = new HashMap<>();

    @Override
    public void init(IMetaField metaField, Map<String, Object> httpParams) {
        Optional<Object> value = Optional.ofNullable(httpParams.get(metaField.fieldCode() + SUFFIX_LIKE));
        String fieldCode = MetaSqlKit.discernColumns(metaField.fieldCode());
        if (value.isPresent()) {
            values.put(SQL_PREFIX + fieldCode + LIKE, value.get());
        }
        value = Optional.ofNullable(httpParams.get(metaField.fieldCode() + SUFFIX_LIKE_RIGHT));
        if (value.isPresent()) {
            values.put(SQL_PREFIX + fieldCode + LIKE_RIGHT, value.get());
        }
        value = Optional.ofNullable(httpParams.get(metaField.fieldCode() + SUFFIX_LIKE_LEFT));
        if (value.isPresent()) {
            values.put(SQL_PREFIX + fieldCode + LIKE_LEFT, value.get());
        }
    }

    @Override
    public Map<String, Object> result() {
        return values;
    }
}
