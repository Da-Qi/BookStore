package web.servlet;

import domain.Product;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/settleAccount")
public class SettleAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断当前浏览器是否有登录用户
        User user = (User) request.getSession().getAttribute("user");
        if (user != null){
            //如果有，进入订单界面
            response.sendRedirect(request.getContextPath()+"/order.jsp");
        }else {
            //否则，先登录
            response.sendRedirect(request.getContextPath()+"/login.jsp");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
