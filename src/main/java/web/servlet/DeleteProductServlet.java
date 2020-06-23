package web.servlet;

import service.ProductService;
import service.ServiceImpl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteProduct")
public class DeleteProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取要删除的书籍的编号
        String productId = request.getParameter("productId");
        //2.调用删除服务
        ProductService ps = new ProductServiceImpl();
        ps.deleteProductById(productId);
        //3.跳转
        response.sendRedirect(request.getContextPath() + "/adminList");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
