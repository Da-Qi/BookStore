package web.servlet;

import exception.UserException;
import service.ServiceImpl.UserServiceImpl;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 激活功能：
 *  1. 通过激活码找到用户
 *  2. 通过激活码改变用户状态
 */
@WebServlet("/active")
public class ActiveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("content-type","text/html;charset=utf-8");
        //1. 获取参数
        String activeCode = request.getParameter("activeCode");
        //2. 激活
        UserService us = new UserServiceImpl();
        try {
            us.activeUser(activeCode);
            response.getWriter().write("激活成功！");
        } catch (UserException e) {
            e.printStackTrace();
            response.getWriter().write(e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
