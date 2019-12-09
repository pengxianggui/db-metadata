package com.hthjsj.web.feature;

import com.jfinal.kit.Kv;

import java.util.List;

/**
 * <p> @Date : 2019/12/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface Feature {

    List<String> metaObjects();

    Kv execute();
}
