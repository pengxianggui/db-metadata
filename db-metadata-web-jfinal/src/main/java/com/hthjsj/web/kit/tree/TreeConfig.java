package com.hthjsj.web.kit.tree;

import lombok.Data;

/**
 * <p> @Date : 2020/1/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Data
public class TreeConfig {

    String objectCode;

    String idKey;

    String pidKey;

    String rootIdentify;

    String label;

    boolean isSync = false;
}
