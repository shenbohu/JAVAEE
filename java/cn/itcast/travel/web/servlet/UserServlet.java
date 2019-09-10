package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.UserServletImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private          cn.itcast.travel.service.UserServlet servlet = new UserServletImpl();
// 注册
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户输入的验证码
        String check = request.getParameter("check");
        // 获取系统生成的验证码
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            // 验证码错误
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
//            ObjectMapper mapper = new ObjectMapper();
//            String json = mapper.writeValueAsString(info);

           String  json =  writeValueString(info);

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

        //cn.itcast.travel.service.UserServlet servlet = new UserServletImpl();
        boolean flag = servlet.regist(user);

        ResultInfo info = new ResultInfo();
        if (flag) {
            //注册成功
           // System.out.println(user);
            info.setFlag(true);


        } else {
            // 注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }
        // 将info序列化为json
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(info);
        String json = writeValueString(info);
        // 将json 数据协会客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
// 登录
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            info.setFlag(false);
            info.setErrorMsg("验证码输入错误");
            // 响应数据
//            ObjectMapper mapper = new ObjectMapper();
//            response.setContentType("application/json;charset=utf-8");
//            mapper.writeValue(response.getOutputStream(), info);

            writeValue(info,response);

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
      //  cn.itcast.travel.service.UserServlet servlet = new UserServletImpl();
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

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取登录的用户
        User user = (User) request.getSession().getAttribute("user");
        //  System.out.println(user);

//        ObjectMapper mapper = new ObjectMapper();
//        response.setContentType("application/json;charset=utf-8");
//        mapper.writeValue(response.getOutputStream(), user);

        writeValue(user,response);
    }

    protected void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取激活码
        String code = request.getParameter("code");
        if (code != null) {
          //  cn.itcast.travel.service.UserServlet servlet = new UserServletImpl();
            boolean flag = servlet.active(code);
            // 3 :判断标记
            String msg = "";
            if (flag) {
                // 激活成功
                msg = "激活成功.请<a href='login.html'>登录</a>";
            } else {
                // 激活失败
                msg = "激活失败,请联系管理员";
            }
            response.setContentType("text/html;charest=utf-8");
            response.getWriter().write(msg);

        }

    }


}