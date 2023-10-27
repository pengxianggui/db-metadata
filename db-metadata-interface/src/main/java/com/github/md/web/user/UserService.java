package com.github.md.web.user;

import java.util.List;
import java.util.Map;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface UserService<U extends User> {

    List<U> findAll();

    U findById(Object idValue);

    /**
     * 提供一种松散的方式更新用户数据。需要注意, data中的key应当与用户表的字段保持一致。DbMeta不会做 下划线与驼峰的转换
     *
     * @param idValue 用户主键
     * @param data    属性数据的map，键名需要匹配数据表字段名
     * @return
     */
    boolean updateById(Object idValue, Map data);

    boolean updateById(U user);

    /**
     * 幂等
     *
     * @param userId
     * @param roleIds
     * @return
     */
    boolean bindRolesForUser(String userId, String... roleIds);

    /**
     * 密码重置, 重置为默认密码
     *
     * @return
     */
    boolean resetPass(Object userId);

    /**
     * 刷新所有用户的密码。此操作不改变用户密码的明文值，但会更改数据库里存储的密文值。当加密密钥发生变化时，执行此方法。
     * <p>
     * 数据库里密码是加密的，若系统配置的加密密钥发生变化，则需要重新加密更新用户的密文密码(对应的明文值是不变的), 否则系统设置时，若更改
     * 了加密密钥，所有用户将无法登陆。
     *
     * @param oldEncryptKey 旧的加密密钥，因为必须要用到之前的密钥进行解密
     * @param newEncryptKey 新的加密密钥
     * @return 所有用户执行成功则返回true，确保事务
     */
    default boolean refreshPass(String oldEncryptKey, String newEncryptKey) {
        return false;
    }

    /**
     * 用户元对象编码
     *
     * @return
     */
    String userObjectCode();

    /**
     * 为用户设置密码
     *
     * @param userId   用户id
     * @param password 明文密码
     * @return
     */
    boolean setPass(Object userId, String password);
}
