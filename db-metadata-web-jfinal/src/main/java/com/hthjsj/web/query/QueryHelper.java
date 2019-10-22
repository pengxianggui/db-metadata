package com.hthjsj.web.query;

import com.jfinal.core.Controller;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/15 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class QueryHelper {

    private Controller tp;

    public QueryHelper(Controller controller) {
        tp = controller;
    }

    public String getObjectCode() {
        return tp.getPara(0, tp.getPara("objectCode".toLowerCase()));
    }

    public String getFieldCode() {
        return tp.getPara("f", tp.getPara("objectField".toLowerCase()));
    }

    public Integer getPageSize() {
        return tp.getInt("s", tp.getInt("pageSize".toLowerCase(), 20));
    }

    public Integer getPageIndex() {
        return tp.getInt("p", tp.getInt("pageIndex".toLowerCase(), 1));
    }

    public String getComponentCode() {
        return tp.getPara("compCode".toLowerCase(), tp.getPara("componentCode".toLowerCase()));
    }

    public String getComponentType() {
        return tp.getPara("compType".toLowerCase(), tp.getPara("componentType".toLowerCase()));
    }
}
