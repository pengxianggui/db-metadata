package com.github.md.web.user.auth;

import com.github.md.web.user.User;
import com.jfinal.kit.StrKit;

import java.util.regex.Pattern;

/**
 * <p> @Date : 2019/12/17 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class RegularPermit implements MRPermit<User, MResource> {

    private final Pattern pattern;

    public RegularPermit(String regex) {
        this(regex, true);
    }

    public RegularPermit(String regex, boolean caseSensitive) {
        if (StrKit.isBlank(regex)) {
            throw new IllegalArgumentException("regex can not be blank.");
        }
        pattern = caseSensitive ? Pattern.compile(regex) : Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    @Override
    public boolean permit(User user, MResource mResource) {
        return pattern.matcher(mResource.mResourceId()).matches();
    }
}
