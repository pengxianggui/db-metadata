package com.hthjsj.web.kit;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author pengxg
 * @date 2020/11/3 11:03 上午
 */
public class PatternPathMatcherTest {

    @Test
    public void testMatchAny() {
        String uri = "/site/preview";
        boolean result = PatternPathMatcher.matchAny(uri, "/file/.*", "/site/.*");
        Assert.assertEquals(true, result);
    }
}
