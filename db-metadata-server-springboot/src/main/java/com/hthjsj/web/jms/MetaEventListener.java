package com.hthjsj.web.jms;

/**
 * <p> @Date : 2020/1/15 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface MetaEventListener<Event extends EventMessage> {

    void handler(Event event);
}
