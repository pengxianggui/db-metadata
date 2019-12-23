package com.hthjsj.analysis.db;

/**
 * <p> Class title: DB模型与MetaObject的装配工具</p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface MetaObjectAssembly<T, M> {

    M assembly(T t);
}

