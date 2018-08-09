package com.imooc.sm.controller;

import com.imooc.sm.entity.Department;
import com.imooc.sm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller("departmentController")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    //  /department/list.do     /department_list.jsp
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> list = departmentService.getAll();
        request.setAttribute("LIST",list);
        request.getRequestDispatcher("../department_list.jsp").forward(request,response);
    }

    //添加功能(直接转发到添加界面)
    public void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("../department_add.jsp").forward(request,response);
    }

    //实际添加功能
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收值
        String name = request.getParameter("name");
        String address = request.getParameter("address");

        //构造一个部门对象
        Department department = new Department();
        //将得到的值设置到其属性中去
        department.setName(name);
        department.setAddress(address);
        //添加进去
        departmentService.add(department);
        //由于不用传值,所以采用重定向到list.do(注意是转到方法上,而不是页面)
        response.sendRedirect("list.do");

    }

    //修改功能toEdit,要得到其id
    public void toEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //先得到其id
        Integer id = Integer.parseInt(request.getParameter("id"));
        //通过访问业务处理对象以id查找该部门对象,且初始化给一个部门对象
        Department department = departmentService.get(id);
        //设置request的参数OBJ为该对象
        request.setAttribute("OBJ",department);
        //转发到修改页面
        request.getRequestDispatcher("../department_edit.jsp").forward(request,response);

    }


    //实际修改功能
    public void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收值
        //先得到其id
        Integer id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String address = request.getParameter("address");

        //构造一个部门对象
        Department department = new Department();
        //将得到的值设置到其属性中去
        department.setId(id);
        department.setName(name);
        department.setAddress(address);
        //编辑
        departmentService.edit(department);
        //由于不用传值,所以采用重定向到list.do(注意是转到方法上,而不是页面)
        response.sendRedirect("list.do");

    }

    //修改功能
    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //先得到其id
        Integer id = Integer.parseInt(request.getParameter("id"));
        //直接删除
        departmentService.remove(id);
        //由于不用传值,所以采用重定向到list.do(注意是转到方法上,而不是页面)
        response.sendRedirect("list.do");
    }


}
