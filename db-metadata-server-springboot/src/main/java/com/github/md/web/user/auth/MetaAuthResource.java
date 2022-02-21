package com.github.md.web.user.auth;

import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.meta.AuthForType;
import com.github.md.analysis.meta.AuthTypeRefered;
import com.github.md.web.ServiceManager;
import com.github.md.web.config.QuickJudge;
import com.github.md.web.query.QueryHelper;
import com.jfinal.plugin.activerecord.Record;
import lombok.Getter;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 默认的权限，即为meta_auth表。即为资源，也为权限。
 * <p>
 * 兼容元编码的接口资源(meta_auth表)。<br>
 * 一般，Rest接口的鉴权通常由在接口上打上注解标记，标识接口关联的权限编码。从而在接口访问前，判定用户是否具有此权限。
 * <p>
 * 而当前资源标记，不是硬编码在接口上的，而是存储在数据表meta_auth中。而且由于dbmeta一些内置接口的特殊性(不同元对象共享一个接口), 因此扩展出
 * {@link AuthForType}的概念。
 * <p>
 * 你可以通过唯一的构造函数，基于表数据构造此对象。也可以通过静态方法{@link #buildByRequest(HttpServletRequest, HandlerMethod)}从运行时的
 * 请求中构建此对象。若依据url、type、metaCode无法关联meta_auth表的记录，则表示未配置此资源-权限映射。此资源将设为无需鉴权。
 *
 * @author pengxg
 * @date 2021/10/15 9:51 上午
 */
public class MetaAuthResource implements MResource, IAuth {
    private Record data;
    private boolean needPermit;

    public MetaAuthResource(Record record) {
        if (record == null) {
            this.needPermit = false;
            this.data = new Record();
        } else {
            this.needPermit = true;
            this.data = record;
        }
    }

    public static MetaAuthResource buildByRequest(HttpServletRequest request, HandlerMethod handlerMethod) {
        String uri = removeEndSlash(request.getServletPath());

        AuthForType type;
        String metaCode = null;
        AuthTypeRefered authTypeRefered = handlerMethod.getMethodAnnotation(AuthTypeRefered.class);
        if (Objects.isNull(authTypeRefered)) {
            type = AuthForType.NORMAL; // 默认此资源鉴权类型为 NORMAL
        } else {
            type = authTypeRefered.value();
            QueryHelper queryHelper = new QueryHelper(((QuickJudge) AnalysisSpringUtil.getBean(QuickJudge.class)).getRequest());
            switch (type) {
                case API_WITH_META_OBJECT:
                    metaCode = queryHelper.getObjectCode();
                    break;
                case API_WITH_META_FEATURE:
                    metaCode = queryHelper.getFeatureCode();
                    break;
            }
        }

        Record record = ServiceManager.authService().findOne(type, metaCode, uri);
        return new MetaAuthResource(record);
    }

    private static String removeEndSlash(String servletPath) {
        if (servletPath.endsWith("/")) {
            return servletPath.substring(0, servletPath.length() - 1);
        }
        return servletPath;
    }

    @Override
    public String mResourceId() {
        return this.data.getStr("id");
    }

    @Override
    public String mResourceName() {
        return this.data.getStr("name");
    }

    @Override
    public boolean needPermit() {
        return this.needPermit;
    }

    @Override
    public String code() {
        return data.getStr("code");
    }

    @Override
    public String name() {
        return data.getStr("name");
    }

    @Override
    public Kv toKv() {
        return Kv.create().set(this.data.getColumns());
    }
}
