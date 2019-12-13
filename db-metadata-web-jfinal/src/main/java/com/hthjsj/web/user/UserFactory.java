package com.hthjsj.web.user;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface UserFactory<U extends User> {

    /**
     * 方法前使用<T> 仅仅标志该方法是泛型方法,对类上下不影响,如果类已经标志了泛型,可以直接在方法中使用;
     */
//    <U extends User> UserService<U> createService();
//
//    <U extends User> LoginService<U> loginService();
//
//    <U extends User> User createUser(Kv params);

    UserService<U> createService();

    LoginService<U> loginService();
}
