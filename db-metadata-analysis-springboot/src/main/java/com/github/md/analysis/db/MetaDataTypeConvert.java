/**
 * Copyright (c) 2013-2016, Jieven. All rights reserved.
 * <p>
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.github.md.analysis.db;

import com.github.md.analysis.MetaAnalysisException;
import com.github.md.analysis.meta.IMetaField;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * db types <> java types
 * 参考开源项目 EOVA
 *
 * @author Jieven
 */
@SuppressWarnings("rawtypes")
@Slf4j
public class MetaDataTypeConvert {

    private static String[] BOOL_DEFAULT_VAL = {"0", "1", "false", "true", "b'0'", "b'1'"}; // 奇数位 为代表false, 偶数位代表true

    /**
     * 参考：http://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-type-conversions.html INT UNSIGNED 这里强制指定为 Integer 因为大部分人不知道应该为Long
     */
    @SuppressWarnings("serial")
    private final static Map<String, Class> map = new HashMap<String, Class>() {

        {
            // MySQL
            put("BIT", Boolean.class);
            put("TEXT", String.class);
            put("DATE", Date.class);
            put("DATETIME", DateTime.class);
            put("TIMESTAMP", Timestamp.class);
            put("TIME", Time.class);
            put("TINYINT", Integer.class);
            put("SMALLINT", Integer.class);
            put("MEDIUMINT", Integer.class);
            put("INT", Integer.class);
            put("BIGINT", Long.class);
            put("SMALLINT UNSIGNED", Integer.class);
            put("MEDIUMINT UNSIGNED", Integer.class);
            // mysql if UNSIGNED Long, because eova the most easy! if the overflow,you should use bigint!
            put("INT UNSIGNED", Integer.class);
            put("BIGINT UNSIGNED", BigInteger.class);
            put("FLOAT", Float.class);
            put("DOUBLE", Double.class);
            put("DECIMAL", BigDecimal.class);
            put("CHAR", String.class);
            put("BINARY", Byte[].class);
            put("VARBINARY", Byte[].class);
            put("TINYBLOB", Byte[].class);
            put("BLOB", Byte[].class);
            put("MEDIUMBLOB", Byte[].class);
            put("LONGBLOB", Byte[].class);
            put("VARCHAR", String.class);
            put("MEDIUMTEXT", String.class);
            put("LONGTEXT", String.class);
            put("JSON", String.class);
        }
    };

    private static final DateTimeFormatter forPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Class getType(String dataType) {
        return map.get(dataType.toUpperCase());
    }

    public static String getTypeName(String dataType) {
        return map.get(dataType).getTypeName();
    }

    public static Object convert(IMetaField field, Object o) {
        if (o == null) {
            return null;
        }
        String typeName = field.dbType().rawData();
        return convert(String.valueOf(o), typeName);
    }

    /**
     * 将字符串值转换为指定类型
     *
     * @param value       字符串值
     * @param dbFieldType 数据库字段类型
     * @return
     */
    public static Object convert(String value, String dbFieldType) {
        return cast(value, getType(dbFieldType));
    }

    public static Object cast(String s, Class c) {
        if (s == null) {
            return null;
        }
        s = s.trim();
        try {
            if (s.length() == 0 && c != String.class) {
                // empty string only cast to string.class
                return null;
            }
            if (c == Integer.class) {
                return Integer.parseInt(s);
            }
            if (c == Long.class) {
                return Long.parseLong(s);
            }
            if (c == Float.class) {
                return Float.parseFloat(s);
            }
            if (c == Double.class) {
                return Double.parseDouble(s);
            }
            if (c == Boolean.class) {
                int position = Arrays.asList(BOOL_DEFAULT_VAL).indexOf(s);
                return (position + 1) % 2 == 0;
            }
            if (c == BigInteger.class) {
                return BigInteger.valueOf(Long.parseLong(s));
            }
            if (c == BigDecimal.class) {
                return BigDecimal.valueOf(Double.parseDouble(s));
            }
            if (c == Byte[].class) {
                return s.getBytes();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MetaDataTypeConvertException("parse failed [%s]->[%s]", s, c.getSimpleName());
        }

        try {
            if (c == Timestamp.class) { // 特殊的默认值
                if (s.equalsIgnoreCase("CURRENT_TIMESTAMP")) {
                    return new Timestamp(new Date().getTime());
                } else {
                    long time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s).getTime();
                    return new Timestamp(time);
                }
            }
            if (c == DateTime.class) {
                if (s.equalsIgnoreCase("CURRENT_TIMESTAMP")) {
                    return new Date();
                } else {
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
                }
            }
            if (c == Date.class) {
                return new SimpleDateFormat("yyyy-MM-dd").parse(s);
            }
            if (c == Time.class) {
                return new SimpleDateFormat("HH:mm:ss").parse(s);
            }
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            throw new MetaDataTypeConvertException("Date parse failed [%s]->[%s]", s, c.getSimpleName());
        }
        return s;
    }

    public static class MetaDataTypeConvertException extends MetaAnalysisException {

        public MetaDataTypeConvertException(String messageTmpl, String... args) {
            super(messageTmpl, args);
        }
    }
}
