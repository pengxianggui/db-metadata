package com.hthjsj.analysis.meta;

/**
 * 数据库类型判断器
 * <p> @Date : 2019/11/6 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class DBTypeJudge {

    String value;

    DBTypeJudge(String value) {
        this.value = value;
    }

    public boolean isText() {
        return isSmallText() || isBigText();
    }

    public boolean isSmallText() {
        return value.equalsIgnoreCase("varchar");
    }

    public boolean isBigText() {
        return value.equalsIgnoreCase("text") || value.equalsIgnoreCase("mediumtext");
    }

    public String rawData() {
        return value;
    }
}
