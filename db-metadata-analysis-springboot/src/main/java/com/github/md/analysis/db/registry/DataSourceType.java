package com.github.md.analysis.db.registry;

import lombok.Getter;

/**
 * <p> @Date : 2021/8/31 </p>
 * <p> @Project : MD</p>
 *
 * <p> @author konbluesky </p>
 */
public enum DataSourceType {
    MAIN("main-db-source"),
    BIZ("biz-db-source");

    @Getter
    private final String typeName;

    DataSourceType(String typeName) {
        this.typeName = typeName;
    }
}
