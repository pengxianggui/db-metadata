package com.hthjsj.web.jfinal;

import com.hthjsj.web.UtilKit;
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
        return getFrom() + getWhereExcept() + getOrderBy();
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
        return UtilKit.verifySQL(getSql());
    }
}
