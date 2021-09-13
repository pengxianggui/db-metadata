package com.github.md.analysis.db;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import static com.github.md.analysis.db.MetaDataTypeConvert.cast;

/**
 * <p> @Date : 2021/9/1 </p>
 * <p> @Project : MD</p>
 *
 * <p> @author konbluesky </p>
 */
class MetaDataTypeConvertTest {

    public static void main(String[] args) {
        {
            Object s = cast("2016-10-08 10:20:20", Date.class);
            System.out.println(s);
            System.out.println(s.getClass());
        }
        {
            Object s = cast("2016-10-08 10:20:20", DateTime.class);
            System.out.println(s);
            System.out.println(s.getClass());
        }
        {
            Object s = cast("10:20:20", DateTime.class);
            System.out.println(s);
            System.out.println(s.getClass());
        }
        {
            Object s = cast("10:20:20", Time.class);
            System.out.println(s);
            System.out.println(s.getClass());
        }
        {
            Object s = cast("2016-10-08 10:20:20", Timestamp.class);
            System.out.println(s);
            System.out.println(s.getClass());
        }
        {
            Object s = cast("true", Boolean.class);
            System.out.println(s);
            System.out.println(s.getClass());
        }
        {
            Object s = cast("false", Boolean.class);
            System.out.println(s);
            System.out.println(s.getClass());
        }
        {
            Object s = cast("1", Boolean.class);
            System.out.println(s);
            System.out.println(s.getClass());
        }
        {
            Object s = cast("0", Boolean.class);
            System.out.println(s);
            System.out.println(s.getClass());
        }
        {
            Object s = cast("2.311", Float.class);
            System.out.println(s);
            System.out.println(s.getClass());
        }
        {
            Object s = cast("111100111.311", Float.class);
            System.out.println(s);
            System.out.println(s.getClass());
        }
        {
            Object s = cast("111100111.311", BigDecimal.class);
            System.out.println(s);
            System.out.println(s.getClass());
        }
    }
}