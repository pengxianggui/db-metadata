package com.hthjsj.web.query;

import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.UtilKit;
import com.hthjsj.web.WebException;
import com.hthjsj.web.jfinal.SqlParaExt;
import com.hthjsj.web.query.sqls.MetaSQLExtract;
import com.jfinal.kit.Okv;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

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
    private IMetaObject metaObject;

    public QueryConditionForMetaObject(IMetaObject metaObject) {
        this.metaObject = metaObject;
    }

    @Override
    public SqlParaExt resolve(Map<String, String[]> httpParams, String[] fields, String[] efields) {
        Map<String, Object> params = UtilKit.toObjectFlat(httpParams);
        SqlParaExt sqlParaExt = new SqlParaExt();

        Okv conds = Okv.create();

        buildSelect(sqlParaExt, fields, efields, metaObject.fields());

        for (IMetaField field : metaObject.fields()) {

            for (Class<? extends MetaSQLExtract> mClass : QueryParses.me().parseter) {
                try {
                    //TODO 待优化,每个请求解析参数时,会创建大量的Extract实例,性能较差
                    MetaSQLExtract metaSQLBuilder = mClass.newInstance();
                    metaSQLBuilder.init(field, params);
                    conds.putAll(metaSQLBuilder.result());
                } catch (InstantiationException e) {
                    log.error(e.getMessage(), e);
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

        log.debug("SQL conditions: {}\n\n\t\tmetaObject:{},fields:{},excludeFields:{}", conds, fields, efields);
        return buildExceptSelect(conds, sqlParaExt, metaObject);
    }

    public void buildSelect(SqlParaExt sqlParaExt, String[] fields, String[] efields, Collection<IMetaField> metaFields) {
        Collection<IMetaField> iMetaFields = new ArrayList<>(metaFields);
        Iterator<IMetaField> mfiter = iMetaFields.iterator();
        Okv kv = Okv.create();
        //important Arrays.binarySearch 必须操作有序数组,所以要对fields,efiedls排序
        Arrays.sort(fields);
        Arrays.sort(efields);
        //相同集合直接返回
        if (StrKit.notBlank(fields) && StrKit.notBlank(efields) && Arrays.equals(fields, efields)) {
            throw new WebException("显示列数组与排除列数组相同,fields:[%s];efields[%s]", Arrays.toString(fields), Arrays.toString(efields));
        }

        StringBuilder sqlSelect = new StringBuilder("select *");
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
            sqlSelect.append(",").append(metaField.en()).append(" ");
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
        sqlParaExt.setWhereExcept(" where 1=1 " + sqlExceptSelect.toString());
        //hack sql, 1=1 写法会影响索引的生效,故需要处理"1=1 and"字符串,正则 非贪婪匹配第一个
        sqlParaExt.setWhereExcept(sqlParaExt.getWhereExcept().replaceFirst("1=1.*?and", ""));
        sqlParaExt.verify();
        log.debug("buildExceptSelect from sql:{}", sqlParaExt.getSql());
        return sqlParaExt;
    }
}
