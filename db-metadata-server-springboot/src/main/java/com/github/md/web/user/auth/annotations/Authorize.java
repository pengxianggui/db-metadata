package com.github.md.web.user.auth.annotations;

import com.github.md.web.user.auth.defaults.AuthorizePermit;

import java.lang.annotation.*;

/**
 * 此注解用于修饰API接口方法，生效于dbmeta内置的注解数据鉴权。与此注解相互配合的判定器为{@link AuthorizePermit}
 * <p>
 * 基于java注解的注解鉴权，它和基于数据的动态数据鉴权，是dbmeta内置的两种鉴权方式，两种方式都会启用。优先级：
 * <b>注解鉴权 > 动态数据鉴权</b>
 * <p>
 * <b>注意: 注解鉴权和动态数据鉴权，两种方式虽然都会启用。但针对具体请求，两种方式是互斥的。如果API上有{@link Authorize}注解，则注解鉴权生效，动态数据鉴权不生效。</b>
 *
 * @author pengxg
 * @date 2023/4/26 11:31
 * @see AuthorizePermit 注解鉴权判定器。
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Authorize {

    /**
     * 任何人都可以访问。若返回true, 则其它均不生效。
     */
    boolean whoever() default false;

    /**
     * 只需要签名（识别已登录）。若返回true, 则{@link #role()} 和 {@link #value()} 都不生效。
     */
    boolean justSign() default false;

    /**
     * 按角色鉴权。优先级低于{@link #value()}， 即后者为空数组时，才会使用此项。
     */
    String[] role() default {};

    /**
     * 匹配所有的角色编码。当{@link #role()} 生效时，标识其生效机制。
     */
    boolean matchAllRole() default false;

    /**
     * 按权限鉴权。优先级高于{@link #role()}
     */
    String[] value() default {};

    /**
     * 匹配所有的权限编码。当{@link #value()} 生效时，标识其生效机制。
     */
    boolean matchAllValue() default false;
}
