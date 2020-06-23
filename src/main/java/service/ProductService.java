package service;

import domain.PageResult;
import domain.Product;

public interface ProductService {
    /**
     * 用户页面根据种类查询
     * @param category
     * @param currentpage
     * @return
     */
    PageResult<Product> findBooksByCategory(String category,int currentpage);

    /**
     * 管理员页面查询
     * @param category
     * @param currentpage
     * @return
     */
    PageResult<Product> findBooksByCategory(String category,int currentpage,int pageSize);
    /**
     * 根据id查找书籍
     * @param id
     * @return
     */
    Product findBookById(String id);

    /**
     * 模糊查询搜索货物
     * @param bookname
     * @param currentpage
     * @return
     */
    PageResult<Product> findBooksBySearch(String bookname,int currentpage);

    /**
     * 修改书籍信息
     * @param product
     */
    void modifyProduct(Product product);

    /**
     * 根据书籍编号删除商品
     * @param productId
     */
    void deleteProductById(String productId);

    /**
     * 根据搜索条件查找书籍
     * @param id
     * @param category
     * @param name
     * @param minprice
     * @param maxprice
     * @return
     */
    PageResult<Product> findProductByConditions(String id, String category, String name, String minprice, String maxprice);

    /**
     * 添加商品
     * @param product
     */
    void addProduct(Product product);
}
