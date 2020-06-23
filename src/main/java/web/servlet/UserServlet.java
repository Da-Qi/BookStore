package web.servlet;

import domain.User;
import exception.UserException;
import org.apache.commons.beanutils.BeanUtils;
import service.ServiceImpl.UserServiceImpl;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

/**
 * 抽取登录，注册，退出功能
 */
@WebServlet("/user")
public class UserServlet extends BaseServlet {
    /**
     * 注册
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //校验验证码
        String checkcode = request.getParameter("checkcode");
        String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");
        if (!checkcode.equals(checkcode_session)){
            request.setAttribute("checkcode_err","验证码输入不一致");
            request.getRequestDispatcher("/register.jsp").forward(request,response);
        }else {
            //1.把参数转成Bean
            User user = new User();
            try {
                BeanUtils.populate(user, request.getParameterMap());
                //设置激活码
                user.setActiveCode(UUID.randomUUID().toString());
                user.setRole("普通用户");
                user.setRegistTime(new Date());
                System.out.println(user);
                //2.注册
                UserService us = new UserServiceImpl();
                us.register(user);
                //3. 返回结果
                //3.1 成功进入界面
                request.getRequestDispatcher("/registersuccess.jsp").forward(request, response);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                //3.2 失败，回到注册页面
                request.setAttribute("register_err", "注册失败，该用户名已存在");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        }
    }

    /**
     * 退出
     * @param request
     * @param response
     * @throws IOException
     */
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //清除session
        request.getSession().invalidate();
        //清除Cookie
        Cookie cookie = new Cookie("userId",String.valueOf(-1));
        cookie.setPath("/BookStore/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }

    /**
     * 登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //2. 调用service
        UserService us = new UserServiceImpl();
        try {
            User user = us.login(username, password);
            Cookie cookie = new Cookie("userId",String.valueOf(user.getId()));
            cookie.setMaxAge(3600);
            cookie.setPath("/BookStore/");
            response.addCookie(cookie);
            //把user对象保存到session中
            request.getSession().setAttribute("user",user);
            //权限校验：
            if ("管理员".equals(user.getRole())){
                response.sendRedirect(request.getContextPath()+"/admin/login/home.jsp");
                //request.getRequestDispatcher("/admin/login/home.jsp").forward(request,response);
            }else{
                //登录成功，回到首页index.jsp
                //解决刷新时重复提交数据
                response.sendRedirect(request.getContextPath()+"/index.jsp");

            }
            //request.getRequestDispatcher("/index.jsp").forward(request,response);

        } catch (UserException | IOException e) {
            e.printStackTrace();
            request.setAttribute("login_msg",e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
    }

}
