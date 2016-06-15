package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKIndexSuppIndexAppDao;
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
 * Created by vpc on 2016/3/22.
 */
@Repository
public class EKIndexSuppIndexAppDaoImpl implements EKIndexSuppIndexAppDao {
    private static final Logger LOGGER = Logger.getLogger(EKIndexSuppIndexAppDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * ApP 定制管理  :  list
     *
     * @return
     */
    @Override
    public Map<String, Object> list(int page, String code, String aid) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
                    " FROM T_EK_INDEX_SUPPLIER_INDEXAPP T" +
                    " LEFT JOIN T_BASE_SUPPLIER TBS ON TBS.C_SUPPLIER_CODE = T.C_CODE AND TBS.C_ISLIVE=1" +
                    " LEFT JOIN  T_EK_INDEX_APP_INFO TAI ON TAI.C_ID = T.C_INDEX_AID AND TAI.C_ISLIVE=1" +
                    " WHERE T.C_ISLIVE = 1");
            List<Object> argsList = new ArrayList<Object>();
            if (StrUtils.isNotEmpty(code)) {
                sql.append(" AND T.C_CODE = ?");
                argsList.add(code);
            }
            if (StrUtils.isNotEmpty(aid)) {
                sql.append(" AND T.C_INDEX_AID = ?");
                argsList.add(aid);
            }
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class); // 查询总数
            sql.append(" ORDER BY T.C_ORDER DESC ");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList).toString().replace("COUNT(1)", "T.C_ID,T.C_CODE,T.C_INDEX_AID,T.C_ORDER,TBS.C_SUP_NAME,TAI.C_NAME");
            // 查询数据
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKIndexSuppIndexAppDaoImpl.list failed, e : " + e);
        }
        return ret;
    }

    /**
     * 定制APP :  供应商查询
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getSuppList() {
        String sql = "SELECT C_SUPPLIER_CODE,C_SUPPLIER_NAME,C_SUP_NAME FROM T_BASE_SUPPLIER T WHERE T.C_ISLIVE=1";
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * 定制APP ： app应用查询
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getAppLsit() {
        String sql = "SELECT C_ID,C_NAME FROM T_EK_INDEX_APP_INFO WHERE C_ISLIVE = 1 AND C_TYPE = 6";
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * ApP 定制管理  :  添加list
     *
     * @return
     */
    @Override
    public Map<String, Object> addList(int page, String code) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM T_BASE_SUPPLIER T WHERE T.C_ISLIVE=1" + " AND NOT EXISTS(SELECT TC.C_CODE FROM T_EK_INDEX_SUPPLIER_INDEXAPP TC WHERE T.C_SUPPLIER_CODE=TC.C_CODE AND TC.C_ISLIVE = 1)");
            List<Object> argsList = new ArrayList<Object>();
            if (StrUtils.isNotEmpty(code)) {
                sql.append(" AND T.C_SUPPLIER_CODE = ?");
                argsList.add(code);
            }
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class); // 查询总数
            sql.append(" ORDER BY T.C_SUPPLIER_CODE");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList).toString().replace("COUNT(1)", "T.C_SUPPLIER_CODE,T.C_SUP_NAME");
            // 查询数据
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKIndexSuppIndexAppDaoImpl.addList failed, e : " + e);
        }
        return ret;
    }

    /**
     * ApP 定制管理  :  添加
     *
     * @param code
     * @param aid
     * @param order
     */
    @Override
    public void add(String code, String aid, String order) {
        try {
            String sql = "INSERT INTO T_EK_INDEX_SUPPLIER_INDEXAPP" +
                    " (C_ID, C_CODE, C_INDEX_AID, C_ORDER,C_ISLIVE) VALUES" +
                    " (SEQ_EK_INDEX_SUPPLIER_INDEXAPP.NEXTVAL,?,?,?,?)";
            List<Object> argsList = new ArrayList<Object>();
            argsList.add(code);
            argsList.add(aid);
            argsList.add(order);
            argsList.add(1);
            jdbcTemplate.update(sql, argsList.toArray());
        } catch (Exception e) {
            LOGGER.error("EKIndexSuppIndexAppDaoImpl.add failed, e : " + e);
        }
    }

    /**
     * ApP 定制管理  :  删除
     *
     * @param id
     */
    @Override
    public void toDel(String id) {
        try {
            String sql = "DELETE FROM T_EK_INDEX_SUPPLIER_INDEXAPP WHERE C_ID = ?";
            this.jdbcTemplate.update(sql, new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKIndexSuppIndexAppDaoImpl.toDel failed, e : " + e);
        }
    }

    /**
     * ApP 定制管理  :  查询一条信息
     *
     * @param id
     * @return
     */
    @Override
    public List<Map<String, Object>> selectOne(String id) {
        try {
            String sql = "SELECT T.C_ID,T.C_CODE,T.C_INDEX_AID,T.C_ORDER,T.C_ISLIVE,TBS.C_SUP_NAME" +
                    " FROM T_EK_INDEX_SUPPLIER_INDEXAPP T" +
                    " LEFT JOIN T_BASE_SUPPLIER TBS ON TBS.C_SUPPLIER_CODE = T.C_CODE WHERE T.C_ID=?";
            List<Object> argsList = new ArrayList<Object>();
            argsList.add(id);
            return jdbcTemplate.queryForList(sql, argsList.toArray());
        } catch (Exception e) {
            LOGGER.error("EKIndexSuppIndexAppDaoImpl.selectOne failed, e : " + e);
        }
        return null;
    }

    /**
     * ApP 定制管理  :  更新
     *
     * @param args
     */
    @Override
    public void update(List<Object> args) {
        try {
            String sql = "UPDATE T_EK_INDEX_SUPPLIER_INDEXAPP T SET T.C_INDEX_AID=?,T.C_ORDER=?,T.C_ISLIVE=? WHERE T.C_ID = ?";
            this.jdbcTemplate.update(sql, args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKIndexSuppIndexAppDaoImpl.update failed, e : " + e);
        }
    }

    @Override
    public int isExists(String code, String aid) {
        try {
            String sql = "SELECT COUNT(1) FROM T_EK_INDEX_SUPPLIER_INDEXAPP T WHERE T.C_CODE = ? AND T.C_INDEX_AID = ?";
            return this.jdbcTemplate.queryForObject(sql, new Object[]{code, aid}, Integer.class);
        } catch (Exception e) {
            LOGGER.error("EKIndexSuppIndexAppDaoImpl.isExists failed, e : " + e);
        }
        return 0;
    }

    @Override
    // 更新已存在的定制APP
    public void updateExists(String code, String aid, String order) {
        try {
            String sql = "UPDATE T_EK_INDEX_SUPPLIER_INDEXAPP T SET T.C_ISLIVE = 1 WHERE T.C_CODE = ? AND T.C_INDEX_AID = ?";
            this.jdbcTemplate.update(sql, new Object[]{code, aid});
        } catch (Exception e) {
            LOGGER.error("EKIndexSuppIndexAppDaoImpl.updateExists failed, e : " + e);
        }
    }

}
