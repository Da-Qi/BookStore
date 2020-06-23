package dao.Impl;

import dao.ProductDao;
import domain.Order;
import domain.OrderItem;
import domain.Product;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.SimpleIdGenerator;
import utils.JDBCUtils;
import utils.ManagerThreadLocal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductDaoImpl implements ProductDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    public int count(String category) {
        String sql = "select count(*) from products where category = ?";

        Integer integer = template.queryForObject(sql, Integer.class, category);

        return integer;
    }

    public int count() {
        String sql = "select count(*) from products where 1=1";
        Integer integer = template.queryForObject(sql, Integer.class);
        return integer;
    }

    public List<Product> findBooks(String category, int currentpage, int pageSize) {
        String sql = "select * from products where category = ? limit ? , ? ";
        int start = (currentpage-1)*pageSize;
        List<Product> products = template.query(sql, new BeanPropertyRowMapper<Product>(Product.class), category, start, pageSize);
        return products;
    }


    public List<Product> findBooks(int currentpage,int pageSize) {
        String sql = "select * from products limit ? , ? ";
        int start = (currentpage-1)*pageSize;
        List<Product> products = template.query(sql, new BeanPropertyRowMapper<Product>(Product.class), start, pageSize);

        return products;
    }

    public Product findBookById(String id) {
        String sql = "select * from products where id = ?";
        Product product = template.queryForObject(sql, new BeanPropertyRowMapper<Product>(Product.class),id);
        return product;
    }

    @Override
    public void updatePNum(Order order) throws SQLException {

        String sql = "update products set pnum = pnum - ? where id = ?";

        List<OrderItem> items = order.getItems();
        //List list = new ArrayList();
        Object[][] params = new Object[items.size()][];
        for (int i=0;i<items.size();i++){
            OrderItem item = items.get(i);
            params[i]=new Object[]{item.getBuynum(),item.getProduct().getId()};
            //list.add(params[i]);
        }

        //template.batchUpdate(sql,list);
        QueryRunner qr = new QueryRunner();
        qr.batch(ManagerThreadLocal.getConnection(),sql,params);
    }

    /**
     *      **************************Jdbctemplate的模糊查询，切不能SELECT count(*) FROM products WHERE NAME LIKE ‘%?%’
     *      需要用数组拼接字符！！！！！！！！！！！！！！！！！
     * @param bookname
     * @return
     */
    @Override
    public int countByName(String bookname) {
        Object[] para = new Object[]{"%"+bookname+"%"};
        String sql =" SELECT count(*) FROM products WHERE NAME LIKE ? ";
        Integer integer = 0;
        integer = template.queryForObject(sql, Integer.class, para);
        return integer;
    }

    @Override
    public List<Product> findBooksBySearch(String bookname, int currentpage, int pageSize) {
        String sql ="SELECT * FROM products WHERE NAME LIKE ? limit ?,?";
        int start = (currentpage - 1)*pageSize;
        Object[] para = new Object[]{"%"+bookname+"%",start,pageSize};
        List<Product> list = template.query(sql, new BeanPropertyRowMapper<Product>(Product.class),para);
        return list;
    }

    @Override
    public void updateProduct(Product product) {
        String sql = "UPDATE products SET name=?,price=?,category=?,pnum=?,description=? WHERE id = ?";
        template.update(sql,product.getName(),product.getPrice(),product.getCategory(),product.getPnum(),product.getDescription(),product.getId());
    }

    @Override
    public void deleteProductById(String productId) {
        String sql = "delete from products where id = ?";
        template.update(sql,productId);
    }

    @Override
    public List<Product> findProductByConditions(String id, String category, String name, String minprice, String maxprice) {
        List<Product> list = new ArrayList<Product>();
        ProductDao pd = new ProductDaoImpl();
        //1. 拼接字符串
        StringBuilder sb = new StringBuilder();
        //2. sql语句
        String sql = "select * from products where 1=1 ";
        //3. 存储参数
        List list1 = new ArrayList();

        if (id != null&& !"".equals(id)){
            Product bookById = pd.findBookById(id);
            list.add(bookById);
            return list;
        }
        if (name != null&& !"".equals(name)){
            //模糊查询
            sb.append(" and name like ? ");
            list1.add("%"+name+"%");

        }
        if (category != null&& !"".equals(category)) {
            sb.append(" and category= ? ");
            list1.add(category);

        }
        if (minprice != null&& !"".equals(minprice)){
            sb.append("and price >= ? ");
            list1.add(minprice);

        }
        if (maxprice != null&& !"".equals(maxprice)){
            sb.append("and price <= ?");
            list1.add(maxprice);
        }
        sql += sb.toString();
        List<Product> products = template.query(sql, new BeanPropertyRowMapper<Product>(Product.class),list1.toArray());
        return products;
    }

    @Override
    public void addProduct(Product product) {
        String sql = "insert into products(name,price,category,pnum,imgurl,description)" +
                "  values(?,?,?,?,?,?)";
        template.update(sql,product.getName(),product.getPrice(),product.getCategory(),product.getPnum(),product.getImgurl(),product.getDescription());
    }

/*
    public static void main(String[] args) {
        ProductDao pd = new ProductDaoImpl();
        List<Product> products = pd.findProductByConditions("1", "计算机", null, "58.88", "200");
        for (Product product : products) {
            System.out.println(product);
        }
    }
*/

}
