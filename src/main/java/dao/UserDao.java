package dao;

import domain.User;


public interface UserDao {
    /**
     * 添加一个用户
     * @param user
     */
    void addUser(User user);

    /**
     * 通过激活码找到用户
     * @param activeCode
     * @return
     */
    User findUserByActiveCode(String activeCode);

    /**
     * 通过激活码更新用户状态
     */
    void updateActiveState(String activeCode);

    /**
     * 通过用户名和密码查找用户
     * @param username
     * @param password
     * @return
     */
    User findUserByUsernameAndPassword(String username , String password);

    /**
     * 更新用户信息
     * @param user
     */
    void updateUser(User user);

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    User findUserById(String id);
}
