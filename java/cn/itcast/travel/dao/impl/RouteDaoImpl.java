package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
// 查询总记录数
public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotalCount(int cid,String cname) {
        String sql = "select count(*) from tab_route where 1=1 ";
        List list = new ArrayList(); // 条件们
        StringBuilder sb = new StringBuilder(sql);

        if(cid!=0) {
           sb.append(" and cid = ? ");
           list.add(cid);

        }
        if(cname!=null && cname.length()>0) {
            sb.append(" and rname like ?");
            list.add("%"+cname+"%");
        }
        sql = sb.toString();

        return template.queryForObject(sql, Integer.class,list.toArray());
    }


 // 查询要现实的记录   根据 导航条的线路 如国内游
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String cname) {
       //String sql = "select * from tab_route where cid = ? limit ? , ? ";
        String sql = "select *  from tab_route where 1=1 ";
        List list = new ArrayList(); // 条件们
        StringBuilder sb = new StringBuilder(sql);

        if(cid!=0) {
            sb.append(" and cid = ? ");
            list.add(cid);

        }
        if(!"null".equals(cname) && cname!=null && cname.length()>0) {
            sb.append(" and rname like ?");
            list.add("%"+cname+"%");
        }
        sb.append(" limit ?, ? ");
        list.add(start);
        list.add(pageSize);
        sql = sb.toString();
        return  template.query(sql, new BeanPropertyRowMapper<>(Route.class),list.toArray());
    }
// 根据rid查询一个的信息
    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route  where rid=?";

        return  template.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid);
    }
}
