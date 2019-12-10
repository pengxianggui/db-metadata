package com.hthjsj.web.controller;

import com.google.common.base.Preconditions;
import com.hthjsj.web.Dicts;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/15 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class DictController extends FrontRestController {

    @Override
    public void index() {
        String key = getPara(0, getPara("key"));

        Preconditions.checkNotNull(key, "参数:key必须填写,参考 dict.json");

        List<Kv> result = Dicts.me().getKvs(key);

        if (result != null && !result.isEmpty()) {
            renderJson(Ret.ok("data", result));
            return;
        }
        log.info("已有key:{}", Arrays.toString(Dicts.me().keys()));
        renderJson(Ret.fail().set("msg", "dict.json中不存在key为" + key + "的数据"));
    }
}
