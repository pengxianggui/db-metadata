package com.hthjsj;

import com.hthjsj.analysis.component.ComponentType;

/**
 * <p> @Date : 2020/12/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ComponentTypeTest {

    public static void main(String[] args) {
        System.out.println(ComponentType.V("tablevaiew").isView());
    }
}
