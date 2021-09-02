package com.hthjsj.analysis.meta;

import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.jfinal.kit.StrKit;

import java.util.List;

/**
 * <p> @Date : 2019/12/11 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 * <p>
 * Sql解析部分参考
 *
 * @see <a href="https://github.com/alibaba/druid/wiki/SQL-Parser">SQL-Parser</a>
 * @see <a href="https://blog.csdn.net/a499477783/article/details/85048055">使用Druid的sql parser做一个表数据血缘分析工具</a>
 */
public class MetaFactory {

    /**
     * <pre>
     * SQLUtils -> SQLStatement
     *
     * SQLSelectStatement包含一个SQLSelect，SQLSelect包含一个SQLSelectQuery
     * 都是组成的关系。SQLSelectQuery有主要的两个派生类，分别是SQLSelectQueryBlock和SQLUnionQuery。
     * SQLSelectQueryBlock 中包含 主要用到的 表,字段,where,group by ,order by 这些信息
     *
     * 组成解构
     * **********************************************************************
     * interface SQLObject {}
     * interface SQLExpr extends SQLObject {}
     * interface SQLStatement extends SQLObject {}
     *
     * interface SQLTableSource extends SQLObject {}
     * class SQLSelect extends SQLObject {}
     * class SQLSelectQueryBlock extends SQLObject {}
     * **********************************************************************
     * </pre>
     */
    public static IMetaObject createBySql(String sql, String objectCode) {
        ManualMetaObject manualMetaObject = new ManualMetaObject();
        /**
         * 特殊规则:
         * 必须指定一列 id
         * 1. 解析 表
         * 2. 解析 列 (分解目标列和别名,用来构建元子段name,label)
         * 3. 构建元子段,填入元对象
         */
        MySqlStatementParser mySqlStatementParser = new MySqlStatementParser(sql);
        SQLSelectStatement selectStatement = (SQLSelectStatement) mySqlStatementParser.parseSelect();
        SQLSelectQueryBlock queryBlock = selectStatement.getSelect().getQueryBlock();
        List<SQLSelectItem> columns = queryBlock.getSelectList();
        SQLTableSource sqlTableSource = queryBlock.getFrom();
        String tableName = sqlTableSource.computeAlias();
        ManualMetaField manualMetaField = null;
        for (SQLSelectItem item : columns) {
            //列名
            String name = item.getExpr().toString();
            //别名
            String label = StrKit.defaultIfBlank(item.getAlias(), name);
            manualMetaField = new ManualMetaField(manualMetaObject);
            manualMetaField.fieldCode(name);
            manualMetaField.en(name);
            manualMetaField.cn(label);
            manualMetaField.objectCode(objectCode);
            manualMetaField.dbType("varchar");
            manualMetaField.config("{}");
            manualMetaField.config(MetaConfigFactory.createV1FieldConfig(manualMetaField, null, null).toJson());
            manualMetaObject.addField(manualMetaField);
            //指定主键
            if ("id".equalsIgnoreCase(name) || "id".equalsIgnoreCase(label)) {
                manualMetaObject.primaryKey(name);
            }
        }
        if (!StrKit.notBlank(manualMetaObject.primaryKey())) {
            throw new MetaOperateException("使用sql创建的元对象,必须包含名[id]列,原始Sql:{}", sql);
        }
        manualMetaObject.tableName(tableName);
        manualMetaObject.code(objectCode);
        return manualMetaObject;
    }

    public static IMetaField createMetaField(IMetaObject parent) {
        return new MetaField(parent);
    }

    public static IMetaField createReadonlyMetaField(IMetaObject parent, String fieldCode, String cn, String value) {
        IMetaField metaField = new MetaField(parent);
        metaField.fieldCode(fieldCode);
        metaField.en(fieldCode);
        metaField.cn(cn);
        metaField.objectCode(parent.code());
        metaField.dbType("varchar");
        metaField.config("{}");
        metaField.config(MetaConfigFactory.createV1FieldConfig(metaField, value, null).toJson());
        return metaField;
    }

    public static IMetaObject createMetaObject() {
        return new MetaObject();
    }
}
