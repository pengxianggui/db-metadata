package com.hthjsj;

import com.alibaba.druid.sql.builder.impl.SQLSelectBuilderImpl;
import com.alibaba.druid.util.JdbcConstants;

/**
 * <p> @Date : 2020/1/6 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class SqlBuilderTest {

    public static void main(String[] args) {
        String sql = "select id,cn from meta_object ";
        SQLSelectBuilderImpl sqlSelectBuilder = new SQLSelectBuilderImpl(sql, JdbcConstants.MYSQL);
//        System.out.println(sqlSelectBuilder.toString());
//        sqlSelectBuilder.select("name");
//        sqlSelectBuilder.orderBy("name asc").orderBy("name asc").orderBy("name asc");
//        sqlSelectBuilder.where("name =sdf");
//        sqlSelectBuilder.where("name2 =sdf and 1=2");
//        sqlSelectBuilder.where("name3 =sdf");
        sqlSelectBuilder.whereAnd("name4 =sdf");
        sqlSelectBuilder.orderBy("1 desc ");
        sqlSelectBuilder.orderBy("2 asc");
        System.out.println(sqlSelectBuilder.toString());
        System.out.println("=====================");
        System.out.println(sqlSelectBuilder.toString().replaceFirst("SELECT", ""));
        System.out.println(sqlSelectBuilder.getSQLSelect().getFirstQueryBlock());
    }
}
