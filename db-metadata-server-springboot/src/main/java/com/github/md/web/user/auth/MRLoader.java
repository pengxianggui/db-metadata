package com.github.md.web.user.auth;

import java.util.List;

/**
 * 资源访问器
 * 1. 载入所负责的资源
 * 2. 判定资源是否在己资源池中;
 *
 * <p> @Date : 2019/12/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 *
 * @deprecated 资源无需载入，由判定器 {@link MRPermit} 直接在判定的时候自取资源即可
 */
@Deprecated
public interface MRLoader {

    void load();

    List<MResource> resources();
}
