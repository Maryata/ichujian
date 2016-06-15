package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKMallProductDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 商品相亲
 * Created by vpc on 2016/4/25.
 */
@Repository
public class EKMallProductDaoImpl implements EKMallProductDao {
    private static final Logger LOGGER = Logger.getLogger(EKMallProductDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询商品信息
     *
     * @param page
     * @param title
     * @param type
     * @return
     */
    @Override
    public Map<String, Object> list(int page, String title, String type) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            if (StrUtils.isNotEmpty(page)) {
                StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM T_EK_MALL_PRODUCT T WHERE T.C_ISLIVE='1'");
                List<Object> args = new ArrayList<Object>();

                if (StrUtils.isNotEmpty(title)) {
                    sql.append(" AND T.C_TITLE LIKE ?");
                    args.add("%" + title + "%");
                }
                if (StrUtils.isNotEmpty(type)) {
                    sql.append(" AND T.C_TYPE = ?");
                    args.add(type);
                }
                int count = jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
                // 查询
                sql.append(" ORDER BY T.C_CTIME DESC");
                String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, args).toString().replace("COUNT(1)", "T.C_ID,T.C_TITLE,T.C_COST,T.C_TOTAL,T.C_IMG,T.C_TYPE,T.C_CREATER,T.C_CTIME,T.C_AMOUNT,CASE WHEN T.C_TYPE=1 THEN '虚拟物品' ELSE '实物物品' END AS TYPE ");
                List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, args.toArray());
                ret.put("count", count);
                ret.put("list", list);
            }
        } catch (Exception e) {
            LOGGER.error("EKMallProductDaoImpl.list failed, e : " + e);
        }
        return ret;
    }

    /**
     * 添加商品信息
     *
     * @param id
     * @param
     * @param logo
     * @param
     * @param
     */
    @Override
    public void addProduct(String id, String title, String subTitle, String function, String anAbstract, String cost,
                           String total, String type, String carriage, String logo, String amount, String islive, String detail, String illustr, String modifier) {
        try {
            //ApDateTime.getDate(sdate, ApDateTime.DATE_TIME_Sec); args.add(ApDateTime.getDate(sdate, ApDateTime.DATE_TIME_HM));
            String sql = "INSERT INTO T_EK_MALL_PRODUCT" +
                    " (C_ID,C_TITLE,C_SUBTITLE,C_FUNCTION,C_ABSTRACT," +
                    "C_COST,C_TOTAL,C_DETAIL,C_ILLUSTR,C_IMG," +
                    "C_TYPE,C_CARRIAGE,C_ISLIVE,C_AMOUNT,C_CREATER," +
                    "C_CTIME)" +
                    " VALUES(" +
                    "?,?,?,?,?," +
                    "?,?,?,?,?," +
                    "?,?,?,?,?," +
                    "?)";
            List<Object> args = new ArrayList<Object>();
            args.add(id);
            args.add(title);
            args.add(subTitle);
            args.add(function);
            args.add(anAbstract);

            args.add(cost);
            args.add(total);
            args.add(detail);
            args.add(illustr);
            args.add(logo);

            args.add(type);
            args.add(carriage);
            args.add(islive);
            args.add(amount);
            args.add(modifier);

            args.add(new Date());
            this.jdbcTemplate.update(sql, args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKMallProductDaoImpl.addProduct failed, e : ", e);
        }
    }

    /**
     * 删除商品信息
     *
     * @param id
     */
    @Override
    public void toDel(String id) {
        try {
            String sql = "DELETE FROM T_EK_MALL_PRODUCT WHERE C_ID = ?";
            this.jdbcTemplate.update(sql, new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKMallProductDaoImpl.toDel failed, e : " + e);
        }
    }

    /**
     * 修改商品信息
     *
     * @param id
     * @param title
     * @param logo
     * @param function
     * @param anAbstract
     */
    @Override
    public void updateProduct(String id, String title, String subTitle, String function, String anAbstract, String cost,
                              String total, String type, String carriage, String logo, String amount, String islive, String detail, String illustr, String modifier) {
        try {
            String sql = "UPDATE T_EK_MALL_PRODUCT SET C_TITLE=?,C_SUBTITLE=?,C_FUNCTION=?,C_ABSTRACT=?," +
                    "C_COST=?,C_TOTAL=?,C_DETAIL=?,C_ILLUSTR=?,C_IMG=?," +
                    "C_TYPE=?,C_CARRIAGE=?,C_ISLIVE=?,C_AMOUNT=?,C_EDITOR=?,C_ETIME=? " +
                    "WHERE C_ID = ?";

            List<Object> args = new ArrayList<Object>();
            args.add(title);
            args.add(subTitle);
            args.add(function);
            args.add(anAbstract);

            args.add(cost);
            args.add(total);
            args.add(detail);
            args.add(illustr);
            args.add(logo);

            args.add(type);
            args.add(carriage);
            args.add(islive);
            args.add(amount);
            args.add(modifier);

            args.add(new Date());
            args.add(Integer.parseInt(id));
            this.jdbcTemplate.update(sql, args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKMallProductDaoImpl.updateProduct failed, e : " + e);
        }
    }

    /**
     * 查询商品详情
     *
     * @param id
     * @return
     */
    @Override
    public List<Map<String, Object>> selectOne(String id) {
        String sql = "SELECT C_ID,C_TITLE,C_SUBTITLE,C_FUNCTION,C_ABSTRACT,C_COST,C_TOTAL,C_DETAIL,C_ILLUSTR,C_IMG,C_TYPE,C_CARRIAGE,C_ISLIVE,C_AMOUNT FROM T_EK_MALL_PRODUCT WHERE  C_ID=?";
        List<Object> args = new ArrayList<Object>();
        args.add(id);
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
        return list;
    }

    /**
     * 查询商品吗列表
     * select t.c_id,t.c_code,t.c_state,t.c_islive from T_EK_MALL_PRO_CODE t
     * WHERE t.c_pid='1'
     *
     * @param page
     * @param type
     * @return
     */
    @Override
    public Map<String, Object> getCodeList(int page, String pid, String type) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            if (StrUtils.isNotEmpty(page)) {
                StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM T_EK_MALL_PRO_CODE T WHERE T.C_PID=?");
                List<Object> args = new ArrayList<Object>();
                args.add(pid);
                if (StrUtils.isNotEmpty(type)) {
                    sql.append(" AND T.C_STATE = ?");
                    args.add(type);
                }
                int count = jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
                // 查询
                sql.append(" ORDER BY T.C_ID ASC");
                String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 10, 10, args).toString().replace("COUNT(1)", "T.C_ID,T.C_CODE,T.C_STATE,T.C_ISLIVE");
                List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, args.toArray());
                ret.put("count", count);
                ret.put("list", list);
            }
        } catch (Exception e) {
            LOGGER.error("EKMallProductDaoImpl.getCodeList failed, e : " + e);
        }
        return ret;
    }

    /**
     * 上传商品码
     *
     * @param list
     */
    @Override
    public void upload(List<Object[]> list) {
        try {
            if (StrUtils.isNotEmpty(list)) {
                String sql = "INSERT INTO T_EK_MALL_PRO_CODE (C_ID, C_PID, C_CODE, C_ISLIVE, C_STATE)" +
                        "VALUES(?, ?, ?, ?, ?)";
                this.jdbcTemplate.batchUpdate(sql, list);
            }
        } catch (Exception e) {
            LOGGER.error("EKMallProductDaoImpl.upload failed, e : " + e);
        }
    }
}
