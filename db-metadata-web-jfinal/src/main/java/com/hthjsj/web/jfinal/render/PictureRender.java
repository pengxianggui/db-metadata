package com.hthjsj.web.jfinal.render;

import com.jfinal.render.FileRender;
import com.jfinal.render.RenderException;

import java.io.File;

/**
 * <p> @Date : 2019/12/20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class PictureRender extends FileRender {

    protected static final String DEFAULT_CONTENT_TYPE = "image/jpeg";

    public PictureRender(File file) {
        super(file);
    }

    @Override
    public void render() {
        throw new RenderException("not finished!");
    }
}
