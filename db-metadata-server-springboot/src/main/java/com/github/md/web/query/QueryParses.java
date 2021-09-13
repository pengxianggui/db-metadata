package com.github.md.web.query;

import com.github.md.web.query.sqls.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class QueryParses {

    private static final QueryParses me = new QueryParses();

    @Getter
    List<Class<? extends MetaSQLExtract>> parseter = new ArrayList<>();

    {
        init();
    }

    public static final QueryParses me() {
        return me;
    }

    private void init() {
        parseter.add(EasyMatch.class);
        parseter.add(FieldEqualsMatch.class);
        parseter.add(SortMatch.class);
        parseter.add(InNotInMatch.class);
        parseter.add(LikeMatch.class);
    }
}
