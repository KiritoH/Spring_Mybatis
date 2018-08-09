package com.imooc.sm.global;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //获取请求路径
        String path = request.getServletPath();
        //如果含有login,就直接放行,先要转换成小写
        if (path.toLowerCase().indexOf("login")!=-1){
            chain.doFilter(request,response);
        }else{
            //这里的url就要登录才能进入
            HttpSession session = request.getSession();
            Object obj = session.getAttribute("USER");
            //如果不为空
            if (obj!=null){
                chain.doFilter(request,response);
            }else {
                //为了防止路径错误,这里使用绝对路径
                response.sendRedirect(request.getContextPath()+"/toLogin.do");
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
