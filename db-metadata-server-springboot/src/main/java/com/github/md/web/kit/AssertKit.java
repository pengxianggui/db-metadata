package com.github.md.web.kit;

import cn.hutool.core.util.StrUtil;
import com.github.md.web.WebException;

/**
 * 断言工具类
 *
 * @author pengxg
 * @date 2023/4/26 13:51
 */
public class AssertKit {

    /**
     * 将抛出异常{@link WebException}
     *
     * @param expression
     * @param errorMsgTemplate
     */
    public static void isTrue(boolean expression, String errorMsgTemplate, Object... params) {
        if (!expression) {
            throw new WebException(StrUtil.format(errorMsgTemplate, params));
        }
    }

    public static <E extends RuntimeException> void isTrue(boolean expression, E e) {
        if (!expression) {
            throw e;
        }
    }
}
