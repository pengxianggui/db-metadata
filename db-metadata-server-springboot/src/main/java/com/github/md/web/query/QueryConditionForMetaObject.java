package com.github.md.web.query;

import com.github.md.web.jfinal.SqlParaExt;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.MetaSqlKit;
import com.github.md.web.kit.UtilKit;
import com.github.md.web.query.sqls.MetaSQLExtract;
import com.github.md.analysis.kit.Kv;
import com.github.md.web.kit.Okv;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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
public class QueryConditionForMetaObject implements IQueryCondition {

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
    /**
     * <p>
     * 过滤字段 -> 根据解析规则 拆解 httpparams -> 构建SqlExtPara
     * Map 结构
     * id=?,v_id
     * name>=?,v_name
     * <p>
     * Call:
     * Db.find(select * from tableA where id=? and name >=?,new String[]{v_id,v_name})
     */
    private final IMetaObject metaObject;

    private final Collection<IMetaField> metaFields;

    public QueryConditionForMetaObject(IMetaObject metaObject, Collection<IMetaField> filtered) {
        this.metaObject = metaObject;
        if (filtered == null || filtered.isEmpty()) {
            this.metaFields = metaObject.fields();
        } else {
            this.metaFields = filtered;
        }
    }

    @Override
    public SqlParaExt resolve(Map<String, String[]> httpParams, String[] fields, String[] efields) {
        Map<String, Object> params = UtilKit.toObjectFlat(httpParams);
        SqlParaExt sqlParaExt = new SqlParaExt();

        //保存 从http中传来的参数 处理后的sql片段
        Okv conds = Okv.create();
        //TODO 保存多值处理结果,SortMatch用,硬编码逻辑,需要修复
        Kv mutipleValueMap = Kv.create();
        buildSelect(sqlParaExt, metaFields);

        for (IMetaField field : metaObject.fields()) {

            for (Class<? extends MetaSQLExtract> mClass : QueryParses.me().parseter) {
                try {
                    //TODO 待优化,每个请求解析参数时,会创建大量的Extract实例,性能差
                    MetaSQLExtract metaSQLBuilder = mClass.newInstance();
                    metaSQLBuilder.init(field, params);
                    if (metaSQLBuilder.isMutiple()) {
                        mutipleValueMap.set(metaSQLBuilder.result());
                    } else {
                        conds.putAll(metaSQLBuilder.result());
                    }
                } catch (InstantiationException e) {
                    log.error(e.getMessage(), e);
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        //order by 逻辑;
        if (!mutipleValueMap.isEmpty()) {
            List<String> orderItem = Lists.newArrayList();
            // k->v : a->desc
            // k->v : b->asc
            // k->v : c->desc
            mutipleValueMap.forEach((key, value) -> {
                orderItem.add(key + " " + value);
            });
            // order by "a desc,b asc,c desc"
            conds.put(" order by ", Joiner.on(",").skipNulls().join(orderItem.toArray()));
        }
        log.debug("SQL conditions: {}\n\n\t\tmetaObject:{},fields:{},excludeFields:{}", conds, fields, efields);
        return buildExceptSelect(conds, sqlParaExt, metaObject);
    }

    public void buildSelect(SqlParaExt sqlParaExt, Collection<IMetaField> metaFields) {
        //遍历处理后的metafield
        Iterator<IMetaField> destIterator = metaFields.iterator();
        StringBuilder sqlSelect = new StringBuilder("select *");

        while (destIterator.hasNext()) {
            IMetaField metaField = destIterator.next();
            String column = MetaSqlKit.discernColumns(metaField.fieldCode());
            sqlSelect.append(",").append(column).append(" ");
        }
        sqlParaExt.setSelect(sqlSelect.toString().replaceFirst("\\*,", ""));
        log.debug("select sql:{}", sqlParaExt.getSelect());
    }

    private SqlParaExt buildExceptSelect(Okv kv, SqlParaExt sqlParaExt, IMetaObject metaObject) {
        StringBuilder sqlExceptSelect = new StringBuilder();
        Iterator iter = kv.keySet().iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();

            //IN NIN 逻辑时 value 为string[],需要将数组元素按顺序解析出来
            if (key.contains("in(")) {
                //这时的getAs取出来的是String[]数据;
                String[] vals = kv.getAs(key);
                sqlExceptSelect.append(" and ").append(key).append(" ");
                for (int i = 0; i < vals.length; i++) {
                    sqlParaExt.addPara(vals[i]);
                }
                continue;
            }

            if (key.contains("order by")) {
                String v = kv.getAs(key);
                sqlParaExt.setOrderBy(key + v);
                continue;
            }
            //正常 where 逻辑
            if (key.startsWith(MetaSQLExtract.SQL_PREFIX)) {
                sqlExceptSelect.append(" and ").append(key.replaceFirst(MetaSQLExtract.SQL_PREFIX, "")).append(" ");
                if (StrKit.notNull(kv.get(key))) {
                    sqlParaExt.addPara(kv.get(key));
                }
            }
        }
        sqlParaExt.setFrom(" from " + metaObject.tableName());
        sqlParaExt.setWhereExcept(" where 1=1 " + sqlExceptSelect);
        //hack sql, 1=1 写法会影响索引的生效,故需要处理"1=1 and"字符串,正则 非贪婪匹配第一个
        sqlParaExt.setWhereExcept(sqlParaExt.getWhereExcept().replaceFirst("1=1.*?and", ""));
        sqlParaExt.verify();
        log.debug("buildExceptSelect from sql:{}", sqlParaExt.getSql());
        return sqlParaExt;
    }
}
