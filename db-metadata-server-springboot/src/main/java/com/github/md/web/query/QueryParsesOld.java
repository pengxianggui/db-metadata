package com.github.md.web.query;

import com.jfinal.kit.StrKit;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/15 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Deprecated
public class QueryParsesOld {

    public static final QueryParsesOld me = new QueryParsesOld();

    @Getter
    List<ParseRule> parseter = new ArrayList<>();

    public static final QueryParsesOld me() {
        me.init();
        return me;
    }

    private void init() {
        parseter.add(new Single(QueryConditionOld.EQ, "=?"));
        parseter.add(new Single(QueryConditionOld.LT, "<?"));
        parseter.add(new Single(QueryConditionOld.GT, ">?"));
        parseter.add(new Single(QueryConditionOld.LE, "<=?"));
        parseter.add(new Single(QueryConditionOld.GE, ">=?"));
        parseter.add(new Single(QueryConditionOld.NE, "<>?"));
        parseter.add(new InOrNinRule(QueryConditionOld.IN, true));
        parseter.add(new InOrNinRule(QueryConditionOld.NIN, false));
    }

    interface ParseRule {

        Object getValue(Map data, String fieldCode);

        String key(String fieldCode);

        String toSql(Map data, String fieldCode);
    }

    abstract class AbsRule implements ParseRule {

        String suffix;

        String sqlTmp;

        @Override
        public Object getValue(Map data, String fieldCode) {
            return getFirst(data.get(fieldCode));
        }

        @Override
        public String key(String fieldCode) {
            return fieldCode + QueryConditionOld.SEPARATOR + suffix;
        }

        @Override
        public String toSql(Map data, String fieldCode) {
            return sqlTmp;
        }

        protected String[] getStrs(Object value) {
            if (value instanceof String[]) {
                String[] ss = (String[]) value;
                if (ss != null && ss.length > 0) {
                    return ss[0].split(",");
                }
            }
            return null;
        }

        protected String getFirst(Object value) {
            if (value instanceof String[]) {
                String[] ss = (String[]) value;
                if (ss != null && ss.length > 0) {
                    return ss[0];
                }
            }
            return null;
        }
    }

    class Single extends AbsRule {

        public Single(String sqlTmp) {
            this.sqlTmp = sqlTmp;
        }

        public Single(String suffix, String sqlTmp) {
            this.suffix = suffix;
            this.sqlTmp = sqlTmp;
        }
    }

    class InOrNinRule extends AbsRule {

        boolean isIn = false;

        public InOrNinRule(String suffix, boolean isIn) {
            this.suffix = suffix;
            this.isIn = isIn;
        }

        @Override
        public Object getValue(Map data, String fieldCode) {
            return getStrs(data.get(fieldCode));
        }

        @Override
        public String toSql(Map data, String fieldCode) {
            StringBuilder sb = new StringBuilder();
            String[] ss = (String[]) getValue(data, fieldCode);
            for (String v : ss) {
                if (StrKit.notBlank(v)) {
                    sb.append(",?");
                }
            }
            String s = sb.toString().replaceFirst("(,)", "");
            return fieldCode + (isIn ? "in" : " not in") + "(" + s + ") ";
        }
    }
}
