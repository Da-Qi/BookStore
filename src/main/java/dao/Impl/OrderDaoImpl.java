package dao.Impl;

import dao.OrderDao;
import domain.Order;
import domain.OrderItem;
import domain.Product;
import domain.User;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import service.ServiceImpl.UserServiceImpl;
import service.UserService;
import utils.JDBCUtils;
import utils.ManagerThreadLocal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public void add(Order order) throws SQLException {

        String sql = "insert into orders  values(?,?,?,?,?,?,?,?) ";
        List<Object> list = new ArrayList<Object>();
        list.add(order.getId());
        list.add(order.getMoney());
        list.add(order.getReceiverAddress());
        list.add(order.getReceiverName());
        list.add(order.getReceiverPhone());
        list.add(order.getPaystate());
        list.add(order.getOrdertime());
        list.add(order.getUser().getId());

        QueryRunner qr = new QueryRunner();
        qr.update(ManagerThreadLocal.getConnection(),sql,list.toArray());

    }

    @Override
    public List<Order> findOrdersByUserId(String userid) {
        String sql = "select * from orders where user_id = ? ";
        List<Order> orders = template.query(sql, new BeanPropertyRowMapper<Order>(Order.class), userid);
        return orders;
    }

    @Override
    public Order findOrderByOrderId(String id) {
        //查询订单编号对应的订单
        String sql1 = "select * from orders where id = ? ";
        Order order = template.queryForObject(sql1, new BeanPropertyRowMapper<Order>(Order.class), id);
        //
        String sql2 = "select o.*,p.name,p.price from orderitem o,products p where o.product_id = p.id and order_id=?";
        List<OrderItem> mItems = template.query(sql2, new ResultSetExtractor<List<OrderItem>>() {
            @Override
            public List<OrderItem> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                //1. 创建集合对象
                List<OrderItem> items = new ArrayList<OrderItem>();
                //2. 遍历
                while (resultSet.next()){
                    //创建OrderItem对象
                    OrderItem item = new OrderItem();
                    item.setBuynum(resultSet.getInt("buynum"));

                    //创建Product对象
                    Product product = new Product();
                    product.setId(resultSet.getInt("product_id"));
                    product.setName(resultSet.getString("name"));
                    product.setPrice(resultSet.getDouble("price"));

                    item.setProduct(product);

                    items.add(item);
                }
                return items;
            }
        },id);
        order.setItems(mItems);
        return order;
    }

    @Override
    public List<Order> findAllOrders() {
        /*String sql = "select * from orders";
        List<Order> query = template.query(sql, new BeanPropertyRowMapper<Order>(Order.class));
        return query;*/

        String sql = "SELECT o.*,u.username FROM orders o,USER u WHERE o.`user_id`= u.`id`";
        List<Order> orders = template.query(sql, new ResultSetExtractor<List<Order>>() {
            @Override
            public List<Order> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<Order> list = new ArrayList<Order>();
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setId(resultSet.getString("id"));
                    order.setReceiverName(resultSet.getString("receiverName"));
                    order.setReceiverAddress(resultSet.getString("receiverAddress"));
                    order.setReceiverPhone(resultSet.getString("receiverPhone"));
                    order.setMoney(resultSet.getDouble("money"));
                    order.setPaystate(resultSet.getInt("paystate"));

                    String username = resultSet.getString("username");
                    User user = new User();
                    user.setUsername(username);
                    order.setUser(user);
                    list.add(order);
                }
                return list;
            }
        });

        return orders;
    }

/*    public static void main(String[] args) {
        Order order = new OrderDaoImpl().findOrderByOrderId("775b890a-eae8-479f-884d-ac862f4c65f1");
        System.out.println(order);
        System.out.println("商品详情");
        for (OrderItem item : order.getItems()){
            System.out.println(item.getProduct().getName());
            System.out.println(item.getBuynum());
            System.out.println(item.getProduct().getPrice());
        }
    }*/
}
