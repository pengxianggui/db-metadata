package com.github.md.web.ui;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.MetaFieldConfigParse;
import com.github.md.web.Server;
import com.github.md.web.ServiceManager;
import com.github.md.web.ex.WebException;
import com.github.md.web.kit.Dicts;
import com.github.md.web.query.dynamic.CompileRuntime;
import com.github.md.web.query.dynamic.DefaultContext;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p> @Date : 2019/11/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class OptionsKit {

    /**
     * 前端直接能用的value,key
     *
     * @param values
     * @return
     */
    public static List<Kv> transKeyValue(String[] values) {
        List<Kv> kvs = Lists.newArrayList();
        for (String value : values) {
            kvs.add(Kv.by("key", value).set("value", value));
        }
        return kvs;
    }

    public static Kv transKeyValueFlatMapByBoolean() {
        return Kv.by("1", "是").set("0", "否").set(true, "是").set(false, "否");
    }

    /**
     * Record 为 "select x id,xx cn from xxx" 查询结果
     * 即:必须包含ID,cn 列
     * id,cn -> value,key
     *
     * @param records
     * @return
     */
    public static List<Kv> transKeyValue(List<Record> records) {
        List<Kv> result = Lists.newArrayList();
        for (Record record : records) {
            if (record.getColumns().containsKey("id") && record.getColumns().containsKey("cn")) {
                log.debug("id-{},cn-{}", record.getStr("id"), record.getStr("cn"));
                result.add(Kv.by("value", record.getStr("id")).set("key", record.getStr("cn")));
            }
        }
        return result;
    }

    public static Kv tranKeyValueFlatMapByArray(List<Kv> jsonArray) {
        Kv mapped = Kv.create();
        jsonArray.forEach(r -> {
            mapped.set(r.get("value"), r.get("key"));
        });
        return mapped;
    }

    /**
     * 执行metaField 中 scopeSql 中的语句
     * 查询结果中包含 id,cn 2个字段
     *
     * @param sql
     * @param dbConfig sql执行的数据源
     * @return
     */
    public static List<Kv> transKeyValueBySql(String sql, String dbConfig) {
        Preconditions.checkArgument(SqlAnalysis.check(sql), "无效的SQL配置,%s", sql);
        List<Record> optionsRecord = Db.use(dbConfig).find(sql);
        return OptionsKit.transKeyValue(optionsRecord);
    }

    /**
     * 执行metaField中scopeApi中的url请求，将结果包装成Kv列表返回
     *
     * @param url
     * @return
     * @throws WebException 若请求失败，或响应数据格式不符合要求，则抛出异常
     */
    public static List<Kv> transKeyValueByUrl(String url) {
        if (!url.matches("^https?://.+$")) { // 相对路径，视为本地接口访问
            url = URLUtil.completeUrl(Server.getUrl(), url);
        }

        List<Kv> options = Lists.newArrayList();
        String res;
        try {
            res = HttpUtil.get(url, 20000);
        } catch (Exception e) {
            log.error("url请求失败。 url: {}, errMsg: {}", url, e.getMessage());
            throw new HttpException("接口请求失败, url: %s, errMsg: %s", url, e.getMessage());
        }

        try {
            JSONObject resJson = JSONObject.parseObject(res);
            JSONArray data = resJson.getJSONArray("data");
            for (Object option : data) {
                JSONObject optionObj = JSONObject.parseObject(JSONObject.toJSONString(option));
                Assert.isTrue(optionObj.containsKey("key") && optionObj.containsKey("value"), "data数组中元素对象必须同时含有key和value");
                options.add(Kv.create().set(optionObj));
            }

            boolean valid = data.stream().allMatch(option -> {
                JSONObject optionObj = JSONObject.parseObject(JSONObject.toJSONString(option));
                return optionObj.containsKey("key") && optionObj.containsKey("value");
            });
            Assert.isTrue(valid, "data数组中元素必须同时含有key和value");

            return options;
        } catch (Exception e) {
            log.error("url响应数据格式不符合要求。 url: {}, 响应数据: {}, errMsg: {}", url, res, e.getMessage());
            throw new HttpException("url响应数据格式不符合要求, 响应数据格式不符合要求。 url: %s, 响应数据: %s, errMsg: %s", url, res, e.getMessage());
        }
    }

    /**
     * 执行metaField 中 scopeSql 中的语句
     * mapped:
     * 10000:张三
     * 10001:李四
     *
     * @param sql
     * @param dbConfig sql执行的数据源
     * @return
     */
    public static Kv transIdCnFlatMapBySql(String sql, String dbConfig) {
        Preconditions.checkArgument(SqlAnalysis.check(sql), "无效的SQL配置,%s", sql);
        Kv mapped = Kv.create();
        List<Record> optionsRecord = Db.use(dbConfig).find(sql);
        optionsRecord.forEach(r -> {
            mapped.set(r.getStr("id"), r.getStr("cn"));
        });
        return mapped;
    }

    public static String buildUrl(String objectCode, String fieldCode) {
        return new StringBuilder("/component/options").append("?objectCode=").append(objectCode).append("&f=").append(fieldCode).toString();
    }

    /**
     * <pre>
     * mapped:
     *      10000:张三
     *      10001:李四
     * mappeds:
     *      name:{10000:张三,10001:李四}
     *
     * 处理逻辑:
     *   1. 遍历元子段,处理转义逻辑
     *      1.1 处理 sql 查询
     *      1.2 处理静态数组
     *   2. 遍历数据列表 用值->从mappeds中反取转义后的内容
     *      2.1 多值逻辑
     *      2.2 单值逻辑
     * </pre>
     *
     * @param fields
     * @param dataRecords
     * @return
     */
    public static <T extends Record> List<T> trans(Collection<IMetaField> fields, List<T> dataRecords) {
        List<T> result = Lists.newArrayList(dataRecords);
        MetaFieldConfigParse configWrapper = null;

        // compileSql时, 有些是有依赖关系的。例如一级分类和二级分类, 二级分类需要根据一级分类的数据不同，而编译不同的 compileSql。
        // 因此, 对于不同的record, mapped可能是不同的
        for (T record : dataRecords) {
            //            final Kv mappeds = Kv.create();
            final Map<IMetaField, Kv> mappeds = Kv.create();

            //计算需要转义的字段的映射关系
            for (IMetaField field : fields) {
                configWrapper = field.configParser();

                if (!configWrapper.escape()) {
                    continue;
                }

                if (configWrapper.hasTranslation()) {
                    // 注意优先级
                    if (configWrapper.isOptions()) { // 静态数组
                        Kv mapped = tranKeyValueFlatMapByArray(configWrapper.options());
                        mappeds.put(field, mapped);
                    } else if (configWrapper.isDict()) { // 字典
                        List<Kv> options = Dicts.me().getKvs(configWrapper.getDictName());
                        Kv mapped = tranKeyValueFlatMapByArray(options);
                        mappeds.put(field, mapped);
                    } else if (configWrapper.isUrl()) { // 接口数据
                        try {
                            List<Kv> options = transKeyValueByUrl(configWrapper.scopeUrl());
                            Kv mapped = tranKeyValueFlatMapByArray(options);
                            mappeds.put(field, mapped);
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                        }
                    } else if (configWrapper.isSql()) { // sql数据
                        log.info("{}-{} has sql translation logic:{}", field.objectCode(), field.fieldCode(), configWrapper.isSql());
                        String dbConfig = StrKit.defaultIfBlank(configWrapper.dbConfig(), field.getParent().schemaName());
                        String compileSql = new CompileRuntime().compile(configWrapper.scopeSql(),
                                ServiceManager.quickJudge().getRequest(),
                                new DefaultContext(record.getColumns()));

                        Kv mapped = transIdCnFlatMapBySql(compileSql, dbConfig);
                        mappeds.put(field, mapped);
                    }
                } else {
                    if (field.dbType().isBoolean()) {
                        mappeds.put(field, transKeyValueFlatMapByBoolean());
                    }
                }
            }

            mappeds.forEach((field, mapped) -> {
                Object oldVal = record.getObject(field.fieldCode()); // 旧值

                if ((oldVal instanceof String) && StrKit.notBlank((String) oldVal)
                        && field.configParser().isMultiple() && ((String) oldVal).contains(",")) { // 多值逻辑
                    String[] ss = Splitter.on(",").trimResults().omitEmptyStrings().splitToList((String) oldVal).toArray(new String[]{});
                    for (int i = 0; i < ss.length; i++) {
                        String newV = mapped.getStr(ss[i]);
                        ss[i] = StrKit.defaultIfBlank(newV, ss[i]);
                    }
                    record.set(field.fieldCode(), Joiner.on(",").skipNulls().join(ss));
                } else { // 单值逻辑
                    Object newVal = mapped.get(oldVal);
                    record.set(field.fieldCode(), newVal != null ? newVal : oldVal);
                }
            });
        }

        return result;
    }
}
