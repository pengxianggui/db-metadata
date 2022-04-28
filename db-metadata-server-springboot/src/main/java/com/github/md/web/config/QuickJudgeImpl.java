package com.github.md.web.config;

/**
 * <p> @Date : 2021/9/7 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
public class QuickJudgeImpl implements QuickJudge {

    MetaServerManager metaServerManager;

    public QuickJudgeImpl(MetaServerManager metaServerManager) {
        this.metaServerManager = metaServerManager;
    }

    @Override
    public boolean isDevMode() {
        return metaServerManager.getMetaServerProperties().isDevMode();
    }

    @Override
    public String mainDbStr() {
        return metaServerManager.getAnalysisManager().getDataSourceManager().mainSource().schemaName();
    }
}
