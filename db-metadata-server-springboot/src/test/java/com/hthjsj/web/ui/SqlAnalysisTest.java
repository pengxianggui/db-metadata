package com.hthjsj.web.ui;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.util.JdbcConstants;

/**
 * <p> @Date : 2021/9/8 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
class SqlAnalysisTest {

    public static void main(String[] args) {
        SQLStatementParser sqlStatementParser = SQLParserUtils.createSQLStatementParser(
                "select name,age,dd as id" + " from meta_component\n" + "where code = 'JsonBox'\n" + "\tand version = (\n" + "\t\tselect max(version)\n"
                        + "\t\tfrom meta_component\n" + "\t\twhere code = 'JsonBox'\n" + "\t)", JdbcConstants.MYSQL);
        SQLStatement sqlStatement = sqlStatementParser.parseStatement();
        SqlAnalysis.ParseModel parseModel = new SqlAnalysis.ParseModel(sqlStatement);
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
}