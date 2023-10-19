package com.github.md.web.event;

import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.web.DbMetaConfigurer;
import lombok.Getter;

/**
 * <p> @Date : 2020/1/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ExtensibleListenerManager {

    private final static ExtensibleListenerManager me = new ExtensibleListenerManager();
    @Getter
    private ExtensibleListenerRegistry registry;

    public static ExtensibleListenerManager me() {
        return me;
    }

    private ExtensibleListenerManager() {
        ExtensibleListenerConfigurer configurer = AnalysisSpringUtil.getBean(ExtensibleListenerConfigurer.class);
        this.registry = new ExtensibleListenerRegistry();
        configurer.configListener(this.registry);
    }
}
