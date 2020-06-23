package service.ServiceImpl;

import dao.UserDao;
import dao.Impl.UserDaoImpl;
import domain.User;
import exception.UserException;
import service.UserService;
import utils.SendJMail;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    /**
     * 注册用户
     * @param user
     */
    public void register(User user) {
        //往数据库添加用户
        userDao.addUser(user);
        String link = "http://localhost:8080/BookStore/active?activeCode=" + user.getActiveCode();
        String html = "<a href=\"" + link + "\">欢迎"+user.getUsername()+"注册网上书城账号，请点击激活</a>";
        //发送激活邮件
        SendJMail.sendMail(user.getEmail(), html);
    }

    /**
     * 激活用户
     * @param activeCode
     */
    public void activeUser(String activeCode) throws UserException {
        //1. 查询激活码的用户是否存在
        User user = userDao.findUserByActiveCode(activeCode);
        if (user == null){
            throw new UserException("该用户不存在，无法激活");
        }else if (user != null && user.getState() == 1){
            throw new UserException("该用户已经是激活状态");
        }
        //2. 激活用户
        userDao.updateActiveState(activeCode);
    }

    /**
     * 登录功能
     * @param username
     * @param password
     */
    public User login(String username, String password) throws UserException {
            //1. 查询
            User user = userDao.findUserByUsernameAndPassword(username, password);
            //2. 判断
            if (user == null){
                throw new UserException("用户名或密码不正确");
            }else if (user.getState() == 0){
                throw new UserException("用户未激活，请先登录邮箱激活");
            }
            return user;

    }

    public void modifyUserInfo(User user) throws UserException {
        userDao.updateUser(user);
    }

    @Override
    public User findUserById(String id) {
        try {
            return userDao.findUserById(id);
        } catch (Exception e) {
            return null;
        }
    }

}
