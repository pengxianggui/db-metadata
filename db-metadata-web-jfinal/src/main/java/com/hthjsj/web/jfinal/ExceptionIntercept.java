package com.hthjsj.web.jfinal;

import com.google.common.base.Throwables;
import com.hthjsj.App;
import com.hthjsj.analysis.db.SnowFlake;
import com.hthjsj.web.kit.UtilKit;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.json.Json;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * <p> @Date : 2019/10/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class ExceptionIntercept implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        Ret ret = Ret.fail();
        try {
            inv.invoke();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (JFinal.me().getConstants().getDevMode()) {
                ret.set("request_uri", controller.getRequest().getRequestURI());
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
                msg = new String[] { e.getMessage() };
            }
            //save to db
            try {
                Record se = new Record();
                se.set("id", SnowFlake.me().nextId());
                se.set("exp_title", Arrays.toString(msg));
                se.set("exp_chain", allMsgString);
                se.set("exp_msg", Arrays.toString(msg));
                se.set("ext_url", controller.getRequest().getRequestURI());
                se.set("req_data", Json.getJson().toJson(controller.getRequest().getParameterMap()));
                se.set("res_data", Json.getJson().toJson(controller.getRequest().getParameterMap()));
                se.set("created_by", "db-metadata-server");
                se.set("created_time", new Date());
                Db.use(App.DB_MAIN).save("meta_exception", se);
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
            controller.renderJson(ret.set("code", 500).set("msg", e.getMessage()));
        }
    }
}
