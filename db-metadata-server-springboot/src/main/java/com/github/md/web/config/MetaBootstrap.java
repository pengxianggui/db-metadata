package com.github.md.web.config;

import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.analysis.meta.aop.PointCutChain;
import com.github.md.web.ServiceManager;
import com.github.md.web.aop.AuditPointCut;
import com.github.md.web.component.Components;
import com.github.md.web.event.EventKit;
import com.github.md.web.event.FormListener;
import com.github.md.web.feature.tree.PreventInfiniteLoopPointCut;
import com.github.md.web.kit.Dicts;
import com.github.md.web.kit.InitKit;
import com.github.md.web.kit.UtilKit;
import com.github.md.web.ui.ComputeKit;
import com.github.md.web.ui.meta.CCUUConfigExtension;
import com.github.md.web.ui.meta.InstanceConfigExtension;
import com.github.md.web.ui.meta.MetaFieldConfigExtension;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

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
@Slf4j
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
        stepList.add(new DbInitStep());
        stepList.add(new EventInitStep());
        stepList.add(new StaticDictInitStep());
        stepList.add(new ExtensionInitStep());
        stepList.add(new MetaDataInit());
        stepList.add(new ComponentInitStep());
        stepList.add(new MetaObjectAndInstanceInitStep());

        log.info("启动初始化配置...");
        stepList.forEach(s -> s.init());
    }

    interface InitStep {

        void init();
    }

    /**
     * 初始化数据库。
     * <p>
     * 判断依据: 判断是否存在 meta_object表，若不存在，则执行数据库初始化脚本
     */
    class DbInitStep implements InitStep {
        public static final String META_OBJECT = "meta_object";
        public static final String DB_INIT_SQL = "db/db-meta.init.sql";

        @Override
        public void init() {
            if (!ServiceManager.mysqlService().hasTable(null, META_OBJECT)) { // 不存在meta_object表，则执行初始化脚本
                log.warn("检测到DbMeta为首次运行, 开始执行数据库初始化脚本....................................................");

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String sql = UtilKit.loadConfigByFile(DB_INIT_SQL);

                Resource sqlResource = new ByteArrayResource(sql.getBytes());
                SpringAnalysisManager.me().dbMain().execute((conn -> {
                    ScriptUtils.executeSqlScript(conn, sqlResource);
                    return true;
                }));
            }
        }
    }

    /**
     * 事件初始化
     */
    class EventInitStep implements InitStep {

        @Override
        public void init() {
            EventKit.init(false, "db-event-bus");
            EventKit.register(new FormListener());
            //            ExtensibleListenerManager.me().addFormListeners(new TestFormExtensibleListener());
        }
    }

    /**
     * 字典初始化。 从文件中读取到内存
     */
    class StaticDictInitStep implements InitStep {

        @Override
        public void init() {
            Dicts.me().init();
        }
    }

    /**
     * 扩展初始化
     */
    class ExtensionInitStep implements InitStep {

        @Override
        public void init() {
            SpringAnalysisManager.me().addMetaFieldConfigExtension(new MetaFieldConfigExtension());
            ComputeKit.addInstanceExtension(new InstanceConfigExtension());
            ComputeKit.addInstanceExtension(new CCUUConfigExtension());

            PointCutChain.registerGlobalPointCut(new AuditPointCut());
            PointCutChain.registerGlobalPointCut(new PreventInfiniteLoopPointCut());
        }
    }

    /**
     * 元数据初始化。 元对象、元字段、组件全局配置、组件实例配置 的自动初始化。判断meta_object表是否为空。若未空表示还未进行过初始化，则执行初始化。
     */
    class MetaDataInit implements InitStep {

        @Override
        public void init() {
            int size = ServiceManager.metaService().findAll().size();
            if (size == 0) { // 尚未初始化
                Components.me().init(); // 初始化组件(全局配置)

                InitKit.me().initSysMeta(); // 初始化系统元数据

                // 根据固定文件(defaultObject.json)更新元对象/元字段配置
                InitKit.me().updateMetaObjectConfig()       // 根据defaultObject.json更新元对象/原字段配置
                        .updateInstanceConfigByMetaObject() // 依据已入库的元对象/原字段配置推导UI实例配置
                        .updateInstanceConfig()            // 依据defaultInstance.json覆盖已入库的UI实例配置
                        .updateFeatureConfig(); // 更新内置功能
            }
        }
    }

    /**
     * 组件全局配置 自动刷新。从文件读取
     */
    class ComponentInitStep implements InitStep {

        @Override
        public void init() {
            if (metaProperties.getServer().getComponent().isReplaceFromJsonFile()) {
                Components.me().init();
            }
        }
    }

    /**
     * 内置元对象、元字段、组件实例配置 自动刷新。从文件读取
     */
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

}
