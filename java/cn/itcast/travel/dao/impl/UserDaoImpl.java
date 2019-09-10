package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    // 查询是否存在用户
    public User findByUsername(String username) {

        try {
            String sql = "select * from tab_user where username=?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
            return user;
        } catch (DataAccessException e) {
            return null;
        }

    }

    //保存
    @Override
    public void save(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code)" +
                " values(?,?,?,?,?,?,?,?,?)";
        template.update(sql, user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
    }

    // 根据计划吗查询用户是否存在
    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code=?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {

        }
        return user;
    }

    // 更改用户的激活状态
    @Override
    public void updateStatus(User user) {
        String sql = "update tab_user set status='Y' where  uid=?";
        template.update(sql, user.getUid());
    }
//登录
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        try {
            String sql = "select * from tab_user where username=? and password=?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username,password);
            return user;
        } catch (DataAccessException e) {
            return null;
        }

    }

}
