package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKIndexKeyAppDao;
import com.org.mokey.common.util.DaoUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/15.
 */
@Repository
public class EKIndexKeyAppDaoImpl implements EKIndexKeyAppDao {
    private static final Logger LOGGER = Logger .getLogger(EKIndexKeyAppDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * e键 ： 首页  ： 键位管理  ：列表
     * @param page
     * @return
     */
    @Override
    public Map<String, Object> ekIndexKeyAppList(int page) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
                    " FROM T_EK_INDEX_KEY_APP T" +
                    " WHERE T.C_ISLIVE = 1");
            List<Object> argsList = new ArrayList<Object>();
            int count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);

            sql.append(" ORDER BY T.C_ORDER DESC");
            String sql1 = DaoUtil.addfy_oracle( sql, (page-1)*5, 5, argsList )
                    .toString().replace( "COUNT(1)", "T.C_ID,T.C_NAME,T.C_TITLE,T.C_KEY,T.C_ORDER,T.C_CREATER,T.C_CDATE," +
                            "(SELECT TB.C_SUPPLIER_NAME FROM T_BASE_SUPPLIER TB WHERE TB.C_SUPPLIER_CODE = T.C_SUPCODE ) C_SUPPLIER_NAME" );
            List<Map<String,Object>> list = jdbcTemplate.queryForList( sql1, argsList.toArray() );
            ret.put( "count", count );
            ret.put( "list", list );
        } catch (Exception e) {
            LOGGER.error("EKIndexKeyAppDaoImpl.ekIndexKeyAppList failed, e : " + e);
        }
        return ret;
    }

    /**
     * e键 ： 首页  ： 键位管理 ： 添加
     * @param argsList
     */
    @Override
    public void addKeyApp(List<Object> argsList) {
        try {
            String sql = "INSERT INTO T_EK_INDEX_KEY_APP" +
                    " (C_ID ,C_KEY ,C_SUPCODE ,C_NAME,C_TITLE,C_LOGO,C_IMG,C_APPURL,C_JAR,C_SIZE,C_MENU,C_MENUTEL,C_ABSTRACT," +
                    " C_VERSION,C_DOWNLOAD,C_GRADE,C_H5URL,C_SOURCE,C_SUPURL,C_ORDER,C_CREATER,C_ISLIVE,C_CDATE)" +
                    " VALUES(?, ?, ?, ? ,?,?, ?, ?, ? ,?,?, ?, ?, ? ,?,?, ?, ?, ? ,?,?, ?, ?)";
            this.jdbcTemplate.update(sql,argsList.toArray());
        } catch (Exception e) {
            LOGGER.error("EKIndexKeyAppDaoImpl.addKeyApp failed, e : " + e);
        }

    }

    /**
     * e键 ： 首页  ： 键位管理  ： 删除
     * @param id
     */
    @Override
    public void toDel(String id) {
        try {
            String sql = "DELETE FROM T_EK_INDEX_KEY_APP WHERE C_ID = ?";
            this.jdbcTemplate.update(sql,new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKIndexKeyAppDaoImpl.toDel failed, e : " + e);
        }
    }

    /**
     * e键 ： 首页  ： 键位管理   ： 更新
     * @param argsList
     */
    @Override
    public void updateKeyApp(List<Object> argsList) {
        try {
            String sql = "UPDATE T_EK_INDEX_KEY_APP T SET C_KEY=? ,C_SUPCODE=? ,C_NAME=?,C_TITLE=?,C_LOGO=?,C_IMG=?,C_APPURL=?,C_JAR=?,C_SIZE=?" +
                    " ,C_MENU=?,C_MENUTEL=?,C_ABSTRACT=?," +
                    " C_VERSION=?,C_DOWNLOAD=?,C_GRADE=?,C_H5URL=?,C_SOURCE=?,C_SUPURL=?,C_ORDER=?,C_MODIFIER=?,C_ISLIVE=?,C_EDATE=?" +
                    " WHERE T.C_ID = ?";
            this.jdbcTemplate.update(sql,argsList.toArray());
        } catch (Exception e) {
            LOGGER.error("EKIndexKeyAppDaoImpl.updateTag failed, e : " + e);
        }
    }
    /**
     * e键 ： 首页  ： 键位管理   ： app应用
     * @param
     */
    @Override
    public List<Map<String, Object>> getList() {
        final String sql = "SELECT C_SUPPLIER_CODE,C_SUPPLIER_NAME,C_SUP_NAME FROM T_BASE_SUPPLIER T WHERE T.C_ISLIVE=1";
        try {
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            LOGGER.error("EKIndexKeyAppDaoImpl.getList failed, e : " + e);
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> selectOne(String id) {
        try {
            String sql = "SELECT T.* FROM T_EK_INDEX_KEY_APP T WHERE T.C_ISLIVE=1 AND T.C_ID=?";
            List<Object> argsList = new ArrayList<Object>();
            argsList.add(id);
            return jdbcTemplate.queryForList(String.valueOf(sql),argsList.toArray());
        } catch (Exception e) {
            LOGGER.error("EKIndexKeyAppDaoImpl.selectOne failed, e : " + e);
        }
        return null;
    }

}
