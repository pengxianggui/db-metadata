package com.hthjsj.web.auth;

import java.util.List;

/**
 * <p> @Date : 2019/12/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface MRService {

    MResource findById(Object id);

    List<MResource> findAll();
}
