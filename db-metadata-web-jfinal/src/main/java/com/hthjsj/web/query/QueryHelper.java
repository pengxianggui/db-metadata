package com.hthjsj.web.query;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.hthjsj.analysis.component.ComponentType;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/15 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class QueryHelper {

    private Controller tp;

    private Kv params = Kv.create();

    public QueryHelper(Controller controller) {
        tp = controller;
    }

    /**
     * 参数构建器
     *
     * @return
     */
    public static QueryHelper queryBuilder() {
        return new QueryHelper(null);
    }

    public String getObjectCode() {
        return getObjectCode("");
    }

    public String getObjectCode(String defaultCode) {
        return tp.getPara(0, tp.getPara("objectCode", defaultCode));
    }

    public String getFeatureCode() {
        return tp.getPara("featureCode", tp.getPara("fc"));
    }

    public String getFeatureType() {
        return tp.getPara("featureType", tp.getPara("ft"));
    }

    public String getFieldCode() {
        return tp.getPara("f", tp.getPara("fieldCode"));
    }

    public Integer getPageSize() {
        return tp.getInt("s", tp.getInt("pageSize", 20));
    }

    public Integer getPageIndex() {
        return tp.getInt("p", tp.getInt("pageIndex", 1));
    }

    public String getComponentCode() {
        return tp.getPara("compCode", tp.getPara("componentCode"));
    }

    public ComponentType getComponentType() {
        return ComponentType.V(tp.getPara("compType", tp.getPara("componentType")));
    }

    public String[] getObjectCodes() {
        return Splitter.on(",").trimResults().omitEmptyStrings().splitToList(getObjectCode()).toArray(new String[0]);
    }

    public QueryHelper builder(String key, String value) {
        params.setIfNotBlank(key, value);
        return this;
    }

    public String buildQueryString(boolean questionMark) {
        StringBuilder sb = new StringBuilder();
        if (questionMark) {
            sb.append("?");
        }
        List<String> ss = Lists.newArrayList();
        params.forEach((key, value) -> {
            ss.add(key + "=" + value);
        });
        return Joiner.on("&").appendTo(sb, ss).toString();
    }

    public Object[] getPks(String primaryKey) {
        List<String> values = new ArrayList<>();
        if (StrKit.notBlank(primaryKey)) {
            String[] keys = primaryKey.split(",");
            for (String key : keys) {
                if (StrKit.notBlank(key)) {
                    values.add(tp.get(key));
                }
            }
        }
        return values.toArray();
    }
}
