package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKGameActDao;
import com.org.mokey.basedata.sysinfo.dao.GameActDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 游戏帮：游戏活动
 */
@Transactional(readOnly = false)
@Repository
public class EKGameActDaoImpl implements EKGameActDao {

    private static final Logger LOGGER = Logger.getLogger(EKGameActDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    // 查询活动列表
    public List<Map<String, Object>> gameActList(String gid, String name, int page, String gName) {
        try {
            if (StrUtils.isNotEmpty(page)) {
                StringBuffer sb = new StringBuffer("SELECT T.C_ID ID,G.C_NAME GNAME," +
                        "T.C_NAME CNAME FROM T_EK_GAME_ACTIVITY_INFO T, T_GAME_APP_INFO G" +
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
            LOGGER.error("EKGameActDaoImpl.gameActList failed, e : " + e);
        }
        return null;
    }

    @Override
    // 获取活动总数
    public Integer getTotal(String gid, String name, String gName) {
        try {
            StringBuffer sb = new StringBuffer("SELECT COUNT(*) FROM " +
                    "T_EK_GAME_ACTIVITY_INFO T, T_GAME_APP_INFO G WHERE T.C_ISLIVE = 1 " +
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
            LOGGER.error("EKGameActDaoImpl.getTotal failed, e : " + e);
        }
        return null;
    }

    @Override
    // 根据id删除游戏活动
    public void deleteGameAct(String id) {
        try {
            String sql = "DELETE FROM T_EK_GAME_ACTIVITY_INFO WHERE C_ID = ?";
            this.jdbcTemplate.update(sql, new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKGameActDaoImpl.deleteGameAct failed, e : " + e);
        }
    }

    @Override
    // 根据id查询活动
    public List<Map<String, Object>> queryActById(String editId) {
        try {
            String sql = "SELECT * FROM T_EK_GAME_ACTIVITY_INFO T WHERE T.C_ISLIVE = 1 AND T.C_ID = ?";
            return this.jdbcTemplate.queryForList(sql, new Object[]{editId});
        } catch (Exception e) {
            LOGGER.error("EKGameActDaoImpl.queryActById failed, e : " + e);
        }
        return null;
    }

    @Override
    // 保存游戏活动
    public void saveGameAct(List<Object> args) {
        try {
            String sql = "INSERT INTO T_EK_GAME_ACTIVITY_INFO " +
                    "(C_ID, C_GID, C_NAME, C_DEPICT, C_ISLIVE," +
                    " C_ORDER, C_SHAREURL, C_LOGOURL, C_SDATE," +
                    " C_EDATE, C_REWARD) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            this.jdbcTemplate.update(sql, args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKGameActDaoImpl.saveGameAct failed, e : " + e);
        }
    }

    @Override
    // 更新游戏活动
    public void updateGameAct(List<Object> args) {
        try {
            String sql = "UPDATE T_EK_GAME_ACTIVITY_INFO T SET" +
                    " T.C_GID = ?, T.C_NAME = ?, T.C_DEPICT = ?," +
                    " T.C_ISLIVE = ?, T.C_ORDER = ?, C_SHAREURL = ? ," +
                    " C_LOGOURL = ?, C_SDATE = ?, C_EDATE = ?, C_REWARD = ?" +
                    "  WHERE T.C_ID = ?";
            this.jdbcTemplate.update(sql, args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKGameActDaoImpl.updateGameAct failed, e : " + e);
        }
    }

    @Override
    // 浏览游戏活动的人数--newId：活动id，gid：游戏id
    public Integer scanedNum(String newId, String gid) {
        try {
            String sql = "SELECT COUNT(*) FROM T_EK_GAME_CHILD_ACTION T WHERE T.C_TYPE = 0 " +
                    "AND T.C_SOURCE = 1 AND T.C_INDEXID = ? AND T.C_GID = ?";
            return this.jdbcTemplate.queryForObject(sql, new Object[]{newId, gid}, Integer.class);
        } catch (Exception e) {
            LOGGER.error("EKGameActDaoImpl.scanedNum failed, e : " + e);
        }
        return null;
    }

    @Override
    // 查询id是否存在
    public boolean existId(String id) {
        try {
            String sql = "SELECT COUNT(*) FROM T_EK_GAME_ACTIVITY_INFO T WHERE T.C_ID = ? ";
            Integer i = this.jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
            if (i == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("EKGameActDaoImpl.scanedNum failed, e : " + e);
        }
        return false;
    }

    @Override
    public Map<String, Object> listUser(int start, int limit, int aid, int flag) {
        Map<String, Object> ret = new HashMap<String, Object>();

        StringBuilder sql = new StringBuilder("select count(1) from T_EK_GAME_MEMBER_ACTIVITY where c_aid=?");
        StringBuilder sql0 = new StringBuilder("select t1.c_nickname,t2.c_name,t.c_comment,t.c_uid,t.c_id,t.c_img from T_EK_GAME_MEMBER_ACTIVITY t left join T_EK_member_info t1 on t.c_uid=t1.c_id left join T_EK_GAME_activity_info t2 on t.c_aid=t2.c_id where t.c_aid=? and t1.c_state=1");
        if (0 == flag) {
            sql.append(" and c_score = 0 or c_score is null ");
            sql0.append(" and c_score = 0 or c_score is null ");
        } else {
            sql.append(" and c_score >= 1");
            sql0.append(" and c_score >= 1");
        }

        List<Object> argsList = new ArrayList<Object>();

        argsList.add(aid);

        int count = jdbcTemplate.queryForObject(sql.toString(), argsList
                .toArray(), Integer.class);

        String sql1 = DaoUtil.addfy_oracle(sql0, start, limit, argsList)
                .toString();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
        ret.put("count", count);
        ret.put("list", list);
        return ret;
    }

    @Override
    public void updateUser(Map<String, Object> map) throws Exception {
        String idName = "C_ID";
        String uid = String.valueOf(map.get("C_UID"));
        int tid = 11;
        String score = String.valueOf(map.get("C_SCORE"));
        String memberActivityId = (String) map.get(idName);
        String seqName = "seq_ek_game_member_task";
        Date currentDate = new Date();

        String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate,
                seqName);
        map.put(idName, newId);

        // 更新用户参与活动的表记录积分
        jdbcTemplate.update("update T_EK_GAME_member_activity set c_score=? where c_id=?", Integer.parseInt(score), memberActivityId);
        // 插入用户获取任务积分
        jdbcTemplate.update("insert into T_EK_GAME_member_task (c_id, c_uid, c_tid, c_count, c_date, c_strdate) values (?, ?, ?, ?,?,?)", newId, uid, tid, score, currentDate, String.valueOf(currentDate.getTime()));
        // 更新用户总积分
        jdbcTemplate.update("update T_EK_GAME_member_score set c_score = c_score + ? where c_uid = ?", score, uid);

    }
}
