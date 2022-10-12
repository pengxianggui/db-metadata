package com.github.md.web.backup_restore;

import lombok.Builder;
import lombok.Getter;

/**
 * 导出配置。默认不包含
 *
 * @author pengxg
 * @date 2022/10/9 1:01 下午
 */
@Getter
@Builder
public class ExportConfig {
    /**
     * 是否包含建表语句。 注意: 若为true, 则生成的脚本中包含建表语句(会先判断存在则删除)，因此在移交线上时务必先备份数据库。
     */
    private boolean create;

    /**
     * 是否包含insert语句
     */
    private boolean insert;

}
