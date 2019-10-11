package com.hthjsj.web.component;

import com.jfinal.kit.Kv;
import lombok.Getter;
import lombok.Setter;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/11 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public abstract class Behavior {

    @Getter
    @Setter
    protected Kv behaviorRuleData = Kv.create();
}
