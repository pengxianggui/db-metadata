package com.github.md.web.config;

import com.github.md.analysis.AnalysisProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> @Date : 2021/9/6 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
@Data
public class MetaProperties {

    private ServerProperties server = new ServerProperties();

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

        private MetaObjectProperties metaObject = new MetaObjectProperties();

        private UploadProperties upload = new UploadProperties();

        private ComponentProperties component = new ComponentProperties();

        private Login login = new Login();

        private Auth auth = new Auth();

        /**
         * 开发模式。开发模式下，若登录无用户，则会取静态缓存的用户。还有一些其他接口可以访问
         */
        private boolean devMode;
    }

    @Data
    public static class UploadProperties {

        //local,other
        private String mode = "local";

        private String baseUploadPath;
    }

    @Data
    public static class ComponentProperties {

        boolean replaceFromJsonFile;
    }

    @Data
    public static class MetaObjectProperties {

        boolean replaceFromJsonFile;
    }

    @Data
    public static class Login {
        boolean enable = false;
        List<String> includes = new ArrayList<>();
        List<String> excludes = new ArrayList<>();
    }

    @Data
    public static class Auth {
        boolean enable = false;
        List<String> includes = new ArrayList<>();
        List<String> excludes = new ArrayList<>();
    }
}
