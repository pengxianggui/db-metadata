package com.hthjsj;

import com.alibaba.fastjson.JSON;

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

        System.out.println(JSON.toJSONString(new User("tom", "hihi")));
    }
}
