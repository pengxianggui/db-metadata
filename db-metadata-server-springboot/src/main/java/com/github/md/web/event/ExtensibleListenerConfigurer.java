package com.github.md.web.event;

/**
 * 可扩展的监听器配置类
 *
 * @author pengxg
 * @date 2022/10/31 4:02 下午
 */
public interface ExtensibleListenerConfigurer {

    /**
     * 配置监听器
     *
     * @return
     */
    default void configListener(ExtensibleListenerRegistry registry) {
    }
}
