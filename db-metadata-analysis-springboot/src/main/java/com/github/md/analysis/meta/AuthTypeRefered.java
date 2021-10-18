package com.github.md.analysis.meta;

import java.lang.annotation.*;

/**
 * 涉及的鉴权类型，用于标注api rest接口
 *
 * @author pengxg
 * @date 2021/10/16 7:51 上午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthTypeRefered {
    AuthForType value();
}
