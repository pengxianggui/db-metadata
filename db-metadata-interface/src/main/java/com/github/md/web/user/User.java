package com.github.md.web.user;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashMap;
import java.util.Map;

/**
 * 顶层抽象的用户对象，凡是使用此框架，你的用户类均应该直接或间接继承此接口。其中{@link #isRoot()} 是一个非常特别的属性，若为true, 则可以访问
 * 任何资源(dbmeta鉴权体系不予以鉴权)，因此你应该自行决定isRoot为true的规则，在决定并实施前，用户登录后，dbmeta内置模块将均不可见。
 * <p> @Date : 2019/10/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface User {

    @JSONField(name = "userId")
    String userId();

    @JSONField(name = "userName")
    String userName();

    @JSONField(name = "avatar")
    String avatar();

    @JSONField(name = "phone")
    String phone();

    @JSONField(name = "email")
    String email();

    /**
     * 返回User所有属性(key对应数据库字段)
     *
     * @return
     */
    @JSONField(name = "attrs")
    Map attrs();

    Map attrs(Map attrs);

    /**
     * ROOT身份标识。若一个用户标识为ROOT, 则其拥有系统的所有权限(实际上，是基于dbmeta的鉴权体系针对ROOT标识的用户不予鉴权)。
     * 默认返回false, 通常你需要在自己的派生类中，自定义什么样的用户才拥有此ROOT标识，因此需要谨慎处理返回true的情况。
     * <p>
     *
     * @return
     */
    default boolean isRoot() {
        return false;
    }

    /**
     * 转换为kv键值对。用于输出
     *
     * @return
     */
    default Map toKv() {
        Map map = new HashMap();
        map.put("id", userId());
        map.put("username", userName());
        map.put("email", email());
        map.put("phone", phone());
        map.put("avatar", avatar());
        map.put("attrs", attrs());
        return map;
    }

}
