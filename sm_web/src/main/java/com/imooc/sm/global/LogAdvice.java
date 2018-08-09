package com.imooc.sm.global;

import com.imooc.sm.entity.Log;
import com.imooc.sm.entity.Staff;
import com.imooc.sm.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@Aspect
public class LogAdvice {

    @Autowired
    private LogService logService;

    /**
     * 操作日志
     * @param joinPoint
     */
    //设置切入对象,同时要屏蔽掉selfController的方法和to开头的方法
    @After("execution(* com.imooc.sm.controller.*.*(..)) && !execution(* com.imooc.sm.controller.SelfController.*(..))&& !execution(* com.imooc.sm.controller.*.to*(..))")
    public void operationLog(JoinPoint joinPoint){
        Log log = new Log();
        //设置操作模块
        log.setMoudle(joinPoint.getTarget().getClass().getSimpleName());
        //设置操作,从方法名中找
        log.setOperation(joinPoint.getSignature().getName());
        //设置操作员,(joinPoint就是操作方法)操作方法的第一项参数就是request对象
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("USER");
        Staff staff = (Staff)obj;
        //设置操作员
        log.setOperator(staff.getAccount());
        //设置结果
        log.setResult("成功");
        //添加操作日志
        logService.addOperationLog(log);

    }

    /**
     * 系统日志,当操作出现异常然后记录
     * @param joinPoint
     * @param e
     */
    //设置切入对象,同时要屏蔽掉selfController的方法,当to开头的也有可能会异常,所以不能隐蔽
    @AfterThrowing(throwing = "e" ,pointcut = "execution(* com.imooc.sm.controller.*.*(..)) && !execution(* com.imooc.sm.controller.SelfController.*(..))")
    public void systemLog(JoinPoint joinPoint, Throwable e){
        Log log = new Log();
        //设置操作模块
        log.setMoudle(joinPoint.getTarget().getClass().getSimpleName());
        //设置操作,从方法名中找
        log.setOperation(joinPoint.getSignature().getName());
        //设置操作员,(joinPoint就是操作方法)操作方法的第一项参数就是request对象
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("USER");
        Staff staff = (Staff)obj;
        //设置操作员
        log.setOperator(staff.getAccount());
        //设置结果,得到异常结果
        log.setResult(e.getClass().getSimpleName());
        //添加系统日志
        logService.addSystemLog(log);

    }

    /**
     * 登录日志
     * @param joinPoint
     */
    @After("execution(* com.imooc.sm.controller.SelfController.login(..))")
    public void loginLog(JoinPoint joinPoint){
        log(joinPoint);
    }


    /**
     * 退出日志
     * @param joinPoint
     */
    @Before("execution(* com.imooc.sm.controller.SelfController.logout(..))")
    public void logoutLog(JoinPoint joinPoint){
        log(joinPoint);
    }


    /**
     * 由于登录日志模块和退出日志模块十分相似,可以采用这样的方法公用一个私有方法,执行效果相同
     * @param joinPoint
     */
    private void log(JoinPoint joinPoint){
        Log log = new Log();
        //设置操作模块
        log.setMoudle(joinPoint.getTarget().getClass().getSimpleName());
        //设置操作,从方法名中找
        log.setOperation(joinPoint.getSignature().getName());
        //设置操作员,(joinPoint就是操作方法)操作方法的第一项参数就是request对象
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("USER");
        //如果obj为空
        if (obj == null){

            log.setOperator(request.getParameter("account"));
            log.setResult("失败");
        }else {
            Staff staff = (Staff)obj;
            //设置操作员
            log.setOperator(staff.getAccount());
            //设置结果,得到异常结果
            log.setResult("成功");
        }
        //添加系统日志
        logService.addLoginLog(log);
    }

}
