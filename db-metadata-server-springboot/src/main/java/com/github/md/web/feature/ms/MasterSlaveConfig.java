package com.github.md.web.feature.ms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.md.analysis.component.ComponentType;
import com.github.md.web.feature.FeatureConfig;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * WARN :Fastjson 默认不支持解析嵌套内部类,parseObject时对于无法确认的属性会统一使用JsonObject 和JsonArray来解析;
 * 此处通过getter方法来对master,slaves的赋值;
 * <p>
 * 主子表功能配置数据结构可见:
 * <pre>
 * {
 *     "master": {
 *         "config": {
 *             "objectCode": null,
 *             "primaryKey": null,
 *         },
 *         "instanceCodes": {
 *             "SearchView": null,
 *             "TableView": null,
 *             "FormView": null
 *         }
 *     },
 *     "slaves": [
 *         {
 *             "config": {
 *                 "objectCode": null,
 *                 "foreignPrimaryKey": null,
 *                 "order": 0
 *             },
 *             "instanceCodes": {
 *                 "SearchView": null,
 *                 "TableView": null,
 *                 "FormView": null
 *             }
 *         }
 *     ]
 * }
 * </pre>
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
        return getSlaves().stream()
                .filter(s -> objectCode.equalsIgnoreCase(s.getConfig().getObjectCode()))
                .findFirst()
                .get();
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

    /**
     * 所有的元对象编码构成
     *
     * @return
     */
    public List<String> metaObjects() {
        List<String> objectCodes = getSlaves().stream().map(slave -> slave.getConfig().getObjectCode())
                .collect(Collectors.toList());

        objectCodes.add(getMaster().getConfig().getObjectCode());
        return objectCodes;
    }

    @Data
    static class Master {
        private Config config;
        private Map<String, String> instanceCodes;

        public String getInstanceCode(ComponentType componentType) {
            return instanceCodes.get(componentType.getCode());
        }

        @Data
        static class Config {
            private String objectCode;
            private String primaryKey;
        }
    }

    @Data
    static class Slave {
        private Config config;
        private Map<String, String> instanceCodes;

        public String getInstanceCode(ComponentType componentType) {
            return instanceCodes.get(componentType.getCode());
        }

        @Data
        static class Config {
            private String objectCode;
            private String foreignPrimaryKey;
            private Integer order;
        }
    }

}
