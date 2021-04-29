package com.hthjsj.web;

import com.hthjsj.analysis.meta.BusinessService;
import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.web.component.ComponentService;
import com.hthjsj.web.feature.FeatureService;
import com.hthjsj.web.kit.tree.TreeService;
import com.hthjsj.web.ui.SqlAnalysis;
import com.hthjsj.web.upload.UploadService;
import com.hthjsj.web.upload.UploadServiceManager;
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

    public static UploadService fileService() {
        return UploadServiceManager.me().getUploadService();
    }

    public static FeatureService featureService() {
        return Aop.get(FeatureService.class);
    }

    public static TreeService treeService() {
        return Aop.get(TreeService.class);
    }

    public static BusinessService businessService() {
        return Aop.get(BusinessService.class);
    }

    public static SqlAnalysis sqlAnalysis() {
        return SqlAnalysis.me();
    }
}
