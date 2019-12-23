package com.hthjsj.web.auth;

/**
 * 抽象资源判定器,判定是否具备该资源的访问
 * 例: resourceHolder.getResources(mResource);
 * <p> @Date : 2019/12/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface MRPermit<U, R> {

    /**
     * 资源判定动作
     *
     * @param resourceHolder
     * @param mResource
     *
     * @return
     */
    boolean permit(U resourceHolder, R mResource);
}
