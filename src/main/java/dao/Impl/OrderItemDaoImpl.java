package dao.Impl;

import dao.OrderItemDao;
import domain.OrderItem;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;
import utils.ManagerThreadLocal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDaoImpl implements OrderItemDao {
    //JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public void addItems(List<OrderItem> items) throws SQLException {
        String sql = "insert into orderitem (order_id,product_id,buynum) values(?,?,?)";
        //多次调用sql，性能不高
        /*for (OrderItem item : items) {
            //System.out.println(item);
            template.update(sql,item.getOrder().getId(),item.getProduct().getId(),item.getBuynum());
        }*/
        //sql批处理
        Object[][] params = new Object[items.size()][];
        //List list = new ArrayList();
        for (int i = 0 ;i<items.size();i++){
            OrderItem item = items.get(i);
            params[i]=new Object[]{item.getOrder().getId(),item.getProduct().getId(),item.getBuynum()};
            //list.add(params[i]);
        }
        QueryRunner qr = new QueryRunner();
        qr.batch(ManagerThreadLocal.getConnection(),sql,params);
        //template.batchUpdate(sql,list); //无法实现事务控制；
    }
}
