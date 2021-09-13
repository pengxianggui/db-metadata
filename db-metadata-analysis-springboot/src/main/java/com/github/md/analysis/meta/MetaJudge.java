package com.github.md.analysis.meta;

/**
 * <p> @Date : 2019/12/11 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaJudge {

    public static final String TABLE = "table";

    public static final String VIEW = "view";

    public static final String MANUAL = "manual";

    String value;

    public MetaJudge(String value) {
        this.value = value;
    }

    public boolean isTable() {
        return value.equalsIgnoreCase(TABLE);
    }

    public boolean isView() {
        return value.equalsIgnoreCase(VIEW);
    }

    public boolean isManual() {
        return value.equalsIgnoreCase(MANUAL);
    }
}
