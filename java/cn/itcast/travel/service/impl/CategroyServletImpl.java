package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategroyDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategroyServlet;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategroyServletImpl implements CategroyServlet {
    private CategoryDao dao = new CategroyDaoImpl();

    // 查询所有
    @Override
    public List<Category> findAll() {
        // 从缓存中查询
        Jedis jedis = JedisUtil.getJedis();
        //Set<String> categorys = jedis.zrange("category", 0, -1);
        // 查询分数  和值
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        List<Category> cs = null;
        if(categorys==null ||categorys.size()==0 ) {
            System.out.println("数据库");
            // 缓存没有数据
             cs = dao.findAll();
            // 存入缓存
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }

        } else {
            System.out.println("缓存");
           cs = new ArrayList<>();

            for (Tuple s: categorys) {
                Category category = new Category();
               category.setCname(s.getElement());
               category.setCid((int) s.getScore());
               cs.add(category);
            }

        }

        return cs;
    }
}
