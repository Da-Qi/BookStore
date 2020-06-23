package web.filter;

import domain.User;
import service.ServiceImpl.UserServiceImpl;
import service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/index.jsp")
public class CookieFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        UserService us = new UserServiceImpl();
        HttpServletRequest request = (HttpServletRequest) req;
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            chain.doFilter(request, resp);
            return;
        }
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("userId")) {
                String value = cookies[i].getValue();
                User user = us.findUserById(value);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                chain.doFilter(request, resp);
                return;
            }
        }

        chain.doFilter(request, resp);

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
