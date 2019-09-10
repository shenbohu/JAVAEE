package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
    public JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    // 根据UID获取图片的集合
    public List<RouteImg> findByid(int rid) {
        String sql = "select * from tab_route_img where rid = ?";
        return  template.query(sql,new BeanPropertyRowMapper<>(RouteImg.class),rid);
    }
}
