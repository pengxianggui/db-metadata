package com.hthjsj.web.query;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.controller.ParameterHelper;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Okv;
import com.jfinal.kit.StrKit;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * @Describe: QueryHelper的目的是一个全局的统一获取系统核心参数的工具类
 * 防止Controller中散落大量的参数名
 * </p>
 * <p> @Date : 2019/10/15 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class QueryHelper {

    private final ParameterHelper tp;

    private ListHelper listHelper;

    public QueryHelper(HttpServletRequest request) {
        this.tp = new ParameterHelper(request);
    }

    public ListHelper list() {
        if (listHelper == null) {
            listHelper = new ListHelper();
        }
        return listHelper;
    }

    public QueryBuilder queryBuilder() {
        return new QueryBuilder();
    }

    public String getObjectCode() {
        return getObjectCode("");
    }

    public String getObjectCode(String defaultCode) {
        return tp.getPara("objectCode", defaultCode);
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

    /**
     * 根据元对象从Request中解析 fieldCode=fieldValue, 如果请求中包含该元对象的字段和值
     * 包装成KV返回
     *
     * @param metaObject
     *
     * @return
     */
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

    public class ListHelper {

        public String fs() {
            return tp.getPara("fs", tp.getPara("fields", ""));
        }

        public String efs() {
            return tp.getPara("efs", tp.getPara("exfields", ""));
        }

        public String[] fields() {
            return Splitter.on(",").omitEmptyStrings().trimResults().splitToList(fs()).toArray(new String[0]);
        }

        public String[] excludeFields() {
            return Splitter.on(",").omitEmptyStrings().trimResults().splitToList(efs()).toArray(new String[0]);
        }

        public boolean raw() {
            return tp.getParaToBoolean("raw", false);
        }
    }
}
