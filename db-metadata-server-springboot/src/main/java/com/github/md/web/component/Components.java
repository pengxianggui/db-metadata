package com.github.md.web.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.md.analysis.SpringAnalysisManager;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.reflect.ClassPath;
import com.github.md.analysis.component.Component;
import com.github.md.analysis.component.ComponentType;
import com.github.md.web.ServiceManager;
import com.github.md.web.kit.UtilKit;
import com.github.md.analysis.kit.Kv;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.InputStreamReader;
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
@Slf4j
final public class Components {

    private static final Components me = new Components();

    private static final String JSON_TEMPLATE_JSON = "data/reset/buildInComponent.json";

    private final List<Class<? extends Component>> pluginList = new ArrayList<Class<? extends Component>>();

    private final Map<ComponentType, Class<? extends Component>> registry = new HashMap<>();

    @Deprecated
    private final List<ComponentType> autoInitComponents = new ArrayList<>();

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

    @Deprecated
    public List<ComponentType> getAutoInitComponents() {
        return autoInitComponents;
    }

    @Deprecated
    public Components addAutoInitComponents(ComponentType type) {
        autoInitComponents.add(type);
        return this;
    }

    /**
     * 1. 自动scanPackage 进行注册
     * 2. 自动依jsonTemplate.json 为准 进行组件配置更新(开发模式下)
     */
    public void init() {
        log.info("即将执行组件默认配置的自动刷新...");
        autoRegister();
        Kv staticGlobalConfig = Kv.create().set(loadTmplConfigFromFile().getInnerMap());
        for (Map.Entry<ComponentType, Class<? extends Component>> componentTypeClassEntry : registry.entrySet()) {
            ComponentType type = componentTypeClassEntry.getKey();
            if (!ServiceManager.componentService().newIfNull(type.getCode(), UtilKit.getKv(staticGlobalConfig, type.getCode()), true)) {
                ServiceManager.componentService().updateDefault(type.getCode(), UtilKit.getKv(staticGlobalConfig, type.getCode()));
            }
        }
    }

    /**
     * 扫描 com.github.md.web.component 包下,根据ComponentType 进行注册;
     */
    private void autoRegister() {
        try {
            ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive("com.github.md.web.component")) {
                Class<?> clazz = classInfo.load();
                if (ComponentType.V(clazz.getSimpleName()) != ComponentType.UNKNOWN) {
                    registry.put(ComponentType.V(clazz.getSimpleName()), (Class<? extends Component>) clazz);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private JSONObject loadTmplConfigFromFile() {
        String result = "";
        try (InputStream fis = getClass().getClassLoader().getResourceAsStream(JSON_TEMPLATE_JSON)) {
            result = CharStreams.toString(new InputStreamReader(fis, Charsets.UTF_8));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        JSONObject jsonObject = JSON.parseObject(result);
        for (ComponentType t : ComponentType.values()) {
            if (jsonObject.get(t.getCode()) == null) {
                log.warn("not find default component [{}] config: {}", t.getCode(), jsonObject.get(t.getCode()));
            } else {
                log.info("find default component [{}] config: {}", t.getCode(), jsonObject.get(t.getCode()));
            }
        }
        return jsonObject;
    }
}

