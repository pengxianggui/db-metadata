package com.hthjsj.analysis.meta.ui;

import com.hthjsj.analysis.meta.Component;

/**
 * @author konbluesky
 */
public abstract class ViewComponent implements Component {

    protected String en;

    protected String cn;

    public abstract String config();

    public abstract void config(String config);
    /**
     * 渲染组件需要的元数据
     *
     * @return
     */
    //    public abstract String renderMeta();
    //    public abstract String mergeConfig(String newConfig);
    //    public abstract void loadConfig();
}
