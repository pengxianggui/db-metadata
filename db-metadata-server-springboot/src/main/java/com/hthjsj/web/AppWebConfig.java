package com.hthjsj.web;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.hthjsj.AnalysisConfig;
import com.hthjsj.SpringAnalysisManager;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.web.component.Components;
import com.hthjsj.web.controller.CoreRouter;
import com.hthjsj.web.controller.FileController;
import com.hthjsj.web.feature.FeatureRouter;
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
import com.hthjsj.web.kit.UtilKit;
import com.hthjsj.web.menu.MenuModuleRouter;
import com.hthjsj.web.ui.ComputeKit;
import com.hthjsj.web.ui.meta.CCUUConfigExtension;
import com.hthjsj.web.ui.meta.InstanceConfigExtension;
import com.hthjsj.web.ui.meta.MetaFieldConfigExtension;
//import com.hthjsj.web.upload.UploadController;
import com.hthjsj.web.user.UserManager;
import com.hthjsj.web.user.UserRouter;
import com.hthjsj.web.user.auth.JsonUserPermit;
import com.hthjsj.web.user.auth.MRManager;
import com.hthjsj.web.user.auth.jfinal.JFinalResourceLoader;
import com.jfinal.config.*;
import com.jfinal.json.FastJsonRecordSerializer;
import com.jfinal.kit.Prop;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;

import java.io.File;

/**
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 *
 * @deprecated 请使用 {@link DynamicWebConfigFacade}
 */
@Deprecated
public class AppWebConfig extends JFinalConfig {

    public static void main(String[] args) {
        UndertowServer.start(AppWebConfig.class, 8888, true);
    }

    /**
     * 配置文件初始化,顺序
     * /config.properties
     * /config/config.properties
     * jar->config.properties
     */
    public void initProp(Prop prop) {
        if (this.prop == null) {
            this.prop = prop;
            File configPropFile = UtilKit.stairsLoad(AnalysisConfig.CONFIG_NAME, "config");
            if (configPropFile != null) {
                this.prop.appendIfExists(configPropFile);
            }
            AnalysisConfig.initProp(this.prop);
        }
    }

    @Override
    public void configConstant(Constants me) {
        initProp(AnalysisConfig.me().getProp());
        me.setDevMode(prop.getBoolean("devMode", false));
        me.setJsonFactory(new CrackFastJsonFactory());
        me.setRenderFactory(new ErrorJsonRenderFactory());
    }

    @Override
    public void configRoute(Routes me) {
        me.setMappingSuperClass(true);
        me.add(new CoreRouter());
        me.add(new FeatureRouter());
        if (getPropertyToBoolean(AppConst.NEED_LOGIN)) {
            me.add(new UserRouter());
        }
        me.add(new MenuModuleRouter());
        me.add(new DefaultRouter() {
            @Override
            public void config() {
//                add("/file/upload", UploadController.class);
//                add("/file", FileController.class);
            }
        });
    }

    @Override
    public void configEngine(Engine me) {
    }

    @Override
    public void configPlugin(Plugins me) {
        AnalysisConfig analysisConfig = AnalysisConfig.me();
        analysisConfig.initDefaultDbSource();
        analysisConfig.getPlugins().forEach(p -> me.add(p));
    }

    @Override
    public void onStart() {
        SerializeConfig.getGlobalInstance().put(Record.class, new FastJsonRecordSerializer());

        if (prop.getBoolean(AppConst.COMPONENT_ALLOW_REPLACE)) {
            //component register
            Components.me().init();
        }
        Components.me().addAutoInitComponents(ComponentType.SEARCHVIEW)
                .addAutoInitComponents(ComponentType.TABLEVIEW)
                .addAutoInitComponents(ComponentType.TABLETREEVIEW)
                .addAutoInitComponents(ComponentType.FORMVIEW);

        //dictionary register
        Dicts.me().init();

        EventKit.init(false, "db-event-bus");
        EventKit.register(new FormListener());
        ExtensibleListenerManager.me().addFormListeners(new TestFormExtensibleListener());
        //Auto import anyConfig from json file;
        if (prop.getBoolean(AppConst.CONFIG_ALLOW_REPLACE)) {
            InitKit.me().updateMetaObjectConfig().updateInstanceConfig();
        }

        if (prop.getBoolean(AppConst.NEED_AUTH)) {
            MRManager mrManager = MRManager.me();
            mrManager.addLoader(new JFinalResourceLoader());
            mrManager.setPermit(new JsonUserPermit("userMRmap.json"));
            mrManager.load();
        }

        SpringAnalysisManager.me().addMetaFieldConfigExtension(new MetaFieldConfigExtension());
        ComputeKit.addInstanceExtension(new InstanceConfigExtension());
        ComputeKit.addInstanceExtension(new CCUUConfigExtension());
    }

    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new ExceptionIntercept());
        me.add(new JsonParamIntercept());
        if (prop.getBoolean(AppConst.NEED_LOGIN)) {
            me.add(UserManager.me().getUserIntercept());
        }
        if (prop.getBoolean(AppConst.NEED_AUTH)) {
            me.add(MRManager.me().getMrAuthIntercept());
        }
    }

    @Override
    public void configHandler(Handlers me) {
    }
}
