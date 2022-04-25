package com.github.md.web;

import com.github.md.web.upload.UploadConfigurer;
import com.github.md.web.user.AuthenticationConfigurer;

/**
 * DbMeta配置器。继承此抽象类，覆盖指定方法实现自定义扩展。
 *
 * @author pengxg
 * @date 2022/4/25 9:37 上午
 */
public abstract class DbMetaConfigurer implements AuthenticationConfigurer, UploadConfigurer {
    // TODO 元对象、元字段、组件实例配置的扩展入口也放到这里
}
