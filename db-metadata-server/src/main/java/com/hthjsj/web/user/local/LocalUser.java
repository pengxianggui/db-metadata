package com.hthjsj.web.user.local;

import com.hthjsj.web.kit.UtilKit;
import com.hthjsj.web.user.User;
import com.jfinal.kit.Kv;

import java.util.Map;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class LocalUser implements User {

    Kv attrs;

    public LocalUser(Map attr) {
        this.attrs = Kv.create().set(attr);
    }

    @Override
    public String userId() {
        return attrs.getStr("userId");
    }

    @Override
    public String userName() {
        return attrs.getStr("userName");
    }

    @Override
    public Kv attrs() {
        return attrs;
    }

    @Override
    public Kv attrs(Map attrs) {
        UtilKit.deepMerge(this.attrs, attrs, true);
        return this.attrs;
    }
}
