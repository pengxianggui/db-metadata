package com.github.md.web.user.auth;

import com.github.md.analysis.kit.Kv;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@AllArgsConstructor
@Getter
public class ResourceAuth implements IAuth {

    MResource mResource;

    @Override
    public String code() {
        return mResource.mResourceId();
    }

    @Override
    public String name() {
        return mResource.mResourceName();
    }

    @Override
    public Kv toKv() {
        return Kv.create()
                .set("code", code())
                .set("name", name())
                .set("resources", Kv.create()
                        .set("id", mResource.mResourceId())
                        .set("name", mResource.mResourceName()));
    }
}
