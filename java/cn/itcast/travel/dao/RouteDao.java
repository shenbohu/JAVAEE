package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    // 查询总记录数  cid

public int findTotalCount(int cid,String cname);

    // 根据cid 查询 页数记录  查看记录的集合

 public List<Route> findByPage(int cid,int  start ,int pageSize,String cname);

 // 跟句rid查询一个的方法
 public  Route findOne(int rid) ;


}
