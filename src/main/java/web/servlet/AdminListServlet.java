package web.servlet;

import domain.PageResult;
import domain.Product;
import service.ProductService;
import service.ServiceImpl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/adminList")
public class AdminListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //2. 调用service
        ProductService ps = new ProductServiceImpl();
        PageResult<Product> pageResult = ps.findBooksByCategory(null, 1,100);
        //3. 存在request
        request.setAttribute("pageResult",pageResult);
        //4. 跳转页面
        request.getRequestDispatcher("/admin/products/list.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
