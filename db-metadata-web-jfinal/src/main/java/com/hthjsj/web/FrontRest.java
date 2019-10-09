package com.hthjsj.web;

import com.jfinal.kit.Ret;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface FrontRest {

    Ret index();

    Ret toAdd();

    Ret doAdd();

    Ret toUpdate();

    Ret doUpdate();

    Ret detail();

    Ret delete();

    Ret list();
}
