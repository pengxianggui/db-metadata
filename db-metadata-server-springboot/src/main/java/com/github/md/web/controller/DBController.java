package com.github.md.web.controller;

import com.github.md.web.user.auth.annotations.Type;
import com.github.md.web.user.auth.annotations.MetaAccess;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.github.md.analysis.db.Table;
import com.github.md.web.AppConst;
import com.github.md.web.ServiceManager;
import com.github.md.web.component.Components;
import com.github.md.web.kit.InitKit;
import com.github.md.web.ui.OptionsKit;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p> @Date : 2019/10/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@RestController
@RequestMapping("db")
public class DBController extends ControllerAdapter {

    @GetMapping(value = {"index"})
    public Ret list() {
        List<String> schemas = ServiceManager.mysqlService().showSchema();
        return Ret.ok("data", OptionsKit.transKeyValue(schemas.toArray(new String[schemas.size()])));
    }

    /**
     * <pre>
     *  param:
     *      schemaName : default value "metadata"
     *  @return
     * </pre>
     */
    @GetMapping("tables")
    public Ret tables() {
        ParameterHelper parameterHelper = parameterHelper();
        String schemaName = parameterHelper.getPara("schemaName");
        Preconditions.checkNotNull(schemaName, "[schemaName]数据库名称是必填参数");
        List<Table> tables = ServiceManager.mysqlService().showTables(schemaName);
        List<Kv> results = Lists.newArrayList();
        tables.forEach(r -> {
            results.add(Kv.create().set("key", r.getTableName()).set("value", r.getTableName()));
        });
        return Ret.ok("data", results);
    }

    @MetaAccess(value = Type.API)
    @Transactional
    @PostMapping("init")
    public Ret init() {
        preConditionCheck();
        truncate();

        Components.me().init(); // 初始化组件(包括组件配置)

        InitKit.me().initSysMeta(); // 初始化系统元数据

        // 根据固定文件(defaultObject.json)更新元对象/元字段配置
        InitKit.me().updateMetaObjectConfig()       // 根据buildInObject.json更新元对象/原字段配置
                .updateInstanceConfigByMetaObject() // 依据已入库的元对象/原字段配置推导UI实例配置
                .updateInstanceConfig()            //  依据buildInInstance.json覆盖已入库的UI实例配置
                .updateFeatureConfig()             // 依据buildInFeature.json覆盖系统内置的功能配置
                .updateBizData();                  // 依据buildInData.sql 导入其他内建数据

        log.info("重置完毕！");
        return Ret.ok();
    }

    /**
     * 先对指定表的内建数据执行删除操作
     *
     * @return
     */
    private Ret truncate() {
        preConditionCheck();

        StringBuilder sb = new StringBuilder();

        Set<AppConst.INNER_TABLE> resetableTable = AppConst.INNER_TABLE.getResetableTable();

        sb.append("即将执行内建数据清理的数据表:").append(resetableTable);
        log.warn("{}", sb.toString());
        resetableTable.forEach(table -> {
            final String tableName = table.getTableName();
            log.warn("即将执行{}表的内建数据清理", tableName);
            if (table.isBuildInSql()) { // 如果内建数据存在于sql中，则支持保留用户更新过的内建数据
                Db.delete("delete from " + tableName + " where build_in=? and updated_time is null", true); // 对于维护在sql中的内置数据，如果用户更新过，则不删除
            } else {
                Db.delete("delete from " + tableName + " where build_in=?", true); // 对于没有维护在sql中的内建数据(若元对象元字段等，他们存在于json中), 则即使用户更新过，也一并删除，重置。
            }
        });

        return Ret.ok("msg", sb.toString());
    }

    private void preConditionCheck() {
        String token = parameterHelper().getPara("token");
        Preconditions.checkArgument(quickJudge().isDevMode(), "未处于开发模式,无法执行该操作");
        Preconditions.checkArgument(token.equalsIgnoreCase(ServiceManager.getAppProperties().getApp().getResetPass()), "口令错误,不能初始化系统");
    }
}
