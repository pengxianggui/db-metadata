package com.dbmeta.starter;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * SpringBoot 默认的{@link org.springframework.web.multipart.support.StandardServletMultipartResolver} 会与db-meta底层使用
 * 的JFinal上传组件cos有冲突，具体冲突机制还不完全确定，不过有如下一些参考材料:
 * 1. https://blog.csdn.net/kai_wei/article/details/51142324
 * 2. https://blog.csdn.net/ruben95001/article/details/102497089?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.baidujs&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.baidujs
 * 3. https://blog.csdn.net/weixin_42447959/article/details/106404204?utm_medium=distribute.pc_relevant.none-task-blog-baidujs_title-1&spm=1001.2101.3001.4242
 * 4. https://www.b521.net/archives/198.html
 * 5. https://blog.csdn.net/zhou_pp/article/details/85060076
 * 6. http://commons.apache.org/proper/commons-fileupload/using.html
 * 7. http://www.ciphermagic.cn/spring-boot-upload.html
 *
 * 因此，使用 {@link org.springframework.web.multipart.commons.CommonsMultipartResolver} 替代
 * {@link org.springframework.web.multipart.support.StandardServletMultipartResolver}。但是excludeUrlArray并没有起作用，isMultipart
 * 并没有被调用。TODO 需要排查 {@link org.springframework.web.multipart.support.StandardServletMultipartResolver} 与 cos冲突的原因。
 * @author pengxg
 * 2021/2/5 3:06 下午
 */
public class MyMultipartResolver extends CommonsMultipartResolver {

    private String excludeUrls;
    private String[] excludeUrlArray;

    public String getExcludeUrls() {
        return excludeUrls;
    }


    public void setExcludeUrls(String excludeUrls) {
        this.excludeUrls = excludeUrls;
        this.excludeUrlArray = excludeUrls.split(",");
    }


    /**
     * 这里是处理Multipart http的方法。如果这个返回值为true,那么Multipart http body就会MyMultipartResolver 消耗掉.如果这里返回false
     * 那么就会交给后面的自己写的处理函数处理例如刚才ServletFileUpload 所在的函数
     *
     * @see org.springframework.web.multipart.commons.CommonsMultipartResolver#isMultipart(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public boolean isMultipart(HttpServletRequest request) {
        for (String url : excludeUrlArray) {
            if (request.getRequestURI().contains(url)) {
                return false;
            }
        }

        return super.isMultipart(request);
    }
}
