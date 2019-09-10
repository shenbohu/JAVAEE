package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    //根据 rid 和 UID 查询收藏的信息
    public Favorite findByRidAndUid(int rid , int uid);
// 根据线路id  查询收藏次数
    int findCountByRid(int rid);
// 添加
    void add(int parseInt, int uid);
}
