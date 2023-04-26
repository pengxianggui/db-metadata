package com.github.md.web.backup_restore;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.web.WebException;
import com.github.md.web.kit.AssertKit;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.io.File;
import java.nio.file.Paths;

/**
 * 表数据导入。
 *
 * @author pengxg
 * @date 2022/10/9 1:17 下午
 */
@Slf4j
public class MetaDataImport {

    /**
     * 导入zipFile。必须是从{@link MetaDataExport}导出的。
     *
     * @param zipFile
     */
    public void importZip(File zipFile) {
        SpringAnalysisManager.me().dbMain().tx(() -> {
            log.debug("准备执行元数据备份导入...{}", zipFile.getName());
            String dirName = FileUtil.getPrefix(zipFile);
            File dir = ZipUtil.unzip(zipFile, Paths.get(System.getProperty("java.io.tmpdir"), dirName).toFile());
            for (File file : dir.listFiles()) {
                if (!"sql".equals(FileUtil.extName(file))) {
                    continue;
                }

                log.debug("开始执行sql文件... {}", file.getAbsolutePath());
                try {
                    boolean flag = executeSQLFile(file);
                    AssertKit.isTrue(flag, new WebException("导入失败！"));
                    log.debug("Sql文件执行成功! {}", file.getAbsolutePath());
                } catch (ActiveRecordException e) {
                    log.error(e.getMessage(), e);
                    throw new WebException("脚本错误");
                }
            }
            log.debug("压缩包中的SQL文件全部执行完毕！");
            return true;
        });
    }

    /**
     * 执行SQL文件
     *
     * @param sqlFile
     * @return
     */
    private boolean executeSQLFile(File sqlFile) {
        return (boolean) SpringAnalysisManager.me().dbMain().execute(conn -> {
            conn.setAutoCommit(false);
            ScriptUtils.executeSqlScript(conn, new FileSystemResource(sqlFile));
            conn.commit();
            return true;
        });
    }
}
