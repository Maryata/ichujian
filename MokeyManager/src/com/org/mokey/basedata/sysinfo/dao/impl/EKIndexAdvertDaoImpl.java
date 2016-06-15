package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKIndexAdvertDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/14.
 * e键 ： 首页  ： 广告位
 */
@Repository
public class EKIndexAdvertDaoImpl implements EKIndexAdvertDao {
    private static final Logger LOGGER = Logger.getLogger(EKIndexAdvertDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * e键 ： 首页  ： 广告位  ：列表
     *
     * @param page
     * @return
     */
    @Override
    public Map<String, Object> ekIndexAdvertList(int page) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1)  FROM T_EK_INDEX_ADVERT T,T_EK_INDEX_APP_INFO TC WHERE T.C_AID = TC.C_ID AND TC.C_ISLIVE=1");
            List<Object> argsList = new ArrayList<Object>();
            int count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);

            sql.append(" ORDER BY T.C_ID DESC");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList).toString().replace("COUNT(1)", "T.C_ID ,T.C_AID ,T.C_IMG ,T.C_NAME,T.C_ORDER,T.C_TYPE,\n" +
                    "CASE WHEN T.C_TYPE = '1' THEN '顶部广告' WHEN T.C_TYPE = '2' THEN '中部广告' ELSE '' END AS TYPE");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKIndexAdvertDaoImpl.ekIndexAdvertList failed, e : " + e);
        }
        return ret;
    }

    /**
     * e键 ： 首页  ： 广告位 ： 添加
     *
     * @param argsList
     */
    @Override
    public void addAdvert(List<Object> argsList) {
        try {
            String sql = "INSERT INTO T_EK_INDEX_ADVERT T" +
                    " (T.C_ID ,T.C_NAME ,T.C_IMG ,T.C_AID,T.C_ORDER,T.C_TYPE)" +
                    " VALUES(?, ?, ?, ? ,?,?)";
            this.jdbcTemplate.update(sql, argsList.toArray());
        } catch (Exception e) {
            LOGGER.error("EKIndexAdvertDaoImpl.addAdvert failed, e : " + e);
        }

    }

    /**
     * e键 ： 首页  ： 广告位  ： 删除
     *
     * @param id
     */
    @Override
    public void toDel(String id) {
        try {
            String sql = "DELETE FROM T_EK_INDEX_ADVERT WHERE C_ID = ?";
            this.jdbcTemplate.update(sql, new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKIndexAdvertDaoImpl.toDel failed, e : " + e);
        }
    }

    /**
     * e键 ： 首页  ： 广告位   ： 更新
     *
     * @param argsList
     */
    @Override
    public void updateAdvert(List<Object> argsList) {
        try {
            String sql = "UPDATE T_EK_INDEX_ADVERT T SET T.C_NAME=? ,T.C_IMG=? ,T.C_AID=?,T.C_ORDER=?,T.C_TYPE=?" + " WHERE T.C_ID = ?";
            this.jdbcTemplate.update(sql, argsList.toArray());
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryTagDaoImpl.updateTag failed, e : " + e);
        }
    }

    /**
     * e键 ： 首页  ： 广告位   ： app应用
     *
     * @param
     */
    @Override
    public List<Map<String, Object>> getList(String name) {
        try {
            String sql = "SELECT C_ID,C_NAME,C_IMG  FROM T_EK_INDEX_APP_INFO WHERE C_ISLIVE = 1";
            List<Object> argsList = new ArrayList<Object>();
            if (StrUtils.isNotEmpty(name)) {
                sql += (" AND C_NAME LIKE ?");
                argsList.add("%" + name + "%");
            }
            return jdbcTemplate.queryForList(String.valueOf(sql), argsList.toArray());
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.activityCategoryList failed, e : " + e);
        }
        return null;
    }
}
