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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cart")
public class CartServlet extends BaseServlet {
    /**
     * 书籍添加到购物车
     * @param request
     * @param response
     * @throws IOException
     */
    public void addCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1. 获取id
        String id = request.getParameter("id");
        //2. 通过id获取书
        ProductService ps = new ProductServiceImpl();
        Product book = ps.findBookById(id);
        //3. 把购买商品放在购物车Map
        Map<Product,Integer> cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");
        //判断map里面是否有数据
        if (cart == null){
            cart = new HashMap<Product, Integer>();
            cart.put(book,1);
        }else {
            if (cart.containsKey(book)){
                cart.put(book,cart.get(book)+1);
            }else {
                cart.put(book,1);
            }
        }
        //4. 把cart存在session
        request.getSession().setAttribute("cart",cart);

        //5. 相应客户端页面
        response.sendRedirect(request.getContextPath()+"/showProductByPage");
    }

    public void deleteCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取要去掉商品的id
        String id = request.getParameter("id");
        //修改session的值
        Map<Product,Integer> cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");
        ProductService ps = new ProductServiceImpl();
        Product book = ps.findBookById(id);
        if (cart.containsKey(book)){
            cart.remove(book);
        }
        request.getSession().setAttribute("cart",cart);
        response.sendRedirect(request.getContextPath()+"/cart.jsp");
    }
}
