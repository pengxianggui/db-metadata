package com.github.md.web.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.md.analysis.kit.Kv;

import java.util.Map;

/**
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

    Kv attrs(Map attrs);

    /**
     * 转换为kv键值对。用于输出
     *
     * @return
     */
    default Kv toKv() {
        return Kv.create().set("id", userId())
                .set("username", userName())
                .set("attrs", attrs());
    }
}
