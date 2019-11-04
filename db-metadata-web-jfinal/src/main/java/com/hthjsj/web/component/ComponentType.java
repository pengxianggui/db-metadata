package com.hthjsj.web.component;

import lombok.Getter;

/**
 * <p> @Date : 2019/10/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public enum ComponentType {
    BOOLBOX("boolbox", "布尔框", "BoolBox"),
    BUTTON("button", "按钮组件", "Button"),
    DATEBOX("datebox", "日期框", "DateBox"),
    DATETIMEBOX("datetimebox", "日期时间框", "DateTimeBox"),
    DROPDOWN("dropdown", "下拉框组件", "DropDownBox"),
    FORMVIEW("formview", "表单组件", "FormTmpl"),
    NUMBERBOX("numberbox", "数值框", "NumBox"),
    RADIOBOX("radiobox", "单选框", "RadioBox"),
    TABLEVIEW("tableview", "表格组件", "TableList"),
    TEXTAREABOX("textareabox", "文本域", "TextAreaBox"),
    TEXTBOX("textbox", "输入框组件", "TextBox"),
    TIMEBOX("timebox", "时间框", "TimeBox"),
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
