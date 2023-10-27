package com.github.md.web.user.auth;

/**
 * 资源抽象
 * <p> @Date : 2019/12/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface MResource {

    /**
     * 资源标志,全局唯一
     *
     * @return
     */
    String mResourceId();

    String mResourceName();

    /**
     * 是否需要许可才能访问的资源
     *
     * @return
     */
    boolean needPermit();
}
