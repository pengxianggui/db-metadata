package com.hthjsj.web.query.dynamic;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class ExtCompileDataContext implements Context {

    @Override
    public Map data() {
        Map data = new HashMap();
        for (Class<VariableDefinition> variableDefinitionClass : CompileManager.getCompileDataStructure()) {
            try {
                VariableDefinition instance = variableDefinitionClass.newInstance();
                data.put(instance.name(), instance.init());
            } catch (InstantiationException | IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        }
        return data;
    }
}
