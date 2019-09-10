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

@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // 获取用户输入的验证码
        String check = request.getParameter("check");
        // 获取系统生成的验证码
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        if(checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)) {
            // 验证码错误
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }


        // 1:获取数据
        Map<String, String[]> map = request.getParameterMap();
        // 2 :封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // 3:调用 servlet 完成注册

       // System.out.println(user);

        UserServlet servlet = new UserServletImpl();
        boolean flag = servlet.regist(user);

        ResultInfo info = new ResultInfo();
        if (flag) {
            //注册成功
            System.out.println(user);
            info.setFlag(true);


        } else {
            // 注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }
        // 将info序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        // 将json 数据协会客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
