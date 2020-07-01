package com.hthjsj.web.jms;

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
    private List<FormExtensibleListener> addFormListeners = Lists.newLinkedList();

    @Getter
    private List<FormExtensibleListener> updateFormListeners = Lists.newLinkedList();

    public static ExtensibleListenerManager me() {
        return me;
    }

    public void addFormListeners(FormExtensibleListener listener) {
        addFormListeners.add(listener);
    }
}
