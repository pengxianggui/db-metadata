package com.hthjsj.web.ui;

import com.alibaba.fastjson.annotation.JSONField;
import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/10/30 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface AccessBehavior {

    /**
     * isAdd/isUpdate 控制sql生成逻辑
     *
     * @return
     */
    @JSONField(name = "isAdd")
    boolean isAdd();

    //    void isAdd(boolean value);

    @JSONField(name = "isUpdate")
    boolean isUpdate();

    //    void isUpdate(boolean value);

    class DefaultAccessBehavior implements AccessBehavior {

        Kv conf;

        public DefaultAccessBehavior(Kv conf) {
            this.conf = conf;
        }

        @Override
        public boolean isAdd() {
            return conf.isTrue("isAdd");
        }

        @Override
        public boolean isUpdate() {
            return conf.isTrue("isUpdate");
        }
    }
}
