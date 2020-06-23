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

@WebServlet("/getProduct")
public class GetProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取要修改商品的id
        String productId = request.getParameter("productId");
        //2. 查找该商品
        ProductService ps = new ProductServiceImpl();
        Product product = ps.findBookById(productId);
        //3. 写入request
        request.setAttribute("product",product);
        //4.转发
        request.getRequestDispatcher("/admin/products/edit.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
