package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteServlet {

// 分页
    PageBean<Route> pageQuery(int currentPage, int pageSize, int cid ,String cname);
// 根据 rid查询
   public Route findOne(String rid);

}
