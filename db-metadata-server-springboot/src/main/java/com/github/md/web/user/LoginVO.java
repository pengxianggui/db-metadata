package com.github.md.web.user;

import com.github.md.analysis.kit.Kv;
import com.github.md.web.upload.UploadFileResolve;
import com.github.md.web.user.auth.IAuth;
import com.github.md.web.user.role.MRRole;
import com.github.md.web.user.role.UserWithRolesWrapper;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登录返回
 *
 * @author pengxg
 * @date 2022/2/28 6:10 下午
 */
@NoArgsConstructor
@Getter
public class LoginVO {
    private Kv data;

    public LoginVO(String token, UserWithRolesWrapper user) {
        String avatarUrl = null;
        UploadFileResolve uploadFileResolve = new UploadFileResolve(user.avatar());
        if (uploadFileResolve.hasFile()) {
            avatarUrl = uploadFileResolve.getFiles().get(0).getUrl();
        }
        this.data = user.toKv().set("token", token)
                .set("avatar", avatarUrl)
                .delete("password");
    }
}
