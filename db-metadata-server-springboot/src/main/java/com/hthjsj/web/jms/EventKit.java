package com.hthjsj.web.jms;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;

/**
 * <p> @Date : 2020/1/15 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class EventKit {

    private static EventBus eventBus;

    private static boolean isInit = false;

    public static void init(boolean isAsync, String identifier) {
        if (isAsync) {
            eventBus = new AsyncEventBus(Executors.newScheduledThreadPool(5));
        } else {
            eventBus = new EventBus(identifier);
        }
        isInit = true;
    }

    public static <T extends EventMessage> void post(T message) {
        if (isInit) {
            eventBus.post(message);
        } else {
            log.error("EventBus is uninitialized");
        }
    }

    public static <T extends MetaEventListener> void register(T listener) {
        if (isInit) {
            eventBus.register(listener);
        } else {
            log.error("EventBus is uninitialized");
        }
    }

    public static <T extends MetaEventListener> void unregister(T listener) {
        if (isInit) {
            eventBus.unregister(listener);
        } else {
            log.error("EventBus is uninitialized");
        }
    }
}
