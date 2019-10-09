package com.hthjsj.web.jfinal.fastjson;

import com.jfinal.json.FastJsonFactory;
import com.jfinal.json.Json;

/**
 * <p> @Date : 2019/9/24 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class CrackFastJsonFactory extends FastJsonFactory {
    @Override
    public Json getJson() {
        return new RefSkipFastJson();
    }
}
