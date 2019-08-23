package com.hthjsj.analysis.meta;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class TestMain {
    
    public static void main(String[] args) {
        
        String json = "{\"isAdd\":true,\"isDisable\":false,\"isEdit\":false,\"isListShow\":false,\"isMultiple\":false,\"isQuery\":false,\"isRequired\":false,\"isUpdate\":false}";
        JSONObject jsonObject = JSON.parseObject(json);
        System.out.println(jsonObject);
        
        System.out.println(JSON.toJSONString(new MetaFieldAccess() {
            
            @Override
            public boolean isQuery() {
                return false;
            }
            
            @Override
            public void isQuery(boolean value) {
            
            }
            
            @Override
            public boolean isListShow() {
                return false;
            }
            
            @Override
            public void isListShow(boolean value) {
            
            }
            
            @Override
            public boolean isDisable() {
                return false;
            }
            
            @Override
            public void isDisable(boolean value) {
            
            }
            
            @Override
            public boolean isAdd() {
                return false;
            }
            
            @Override
            public void isAdd(boolean value) {
            
            }
            
            @Override
            public boolean isUpdate() {
                return false;
            }
            
            @Override
            public void isUpdate(boolean value) {
            
            }
            
            @Override
            public boolean isEdit() {
                return false;
            }
            
            @Override
            public void isEdit(boolean value) {
            
            }
            
            @Override
            public String addStatus() {
                return "11";
            }
            
            @Override
            public void addStatus(String value) {
            
            }
            
            @Override
            public String updateStatus() {
                return "fa";
            }
            
            @Override
            public void updateStatus(String value) {
            
            }
            
            @Override
            public boolean isRequired() {
                return false;
            }
            
            @Override
            public void isRequired(boolean value) {
            
            }
            
            @Override
            public boolean isMultiple() {
                return false;
            }
            
            @Override
            public void isMultiple(boolean value) {
            
            }
        }));
        
        System.out.println(JSON.parseObject("{\"addStatus\":\"11\",\"isAdd\":false,\"isDisable\":false,\"isEdit\":false,\"isListShow\":false,\"isMultiple\":false,\"isQuery\":false,\"isRequired\":false,\"isUpdate\":false,\"updateStatus\":\"fa\"}"));
    }
}
