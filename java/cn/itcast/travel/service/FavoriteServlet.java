package cn.itcast.travel.service;

public interface FavoriteServlet {
    // 判断用户是否收藏RID
    public  boolean ifFavorite(String rid, int uid);
    // 添加
    void add(String rid, int uid);
}
