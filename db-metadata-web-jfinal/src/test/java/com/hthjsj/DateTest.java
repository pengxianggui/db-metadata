package com.hthjsj;

import com.jfinal.ext.kit.DateKit;

import java.util.Date;

/**
 * <p> @Date : 2019/12/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class DateTest {

    public static void main(String[] args) {
        System.out.println(DateKit.toStr(new Date(), "yyyyMMdd_HH_mm_ss_SSS"));
    }
}
