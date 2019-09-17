package com.hthjsj.analysis.meta;

import java.util.Map;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaFieldDBAdapter implements IMetaField, Storage {

    @Override
    public boolean isPrimary() {
        return false;
    }

    @Override
    public void isPrimary(boolean value) {

    }

    @Override
    public String fieldCode() {
        return null;
    }

    @Override
    public void fieldCode(String value) {

    }

    @Override
    public String objectCode() {
        return null;
    }

    @Override
    public void objectCode(String value) {

    }

    @Override
    public String cn() {
        return null;
    }

    @Override
    public void cn(String value) {

    }

    @Override
    public String en() {
        return null;
    }

    @Override
    public void en(String value) {

    }

    @Override
    public String dbType() {
        return null;
    }

    @Override
    public void dbType(String value) {

    }

    @Override
    public String javaType() {
        return null;
    }

    @Override
    public void javaType(String value) {

    }

    @Override
    public int orderNum() {
        return 0;
    }

    @Override
    public void orderNum(int value) {

    }

    @Override
    public int dbTypeLength() {
        return 0;
    }

    @Override
    public void dbTypeLength(int value) {

    }

    @Override
    public MetaConfig config() {
        return null;
    }

    @Override
    public Map<String, Object> dataMap() {
        return null;
    }

    @Override
    public void dataMap(Map<String, Object> data) {

    }

    @Override
    public void config(String config) {

    }

    @Override
    public void config(MetaConfig config) {

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
