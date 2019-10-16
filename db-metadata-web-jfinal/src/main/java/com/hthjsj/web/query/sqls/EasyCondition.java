package com.hthjsj.web.query.sqls;

import com.hthjsj.analysis.meta.IMetaField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class EasyCondition extends MetaSQLBuilder {

    /**
     * lt （less than）               小于
     * gt （greater than）            大于
     * le （less than or equal to）   小于等于
     * ge （greater than or equal to）大于等于
     * eq （equal to）                等于
     * ne （not equal to）            不等于
     */
    public final static String SEPARATOR = "_";

    private final static List<String[]> rules = new ArrayList<String[]>();

    static {
        rules.add(new String[] { "lt", "<?" });
        rules.add(new String[] { "gt", ">?" });
        rules.add(new String[] { "le", "<=?" });
        rules.add(new String[] { "ge", ">=?" });
        rules.add(new String[] { "eq", "=?" });
        rules.add(new String[] { "ne", "<>?" });
    }

    Map<String, Object> values = new HashMap<>();

    private String buildQueryKey(String key, String suffix) {
        return key + SEPARATOR + suffix;
    }

    @Override
    public Map<String, Object> result() {
        return values;
    }

    @Override
    public void init(IMetaField metaField, Map<String, Object> httpParams) {
        for (String[] ss : rules) {
            Object v = httpParams.get(buildQueryKey(metaField.en(), ss[0]));
            if (v != null) {
                values.put(SQL_PREFIX + metaField.en() + ss[1], v);
            }
        }
    }
}
