package com.github.md.web.component.form;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.kit.Kv;

/**
 * @author pengxg
 * @date 2021/2/2 10:47 上午
 */
public class RichTextBox extends FormField {
    // http://tinymce.ax-z.cn/configure/file-image-upload.php#images_upload_url
    public static final String IMAGE_UPLOAD_RETURN_KEY = "location";

    public RichTextBox(String name, String label) {
        super(name, label);
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.RICHTEXTBOX;
    }

    @Override
    public Kv toKv() {
        return render.render();
    }
}
