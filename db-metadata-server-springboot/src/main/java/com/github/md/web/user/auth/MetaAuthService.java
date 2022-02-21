package com.github.md.web.user.auth;

import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.analysis.meta.AuthForType;
import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author pengxg
 * @date 2021/10/15 11:20 上午
 */
@Slf4j
@Transactional
@Service
public class MetaAuthService {
    private DbPro db() {
        return SpringAnalysisManager.me().dbMain();
    }

    public List<Record> findAll() {
        return db().findAll("meta_auth");
    }

    public List<IAuth> findByRole(String roleId) {
        List<IAuth> auths = Lists.newArrayList();
        List<Record> records = db().find("select a.* from meta_auth a, meta_role_auth_rela r where a.id = r.auth_id and r.role_id=?", roleId);
        for (Record record : records) {
            auths.add(new MetaAuthResource(record));
        }
        return auths;
    }

    public List<IAuth> findByUser(String userId) {
        List<IAuth> auths = Lists.newArrayList();
        List<Record> records = db().find("select a.* from meta_auth a, meta_role_auth_rela ra, meta_user_role_rela ur, meta_user u where a.id = ra.auth_id and ra.role_id = ur.role_id and u.id=?", userId);
        for (Record record : records) {
            auths.add(new MetaAuthResource(record));
        }
        return auths;
    }

    @Deprecated
    public String findAuthCode(AuthForType type, String metaCode, String uri) {
        Record record = db().findFirst("select * from meta_auth where type = ? and meta_code = ? and uri = ?",
                type.getCode(), metaCode, uri);
        if (Objects.isNull(record)) {
            return null;
        }
        return record.getStr("code");
    }

    public Record findOne(AuthForType type, String metaCode, String uri) {
        return db().findFirst("select * from meta_auth where type=? and meta_code=? and uri=?",
                type.getCode(), metaCode, uri);
    }
}
