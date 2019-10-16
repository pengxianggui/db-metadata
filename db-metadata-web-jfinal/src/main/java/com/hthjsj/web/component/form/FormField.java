package com.hthjsj.web.component.form;

import com.hthjsj.analysis.meta.Component;
import com.jfinal.kit.Kv;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public abstract class FormField implements Component {

    public abstract Kv renderMeta();
}
