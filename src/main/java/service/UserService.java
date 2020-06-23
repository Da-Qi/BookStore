package service;

import domain.User;
import exception.UserException;

public interface UserService {
    /**
     * 注册功能
     * @param user
     */
    void register(User user);

    /**
     * 激活功能
     * @param activeCode
     * @throws UserException
     */
    void activeUser(String activeCode) throws UserException;

    /**
     * 登录功能
     * @param username
     * @param password
     */
    User login(String username,String password) throws UserException;

    /**
     * 更改用户信息
     * @param user
     */
    void modifyUserInfo(User user) throws UserException;

    /**
     * 根据用户id寻找用户
     * @param id
     * @return
     */
    User findUserById(String id);
}
