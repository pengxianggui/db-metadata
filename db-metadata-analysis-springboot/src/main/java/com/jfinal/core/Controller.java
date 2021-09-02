package com.jfinal.core;

import com.jfinal.kit.Kv;

/**
 * 整理JFinal Controller中用到的方法,剥离出来
 * <p> @Date : 2021/9/2 </p>
 * <p> @Project : MD</p>
 *
 * <p> @author konbluesky </p>
 */
public interface Controller {

    default Kv getKv() {
        throw new RuntimeException("该接口用来保证迁移时编译通过,未实现");
    }
}
