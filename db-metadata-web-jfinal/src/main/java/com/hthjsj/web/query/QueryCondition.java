package com.hthjsj.web.query;

import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.jfinal.SqlParaExt;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * <p> Class title: 查询条件 构造器</p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/9/24 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class QueryCondition {

    public final static String SEPARATOR = "_";

    public static final String PREFIX_COL = "__COLUMN__";

    public final static String LT = "lt";

    public final static String LE = "le";

    public final static String EQ = "eq";

    public final static String NE = "ne";

    public final static String GE = "ge";

    public final static String GT = "gt";

    public final static String IN = "in";

    public final static String NIN = "nin";


    /**
     * 普通值过滤
     * 数值 : key=value
     * 字符 : key=value ( %like% )
     * 日期 : key=2019-10-10  ->
     *
     * 连续区间过滤
     * 日期 : key_start={} & key_end={}
     * [ok] 数值 : key_lt={} & key_eq={}
     * 字符 :
     *
     * 非连续区间过滤
     * 数值 : key_in = 1,3,4,5,6
     * 字符 : key_in = "1","2","3","4"
     *
     */
    /**
     * lt （less than）               小于
     * gt （greater than）            大于
     * le （less than or equal to）   小于等于
     * ge （greater than or equal to）大于等于
     * eq （equal to）                等于
     * ne （not equal to）            不等于
     */
    public static void main(String[] args) {
        QueryCondition q = new QueryCondition();
        String value = "1";
        Kv kv = Kv.create();
        kv.set(q.LT_SQL("username"), value);
        kv.set(q.EQ_SQL("password"), 23);
        String[] ins = new String[] { "11", "22" };
        kv.set(q.IN_SQL("u", ins), ins);

        System.out.println(kv.toJson());

        System.out.println(q.buildExceptSelect(kv, new SqlParaExt(), "ta_name"));
    }

    /**
     * 解析逻辑
     * 非连续-> 连续-> 普通
     * <p>
     * FIXME :
     * http://url?ef=id,name,config&f=config 会滤出全部列
     */
    public SqlParaExt resolve(Map<String, String[]> httpParams, MetaObject metaObject, String[] fields, String[] efields) {

        //FIXME
        Collection<IMetaField> metaFields = metaObject.fields();
        SqlParaExt sqlParaExt = new SqlParaExt();

        Kv conds = Kv.create();
        Iterator<IMetaField> mfiter = metaFields.iterator();

        //important Arrays.binarySearch 必须操作有序数组,所以要对fields,efiedls排序
        Arrays.sort(fields);
        Arrays.sort(efields);
        //过滤字段
        while (mfiter.hasNext()) {
            IMetaField metaField = mfiter.next();
            //不在指定列表中的剔除
            if (fields != null && fields.length > 0) {
                if (Arrays.binarySearch(fields, metaField.en()) < 0) {
                    mfiter.remove();
                    continue;
                }
            }
            //在排除列表中的 剔除
            if (efields != null && efields.length > 0) {
                if (Arrays.binarySearch(efields, metaField.en()) >= 0) {
                    mfiter.remove();
                    continue;
                }
            }
        }

        for (IMetaField field : metaFields) {
            String fieldCode = field.en();
            conds.set(PREFIX_COL(fieldCode), fieldCode);
            // = & equals
            String value = StrKit.isBlank(getFirst(httpParams.get(fieldCode))) ? getFirst(httpParams.get(EQ(fieldCode))) : getFirst(httpParams.get(fieldCode));
            if (StrKit.notBlank(value)) {
                conds.set(EQ_SQL(fieldCode), value);
            }
            // less than
            value = getFirst(httpParams.get(LT(fieldCode)));
            if (StrKit.notBlank(value)) {
                conds.set(LT_SQL(fieldCode), value);
            }
            // greater than
            value = getFirst(httpParams.get(GT(fieldCode)));
            if (StrKit.notBlank(value)) {
                conds.set(GT_SQL(fieldCode), value);
            }
            // less than or equal to
            value = getFirst(httpParams.get(LE(fieldCode)));
            if (StrKit.notBlank(value)) {
                conds.set(LE_SQL(fieldCode), value);
            }
            // greater than or equal to
            value = getFirst(httpParams.get(GE(fieldCode)));
            if (StrKit.notBlank(value)) {
                conds.set(GE_SQL(fieldCode), value);
            }
            // not equals
            value = getFirst(httpParams.get(NE(fieldCode)));
            if (StrKit.notBlank(value)) {
                conds.set(NE_SQL(fieldCode), value);
            }
            // in(?,?)
            //request中获取的是"a,b,httpParams"所以需要toStrs
            String[] values = getStrs(httpParams.get(IN(fieldCode)));
            if (values != null && values.length > 0) {
                conds.set(IN_SQL(fieldCode, values), values);
            }

            // not in(?,?)
            values = getStrs(httpParams.get(NIN(fieldCode)));
            if (values != null && values.length > 0) {
                conds.set(NIN_SQL(fieldCode, values), values);
            }
        }

        return buildExceptSelect(conds, sqlParaExt, metaObject.tableName());
    }

    private String[] getStrs(String[] s) {
        if (s != null && s.length > 0) {
            return s[0].split(",");
        }
        return null;
    }

    private String getFirst(String[] s) {
        if (s != null && s.length > 0) {
            return s[0];
        }
        return null;
    }
    

    private SqlParaExt buildExceptSelect(Kv kv, SqlParaExt sqlParaExt, String tableName) {
        StringBuilder sqlExceptSelect = new StringBuilder();
        StringBuilder sqlSelect = new StringBuilder("select *");
        Iterator iter = kv.keySet().iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            // 构建selector
            if (key.startsWith(PREFIX_COL)) {
                sqlSelect.append(",").append(kv.get(key)).append(" ");
                continue;
            }

            //IN NIN 逻辑时 value 为string[],需要将数组元素按顺序解析出来
            if (key.indexOf("in(") >= 0) {
                //这时的getAs取出来的是String[]数据;
                String[] vals = kv.getAs(key);
                sqlExceptSelect.append(" and ").append(key).append(" ");
                for (int i = 0; i < vals.length; i++) {
                    sqlParaExt.addPara(vals[i]);
                }
                continue;
            }
            //其他逻辑
            sqlExceptSelect.append(" and ").append(key).append(" ");
            sqlParaExt.addPara(kv.get(key));
        }
        sqlParaExt.setSelect(sqlSelect.toString().replaceFirst("\\*,", ""));
        sqlParaExt.setFrom(" from " + tableName);
        sqlParaExt.setSqlExceptSelect(" where 1=1 " + sqlExceptSelect.toString());
        sqlParaExt.verify();
        return sqlParaExt;
    }

    private String LT(String key) {
        return key + SEPARATOR + LT;
    }

    private String LT_SQL(String key) {
        return key + " <? ";
    }

    private String GT(String key) {
        return key + SEPARATOR + GT;
    }

    private String GT_SQL(String key) {
        return key + " >? ";
    }

    private String LE(String key) {
        return key + SEPARATOR + LE;
    }

    private String LE_SQL(String key) {
        return key + " <=? ";
    }

    private String GE(String key) {
        return key + SEPARATOR + GE;
    }

    private String GE_SQL(String key) {
        return key + " >=? ";
    }

    private String EQ(String key) {
        return key + SEPARATOR + EQ;
    }

    private String EQ_SQL(String key) {
        return key + " =? ";
    }

    private String NE(String key) {
        return key + SEPARATOR + NE;
    }

    private String NE_SQL(String key) {
        return key + " <>? ";
    }

    private String IN(String key) {
        return key + SEPARATOR + IN;
    }

    private String IN_SQL(String key, String[] values) {
        StringBuilder sb = new StringBuilder();
        for (String v : values) {
            if (StrKit.notBlank(v)) {
                sb.append(",?");
            }
        }
        String s = sb.toString().replaceFirst("(,)", "");
        return key + " in(" + s + ") ";
    }

    private String NIN(String key) {
        return key + SEPARATOR + NIN;
    }

    private String NIN_SQL(String key, String[] values) {
        StringBuilder sb = new StringBuilder();
        for (String v : values) {
            if (StrKit.notBlank(v)) {
                sb.append(",?");
            }
        }
        String s = sb.toString().replaceFirst("(,)", "");
        return key + " not in(" + s + ") ";
    }

    private String PREFIX_COL(String key) {
        return PREFIX_COL + key;
    }
}
