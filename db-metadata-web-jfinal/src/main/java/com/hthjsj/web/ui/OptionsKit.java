package com.hthjsj.web.ui;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.hthjsj.analysis.meta.FieldConfigWrapper;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.web.UtilKit;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

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
     *
     * @return
     */
    public static List<Kv> transKeyValueBySql(String sql) {
        Preconditions.checkArgument(UtilKit.verifySQL(sql), "无效的SQL配置,%s", sql);
        List<Record> optionsRecord = Db.find(sql);
        return OptionsKit.transKeyValue(optionsRecord);
    }

    /**
     * 执行metaField 中 scopeSql 中的语句
     * mapped:
     * 10000:张三
     * 10001:李四
     *
     * @param sql
     *
     * @return
     */
    public static Kv transIdCnFlatMapBySql(String sql) {
        Preconditions.checkArgument(UtilKit.verifySQL(sql), "无效的SQL配置,%s", sql);
        Kv mapped = Kv.create();
        List<Record> optionsRecord = Db.find(sql);
        optionsRecord.forEach(r -> {
            mapped.set(r.getStr("id"), r.getStr("cn"));
        });
        return mapped;
    }

    public static String buildUrl(String objectCode, String fieldCode) {
        return new StringBuilder("/component/options/").append(objectCode).append("?f=").append(fieldCode).toString();
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
     *   2. 将需要转义的字段 -> mappeds 存放
     *   3. 遍历数据列表 用值->从mappeds中反取转义后的内容
     * </pre>
     *
     * @param fields
     * @param dataRecords
     *
     * @return
     */
    public static List<Record> trans(Collection<IMetaField> fields, List<Record> dataRecords) {
        List<Record> result = Lists.newArrayList(dataRecords);
        FieldConfigWrapper configWrapper = null;
        Kv mappeds = Kv.create();
        //计算需要转义的字段的映射关系
        for (IMetaField field : fields) {
            configWrapper = new FieldConfigWrapper(field.config());
            if (configWrapper.hasTranslation()) {
                if (configWrapper.isSql()) {
                    Kv mapped = transIdCnFlatMapBySql(configWrapper.sourceSql());
                    mappeds.set(field.fieldCode(), mapped);
                }
                if (configWrapper.isOptions()) {
                    Kv mapped = tranKeyValueFlatMapByArray(configWrapper.options());
                    mappeds.set(field.fieldCode(), mapped);
                }
            }
        }
        //转义数据
        for (Record record : dataRecords) {
            mappeds.forEach((key, value) -> {
                //旧值
                String oldVal = record.getStr((String) key);
                record.set((String) key, ((Kv) value).getStr(oldVal));
            });
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(transKeyValue(new String[] { "123", "12312", "fdsa" }));
    }
}
