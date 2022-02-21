package com.github.md.web.user.auth.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.md.web.user.User;
import com.github.md.web.user.auth.MRPermit;
import com.github.md.web.user.auth.MResource;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.github.md.web.kit.UtilKit;

import java.util.Collection;
import java.util.List;

/**
 * 从json文件中加载用户信息。json文件中存储用户和用户拥有的资源, 依次作为判定用户是否拥有资源权限的依据。
 * <p>
 * json文件内容格式如下:
 * <pre>
 *     {
 *         "rules": [
 *          {
 *              "userId": "0",
 *              "resources": ["xxx", "yyy"]
 *          },
 *          ...
 *         ]
 *     }
 * </pre>
 * <p> @Date : 2019/12/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class JsonUserPermit implements MRPermit<User, MResource> {

    Multimap<String, String> userPermits = HashMultimap.create();

    /**
     * json数据文件
     *
     * @param filename
     */
    public JsonUserPermit(String filename) {
        String jsonString = UtilKit.loadConfigByFile(filename);
        JSONArray users = JSON.parseObject(jsonString).getJSONArray("rules");
        List<JSONObject> userLists = users.toJavaList(JSONObject.class);
        for (JSONObject u : userLists) {
            String[] resoutces = u.getJSONArray("resources").toArray(new String[0]);
            for (String s : resoutces) {
                userPermits.put(u.getString("userId"), s);
            }
        }
    }

    /**
     * 对需要放行的资源校验,对于不需要验证的资源,默认放行
     *
     * @param user
     * @param mResource
     * @return
     */
    @Override
    public boolean permit(User user, MResource mResource) {
        if (mResource.needPermit()) {
            Collection<String> rs = userPermits.get(user.userId());
            return rs.contains(mResource.mResourceId());
        }
        return true;
    }
}
