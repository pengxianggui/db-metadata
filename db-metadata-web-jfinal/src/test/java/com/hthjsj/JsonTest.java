package com.hthjsj;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hthjsj.web.UtilKit;
import com.jfinal.kit.Kv;

import java.util.Map;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class JsonTest {

    public static void main(String[] args) {

//        t1();
        mergeTest();
    }

    public static void t1() {
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

    public static void mergeTest() {
        String str1 = "{\n" + "  \"meta_field\": {\n" + "    \"FormTmpl\": {\n" + "      \"config\": {\n" + "        \"conf\": {\n" + "          \"size\": \"mini\"\n"
                + "        },\n" + "        \"name\": \"config\",\n" + "        \"label\": \"配置1\",\n" + "        \"inline\": false,\n"
                + "        \"component_name\": \"MiniFormBox\"\n" + "      }\n" + "    }\n" + "  }\n" + "}\n";
        String str2 = "{\n" + "  \"meta_field\": {\n" + "    \"FormTmpl\": {\n" + "      \"config\": {\n" + "        \"conf\": {\n" + "          \"size\": \"mini\"\n"
                + "        },\n" + "        \"name\": \"config\",\n" + "        \"label\": \"配置12\",\n" + "        \"inline\": true,\n"
                + "        \"component_name\": \"_MiniFOrmBox\",\"dfjie\":\"extention11\"\n" + "      }\n" + "    }\n" + "  }\n" + "}\n";
        JSONObject json1 = JSON.parseObject(str1);
        JSONObject json2 = JSON.parseObject(str2);
        Map result = UtilKit.deepMerge(json1.getInnerMap(), json2.getInnerMap(), true);

        System.out.println(JSON.toJSONString(result, SerializerFeature.PrettyFormat));
    }

    static class User {

        String name;

        String age;

        public User(String name, String age) {
            this.name = name;
            this.age = age;
        }
    }
}
