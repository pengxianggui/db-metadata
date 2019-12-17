package com.hthjsj.web.jfinal;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.Ret;
import lombok.extern.slf4j.Slf4j;

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
        try {
            inv.invoke();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Ret ret = Ret.fail();
            if (JFinal.me().getConstants().getDevMode()) {
                ret.set("request_uri", inv.getController().getRequest().getRequestURI());
                //                ret.set("ex_msg", Throwables.getStackTraceAsString(e));
            }
            controller.renderJson(ret.set("code", 500).set("msg", e.getMessage()));
        }
//
//
//        /**
//         * 异常信息保存在数据库中,方便在线查阅
//         * 过滤TemplateUtils生成的exception信息
//         * 测试异常：
//         * 内容1：新增异常:<br/><p title="com.jfinal.plugin.activerecord.ActiveRecordException: com.mysql.jdbc.MysqlDataTruncation: Data truncation: Data too long for column 'inname' at row 1at com.jfinal.plugin.activerecord.DbPro.save(DbPro.java:588)
//         * 内容2：java.lang.ArithmeticException: / by zero
//         * 内容3：com.jfinal.plugin.activerecord.ActiveRecordException: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column 'v_studentid' in 'where clause'
//         *
//         */
//        String[] msg = UtilKit.getMatcherValue("\"(.*?Exception.*)(?=at)", e.getMessage().toString());
//        //内容1，2匹配失败后 手工指定
//        if(xx.isEmpty(msg)){
//            msg=new String[]{e.getMessage().toString()};
//        }
//        //render为null；判定ctrl方法执行过程中被异常打断
//        //指定render默认行为
//        if(controller.getRender()==null){
//            //ajax返回json错误对象
//            if("XMLHttpRequest".equals(controller.getRequest().getHeader("X-Requested-With"))){
//                controller.renderJson(new Easy("操作发生错误："+e.getMessage()));
//            }//非ajax请求
//            else{
//                controller.setAttr("error_msg", Throwables.getStackTraceAsString(e));
//                controller.render("/eova/500.html");
//            }
//        }else{
//            if(!xx.isEmpty(ctrl_errorInfo)){
//                controller.renderJson(new Easy("操作发生错误："+ctrl_errorInfo));
//            }else{
//                controller.renderJson(new Easy(Arrays.toString(msg)));
//            }
//        }
//
//        //save to db
//        try {
//            SysException se = new SysException();
//            se.setExpmsg(Arrays.toString(msg));
//            se.setExpchain(Throwables.getStackTraceAsString(e));
//            se.setExpname(Arrays.toString(msg));
//            se.setExptype(SysException.EXPTYPE_WEBAPP);
//            se.setRequesturl(requestPath.toString());
//            se.setRequestparams(JsonKit.toJson(inv.getController().getRequest().getParameterMap()));
//            se.save();
//        }catch (Exception ex) {
//            log.error(ex.getMessage());
//        }
    }
}
