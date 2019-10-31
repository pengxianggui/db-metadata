package com.hthjsj.web.component;

import lombok.Getter;

/**
 * <p> @Date : 2019/10/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public enum ComponentType {
    TABLEVIEW("tableview", "表格组件", "TableList"),
    FORMVIEW("formview", "表单组件", "FormTmpl"),
    BUTTON("button", "按钮组件", "Button"),
    DROPDOWN("dropdown", "下拉框组件", "DropDownBox"),
    TEXTBOX("textbox", "输入框组件", "TextBox"),
    RADIOBOX("radiobox", "单选框", "RadioBox"),
    NUMBERBOX("numberbox", "数值框", "NumBox"),
    BOOLBOX("boolbox", "布尔框", "BoolBox"),
    TEXTAREABOX("textareabox", "文本域", "TextAreaBox"),
    UNKNOWN("unknow", "未知控件", "unknow");

    @Getter
    String name;

    @Getter
    String code;

    @Getter
    String cn;

    ComponentType(String name, String cn, String code) {
        this.name = name;
        this.cn = cn;
        this.code = code;
    }

    public static ComponentType V(String s) {
        for (ComponentType t : ComponentType.values()) {
            if (s.equalsIgnoreCase(t.code.toLowerCase()) || s.equalsIgnoreCase(t.name.toLowerCase()) || s.equalsIgnoreCase(t.cn.toLowerCase())) {
                return t;
            }
        }
        return UNKNOWN;
    }
}
