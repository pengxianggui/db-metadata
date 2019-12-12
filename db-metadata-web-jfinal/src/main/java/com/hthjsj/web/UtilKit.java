package com.hthjsj.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaData;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * <p> @Date : 2019/10/31 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
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
        User user = UserThreadLocal.getUser();
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

    /**
     * 从 class path下读取json文件;
     *
     * @param fileName
     *
     * @return
     */
    public static String loadContentByFile(String fileName) {
        String result = "";
        try (InputStream fis = UtilKit.class.getClassLoader().getResourceAsStream(fileName)) {
            log.info("load  {} file", fileName);
            result = CharStreams.toString(new InputStreamReader(fis, Charsets.UTF_8));
            log.info("file length : {}", result.length());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * <pre>
     * 递归merge 两个json对象
     * 说明:
     * 从source -> merge 到 mergeMap
     * 如遇key重复, newValue指用source的内容,oldValue指用target的内容
     * </pre>
     *
     * @param mergeMap
     * @param newMap
     *
     * @return
     */
    public static Map deepMerge(Map mergeMap, Map newMap, boolean overwrite) {

        for (Object key : newMap.keySet()) {
            Object value = newMap.get(String.valueOf(key));
            if (!mergeMap.containsKey(key)) {
                // new value for "key":
                mergeMap.put(key, value);
            } else {
                // existing value for "key" - recursively deep merge:
                if (value instanceof JSONObject) {
                    JSONObject valueJson = (JSONObject) value;
                    deepMerge((Map) mergeMap.get(key), valueJson, overwrite);
                } else {
                    if (overwrite) {
                        mergeMap.merge(key, value, (oldValue, newValue) -> newValue);
                    } else {
                        mergeMap.merge(key, value, (oldValue, newValue) -> oldValue);
                    }
                }
            }
        }
        return mergeMap;
    }

    public static void diffJson(JSONObject source, JSONObject target) {
        throw new RuntimeException("not finished!");
    }

    public static String domainUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
    }

    /**
     * 对Record中的记录,列名进行重命名
     *
     * @param dataList
     * @param aliasMap
     *
     * @return
     */
    public static List<Record> aliasList(List<Record> dataList, Kv aliasMap) {
        List<Record> list = dataList;
        aliasMap.forEach((oldColumn, newColumn) -> {
            list.forEach((r) -> {
                r.set(String.valueOf(newColumn), r.get(String.valueOf(oldColumn)));
            });
        });
        return list;
    }

    /**
     * 过滤字段
     *
     * @param fields
     * @param efields
     * @param metaFields
     *
     * @return
     */
    public static Collection<IMetaField> filter(String[] fields, String[] efields, Collection<IMetaField> metaFields) {
        Collection<IMetaField> iMetaFields = new ArrayList<>(metaFields);
        //不需要过滤时,原样返回
        if (fields.length == 0 && efields.length == 0) {
            return iMetaFields;
        }
        Iterator<IMetaField> metaFieldIterator = iMetaFields.iterator();
        //important Arrays.binarySearch 必须操作有序数组,所以要对fields,efiedls排序
        Arrays.sort(fields);
        Arrays.sort(efields);
        //相同集合直接返回
        if (StrKit.notBlank(fields) && StrKit.notBlank(efields) && Arrays.equals(fields, efields)) {
            throw new WebException("显示列数组与排除列数组相同,fields:[%s];efields[%s]", Arrays.toString(fields), Arrays.toString(efields));
        }
        //过滤字段
        while (metaFieldIterator.hasNext()) {
            IMetaField metaField = metaFieldIterator.next();
            //不在指定列表中的剔除
            if (fields != null && fields.length > 0) {
                if (Arrays.binarySearch(fields, metaField.en()) < 0) {
                    metaFieldIterator.remove();
                    continue;
                }
            }
            //在排除列表中的 剔除
            if (efields != null && efields.length > 0) {
                if (Arrays.binarySearch(efields, metaField.en()) >= 0) {
                    metaFieldIterator.remove();
                    continue;
                }
            }
        }
        return iMetaFields;
    }
}
