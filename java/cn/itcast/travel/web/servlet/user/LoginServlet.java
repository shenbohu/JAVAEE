package cn.itcast.travel.web.servlet.user;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserServlet;
import cn.itcast.travel.service.impl.UserServletImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取系统生成的验证码

        ResultInfo info = new ResultInfo();
        // 获取用户输入的验证码
        String check = request.getParameter("check");
////        if(check==null || checkcode_server==null) {
////            return;
////        }
//        if(check==null || checkcode_server==null ||!checkcode_server.equalsIgnoreCase(check)) {
//            info.setFlag(false);
//            info.setErrorMsg("验证码输入错误");
//            return;
//        }

        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)) {
            info.setFlag(false);
            info.setErrorMsg("验证码输入错误");
            // 响应数据
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            mapper.writeValue(response.getOutputStream(), info);

            return;
        }

        //1:获取用户名和密码数据
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserServlet servlet = new UserServletImpl();
        User u = servlet.login(user);

        // System.out.println(u);


        // 判断用户对象是否存在
        if (u == null) {
            // 用户名或者密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名或者密码错误");
        }
        // 判断用户是否激活
        if (u != null && !"Y".equals(u.getStatus())) {
            // 用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("用户尚未激活");
        }
        // 登录成功的判断
        if (u != null && "Y".equals(u.getStatus())) {
            request.getSession().setAttribute("user", u);
            info.setFlag(true);
            info.setErrorMsg("登录成功");

        }
        // 响应数据
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
