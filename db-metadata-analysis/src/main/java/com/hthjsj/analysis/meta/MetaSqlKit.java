package com.hthjsj.analysis.meta;

import com.alibaba.druid.sql.builder.SQLSelectBuilder;
import com.alibaba.druid.sql.builder.impl.SQLSelectBuilderImpl;
import com.alibaba.druid.util.JdbcConstants;
import com.jfinal.kit.StrKit;

/**
 * <p> @Date : 2020/1/6 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaSqlKit {

    /**
     * 追加where条件
     */
    public static String where(String fullSql, IMetaObject metaObject) {
        return where(fullSql, metaObject.configParser().where(), metaObject.configParser().orderBy());
    }

    /**
     * 追加where条件
     *
     * @param fullSql       1. select id,cn from meta_object
     *                      2. select id,cn from meta_object where a=b
     * @param appendWhere   1. where c=d
     *                      2. c=d
     * @param appendOrderby a asc,b esc
     *
     * @return
     */
    public static String where(String fullSql, String appendWhere, String appendOrderby) {
        SQLSelectBuilder sqlSelectBuilder = new SQLSelectBuilderImpl(fullSql, JdbcConstants.MYSQL);
        if (StrKit.notBlank(appendWhere)) {
            sqlSelectBuilder.whereAnd(appendWhere);
        }
        if (StrKit.notBlank(appendOrderby)) {
            for (String s : appendOrderby.split(",")) {
                sqlSelectBuilder.orderBy(s);
            }
        }
        return " from " + sqlSelectBuilder.toString().split("(?i)from")[1];
    }
}
