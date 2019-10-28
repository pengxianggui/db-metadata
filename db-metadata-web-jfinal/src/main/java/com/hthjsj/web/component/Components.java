package com.hthjsj.web.component;

import com.hthjsj.analysis.meta.Component;
import com.jfinal.aop.Aop;
import com.jfinal.kit.Kv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
final public class Components {

    private static final Components me = new Components();

    private final List<Class<? extends Component>> pluginList = new ArrayList<Class<? extends Component>>();

    private final Map<ComponentType, Class<? extends Component>> registry = new HashMap<>();

    public static Components me() {
        return me;
    }

    public Components add(ComponentType type, Class<? extends Component> clazz) {
        registry.put(type, clazz);
        pluginList.add(clazz);
        return this;
    }

    public Map<ComponentType, Class<? extends Component>> getRegistry() {
        return registry;
    }

    public void init() {
        for (Map.Entry<ComponentType, Class<? extends Component>> componentTypeClassEntry : registry.entrySet()) {
            try {
                ComponentType type = componentTypeClassEntry.getKey();
                Component componentInstance = componentTypeClassEntry.getValue().newInstance();
                Aop.get(ComponentService.class).register(type, Kv.create());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}

