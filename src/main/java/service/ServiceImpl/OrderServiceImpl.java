package service.ServiceImpl;

import dao.Impl.OrderDaoImpl;
import dao.Impl.OrderItemDaoImpl;
import dao.Impl.ProductDaoImpl;
import dao.OrderDao;
import dao.OrderItemDao;
import dao.ProductDao;
import domain.Order;
import domain.OrderItem;
import service.OrderService;
import utils.ManagerThreadLocal;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();
    @Override
    public void createOrder(Order order) {
        try {
            //开启事务
            ManagerThreadLocal.beginTransaction();
            //1.插入订单表
            orderDao.add(order);
            //2. 插入订单详情表
            orderItemDao.addItems(order.getItems());
            //3. 更新库存
            productDao.updatePNum(order);
            //结束事务
            ManagerThreadLocal.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            //事务回滚
            ManagerThreadLocal.rollback();
        }
    }

    @Override
    public List<Order> findOrdersByUserId(String userid) {
        try {
            return orderDao.findOrdersByUserId(userid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Order findOrderByOrderId(String orderid) {
        try {
            return orderDao.findOrderByOrderId(orderid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Order> findAllOrders() {
        OrderDao od = new OrderDaoImpl();
        return od.findAllOrders();
    }


}
