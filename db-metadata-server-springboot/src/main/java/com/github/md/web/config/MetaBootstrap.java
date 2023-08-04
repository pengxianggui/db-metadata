package com.github.md.web.config;

import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.analysis.db.registry.JFinalActiveRecordPluginManager;
import com.github.md.analysis.meta.MetaFieldConfigExtension;
import com.github.md.analysis.meta.aop.PointCutChain;
import com.github.md.web.DbMetaConfigurer;
import com.github.md.web.ServiceManager;
import com.github.md.web.aop.AuditPointCut;
import com.github.md.web.aop.UniqueConstraintAop;
import com.github.md.web.cache.CacheRegistry;
import com.github.md.web.cache.MdCache;
import com.github.md.web.component.Components;
import com.github.md.web.component.render.RenderKit;
import com.github.md.web.component.render.form.FormFieldRenderExtension;
import com.github.md.web.component.render.table.TableCellRenderExtension;
import com.github.md.web.event.EventKit;
import com.github.md.web.event.FormListener;
import com.github.md.web.event.user.UserStatusChangeListener;
import com.github.md.web.feature.tree.PreventInfiniteLoopPointCut;
import com.github.md.web.file.FileManager;
import com.github.md.web.kit.InitKit;
import com.github.md.web.kit.UtilKit;
import com.github.md.web.ui.ComputeKit;
import com.github.md.web.ui.meta.InstanceConfigExtension;
import com.github.md.web.user.AuthenticationManager;
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
        stepList.add(new DbMetaConfigInit()); // dbmeta java配置
        stepList.add(new DbTableStructureInit()); // 首先初始化表结构
        stepList.add(new ExtensionInitStep()); // 配置扩展： 配置扩展必须在数据刷入之前注册，否则扩展对于 初始化时刷入的数据 不生效
        stepList.add(new DbTableDataInit()); // 初始数据初始化
        stepList.add(new ComponentAutoRefreshInit()); // 组件默认配置自动刷新(依据buildInComponent.json)
        stepList.add(new MetaObjectAndInstanceInitStep()); // 内置元对象元字段、内置组件实例配置 自动刷新(依据buildInObject.json和buildInInstance.json)
        stepList.add(new EventInitStep());

        log.info("启动初始化配置...");
        stepList.forEach(s -> s.init());
    }

    interface InitStep {

        void init();
    }

    class DbMetaConfigInit implements InitStep {

        @Override
        public void init() {
            DbMetaConfigurer configurer = AnalysisSpringUtil.getBean(DbMetaConfigurer.class);

            // 配置AuthenticationManager
            AuthenticationManager.config(configurer);

            // 配置FileManager
            FileManager.config(configurer);

            // 配置Cache
            JFinalActiveRecordPluginManager jFinalActiveRecordPluginManager = AnalysisSpringUtil.getBean(JFinalActiveRecordPluginManager.class);
            CacheRegistry cacheRegistry = new CacheRegistry();
            configurer.configCacheManager(cacheRegistry);
            jFinalActiveRecordPluginManager.setCache(new MdCache(cacheRegistry.getCacheManager()));
        }
    }

    /**
     * 初始化数据库表结构
     * <p>
     * 判断依据: 判断是否存在 meta_object表，若不存在，则执行数据库初始化脚本
     */
    class DbTableStructureInit implements InitStep {
        public static final String META_OBJECT = "meta_object";
        public static final String DB_INIT_SQL = "data/init/db-meta.init.sql";

        @Override
        public void init() {
            if (!ServiceManager.mysqlService().hasTable(null, META_OBJECT)) { // 不存在meta_object表，则执行初始化脚本
                log.info("检测到DbMeta为首次运行, 开始执行数据库初始化脚本...");

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
            EventKit.register(new UserStatusChangeListener());
//                        ExtensibleListenerManager.me().getFormListeners().add(new TestFormExtensibleListener());
        }
    }

    /**
     * 扩展初始化
     */
    class ExtensionInitStep implements InitStep {

        @Override
        public void init() {
            // 针对元对象和元字段的扩展
            SpringAnalysisManager.me().addMetaFieldConfigExtension(new MetaFieldConfigExtension());

            // 针对实例配置生成时(计算时)的扩展
            ComputeKit.addInstanceExtension(new InstanceConfigExtension());

            // 针对实例配置渲染时的扩展
            RenderKit.addRenderExtension(new FormFieldRenderExtension());
            RenderKit.addRenderExtension(new TableCellRenderExtension());

            PointCutChain.registerGlobalPointCut(new AuditPointCut()); // 审计
            PointCutChain.registerGlobalPointCut(new UniqueConstraintAop()); // 唯一约束校验
            PointCutChain.registerGlobalPointCut(new PreventInfiniteLoopPointCut()); // 防止树结构无穷循环(头尾连接)
        }
    }

    /**
     * 表数据初始化。
     * <p>
     * 初始化内置数据: 元对象、元字段、组件全局配置、组件实例配置、功能、内置业务数据(菜单、路由、权限、权限模块、角色、接口资源、字典) 的自动初始化。判断meta_object表是否为空。若未空表示还未进行过初始化，则执行初始化。
     */
    class DbTableDataInit implements InitStep {

        @Override
        public void init() {
            int size = ServiceManager.metaService().findAll().size();
            if (size == 0) { // 尚未初始化
                Components.me().init(); // 初始化组件(全局配置)

                InitKit.me().initSysMeta(); // 初始化系统元数据

                // 根据固定文件(buildInObject.json)更新元对象/元字段配置
                InitKit.me().updateMetaObjectConfig()       // 根据buildInObject.json更新元对象/原字段配置
                        .updateInstanceConfigByMetaObject() // 依据已入库的元对象/原字段配置推导UI实例配置
                        .updateInstanceConfig()            // 依据buildInInstance.json覆盖已入库的UI实例配置
                        .updateFeatureConfig()             // 更新内置功能
                        .updateBizData();                  // 依据buildInData.sql 导入其他内建数据

                InitKit.me().initROOTAccount(); // 初始化账号(ROOT)
            }
        }
    }

    /**
     * 组件全局配置 自动刷新。从文件读取
     */
    class ComponentAutoRefreshInit implements InitStep {

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
