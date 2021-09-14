package com.github.md.web.config;

import com.github.md.web.ServiceManager;
import com.github.md.analysis.SpringAnalysisManager;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This class will act as a global manager of MetaServer;
 * It can get any information or content related to MetaServer;
 * <p> @Date : 2021/9/7 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
@Getter
@AllArgsConstructor
public class MetaServerManager {

    final ServiceManager serviceManager = new ServiceManager();

    private SpringAnalysisManager analysisManager;

    private MetaProperties metaServerProperties;
}
