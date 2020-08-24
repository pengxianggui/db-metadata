package com.hthjsj.web;

import com.hthjsj.AnalysisConfig;
import com.hthjsj.AnalysisManager;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.web.component.Components;
import com.hthjsj.web.jfinal.ExceptionIntercept;
import com.hthjsj.web.jfinal.JsonParamIntercept;
import com.hthjsj.web.jfinal.fastjson.CrackFastJsonFactory;
import com.hthjsj.web.jfinal.render.ErrorJsonRenderFactory;
import com.hthjsj.web.jms.EventKit;
import com.hthjsj.web.jms.ExtensibleListenerManager;
import com.hthjsj.web.jms.FormListener;
import com.hthjsj.web.jms.TestFormExtensibleListener;
import com.hthjsj.web.kit.Dicts;
import com.hthjsj.web.kit.InitKit;
import com.hthjsj.web.ui.ComputeKit;
import com.hthjsj.web.ui.meta.CCUUConfigExtension;
import com.hthjsj.web.ui.meta.InstanceConfigExtension;
import com.hthjsj.web.ui.meta.MetaFieldConfigExtension;
import com.hthjsj.web.user.UserIntercept;
import com.hthjsj.web.user.UserRouter;
import com.jfinal.config.*;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p> @Date : 2020/7/27 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class DynamicWebConfig extends JFinalConfig {

    private JFinalConfig coreConfig = new ExtJFinalConfig() {

        @Override
        public void configConstant(Constants me) {
            me.setDevMode(AppConst.getProp().getBoolean("devMode", false));
            me.setJsonFactory(new CrackFastJsonFactory());
            me.setRenderFactory(new ErrorJsonRenderFactory());
        }

        @Override
        public void configPlugin(Plugins me) {
            AnalysisConfig analysisConfig = AnalysisConfig.me();
            analysisConfig.initDefaultDbSource();
            analysisConfig.getPlugins().forEach(p -> me.add(p));
        }

        @Override
        public void configRoute(Routes me) {
            me.setMappingSuperClass(true);
        }

        @Override
        public void configInterceptor(Interceptors me) {
            me.add(new ExceptionIntercept());
            me.add(new JsonParamIntercept());
        }
    };

    private JFinalConfig userConfig = new ExtJFinalConfig() {

        @Override
        public void configRoute(Routes me) {
            if (AppConst.getProp().getBoolean(AppConst.NEED_LOGIN)) {
                me.add(new UserRouter());
            }
        }

        @Override
        public void configInterceptor(Interceptors me) {
            if (AppConst.getProp().getBoolean(AppConst.NEED_LOGIN)) {
                me.add(new UserIntercept());
            }
        }
    };

    private JFinalConfig componentConfig = new ExtJFinalConfig() {

        @Override
        public void onStart() {
            if (AppConst.getProp().getBoolean(AppConst.COMPONENT_ALLOW_REPLACE)) {
                //component register
                Components.me().init();
                Components.me()
                          .addAutoInitComponents(ComponentType.SEARCHVIEW)
                          .addAutoInitComponents(ComponentType.TABLEVIEW)
                          .addAutoInitComponents(ComponentType.FORMVIEW);
                //Auto import anyConfig from json file;
                if (AppConst.getProp().getBoolean(AppConst.CONFIG_ALLOW_REPLACE)) {
                    InitKit.me().importMetaObjectConfig().importInstanceConfig();
                }
            }
            AnalysisManager.me().addMetaFieldConfigExtension(new MetaFieldConfigExtension());
            ComputeKit.addInstanceExtension(new InstanceConfigExtension());
            ComputeKit.addInstanceExtension(new CCUUConfigExtension());
        }
    };

    private JFinalConfig dictConfig = new ExtJFinalConfig() {

        @Override
        public void onStart() {
            //dictionary register
            Dicts.me().init();
        }
    };

    private JFinalConfig eventConfig = new ExtJFinalConfig() {

        @Override
        public void onStart() {
            EventKit.init(false, "db-event-bus");
            EventKit.register(new FormListener());
            ExtensibleListenerManager.me().addFormListeners(new TestFormExtensibleListener());
        }
    };

    private final Collection<JFinalConfig> extConfig = new ArrayList<>(0);

    public static void main(String[] args) {
        UndertowServer.start(DynamicWebConfig.class, 8888, true);
    }

    public void addExtConfig(JFinalConfig jFinalConfig) {
        extConfig.add(jFinalConfig);
    }

    public void coreConfig(JFinalConfig coreConfig) {
        this.coreConfig = coreConfig;
    }

    public void userConfig(JFinalConfig userConfig) {
        this.userConfig = userConfig;
    }

    public void componentConfig(JFinalConfig componentConfig) {
        this.componentConfig = componentConfig;
    }

    public void dictConfig(JFinalConfig dictConfig) {
        this.dictConfig = dictConfig;
    }

    public void eventConfig(JFinalConfig eventConfig) {
        this.eventConfig = eventConfig;
    }

    @Override
    public void configConstant(Constants me) {
        coreConfig.configConstant(me);
        userConfig.configConstant(me);
        componentConfig.configConstant(me);
        dictConfig.configConstant(me);
        eventConfig.configConstant(me);
        extConfig.forEach(c -> c.configConstant(me));
    }

    @Override
    public void configRoute(Routes me) {
        coreConfig.configRoute(me);
        userConfig.configRoute(me);
        componentConfig.configRoute(me);
        dictConfig.configRoute(me);
        eventConfig.configRoute(me);
        extConfig.forEach(c -> c.configRoute(me));
    }

    @Override
    public void configEngine(Engine me) {
        coreConfig.configEngine(me);
        userConfig.configEngine(me);
        componentConfig.configEngine(me);
        dictConfig.configEngine(me);
        eventConfig.configEngine(me);
        extConfig.forEach(c -> c.configEngine(me));
    }

    @Override
    public void configPlugin(Plugins me) {
        coreConfig.configPlugin(me);
        userConfig.configPlugin(me);
        componentConfig.configPlugin(me);
        dictConfig.configPlugin(me);
        eventConfig.configPlugin(me);
        extConfig.forEach(c -> c.configPlugin(me));
    }

    @Override
    public void configInterceptor(Interceptors me) {
        coreConfig.configInterceptor(me);
        userConfig.configInterceptor(me);
        componentConfig.configInterceptor(me);
        dictConfig.configInterceptor(me);
        eventConfig.configInterceptor(me);
        extConfig.forEach(c -> c.configInterceptor(me));
    }

    @Override
    public void configHandler(Handlers me) {
        coreConfig.configHandler(me);
        userConfig.configHandler(me);
        componentConfig.configHandler(me);
        dictConfig.configHandler(me);
        eventConfig.configHandler(me);
        extConfig.forEach(c -> c.configHandler(me));
    }

    @Override
    public void onStart() {
        coreConfig.onStart();
        userConfig.onStart();
        componentConfig.onStart();
        dictConfig.onStart();
        eventConfig.onStart();
        extConfig.forEach(c -> c.onStart());
    }
}