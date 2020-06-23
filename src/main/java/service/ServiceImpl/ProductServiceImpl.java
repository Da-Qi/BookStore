package service.ServiceImpl;

import dao.Impl.ProductDaoImpl;
import dao.ProductDao;
import domain.PageResult;
import domain.Product;
import service.ProductService;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {


    ProductDao pd = new ProductDaoImpl();
    public PageResult<Product> findBooksByCategory(String category, int currentpage) {
        //创建模型
        PageResult<Product> pr = new PageResult<Product>();
        //书籍数量
        int totalcount = 0;
        //页面大小
        pr.setPageSize(4);
        //总页数
        int totalPage = 0;
        //设置当前页数
        pr.setCurrentPage(currentpage);

        //如果传递了category参数
        if (category != null && !"".equals(category)){
            //设置总记录数
            totalcount = pd.count(category);
            pr.setTotalCount(totalcount);

            //设置总页数
            totalPage = (int) Math.ceil(totalcount * 1.0 / pr.getPageSize());
            pr.setTotalPage(totalPage);

            List<Product> list = pd.findBooks(category, currentpage, pr.getPageSize());
            pr.setList(list);
        }else{
            //没有传递书类名
            totalcount = pd.count();
            pr.setTotalCount(totalcount);
            //设置总页数
            totalPage = (int) Math.ceil(totalcount * 1.0 / pr.getPageSize());
            pr.setTotalPage(totalPage);
            List<Product> list = pd.findBooks(currentpage, pr.getPageSize());
            pr.setList(list);
        }

        return pr;
    }

    @Override
    public PageResult<Product> findBooksByCategory(String category, int currentpage, int pageSize) {
        //创建模型
        PageResult<Product> pr = new PageResult<Product>();
        //书籍数量
        int totalcount = 0;
        //页面大小
        pr.setPageSize(pageSize);
        //总页数
        int totalPage = 0;
        //设置当前页数
        pr.setCurrentPage(currentpage);

        //如果传递了category参数
        if (category != null && !"".equals(category)){
            //设置总记录数
            totalcount = pd.count(category);
            pr.setTotalCount(totalcount);

            //设置总页数
            totalPage = (int) Math.ceil(totalcount * 1.0 / pr.getPageSize());
            pr.setTotalPage(totalPage);

            List<Product> list = pd.findBooks(category, currentpage, pr.getPageSize());
            pr.setList(list);
        }else{
            //没有传递书类名
            totalcount = pd.count();
            pr.setTotalCount(totalcount);
            //设置总页数
            totalPage = (int) Math.ceil(totalcount * 1.0 / pr.getPageSize());
            pr.setTotalPage(totalPage);
            List<Product> list = pd.findBooks(currentpage, pr.getPageSize());
            pr.setList(list);
        }

        return pr;
    }

    public Product findBookById(String id) {
        Product book = pd.findBookById(id);
        return book;
    }

    @Override
    public PageResult<Product> findBooksBySearch(String bookname, int currentpage) {
        PageResult<Product> pr = new PageResult<Product>();
        //书籍数量
        int totalcount = 0;
        //页面大小
        pr.setPageSize(4);
        //设置当前页数
        pr.setCurrentPage(currentpage);
        if (bookname != null && !"".equals(bookname)){
            totalcount = pd.countByName(bookname);
            pr.setTotalCount(totalcount);
            pr.setTotalPage((int) Math.ceil(totalcount * 1.0 / pr.getPageSize()));
        }
        List<Product> list = pd.findBooksBySearch(bookname, currentpage, pr.getPageSize());
        pr.setList(list);
        return pr;
    }

    @Override
    public void modifyProduct(Product product) {
        pd.updateProduct(product);
    }

    @Override
    public void deleteProductById(String productId) {
        pd.deleteProductById(productId);
    }

    @Override
    public PageResult<Product> findProductByConditions(String id, String category, String name, String minprice, String maxprice) {
        PageResult<Product> pr = new PageResult<Product>();
        List<Product> list = pd.findProductByConditions(id,category,name,minprice,maxprice);
        pr.setList(list);
        return pr;
    }

    @Override
    public void addProduct(Product product) {
        pd.addProduct(product);
    }

}
