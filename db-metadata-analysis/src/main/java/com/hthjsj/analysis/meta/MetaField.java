package com.hthjsj.analysis.meta;

import java.util.List;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface MetaField {
    
    boolean isPrimary();
    
    String code();
    
    String objectCode();
    
    String getCn();
    
    String getEn();
    
    String showType();
    
    String rawDataType();
    
    String javaType();
    
    String fieldCode();
    
}

interface MetaFieldShowBehavior {
    
    String placeHolder();
    
    String validator();
    
    String defaultVal();
    
    String formatter();
}

interface MetaFieldSource {
    
    List<Object> values(Object[] args);
}

interface MetaFieldAccess {
    
    boolean isQuery();
    
    boolean isListShow();
    
    boolean isDisable();
    
    boolean isAdd();
    
    boolean isUpdate();
    
    boolean isEdit();
    
    String addStatus();
    
    String updateStatus();
    
    boolean isRequired();
    
    boolean isMultiple();
}
