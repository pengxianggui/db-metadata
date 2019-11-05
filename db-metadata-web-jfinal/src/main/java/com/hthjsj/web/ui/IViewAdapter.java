package com.hthjsj.web.ui;

import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.web.component.ViewComponent;
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

    ViewComponent getComponent();

    List<IViewAdapter<IMetaField>> fields();
}
