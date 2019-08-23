package com.hthjsj.analysis.meta;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
interface MetaFieldAccess {
    
    @JSONField(name = "isQuery")
    boolean isQuery();
    
    void isQuery(boolean value);
    
    @JSONField(name = "isListShow")
    boolean isListShow();
    
    void isListShow(boolean value);
    
    @JSONField(name = "isDisable")
    boolean isDisable();
    
    void isDisable(boolean value);
    
    @JSONField(name = "isAdd")
    boolean isAdd();
    
    void isAdd(boolean value);
    
    @JSONField(name = "isUpdate")
    boolean isUpdate();
    
    void isUpdate(boolean value);
    
    @JSONField(name = "isEdit")
    boolean isEdit();
    
    void isEdit(boolean value);
    
    @JSONField(name = "addStatus")
    String addStatus();
    
    void addStatus(String value);
    
    @JSONField(name = "updateStatus")
    String updateStatus();
    
    void updateStatus(String value);
    
    @JSONField(name = "isRequired")
    boolean isRequired();
    
    void isRequired(boolean value);
    
    @JSONField(name = "isMultiple")
    boolean isMultiple();
    
    void isMultiple(boolean value);
}
