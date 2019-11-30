package com.hthjsj.web.query.sqls;

import com.hthjsj.analysis.meta.IMetaField;
import com.jfinal.kit.StrKit;

import java.util.HashMap;
import java.util.Map;

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
        Object value = httpParams.get(metaField.fieldCode() + SUFFIX_LIKE);
        if (StrKit.notNull(value)) {
            values.put(SQL_PREFIX + metaField.fieldCode() + LIKE, value);
        }
        value = httpParams.get(metaField.fieldCode() + SUFFIX_LIKE_RIGHT);
        if (StrKit.notNull(value)) {
            values.put(SQL_PREFIX + metaField.fieldCode() + LIKE_RIGHT, value);
        }
        value = httpParams.get(metaField.fieldCode() + SUFFIX_LIKE_LEFT);
        if (StrKit.notNull(value)) {
            values.put(SQL_PREFIX + metaField.fieldCode() + LIKE_LEFT, value);
        }
    }

    @Override
    public Map<String, Object> result() {
        return values;
    }
}
