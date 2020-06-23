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

@WebServlet("/showProductByPage")
public class ShowProductByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取参数
        String category = request.getParameter("category");
        String pageStr = request.getParameter("page");
        int page = 1;
        //如果传递了page参数
        if (pageStr != null && !"".equals(pageStr)){
            page = Integer.parseInt(pageStr);
        }
        //2. 调用service
        ProductService ps = new ProductServiceImpl();
        PageResult<Product> pageResult = ps.findBooksByCategory(category, page);

        //3. 存在request
        request.setAttribute("category",category);
        request.setAttribute("pageResult",pageResult);
        //4. 跳转页面
        request.getRequestDispatcher("/product_list.jsp").forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
