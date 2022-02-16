package com.github.md.analysis.meta;

import lombok.Getter;

/**
 * 鉴权类型
 *
 * @author pengxg
 * @date 2021/10/16 7:40 上午
 */
public enum AuthForType {
    NORMAL("-1", "普通权限，需要自行鉴别用户是否有权限"),
    /**
     * api接口直接鉴权。
     * 鉴权时，直接根据 uri关联的auth_code进行权限认证
     */
    API("0", "鉴权时，直接根据 uri关联的auth_code进行权限认证"),
    /**
     * 基于api接口 + 元对象进行鉴权，即uri结合元对象编码作为唯一资源。关联权限编码。<br>
     * 例如有一个元对象 user, 针对这个元对象的操作,例如toAdd, doAdd, doUpdate, delete等
     * 的鉴权。
     */
    API_WITH_META_OBJECT("1", "基于api接口 + 元对象关联auth_code进行权限认证"),
    /**
     * 基于api接口 + 功能进行鉴权，即uri结合功能编码作为唯一资源。关联权限编码。
     */
    API_WITH_META_FEATURE("2", "基于api接口 + 功能进行鉴权关联权限编码进行权限认证");

    @Getter
    private String code;
    @Getter
    private String remark;

    AuthForType(String code, String remark) {
        this.code = code;
        this.remark = remark;
    }

    public static AuthForType byCode(String code) {
        for (AuthForType value : AuthForType.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("metaType is illegal, code:" + code);
    }
}
