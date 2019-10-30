package com.hthjsj.web.component;

import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/10/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface FieldInject<F> {

    /**
     * 外部用
     *
     * @param meta
     * @param conf
     * @param field
     *
     * @return
     */
    Kv inject(Kv meta, Kv conf, F field);

    /**
     * 默认内部用
     *
     * @param meta
     * @param conf
     *
     * @return
     */
    Kv inject(Kv meta, Kv conf);

    /**
     * 选择实现DefaultFieldInject的内部方法;
     *
     * @param <F>
     */
    abstract class DefaultFieldInject<F> implements FieldInject<F> {

        @Override
        public Kv inject(Kv meta, Kv conf, F field) {
            return Kv.create();
        }

        @Override
        public Kv inject(Kv meta, Kv conf) {
            return Kv.create();
        }
    }
}
