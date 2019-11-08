package com.hthjsj.web.ui;

import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.meta.IMetaField;
import com.jfinal.kit.Kv;

import java.util.List;

/**
 * <p> @Date : 2019/11/5 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface IViewAdapter<M> {

    M getMeta();

    Kv instanceConfig();

    Component getComponent();

    List<IViewAdapter<IMetaField>> fields();
}
