package com.hthjsj.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import com.hthjsj.analysis.meta.MetaData;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p> @Date : 2019/10/31 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UtilKit {

    /**
     * HttpServletRequest 中 request.getParameterMap 类型为Map<String, String[]>,计算时需要判断String[] 和String
     * 为了方便存取,统一转换为Map<String, Object> 格式;
     *
     * @param maps
     *
     * @return
     */
    public static Map<String, Object> toObjectFlat(Map<String, String[]> maps) {
        Map<String, Object> result = Maps.newHashMap();
        for (Map.Entry<String, String[]> e : maps.entrySet()) {
            String[] values = e.getValue();
            if (values.length == 1) {
                result.put(e.getKey(), values[0]);
            } else {
                result.put(e.getKey(), values);
            }
        }
        return result;
    }

    public static Kv getKv(Kv kv, String key) {
        Kv result = JSON.parseObject(kv.getStr(key), Kv.class);
        return result != null ? result : Kv.create();
    }

    public static List<Kv> getKvs(Kv kv, String key) {
        return getKvs(kv.getStr(key));
    }

    public static List<Kv> getKvs(String jsonArray) {
        return JSONArray.parseArray(jsonArray, Kv.class);
    }

    public static Kv getKv(String json) {
        return JSON.parseObject(json, Kv.class);
    }

    public static void setUser(MetaData data) {
        User user = ThreadLocalUserKit.getUser();
        data.set("created_by", user.userId());
        data.set("created_time", new Date());
    }

    public static String defaultIfBlank(String str, String defaultValue) {
        if (!StrKit.isBlank(str)) {
            if (str.equalsIgnoreCase("null")) {
                return defaultValue;
            }
        }
        return StrKit.isBlank(str) ? defaultValue : str;
    }

    /**
     * 合并两个Map,Key重复时不合并
     *
     * @param mergeMap 等待合并的对象
     * @param newMap
     *
     * @return
     */
    public static Kv mergeUseOld(Kv mergeMap, Kv newMap) {
        newMap.forEach((k, v) -> mergeMap.merge(k, v, (oldValue, newValue) -> oldValue));
        return mergeMap;
    }

    /**
     * 合并两个Map,Key重复时执行覆盖操作
     *
     * @param mergeMap
     * @param newMap
     *
     * @return
     */
    public static Kv mergeUseNew(Kv mergeMap, Kv newMap) {
        newMap.forEach((k, v) -> mergeMap.merge(k, v, (oldValue, newValue) -> newValue));
        return mergeMap;
    }
}
