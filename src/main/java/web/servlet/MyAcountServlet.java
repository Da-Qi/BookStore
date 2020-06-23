package web.servlet;

import domain.User;
import service.ServiceImpl.UserServiceImpl;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/myacount")
public class MyAcountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //如果是登录状态，进入myAcount.jsp
        User user = (User) request.getSession().getAttribute("user");
        if (user != null){
            response.sendRedirect(request.getContextPath()+"/myAccount.jsp");
        }else {
            //如果未登录，则进去login.jsp
            response.sendRedirect(request.getContextPath()+"/login.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
