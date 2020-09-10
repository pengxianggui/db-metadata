package com.hthjsj.web.query;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Okv;
import com.jfinal.kit.StrKit;
import lombok.Data;

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

    private final Controller tp;

    private final Kv params = Kv.create();

    private ListHelper listHelper;

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

    public ListHelper list() {
        if (listHelper == null) {
            listHelper = new ListHelper(tp);
        }
        return listHelper;
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

    public String getInstanceCode() {
        return tp.getPara("ic", tp.getPara("instanceCode"));
    }

    public String getInstanceName() {
        return tp.getPara("in", tp.getPara("instanceName"));
    }

    public String[] getObjectCodes() {
        return Splitter.on(",").trimResults().omitEmptyStrings().splitToList(getObjectCode()).toArray(new String[0]);
    }

    public Kv hasMetaParams(IMetaObject metaObject) {
        Kv kv = Kv.create();
        for (IMetaField metaField : metaObject.fields()) {
            String metaFieldValue = tp.getPara(metaField.fieldCode());
            if (StrKit.notBlank(metaFieldValue)) {
                kv.putIfAbsent(metaField.fieldCode(), metaFieldValue);
            }
        }
        return kv;
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
     * 前后端id传输,用"id"作为key,
     * 复合主键处理
     * <pre>
     * 单条记录:
     *      id=pk1_v1,pk2_v2
     *      id=1
     * 多条记录:
     *      id=pk1_v1,pk2_v2&id=pk1_v1,pk2_v2
     *      id=1&id=2
     * </pre>
     *
     * @param metaObject
     *
     * @return 复合主键[[v1, v2], [v1, v2]]
     * 单主键[[v1],[v2]]
     */
    public Object[] getPks(IMetaObject metaObject) {
        List<Object[]> pks = new ArrayList<>();
        if (metaObject.isMultiplePrimaryKey()) {
            //pk1,pk2
            String[] pkkeys = metaObject.primaryKey().split(",");
            //["pk1_v1,pk2_v2","pk1_v1,pk2_v2"]
            String[] vals = tp.getParaValues("id");

            List<Object> results = Lists.newLinkedList();
            for (String val : vals) {
                results.clear();
                Okv pksmap = resolvePk(val, pkkeys);
                for (String key : pkkeys) {
                    results.add(pksmap.getStr(key));
                }
                pks.add(results.toArray());
            }
            //result : [[v1,v2],[v1,v2]]
        } else {
            for (String vs : tp.getParaValues(metaObject.primaryKey())) {
                pks.add(new Object[] { vs });
            }
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
    private Okv resolvePk(String value, String[] pkkeys) {
        String[] ss = value.split(",");
        Okv params = Okv.create();
        for (String key : pkkeys) {
            for (String s : ss) {
                if (s.startsWith(key)) {
                    params.set(key, s.replaceFirst(key + "_", ""));
                }
            }
        }
        return params;
    }

    @Data
    public class ListHelper {

        String[] fields;

        String[] excludeFields;

        boolean raw;

        Controller controller;

        public ListHelper(Controller controller) {
            this.controller = controller;
        }

        public String fs() {
            return controller.getPara("fs", controller.getPara("fields", ""));
        }

        public String efs() {
            return controller.getPara("efs", controller.getPara("exfields", ""));
        }

        public String[] fields() {
            return Splitter.on(",").omitEmptyStrings().trimResults().splitToList(fs()).toArray(new String[0]);
        }

        public String[] excludeFields() {
            return Splitter.on(",").omitEmptyStrings().trimResults().splitToList(efs()).toArray(new String[0]);
        }
    }
}
