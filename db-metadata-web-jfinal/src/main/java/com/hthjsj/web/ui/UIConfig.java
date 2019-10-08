package com.hthjsj.web.ui;

import com.hthjsj.analysis.meta.IMetaConfig;
import com.hthjsj.analysis.meta.MetaData;
import com.hthjsj.analysis.meta.Storage;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/9/24 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UIConfig extends MetaData implements IMetaConfig, Storage {
    @Override
    public String module() {
        return UI;
    }

    @Override
    public String moduleCode() {
        return UI;
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getConfig() {
        return toJson();
    }

    @Override
    public void setItem(Object key, Object value) {
        set(key, value);
    }

    @Override
    public <T> T getItem(Object key) {
        return getAs(key);
    }

    @Override
    public Object save() {
        return null;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }
}
