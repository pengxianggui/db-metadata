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

    private AppProperties app = new AppProperties();

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
    public static class AppProperties {
        private String name = "DB-Metadata低代码开发工具————Data drive everything";
        private String logo;
        private Boolean registerable = true;
        private Boolean addable = true;
        private String defaultPass = "888888";
        private String passEncryptKey = "DB-Metadata is delicious";
    }

    @Data
    public static class ServerProperties {

        private MetaObjectProperties metaObject = new MetaObjectProperties();

        private UploadProperties upload = new UploadProperties();

        private ComponentProperties component = new ComponentProperties();

        private Login login = new Login();

        private Auth auth = new Auth();

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
        private String tokenKey = "X-TOKEN";
        private String cookieKey = "DB-Metadata";
        private String loginKey = "username";
        private String pwdKey = "password";

        LoginCtrl ctrl = new LoginCtrl();
        List<String> includes = new ArrayList<>();
        List<String> excludes = new ArrayList<>();

        public List<String> getExcludes() {
            excludes.add("/app/config");
            excludes.add("/router");
            excludes.add("/user/login");
            return excludes;
        }
    }

    @Data
    public static class Auth {
        boolean enable = false;
        List<String> includes = new ArrayList<>();
        List<String> excludes = new ArrayList<>();
    }

    @Data
    public static class LoginCtrl {
        boolean enable = true;
        String loginPath = "/user/login";
        String logoutPath = "/user/logout";
        String infoPath = "/user/info";
    }
}
