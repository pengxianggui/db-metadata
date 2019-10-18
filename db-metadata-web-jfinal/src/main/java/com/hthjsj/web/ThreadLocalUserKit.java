package com.hthjsj.web;

/**
 * <p> Class title:  线程内User共享</p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ThreadLocalUserKit {

    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<User>();

    public static void setUser(User user) {
        userThreadLocal.set(user);
    }

    public static User getUser() {
        return userThreadLocal.get();
    }

    public static void removeUser(User user) {
        userThreadLocal.remove();
    }
}
