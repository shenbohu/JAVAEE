package cn.itcast.travel.service;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategroyDaoImpl;
import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategroyServlet {
    // 查询所有
    public List<Category> findAll();
}
