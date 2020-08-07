package com.hthjsj.analysis.component;

import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p> @Date : 2020/8/7 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class ComponentInjectFactory {

    public static ViewInject[] viewInjects(String s) {
        String[] ss = null;
        if (StrKit.isBlank(s)) {
            ss = new String[0];
        } else {
            if (s.contains(",")) {
                ss = s.split(",");
            } else {
                ss = new String[] { s };
            }
        }

        if (ss == null || ss.length == 0) {
            return new ViewInject[0];
        }
        Collection<ViewInject> viewInjects = new ArrayList<>();
        for (String clazz : ss) {

            try {
                viewInjects.add((ViewInject) Class.forName(clazz).newInstance());
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                log.error(e.getMessage(), e);
            }
        }
        return viewInjects.toArray(new ViewInject[] {});
    }
}
