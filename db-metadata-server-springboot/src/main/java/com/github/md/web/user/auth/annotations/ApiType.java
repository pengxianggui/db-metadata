package com.github.md.web.user.auth.annotations;

import java.lang.annotation.*;

/**
 * API类型，用于描述API接口。生效于db-meta内置的基于数据的动态数据鉴权。
 * <p>
 * <b>当采用基于dbmeta的动态权限数据鉴权时，此注解才会生效。当然，动态数据鉴权是启用的，只是有其他优先级更高的鉴权类别放行时，动态数据鉴权才不会生效。</b>
 * <p>
 * <b>如果使用了内置注解鉴权(内置的{@link Authorize}修饰接口)，则其优先级高于动态数据鉴权，后者就不会生效。</b>
 * <p>
 * 动态访问控制时，用于标注api rest接口的访问控制方式。
 *
 * @author pengxg
 * @date 2021/10/16 7:51 上午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiType {

    /**
     * 接口资源的鉴权标注类型。默认为 {@link Type#API}, 表示只基于接口url关联权限编码code(详见meta_auth表), 并判定用户是否有code权限。
     * <p>
     * 当为其他值时，详见{@link Type}
     * <p>
     * 说明：之所以有这个属性。是因为dbMeta内置的接口很多是共用的，例如/table/list?objectCode={objectCode}，不同的objectCode，响应
     * 数据是不同的，对应的页面也不同，查询的表也不同。因此鉴权无法单独依赖硬编码的注解说明，还需要依赖入参objectCode，因此有了{@link Type},
     * 它描述了，系统鉴权时的依据
     *
     * @return
     * @see Type
     */
    Type value() default Type.API;
}
