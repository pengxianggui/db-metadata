package com.hthjsj;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.Kv;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class JsonTest {

    static class User {

        String name;

        String age;

        public User(String name, String age) {
            this.name = name;
            this.age = age;
        }
    }

    public static void main(String[] args) {

        Kv kv = Kv.create();
        kv.set("one", 1);
        kv.set("two", true);
        kv.set("three", "fie");
        kv.set("four", new String[] { "dfadf", "fasdf", "ff" });


        System.out.println(kv.toJson());
        JSONObject jsonObject = JSON.parseObject(kv.toJson());
        System.out.println(jsonObject);
        System.out.println(JSON.parseObject(kv.toJson(), Kv.class));
        System.out.println(JSON.toJSONString(kv));
        System.out.println(JSON.toJSONString(new User("tom", "hihi")));

        System.out.println();
    }
}
