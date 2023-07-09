package com.github.md.web.kit;

import cn.hutool.crypto.SecureUtil;
import com.github.md.web.ServiceManager;
import com.github.md.web.app.AppConfig;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * @author pengxg
 * @date 2022/2/18 1:16 下午
 */
@Slf4j
public class PassKit {

    /**
     * 按照配置的密钥进行密码加密。加密对象为配置的默认密码
     *
     * @return
     */
    public static String encryptPass() {
        AppConfig appConfig = ServiceManager.getAppConfigService().getLatest();

        String defaultPass = Optional.ofNullable(appConfig)
                .map(AppConfig::getDefaultPass).orElse(null);

        return encryptPass(StrKit.defaultIfBlank(defaultPass, "888888"));
    }

    /**
     * 按照配置的密钥进行明文密码加密
     *
     * @param clearPass 明文密码
     * @return
     */
    public static String encryptPass(String clearPass) {
        AppConfig appConfig = ServiceManager.getAppConfigService().getLatest();

        String passEncryptKey = Optional.ofNullable(appConfig)
                .map(AppConfig::getPassEncryptKey).orElse(null);

        if (StrKit.isBlank(passEncryptKey)) {
            log.warn("您未配置密码加密密钥(请于【系统管理】>【系统设置】>【基础设置】中设置用户密码加密密钥), 无法加密, 将输出原内容。");
            return clearPass;
        }

        return SecureUtil.aes(passEncryptKey.getBytes()).encryptHex(clearPass);
    }

    /**
     * 按照配置的密钥进行加密密码的解密
     *
     * @param encryptPass
     * @return
     */
    public static String decryptPass(String encryptPass) {
        AppConfig appConfig = ServiceManager.getAppConfigService().getLatest();

        String passEncryptKey = Optional.ofNullable(appConfig)
                .map(AppConfig::getPassEncryptKey).orElse(null);

        if (StrKit.isBlank(passEncryptKey)) {
            log.warn("您未配置密码加密密钥(请于【系统管理】>【系统设置】>【基础设置】中设置用户密码加密密钥), 无法加密, 将输出原内容。");
            return encryptPass;
        }

        return SecureUtil.aes(passEncryptKey.getBytes()).decryptStr(encryptPass);
    }
}
