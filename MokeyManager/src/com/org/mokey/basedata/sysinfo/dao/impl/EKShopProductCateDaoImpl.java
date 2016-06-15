package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKShopProductCateDao;
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
 * e键 ： 商品分类管理
 */
@Repository
public class EKShopProductCateDaoImpl implements EKShopProductCateDao {
    private static final Logger LOGGER = Logger.getLogger(EKShopProductCateDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    // 分页显示商品分类
    public Map<String, Object> productCateList(int page) {
        Map<String, Object> ret = new HashMap<>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM T_EK_MALL_CATEGORY WHERE C_ISLIVE = 1");
            List<Object> argsList = new ArrayList<>();
            int count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);

            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList)
                    .toString().replace("COUNT(1)", "*");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKShopProductCateDaoImpl.productCateList failed, e : " + e);
        }
        return ret;
    }

    @Override
    // 查询非当前商品分类的其他所有商品
    public Map<String, Object> getAllProduct(int page, String cid, String name) {
        Map<String, Object> ret = new HashMap<>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM T_EK_MALL_PRODUCT T" +
                    " WHERE T.C_ISLIVE=1 AND NOT EXISTS(SELECT C.C_PID FROM T_EK_MALL_PRO_CATE C WHERE C.C_PID = T.C_ID AND C.C_CID=?)");
            List<Object> argsList = new ArrayList<Object>();
            argsList.add(cid);
            if (StrUtils.isNotEmpty(name)) {
                sql.append(" AND T.C_TITLE LIKE ?");
                argsList.add("%" + name + "%");
            }
            // 查询总数
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

            sql.append(" ORDER BY T.C_ID DESC ");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList).toString().replace("COUNT(1)", "T.C_ID, T.C_TITLE");
            // 查询数据
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKShopProductCateDaoImpl.getAllProduct failed, e : " + e);
        }
        return ret;
    }

    @Override
    // 查询当前商品分类的商品
    public Map<String, Object> getCurrCateProduct(int page, String cid, String name) {
        Map<String, Object> ret = new HashMap<>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM" +
                    " T_EK_MALL_PRODUCT T ,T_EK_MALL_PRO_CATE C" +
                    " WHERE T.C_ISLIVE=1 AND T.C_ID = C.C_PID AND C.C_CID = ?");
            List<Object> argsList = new ArrayList<>();
            argsList.add(cid);
            if (StrUtils.isNotEmpty(name)) {
                sql.append(" AND T.C_TITLE LIKE ?");
                argsList.add("%" + name + "%");
            }
            // 查询总数
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

            sql.append(" ORDER BY C.C_ID");
            String sql1 = DaoUtil.addfy_oracle(sql,
                    (page - 1) * 5, 5, argsList).toString().replace("COUNT(1)", "C.C_PID , T.C_TITLE, C.C_ORDER");
            // 查询数据
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKShopProductCateDaoImpl.getCurrCateProduct failed, e : " + e);
        }
        return ret;
    }

    @Override
    // 查询商品是否已经存在于当前分类
    public int isExist(String cid, String pid) {
        try {
            String sql = "SELECT COUNT(1) FROM T_EK_MALL_PRO_CATE WHERE C_CID = ? AND C_PID = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{cid, pid}, Integer.class);
        } catch (Exception e) {
            LOGGER.error("EKShopProductCateDaoImpl.isExist failed, e : " + e);
        }
        return 0;
    }

    @Override
    // 新增商品到当前分类
    public void add(String cid, String pid, String order) {
        try {
            String sql = "INSERT INTO T_EK_MALL_PRO_CATE" +
                    " (C_ID, C_PID, C_CID, C_ORDER) VALUES" +
                    " (SEQ_EK_MALL_PRO_CODE.NEXTVAL,?,?,?)";
            jdbcTemplate.update(sql, new Object[]{pid, cid, order});
        } catch (Exception e) {
            LOGGER.error("EKShopProductCateDaoImpl.add failed, e : " + e);
        }
    }

    @Override
    // 更新当前分类中的商品
    public void update(String cid, String pid, String order) {
        try {
            String sql = "UPDATE T_EK_MALL_PRO_CATE SET C_ORDER = ? WHERE C_CID = ? AND C_PID = ?";
            jdbcTemplate.update(sql, new Object[]{order, cid, pid});
        } catch (Exception e) {
            LOGGER.error("EKShopProductCateDaoImpl.update failed, e : " + e);
        }
    }

    @Override
    // 将指定商品移除出当前分类
    public void remove(String cid, String pid) {
        try {
            String sql = "DELETE FROM T_EK_MALL_PRO_CATE WHERE C_CID = ? AND C_PID = ?";
            jdbcTemplate.update(sql, new Object[]{cid, pid});
        } catch (Exception e) {
            LOGGER.error("EKShopProductCateDaoImpl.remove failed, e : " + e);
        }
    }
    
    
}
