package com.hthjsj.web.component;

/**
 * <p> @Date : 2019/10/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public enum ComponentType {
    TABLEVIEW("tableview", "TableList"), FORMVIEW("", ""), BUTTON("", ""), DROPDOWN("", ""), INPUTFIELD("", "");

    String name;

    String code;

    ComponentType(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
