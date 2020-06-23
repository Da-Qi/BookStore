package web.servlet;

import domain.Order;
import service.OrderService;
import service.ServiceImpl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/adminOrderList")
public class AdminOrderListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService os = new OrderServiceImpl();
        List<Order> allOrders = os.findAllOrders();
        request.setAttribute("orders",allOrders);
        request.getRequestDispatcher("/admin/orders/list.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
