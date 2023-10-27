package com.github.md.web.res;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页对象
 * @author pengxg
 * @date 2023/10/13 15:14
 */
@NoArgsConstructor
@Data
public class Page<T> {
    /**
     * 列表数据
     */
    private T list;
    /**
     * 当前页
     */
    private int index;
    /**
     * 每页条数
     */
    private int size;
    /**
     * 总记录数
     */
    private int total;

    public Page(T list, int index, int size, int total) {
        this.list = list;
        this.index = index;
        this.size = size;
        this.total = total;
    }
}
