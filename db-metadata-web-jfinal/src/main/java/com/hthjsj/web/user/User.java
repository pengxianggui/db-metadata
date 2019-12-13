package com.hthjsj.web.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.jfinal.kit.Kv;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
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

    @JSONField(name = "attrs")
    Kv attrs();
}
