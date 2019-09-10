package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    // 根据sid 获取商家的信息
        public Seller findById(int sid) {
        String sql = "select * from  tab_seller where sid = ?";
        return  template.queryForObject(sql, new BeanPropertyRowMapper<>(Seller.class),sid);

    }
}
