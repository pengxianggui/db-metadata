package com.hthjsj.analysis.meta;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class DefaultMetaField extends AbstractMetaField {
    
    @Override
    public boolean isPrimary() {
        return record.getBoolean("is_primary");
    }
    
    @Override
    public String objectCode() {
        return record.getStr("object_code");
    }
    
    @Override
    public String getCn() {
        return record.getStr("cn");
    }
    
    @Override
    public String getEn() {
        return record.getStr("en");
    }
    
    @Override
    public String showType() {
        return record.getStr("show_type");
    }
    
    @Override
    public String dbType() {
        return record.getStr("db_type");
    }
    
    @Override
    public String javaType() {
        return record.getStr("java_type");
    }
    
    @Override
    public String fieldCode() {
        return record.getStr("field_code");
    }
    
    @Override
    public int orderNum() {
        return record.getInt("order_num");
    }
    
    @Override
    public String placeHolder() {
        return record.getStr("place_holder");
    }
    
    @Override
    public String validator() {
        return null;
    }
    
    @Override
    public String defaultVal() {
        return record.getStr("default_val");
    }
    
    @Override
    public String formatter() {
        return record.getStr("formatter");
    }
    
    @Override
    public boolean isQuery() {
        return record.getBoolean("is_query");
    }
    
    @Override
    public boolean isListShow() {
        return record.getBoolean("is_listshow");
    }
    
    @Override
    public boolean isDisable() {
        return record.getBoolean("is_disable");
    }
    
    @Override
    public boolean isAdd() {
        return record.getBoolean("is_add");
    }
    
    @Override
    public boolean isUpdate() {
        return record.getBoolean("is_update");
    }
    
    @Override
    public boolean isEdit() {
        return record.getBoolean("is_edit");
    }
    
    @Override
    public String addStatus() {
        return record.getStr("add_status");
    }
    
    @Override
    public String updateStatus() {
        return record.getStr("update_status");
    }
    
    @Override
    public boolean isRequired() {
        return record.getBoolean("is_required");
    }
    
    @Override
    public boolean isMultiple() {
        return record.getBoolean("is_multiple");
    }
    
    @Override
    MetaFieldConfig getConfig() {
        return new MetaFieldConfig(record.getStr("config"), objectCode(), fieldCode());
    }
}
