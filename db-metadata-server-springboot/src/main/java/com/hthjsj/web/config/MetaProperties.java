package com.hthjsj.web.config;

import com.hthjsj.analysis.AnalysisProperties;
import lombok.Data;

/**
 * <p> @Date : 2021/9/6 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
@Data
public class MetaProperties {

    private ServerProperties server;

    private AnalysisProperties analysis;

    private boolean devMode;

    /**
     * !!! Very important !!!
     * Inject the configuration used by the analysis module
     **/
    public MetaProperties(AnalysisProperties analysis) {
        this.analysis = analysis;
    }

    @Data
    public static class ServerProperties {

        private MetaObjectProperties metaObject;

        private ComponentProperties component;
    }

    @Data
    public static class UploadProperties {

        //local,other
        private String mode = "local";

        private String baseUploadPath;

        private long maxPostSize;
    }

    @Data
    public static class ComponentProperties {

        boolean replaceFromJsonFile;
    }

    @Data
    public static class MetaObjectProperties {

        boolean replaceFromJsonFile;
    }
}
