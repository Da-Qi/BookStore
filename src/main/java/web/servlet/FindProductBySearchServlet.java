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

@WebServlet("/findProductBySearch")
public class FindProductBySearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String name = request.getParameter("name");
        String pageStr = request.getParameter("page");

        int page = 1;
        //如果传递了page参数
        if (pageStr != null && !"".equals(pageStr)){
            page = Integer.parseInt(pageStr);
        }
        //调用服务
        ProductService ps =new ProductServiceImpl();
        PageResult<Product> pageResult = ps.findBooksBySearch(name, page);

        //存入request
        request.setAttribute("name",name);
        request.setAttribute("pageResult",pageResult);
        //4. 跳转页面
        request.getRequestDispatcher("/product_Searchlist.jsp").forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
