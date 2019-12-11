package com.hthjsj.web.query;

import com.hthjsj.web.jfinal.SqlParaExt;

import java.util.Map;

/**
 * <p> @Date : 2019/12/11 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface IQueryCondition {

    SqlParaExt resolve(Map<String, String[]> httpParams, String[] fields, String[] efields);
}
