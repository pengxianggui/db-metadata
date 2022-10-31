package com.github.md.web.event;

/**
 * 扩展监听器，用于业务系统扩展。
 * <p> @Date : 2020/1/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface ExtensibleListener<T extends EventMessage> {

    /**
     * 是否命中
     *
     * @param t
     * @return
     */
    boolean isHit(T t);

    /**
     * 处理
     *
     * @param t
     */
    void handler(T t);
}
