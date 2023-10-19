package com.github.md.web.user.auth;

/**
 * <pre>
 * 更名为判定器
 *  1. 资源判定器
 *  2. 角色判定器
 *  3. 权限判定器
 *
 * 抽象资源判定器,判定是否具备该资源的访问
 * 例: resourceHolder.getResources(mResource);
 * </pre>
 * <p> @Date : 2019/12/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface MRPermit<U, R> {

    /**
     * 资源判定动作.
     *
     * @param resourceHolder 资源持有对象
     * @param mResource
     * @return
     */
    boolean permit(U resourceHolder, R mResource);
}
