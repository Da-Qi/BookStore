package dao;

import domain.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    /**
     * 添加订单
     * @param order
     */
    void add(Order order) throws SQLException;

    /**
     * 通过用户id查找订单
     * @param userid
     * @return 所有订单
     */
    List<Order> findOrdersByUserId(String userid);

    /**
     * 通过订单编号查找订单
     * @param id
     * @return
     */
    Order findOrderByOrderId(String id);

    /**
     * 查询所有订单
     * @return
     */
    List<Order> findAllOrders();
}
