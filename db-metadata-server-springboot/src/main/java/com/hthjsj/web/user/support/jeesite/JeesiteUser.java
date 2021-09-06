package com.hthjsj.web.user.support.jeesite;

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
public class JeesiteUser implements User {

    Kv attrs;

    public JeesiteUser(Map attrs) {
        this.attrs = Kv.create().set(attrs);
    }

    @Override
    public String userId() {
        return attrs.getStr("user_code");
    }

    @Override
    public String userName() {
        return attrs.getStr("user_name");
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
