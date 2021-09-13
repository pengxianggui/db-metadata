package com.github.md.web.user.auth.jfinal;

import com.github.md.web.user.auth.MRLoader;
import com.github.md.web.user.auth.MResource;
//import com.jfinal.core.JFinal;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> @Date : 2019/12/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class JFinalResourceLoader implements MRLoader {

    private final List<MResource> resources = new ArrayList<>(0);

    @Override
    public void load() {
//        for (String action : JFinal.me().getAllActionKeys()) {
//            resources.add(new ActionResource(action, action, true));
//        }
    }

    @Override
    public List<MResource> resources() {
        return resources;
    }
}
