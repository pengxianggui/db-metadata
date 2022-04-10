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
    private String token;
    private String id;
    private String username;
    private String avatar;
    private Set<String> roles;
    private Set<String> auths;
    private Kv attrs;

    public LoginVO(String token, UserWithRolesWrapper user) {
        this.token = token;
        this.id = user.userId();
        this.username = user.userName();
        this.roles = Arrays.stream(user.roles()).map(MRRole::code).collect(Collectors.toSet());
        this.auths = Arrays.stream(user.auths()).map(IAuth::code).collect(Collectors.toSet());
        this.attrs = user.attrs();

        String avatarUrl = null;
        UploadFileResolve uploadFileResolve = new UploadFileResolve(user.avatar());
        if (uploadFileResolve.hasFile()) {
            avatarUrl = uploadFileResolve.getFiles().get(0).getUrl();
        }
        this.avatar = avatarUrl;
    }
}
