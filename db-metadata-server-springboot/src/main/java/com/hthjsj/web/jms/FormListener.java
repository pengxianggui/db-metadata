package com.hthjsj.web.jms;

import com.google.common.eventbus.Subscribe;

/**
 * <p> @Date : 2020/1/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FormListener implements MetaEventListener<FormMessage> {

    @Subscribe
    @Override
    public void handler(FormMessage formMessage) {
        if (formMessage.getType().equalsIgnoreCase("add")) {
            ExtensibleListenerManager.me().getAddFormListeners().forEach(f -> {
                if (f.isHit(formMessage.getInvocation())) {
                    f.handler(formMessage.getInvocation());
                }
            });
        } else if (formMessage.getType().equalsIgnoreCase("update")) {
            ExtensibleListenerManager.me().getUpdateFormListeners().forEach(f -> {
                if (f.isHit(formMessage.getInvocation())) {
                    f.handler(formMessage.getInvocation());
                }
            });
        }
    }
}
