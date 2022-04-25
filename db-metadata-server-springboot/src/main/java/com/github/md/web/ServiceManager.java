package com.github.md.web;

import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.analysis.db.MysqlService;
import com.github.md.analysis.meta.BusinessService;
import com.github.md.analysis.meta.DbMetaService;
import com.github.md.web.component.ComponentService;
import com.github.md.web.config.MetaProperties;
import com.github.md.web.config.QuickJudge;
import com.github.md.web.feature.FeatureService;
import com.github.md.web.kit.tree.TreeService;
import com.github.md.web.ui.SqlAnalysis;
import com.github.md.web.upload.UploadManager;
import com.github.md.web.upload.UploadService;

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
        return UploadManager.me().getUploadService();
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

    public static MetaProperties getAppProperties() {
        return AnalysisSpringUtil.getBean(MetaProperties.class);
    }
}
