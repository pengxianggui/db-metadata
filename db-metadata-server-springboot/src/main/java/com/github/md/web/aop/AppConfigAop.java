package com.github.md.web.aop;

import com.github.md.analysis.meta.aop.AddPointCut;
import com.github.md.analysis.meta.aop.FormInvocation;
import com.github.md.analysis.meta.aop.UpdatePointCut;
import com.github.md.web.ServiceManager;
import com.github.md.web.WebException;
import com.github.md.web.app.AppConfig;
import com.github.md.web.kit.AssertKit;
import com.github.md.web.user.AuthenticationManager;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 动态维护的系统配置表。更新动作，视为新增，版本自增。
 */
@Slf4j
public class AppConfigAop implements AddPointCut, UpdatePointCut {
    private static final String OBJECT_CODE = "meta_app_config";

    @Override
    public boolean addBefore(FormInvocation invocation) {
        AssertKit.isTrue(OBJECT_CODE.equals(invocation.getMetaObject().code()),
                "此AOP:%s 只适用于元对象%s。请检查元对象配置。", this.getClass().getName(), OBJECT_CODE);

        Integer version = invocation.getFormData().getInt("version");
        invocation.getFormData().set("version", version + 1);

        return true;
    }

    /**
     * 检测pass_encrypt_key是否变更，若变更，则重新刷一遍meta_user表中的password
     *
     * @param invocation
     * @return
     */
    @Override
    public boolean addAfter(FormInvocation invocation) {
        String oldId = invocation.getHttpParams().getStr("id");

        AppConfig appConfig = ServiceManager.getAppConfigService().getById(oldId);
        String oldEncryptKey = appConfig.getPassEncryptKey();
        String newEncryptKey = invocation.getFormData().getStr("pass_encrypt_key");

        if (Objects.equals(oldEncryptKey, newEncryptKey)) {
            return true;
        }

        log.warn("The login passwords for all users are going to refresh based on the new encryption key!");
        boolean flag = AuthenticationManager.me().getUserService().refreshPass(oldEncryptKey, newEncryptKey);
        AssertKit.isTrue(flag == true, "您更改了加密密钥, 但系统未成功重新加密用户密码，系统配置将不会保存。");

        return true;
    }

    @Override
    public boolean updateBefore(FormInvocation invocation) {
        throw new WebException("系统配置不允许更新");
    }
}
