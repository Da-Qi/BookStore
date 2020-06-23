package dao.Impl;

import dao.UserDao;
import domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    public void addUser(User user){
        //sql语句
        String sql = "insert into user ";
        sql += "(username,password,gender,email,telephone,introduce,activeCode,state,role,registTime) ";
        sql += "values(?,?,?,?,?,?,?,?,?,?) ";

        //执行sql
        template.update(sql,
                user.getUsername(),user.getPassword(),user.getGender(),user.getEmail(),user.getTelephone(),
                user.getIntroduce(),user.getActiveCode(),user.getState(),user.getRole(),user.getRegistTime());
    }

    /**
     *
     * 通过激活码找到用户
     * @param activeCode
     * @return
     */
    public User findUserByActiveCode(String activeCode) {
        //sql
        User user = null;
        try {
            String sql = "select * from user where activeCode = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), activeCode);
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 更新用户状态
     * @param activeCode
     */
    public void updateActiveState(String activeCode) {
        String sql = "update user set state= 1 where activeCode = ?";
        template.update(sql,activeCode);
    }

    /**
     * 登录具体实现
     * @param username
     * @param password
     * @return
     */
    public User findUserByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            String sql = "select * from user where username = ? and password = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateUser(User user) {
        String sql = "update user set password= ? ,gender = ?,telephone=? where id = ?";
        template.update(sql,user.getPassword(),user.getGender(),user.getTelephone(),user.getId());

    }

    @Override
    public User findUserById(String id) {
        String sql = "select * from user where id = ?";
        User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
        return user;
    }


}
