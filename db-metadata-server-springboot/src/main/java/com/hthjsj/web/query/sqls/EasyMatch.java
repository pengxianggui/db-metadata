package com.hthjsj.web.query.sqls;

import com.hthjsj.analysis.db.MetaDataTypeConvert;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaSqlKit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class EasyMatch extends MetaSQLExtract {

    /**
     * lt （less than）               小于
     * gt （greater than）            大于
     * le （less than or equal to）   小于等于
     * ge （greater than or equal to）大于等于
     * eq （equal to）                等于
     * ne （not equal to）            不等于
     */
    public final static String SEPARATOR = "_";

    private final static List<String[]> rules = new ArrayList<String[]>();

    static {
        rules.add(new String[]{"lt", "<?"});
        rules.add(new String[]{"gt", ">?"});
        rules.add(new String[]{"le", "<=?"});
        rules.add(new String[]{"ge", ">=?"});
        rules.add(new String[]{"eq", "=?"});
        rules.add(new String[]{"ne", "<>?"});
    }

    Map<String, Object> values = new HashMap<>();

    /**
     * 判断时间格式 格式必须为“YYYY-MM-dd”
     * 2004-2-30 是无效的
     * 2003-2-29 是无效的
     *
     * @param sDate
     * @return
     */
    private boolean isLegalDate(String sDate, String fmt) {
        int legalLen = 10;
        if ((sDate == null) || (sDate.length() != legalLen)) {
            return false;
        }

        DateFormat formatter = new SimpleDateFormat(fmt);
        try {
            Date date = formatter.parse(sDate);
            return sDate.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }

//    public static void main(String[] args) {
//        System.out.println(isLegalDate("1988-01-22","yyyy-MM-dd"));
//        System.out.println(isLegalDate("1988-01-22 22:11","yyyy-MM-dd HH:mm:ss"));
//    }

    private String buildQueryKey(String key, String suffix) {
        return key + SEPARATOR + suffix;
    }

    @Override
    public Map<String, Object> result() {
        return values;
    }

    @Override
    public void init(IMetaField metaField, Map<String, Object> httpParams) {
        for (String[] ss : rules) {
            Object v = httpParams.get(buildQueryKey(metaField.en(), ss[0]));

            if (v == null) {
                continue;
            }
            //对时间日期类型数据,进行对齐
            //普通日期 2019-01-01 -> 2019-01-01 00:00:00.000  便于生成 date>2019-01-01 00:00:00.000 sql
//            if (metaField.dbType().isDate()) {
//                String str = String.valueOf(v);
//                if (metaField.dbType().isDateOnly()) {
//                    if (isLegalDate(str, DateKit.datePattern)) {
//                        v = dateAlignAtZeroTime(str);
//                    }
//                }
//            }

            values.put(SQL_PREFIX + MetaSqlKit.discernColumns(metaField.en()) + ss[1], v);
        }
    }

    private String dateAlignAtZeroTime(String value) {
        return value.concat(" 00:00:00.000");
    }
}
