package web.servlet;

import domain.Product;
import service.ProductService;
import service.ServiceImpl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/changeNum")
public class ChangeNumServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String num = request.getParameter("num");
        Map<Product,Integer> cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");

        ProductService ps = new ProductServiceImpl();
        Product book = ps.findBookById(id);
        int pnum = book.getPnum();
        if (Integer.parseInt(num)>pnum || Integer.parseInt(num)<0){
           response.sendRedirect(request.getContextPath()+"/cart.jsp");
        }else {

            if ("0".equals(num)) {
                cart.remove(book);
            } else {
                cart.put(book, Integer.parseInt(num));
            }
            request.getSession().setAttribute("cart", cart);
            //回到购物车页面
            response.sendRedirect(request.getContextPath() + "/cart.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
