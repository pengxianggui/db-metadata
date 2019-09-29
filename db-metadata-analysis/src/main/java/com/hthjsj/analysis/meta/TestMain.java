package com.hthjsj.analysis.meta;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class TestMain {

    public static void main(String[] args) throws ParseException {
//        testDateend();
        System.out.println(String.class.getSimpleName());
        System.out.println(String.class.getTypeName());
    }

    public static void testJsonSerilizble() {


        String json = "{\"isAdd\":true,\"isDisable\":false,\"isEdit\":false,\"isListShow\":false,\"isMultiple\":false,\"isQuery\":false,\"isRequired\":false,\"isUpdate\":false}";
        JSONObject jsonObject = JSON.parseObject(json);
        System.out.println(jsonObject);

        System.out.println(JSON.parseObject("{\"addStatus\":\"11\",\"isAdd\":false,\"isDisable\":false,\"isEdit\":false,\"isListShow\":false,\"isMultiple\":false,\"isQuery\":false,\"isRequired\":false,\"isUpdate\":false,\"updateStatus\":\"fa\"}"));
    }

    public static void testDateend() throws ParseException {
        Date d = new Date();
        System.out.println(d.getTime());

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf1.parse(sdf1.format(d)).getTime());
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(sdf2.parse(sdf2.format(d)).getTime());
    }
}
