package com.github.md.web.kit;

import com.jfinal.plugin.activerecord.Page;

/**
 * @author pengxg
 * @date 2023/10/16 15:10
 */
public class PageKit {

    public static <T> com.github.md.web.res.Page<T> toPage(Page<T> page) {
        return new com.github.md.web.res.Page(page.getList(), page.getPageNumber(), page.getPageSize(), page.getTotalRow());
    }
}
