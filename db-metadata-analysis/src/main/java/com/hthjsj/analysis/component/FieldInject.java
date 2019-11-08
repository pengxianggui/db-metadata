package com.hthjsj.analysis.component;

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
     * @param field
     *
     * @return
     */
    Kv inject(Kv meta, F field);

    /**
     * 默认内部用
     *
     * @param meta
     *
     * @return
     */
    Kv inject(Kv meta);

    /**
     * 选择实现DefaultFieldInject的内部方法;
     *
     * @param <F>
     */
    abstract class DefaultFieldInject<F> implements FieldInject<F> {

        @Override
        public Kv inject(Kv meta, F field) {
            return Kv.create();
        }

        @Override
        public Kv inject(Kv meta) {
            return Kv.create();
        }
    }
}
