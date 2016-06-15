package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKGameGiftDao;
import com.org.mokey.basedata.sysinfo.dao.GameGiftDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏礼包
 */
@Repository
public class EKGameGiftDaoImpl implements EKGameGiftDao {

    private static final Logger LOGGER = Logger.getLogger(EKGameGiftDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    // 查询礼包列表
    public List<Map<String, Object>> gameGiftList(String gid, String name, int page, String gName) {
        try {
            if (StrUtils.isNotEmpty(page)) {
                StringBuffer sb = new StringBuffer("SELECT T.C_ID ID,T.C_NAME CNAME,G.C_NAME GNAME," +
                        "TO_CHAR(T.C_SDATE,'YYYY-MM-DD') SDATE,TO_CHAR(T.C_EDATE,'YYYY-MM-DD') EDATE," +
                        "T.C_COUNT COUNT FROM T_EK_GAME_GIFTS_INFO T,T_GAME_APP_INFO G " +
                        "WHERE T.C_ISLIVE = 1 AND G.C_ISLIVE = 1 AND T.C_GID = G.C_ID");
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
            LOGGER.error("EKGameGiftDaoImpl.gameGiftList failed, e : " + e);
        }
        return null;
    }

    @Override
    // 获取礼包总数
    public Integer getTotal(String gid, String name, String gName) {
        try {
            StringBuffer sb = new StringBuffer("SELECT COUNT(*) FROM " +
                    "T_EK_GAME_GIFTS_INFO T, T_GAME_APP_INFO G WHERE T.C_ISLIVE = 1 " +
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
            LOGGER.error("EKGameGiftDaoImpl.getTotalCol failed, e : " + e);
        }
        return null;
    }


    @Override
    // 删除礼包码
    public void deleteGameGiftCode(String id) {
        try {
            String sql = "DELETE FROM T_EK_GAME_GIFTS_CODE_INFO WHERE C_GID = ?";
            this.jdbcTemplate.update(sql, new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKGameGiftDaoImpl.deleteGameGiftCode failed, e : " + e);
        }
    }

    @Override
    // 根据id删除游戏礼包
    public void deleteGameGift(String id) {
        try {
            String sql = "DELETE FROM T_EK_GAME_GIFTS_INFO WHERE C_ID = ?";
            this.jdbcTemplate.update(sql, new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKGameGiftDaoImpl.getTotalCol failed, e : " + e);
        }
    }

    @Override
    // 根据id查询礼包
    public List<Map<String, Object>> queryGiftById(String editId) {
        try {
            String sql = "SELECT T.C_ID ID,T.C_GID GID,T.C_NAME NAME,T.C_DEPICT DEPICT," +
                    "T.C_METHOD CMETHOD,TO_CHAR(T.C_SDATE,'YYYY-MM-DD hh24:mi:ss') SDATE," +
                    "TO_CHAR(T.C_EDATE,'YYYY-MM-DD hh24:mi:ss') EDATE,T.C_COUNT CCOUNT,T.C_ORDER CORDER " +
                    ", T.C_TYPE CATE FROM T_EK_GAME_GIFTS_INFO T WHERE T.C_ISLIVE = 1 AND T.C_ID = ?";
            return this.jdbcTemplate.queryForList(sql, new Object[]{editId});
        } catch (Exception e) {
            LOGGER.error("EKGameGiftDaoImpl.getTotalCol failed, e : " + e);
        }
        return null;
    }

    @Override
    // 保存游戏礼包
    public void saveGameGift(List<Object> args) {
        try {
            String sql = "INSERT INTO T_EK_GAME_GIFTS_INFO " +
                    "(C_ID, C_GID, C_NAME, C_DEPICT, C_SDATE, C_EDATE," +
                    " C_METHOD, C_COUNT, C_ISLIVE, C_ORDER, C_TYPE )" +
                    "VALUES(?,?,?,?,?,?,?,?,1,?,?)";
            this.jdbcTemplate.update(sql, args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKGameGiftDaoImpl.saveGameGift failed, e : " + e);
        }
    }

    @Override
    // 更新游戏礼包
    public void updateGameGift(List<Object> args) {
        try {
            String sql = "UPDATE T_EK_GAME_GIFTS_INFO T SET T.C_GID = ?, T.C_NAME = ?, " +
                    "T.C_DEPICT = ?, T.C_SDATE = ?, T.C_EDATE = ?, T.C_METHOD = ?, " +
                    "T.C_COUNT = ?, T.C_ORDER = ?, T.C_TYPE = ? WHERE T.C_ID = ?";
            this.jdbcTemplate.update(sql, args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKGameGiftDaoImpl.updateGameGift failed, e : " + e);
        }
    }

    @Override
    // 浏览游戏礼包的人数--newId：礼包id，gid：游戏id
    public Integer scanedNum(String newId, String gid) {
        try {
            String sql = "SELECT COUNT(*) FROM T_EK_GAME_CHILD_ACTION T WHERE T.C_TYPE = 0 " +
                    "AND T.C_SOURCE = 1 AND T.C_INDEXID = ? AND T.C_GID = ?";
            return this.jdbcTemplate.queryForObject(sql, new Object[]{newId, gid}, Integer.class);
        } catch (Exception e) {
            LOGGER.error("EKGameGiftDaoImpl.scanedNum failed, e : " + e);
        }
        return null;
    }

    @Override
    // 查询id是否存在
    public boolean existId(String id) {
        try {
            String sql = "SELECT COUNT(*) FROM T_EK_GAME_GIFTS_INFO T WHERE T.C_ID = ? ";
            Integer i = this.jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
            if (i == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("EKGameGiftDaoImpl.scanedNum failed, e : " + e);
        }
        return false;
    }

    @Override
    // 保存上传文件中的礼包码
    public void upload(List<Object[]> list) {
        try {
            if (StrUtils.isNotEmpty(list)) {
//				LOGGER.info("into EKGameGiftDaoImpl.scanedNum");
//				LOGGER.info("Counts of codes is : " + list.size());
                String sql = "INSERT INTO T_EK_GAME_GIFTS_CODE_INFO (C_ID, C_GID, C_CODE, C_ISLIVE, C_STATE)" +
                        "VALUES(?, ?, ?, ?, ?)";
                this.jdbcTemplate.batchUpdate(sql, list);
            }
        } catch (Exception e) {
            LOGGER.error("EKGameGiftDaoImpl.updateGameGift failed, e : " + e);
        }
    }

    @Override
    // 根据礼包id查询相关礼包码
    public Map<String, Object> getCodesListByGid(int page, String gid,
                                                 String state, String sDate, String eDate) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
                    " FROM ( SELECT T.C_ID, T.C_CODE, T.C_SDATE," +
                    " T.C_EDATE, T.C_STATE FROM T_EK_GAME_GIFTS_CODE_INFO T" +
                    " WHERE T.C_ISLIVE = 1 AND T.C_GID = ?");
            List<Object> argsList = new ArrayList<Object>();
            argsList.add(gid);
            if (StrUtils.isNotEmpty(state)) {
                argsList.add(state);
                sql.append(" AND T.C_STATE = ?");
            }
            if (StrUtils.isNotEmpty(sDate)) {
                argsList.add(sDate);
                sql.append(" AND T.C_SDATE > TO_DATE(?,'yyyy-MM-dd')");
            }
            if (StrUtils.isNotEmpty(eDate)) {
                argsList.add(eDate);
                sql.append(" AND T.C_EDATE < TO_DATE(?,'yyyy-MM-dd')+1");
            }
            sql.append(") TEMP1" +
                    " LEFT JOIN (SELECT MG.C_CODE,M.C_ID USERID,M.C_NICKNAME" +
                    " UNAME FROM T_EK_GAME_MEMBER_GIFTS MG, T_EK_MEMBER_INFO M" +
                    " WHERE M.C_ISLIVE = 1 AND MG.C_TYPE = 1 AND MG.C_UID = M.C_ID" +
                    " AND M.C_STATE = 1) TEMP2 ON TEMP1.C_CODE = TEMP2.C_CODE");
            // 查询总数
            int count = jdbcTemplate.queryForObject(sql.toString(),
                    argsList.toArray(), Integer.class);

            sql.append(" ORDER BY TEMP1.C_CODE ");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 10, 10, argsList).toString()
                    .replace(" COUNT(1) ", " C_ID, TEMP1.C_CODE C_CODE," +
                            " C_SDATE, C_EDATE, C_STATE, USERID, UNAME ");
            // 查询数据
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1,
                    argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKGameGiftDaoImpl.getCodesListByGid failed, e : " + e);
        }
        return ret;
    }

    @Override
    // 删除礼包码
    public void delCode(String gid, String id) {
        try {
            String sql = "DELETE FROM T_EK_GAME_GIFTS_CODE_INFO WHERE C_GID = ? AND C_ID = ?";
            this.jdbcTemplate.update(sql, new Object[]{gid, id});
        } catch (Exception e) {
            LOGGER.error("EKGameGiftDaoImpl.delCode failed, e : " + e);
        }
    }

    @Override
    // 批量删除礼包码
    public void batchDel(List<Object[]> list) {
        try {
            String sql = "DELETE FROM T_EK_GAME_GIFTS_CODE_INFO WHERE C_GID = ? AND C_ID = ?";
            this.jdbcTemplate.batchUpdate(sql, list);
        } catch (Exception e) {
            LOGGER.error("EKGameGiftDaoImpl.batchDel failed, e : " + e);
        }
    }

    @Override
    // 按“领取状态”查询所有指定礼包的礼包码
    public List<Map<String, Object>> getCodesByGidNState(String gid,
                                                         String state) {
        try {
            String sql = "SELECT TEMP1.C_CODE C_CODE, USERID, UNAME" +
                    " FROM (SELECT T.C_CODE FROM T_EK_GAME_GIFTS_CODE_INFO T" +
                    " WHERE T.C_ISLIVE = 1 AND T.C_GID = ? AND T.C_STATE = ?)" +
                    " TEMP1 LEFT JOIN (SELECT MG.C_CODE, M.C_ID USERID," +
                    " M.C_NICKNAME UNAME FROM T_EK_GAME_MEMBER_GIFTS MG," +
                    " T_EK_MEMBER_INFO M WHERE M.C_ISLIVE = 1 AND MG.C_TYPE = 1" +
                    " AND MG.C_UID = M.C_ID AND M.C_STATE = 1) TEMP2" +
                    " ON TEMP1.C_CODE = TEMP2.C_CODE";
            return this.jdbcTemplate.queryForList(sql, new Object[]{gid, state});
        } catch (Exception e) {
            LOGGER.error("EKGameGiftDaoImpl.getCodesByGidNState failed, e : " + e);
        }
        return null;
    }

}
