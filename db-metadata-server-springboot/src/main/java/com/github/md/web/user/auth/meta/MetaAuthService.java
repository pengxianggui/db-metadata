package com.github.md.web.user.auth.meta;

import com.github.md.web.user.auth.defaults.DefaultAuthService;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 内置meta权限服务
 *
 * @author pengxg
 * @date 2021/10/15 11:20 上午
 */
@Slf4j
@Service
public class MetaAuthService extends DefaultAuthService {

    public MetaAuthResource findOne(Type type, String metaCode, String uri) {
        Record record = db().findFirst("select * from meta_auth where type=? and meta_code=? and uri=?",
                type.getCode(), metaCode, uri);
        return new MetaAuthResource(record);
    }

    public MetaAuthResource findOne(Type type, String uri) {
        Record record = db().findFirst("select * from meta_auth where type=? and uri=?", type.getCode(), uri);
        return new MetaAuthResource(record);
    }
}
