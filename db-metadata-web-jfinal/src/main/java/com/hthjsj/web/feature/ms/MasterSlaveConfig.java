package com.hthjsj.web.feature.ms;

import com.hthjsj.analysis.meta.MetaData;
import com.hthjsj.web.feature.Feature;
import com.jfinal.kit.Kv;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> @Date : 2019/12/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MasterSlaveConfig extends MetaData implements Feature {

    Master master;

    List<Slave> slaves;

    public String masterCode() {
        return master.getObjectCode();
    }

    public String masterKey() {
        return master.getPrimaryKey();
    }

    public Slave get(int i) {
        return slaves.get(i);
    }

    @Override
    public List<String> metaObjects() {
        List<String> objectCodes = slaves.stream().map(Slave::getObjectCode).collect(Collectors.toList());
        objectCodes.add(master.getObjectCode());
        return objectCodes;
    }

    @Override
    public Kv execute() {
        return this;
    }

    @Data
    class Master {

        String objectCode;

        String primaryKey;
    }

    @Data
    class Slave {

        String objectCode;

        String foreignFieldCode;

        int order;
    }
}
