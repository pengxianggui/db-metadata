package com.hthjsj.web.query;

import com.google.common.base.Splitter;
import com.hthjsj.analysis.component.ComponentType;
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
        return getObjectCode("");
    }

    public String getObjectCode(String defaultCode) {
        return tp.getPara(0, tp.getPara("objectCode", defaultCode));
    }

    public String getFieldCode() {
        return tp.getPara("f", tp.getPara("fieldCode"));
    }

    public Integer getPageSize() {
        return tp.getInt("s", tp.getInt("pageSize", 20));
    }

    public Integer getPageIndex() {
        return tp.getInt("p", tp.getInt("pageIndex", 1));
    }

    public String getComponentCode() {
        return tp.getPara("compCode", tp.getPara("componentCode"));
    }

    public ComponentType getComponentType() {
        return ComponentType.V(tp.getPara("compType", tp.getPara("componentType")));
    }

    public String[] getObjectCodes() {
        return Splitter.on(",").omitEmptyStrings().splitToList(getObjectCode()).toArray(new String[0]);
    }
}
