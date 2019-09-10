package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteServlet;

public class FavoriteServletImpl implements FavoriteServlet {
  private   FavoriteDao favoriteDao = new FavoriteDaoImpl();


    @Override
    public boolean ifFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        return favorite!=null; //  返回true  有值
    }
// 添加
    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);

    }
}
