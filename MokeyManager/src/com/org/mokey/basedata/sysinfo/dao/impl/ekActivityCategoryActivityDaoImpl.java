package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKActivityCategoryActivityDao;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by vpc on 2016/3/7.
 */
public class ekActivityCategoryActivityDaoImpl implements EKActivityCategoryActivityDao {
    private static final Logger LOGGER = Logger.getLogger(ekActivityCategoryActivityDaoImpl.class);

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //@Override
    /*public int isExist(String cid, String aid) {
        try {
            String sql = "SELECT COUNT(1) FROM T_EK_ACTIVITY_CATEGORY_ACTIVIT " +
                    " WHERE C_CID = ? AND C_AID = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{cid, aid}, Integer.class);
        } catch (Exception e) {
            LOGGER.error("ekActivityCategoryActivityDaoImpl.isExist failed, e : " + e);
        }
        return 0;
    }*/


}
