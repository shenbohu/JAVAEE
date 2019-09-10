package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteServlet;
import cn.itcast.travel.service.RouteServlet;
import cn.itcast.travel.service.impl.FavoriteServletImpl;
import cn.itcast.travel.service.impl.RouteServletImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlets extends BaseServlet {
    private RouteServlet servlet = new RouteServletImpl();
    private FavoriteServlet favoriteServlet = new FavoriteServletImpl();

    // 分页显示参数
    // 模糊查询
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获当前的页数 每页显示的条数 接受线路的cid
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");
        String _cid = request.getParameter("cid");
        // 获取模糊查询 cname
        String cname = request.getParameter("cname");
        // tomcat 7 乱码
        cname = new String(cname.getBytes("iso-8859-1"), "utf-8");

        System.out.println(cname);


        int currentPage = 0; // 当前的页数
        if (_currentPage != null && _currentPage.length() > 0) {
            currentPage = Integer.parseInt(_currentPage);
        } else {
            currentPage = 1;
        }

        int pageSize = 0; // 每页显示的条数
        if (_pageSize != null && _pageSize.length() > 0) {
            pageSize = Integer.parseInt(_pageSize);
        } else {
            pageSize = 5;
        }

        int cid = 0;
        if (_cid != null && _cid.length() != 0 && !"null".equals(_cid)) {
            cid = Integer.parseInt(_cid);
        }
        // 调用servlet 查询PageBean

//      System.out.println(cid);
//     System.out.println(pageSize);
//       System.out.println(currentPage);
        PageBean<Route> pb = servlet.pageQuery(currentPage, pageSize, cid, cname);

        // 将对象返回
        writeValue(pb, response);

    }

    // 根据id查询一个旅游线路的详细信息
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        System.out.println(rid);
        Route route = servlet.findOne(rid);
        System.out.println(route);
        writeValue(route, response);

    }

    // 判断当前用户是否收藏过改线路
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("user");
        int uid ;
        if(user==null) {
           uid = 0;
        } else {
            uid = user.getUid();
        }

        // 调用FavoriteServlet 查询是否收藏
        boolean b = favoriteServlet.ifFavorite(rid, uid);
        writeValue(b,response);

    }
// 添加收藏
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // huo去rid
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid ;
        if(user==null) {
            return;
        } else {
            uid = user.getUid();
        }
        // 调用 servlet 添加
        favoriteServlet.add(rid,uid);

    }
}
