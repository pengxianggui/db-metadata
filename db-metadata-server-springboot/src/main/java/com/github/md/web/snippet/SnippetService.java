package com.github.md.web.snippet;

import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import cn.hutool.extra.template.engine.beetl.BeetlEngine;
import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.analysis.kit.Kv;
import com.github.md.web.AppConst;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SnippetService {

    public List<Kv> listForOptions() {
        return SpringAnalysisManager.me().dbMain().findAll(AppConst.INNER_TABLE.META_SNIPPET.getTableName()).stream().map(r -> Kv.by("key", String.format("%s-%s", r.getStr("code"), r.getStr("title"))).set("value", r.getStr("code"))).collect(Collectors.toList());
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
        List<Record> records = getAllSnippets();
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

        Map<String, String> varsMap = getAllVars().stream()
                .collect(Collectors.toMap(r -> r.getStr("name"), r -> r.getStr("value")));

        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig().setCustomEngine(BeetlEngine.class));
        Template template = engine.getTemplate(snippet);
        return template.render(varsMap);
    }

    /**
     * 获取所有的全局变量。 TODO 缓存
     *
     * @return
     */
    public List<Record> getAllVars() {
        return SpringAnalysisManager.me().dbMain().findAll(AppConst.INNER_TABLE.META_VAR.getTableName());
    }

    /**
     * 获取所有的代码片段。TODO 缓存
     *
     * @return
     */
    public List<Record> getAllSnippets() {
        return SpringAnalysisManager.me().dbMain().findAll(AppConst.INNER_TABLE.META_SNIPPET.getTableName());
    }
}
