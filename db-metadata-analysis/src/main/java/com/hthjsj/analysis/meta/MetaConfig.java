package com.hthjsj.analysis.meta;

import com.alibaba.fastjson.JSONObject;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface MetaConfig {
    
    String MODULE_OBJECT = "meta_object";
    String MODULE_FIELD  = "meta_field";
    
    String module();
    
    JSONObject toJson();
    
    String moduleCode();
    
    String getVersion();
    
    String getConfig();
    
}
