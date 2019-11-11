package com.hthjsj.web.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.io.Files;
import com.google.common.reflect.ClassPath;
import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.web.ServiceManager;
import com.jfinal.core.JFinal;
import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
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

    /**
     * 1. 自动scanPackage 进行注册
     * 2. 自动依jsonTemplate.json 为准 进行组件配置更新
     */
    public void init() {
        autoRegister();
        Kv staticGlobalConfig = Kv.create().set(loadTmplConfigFromFile().getInnerMap());
        for (Map.Entry<ComponentType, Class<? extends Component>> componentTypeClassEntry : registry.entrySet()) {
            ComponentType type = componentTypeClassEntry.getKey();
            if (!ServiceManager.componentService().newDefault(type.getCode(), JSON.parseObject(staticGlobalConfig.getStr(type.getCode()), Kv.class))) {
                if (JFinal.me().getConstants().getDevMode()) {
                    ServiceManager.componentService().updateDefault(type.getCode(), JSON.parseObject(staticGlobalConfig.getStr(type.getCode()), Kv.class));
                }
            }
        }
    }

    /**
     * 扫描 com.hthjsj.web.component 包下,根据ComponentType 进行注册;
     */
    private void autoRegister() {
        try {
            ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive("com.hthjsj.web.component")) {
                Class<?> clazz = classInfo.load();
                if (ComponentType.V(clazz.getSimpleName()) != ComponentType.UNKNOWN) {
                    registry.put(ComponentType.V(clazz.getSimpleName()), (Class<? extends Component>) clazz);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public JSONObject loadTmplConfigFromFile() {
        List<String> lines = null;
        try {
            lines = Files.readLines(new File(PathKit.getRootClassPath() + "/jsonTemplate.json"), Charset.defaultCharset());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        String result = Joiner.on("").join(lines);
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

