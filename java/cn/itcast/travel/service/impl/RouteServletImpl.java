package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteServlet;

import java.util.List;

public class RouteServletImpl implements RouteServlet {
    // 分页

    public RouteDao dao = new RouteDaoImpl();

    private RouteImgDao imgDao = new RouteImgDaoImpl();

    private SellerDao sellerDao = new SellerDaoImpl();

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int currentPage, int pageSize, int cid, String cname) {
//        totalCount;// 总记录数
//            totalPage;// 总页数
//            currentPage;// 当前页码
//            pageSize;// 每页显示的条数
//            List<T> list; // 每页显示的数据集合
        PageBean<Route> pb = new PageBean<Route>();
        // 查询总记录数
        int totalCount = dao.findTotalCount(cid, cname);
        pb.setTotalCount(totalCount);
        // 当前页码
        pb.setCurrentPage(currentPage);
        // 每页现实的条数
        pb.setPageSize(pageSize);
        // 总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        pb.setTotalPage(totalPage);
        // 第几条数据
        int start = (currentPage - 1) * pageSize;
        // 查询记录
        List<Route> list = dao.findByPage(cid, start, pageSize, cname);
        pb.setList(list);
        return pb;
    }

    // 查询跟句UID查询线路的详细信息
    @Override
    public Route findOne(String rid) {
        Route route = dao.findOne(Integer.parseInt(rid));
        // 跟句route 查询图片的集合信息
        List<RouteImg> imgList = imgDao.findByid(route.getRid());
        route.setRouteImgList(imgList);
        // 根据 route表的 sid 查询商家的信息
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);
        // 查询收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }
}


