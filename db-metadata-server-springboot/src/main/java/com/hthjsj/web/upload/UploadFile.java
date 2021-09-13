package com.hthjsj.web.upload;

import lombok.Data;

/**
 * <p> @Date : 2021/9/13 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
@Data
public class UploadFile {

    private String name;

    private String url;

    private String seat;

    public String getUploadedName() {
        return url.substring(url.lastIndexOf("/")+1);
    }
}
