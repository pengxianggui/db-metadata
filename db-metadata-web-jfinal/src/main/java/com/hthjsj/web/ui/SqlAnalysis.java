package com.hthjsj.web.ui;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.ParserException;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.util.JdbcConstants;
import com.google.common.base.Preconditions;
import com.hthjsj.web.WebException;
import lombok.Data;

/**
 * <p> @Date : 2019/11/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class SqlAnalysis {

    private final static SqlAnalysis me = new SqlAnalysis();

    public static SqlAnalysis me() {
        return me;
    }

    public static boolean check(String sql) {
        boolean flag = false;
        try {

            SQLUtils.parseStatements(getExecSql(sql), JdbcConstants.MYSQL);
            flag = true;
        } catch (ParserException e) {
            throw new WebException("SQL格式不正确 %s", sql);
        }
        return flag;
    }

    public static String getExecSql(String sql) {
        String s = sql;
        if (s.contains(";")) {
            s = s.split(";")[0];
        }
        return s;
    }

    public static String getSchema(String sql) {
        String s = sql;
        if (s.contains(";")) {
            s = s.split(";")[1];
        }
        return s;
    }

    public static boolean checkIdCn(String s) {
        String sql = getExecSql(s);
        SQLStatementParser sqlStatementParser = SQLParserUtils.createSQLStatementParser(sql, JdbcConstants.MYSQL);
        ParseModel parseModel = new ParseModel(sqlStatementParser.parseStatement());
        MySqlSchemaStatVisitor mySqlSchemaStatVisitor = new MySqlSchemaStatVisitor();
        parseModel.getSelectStatement().accept(mySqlSchemaStatVisitor);
        Preconditions.checkArgument(parseModel.getQuery().getSelectList().size() == 2, "该sql只允许返回2列内容");
        int i = 0;
        for (SQLSelectItem item : parseModel.getQuery().getSelectList()) {
            if ("id".equalsIgnoreCase(item.getExpr().toString()) || "id".equalsIgnoreCase(item.getAlias())) {
                i++;
            }
            if ("cn".equalsIgnoreCase(item.getExpr().toString()) || "cn".equalsIgnoreCase(item.getAlias())) {
                i++;
            }
        }
        return i == 2;
    }

    public static void main(String[] args) {
        SQLStatementParser sqlStatementParser = SQLParserUtils.createSQLStatementParser(
                "select name,age,dd as id" + " from meta_component\n" + "where code = 'JsonBox'\n" + "\tand version = (\n" + "\t\tselect max(version)\n"
                        + "\t\tfrom meta_component\n" + "\t\twhere code = 'JsonBox'\n" + "\t)", JdbcConstants.MYSQL);
        SQLStatement sqlStatement = sqlStatementParser.parseStatement();
        ParseModel parseModel = new ParseModel(sqlStatement);
        //        System.out.println(parseModel.getQuery().getFrom());
        //        System.out.println(parseModel.getQuery().getFirst());
        //        System.out.println(parseModel.getQuery().getSelectList());
        //        System.out.println(parseModel.getQuery().getOrderBy());
        //        System.out.println(parseModel.getQuery().getWhere());

        MySqlSchemaStatVisitor mySqlSchemaStatVisitor = new MySqlSchemaStatVisitor();
        parseModel.getSelectStatement().accept(mySqlSchemaStatVisitor);

        System.out.println(mySqlSchemaStatVisitor.getColumns());

        for (SQLSelectItem item : parseModel.getQuery().getSelectList()) {
            System.out.println(item.getExpr().toString());
            System.out.println(item.getAlias());
        }

        System.out.println(parseModel.getQuery().getSelectList());
    }

    @Data
    static class ParseModel {

        SQLStatement sqlStatement;

        SQLSelectStatement selectStatement;

        SQLSelect sqlselect;

        SQLSelectQueryBlock query;

        public ParseModel(SQLStatement sqlStatement) {
            this.sqlStatement = sqlStatement;
            this.selectStatement = (SQLSelectStatement) sqlStatement;
            this.sqlselect = selectStatement.getSelect();
            this.query = sqlselect.getQueryBlock();
        }
    }
}
