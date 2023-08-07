package com.github.md.web;

import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.analysis.db.MysqlService;
import com.github.md.analysis.meta.BusinessService;
import com.github.md.analysis.meta.DbMetaService;
import com.github.md.web.app.AppConfigService;
import com.github.md.web.component.ComponentService;
import com.github.md.web.config.MetaProperties;
import com.github.md.web.config.QuickJudge;
import com.github.md.web.feature.FeatureService;
import com.github.md.web.kit.tree.TreeService;
import com.github.md.web.snippet_var.SnippetService;
import com.github.md.web.snippet_var.VarService;
import com.github.md.web.ui.SqlAnalysis;
import com.github.md.web.file.DownloadService;
import com.github.md.web.file.FileManager;
import com.github.md.web.file.UploadService;

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

    /**
     * 直接使用 {@link FileManager#getUploadService()}
     *
     * @return
     */
    @Deprecated
    public static UploadService uploadService() {
        return FileManager.me().getUploadService();
    }

    /**
     * 直接使用 {@link FileManager#getDownloadService()}
     *
     * @return
     */
    @Deprecated
    public static DownloadService downloadService() {
        return FileManager.me().getDownloadService();
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

    public static AppConfigService getAppConfigService() {
        return AnalysisSpringUtil.getBean(AppConfigService.class);
    }

    public static SnippetService getSnippetService() {
        return AnalysisSpringUtil.getBean(SnippetService.class);
    }

    public static VarService getVarService() {
        return AnalysisSpringUtil.getBean(VarService.class);
    }
}
