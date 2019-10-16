package com.hthjsj.web.query;

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
public class QueryParses {

    public static final QueryParses me = new QueryParses();

    @Getter
    List<ParseRule> parseter = new ArrayList<>();

    public static final QueryParses me() {
        me.init();
        return me;
    }

    private void init() {
        parseter.add(new Single(QueryCondition.EQ, "=?"));
        parseter.add(new Single(QueryCondition.LT, "<?"));
        parseter.add(new Single(QueryCondition.GT, ">?"));
        parseter.add(new Single(QueryCondition.LE, "<=?"));
        parseter.add(new Single(QueryCondition.GE, ">=?"));
        parseter.add(new Single(QueryCondition.NE, "<>?"));
        parseter.add(new InOrNinRule(QueryCondition.IN, true));
        parseter.add(new InOrNinRule(QueryCondition.NIN, false));
    }

    interface ParseRule {

        Object getValue(Map data, String fieldCode);

        String key(String fieldCode);

        String toSql(Map data, String fieldCode);
    }

    abstract class AbsRule implements QueryParses.ParseRule {

        String suffix;

        String sqlTmp;

        @Override
        public Object getValue(Map data, String fieldCode) {
            return getFirst(data.get(fieldCode));
        }

        @Override
        public String key(String fieldCode) {
            return fieldCode + QueryCondition.SEPARATOR + suffix;
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
