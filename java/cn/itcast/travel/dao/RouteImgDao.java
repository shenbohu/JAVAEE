package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

// 图片
public interface RouteImgDao {
// 根据rid 获取图片信息的集合
    public List<RouteImg> findByid(int  rid);

}
