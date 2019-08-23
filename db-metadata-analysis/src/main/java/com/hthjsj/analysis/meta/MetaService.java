package com.hthjsj.analysis.meta;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface MetaService {
    
    MetaObject importFromTable(String schema, String table);
    
    MetaObject findByCode(String code);
    
}
