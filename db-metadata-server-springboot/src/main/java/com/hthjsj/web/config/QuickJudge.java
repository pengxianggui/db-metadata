package com.hthjsj.web.config;

import com.hthjsj.analysis.meta.BusinessService;
import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.component.ComponentService;
import com.hthjsj.web.feature.FeatureService;
import com.hthjsj.web.kit.tree.TreeService;

/**
 * 可以混入代码的工具类
 * <p> @Date : 2021/9/7 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
public interface QuickJudge {

    boolean isDevMode();

    String mainDbStr();

    /**
     * ===========================================================================
     * Continue to increase on demand
     * ===========================================================================
     */
    default DbMetaService metaService() {
        return ServiceManager.metaService();
    }

    default ComponentService componentService() {
        return ServiceManager.componentService();
    }

    default FeatureService featureService() {
        return ServiceManager.featureService();
    }

    default TreeService treeService() {
        return ServiceManager.treeService();
    }

    default BusinessService businessService() {
        return ServiceManager.businessService();
    }

    //    final class INSTANCE {
    //
    //        private INSTANCE() {
    //        }
    //
    //        public static QuickJudge getInstance() {
    //            return AnalysisSpringUtil.getBean(QuickJudge.class);
    //        }
    //    }
}
