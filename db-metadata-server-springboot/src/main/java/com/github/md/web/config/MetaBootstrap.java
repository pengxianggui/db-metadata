package com.github.md.web.config;

import com.github.md.web.component.Components;
import com.github.md.web.feature.tree.PreventInfiniteLoopAop;
import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.aop.PointCutChain;
import com.github.md.web.jms.EventKit;
import com.github.md.web.jms.FormListener;
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
        startInit();
    }

    private void startInit() {
        stepList.add(new StaticDictInitStep());
        stepList.add(new ComponentInitStep());
        stepList.add(new MetaObjectAndInstanceInitStep());
        stepList.add(new ExtensionInitStep());
        stepList.add(new EventInitStep());
        stepList.forEach(s -> s.init());


        /**
         * 待迁移代码
         */

        //        MRManager mrManager = MRManager.me();
        //        mrManager.addLoader(new JFinalResourceLoader());
        //        mrManager.setPermit(new JsonUserPermit("userMRmap.json"));
        //        mrManager.load();

        //        me.add(new UserRouter());
        //        me.add(new UserIntercept());
        //        me.add(new MRAuthIntercept());


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
                          .addAutoInitComponents(ComponentType.TABLETREEVIEW).addAutoInitComponents(ComponentType.FORMVIEW);
            }
        }
    }

    class MetaObjectAndInstanceInitStep implements InitStep {

        @Override
        public void init() {
            if (metaProperties.getServer().getMetaObject().isReplaceFromJsonFile()) {
                InitKit.me().updateMetaObjectConfig().updateInstanceConfig();
            }
        }
    }

    class StaticDictInitStep implements InitStep {

        @Override
        public void init() {
            Dicts.me().init();
        }
    }
}
