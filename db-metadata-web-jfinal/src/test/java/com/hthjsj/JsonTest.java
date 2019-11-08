package com.hthjsj;

import com.alibaba.fastjson.JSON;
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
        System.out.println(kv.toJson());
        System.out.println(JSON.toJSONString(kv));
        System.out.println(JSON.toJSONString(new User("tom", "hihi")));
    }
}
