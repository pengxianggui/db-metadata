package com.github.md.web.config;

import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.aop.PointCutChain;
import com.github.md.web.ServiceManager;
import com.github.md.web.component.Components;
import com.github.md.web.event.EventKit;
import com.github.md.web.event.FormListener;
import com.github.md.web.feature.tree.PreventInfiniteLoopAop;
import com.github.md.web.kit.Dicts;
import com.github.md.web.kit.InitKit;
import com.github.md.web.ui.ComputeKit;
import com.github.md.web.ui.meta.CCUUConfigExtension;
import com.github.md.web.ui.meta.InstanceConfigExtension;
import com.github.md.web.ui.meta.MetaFieldConfigExtension;

import java.util.LinkedList;
import java.util.List;

/**
 * Specially perform some initialization actions
 *
 * <p> @Date : 2021/9/8 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaBootstrap {

    MetaProperties metaProperties;

    List<InitStep> stepList = new LinkedList<>();

    MetaBootstrap(MetaProperties metaProperties) {
        this.metaProperties = metaProperties;
        preCheck();
        startInit();
    }

    private void preCheck() {
        if (!metaProperties.isDevMode() && !metaProperties.getServer().isEnableCertification()) {
            throw new IllegalArgumentException("非开发模式, 不允许禁用认证。请开启认证(md.server.enable-certification设为true)，或启用开发模式(md.dev-mode:true)");
        }
    }

    private void startInit() {
        stepList.add(new StaticDictInitStep());
        stepList.add(new ComponentInitStep());
        stepList.add(new MetaDataInit());
        stepList.add(new MetaObjectAndInstanceInitStep());
        stepList.add(new ExtensionInitStep());
        stepList.add(new EventInitStep());
        stepList.forEach(s -> s.init());
    }

    interface InitStep {

        void init();
    }

    class EventInitStep implements InitStep {

        @Override
        public void init() {
            EventKit.init(false, "db-event-bus");
            EventKit.register(new FormListener());
            //            ExtensibleListenerManager.me().addFormListeners(new TestFormExtensibleListener());
        }
    }

    class ExtensionInitStep implements InitStep {

        @Override
        public void init() {
            SpringAnalysisManager.me().addMetaFieldConfigExtension(new MetaFieldConfigExtension());
            ComputeKit.addInstanceExtension(new InstanceConfigExtension());
            ComputeKit.addInstanceExtension(new CCUUConfigExtension());

            PointCutChain.registerGlobalPointCut(new PreventInfiniteLoopAop());
        }
    }

    class ComponentInitStep implements InitStep {

        @Override
        public void init() {
            if (metaProperties.getServer().getComponent().isReplaceFromJsonFile()) {
                Components.me().init();
                Components.me().addAutoInitComponents(ComponentType.SEARCHVIEW).addAutoInitComponents(ComponentType.TABLEVIEW)
                        .addAutoInitComponents(ComponentType.TABLETREEVIEW).addAutoInitComponents(ComponentType.FORMVIEW)
                        .addAutoInitComponents(ComponentType.TREEVIEW);
            }
        }
    }

    class MetaObjectAndInstanceInitStep implements InitStep {

        @Override
        public void init() {
            if (metaProperties.getServer().getMetaObject().isReplaceFromJsonFile()) {
                InitKit.me().updateMetaObjectConfig().updateInstanceConfigByMetaObject();
            }
            if (metaProperties.getServer().getComponent().isReplaceFromJsonFile()) {
                InitKit.me().updateInstanceConfig();
            }
        }
    }

    class StaticDictInitStep implements InitStep {

        @Override
        public void init() {
            Dicts.me().init();
        }
    }

    /**
     * 元数据初始化
     */
    class MetaDataInit implements InitStep {

        @Override
        public void init() {
            int size = ServiceManager.metaService().findAll().size();
            if (size == 0) { // 尚未初始化
                InitKit.me().initSysMeta(); // 初始化系统元数据

                // 根据固定文件(defaultObject.json)更新元对象/元字段配置
                InitKit.me().updateMetaObjectConfig()       // 根据defaultObject.json更新元对象/原字段配置
                        .updateInstanceConfigByMetaObject() // 依据已入库的元对象/原字段配置推导UI实例配置
                        .updateInstanceConfig()            // 依据defaultInstance.json覆盖已入库的UI实例配置
                        .updateFeatureConfig(); // 更新内置功能
            }
        }
    }
}
