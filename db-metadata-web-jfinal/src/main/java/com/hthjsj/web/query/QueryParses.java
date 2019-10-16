package com.hthjsj.web.query;

import com.hthjsj.web.query.sqls.EasyCondition;
import com.hthjsj.web.query.sqls.FieldEquals;
import com.hthjsj.web.query.sqls.MetaSQLBuilder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class QueryParses {

    private static final QueryParses me = new QueryParses();

    @Getter
    List<Class<? extends MetaSQLBuilder>> parseter = new ArrayList<>();

    {
        init();
    }

    public static final QueryParses me() {
        return me;
    }

    private void init() {
        parseter.add(EasyCondition.class);
        parseter.add(FieldEquals.class);
    }
}
