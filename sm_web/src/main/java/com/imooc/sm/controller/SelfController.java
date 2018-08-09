package com.imooc.sm.controller;

import com.imooc.sm.entity.Staff;
import com.imooc.sm.service.SelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller("selfController")
public class SelfController {
    @Autowired
    private SelfService selfService;

    //打开登录界面,toLogin
    public void toLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request,response);
    }

    //登录操作
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取提交的账号和密码
        String account = request.getParameter("account");
        String password = request.getParameter("password");

        //获取调用了登录方法的对象
        Staff staff = selfService.login(account,password);
        //如果为空即登录失败
        if(staff == null){
            //重定向到登录
            response.sendRedirect("toLogin.do");
        }else {
            //登录成功
            //将staff存到session中去
            HttpSession session = request.getSession();
            session.setAttribute("USER",staff);
            response.sendRedirect("main.do");
        }
    }

    //退出
    //登录操作
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.setAttribute("USER",null);
        response.sendRedirect("toLogin.do");

    }


    //打开主界面    /main.do
    public void main(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }


    //查看个人信息  url结构  /self/info.do
    public void info(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("../info.jsp").forward(request,response);
    }

    //打开修改密码界面   /self/toChangePassword.do
    public void toChangePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("../change_password.jsp").forward(request,response);
    }

    //修改密码
    public void changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取输入原密码和新密码
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");
        //得到session
        HttpSession session = request.getSession();
        //得到staff对象
        Staff staff = (Staff) session.getAttribute("USER");
        //如果两者密码不一致,回到之前页面,重输
        if (!staff.getPassword().equals(password)){
            response.sendRedirect("toChangePassword.do");
        }else {
            //如果一致,则修改密码,然后到退出页面
            selfService.changePassword(staff.getId(),password1);
            response.sendRedirect("../logout.do");
            //由于单纯转向回去的话,框架仍然会存在,十分不美观,此处用一js代码解决
            //<script type="text/javascript">parent.location.href="../logout.do"</script>
            response.getWriter().print("<script type=\"text/javascript\">parent.location.href=\"../logout.do\"</script> ");
        }
    }
}
