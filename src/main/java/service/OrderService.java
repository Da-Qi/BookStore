package service;

import domain.Order;
import domain.OrderItem;

import java.util.List;

public interface OrderService {
    /**
     * 生成订单写入数据库
     * @param order
     */
    void createOrder(Order order);

    /**
     * 通过用户id查找订单
     * @param userid
     * @return
     */
    List<Order> findOrdersByUserId(String userid);

    /**
     * 通过订单id查找订单
     * @param orderid
     * @return
     */
    Order findOrderByOrderId(String orderid);

    /**
     * 查询所有订单
     * @return
     */
    List<Order> findAllOrders();
}
