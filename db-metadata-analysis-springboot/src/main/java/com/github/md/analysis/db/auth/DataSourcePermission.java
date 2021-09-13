package com.github.md.analysis.db.auth;

import com.jfinal.kit.StrKit;

/**
 * <p> @Date : 2021/9/8 </p>
 * <p> @Project : db-metadata-analysis-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
public enum DataSourcePermission {
    UNKNOW,
    R,//读
    W,//写
    RW;//读写

    public static DataSourcePermission get(String s) {
        if (StrKit.isBlank(s)) {
            return R;
        }
        if (s.equalsIgnoreCase("r"))
            return R;
        else if (s.equalsIgnoreCase("w"))
            return W;
        else if (s.equalsIgnoreCase("rw"))
            return RW;
        return UNKNOW;
    }
}
