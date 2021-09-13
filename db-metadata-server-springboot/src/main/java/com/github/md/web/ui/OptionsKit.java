package com.github.md.web.ui;

import com.github.md.web.ServiceManager;
import com.github.md.web.query.dynamic.CompileRuntime;
import com.github.md.web.query.dynamic.DefaultContext;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.MetaFieldConfigParse;
import com.github.md.analysis.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;

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
     *
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
     *
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
            mapped.set(r.getStr("value"), r.getStr("key"));
        });
        return mapped;
    }

    /**
     * 执行metaField 中 scopeSql 中的语句
     * 查询结果中包含 id,cn 2个字段
     *
     * @param sql
     * @param dbConfig sql执行的数据源
     *
     * @return
     */
    public static List<Kv> transKeyValueBySql(String sql, String dbConfig) {
        Preconditions.checkArgument(SqlAnalysis.check(sql), "无效的SQL配置,%s", sql);
        List<Record> optionsRecord = Db.use(dbConfig).find(sql);
        return OptionsKit.transKeyValue(optionsRecord);
    }

    /**
     * 执行metaField 中 scopeSql 中的语句
     * mapped:
     * 10000:张三
     * 10001:李四
     *
     * @param sql
     * @param dbConfig sql执行的数据源
     *
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
     *
     * @return
     */
    public static <T extends Record> List<T> trans(Collection<IMetaField> fields, List<T> dataRecords) {
        List<T> result = Lists.newArrayList(dataRecords);
        MetaFieldConfigParse configWrapper = null;

        // compileSql时, 有些是有依赖关系的。例如一级分类和二级分类, 二级分类需要根据一级分类的数据不同，而编译不同的 compileSql。
        // 因此, 对于不同的record, mapped可能是不同的
        for (T record : dataRecords) {
            //            final Kv mappeds = Kv.create();
            final Map<String, Kv> mappeds = Kv.create();

            //计算需要转义的字段的映射关系
            for (IMetaField field : fields) {
                configWrapper = field.configParser();
                if (configWrapper.hasTranslation()) {
                    if (configWrapper.isSql()) {
                        log.info("{}-{} has sql translation logic:{}", field.objectCode(), field.fieldCode(), configWrapper.isSql());
                        String dbConfig = StrKit.defaultIfBlank(configWrapper.dbConfig(), field.getParent().schemaName());
                        String compileSql = new CompileRuntime().compile(configWrapper.scopeSql(),
                                                                         ServiceManager.quickJudge().getRequest(),
                                                                         new DefaultContext(record.getColumns()));

                        Kv mapped = transIdCnFlatMapBySql(compileSql, dbConfig);
                        mappeds.put(field.fieldCode(), mapped);
                    }
                    if (configWrapper.isOptions()) {
                        Kv mapped = tranKeyValueFlatMapByArray(configWrapper.options());
                        mappeds.put(field.fieldCode(), mapped);
                    }
                } else {
                    if (field.dbType().isBoolean()) {
                        mappeds.put(field.fieldCode(), transKeyValueFlatMapByBoolean());
                    }
                }
            }

            mappeds.forEach((fieldCode, mapped) -> {
                //旧值
                Object oldVal = record.getObject(fieldCode);
                if ((oldVal instanceof String) && StrKit.notBlank((String) oldVal) && ((String) oldVal).contains(",")) { // 多值逻辑
                    String[] ss = Splitter.on(",").trimResults().omitEmptyStrings().splitToList((String) oldVal).toArray(new String[] {});
                    for (int i = 0; i < ss.length; i++) {
                        ss[i] = mapped.getStr(ss[i]);
                    }
                    record.set(fieldCode, Joiner.on(",").skipNulls().join(ss));
                } else { // 单值逻辑
                    record.set(fieldCode, mapped.get(oldVal));
                }
            });
        }

        return result;
    }
}
