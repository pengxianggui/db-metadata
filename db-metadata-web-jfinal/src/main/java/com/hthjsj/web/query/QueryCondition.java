package com.hthjsj.web.query;

import com.google.common.collect.Maps;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.WebException;
import com.hthjsj.web.jfinal.SqlParaExt;
import com.hthjsj.web.query.sqls.MetaSQLBuilder;
import com.jfinal.kit.Okv;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * <p> Class title: 查询条件 构造器</p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/9/24 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class QueryCondition {

    public static final String PREFIX_COL = "__COLUMN__";

    /**
     * 普通值过滤
     * 数值 : key=value
     * 字符 : key=value ( %like% )
     * 日期 : key=2019-10-10  ->
     * <p>
     * 连续区间过滤
     * 日期 : key_start={} & key_end={}
     * [ok] 数值 : key_lt={} & key_eq={}
     * 字符 :
     * <p>
     * 非连续区间过滤
     * 数值 : key_in = 1,3,4,5,6
     * 字符 : key_in = "1","2","3","4"
     */
    private Map<String, Object> toObjectFlat(Map<String, String[]> maps) {
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

    /**
     * <p>
     * FIXME :
     * http://url?ef=id,name,config&f=config 会滤出全部列
     * <p>
     * 过滤字段 -> 根据解析规则 拆解 httpparams -> 构建SqlExtPara
     */
    public SqlParaExt resolve(Map<String, String[]> httpParams, MetaObject metaObject, String[] fields, String[] efields) {
        Map<String, Object> params = toObjectFlat(httpParams);
        Collection<IMetaField> metaFields = metaObject.fields();
        SqlParaExt sqlParaExt = new SqlParaExt();

        Okv conds = Okv.create();
        Iterator<IMetaField> mfiter = metaFields.iterator();

        //important Arrays.binarySearch 必须操作有序数组,所以要对fields,efiedls排序
        Arrays.sort(fields);
        Arrays.sort(efields);
        //相同集合直接返回
        if (StrKit.notBlank(fields) && StrKit.notBlank(efields) && Arrays.equals(fields, efields)) {
            throw new WebException("显示列数组与排除列数组相同,fields:[%s];efields[%s]", Arrays.toString(fields), Arrays.toString(efields));
        }
        //过滤字段
        while (mfiter.hasNext()) {
            IMetaField metaField = mfiter.next();
            //不在指定列表中的剔除
            if (fields != null && fields.length > 0) {
                if (Arrays.binarySearch(fields, metaField.en()) < 0) {
                    mfiter.remove();
                    continue;
                }
            }
            //在排除列表中的 剔除
            if (efields != null && efields.length > 0) {
                if (Arrays.binarySearch(efields, metaField.en()) >= 0) {
                    mfiter.remove();
                    continue;
                }
            }
        }

        for (IMetaField field : metaFields) {
            String fieldCode = field.en();
            conds.set(PREFIX_COL(fieldCode), fieldCode);

            for (Class<? extends MetaSQLBuilder> mClass : QueryParses.me().parseter) {
                try {
                    MetaSQLBuilder metaSQLBuilder = mClass.newInstance();
                    metaSQLBuilder.init(field, params);
                    conds.putAll(metaSQLBuilder.result());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("SQL conditions: {}\n\n\t\tmetaObject:{},fields:{},excludeFields:{}", conds, fields, efields);
        return buildExceptSelect(conds, sqlParaExt, metaObject);
    }

    private SqlParaExt buildExceptSelect(Okv kv, SqlParaExt sqlParaExt, IMetaObject metaObject) {
        StringBuilder sqlExceptSelect = new StringBuilder();
        StringBuilder sqlSelect = new StringBuilder("select *");
        Iterator iter = kv.keySet().iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            // 构建selector
            if (key.startsWith(PREFIX_COL)) {
                sqlSelect.append(",").append(kv.get(key)).append(" ");
                continue;
            }

            //IN NIN 逻辑时 value 为string[],需要将数组元素按顺序解析出来
            if (key.indexOf("in(") >= 0) {
                //这时的getAs取出来的是String[]数据;
                String[] vals = kv.getAs(key);
                sqlExceptSelect.append(" and ").append(key).append(" ");
                for (int i = 0; i < vals.length; i++) {
                    sqlParaExt.addPara(vals[i]);
                }
                continue;
            }

            if (key.indexOf("order by") >= 0) {
                String v = kv.getAs(key);
                sqlParaExt.setOrderBy(key + v);
                continue;
            }
            //其他逻辑
            sqlExceptSelect.append(" and ").append(key).append(" ");
            sqlParaExt.addPara(kv.get(key));
        }
        sqlParaExt.setSelect(sqlSelect.toString().replaceFirst("\\*,", ""));
        sqlParaExt.setFrom(" from " + metaObject.tableName());
        sqlParaExt.setWhereExcept(" where 1=1 " + sqlExceptSelect.toString());
        sqlParaExt.verify();
        log.info(sqlParaExt.toString());
        return sqlParaExt;
    }

    private String PREFIX_COL(String key) {
        return PREFIX_COL + key;
    }
}
