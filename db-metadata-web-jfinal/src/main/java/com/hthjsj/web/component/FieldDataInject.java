package com.hthjsj.web.component;

import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/10/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface FieldDataInject<F> {

    Kv inject(Kv meta, Kv conf, F field);
}
