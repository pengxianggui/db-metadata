package com.hthjsj.web.feature.ms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hthjsj.web.feature.FeatureConfig;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * WARN :Fastjson 默认不支持解析嵌套内部类,parseObject时对于无法确认的属性会统一使用JsonObject 和JsonArray来解析;
 * 此处通过getter方法来对master,slaves的赋值;
 * <p>
 * 静态数据见resources/ms.json
 *
 * <p> @Date : 2019/12/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Data
public class MasterSlaveConfig extends FeatureConfig {

    private Master master;

    private List<Slave> slaves;

    public Slave get(int i) {
        return getSlaves().get(i);
    }

    public Slave get(String objectCode) {
        return getSlaves().stream().filter(s -> objectCode.equalsIgnoreCase(s.getObjectCode())).findFirst().get();
    }

    public Master getMaster() {
        if (master == null) {
            master = JSON.parseObject(getStr("master"), Master.class);
        }
        return master;
    }

    public List<Slave> getSlaves() {
        if (slaves == null) {
            slaves = JSONArray.parseArray(getStr("slaves"), Slave.class);
        }
        return slaves;
    }

    public List<String> metaObjects() {
        List<String> objectCodes = getSlaves().stream().map(Slave::getObjectCode).collect(Collectors.toList());
        objectCodes.add(getMaster().getObjectCode());
        return objectCodes;
    }

    @Data
    static class Master {

        private String objectCode;

        private String primaryKey;

        private String instanceCode;
    }

    @Data
    static class Slave {

        private String objectCode;

        private String foreignFieldCode;

        private String instanceCode;

        private int order;
    }
}
