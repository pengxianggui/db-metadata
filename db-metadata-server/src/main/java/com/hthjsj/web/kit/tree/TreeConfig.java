package com.hthjsj.web.kit.tree;

import com.jfinal.kit.StrKit;
import lombok.Data;

/**
 * 构建树用config
 * <p> @Date : 2020/1/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Data
public class TreeConfig {

    private String objectCode;

    private String idKey;

    private String pidKey;

    private String rootIdentify;

    private boolean keepRoot = false; // rootIdentify有值时标示构建树时是否保留root节点

    private String label;

    private boolean isSync = false;

    private String orderBy;

    private boolean asc;

    public void setOrderBy(String orderBy) {
        if (StrKit.notBlank(orderBy)) {
            String firstOrder;
            if (orderBy.contains(",")) {
                firstOrder = orderBy.split(",")[0];
            } else {
                firstOrder = orderBy;
            }

            if (firstOrder.contains(" ")) {
                this.orderBy = firstOrder.split(" ")[0];
                this.asc = ("asc".equalsIgnoreCase(firstOrder.split(" ")[1]));
            } else {
                this.orderBy = firstOrder;
            }
        }
    }
}
