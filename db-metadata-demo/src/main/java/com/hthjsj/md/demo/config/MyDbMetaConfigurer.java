package com.hthjsj.md.demo.config;

import com.github.md.web.DefaultDbMetaConfigurer;
import com.github.md.web.file.FileRegistry;
import com.github.md.web.user.AuthenticationRegistry;
import org.springframework.context.annotation.Configuration;

/**
 * 这个是dbmeta自定义扩展的核心java配置类，所有的自定义扩展都是通过这个入口装配到dbmeta中去。
 *
 * @author pengxg
 * @date 2022/4/25 5:16 下午
 */
@Configuration
public class MyDbMetaConfigurer extends DefaultDbMetaConfigurer {

    /**
     * 通过重写此方法，你可以向dbmeta注册上传服务，内置的上传服务为local: LocalUploadService
     *
     * @param fileRegistry
     */
    @Override
    public void configFileService(FileRegistry fileRegistry) {
        super.configFileService(fileRegistry);
    }

    /**
     * 通过重写方法，你可以自定义: 用户服务、角色服务、权限服务、登录服务、用户预识别拦截执行器
     * <p>
     * 扩展：权限拦截执行器、资源判定策略
     * <p>
     * 等和用户、认证鉴权相关的内容。<b>同样，相关自定义类也必须实现对应的接口。</b>
     *
     * @param registry
     */
    @Override
    public void configAuthentication(AuthenticationRegistry registry) {
        // 更多配置见AuthenticationRegistry中的配置项
    }
}
