package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKActivityHeadLineDao;
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
 * e键 ：活动头条
 */
@Repository
public class EKActivityHeadLineDaoImpl implements EKActivityHeadLineDao {
    private static final Logger LOGGER = Logger.getLogger(EKActivityHeadLineDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 活动头条   查询活动分类   所有信息
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
                    " AND NOT EXISTS(SELECT TC.C_EID FROM T_EK_ACTIVITY_HEADLINE TC WHERE  TC.C_EID = T.C_ID AND TC.C_TYPE='0')");
            List<Object> argsList = new ArrayList<Object>();
            if (StrUtils.isNotEmpty(title)) {
                sql.append(" AND T.C_NAME LIKE ?");
                argsList.add("%" + title + "%");
            }
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

            sql.append(" ORDER BY T.C_ID DESC");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList).toString().replace("COUNT(1)", "T.C_ID,T.C_NAME,T.C_IMG_HEADlINE AS C_IMG ,T.C_TYPE");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKActivityHeadLineDaoImpl.getAllInfo failed, e : " + e);
        }
        return ret;
    }

    /**
     * 活动头条   查询活动头条中间表   所有活动分类信息
     *
     * @param page
     * @param title
     * @return
     */
    @Override
    public Map<String, Object> getCurrInfo(int page, String title, String type) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM  T_EK_ACTIVITY_HEADLINE T WHERE T.C_TYPE=?");
            List<Object> argsList = new ArrayList<Object>();
            argsList.add(type);
            if (StrUtils.isNotEmpty(title)) {
                sql.append(" AND T.C_NAME LIKE ?");
                argsList.add("%" + title + "%");
            }
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);
            sql.append(" ORDER BY T.C_ORDER");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList).toString().replace("COUNT(1)", "T.C_ID,T.C_EID,T.C_ORDER,T.C_NAME,T.C_IMG");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKActivityHeadLineDaoImpl.getCurrInfo failed, e : " + e);
        }
        return ret;
    }

    /**
     * 活动头条   查询活动   所有信息
     *
     * @param page
     * @param title
     * @return
     */
    @Override
    public Map<String, Object> getAllActivityInfo(int page, String title) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
                    " FROM T_EK_ACTIVITY_INFO T WHERE T.C_ISLIVE = 1" +
                    " AND NOT EXISTS(SELECT TC.C_EID FROM T_EK_ACTIVITY_HEADLINE TC WHERE  TC.C_EID = T.C_ID AND TC.C_TYPE='1')");
            List<Object> argsList = new ArrayList<Object>();
            if (StrUtils.isNotEmpty(title)) {
                sql.append(" AND T.C_NAME LIKE ?");
                argsList.add("%" + title + "%");
            }
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

            sql.append(" ORDER BY T.C_ID DESC");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList).toString().replace("COUNT(1)", "T.C_ID,T.C_TITLE,REGEXP_SUBSTR(T.C_IMG,'[^,]+',1,1,'i') AS C_IMG");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKActivityHeadLineDaoImpl.getAllActivityInfo failed, e : " + e);
        }
        return ret;
    }

    /**
     * 查询  ：  数据是否存在活动头条中间表
     *
     * @param sid
     * @return
     */
    @Override
    public int isExitHeadLine(String sid, String type) {
        try {
            String sql = "SELECT COUNT(1) FROM T_EK_ACTIVITY_HEADLINE  WHERE C_EID = ? AND C_TYPE=?";
            return jdbcTemplate.queryForObject(sql, new Object[]{sid, type}, Integer.class);
        } catch (Exception e) {
            LOGGER.error("EKActivityHeadLineDaoImpl.isExitHeadLine failed, e : " + e);
        }
        return 0;
    }

    /**
     * 添加  ：  向活动头条中间表添加
     *
     * @param sid
     * @param order
     * @param name
     * @param img
     */
    @Override
    public void addHeadLine(String sid, String order, String name, String img, String type) {
        try {
            String sql = "INSERT INTO T_EK_ACTIVITY_HEADLINE(C_ID, C_EID, C_TYPE ,C_IMG,C_NAME ,C_ORDER) VALUES" + " (SEQ_EK_ACTIVITY_HEADLINE.NEXTVAL,?,?,?,?,?)";
            jdbcTemplate.update(sql, new Object[]{sid, type, img, name, order});
        } catch (Exception e) {
            LOGGER.error("EKActivityHeadLineDaoImpl.addHeadLine failed, e : " + e);
        }

    }

    /**
     * 修改  ：  活动头条中间表
     *
     * @param sid
     * @param order
     * @param name
     * @param img
     */
    @Override
    public void updateHeadLine(String sid, String order, String name, String img, String type) {
        try {
            String sql = "UPDATE T_EK_ACTIVITY_HEADLINE SET C_NAME=?,C_IMG=?, C_ORDER = ? WHERE C_EID = ? AND C_TYPE=?";
            jdbcTemplate.update(sql, new Object[]{name, img, order, sid, type});
        } catch (Exception e) {
            LOGGER.error("EKActivityHeadLineDaoImpl.updateHeadLine failed, e : " + e);
        }
    }

    /**
     * 删除活动头条中间表
     *
     * @param id
     */
    @Override
    public void removeHeadLine(String id, String type) {
        try {
            String sql = "DELETE FROM T_EK_ACTIVITY_HEADLINE  WHERE  C_EID = ? AND C_TYPE=?";
            jdbcTemplate.update(sql, new Object[]{id, type});
        } catch (Exception e) {
            LOGGER.error("EKActivityHeadLineDaoImpl.removeHeadLine failed, e : " + e);
        }
    }
}
