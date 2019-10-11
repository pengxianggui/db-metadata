package com.hthjsj.web.controller;

import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-24 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class IndexController extends Controller {

    public void index() {

        //        JFinal.me().getAllActionKeys()

        renderJson(JFinal.me().getAllActionKeys());
    }
}
