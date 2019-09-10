package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserServlet {
    boolean regist(User user);
// 激活
    boolean active(String code);
// 登录
    User login(User user);
}
