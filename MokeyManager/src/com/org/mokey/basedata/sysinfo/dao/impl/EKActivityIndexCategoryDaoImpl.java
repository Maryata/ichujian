package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKActivityIndexCategoryDao;
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
 * Created by vpc on 2016/3/3.
 * e键 ：首页分类管理
 */
@Repository
public class EKActivityIndexCategoryDaoImpl implements EKActivityIndexCategoryDao {
    private static final Logger LOGGER = Logger.getLogger(EKActivityIndexCategoryDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询活动分类信息
     *
     * @param page
     * @param title
     * @return
     */
    @Override
    public Map<String, Object> getAllInfo(int page, String title) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM T_EK_ACTIVITY_CATEGORY_INFO T" +
                    " WHERE T.C_ISLIVE = 1 AND T.C_TYPE = '1'" +
                    " AND NOT EXISTS(SELECT TC.C_CID FROM T_EK_ACTIVITY_INDEX_CATEGORY TC WHERE  TC.C_CID = T.C_ID)");
            List<Object> argsList = new ArrayList<Object>();
            if (StrUtils.isNotEmpty(title)) {
                sql.append(" AND T.C_NAME LIKE ?");
                argsList.add("%" + title + "%");
            }
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);
            sql.append(" ORDER BY T.C_ID DESC");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList).toString().replace("COUNT(1)", "T.C_ID,T.C_NAME,T.C_IMG,T.C_TYPE");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKActivityIndexCategoryDaoImpl.getAllInfo failed, e : " + e);
        }
        return ret;
    }

    @Override
    public Map<String, Object> getCurrInfo(int page, String title) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM T_EK_ACTIVITY_INDEX_CATEGORY T LEFT JOIN T_EK_ACTIVITY_CATEGORY_INFO TC ON TC.C_ID = T.C_CID");
            List<Object> argsList = new ArrayList<Object>();
            if (StrUtils.isNotEmpty(title)) {
                sql.append(" WHERE TC.C_NAME LIKE ?");
                argsList.add("%" + title + "%");
            }
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);
            sql.append(" ORDER BY T.C_ORDER");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList).toString().replace("COUNT(1)", "T.C_ID,T.C_CID,T.C_ORDER,TC.C_NAME,TC.C_IMG");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKActivityIndexCategoryDaoImpl.getCurrInfo failed, e : " + e);
        }
        return ret;
    }

    /**
     * 查询 需要添加的是否存在中间表
     *
     * @param sid
     * @return
     */
    @Override
    public int isExitIndexCategory(String sid) {
        try {
            String sql = "SELECT COUNT(1) FROM T_EK_ACTIVITY_INDEX_CATEGORY " + " WHERE C_CID = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{sid}, Integer.class);
        } catch (Exception e) {
            LOGGER.error("EKActivityIndexCategoryDaoImpl.isExitIndexCategory failed, e : " + e);
        }
        return 0;
    }

    /**
     * 添加信息到中间表
     *
     * @param sid
     * @param order
     */
    @Override
    public void addIndexCategory(String sid, String order) {
        try {
            String sql = "INSERT INTO T_EK_ACTIVITY_INDEX_CATEGORY" +
                    " (C_ID, C_CID, C_ORDER) VALUES" +
                    " (SEQ_EK_ACTIVITY_INDEX_CATE.NEXTVAL,?,?)";
            jdbcTemplate.update(sql, new Object[]{sid, order});
        } catch (Exception e) {
            LOGGER.error("EKActivityIndexCategoryDaoImpl.addIndexCategory failed, e : " + e);
        }
    }

    /**
     * 更新需要添加到中间表的信息
     *
     * @param sid
     * @param order
     */
    @Override
    public void updateIndexCategory(String sid, String order) {
        try {
            String sql = "UPDATE T_EK_ACTIVITY_INDEX_CATEGORY SET" + " C_ORDER = ? WHERE C_CID = ?";
            jdbcTemplate.update(sql, new Object[]{order, sid});
        } catch (Exception e) {
            LOGGER.error("EKActivityIndexCategoryDaoImpl.updateIndexCategory failed, e : " + e);
        }
    }

    /**
     * 删除中简表数据
     *
     * @param id
     */
    @Override
    public void removeIndexCategory(String id) {
        try {
            String sql = "DELETE FROM T_EK_ACTIVITY_INDEX_CATEGORY" + " WHERE  C_CID = ?";
            jdbcTemplate.update(sql, new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKActivityIndexCategoryDaoImpl.removeIndexCategory failed, e : " + e);
        }
    }
}
