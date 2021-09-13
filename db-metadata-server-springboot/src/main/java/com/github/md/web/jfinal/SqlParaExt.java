package com.github.md.web.jfinal;

import com.github.md.web.query.sqls.InNotInMatch;
import com.github.md.web.ui.SqlAnalysis;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.SqlPara;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/14 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class SqlParaExt extends SqlPara {

    @Getter
    @Setter
    private String select;

    @Getter
    @Setter
    private String from;

    @Getter
    @Setter
    private String whereExcept;

    @Setter
    private String orderBy;

    public SqlParaExt() {
    }

    public String getFromWhere() {
        return getFrom() + getWhereExcept();
    }

    public String getOrderBy() {
        return StrKit.isBlank(orderBy) ? "" : orderBy;
    }

    @Override
    public String getSql() {
        return getSelect() + getFromWhere() + getOrderBy();
    }

    /**
     * Sql 格式验证,确保生成后的sql有效;
     *
     * @return
     */
    public boolean verify() {
        return SqlAnalysis.check(getSql());
    }

    /**
     * <pre>
     *  appendSql : a=?
     *  params: 与?需一一对应
     * </pre>
     *
     * @param columnName
     * @param params
     */
    public void appendIn(String columnName, Object... params) {
        InNotInMatch inNotInMatch = new InNotInMatch();
        String oneCondition = inNotInMatch.toSqlKey(columnName, params, true);
        appendAndCondition(oneCondition, params);
    }

    /**
     * @param conditionAndValue column=abc
     */
    public void appendAndCondition(String conditionAndValue) {
        whereExcept += " and " + conditionAndValue;
    }

    public void appendAndCondition(String conditionFull, Object... params) {
        whereExcept += " and " + conditionFull;
        for (int i = 0; i < params.length; i++) {
            addPara(params[i]);
        }
    }
}
