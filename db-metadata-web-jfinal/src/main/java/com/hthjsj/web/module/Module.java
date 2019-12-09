package com.hthjsj.web.module;

import com.hthjsj.analysis.component.ComponentType;
import com.jfinal.kit.Kv;

import java.util.List;
import java.util.Map;

/**
 * <p> @Date : 2019/12/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface Module {

    List<String> metaObjects();

    Map<String, ComponentType> componentsMap();

    Kv execute();
}
