package com.hthjsj.web;

import com.hthjsj.analysis.component.ComponentType;

/**
 * <p> @Date : 2020/7/27 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ServerConfigBuilder {

    interface Init {

        void init();
    }

    interface ConfigResourceBuilder {

    }

    interface ComponentBuilder {

        ComponentBuilder autoInit(ComponentType initComponentType);
    }

    interface DictBuilder {

    }

    interface EventBuilder {

    }

    interface ExtensibleBuilder {

    }

    interface MRBuilder {

    }

    interface RouterBuilder {

        void exclude(String urlOrPathKey);
    }
}
