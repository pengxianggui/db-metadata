package com.hthjsj.analysis.meta;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
interface MetaObjectDbBehavior {
    
    Object save();
    
    boolean update();
    
    boolean delete();
}
