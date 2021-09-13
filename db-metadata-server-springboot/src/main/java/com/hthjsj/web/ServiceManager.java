package com.hthjsj.web;

import com.hthjsj.AnalysisSpringUtil;
import com.hthjsj.analysis.db.MysqlService;
import com.hthjsj.analysis.meta.BusinessService;
import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.web.component.ComponentService;
import com.hthjsj.web.config.QuickJudge;
import com.hthjsj.web.feature.FeatureService;
import com.hthjsj.web.kit.tree.TreeService;
import com.hthjsj.web.ui.SqlAnalysis;
import com.hthjsj.web.upload.UploadService;

/**
 * FIXME 这个类逐步过度 0908
 * <p> @Date : 2019/10/22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ServiceManager {

    public static DbMetaService metaService() {
        return AnalysisSpringUtil.getBean(DbMetaService.class);
    }

    public static MysqlService mysqlService() {
        return AnalysisSpringUtil.getBean(MysqlService.class);
    }

    public static ComponentService componentService() {
        return AnalysisSpringUtil.getBean(ComponentService.class);
    }

    public static UploadService fileService() {
        return AnalysisSpringUtil.getBean(UploadService.class);
    }

    public static FeatureService featureService() {
        return AnalysisSpringUtil.getBean(FeatureService.class);
    }

    public static TreeService treeService() {
        return AnalysisSpringUtil.getBean(TreeService.class);
    }

    public static BusinessService businessService() {
        return AnalysisSpringUtil.getBean(BusinessService.class);
    }

    public static QuickJudge quickJudge() {
        return AnalysisSpringUtil.getBean(QuickJudge.class);
    }

    public static SqlAnalysis sqlAnalysis() {
        return SqlAnalysis.me();
    }
}
