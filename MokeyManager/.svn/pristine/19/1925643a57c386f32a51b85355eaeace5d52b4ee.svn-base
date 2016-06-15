package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKHeadLineDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/3.
 * e键 ：活动头条
 */
public class EKHeadLineDaoImpl implements EKHeadLineDao{

    private static final Logger LOGGER = Logger .getLogger(EKHeadLineDaoImpl.class);
    private JdbcTemplate jdbcTemplate;
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //@Override
    public Map<String, Object> ekHeadLineList(int page) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            if(StrUtils.isNotEmpty(page)) {
                StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM T_EK_ACTIVITY_HEADLINE T");
                List<Object> args = new ArrayList<Object>();
                int count = jdbcTemplate.queryForObject(sql.toString(),args.toArray(), Integer.class);
                // 查询
                sql.append(" ORDER BY T.C_ORDER ");
                String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, args).toString().replace("COUNT(1)", "T.C_ID,T.C_EID,T.C_IMG,T.C_NAME,T.C_TYPE,T.C_ORDER,CASE WHEN T.C_TYPE = '1' THEN '活动' ELSE '活动分类' END AS NAME");
                List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, args.toArray());
                ret.put("count", count);
                ret.put("list", list);
            }
        } catch (Exception e) {
            LOGGER.error("EKHeadLineDaoImpl.ekHeadLineList failed, e : " + e);
        }
        return ret;
    }



    @Override
    public void addHeadLine(String id, String name, String order, String type, String logo) {
        try {
            String sql = "INSERT INTO T_EK_ACTIVITY_HEADLINE T" +
                    " (T.C_ID,T.C_EID,T.C_IMG,T.C_NAME,T.C_TYPE,T.C_ORDER)"+
                    " VALUES(?, ? , ? , ? , ? , ?)";
            List<Object> args = new ArrayList<Object>();
            args.add(id);
            args.add(type);
            args.add(logo);
            args.add(name);
            args.add(type);
            args.add(order);
            this.jdbcTemplate.update(sql,args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKHeadLineDaoImpl.addHeadLine failed, e : " + e);
        }
    }

    @Override
    public void toDel(String id) {
        try {
            String sql = "DELETE FROM T_EK_ACTIVITY_HEADLINE WHERE C_ID = ?";
            this.jdbcTemplate.update(sql,new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKHeadLineDaoImpl.toDel failed, e : " + e);
        }
    }

    @Override
    public void updateHeadLine(String id, String name, String order, String type, String logo) {
        try {
            String sql = "UPDATE T_EK_ACTIVITY_HEADLINE T SET T.C_EID=?,T.C_IMG=?,T.C_NAME=?,T.C_TYPE=?,T.C_ORDER=?" +
                         " WHERE T.C_ID = ?";
            List<Object> args = new ArrayList<Object>();
            args.add(type);
            args.add(logo);
            args.add(name);
            args.add(type);
            args.add(order);
            args.add(id);
            this.jdbcTemplate.update(sql,args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKHeadLineDaoImpl.updateHeadLine failed, e : " + e);
        }
    }



}
