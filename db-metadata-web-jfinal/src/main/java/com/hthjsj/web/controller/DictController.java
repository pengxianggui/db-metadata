package com.hthjsj.web.controller;

import com.hthjsj.web.Dicts;
import com.jfinal.kit.Ret;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/15 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class DictController extends FrontRestController {

    @Override
    public void index() {
        renderJson(Ret.ok("data", Dicts.me().dict()));
    }

    public void sex() {
        renderJson(Ret.ok("data", Dicts.me().getGender()));
    }

    /**
     * yes or no
     */
    public void yn() {
        renderJson(Ret.ok("data", Dicts.me().getYesNo()));
    }
}
