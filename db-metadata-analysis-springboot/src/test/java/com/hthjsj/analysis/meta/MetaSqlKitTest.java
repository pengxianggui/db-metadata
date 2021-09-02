package com.hthjsj.analysis.meta;

import org.junit.jupiter.api.Test;

/**
 * <p> @Date : 2021/9/2 </p>
 * <p> @Project : MD</p>
 *
 * <p> @author konbluesky </p>
 */
class MetaSqlKitTest {

    @Test
    void main() {
        String ss = MetaSqlKit.where("from a where 1=1", "a in (?,?,?)", "");
        System.out.println(ss);
    }
}