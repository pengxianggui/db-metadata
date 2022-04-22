package com.github.md.web.ui.meta.form;

import cn.com.asoco.util.AssertUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.WebException;
import com.github.md.web.component.attr.AttributeBuilder;
import com.github.md.web.upload.UploadKit;

import java.util.List;

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

        List<String> seats = metaField.configParser().fileConfig();
        AssertUtil.isTrue(CollectionUtil.isNotEmpty(seats),
                new WebException("元字段(%s:%s)配置有误, json类型必须设置【上传插槽】", metaField.objectCode(), metaField.fieldCode()));

        if (metaField.dbType().isJson()) {
            builder.defaultVal(new JSONArray());
            builder.seats(seats.toArray(new String[seats.size()]));
        } else {
            builder.defaultVal(null);
            AssertUtil.isTrue(seats.size() == 1,
                    new WebException("元字段(%s:%s)配置有误, 非json字段类型【上传插槽】能设置一个",
                            metaField.objectCode(), metaField.fieldCode()));
            builder.seats(new String[]{""});
        }

        builder.actionUrl(UploadKit.uploadUrl(metaField.objectCode(), metaField.fieldCode()));
        builder.autoUpload(true);
    }
}
