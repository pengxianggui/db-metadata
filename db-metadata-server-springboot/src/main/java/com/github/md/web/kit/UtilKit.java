package com.github.md.web.kit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.AppConst;
import com.github.md.web.WebException;
import com.github.md.web.kit.tree.TreeNode;
import com.github.md.web.user.User;
import com.github.md.web.user.UserThreadLocal;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.system.ApplicationHome;

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
        if (!kv.containsKey(key)) {
            return Kv.create();
        }

        if (kv.get(key) instanceof Kv) {
            return (Kv) kv.get(key);
        }

        Kv result = JSON.parseObject(kv.getStr(key), Kv.class);
        return result != null ? result : Kv.create();
    }

    public static Kv getKv(Okv kv, String key) {
        Kv result = JSON.parseObject(kv.getStr(key), Kv.class);
        return result != null ? result : Kv.create();
    }

    public static Okv getOKv(Kv kv, String key) {
        Okv result = JSON.parseObject(kv.getStr(key), Okv.class);
        return result != null ? result : Okv.create();
    }

    public static List<Kv> getKvs(Kv kvSource, String key) {
        return getKvs(kvSource.getStr(key));
    }

    public static List<Kv> getKvs(String jsonArray) {
        return JSONArray.parseArray(jsonArray, Kv.class);
    }

    public static Kv getKv(String json) {
        if (StrKit.isBlank(json)) {
            return Kv.create();
        }
        return JSON.parseObject(json, Kv.class);
    }

    public static void setCreateUser(Map data) {
        User user = UserThreadLocal.getUser();
        if (user != null) {
            data.put("created_by", user.userId());
            data.put("created_time", new Date());
        } else {
            data.put("created_by", AppConst.UN_KNOW_USERNAME);
            data.put("created_time", new Date());
        }
    }

    public static void setUpdateUser(Map data) {
        User user = UserThreadLocal.getUser();
        if (user != null) {
            data.put("updated_by", user.userId());
            data.put("updated_time", new Date());
        } else {
            data.put("created_by", AppConst.UN_KNOW_USERNAME);
            data.put("created_time", new Date());
        }
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
     * @return
     */
    public static Kv mergeUseNew(Kv mergeMap, Kv newMap) {
        deepMerge(mergeMap, newMap, true);
        return mergeMap;
    }

    /**
     * 从 class path下读取json文件;
     *
     * @param fileName
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
        File file = stairsLoad(fileName, "config");
        if (file == null) {
            return loadContentInCurrentJar(fileName);
        }
        return loadFileString(file);
    }

    private static String loadFileString(File file) {
        try {
            return Joiner.on("").join(Files.readLines(file, Charsets.UTF_8));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * load 执行jar包所在目录下逻辑
     * 读取顺序 : fileName -> /{defDirectory}/fileName -> jar包内部
     * /fileName
     * /{defDirectory}/fileName
     *
     * @param fileName         文件名
     * @param defaultDirectory fileName读取失败后,再使用defaultDirectory目录继续读取
     * @return File
     */
    public static File stairsLoad(String fileName, String... defaultDirectory) {

        /**
         * 路径样本:
         *      PathKit.getRootClassPath():/Users/konbluesky/work/db-meta-serve/db-metadata-web-jfinal/aaa/config
         *      PathKit.getWebRootPath():/Users/konbluesky/work/db-meta-serve/db-metadata-web-jfinal/aaa/webapp
         *      PathKitExt.getWebRootPath():/Users/konbluesky/work/db-meta-serve/db-metadata-web-jfinal/aaa/webapp
         *      PathKitExt.getLocationPath():/Users/konbluesky/work/db-meta-serve/db-metadata-web-jfinal/aaa
         *      PathKitExt.getRootClassPath():/Users/konbluesky/work/db-meta-serve/db-metadata-web-jfinal/aaa/config
         *
         * SpringBoot ApplicationHome:
         *  applicationHome.getSource().toString()  : /Users/konbluesky/work/spring-boot-snippets/target/spring-boot-snippets-0.0.1-SNAPSHOT.jar
         *  applicationHome.getDir().toString()     : /Users/konbluesky/work/spring-boot-snippets/target
         */
        String locationPath = new ApplicationHome().getDir().toString();
        //fileName
        {
            String destFilePath = Joiner.on(File.separator).join(locationPath, fileName);
            File f = new File(destFilePath);
            if (f.exists()) {
                log.info("Load success file content in {}", destFilePath);
                return f;
            }
        }
        // /config/fileName
        for (String d : defaultDirectory) {
            String destFilePath = Joiner.on(File.separator).join(locationPath, d, fileName);
            File f = new File(destFilePath);
            if (f.exists()) {
                log.info("load success file content in {}", destFilePath);
                return f;
            }
        }
        //classpath:filename
        return null;
    }

    /**
     * <pre>
     * 递归merge 两个json对象
     * 说明:
     * 从 newMap -> merge 到 mergeMap
     *
     * 若key不重复，则newMap往mergeMap合并; 若key重复，则视第三个参数而定。
     *
     * </pre>
     *
     * @param mergeMap
     * @param newMap
     * @param overwrite 若为true, 当两个map都有某个属性时，则会以后者向前者覆盖。为false, 则不覆盖，保留前者
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
                if (value instanceof Map) {
                    Map newValueJson = (Map) value;
                    JSONObject oldValueJson = toJSONObject(mergeMap.get(key));
                    deepMerge(oldValueJson, newValueJson, overwrite);
                } else {
                    if (overwrite) {
                        if (value == null) {
                            mergeMap.put(key, null);
                            continue;
                        }
                        mergeMap.merge(key, value, (oldValue, newValue) -> {
                            //                            StrKit.defaultIfBlank(String.valueOf(newValue), String.valueOf(oldValue))
                            if (newValue != null && StrKit.notBlank(String.valueOf(newValue))) {
                                return newValue;
                            }
                            if (oldValue != null && StrKit.notBlank(String.valueOf(oldValue))) {
                                return oldValue;
                            }
                            return newValue;
                        });
                    } else {
                        if (value == null) {
                            continue;
                        }
                        mergeMap.merge(key, value, (oldValue, newValue) -> oldValue);
                    }
                }
            }
        }
        return mergeMap;
    }

    /**
     * 将一个对象转为JSONObject。
     *
     * <pre>
     * 若已为JSONObject类型, 直接返回;
     * 若为字符串类型, 为空则返回空JSONObject，否则尝试解析为JSONObject，失败可能抛出异常;
     * 若为其他类型, 则尝试先转为JSON 格式的String, 再转为JSONObject，失败可能抛出异常
     * </pre>
     * <p>
     * 可能抛出类型转换异常。
     *
     * @param o
     * @return
     */
    public static JSONObject toJSONObject(Object o) {
        if (o instanceof JSONObject) {
            return (JSONObject) o;
        }

        if (o instanceof String) {
            if (StrKit.isBlank((String) o)) {
                return new JSONObject();
            }
            return JSONObject.parseObject((String) o);
        }

        return JSONObject.parseObject(JSONObject.toJSONString(o));
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

    public static List<TreeNode<String, Record>> aliasTreeList(List<TreeNode<String, Record>> dataList, Kv aliasMap) {
        List<TreeNode<String, Record>> result = dataList;
        dataList.forEach(tree -> aliasTreeNode(tree, aliasMap));
        return result;
    }

    public static TreeNode<String, Record> aliasTreeNode(TreeNode<String, Record> tree, Kv aliasMap) {
        // TODO
        return tree;
    }

    /**
     * 过滤字段
     *
     * @param fields
     * @param efields
     * @param metaFields
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
