package com.hthjsj.web.kit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.web.WebException;
import com.hthjsj.web.user.User;
import com.hthjsj.web.user.UserThreadLocal;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.server.undertow.PathKitExt;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static void setUser(Map data) {
        User user = UserThreadLocal.getUser();
        data.put("created_by", user.userId());
        data.put("created_time", new Date());
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
    public static String loadContentInCurrentJar(String fileName) {
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

    public static String loadConfigByFile(String fileName) {
        return stairsLoad(fileName, "config");
    }

    /**
     * load 执行jar包所在目录下逻辑
     * 读取顺序 : fileName -> /{defDirectory}/fileName -> jar包内部
     * /fileName
     * /{defDirectory}/fileName
     *
     * @param fileName         文件名
     * @param defaultDirectory fileName读取失败后,再使用defaultDirectory目录继续读取
     *
     * @return
     */
    public static String stairsLoad(String fileName, String defaultDirectory) {

        /**
         * 路径样本:
         *      PathKit.getRootClassPath():/Users/konbluesky/work/db-meta-serve/db-metadata-web-jfinal/aaa/config
         *      PathKit.getWebRootPath():/Users/konbluesky/work/db-meta-serve/db-metadata-web-jfinal/aaa/webapp
         *      PathKitExt.getWebRootPath():/Users/konbluesky/work/db-meta-serve/db-metadata-web-jfinal/aaa/webapp
         *      PathKitExt.getLocationPath():/Users/konbluesky/work/db-meta-serve/db-metadata-web-jfinal/aaa
         *      PathKitExt.getRootClassPath():/Users/konbluesky/work/db-meta-serve/db-metadata-web-jfinal/aaa/config
         */
        String locationPath = PathKitExt.getLocationPath();
        // /fileName
        {
            try {
                String destFilePath = Joiner.on(File.separator).join(locationPath, fileName);
                File f = new File(destFilePath);
                if (f.exists()) {
                    log.info("load success file content in {}", destFilePath);
                    return Joiner.on("").join(Files.readLines(f, Charsets.UTF_8));
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

        // /config/fileName
        {
            try {
                String destFilePath = Joiner.on(File.separator).join(locationPath, defaultDirectory, fileName);
                File f = new File(destFilePath);
                if (f.exists()) {
                    log.info("load success file content in {}", destFilePath);
                    return Joiner.on("").join(Files.readLines(f, Charsets.UTF_8));
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

        //classpath:filename
        return loadContentInCurrentJar(fileName);
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
                //对列重命名
                r.set(String.valueOf(newColumn), r.get(String.valueOf(oldColumn)));
                //删除旧名列
                r.remove(String.valueOf(oldColumn));
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

    /**
     * 获取匹配参数值
     *
     * @param regex 正则表达式
     * @param str   目标字符串
     * @param flags 匹配模式
     *              Pattern.CASE_INSENSITIVE忽略大小写
     *
     * @return
     */
    public static String[] getMatcherValue(String regex, String str, int flags) {
        Pattern pat = null;
        if (flags == -1) {
            pat = Pattern.compile(regex);
        } else {
            pat = Pattern.compile(regex, flags);
        }
        Matcher mat = pat.matcher(str);
        if (mat.find()) {
            String[] param = new String[mat.groupCount()];
            for (int i = 0; i < mat.groupCount(); i++) {
                param[i] = mat.group(i + 1);
            }
            return param;
        }
        return null;
    }
}
