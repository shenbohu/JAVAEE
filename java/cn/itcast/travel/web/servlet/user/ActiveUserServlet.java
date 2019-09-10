package cn.itcast.travel.web.servlet.user;

import cn.itcast.travel.service.UserServlet;
import cn.itcast.travel.service.impl.UserServletImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // 获取激活码
        String code = request.getParameter("code");
        if(code!=null) {
            UserServlet servlet = new UserServletImpl();
          boolean flag =   servlet.active(code);
          // 3 :判断标记
            String msg = "";
            if(flag) {
                // 激活成功
                msg = "激活成功.请<a href='login.html'>登录</a>";
            } else  {
                // 激活失败
                msg = "激活失败,请联系管理员";
            }
            response.setContentType("text/html;charest=utf-8");
            response.getWriter().write(msg);

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
