package web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class MyEncodingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        //1. 设置post请求的中文乱码问题
        HttpServletRequest hsr = (HttpServletRequest) req;
        if (hsr.getMethod().equalsIgnoreCase("post")){
            req.setCharacterEncoding("UTF-8");
        }
            chain.doFilter(req,resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
