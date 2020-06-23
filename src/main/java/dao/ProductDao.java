package dao;

import domain.Order;
import domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    /**
     * 查询该种类的总数
     * @param category
     * @return
     */
    int count(String category);

    /**
     * 查询所有商品总数
     * @return
     */
    int count();

    /**
     * 根据种类和页面查询书籍；
     * @param category  类型
     * @param currentpage  当前页
     * @param pageSize 每页显示的条数
     * @return
     */
    List<Product> findBooks(String category, int currentpage,int pageSize);

    /**
     * 查询所有书籍
     * @param currentpage
     * @param pageSize
     * @return
     */
    List<Product> findBooks(int currentpage,int pageSize);

    /**
     * 根据id查找书籍
     * @param id
     * @return
     */
    Product findBookById(String id);

    /**
     * 更新库存
     * @param order 订单
     */
    void updatePNum(Order order) throws SQLException;

    /**
     * 根据书名查找包含它的数量
     * @param bookname
     */
    int countByName(String bookname);

    /**
     * 根据书名搜索结果，传出某一页的书结果
     * @param bookname
     * @param currentpage
     * @param pageSize
     */
    List<Product> findBooksBySearch(String bookname, int currentpage, int pageSize);

    /**
     * 更新书籍信息
     * @param product
     */
    void updateProduct(Product product);

    /**
     * 删除书籍
     * @param productId
     */
    void deleteProductById(String productId);

    /**
     * 根据条件查询书籍
     * @param id
     * @param category
     * @param name
     * @param minprice
     * @param maxprice
     */
    List<Product> findProductByConditions(String id, String category, String name, String minprice, String maxprice);

    /**
     * 添加商品
     * @param product
     */
    void addProduct(Product product);
}
