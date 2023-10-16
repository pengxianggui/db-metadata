package com.github.md.web.controller;

import com.github.md.analysis.kit.Kv;
import com.github.md.web.res.Res;
import com.github.md.web.kit.Dicts;
import com.google.common.collect.Lists;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 入参name是根字典的value, 即字典项的编码
     *
     * @return
     */
    @GetMapping
    public Res index() {
        ParameterHelper parameterHelper = parameterHelper();
        String pValue = parameterHelper.getPara("name");

        if (StrKit.isBlank(pValue)) {
            return Res.ok(Lists.newArrayList());
        }

        List<Kv> result = Dicts.me().getKvs(pValue);

        if (CollectionUtils.isEmpty(result)) {
            String msg = String.format("指定字典不存在: %s, 请检查是否有此字典项", pValue);
            log.info(msg);
            return Res.fail(msg);
        }
        return Res.ok(result);
    }

    @GetMapping("names")
    public Res names() {
        return Res.ok(Dicts.me().names());
    }
}
