package com.hthjsj.web.component.form;

import com.alibaba.fastjson.JSON;
import com.hthjsj.analysis.meta.IMetaField;
import com.jfinal.kit.Kv;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class InputField extends FormField {

    String cn;

    String en;

    IMetaField metaField;

    Kv metaFieldConfig = Kv.create();

    public InputField(IMetaField metaField) {
        this.metaField = metaField;
        cn = metaField.cn();
        en = metaField.en();
        metaFieldConfig = JSON.parseObject(metaField.config().getConfig(), Kv.class);
    }

    @Override
    public String name() {
        return cn;
    }

    @Override
    public String code() {
        return en;
    }

    @Override
    public String type() {
        return getClass().getSimpleName();
    }

    @Override
    public Kv renderMeta() {
        Kv kv = Kv.create();
        kv.put("cn", cn);
        kv.put("en", en);
        return kv;
    }
}
