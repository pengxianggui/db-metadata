package com.github.md.web.kit;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author pengxg
 * @date 2020/11/3 10:56 上午
 */
@Slf4j
public class PatternPathMatcher {

    /**
     * 匹配任一即为true
     *
     * @param uri
     * @param patterns
     * @return
     */
    public static boolean matchAny(String uri, String... patterns) {
        return Arrays.stream(patterns).anyMatch(pattern -> match(uri, pattern));
    }

    public static boolean match(String uri, String pattern) {
        return Pattern.compile(pattern).matcher(uri).matches();
    }

}
