package com.hthjsj;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MapTest {
    public static void main(String[] args) {
//        Map<String, String> map = new HashMap<>();
//        map.put("fname", "tomcat");
//        map.put("lname", "jerry");
//        map.merge("lname", 1, (oldValue, newValue) -> oldValue + newValue);
//        map.merge("count", 1, (oldValue, newValue) -> oldValue + newValue);



        Map<String, Object> map1 = new HashMap<>();

        map1.put("one", new String[] { "hi", "hihi", "ihih" });
        map1.put("two", "sam");


        Object o = map1.get("one");
        if (o instanceof String[]) {
            System.out.println("yes");
        }
        if (o instanceof String) {
            System.out.println("he");
        }
    }
}
