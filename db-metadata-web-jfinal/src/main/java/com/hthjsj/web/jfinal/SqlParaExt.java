package com.hthjsj.web.jfinal;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.parser.ParserException;
import com.alibaba.druid.util.JdbcConstants;
import com.hthjsj.web.controller.ControllerException;
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
    private String sqlExceptSelect;

    public SqlParaExt() {
    }

    public SqlParaExt(String select, String from, String sqlExceptSelect) {
        this.select = select;
        this.from = from;
        this.sqlExceptSelect = sqlExceptSelect;
    }

    public String getFromExceptSelect() {
        return from + sqlExceptSelect;
    }

    @Override
    public String getSql() {
        return getSelect() + getFromExceptSelect();
    }

    /**
     * Sql 格式验证,确保生成后的sql有效;
     *
     * @return
     */
    public boolean verify() {
        boolean flag = false;
        try {
            SQLUtils.parseStatements(getSql(), JdbcConstants.MYSQL);
            flag = true;
        } catch (ParserException e) {
            throw new ControllerException("SQL格式不正确%s", getSql());
        }
        return flag;
    }
}
