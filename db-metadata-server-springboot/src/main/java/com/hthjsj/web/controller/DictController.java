package com.hthjsj.web.controller;

import com.google.common.base.Preconditions;
import com.hthjsj.web.kit.Dicts;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * <p> @Date : 2019/10/15 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@RestController
@RequestMapping("dict")
public class DictController extends ControllerAdapter {

    @GetMapping("/{key}")
    public Ret index() {
        ParameterHelper parameterHelper = parameterHelper();
        String key = parameterHelper.getPara("key");

        Preconditions.checkNotNull(key, "参数:key必须填写,参考 dict.json");

        List<Kv> result = Dicts.me().getKvs(key);

        if (result != null && !result.isEmpty()) {
            return Ret.ok("data", result);
        }
        log.info("已有key:{}", Arrays.toString(Dicts.me().keys()));
        return Ret.fail().set("msg", "dict.json中不存在key为" + key + "的数据");
    }
}
