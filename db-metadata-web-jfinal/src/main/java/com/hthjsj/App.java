package com.hthjsj;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.io.Files;
import com.hthjsj.web.component.ComponentType;
import com.jfinal.kit.PathKit;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readLines(new File(PathKit.getRootClassPath() + "/jsonTemplate.json"), Charset.defaultCharset());
        String result = Joiner.on("").join(lines);
        System.out.println(result);
        JSONObject jsonObject = JSON.parseObject(result);
        for (ComponentType t : ComponentType.values()) {
            if (jsonObject.get(t.getCode()) == null) {
                System.out.println(t.getCode());
            }
            System.out.println(jsonObject.get(t.getCode()));
        }


    }
}
