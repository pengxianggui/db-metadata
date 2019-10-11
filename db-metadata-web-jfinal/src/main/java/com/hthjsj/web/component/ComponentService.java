package com.hthjsj.web.component;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Before(Tx.class)
public class ComponentService {

    public Record load(String code) {
        if (StrKit.isBlank(code)) {
            throw new ComponentException(String.format("无效的组件Code:%s", code));
        }
        Record record = Db.findFirst("select * from meta_component where code=?", code);
        if (record == null) {
            throw new ComponentException(String.format("无效的组件Code:%s", code));
        }
        return record;
    }
}