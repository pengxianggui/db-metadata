/** * $Id: IndexController.java,v 1.0 2019-08-09 09:56 chenmin Exp $ */package com.hthjsj.jfinalspringbootdemo;import com.github.artislong.annotation.RouterPath;import com.hthjsj.web.controller.FrontRestController;import com.jfinal.core.Controller;import com.jfinal.core.paragetter.Para;import com.jfinal.kit.Ret;/** * @author 陈敏 * @version $Id: IndexController.java,v 1.1 2019-08-09 09:56 chenmin Exp $ * Created on 2019-08-09 09:56 * My blog： https://www.chenmin.info */@RouterPath("/spring-boot")public class IndexController extends Controller {    public void index(@Para(value = "title", defaultValue = "") String title) {        renderJson(Ret.ok());    }}