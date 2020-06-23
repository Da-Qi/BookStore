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
import java.util.List;
import java.util.Map;

@WebServlet("/findProductByManyCondition")
public class FindProductByManyConditionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取参数
        String id = request.getParameter("id");
        String category = request.getParameter("category");
        String name = request.getParameter("name");
        String minprice = request.getParameter("minprice");
        String maxprice = request.getParameter("maxprice");

        System.out.println(id);
        System.out.println(category);
        System.out.println(name);
        System.out.println(minprice);
        System.out.println(maxprice);
        //2.调用查询服务
        ProductService ps = new ProductServiceImpl();
        PageResult<Product> pr = ps.findProductByConditions(id,category,name,minprice,maxprice);
        List<Product> list = pr.getList();
        request.setAttribute("pageResult",pr);
        request.getRequestDispatcher("/admin/products/list.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
