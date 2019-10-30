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
interface UIMetaFieldAccess {

    @JSONField(name = "isQuery")
    boolean isQuery();

    void isQuery(boolean value);

    @JSONField(name = "isListShow")
    boolean isListShow();

    void isListShow(boolean value);

    @JSONField(name = "isDisable")
    boolean isDisable();

    void isDisable(boolean value);

    /**
     * isAdd/isUpdate 控制sql生成逻辑
     *
     * @return
     */
    @JSONField(name = "isAdd")
    boolean isAdd();

    void isAdd(boolean value);

    @JSONField(name = "isUpdate")
    boolean isUpdate();

    void isUpdate(boolean value);

    /**
     * addStatus/updateStatus 控制展示行为
     * 正常/隐藏/只读/禁用
     * 100/50/30/10
     * @return
     */
    @JSONField(name = "addStatus")
    int addStatus();

    void addStatus(int value);

    @JSONField(name = "updateStatus")
    int updateStatus();

    void updateStatus(int value);

    @JSONField(name = "isRequired")
    boolean isRequired();

    void isRequired(boolean value);

    @JSONField(name = "isMultiple")
    boolean isMultiple();

    void isMultiple(boolean value);
}
