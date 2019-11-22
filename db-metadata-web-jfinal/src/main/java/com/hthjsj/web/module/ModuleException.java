package com.hthjsj.web.module;

import com.hthjsj.web.WebException;

/**
 * <p> @Date : 2019/11/22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ModuleException extends WebException {

    public ModuleException(String message) {
        super(message);
    }

    public ModuleException(String messageTmpl, String... args) {
        super(messageTmpl, args);
    }
}
