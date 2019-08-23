package com.hthjsj.analysis.meta;

import com.alibaba.fastjson.JSON;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class TestJsonInterface {
    
    public static void main(String[] args) {
        
        String s = JSON.toJSONString(new MetaObjectConfig());
        
        System.out.println(s);
        
    }
}
