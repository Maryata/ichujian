package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKIndexModelDao;
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
 */
@Repository
public class EKIndexModelDaoImpl implements EKIndexModelDao{
    private static final Logger LOGGER = Logger.getLogger(EKIndexModelDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * e键 ： 首页  ： 查询所有app应用
     * @param page
     * @param name
     * @return
     */
    @Override
    public Map<String, Object> getAllApp(int page, String name) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1) " +
                    " FROM T_EK_INDEX_APP_INFO T" +
                    " WHERE T.C_ISLIVE = 1 AND T.C_TYPE = 0 AND NOT EXISTS(SELECT C.C_AID FROM T_EK_INDEX_MODEL C WHERE T.C_ID=C.C_AID)");
            List<Object> argsList = new ArrayList<Object>();
            if (StrUtils.isNotEmpty(name)) {
                sql.append(" AND T.C_NAME LIKE ?");
                argsList.add("%" + name + "%");
            }
            // 查询总数
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

            sql.append(" ORDER BY T.C_CTIME DESC ");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList)
                    .toString().replace("COUNT(1)", "T.C_ID ,T.C_NAME");
            // 查询数据
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKIndexModelDaoImpl.getAllGame failed, e : " + e);
        }
        return ret;
    }

    /**
     * e键 ： 首页  ： 查询当前app应用
     * @param page
     * @param name
     * @return
     */
    @Override
    public Map<String, Object> getCurrApp(int page, String name) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
                    " FROM T_EK_INDEX_APP_INFO T ,T_EK_INDEX_MODEL C" +
                    " WHERE T.C_ISLIVE=1 AND T.C_ID =C_AID");
            List<Object> argsList = new ArrayList<Object>();
            if (StrUtils.isNotEmpty(name)) {
                sql.append(" AND T.C_NAME LIKE ?");
                argsList.add("%" + name + "%");
            }
            // 查询总数
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

            sql.append(" ORDER BY C.C_ORDER");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList)
                    .toString().replace("COUNT(1)", "C.C_AID,C.C_ORDER,C.C_EDITABLE,T.C_NAME");
            // 查询数据
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKIndexModelDaoImpl.getCurrApp failed, e : " + e);
        }
        return ret;
    }

    /**
     * e键 ： 首页  ： 查询当前app应用 是否存在中间表
     * @param aid
     * @return
     */
    @Override
    public int isExist(String aid) {
        try {
            String sql = "SELECT COUNT(1) FROM T_EK_INDEX_MODEL " +
                    " WHERE C_AID = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{aid}, Integer.class);
        } catch (Exception e) {
            LOGGER.error("EKIndexModelDaoImpl.isExist failed, e : " + e);
        }
        return 0;
    }
    /**
     * e键 ： 首页  ：添加
     * @param aid
     * @return
     */
    @Override
    public void add(String aid, String order, String isEdit) {
        try {
            String sql = "INSERT INTO T_EK_INDEX_MODEL" +
                    " (C_ID, C_AID,C_ORDER,C_EDITABLE) VALUES" +
                    " (SEQ_EK_INDEX_MODEL.NEXTVAL,?,?,?)";
            jdbcTemplate.update(sql, new Object[]{aid, order,isEdit});
        } catch (Exception e) {
            LOGGER.error("EKIndexModelDaoImpl.add failed, e : " + e);
        }
    }
    /**
     * e键 ： 首页  ：更新
     * @param aid
     * @return
     */
    @Override
    public void update(String aid, String order, String isEdit) {
        try {
            String sql = "UPDATE T_EK_INDEX_MODEL SET" +
                    " C_ORDER = ?,C_EDITABLE = ? WHERE C_AID = ?";
            jdbcTemplate.update(sql, new Object[]{order,isEdit,aid});
        } catch (Exception e) {
            LOGGER.error("EKIndexModelDaoImpl.update failed, e : " + e);
        }
    }

    /**
     * 移除
     * @param id
     */
    @Override
    public void remove(String id) {
        try {
            String sql = "DELETE FROM T_EK_INDEX_MODEL" +
                    " WHERE C_AID = ?";
            jdbcTemplate.update(sql, new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKIndexModelDaoImpl.remove failed, e : " + e);
        }
    }
}
