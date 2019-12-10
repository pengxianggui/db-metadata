package com.hthjsj.analysis.meta;

import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaData extends Kv {

    /**
     * @param primaryKey abc,bcd  支持,号分割的的主键
     *
     * @return
     */
    public Object[] getPks(String primaryKey) {
        List<String> values = new ArrayList<>();
        if (StrKit.notBlank(primaryKey)) {
            String[] keys = primaryKey.split(",");
            for (String key : keys) {
                if (StrKit.notBlank(key)) {
                    values.add(getStr(key));
                }
            }
        }
        return values.toArray();
    }
}
