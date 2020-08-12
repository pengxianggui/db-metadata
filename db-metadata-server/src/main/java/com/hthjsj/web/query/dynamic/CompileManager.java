package com.hthjsj.web.query.dynamic;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class CompileManager {

    @Getter
    private static final Collection<Class> compileDataStructure = new ArrayList<>();

    public static void register(Class<? extends VariableDefinition> variableDefinition) {
        compileDataStructure.add(variableDefinition);
    }
}
