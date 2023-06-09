package com.github.md.web.user.auth.annotations;

import lombok.Getter;

/**
 * 接口资源类型。
 *
 * @author pengxg
 * @date 2021/10/16 7:40 上午
 */
public enum Type {
    /**
     * api接口直接鉴权。
     * 鉴权时，直接根据 uri关联的code进行权限认证
     */
    API("0", "鉴权时，直接根据 uri关联的code进行权限认证"),
    /**
     * 基于api接口 + 元对象进行鉴权，即uri结合元对象编码作为唯一资源。进行鉴权。<br>
     * 例如有一个元对象 user, 针对这个元对象的操作,例如toAdd, doAdd, doUpdate, delete等
     * 的鉴权。
     */
    API_WITH_META_OBJECT("1", "基于api接口 + 元对象进行鉴权"),
    /**
     * 基于api接口 + 实例配置进行鉴权，即uri结合实例编码作为唯一资源。进行鉴权
     */
    API_WITH_META_INSTANCE("2", "基于api接口 + 实例配置进行鉴权"),
    /**
     * 基于api接口 + 功能进行鉴权，即uri结合功能编码作为唯一资源。进行鉴权。
     */
    API_WITH_META_FEATURE("3", "基于api接口 + 功能进行鉴权");

    @Getter
    private String code;
    @Getter
    private String remark;

    Type(String code, String remark) {
        this.code = code;
        this.remark = remark;
    }

    public static Type byCode(String code) {
        for (Type value : Type.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("metaType is illegal, code:" + code);
    }
}
