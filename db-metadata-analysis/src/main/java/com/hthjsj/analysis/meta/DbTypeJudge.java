package com.hthjsj.analysis.meta;

/**
 * 数据库类型判断器
 * <p> @Date : 2019/11/6 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class DbTypeJudge {

    String value;

    DbTypeJudge(String value) {
        this.value = value;
    }

    public boolean isText() {
        return isSmallText() || isBigText() || isJson();
    }

    public boolean isSmallText() {
        return value.equalsIgnoreCase("varchar");
    }

    public boolean isBigText() {
        return value.equalsIgnoreCase("text") || value.equalsIgnoreCase("mediumtext");
    }

    public boolean isNumber() {
        return isInt() || isBigInt() || isDecimal() || isTinyInt();
    }

    public boolean isInt() {
        return value.equalsIgnoreCase("int");
    }

    public boolean isTinyInt() {
        return value.equalsIgnoreCase("tinyint");
    }

    public boolean isBigInt() {
        return value.equalsIgnoreCase("bigint");
    }

    public boolean isDecimal() {
        return value.equalsIgnoreCase("decimal");
    }

    public boolean isDate() {
        return isDateOnly() || isDateTime() || isDateOnly() || isTime();
    }

    public boolean isDateOnly() {
        return value.equalsIgnoreCase("date");
    }

    public boolean isDateTime() {
        return value.equalsIgnoreCase("datetime");
    }

    public boolean isTime() {
        return value.equalsIgnoreCase("timestamp");
    }

    public boolean isJson() {
        return value.equalsIgnoreCase("json");
    }

    /**
     * DbTypeJudge 不持有dbTypeLength,需外部传入
     *
     * @param length
     *
     * @return
     */
    public boolean isBoolean(int length) {
        return isText() && length == 1;
    }

    public String rawData() {
        return value;
    }
}
