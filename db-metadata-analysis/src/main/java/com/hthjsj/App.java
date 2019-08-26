package com.hthjsj;

import com.alibaba.fastjson.JSON;
import com.hthjsj.analysis.db.MysqlService;
import com.hthjsj.analysis.db.Table;
import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.analysis.meta.MetaObject;

/**
 * Hello world!
 */
public class App {
    
    public static void main(String[] args) {
        
        AppConfig appConfig = new AppConfig();
        appConfig.start();
    
        Table table = new MysqlService().getTable("metadata", "meta_field");
        System.out.println(JSON.toJSON(table));
    
        MetaObject metaObject = new DbMetaService().findByCode("sys_meta_object");
        System.out.println(JSON.toJSONString(metaObject));
    }
}
