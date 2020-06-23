package web.servlet;

import domain.User;
import exception.UserException;
import org.apache.commons.beanutils.BeanUtils;
import service.ServiceImpl.UserServiceImpl;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@WebServlet("/modifyUserInfo")
public class ModifyUserInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        try {
            BeanUtils.populate(user,request.getParameterMap());
            System.out.println(user);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService us = new UserServiceImpl();
        try {
            us.modifyUserInfo(user);
        } catch (UserException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath()+"/modifyUserInfoSuccess.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
