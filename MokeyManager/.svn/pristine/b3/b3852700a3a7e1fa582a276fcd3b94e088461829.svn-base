package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKGameStrategyDao;
import com.org.mokey.basedata.sysinfo.dao.GameStrategyDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏攻略
 */
@Repository
public class EKGameStrategyDaoImpl implements EKGameStrategyDao {

    private static final Logger LOGGER = Logger.getLogger(EKGameStrategyDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    // 查询攻略列表
    public List<Map<String, Object>> gameStrategyList(String gid, String name, int page, String gName) {
        try {
            if (StrUtils.isNotEmpty(page)) {
                StringBuffer sb = new StringBuffer("SELECT T.C_ID ID,G.C_NAME GNAME," +
                        "T.C_NAME CNAME FROM T_EK_GAME_STRATEGY_INFO T, T_GAME_APP_INFO G" +
                        " WHERE T.C_ISLIVE = 1 AND G.C_ISLIVE = 1 AND T.C_GID = G.C_ID");
                List<Object> args = new ArrayList<Object>();
                if (StrUtils.isNotEmpty(gid)) {
                    sb.append(" AND T.C_GID = ?");
                    args.add(gid);
                }
                if (StrUtils.isEmpty(gid) && StrUtils.isNotEmpty(gName)) {
                    sb.append(" AND G.C_NAME LIKE ?");
                    args.add("%" + gName + "%");
                }
                if (StrUtils.isNotEmpty(name)) {
                    sb.append(" AND T.C_NAME LIKE ?");
                    args.add("%" + name + "%");
                }
                // 组织分页语句
                StringBuffer sql = DaoUtil.addfy_oracle(sb, "T.C_ORDER", (page - 1) * 10, 10, args);// 默认每页10条数据
                // 查询
                List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql.toString(), args.toArray());
                return list;
            }
        } catch (DataAccessException e) {
            LOGGER.error("EKGameStrategyDaoImpl.gameStrategyList failed, e : " + e);
        }
        return null;
    }

    @Override
    // 获取攻略总数
    public Integer getTotal(String gid, String name, String gName) {
        try {
            StringBuffer sb = new StringBuffer("SELECT COUNT(*) FROM " +
                    "T_EK_GAME_STRATEGY_INFO T, T_GAME_APP_INFO G WHERE T.C_ISLIVE = 1 " +
                    "AND G.C_ISLIVE = 1 AND T.C_GID = G.C_ID ");
            List<Object> args = new ArrayList<Object>();
            if (StrUtils.isNotEmpty(gid)) {
                sb.append(" AND T.C_GID = ?");
                args.add(gid);
            }
            if (StrUtils.isEmpty(gid) && StrUtils.isNotEmpty(gName)) {
                sb.append(" AND G.C_NAME LIKE ?");
                args.add("%" + gName + "%");
            }
            if (StrUtils.isNotEmpty(name)) {
                sb.append(" AND T.C_NAME LIKE ?");
                args.add("%" + name + "%");
            }
            return this.jdbcTemplate.queryForObject(sb.toString(), args.toArray(), Integer.class);
        } catch (Exception e) {
            LOGGER.error("EKGameStrategyDaoImpl.getTotalCol failed, e : " + e);
        }
        return null;
    }

    @Override
    // 根据id删除游戏攻略
    public void deleteGameStrategy(String id) {
        try {
            String sql = "DELETE FROM T_EK_GAME_STRATEGY_INFO WHERE C_ID = ?";
            this.jdbcTemplate.update(sql, new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKGameStrategyDaoImpl.getTotalCol failed, e : " + e);
        }
    }

    @Override
    // 根据id查询攻略
    public List<Map<String, Object>> queryStrategyById(String editId) {
        try {
            String sql = "SELECT * FROM T_EK_GAME_STRATEGY_INFO T WHERE T.C_ISLIVE = 1 AND T.C_ID = ?";
            return this.jdbcTemplate.queryForList(sql, new Object[]{editId});
        } catch (Exception e) {
            LOGGER.error("EKGameStrategyDaoImpl.getTotalCol failed, e : " + e);
        }
        return null;
    }

    @Override
    // 保存游戏攻略
    public void saveGameStrategy(List<Object> args) {
        try {
            String sql = "INSERT INTO T_EK_GAME_STRATEGY_INFO " +
                    "(C_ID, C_GID, C_NAME, C_DEPICT, C_DATE, C_ISLIVE, C_ORDER, C_SHAREURL, C_LOGOURL)" +
                    "VALUES(?,?,?,?,?,?,?,?,?)";
            this.jdbcTemplate.update(sql, args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKGameStrategyDaoImpl.saveGameStrategy failed, e : " + e);
        }
    }

    @Override
    // 更新游戏攻略
    public void updateGameStrategy(List<Object> args) {
        try {
            String sql = "UPDATE T_EK_GAME_STRATEGY_INFO T SET T.C_GID = ?, T.C_NAME = ?, " +
                    "T.C_DEPICT = ?, T.C_DATE = ?, T.C_ISLIVE = ?, T.C_ORDER = ?, C_SHAREURL = ?, C_LOGOURL = ? WHERE T.C_ID = ?";
            this.jdbcTemplate.update(sql, args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKGameStrategyDaoImpl.updateGameStrategy failed, e : " + e);
        }
    }

    @Override
    // 浏览游戏攻略的人数--newId：攻略id，gid：游戏id
    public Integer scanedNum(String newId, String gid) {
        try {
            String sql = "SELECT COUNT(*) FROM T_EK_GAME_CHILD_ACTION T WHERE T.C_TYPE = 0 " +
                    "AND T.C_SOURCE = 1 AND T.C_INDEXID = ? AND T.C_GID = ?";
            return this.jdbcTemplate.queryForObject(sql, new Object[]{newId, gid}, Integer.class);
        } catch (Exception e) {
            LOGGER.error("EKGameStrategyDaoImpl.scanedNum failed, e : " + e);
        }
        return null;
    }

    @Override
    // 查询id是否存在
    public boolean existId(String id) {
        try {
            String sql = "SELECT COUNT(*) FROM T_EK_GAME_STRATEGY_INFO T WHERE T.C_ID = ? ";
            Integer i = this.jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
            if (i == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("EKGameStrategyDaoImpl.scanedNum failed, e : " + e);
        }
        return false;
    }

    @Override
    // 查询所有攻略id和其对应的攻略内容（仅用于给所有图片设置尺寸）
    public List<Map<String, Object>> selectDepict() {
        try {
            StringBuffer sql = new StringBuffer("SELECT T.C_ID ID,T.C_DEPICT DECIPT" +
                    " FROM T_EK_GAME_INFORMATION_INFO T WHERE T.C_ISLIVE = 1" +
                    " AND T.C_CHANGE = 0");
            DaoUtil.addfy_oracle(sql, 0, 10);
            return this.jdbcTemplate.queryForList(sql.toString());
        } catch (Exception e) {
            LOGGER.error("EKGameStrategyDaoImpl.selectDepict failed, e : " + e);
        }
        return null;
    }

    @Override
    // 给所有图片设置尺寸
    public int setImgSize(String id, String depict, String flag) {
        try {
            String sql = "UPDATE T_EK_GAME_INFORMATION_INFO T SET T.C_DEPICT = ?, T.C_CHANGE = ? WHERE T.C_ID = ?";
            int i = this.jdbcTemplate.update(sql, new Object[]{depict, flag, id});
            return i;
        } catch (Exception e) {
            LOGGER.error("EKGameStrategyDaoImpl.updateGameStrategy failed, e : " + e);
            return 0;
        }
    }

}
