package com.github.md.web.config;

import com.github.md.web.ServiceManager;
import com.github.md.web.component.ComponentService;
import com.github.md.web.feature.FeatureService;
import com.github.md.analysis.meta.BusinessService;
import com.github.md.analysis.meta.DbMetaService;
import com.github.md.web.kit.tree.TreeService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    String baseUploadPath();

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

    default HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    default HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
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
