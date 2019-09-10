package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    // 查询是否存在用户
    public User findByUsername(String username);

    // 保存
    public void save(User user);

    //查询用户是否存在
    User findByCode(String code);

    // 更改激活码状态
    void updateStatus(User user);

    //登录
    User findByUsernameAndPassword(String username, String password);
}
