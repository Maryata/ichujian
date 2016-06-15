package com.org.mokey.system.dao.impl;

import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.system.dao.DataPermissionDao;
import com.org.mokey.util.StrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/1/11.
 */
public class DataPermissionDaoImpl implements DataPermissionDao {

    private static final Log LOGGER = LogFactory.getLog(DataPermissionDaoImpl.class);

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Map<String, Object>> selectSuppliers() {
        try {
            String sql = "SELECT T.C_USERID, T.C_USERCODE, T.C_USERNAME" +
                    " FROM T_SYS_USER T, T_SYS_USER_ROLE R WHERE" +
                    " T.C_USERID = R.C_USERID AND R.C_ROLE_ID = '28A514DBAAAA0847E050A8C0DF082A64'";
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            LOGGER.error("DataPermissionDaoImpl.selectSuppliers failed! e : ", e);
        }
        return new ArrayList<Map<String, Object>>();
    }

    @Override
    public Map<String, Object> getAllSuppliers(String uid, int page, String supCode, String supName) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM T_BASE_SUPPLIER T" +
                    " WHERE T.C_ISLIVE = '1' AND T.C_IS_POTENTIAL_DEMAND = '0'");
            List<Object> argsList = new ArrayList<Object>();
            if (StrUtils.isNotEmpty(supCode)) {
                sql.append(" AND T.C_SUPPLIER_CODE = ?");
                argsList.add(supCode);
            }
            if (StrUtils.isNotEmpty(supName)) {
                sql.append(" AND T.C_SUPPLIER_NAME LIKE ?");
                argsList.add("%" + supName + "%");
            }
            sql.append(" AND NOT EXISTS (SELECT C_SID FROM T_SYS_DATA_PERMISSION P" +
                    " WHERE T.C_ID = P.C_SID AND C_UID = ?)");
            argsList.add(uid);
            // 查询总数
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

            sql.append(" ORDER BY T.C_SUPPLIER_NAME ");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList)
                    .toString().replace("COUNT(1)", "C_ID, C_SUPPLIER_CODE, C_SUPPLIER_NAME");
            // 查询数据
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("DataPermissionDaoImpl.getAllSuppliers failed, e : ", e);
        }
        return ret;
    }

    @Override
    public Map<String, Object> getCurrSup(String uid, int page, String supCode, String supName) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM" +
                    " T_BASE_SUPPLIER T, T_SYS_DATA_PERMISSION P" +
                    " WHERE T.C_ISLIVE = '1' AND T.C_IS_POTENTIAL_DEMAND = '0'" +
                    " AND T.C_ID = P.C_SID AND P.C_UID = ?");
            List<Object> argsList = new ArrayList<Object>();
            argsList.add(uid);
            if (StrUtils.isNotEmpty(supCode)) {
                sql.append(" AND T.C_SUPPLIER_CODE = ?");
                argsList.add(supCode);
            }
            if (StrUtils.isNotEmpty(supName)) {
                sql.append(" AND T.C_SUPPLIER_NAME LIKE ?");
                argsList.add("%" + supName + "%");
            }
            // 查询总数
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

            sql.append(" ORDER BY T.C_SUPPLIER_NAME ");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList)
                    .toString().replace("COUNT(1)", "T.C_ID, T.C_SUPPLIER_CODE, T.C_SUPPLIER_NAME");
            // 查询数据
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("DataPermissionDaoImpl.getCurrSup failed, e : ", e);
        }
        return ret;
    }

    @Override
    public int isExist(String userId, String supId) {
        try {
            String sql = "SELECT COUNT(1) FROM T_SYS_DATA_PERMISSION T WHERE T.C_UID = ? AND T.C_SID = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{userId, supId}, Integer.class);
        } catch (Exception e) {
            LOGGER.error("DataPermissionDaoImpl.isExist failed! e : ", e);
        }
        return 0;
    }

    @Override
    public void handle(String userId, String supId, String supCode) {
        try {
            String sql = "INSERT INTO T_SYS_DATA_PERMISSION (C_ID, C_UID, C_SID, C_SCODE)" +
                    " VALUES (SEQ_SYS_DATA_PERMISSION.NEXTVAL, ?, ?, ?)";
            jdbcTemplate.update(sql, new Object[]{userId, supId, supCode});
        } catch (Exception e) {
            LOGGER.error("DataPermissionDaoImpl.handle failed! e : ", e);
        }
    }

    @Override
    public void removeSup(String userId, String supId) {
        try {
            String sql = "DELETE FROM T_SYS_DATA_PERMISSION WHERE C_UID = ? AND C_SID = ?";
            jdbcTemplate.update(sql, new Object[]{userId, supId});
        } catch (Exception e) {
            LOGGER.error("DataPermissionDaoImpl.removeGame failed! e : ", e);
        }
    }
}
