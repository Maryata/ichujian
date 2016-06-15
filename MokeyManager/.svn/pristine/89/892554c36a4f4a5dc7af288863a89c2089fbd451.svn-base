package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKMallAdvertDao;
import com.org.mokey.common.util.DaoUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 广告位维护
 * Created by vpc on 2016/4/20.
 */
@Repository
public class EKMallAdvertDaoImpl implements EKMallAdvertDao {
    private static final Logger LOGGER = Logger.getLogger(EKMallAdvertDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询  广告位信息列表
     *
     * @param page
     * @return
     */
    @Override
    public Map<String, Object> list(int page) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM T_EK_MALL_ADVERT T LEFT JOIN T_EK_MALL_PRODUCT TEMP ON TEMP.C_ID=T.C_PID");
            List<Object> argsList = new ArrayList<Object>();
            int count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);

            sql.append(" ORDER BY T.C_ORDER DESC");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList).toString().replace("COUNT(1)", "T.C_ID,T.C_PID,T.C_IMG,T.C_NAME,T.C_ORDER,TEMP.C_TITLE");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKMallAdvertDaoImpl.list failed, e : " + e);
        }
        return ret;
    }

    /**
     * 查询商品信息列表
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getList() {
        try {
            String sql = "SELECT C_ID,C_TITLE FROM T_EK_MALL_PRODUCT WHERE C_ISLIVE='1'";
            List<Object> argsList = new ArrayList<Object>();
            return jdbcTemplate.queryForList(String.valueOf(sql), argsList.toArray());
        } catch (Exception e) {
            LOGGER.error("EKMallAdvertDaoImpl.getList failed, e : " + e);
        }
        return null;
    }

    /**
     * 添加 广告位
     *
     * @param id
     * @param name
     * @param logo
     * @param pid
     * @param order
     */
    @Override
    public void addAdvert(String id, String name, String logo, String pid, String order) {
        List<Object> argsList = new ArrayList<Object>();
        argsList.add(id);
        argsList.add(name);
        argsList.add(logo);
        argsList.add(pid);
        argsList.add(order);
        try {
            String sql = "INSERT INTO T_EK_MALL_ADVERT T" +
                    " (T.C_ID ,T.C_NAME ,T.C_IMG ,T.C_PID,T.C_ORDER)" +
                    " VALUES(?, ?, ? ,?,?)";
            this.jdbcTemplate.update(sql, argsList.toArray());
        } catch (Exception e) {
            LOGGER.error("EKMallAdvertDaoImpl.addAdvert failed, e : " + e);
        }

    }

    /**
     * 删除广告
     *
     * @param id
     */
    @Override
    public void toDel(String id) {
        try {
            String sql = "DELETE FROM T_EK_MALL_ADVERT WHERE C_ID = ?";
            this.jdbcTemplate.update(sql, new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKMallAdvertDaoImpl.toDel failed, e : " + e);
        }
    }

    /**
     * 修改广告位
     *
     * @param id
     * @param name
     * @param logo
     * @param pid
     * @param order
     */
    @Override
    public void updateAdvert(String id, String name, String logo, String pid, String order) {
        List<Object> argsList = new ArrayList<Object>();
        argsList.add(name);
        argsList.add(logo);
        argsList.add(pid);
        argsList.add(order);
        argsList.add(id);
        try {
            String sql = "UPDATE T_EK_MALL_ADVERT T SET T.C_NAME=? ,T.C_IMG=? ,T.C_PID=?,T.C_ORDER=?  WHERE T.C_ID = ?";
            this.jdbcTemplate.update(sql, argsList.toArray());
        } catch (Exception e) {
            LOGGER.error("EKMallAdvertDaoImpl.updateTag failed, e : " + e);
        }
    }
}
