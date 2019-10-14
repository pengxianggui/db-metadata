package com.hthjsj.web.jfinal;

import com.jfinal.plugin.activerecord.SqlPara;
import lombok.Getter;
import lombok.Setter;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/14 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
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
}
