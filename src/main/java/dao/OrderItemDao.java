package dao;

import domain.OrderItem;

import java.sql.SQLException;
import java.util.List;

public interface OrderItemDao {
    /**
     * 订单详情传入数据库
     * @param items
     */
    void addItems(List<OrderItem> items) throws SQLException;
}
