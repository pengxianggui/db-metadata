package com.hthjsj.web.query;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.IMetaObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Okv;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    /**
     * 前后端id传输,用"id"作为key
     * 复合主键处理
     * id=pk1_v1,pk2_v2&id=pk1_v1,pk2_v2
     * id=1&id=2
     *
     * @param metaObject
     * @param defaultKey
     *
     * @return [[v1, v2], [v1, v2]]
     * [v1]
     */
    public Object[] getPks(IMetaObject metaObject, String defaultKey) {
        List<Object[]> pks = new ArrayList<>();
        if (metaObject.isMultiplePrimaryKey()) {
            //pk1,pk2
            String[] pkkeys = metaObject.primaryKey().split(",");
            //["pk1_v1,pk2_v2","pk1_v1,pk2_v2"]
            String[] vals = tp.getParaValues("id");

            Set<Object> results = Sets.newLinkedHashSet();
            for (String val : vals) {
                results.clear();
                Okv pksmap = resolvePk(val);
                for (String key : pkkeys) {
                    results.add(pksmap.getStr(key));
                }
                pks.add(results.toArray());
            }
            //result : [[v1,v2],[v1,v2]]
        } else {
            pks.add(new Object[] { tp.get(metaObject.primaryKey()) });
        }
        return pks.toArray();
    }

    /**
     * 拆解一条复合主键
     *
     * @param value "id=pk1_v1,pk2_v2"
     *
     * @return {pk1:v1,pk2:v2}
     */
    private Okv resolvePk(String value) {
        String[] ss = value.split(",");
        Okv params = Okv.create();
        for (String s : ss) {
            params.set(s.split("_")[0], s.split("_")[1]);
        }
        return params;
    }
}
