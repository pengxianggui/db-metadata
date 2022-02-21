package com.github.md.web.user.auth.meta;

import java.lang.annotation.*;

/**
 * db-meta内置访问控制注解。
 * <p>
 * 用于标注api rest接口的访问控制方式。
 *
 * @author pengxg
 * @date 2021/10/16 7:51 上午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MetaAccess {

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
