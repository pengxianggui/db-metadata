package com.github.md.web.config;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.analysis.db.SnowFlake;
import com.github.md.web.WebException;
import com.github.md.web.kit.UtilKit;
import com.github.md.analysis.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 替换ExceptionIntercept
 * <p>
 * RestControllerAdvice + ExceptionHandler 不足:
 * 1. TODO 无法处理Controller调用链外的异常;
 *
 * <p> @Date : 2021/9/9 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
@Order
@RestControllerAdvice
@Slf4j
public class GlobalMetaException {

    @ExceptionHandler(value = {Exception.class})
    public Ret arithmeticExceptionHandle(Exception e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        MetaServerManager metaServerManager = AnalysisSpringUtil.getBean(MetaServerManager.class);
        Ret ret = Ret.fail();

        log.error("url: {}", request.getRequestURI());
        if (e instanceof WebException) {
            log.error(e.getMessage());
        } else {
            log.error(e.getMessage(), e);
        }

        if (metaServerManager.getMetaServerProperties().isDevMode()) {
            ret.set("request_uri", request.getRequestURI());
            //                ret.set("ex_msg", Throwables.getStackTraceAsString(e));
        }
        /**
         * 异常信息保存在数据库中,方便在线查阅
         * 过滤TemplateUtils生成的exception信息
         * 测试异常：
         * 内容1：新增异常:<br/><p title="com.jfinal.plugin.activerecord.ActiveRecordException: com.mysql.jdbc.MysqlDataTruncation: Data truncation: Data too long for column 'inname' at row 1at com.jfinal.plugin.activerecord.DbPro.save(DbPro.java:588)
         * 内容2：java.lang.ArithmeticException: / by zero
         * 内容3：com.jfinal.plugin.activerecord.ActiveRecordException: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column 'v_studentid' in 'where clause'
         *
         */
        String allMsgString = Throwables.getStackTraceAsString(e);
        String[] msg = UtilKit.getMatcherValue("\"(.*?Exception.*)(?=at)", allMsgString, Pattern.CASE_INSENSITIVE);
        //内容1，2匹配失败后 手工指定
        if (StrKit.notBlank(msg)) {
            msg = new String[]{e.getMessage()};
        }
        //save to db
        try {
            Record se = new Record();
            se.set("id", SnowFlake.me().nextId());
            se.set("exp_title", e.getMessage());
            se.set("exp_chain", allMsgString);
            se.set("exp_msg", Arrays.toString(msg));
            se.set("ext_url", request.getRequestURI());
            se.set("req_data", JSON.toJSONString(request.getParameterMap()));
            se.set("res_data", JSON.toJSONString(request.getParameterMap()));
            se.set("created_by", "SYSTEM");
            se.set("created_time", new Date());
            SpringAnalysisManager.me().dbMain().save("meta_exception", se);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        if (e instanceof WebException) {
            return ret.set("code", ((WebException) e).getCode()).set("msg", ((WebException) e).getMsg());
        }
        return ret.set("code", 500).set("msg", e.getMessage());
    }
}
