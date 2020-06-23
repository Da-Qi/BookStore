package web.servlet;

import domain.Order;
import domain.OrderItem;
import domain.Product;
import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import service.OrderService;
import service.ServiceImpl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@WebServlet("/order")
public class OrderServlet extends BaseServlet {

    /**
     * 通过用户id查找其订单
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void  findOrderById(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            response.getWriter().write("非法访问");
            return;
        }

        //调用Service
        OrderService os = new OrderServiceImpl();
        List<Order> orders = os.findOrdersByUserId(String.valueOf(user.getId()));
        //存储数据到request
        request.setAttribute("orders",orders);
        request.getRequestDispatcher("/orderlist.jsp").forward(request,response);
    }

    public void createOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取session的user
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            response.getWriter().write("非法访问！");
            return;
        }
        //获取购物车信息
        Map<Product,Integer> cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");
        if (cart == null || cart.size() == 0){
            response.getWriter().write("尊敬的顾客，得先选择心仪物品加入购物车哟~");
        }
        Order order = new Order();
        try {
            //1. 封装数据
            BeanUtils.populate(order,request.getParameterMap());
            //2. 补全order数据
            //订单编号
            order.setId(UUID.randomUUID().toString());
            //下单时间
            order.setOrdertime(new Date());
            //顾客信息
            order.setUser(user);
            double totalprice = 0;
            //3. 封装订单详情
            List<OrderItem> items = new ArrayList<OrderItem>();
            for (Map.Entry<Product,Integer> entry : cart.entrySet()) {
                OrderItem item = new OrderItem();
                //设置购物数量
                item.setBuynum(entry.getValue());
                //设置商品
                item.setProduct(entry.getKey());
                //设置订单详情
                item.setOrder(order);

                totalprice += entry.getValue() * entry.getKey().getPrice();

                items.add(item);
            }
            order.setMoney(totalprice);
            //方便传参
            order.setItems(items);

            //插入数据库
            OrderService orderService = new OrderServiceImpl();
            orderService.createOrder(order);

            //订单成功
            request.getSession().removeAttribute("cart");

            response.sendRedirect(request.getContextPath()+"/createOrderSuccess.jsp");

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void findOrderByOrderId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取orderid
        String orderid = request.getParameter("orderid");
        //2. 调用service
        OrderService os = new OrderServiceImpl();
        Order order = os.findOrderByOrderId(orderid);
        //3. 存储加转发
        request.setAttribute("order",order);
        request.getRequestDispatcher("/orderInfo.jsp").forward(request,response);
    }

}
