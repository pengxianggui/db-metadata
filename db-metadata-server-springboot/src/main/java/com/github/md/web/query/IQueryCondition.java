package com.github.md.web.query;

import com.github.md.web.jfinal.SqlParaExt;

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
