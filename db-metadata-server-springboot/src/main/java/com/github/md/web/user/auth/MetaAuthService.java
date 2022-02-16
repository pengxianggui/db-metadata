package com.github.md.web.user.auth;

import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.analysis.meta.AuthForType;
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

    public List<Record> findByRole(String roleId) {
        // TODO
        return null;
    }

    public List<Record> findByUser(String userId) {
        // TODO
        return null;
    }

    public String findAuthCode(AuthForType type, String metaCode, String uri) {
        Record record = db().findFirst("select * from meta_auth where type = ? and meta_code = ? and uri = ?",
                type.getCode(), metaCode, uri);
        if (Objects.isNull(record)) {
            return null;
        }
        return record.getStr("auth_code");
    }
}
