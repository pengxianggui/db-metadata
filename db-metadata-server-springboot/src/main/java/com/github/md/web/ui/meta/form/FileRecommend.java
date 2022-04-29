package com.github.md.web.ui.meta.form;

import com.alibaba.fastjson.JSONArray;
import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.component.attr.AttributeBuilder;
import com.github.md.web.file.UploadKit;

/**
 * @author pengxg
 * @date 2022/4/10 8:36 上午
 */
public class FileRecommend extends FieldComponentConfigExtension {

    @Override
    public void config(IMetaField metaField, AttributeBuilder.FatAttributeBuilder builder, ComponentType type) {
        if (!metaField.configParser().isFile()) {
            return;
        }

        if (metaField.fieldCode().contains("image")
                || metaField.fieldCode().contains("avatar")
                || metaField.fieldCode().contains("picture")
                || metaField.fieldCode().contains("photo")) {
            builder.componentName(ComponentType.IMAGEBOX);
            builder.listType("picture-card");
        } else {
            builder.componentName(ComponentType.FILEBOX);
            builder.listType("text");
        }

        if (metaField.dbType().isJson()) {
            builder.defaultVal(new JSONArray());
        } else {
            builder.defaultVal(null);
        }

        builder.limit(1); // 默认上传文件数量限制为1
        builder.seats(new String[0]); // 采用默认模式，而非座位模式
        builder.actionUrl(UploadKit.uploadUrl(metaField.objectCode(), metaField.fieldCode()));
        builder.autoUpload(true);
    }
}
