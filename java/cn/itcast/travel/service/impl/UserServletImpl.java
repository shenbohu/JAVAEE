package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserServlet;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServletImpl implements UserServlet {
    private UserDao dao = new UserDaoImpl();

    @Override
    // 注册用户
    public boolean regist(User user) {
        User u = dao.findByUsername(user.getUsername());
        if (u != null) {
            // 存在
            return false;
        }
        // 2 :保存用户信息
        //2.1设置唯一的激活码
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");
        dao.save(user);
        //3 激活邮件 邮件正文
        String contert = "<a href='http://localhost/travel/user/active?code=" + user.getCode() + "'>点击激活[黑马程序员]我是你爸爸的爸爸</a>";
        MailUtils.sendMail(user.getEmail(), contert, "博虎爸爸的激活码");
        return true;
    }

    // 激活
    @Override
    public boolean active(String code) {
        // 1:根据激活码查看用户是否存在
        User user = dao.findByCode(code);
        // 2:调用dao更改激活码
        if (user != null) {
            dao.updateStatus(user);
            return true;
        } else {
            return false;
        }

    }

    //登录
    @Override
    public User login(User user) {
        return   dao.findByUsernameAndPassword(user.getUsername(),user.getPassword());

    }
}
