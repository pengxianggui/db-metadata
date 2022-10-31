package com.github.md.web.event;

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
        ExtensibleListenerManager.me().getRegistry().getFormListeners().forEach(f -> {
            if (f.isHit(formMessage)) {
                f.handler(formMessage);
            }
        });
    }
}
