package com.hthjsj.web;

import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.web.component.ComponentService;
import com.hthjsj.web.ui.SqlAnalysis;
import com.jfinal.aop.Aop;

/**
 * <p> @Date : 2019/10/22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ServiceManager {

    private ServiceManager() {
    }

    public static DbMetaService metaService() {
        return Aop.get(DbMetaService.class);
    }

    public static ComponentService componentService() {
        return Aop.get(ComponentService.class);
    }

    public static SqlAnalysis sqlAnalysis() {
        return SqlAnalysis.me();
    }
}
