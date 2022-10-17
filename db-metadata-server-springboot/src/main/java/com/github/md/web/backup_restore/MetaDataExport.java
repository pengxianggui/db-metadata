package com.github.md.web.backup_restore;

import cn.hutool.core.util.ZipUtil;
import com.alibaba.fastjson.JSONValidator;
import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.web.AppConst;
import com.github.md.web.ServiceManager;
import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.jfinal.plugin.activerecord.Record;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 表数据导出。
 *
 * @author pengxg
 * @date 2022/10/9 1:18 下午
 */
public class MetaDataExport {
    private static Set<String> DEFAULT_EXPORT_TABLE = AppConst.INNER_TABLE.getDefaultExportTable().stream().map(AppConst.INNER_TABLE::getTableName).collect(Collectors.toSet());

    /**
     * 默认需要导出的表。排除meta_exception, 这是异常日志表。
     */
    private final Set<String> tableNames;

    /**
     * 导出配置
     */
    private final ExportConfig exportConfig;

    /**
     * 默认构造器。导出meta_开头的所有表(除了meta_exception)，每个表导出为一个sql文件(不含建表语句、先执行truncate, 再insert)。
     */
    public MetaDataExport() {
        this(ExportConfig.builder().create(false).insert(true).build(), DEFAULT_EXPORT_TABLE);
    }

    private MetaDataExport(ExportConfig exportConfig, Set<String> tableNames) {
        this.exportConfig = exportConfig;
        this.tableNames = tableNames;
    }

    /**
     * 导出ZIP文件，包含meta_*表的全量备份数据，内容为建表语句(可配)、insert语句(可配)
     *
     * @return
     */
    public File exportZip() throws IOException {
        final String schemaName = SpringAnalysisManager.me().getDataSourceManager().mainSource().schemaName();
        final String tmpDir = System.getProperty("java.io.tmpdir");
        final String prefixName = schemaName + "_meta_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        File sqlFile = Paths.get(tmpDir, prefixName + ".sql").toFile();
        exportToSQL(sqlFile);

        File zipFile = Paths.get(tmpDir, prefixName + ".zip").toFile();
        return ZipUtil.zip(zipFile, false, sqlFile);
    }

    /**
     * 导出数据并写入sql文件
     *
     * @param sqlFile 将导出的sql脚本写入sqlFile
     * @return
     */
    public void exportToSQL(File sqlFile) throws IOException {
        SpringAnalysisManager springAnalysisManager = SpringAnalysisManager.me();
        final String systemName = ServiceManager.getAppProperties().getApp().getName();
        final String schemaName = springAnalysisManager.getDataSourceManager().mainSource().schemaName();

        FileWriter fileWriter = new FileWriter(sqlFile);

        try {
            // 写入注释
            fileWriter.append("-- ----------------------------").append("\n");
            fileWriter.append("-- App Name: " + systemName).append("\n");
            fileWriter.append("-- Source Schema: " + schemaName).append("\n");
            fileWriter.append("-- Date: " + new Date().toString()).append("\n");
            fileWriter.append("-- ----------------------------").append("\n");

            // 设置
            fileWriter.append("SET NAMES utf8mb4;").append("\n");
            fileWriter.append("SET autocommit = 0;").append("\n");
            fileWriter.append("SET FOREIGN_KEY_CHECKS = 0;").append("\n\n");
            fileWriter.append("BEGIN;\n");

            for (String tableName : this.tableNames) {
                fileWriter.append("-- ----------------------------").append("\n");
                fileWriter.append("-- Sql For Table: " + tableName).append("\n");
                fileWriter.append("-- ----------------------------").append("\n");

                if (this.exportConfig.isCreate()) {
                    fileWriter.append("-- ----------------------------").append("\n");
                    fileWriter.append("-- Table structure for " + tableName).append("\n");
                    fileWriter.append("-- ----------------------------").append("\n");
                    fileWriter.append("DROP TABLE IF EXISTS " + tableName + ";\n");
                    Record record = springAnalysisManager.dbMain().findFirst("SHOW CREATE TABLE " + tableName);
                    fileWriter.append(record.getStr("Create Table")).append(";\n\n");
                }

                if (this.exportConfig.isInsert()) {
                    fileWriter.append("DELETE FROM " + tableName + ";\n"); // truncate不能回滚
                    fileWriter.append("-- ----------------------------\n");
                    fileWriter.append("-- Records for " + tableName).append("\n");
                    fileWriter.append("-- ----------------------------").append("\n");
                    List<Record> data = springAnalysisManager.dbMain().findAll(tableName);
                    for (Record record : data) {
                        fileWriter.append(toInsertSql(tableName, record)).append("\n");
                    }
                }
            }

            fileWriter.append("COMMIT;\n");
            fileWriter.append("SET FOREIGN_KEY_CHECKS = 1;\n\n\n");
        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }

    private String toInsertSql(String tableName, Record record) {
        String fieldNameStr = Joiner.on(",").join(Arrays.stream(record.getColumnNames()).map(fieldName -> '`' + fieldName + '`').collect(Collectors.toList()));
        String fieldValueStr = Joiner.on(",").join(Arrays.stream(record.getColumnValues()).map(fieldValue -> escapeValue(fieldValue)).collect(Collectors.toList()));
        StringBuilder sb = new StringBuilder("INSERT INTO ").append(tableName).append("(").append(fieldNameStr).append(") ").append(" VALUES(").append(fieldValueStr).append(");");
        return sb.toString();
    }

    private Object escapeValue(Object fieldValue) {
        if (fieldValue == null) {
            return "NULL";
        }

        if (fieldValue instanceof Number) {
            return fieldValue;
        }

        if (fieldValue instanceof Boolean) {
            return (Boolean) fieldValue ? "b'1'" : "b'0'";
        }

        String strFieldValue = String.valueOf(fieldValue);
        JSONValidator jsonValidator = JSONValidator.from(strFieldValue);
        if (!jsonValidator.validate()) {
            return "'" + strFieldValue + "'";
        }

        switch (jsonValidator.getType()) {
            case Array:
            case Object:
                strFieldValue = strFieldValue.replace("\\", "\\\\") // 单斜杠转义
                        .replace("'", "\\'") // 单引号转义
                        .replace("\"", "\\\""); // 双引号转义
                return "'" + strFieldValue + "'";

            // TODO 导出导入要求百分百完全复原数据，这非常重要。上面手动处理转义总感觉不那么可靠，尝试使用StringEscapeUtils进行转义处理。但是
            //  StringEscapeUtils会把中文转为 unicode编码，而且不会转义单引号！ 尝试找到更可靠的API
            //  strFieldValue = StringEscapeUtils.escapeJson(strFieldValue);
            //  return "'" + strFieldValue + "'";
            case Value:
            default:
                return "'" + strFieldValue + "'";
        }

    }
}
