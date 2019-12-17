package com.hthjsj.web.auth;

import com.hthjsj.web.user.User;
import com.jfinal.kit.StrKit;

import java.util.regex.Pattern;

/**
 * <p> @Date : 2019/12/17 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class RegularPermit implements MRPermit {

    private Pattern pattern;

    public RegularPermit(String regex) {
        this(regex, true);
    }

    public RegularPermit(String regex, boolean caseSensitive) {
        if (StrKit.isBlank(regex)) {
            throw new IllegalArgumentException("regex can not be blank.");
        }
        pattern = caseSensitive ? java.util.regex.Pattern.compile(regex) : java.util.regex.Pattern.compile(regex, java.util.regex.Pattern.CASE_INSENSITIVE);
    }

    @Override
    public boolean permit(User user, MResource mResource) {
        return pattern.matcher(mResource.mResourceId()).matches();
    }
}
