package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKShopTaskInfoDao;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/4/19.
 */
@Repository
public class EKShopTaskInfoDaoImpl implements EKShopTaskInfoDao {
    private static final Logger LOGGER = Logger.getLogger(EKShopTaskInfoDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询 商城任务列表
     *
     * @param page
     * @param type
     * @param subType
     * @return
     */
    @Override
    public Map<String, Object> list(int page, String type, String subType) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            if (StrUtils.isNotEmpty(page)) {
                StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM T_EK_TASK T WHERE 1=1 AND T.C_ISLIVE = '1' ");
                List<Object> args = new ArrayList<Object>();

                if (StrUtils.isNotEmpty(type)) {
                    sql.append(" AND T.C_TYPE= ?");
                    args.add(type);
                }
                if (StrUtils.isNotEmpty(subType.trim())) {
                    sql.append(" AND T.C_SUBTYPE = ?");
                    args.add(subType.trim());
                }
                int count = jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
                // 查询
                sql.append(" ORDER BY C_ID");
                String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, args).toString().replace("COUNT(1)", "T.C_ID,T.C_TYPE,T.C_SUBTYPE,T.C_NAME,T.C_SUBNAME,T.C_SCORE,T.C_LOGO," +
                        "CASE WHEN T.C_TYPE = '1' THEN '新手任务' WHEN T.C_TYPE = '2' THEN '每日任务'  WHEN T.C_TYPE = '3' THEN '特殊任务' ELSE '其他' END AS TYPE");
                List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, args.toArray());
                ret.put("count", count);
                ret.put("list", list);
            }
        } catch (Exception e) {
            LOGGER.error("EKFreeCallDaoImpl.list failed, e : " + e);
        }
        return ret;
    }

    /**
     * 商城任务 保存
     *
     * @param map
     */
    @Override
    public void save(Map<String, Object> map) {
        Object id_old = map.get("C_ID");

        if (!StringUtils.isEmpty(id_old)) {
            String sql = "select count(1) from T_EK_TASK t where t.c_id=?";
            int count = jdbcTemplate.queryForObject(sql, new Object[]{id_old}, Integer.class);

            if (count >= 1) {
                Map<String, Object> wMap = new HashMap<String, Object>();
                wMap.put("C_ID", id_old);
                JdbcTemplateUtils.updateDataByMap(jdbcTemplate, map, wMap,
                        "T_EK_TASK");
            } else {
                String sqlid = JdbcTemplateUtils.getSeqId(jdbcTemplate,
                        "SEQ_EK_TASK");
                map.put("C_ID", sqlid);
                JdbcTemplateUtils.saveDataByMap(jdbcTemplate, map,
                        "T_EK_TASK");
            }
        } else {
            String sqlid = JdbcTemplateUtils.getSeqId(jdbcTemplate,
                    "SEQ_EK_TASK");
            map.put("C_ID", sqlid);
            JdbcTemplateUtils.saveDataByMap(jdbcTemplate, map,
                    "T_EK_TASK");
        }
    }

    /**
     * 加载查询条件
     *
     * @return
     */
    @Override
    public Map<String, Object> loadInfo() {
        Map<String, Object> ret = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer(
                "SELECT DISTINCT(T.C_SUBTYPE),T.C_NAME FROM T_EK_TASK T WHERE 1=1 AND T.C_ISLIVE = '1' AND T.C_TYPE !='1'");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
        ret.put("list", list);
        return ret;
    }

    /**
     * 查询 子类型
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> subTypeInfo() {
        String sql = "SELECT DISTINCT(T.C_SUBTYPE),T.C_NAME FROM T_EK_TASK T WHERE 1=1 AND T.C_ISLIVE = '1' AND T.C_TYPE !='1'";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    /**
     * 查询 一条数据
     *
     * @param id
     * @return
     */
    @Override
    public List<Map<String, Object>> taskList(String id) {
        String sql = "SELECT T.C_ID,T.C_TYPE,T.C_SUBTYPE,T.C_NAME,T.C_SUBNAME,T.C_SCORE,T.C_LOGO,T.C_ISLIVE FROM T_EK_TASK T WHERE T.C_ID=?";
        List<Object> args = new ArrayList<Object>();
        args.add(id);
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
        return list;
    }

    @Override
    public Map<String, Object> loadInfos(String type) {
        Map<String, Object> ret = new HashMap<String, Object>();
        List<Object> args = new ArrayList<Object>();
        args.add(type);
        String sql = "SELECT DISTINCT(T.C_SUBTYPE),T.C_NAME FROM T_EK_TASK T WHERE 1=1 AND T.C_ISLIVE = '1' AND T.C_TYPE =? ";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
        ret.put("list", list);
        return ret;
    }

    /**
     * 添加任务
     *
     * @param args
     */
    @Override
    public void addTask(List<Object> args) {
        try {
            String sql = "INSERT INTO T_EK_TASK" +
                    " (C_ID,C_TYPE,C_SUBTYPE,C_NAME,C_SUBNAME,C_SCORE,C_LOGO,C_ISLIVE)" +
                    " VALUES(?, ? , ? , ? , ? , ?,?, ? )";
            this.jdbcTemplate.update(sql, args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKShopTaskInfoDaoImpl.addTask failed, e : ", e);
        }
    }

    /**
     * 更新任务
     *
     * @param args
     */
    @Override
    public void updateTask(List<Object> args) {
        try {
            String sql = "UPDATE T_EK_TASK SET C_TYPE = ?,C_SUBTYPE=?,C_NAME=?,C_SUBNAME=?,C_SCORE=?," +
                    "C_LOGO=?, C_ISLIVE = ? WHERE C_ID = ?";
            this.jdbcTemplate.update(sql, args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKShopTaskInfoDaoImpl.updateTask failed, e : " + e);
        }
    }

    /**
     * 获取特殊任务列表
     *
     * @param page
     * @return
     */
    @Override
    public Map<String, Object> getTSTask(int page) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            if (StrUtils.isNotEmpty(page)) {
                StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM T_EK_TASK T WHERE 1=1 AND T.C_ISLIVE = '1' AND T.C_TYPE='3'");
                List<Object> args = new ArrayList<Object>();

                int count = jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
                // 查询
                sql.append(" ORDER BY C_ID");
                String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, args).toString().replace("COUNT(1)", "T.C_ID,T.C_TYPE,T.C_SUBTYPE,T.C_NAME,T.C_SUBNAME,T.C_SCORE,T.C_LOGO," +
                        "CASE WHEN T.C_TYPE = '1' THEN '新手任务' WHEN T.C_TYPE = '2' THEN '每日任务'  WHEN T.C_TYPE = '3' THEN '特殊任务' ELSE '其他' END AS TYPE");
                List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, args.toArray());
                ret.put("count", count);
                ret.put("list", list);
            }
        } catch (Exception e) {
            LOGGER.error("EKFreeCallDaoImpl.getTSTask failed, e : " + e);
        }
        return ret;
    }

    /**
     * 查询特殊任务中间表的一条数据
     *
     * @param id
     * @return
     */
    @Override
    public List<Map<String, Object>> selectOne(String id,String tid) {
        String sql = "SELECT T.C_ID,T.C_TID,T.C_SDATE,T.C_EDATE,T.C_ITEM_ID,(SELECT TET.C_SUBNAME FROM T_EK_TASK TET WHERE TET.C_ID=?)AS C_SUBNAME  FROM T_EK_TASK_SPECIAL T " +
                "WHERE T.C_ID=?";
        List<Object> args = new ArrayList<Object>();
        args.add(tid);
        args.add(id);
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
        return list;
    }

    /**
     * 查询指定 的APP
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getAppList() {
        String sql = "SELECT C_ID,C_NAME FROM T_EK_INDEX_APP_INFO WHERE C_ISLIVE = 1 AND C_TYPE = '0'";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    /**
     * 查询活动
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getActList() {
        String sql = "SELECT T.C_ID,T.C_TITLE AS C_NAME FROM T_EK_ACTIVITY_INFO T WHERE T.C_ISLIVE='1' AND T.C_AUDIT_STATUS='1'";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    /**
     * 修改特殊任务
     *
     * @param id
     * @param sdate
     * @param edate
     * @param gid
     */
    @Override
    public void updateInfo(String id, String sdate, String edate, String gid,String tid) {
        try {
            String sql = "UPDATE T_EK_TASK_SPECIAL SET C_SDATE = ?,C_EDATE=?,C_ITEM_ID=?,C_TID=? WHERE C_ID = ?";
            List<Object> args = new ArrayList<Object>();
            args.add(ApDateTime.getDate(sdate, ApDateTime.DATE_TIME_Sec));
            args.add(ApDateTime.getDate(edate, ApDateTime.DATE_TIME_Sec));
            args.add(gid);
            args.add(tid);
            args.add(id);
            this.jdbcTemplate.update(sql, args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKShopTaskInfoDaoImpl.updateInfo failed, e : " + e);
        }
    }

    /**
     * 游戏列表
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getGameList() {
        String sql = "SELECT T.C_ID,T.C_NAME FROM T_GAME_APP_INFO T WHERE T.C_ISLIVE='1'";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    /**
     * 按键设置列表
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getSettingList() {
        String sql = "SELECT T.C_ID,T.C_TITLE AS C_NAME\n" +
                "FROM T_EK_INDEX_KEY_APP T WHERE T.C_KEY ='4' AND T.C_SUPCODE !='-1' AND T.C_ISLIVE=1";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }


}
