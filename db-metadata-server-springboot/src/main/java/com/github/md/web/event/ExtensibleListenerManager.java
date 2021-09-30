package com.github.md.web.event;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

/**
 * <p> @Date : 2020/1/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ExtensibleListenerManager {

    private final static ExtensibleListenerManager me = new ExtensibleListenerManager();

    @Getter
    private final List<FormExtensibleListener> addFormListeners = Lists.newLinkedList();

    @Getter
    private final List<FormExtensibleListener> updateFormListeners = Lists.newLinkedList();

    public static ExtensibleListenerManager me() {
        return me;
    }

    public void addFormListeners(FormExtensibleListener listener) {
        addFormListeners.add(listener);
    }
}
