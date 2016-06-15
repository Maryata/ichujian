package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKGameInfoDao;
import com.org.mokey.basedata.sysinfo.dao.GameInfoDao;
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
 * 游戏帮：游戏资讯
 */
@Repository
public class EKGameInfoDaoImpl implements EKGameInfoDao {

    private static final Logger LOGGER = Logger.getLogger(EKGameInfoDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    // 查询资讯列表
    public List<Map<String, Object>> gameInfoList(String gid, String name, int page, String gName) {
        try {
            if (StrUtils.isNotEmpty(page)) {
                StringBuffer sb = new StringBuffer("SELECT T.C_ID ID,G.C_NAME GNAME," +
                        "T.C_NAME CNAME FROM T_EK_GAME_INFORMATION_INFO T, T_GAME_APP_INFO G" +
                        " WHERE T.C_ISLIVE = 1 AND G.C_ISLIVE = 1 AND T.C_GID = G.C_ID");
                List<Object> args = new ArrayList<Object>();
                if (StrUtils.isNotEmpty(gid)) {
                    sb.append(" AND T.C_GID = ?");
                    args.add(gid);
                }
                if (StrUtils.isEmpty(gid) && StrUtils.isNotEmpty(gName)) {
                    sb.append(" AND G.C_NAME LIKE ?");
                    args.add("'%" + gName + "%'");
                }
                if (StrUtils.isNotEmpty(name)) {
                    sb.append(" AND T.C_NAME LIKE ?");
                    args.add("'%" + name + "%'");
                }
                // 组织分页语句
                StringBuffer sql = DaoUtil.addfy_oracle(sb, "T.C_ORDER", (page - 1) * 10, 10, args);// 默认每页10条数据
                // 查询
                List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql.toString(), args.toArray());
                return list;
            }
        } catch (DataAccessException e) {
            LOGGER.error("EKGameInfoDaoImpl.getGameList failed, e : " + e);
        }
        return null;
    }

    @Override
    // 获取资讯总数
    public Integer getTotal(String gid, String name, String gName) {
        try {
            StringBuffer sb = new StringBuffer("SELECT COUNT(*) FROM " +
                    "T_EK_GAME_INFORMATION_INFO T, T_GAME_APP_INFO G WHERE T.C_ISLIVE = 1 " +
                    "AND G.C_ISLIVE = 1 AND T.C_GID = G.C_ID ");
            List<Object> args = new ArrayList<Object>();
            if (StrUtils.isNotEmpty(gid)) {
                sb.append(" AND T.C_GID = ?");
                args.add(gid);
            }
            if (StrUtils.isEmpty(gid) && StrUtils.isNotEmpty(gName)) {
                sb.append(" AND G.C_NAME LIKE ?");
                args.add("'%" + gName + "%'");
            }
            if (StrUtils.isNotEmpty(name)) {
                sb.append(" AND T.C_NAME LIKE ?");
                args.add("'%" + name + "%'");
            }
            return this.jdbcTemplate.queryForObject(sb.toString(), args.toArray(), Integer.class);
        } catch (Exception e) {
            LOGGER.error("EKGameInfoDaoImpl.getTotalCol failed, e : " + e);
        }
        return null;
    }

    @Override
    // 根据id删除游戏资讯
    public void deleteGameInfo(String id) {
        try {
            String sql = "DELETE FROM T_EK_GAME_INFORMATION_INFO WHERE C_ID = ?";
            this.jdbcTemplate.update(sql, new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKGameInfoDaoImpl.getTotalCol failed, e : " + e);
        }
    }

    @Override
    // 根据id查询资讯
    public List<Map<String, Object>> queryInfoById(String editId) {
        try {
            String sql = "SELECT * FROM T_EK_GAME_INFORMATION_INFO T WHERE T.C_ISLIVE = 1 AND T.C_ID = ?";
            return this.jdbcTemplate.queryForList(sql, new Object[]{editId});
        } catch (Exception e) {
            LOGGER.error("EKGameInfoDaoImpl.getTotalCol failed, e : " + e);
        }
        return null;
    }

    @Override
    // 保存游戏资讯
    public void saveGameInfo(List<Object> args) {
        try {
            String sql = "INSERT INTO T_EK_GAME_INFORMATION_INFO " +
                    "(C_ID, C_GID, C_NAME, C_DEPICT, C_DATE, C_ISLIVE, C_ORDER, C_SHAREURL , C_LOGOURL)" +
                    "VALUES(?,?,?,?,?,?,?,?,?)";
            this.jdbcTemplate.update(sql, args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKGameInfoDaoImpl.saveGameInfo failed, e : " + e);
        }
    }

    @Override
    // 更新游戏资讯
    public void updateGameInfo(List<Object> args) {
        try {
            String sql = "UPDATE T_EK_GAME_INFORMATION_INFO T SET T.C_GID = ?, T.C_NAME = ?, " +
                    "T.C_DEPICT = ?, T.C_DATE = ?, T.C_ISLIVE = ?, T.C_ORDER = ?, C_SHAREURL = ? , C_LOGOURL = ? WHERE T.C_ID = ?";
            this.jdbcTemplate.update(sql, args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKGameInfoDaoImpl.updateGameInfo failed, e : " + e);
        }
    }

    @Override
    // 浏览游戏资讯的人数--newId：资讯id，gid：游戏id
    public Integer scanedNum(String newId, String gid) {
        try {
            String sql = "SELECT COUNT(*) FROM T_EK_GAME_CHILD_ACTION T WHERE T.C_TYPE = 0 " +
                    "AND T.C_SOURCE = 1 AND T.C_INDEXID = ? AND T.C_GID = ?";
            return this.jdbcTemplate.queryForObject(sql, new Object[]{newId, gid}, Integer.class);
        } catch (Exception e) {
            LOGGER.error("EKGameInfoDaoImpl.scanedNum failed, e : " + e);
        }
        return null;
    }

    @Override
    // 查询id是否存在
    public boolean existId(String id) {
        try {
            String sql = "SELECT COUNT(*) FROM T_EK_GAME_INFORMATION_INFO T WHERE T.C_ID = ? ";
            Integer i = this.jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
            if (i == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("gameActDaoImpl.scanedNum failed, e : " + e);
        }
        return false;
    }
}
