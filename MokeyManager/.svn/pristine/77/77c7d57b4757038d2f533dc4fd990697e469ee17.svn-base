package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.PhoneModelDao;
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
 * Created by Maryn on 2016/4/26.
 */
@Repository
public class PhoneModelDaoImpl implements PhoneModelDao {

    private static final Logger LOGGER = Logger.getLogger(EKIndexSuppIndexAppDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    // 分页查询
    public Map<String, Object> phoneList(int page, int rows, String brandName, String islive) {
        Map<String, Object> reqMap = new HashMap<>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM T_BASE_PHONE_BRAND T WHERE 1 = 1");
            List<Object> argsList = new ArrayList<>();
            if (StrUtils.isNotEmpty(brandName)) {
                sql.append(" AND T.C_NAME LIKE ? ");
                argsList.add("%" + brandName + "%");
            }
            if (StrUtils.isNotEmpty(islive)) {
                sql.append(" AND T.C_ISLIVE = ? ");
                argsList.add(islive);
            }
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

            sql.append(" ORDER BY C_ETIME DESC, C_ORDER ");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * rows, rows, argsList)
                    .toString().replace("COUNT(1)", "C_ID, C_NAME, C_MODIFIER, C_ETIME, C_ISLIVE");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            reqMap.put("count", count);
            reqMap.put("list", list);
        } catch (Exception e) {
            LOGGER.error("PhoneModelDaoImpl.phoneList failed, e : " + e);
        }
        return reqMap;
    }

    @Override
    // 新增手机品牌
    public void addBrand(String name, String userName) {
        try {
            StringBuffer sql = new StringBuffer("INSERT INTO T_BASE_PHONE_BRAND" +
                    " (C_ID, C_NAME, C_ISLIVE, C_MODIFIER, C_ETIME)" +
                    " VALUES (SEQ_BASE_PHONE_BRAND.NEXTVAL, ?, '1', ?, SYSDATE)");
            jdbcTemplate.update(sql.toString(), new Object[]{name, userName});
        } catch (Exception e) {
            LOGGER.error("PhoneModelDaoImpl.addBrand failed, e : " + e);
        }
    }

    @Override
    // 编辑品牌
    public void editBrand(String id, String name, String userName) {
        try {
            StringBuffer sql = new StringBuffer("UPDATE T_BASE_PHONE_BRAND T" +
                    " SET T.C_NAME = ?, T.C_MODIFIER = ?, T.C_ETIME = SYSDATE WHERE T.C_ID = ?");
            jdbcTemplate.update(sql.toString(), new Object[]{name, userName, id});
        } catch (Exception e) {
            LOGGER.error("PhoneModelDaoImpl.addBrand failed, e : " + e);
        }
    }

    @Override
    // 手机品牌上下架
    public void onShelf(String id, String act, String type, String userName) {
        try {
            String tableName = "";
            switch (type) {
                case "1":
                    tableName = "T_BASE_PHONE_BRAND";
                    break;
                case "2":
                    tableName = "T_BASE_PHONE_MODEL";
                    break;
            }
            StringBuffer sql = new StringBuffer("UPDATE " + tableName + " T SET" +
                    " T.C_ISLIVE = ?, T.C_MODIFIER = ?, T.C_ETIME = SYSDATE WHERE T.C_ID = ?");
            jdbcTemplate.update(sql.toString(), new Object[]{act, userName, id});
        } catch (Exception e) {
            LOGGER.error("PhoneModelDaoImpl.onShelf failed, e : " + e);
        }
    }

    @Override
    // 分页查询品牌下的型号
    public Map<String, Object> modelList(Integer page, Integer rows, String brandId, String phoneName, String islive) {
        Map<String, Object> reqMap = new HashMap<>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM T_BASE_PHONE_MODEL T WHERE T.C_BRAND_ID = ?");
            List<Object> argsList = new ArrayList<>();
            argsList.add(brandId);
            if (StrUtils.isNotEmpty(phoneName)) {
                sql.append(" AND T.C_NAME LIKE ? ");
                argsList.add("%" + phoneName + "%");
            }
            if (StrUtils.isNotEmpty(islive)) {
                sql.append(" AND T.C_ISLIVE = ? ");
                argsList.add(islive);
            }
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

            sql.append(" ORDER BY T.C_ETIME DESC, T.C_NAME ");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * rows, rows, argsList)
                    .toString().replace("COUNT(1)", "*");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            reqMap.put("count", count);
            reqMap.put("list", list);
        } catch (Exception e) {
            LOGGER.error("PhoneModelDaoImpl.modelList failed, e : " + e);
        }
        return reqMap;
    }

    @Override
    // 新增指定手机品牌下的型号
    public void addModel(String name, String code, String brandId, String userName) {
        try {
            StringBuffer sql = new StringBuffer("INSERT INTO T_BASE_PHONE_MODEL" +
                    " (C_ID, C_BRAND_ID, C_CODE,  C_NAME, C_ISLIVE, C_MODIFIER, C_ETIME)" +
                    " VALUES (SEQ_BASE_PHONE_MODEL.NEXTVAL, ?, ?, ?, '1', ?, SYSDATE)");
            jdbcTemplate.update(sql.toString(), new Object[]{brandId, code, name, userName});
        } catch (Exception e) {
            LOGGER.error("PhoneModelDaoImpl.addModel failed, e : " + e);
        }
    }

    @Override
    // 编辑型号
    public void editModel(String name, String code, String brandId, String userName, String id) {
        try {
            StringBuffer sql = new StringBuffer("UPDATE T_BASE_PHONE_MODEL T SET" +
                    " T.C_NAME = ?, T.C_CODE = ?, T.C_MODIFIER = ?, T.C_ETIME = SYSDATE" +
                    " WHERE T.C_ID = ? AND T.C_BRAND_ID = ?");
            jdbcTemplate.update(sql.toString(), new Object[]{name, code, userName, id, brandId});
        } catch (Exception e) {
            LOGGER.error("PhoneModelDaoImpl.addModel failed, e : " + e);
        }
    }
}
