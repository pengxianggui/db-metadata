package com.github.md.web.kit;

import cn.hutool.crypto.SecureUtil;
import com.github.md.web.ServiceManager;
import com.github.md.web.config.MetaProperties;
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
        MetaProperties metaProperties = ServiceManager.getAppProperties();

        String defaultPass = Optional.ofNullable(metaProperties)
                .map(MetaProperties::getApp)
                .map(MetaProperties.AppProperties::getDefaultPass).orElse(null);

        return encryptPass(StrKit.defaultIfBlank(defaultPass, "888888"));
    }

    /**
     * 按照配置的密钥进行明文密码加密
     *
     * @param clearPass 明文密码
     * @return
     */
    public static String encryptPass(String clearPass) {
        MetaProperties metaProperties = ServiceManager.getAppProperties();

        String passEncryptKey = Optional.ofNullable(metaProperties)
                .map(MetaProperties::getApp)
                .map(MetaProperties.AppProperties::getPassEncryptKey).orElse(null);

        if (StrKit.isBlank(passEncryptKey)) {
            log.warn("您未配置密码加密密钥(请配置: md.app.pass-encrypt-key), 无法加密, 将输出原内容。");
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
        MetaProperties metaProperties = ServiceManager.getAppProperties();

        String passEncryptKey = Optional.ofNullable(metaProperties)
                .map(MetaProperties::getApp)
                .map(MetaProperties.AppProperties::getPassEncryptKey).orElse(null);

        if (StrKit.isBlank(passEncryptKey)) {
            log.warn("您未配置密码加密密钥(请配置: md.app.pass-encrypt-key), 无法加密, 将输出原内容。");
            return encryptPass;
        }

        return SecureUtil.aes(passEncryptKey.getBytes()).decryptStr(encryptPass);
    }
}
