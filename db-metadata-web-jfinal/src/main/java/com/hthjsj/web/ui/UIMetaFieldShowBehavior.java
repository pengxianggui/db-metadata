package com.hthjsj.web.ui;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
interface UIMetaFieldShowBehavior {

    /**
     * TODO 预留扩展机制
     *
     * @return
     */
    @JSONField(name = "showType") String showType();

    @JSONField(name = "showType") void showType(String value);

    @JSONField(name = "placeHolder") String placeHolder();

    @JSONField(name = "placeHolder") void placeHolder(String value);

    @JSONField(name = "validator") String validator();

    @JSONField(name = "validator") void validator(String value);

    @JSONField(name = "defaultVal") String defaultVal();

    @JSONField(name = "defaultVal") void defaultVal(String value);

    @JSONField(name = "formatter") String formatter();

    @JSONField(name = "formatter") void formatter(String value);
}
