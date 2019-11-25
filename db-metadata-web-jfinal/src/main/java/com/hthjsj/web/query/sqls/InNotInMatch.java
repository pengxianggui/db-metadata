package com.hthjsj.web.query.sqls;

import com.google.common.base.Splitter;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.web.UtilKit;
import com.jfinal.kit.StrKit;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> @Date : 2019/11/22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class InNotInMatch extends MetaSQLExtract {

    public static final String IN_SUFFIX = "_in";

    public static final String NOT_IN_SUFFIX = "_nin";

    Map<String, Object> values = new HashMap<>();

    @Override
    public void init(IMetaField metaField, Map<String, Object> httpParams) {
        String inValString = UtilKit.defaultIfBlank(String.valueOf(httpParams.get(metaField.fieldCode() + IN_SUFFIX)), "");

        String notInValString = UtilKit.defaultIfBlank(String.valueOf(httpParams.get(metaField.fieldCode() + NOT_IN_SUFFIX)), "");

        String[] notInVals = Splitter.on(",").omitEmptyStrings().splitToList(notInValString).toArray(new String[] {});
        if (notInVals.length > 0) {
            // sqlKey : "fieldAbc not in(?,?,?)"  value:["s1","s2","s3"]
            values.put(toSqlKey(metaField.fieldCode(), notInVals, false), notInVals);
        }
        String[] inVals = Splitter.on(",").omitEmptyStrings().splitToList(inValString).toArray(new String[] {});
        if (inVals.length > 0) {
            // sqlKey : "fieldAbc in(?,?,?)"  value:["s1","s2","s3"]
            values.put(toSqlKey(metaField.fieldCode(), inVals, true), inVals);
        }
    }

    public String toSqlKey(String fieldCode, String[] ss, boolean isIn) {
        StringBuilder sb = new StringBuilder();
        for (String v : ss) {
            if (StrKit.notBlank(v)) {
                sb.append(",?");
            }
        }
        String s = sb.toString().replaceFirst("(,)", "");
        return fieldCode + (isIn ? " in" : " not in") + "(" + s + ") ";
    }

    @Override
    public Map<String, Object> result() {
        return values;
    }
}
