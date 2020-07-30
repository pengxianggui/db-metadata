package com.hthjsj.web.jfinal.render;

import com.jfinal.render.Render;
import com.jfinal.render.RenderException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p> @Date : 2019/12/20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class PictureRender extends Render {

    protected static final String DEFAULT_CONTENT_TYPE = "image/jpeg";

    protected InputStream inputStream;

    protected String sFileExt;

    public PictureRender() {

    }

    /**
     * 构造函数。
     *
     * @param bytes       内容流
     * @param contentType 文件扩展名，或者叫文件类型。此处不带“.”。如jpg
     */
    public PictureRender(byte[] bytes, String contentType) {
        this.inputStream = new ByteArrayInputStream(bytes);
        this.sFileExt = contentType;
    }

    @Override
    public void render() {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType(sFileExt);
        byte[] buf = new byte[1024]; //定义缓存大小
        ServletOutputStream sos = null;
        try {
            sos = response.getOutputStream();
            while (inputStream.read(buf) != -1) {
                sos.write(buf);
            }
        } catch (IOException e) {
            if (getDevMode()) {
                throw new RenderException(e);
            }
        } catch (Exception e) {
            throw new RenderException(e);
        } finally {
            if (sos != null) {
                try {
                    sos.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
