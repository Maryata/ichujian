package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKActivityNoticeDao;
import com.org.mokey.common.util.DaoUtil;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/3.
 * e键 ：活动注意事项
 */
public class EKActivityNoticeDaoImpl implements EKActivityNoticeDao{

    private static final Logger LOGGER = Logger .getLogger(EKActivityNoticeDaoImpl.class);
    private JdbcTemplate jdbcTemplate;
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<String, Object> ekActivityNoticeList(int page) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1)" + " FROM T_EK_ACTIVITY_NOTICE_BASE WHERE C_ISLIVE = 1 ");
            List<Object> argsList = new ArrayList<Object>();
            int count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);

            sql.append(" ORDER BY C_ID ");
            String sql1 = DaoUtil.addfy_oracle( sql, (page-1)*5, 5, argsList )
                    .toString().replace( "COUNT(1)", "C_ID,C_NAME,C_IMG" );
            List<Map<String,Object>> list = jdbcTemplate.queryForList( sql1, argsList.toArray() );
            ret.put( "count", count );
            ret.put( "list", list );
        } catch (Exception e) {
            LOGGER.error("EKActivityNoticeDaoImpl.ekActivityNoticeList failed, e : " + e);
        }
        return ret;
    }

    @Override
    public void addTag(String id, String name, String logo) {
        try {
            String sql = "INSERT INTO T_EK_ACTIVITY_NOTICE_BASE" +
                    " (C_ID,C_NAME,C_ISLIVE,C_IMG)" +
                    " VALUES(?, ?, '1', ?)";
            this.jdbcTemplate.update(sql,new Object[]{id,name,logo});
        } catch (Exception e) {
            LOGGER.error("EKActivityNoticeDaoImpl.addGameGiftCate failed, e : " + e);
        }
    }

    @Override
    public void toDel(String id) {
        try {
            String sql = "DELETE FROM T_EK_ACTIVITY_NOTICE_BASE WHERE C_ID = ?";
            this.jdbcTemplate.update(sql,new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKActivityNoticeDaoImpl.toDel failed, e : " + e);
        }
    }

    @Override
    public void updateTag(String id, String name, String logo) {
        try {
            String sql = "UPDATE T_EK_ACTIVITY_NOTICE_BASE SET C_NAME = ?, C_IMG = ?" +
                    " WHERE C_ID = ?";
            this.jdbcTemplate.update(sql,new Object[]{name,logo,id});
        } catch (Exception e) {
            LOGGER.error("EKActivityNoticeDaoImpl.updateTag failed, e : " + e);
        }
    }

    /**
     * 查询  ：  注意事项列表
     * @return
     */
    @Override
    public List<Map<String, Object>> noticeList() {
        String sql = "SELECT C_ID,C_NAME,C_IMG FROM T_EK_ACTIVITY_NOTICE_BASE WHERE C_ISLIVE = 1";
        return jdbcTemplate.queryForList( sql);
    }


}
