package com.hthjsj.web.ui;

import com.alibaba.fastjson.annotation.JSONField;
import com.hthjsj.web.component.ComponentType;

/**
 * <p> @Date : 2019/10/29 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public abstract class FieldBehavior {

    @JSONField(name = "component_name")
    abstract ComponentType compType();

    abstract void showType(ComponentType type);

    @JSONField(name = "placeHolder")
    abstract String placeHolder();

    @JSONField(name = "placeHolder")
    abstract void placeHolder(String value);

    @JSONField(name = "defaultVal")
    abstract String defaultVal();

    @JSONField(name = "defaultVal")
    abstract void defaultVal(String value);
}
