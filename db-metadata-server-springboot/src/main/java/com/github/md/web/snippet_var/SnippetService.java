package com.github.md.web.snippet_var;

import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import cn.hutool.extra.template.engine.beetl.BeetlEngine;
import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.analysis.kit.Kv;
import com.github.md.web.AppConst;
import com.github.md.web.ServiceManager;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SnippetService {
    private static final String SNIPPET_CACHE_NAME = "meta_snippet";
    private VarService varService;

    public SnippetService(VarService varService) {
        this.varService = varService;
    }

    public List<Kv> listForOptions() {
        // ServiceManager.getSnippetService().getAllSnippets() 避免AOP代理问题导致缓存不生效
        return ServiceManager.getSnippetService().getAllSnippets().stream()
                .map(r ->
                        Kv.by("key", String.format("%s-%s", r.getStr("code"), r.getStr("title")))
                                .set("value", r.getStr("code")))
                .collect(Collectors.toList());
    }

    /**
     * 获取snippet代码内容。
     * <p>
     *
     * @param renderFnOrSnippetCode 可能是完整的snippet函数字符串，也可能是snippet编码，指向snippet表中唯一记录。优先匹配snippet编码，若
     *                              检索到存在snippet，则取之函数内容； 若未匹配到，则视为函数字符串。important: 为了版本兼容过度。
     * @param ifCompileVar          是否编译snippet, 若为true, 则将获取到的snippet代码片段执行模板渲染，替换其中存在的全局变量。
     * @return 返回snippet代码片段(字符串形式)。若renderFnOrSnippetCode能匹配到snippet，则取数据库里存储的snippet代码片段，否则取
     * 入参renderFnOrSnippetCode, 并依据入参ifCompileVar决定是否返回编译后的snippet代码片段。
     */
    public String getSnippet(String renderFnOrSnippetCode, boolean ifCompileVar) {
        List<Record> records = ServiceManager.getSnippetService().getAllSnippets(); //避免AOP代理问题导致缓存不生效
        if (CollectionUtils.isEmpty(records)) {
            return renderFnOrSnippetCode;
        }

        String snippet = records.stream().filter(r -> r.getStr("code").equals(renderFnOrSnippetCode)).findFirst().map(r -> r.getStr("content")).orElse(renderFnOrSnippetCode);

        if (ifCompileVar) {
            return compileSnippet(snippet);
        }

        return snippet;
    }

    /**
     * 编译snippet代码片段, 采用beetl语法，动态参数为全局变量(meta_var)。
     *
     * @param snippet
     * @return
     */
    private String compileSnippet(String snippet) {
        if (StrKit.isBlank(snippet)) {
            return snippet;
        }

        Map<String, String> varsMap = varService.getAllVars().stream()
                .collect(Collectors.toMap(
                        r -> r.getStr("name"),
                        r -> StrKit.defaultIfBlank(r.getStr("value"), "")));

        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig().setCustomEngine(BeetlEngine.class));
        Template template = engine.getTemplate(snippet);
        return template.render(varsMap);
    }


    /**
     * 获取所有的代码片段。
     *
     * @return
     */
    @Cacheable(value = SNIPPET_CACHE_NAME, key = "'all'")
    public List<Record> getAllSnippets() {
        return SpringAnalysisManager.me().dbMain().findAll(AppConst.INNER_TABLE.META_SNIPPET.getTableName());
    }

    @CacheEvict(value = SNIPPET_CACHE_NAME, key = "'all'")
    public void clearCache() {
        log.debug("The cache of {} has been cleared", SNIPPET_CACHE_NAME);
    }

}
