package com.github.md.web.config;

import com.github.md.analysis.AnalysisProperties;
import com.github.md.web.file.local.LocalProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * 当前是否处于开发模式
     */
    private boolean devMode = false;

    /**
     * dbmeta文档地址
     */
    private String docUrl;

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
        private String loginBg; // 登录背景图
        private String resetPass = "dbmeta";
        private Boolean showVersion = false; // 在系统名后显示当前dbmeta版本号
        private Boolean showGreeting = true; // 显示问候
        private Boolean showThemeSetting = true; // 显示主题设置
        private Boolean allowCustomTheme = false; // 允许自定义主题
        private String docUrl = "https://doc-dbmeta.asoco.com.cn";

        private Map<String, String> root; // ROOT账号配置
    }

    @Data
    public static class ServerProperties {

        private MetaObjectProperties metaObject = new MetaObjectProperties();

        private UploadProperties upload = new UploadProperties();

        private ComponentProperties component = new ComponentProperties();

        private InstanceProperties instance = new InstanceProperties();

        private boolean enableCertification = true;

        private Login login = new Login();

        private Auth auth = new Auth();

    }

    @Data
    public static class UploadProperties {

        //local,other
        private String mode = "local";

        private LocalProperties local;
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
    public static class InstanceProperties {
        boolean recomputeWhenMetaFieldUpdate;
    }

    @Data
    public static class Login {
        private String tokenKey = "X-TOKEN";
        private String tokenIn = "localStorage"; // token在前端的存储位置，默认是localStorage
        private String cookieKey = "DB-Metadata";
        private String loginKey = "username";
        private String pwdKey = "password";

        LoginCtrl ctrl = new LoginCtrl();
        /**
         * 认证拦截器需要作用的路径
         */
        List<String> includes = new ArrayList<>();
        /**
         * 认证拦截器排除路径
         */
        List<String> excludes = new ArrayList<>();

        public List<String> getExcludes() {
            excludes.add("/app/config");
            excludes.add("/router");
            excludes.add("/user/login");
            excludes.add("/error");

            excludes.add("/doc.html");
            excludes.add("/webjars/**");
            excludes.add("/index.html");
            excludes.add("/template/**");
            excludes.add("/static/**");
            excludes.add("/favicon.ico");
            return excludes;
        }

        public List<String> getIncludes() {
            includes.add("/**");
            return includes;
        }
    }

    @Data
    public static class Auth {
        boolean enable = false;
        /**
         * 鉴权拦截器需要作用的路径
         */
        List<String> includes = new ArrayList<>();
        /**
         * 鉴权拦截器需要排除的路径
         */
        List<String> excludes = new ArrayList<>();

        public List<String> getExcludes() {
            excludes.add("/app/config");
            excludes.add("/router");
            excludes.add("/user/login");
            excludes.add("/error");

            excludes.add("/doc.html");
            excludes.add("/webjars/**");
            excludes.add("/index.html");
            excludes.add("/template/**");
            excludes.add("/static/**");
            excludes.add("/favicon.ico");
            return excludes;
        }

        public List<String> getIncludes() {
            includes.add("/**");
            return includes;
        }
    }

    @Data
    public static class LoginCtrl {
        boolean enable = true;
        String loginPath = "/user/login";
        String logoutPath = "/user/logout";
        String infoPath = "/user/info";
    }
}
