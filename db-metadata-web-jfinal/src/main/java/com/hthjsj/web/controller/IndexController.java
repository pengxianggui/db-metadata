package com.hthjsj.web.controller;

import com.jfinal.core.Controller;

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
        renderJson("Hello world");
    }
}
