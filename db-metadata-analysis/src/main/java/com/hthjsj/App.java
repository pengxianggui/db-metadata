package com.hthjsj;

import com.alibaba.fastjson.JSON;
import com.hthjsj.analysis.db.MysqlService;
import com.hthjsj.analysis.db.Table;

/**
 * Hello world!
 */
public class App {
    
    public static void main(String[] args) {
        
        AppConfig appConfig = new AppConfig();
        appConfig.start();
        
        Table table = new MysqlService().getTable("eova", "eova_menu");
        System.out.println(JSON.toJSON(table));
    }
}
