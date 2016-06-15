package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKGameCateDao;
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
 * Created by vpc on 2016/3/14.
 * e键 ： 游戏 ： 游戏攻略分类
 */
@Repository
public class EKGameCateDaoImpl  implements EKGameCateDao{
    private static final Logger LOGGER = Logger.getLogger(EKGameCateDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    // 获取游戏分类（下拉菜单）
    public List<Map<String, Object>> getGameCateList() {
        try {
            String sql = "SELECT C_ID, C_NAME FROM T_EK_GAME_CATEGORY" +
                    " WHERE C_ISLIVE = 1 AND C_TYPE = '06' ORDER BY C_ORDER";
            return jdbcTemplate.queryForList(sql);
        } catch (DataAccessException e) {
            LOGGER.error("EKGameCateDaoImpl.getGameCateList failed, e : " + e);
        }
        return null;
    }

    @Override
    // 分页显示游戏分类
    public Map<String, Object> gameGiftCateList(int page) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
                    " FROM T_EK_GAME_CATEGORY WHERE C_ISLIVE = 1 AND C_TYPE = '06'");
            List<Object> argsList = new ArrayList<Object>();
            int count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);

            sql.append(" ORDER BY C_ORDER ");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList)
                    .toString().replace("COUNT(1)", "C_ID,C_NAME,C_LOGO,C_ORDER,C_MODIFIER");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKGameCateDaoImpl.gameGiftCateList failed, e : " + e);
        }
        return ret;
    }

    @Override
    // 新增游戏游戏分类
    public void addGameGiftCate(String id, String name, String logo,
                                String order, String modifier) {
        try {
            String sql = "INSERT INTO T_EK_GAME_CATEGORY" +
                    " (C_ID,C_NAME,C_ISLIVE,C_LOGO,C_ORDER,C_MODIFIER,C_TYPE)" +
                    " VALUES(?, ?, '1', ?, ?, ?, '06')";
            this.jdbcTemplate.update(sql, new Object[]{id, name, logo, order, modifier});
        } catch (Exception e) {
            LOGGER.error("EKGameCateDaoImpl.addGameGiftCate failed, e : " + e);
        }
    }

    @Override
    // 删除分类
    public void delGameGiftCate(String id) {
        try {
            String sql = "DELETE FROM T_EK_GAME_CATEGORY WHERE C_ID = ?";
            this.jdbcTemplate.update(sql, new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKGameCateDaoImpl.delGameGiftCate failed, e : " + e);
        }
    }

    @Override
    // 更新游戏游戏分类
    public void updateGameGiftCate(String id, String name, String logo,
                                   String order, String modifier) {
        try {
            String sql = "UPDATE T_EK_GAME_CATEGORY SET C_NAME = ?, C_LOGO = ?," +
                    " C_ORDER = ?, C_MODIFIER = ? WHERE C_ID = ?";
            this.jdbcTemplate.update(sql, new Object[]{name, logo, order, modifier, id});
        } catch (Exception e) {
            LOGGER.error("EKGameCateDaoImpl.delGameGiftCate failed, e : " + e);
        }
    }

    @Override
    // 查询非当前游戏分类的其他所有游戏
    public Map<String, Object> getAllGame(int page, String cid, String name,
                                          String gameCate) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM T_EK_GAME_STRATEGY_INFO T" +
                    " WHERE T.C_ISLIVE=1 AND NOT EXISTS(SELECT C.C_SID FROM T_EK_GAME_STRATEGY_CATEGORY C WHERE C.C_SID = T.C_ID AND C.C_CID=?)");
            List<Object> argsList = new ArrayList<Object>();
            argsList.add(cid);
            if (StrUtils.isNotEmpty(name)) {
                sql.append(" AND T.C_NAME LIKE ?");
                argsList.add("%" + name + "%");
            }
            if (StrUtils.isNotEmpty(gameCate)) {
                sql.append(" AND T.C_GID = ?");
                argsList.add(gameCate);
            }
            // 查询总数
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

            sql.append(" ORDER BY T.C_ID DESC ");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList)
                    .toString().replace("COUNT(1)", "T.C_ID, T.C_NAME");
            // 查询数据
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKGameCateDaoImpl.getAllGame failed, e : " + e);
        }
        return ret;
    }

    @Override
    // 查询当前游戏分类的游戏
    public Map<String, Object> getCurrCateGame(int page, String cid, String name) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1)  FROM" +
                    " T_EK_GAME_STRATEGY_INFO T ,T_EK_GAME_STRATEGY_CATEGORY C" +
                    " WHERE T.C_ISLIVE=1 AND T.C_ID = C.C_SID AND C.C_CID=?");
            List<Object> argsList = new ArrayList<Object>();
            argsList.add(cid);
            if (StrUtils.isNotEmpty(name)) {
                sql.append(" AND T.C_NAME LIKE ?");
                argsList.add("%" + name + "%");
            }
            // 查询总数
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

            sql.append(" ORDER BY C.C_ORDER");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList)
                    .toString().replace("COUNT(1)", "C.C_SID ,C.C_ORDER,T.C_NAME");
            // 查询数据
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKGameCateDaoImpl.getCurrCateGift failed, e : " + e);
        }
        return ret;
    }

    @Override
    // 查询游戏是否已经存在于当前分类
    public int isExist(String cid, String sid) {
        try {
            String sql = "SELECT COUNT(1) FROM T_EK_GAME_STRATEGY_CATEGORY " +
                    " WHERE C_CID = ? AND C_SID = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{cid, sid}, Integer.class);
        } catch (Exception e) {
            LOGGER.error("EKGameCateDaoImpl.isExist failed, e : " + e);
        }
        return 0;
    }

    @Override
    // 新增游戏到当前分类
    public void add(String cid, String sid, String order) {
        try {
            String sql = "INSERT INTO T_EK_GAME_STRATEGY_CATEGORY" +
                    " (C_ID, C_SID, C_CID, C_ORDER) VALUES" +
                    " (SEQ_EK_GAME_STRATEGY_CATE.NEXTVAL,?,?,?)";
            jdbcTemplate.update(sql, new Object[]{sid, cid, order});
        } catch (Exception e) {
            LOGGER.error("EKGameCateDaoImpl.add failed, e : " + e);
        }
    }

    @Override
    // 更新当前分类中的游戏
    public void update(String cid, String sid, String order) {
        try {
            String sql = "UPDATE T_EK_GAME_STRATEGY_CATEGORY SET" +
                    " C_ORDER = ? WHERE C_CID = ? AND C_SID = ?";
            jdbcTemplate.update(sql, new Object[]{order, cid, sid});
        } catch (Exception e) {
            LOGGER.error("EKGameCateDaoImpl.update failed, e : " + e);
        }
    }

    @Override
    // 将指定游戏移除出当前分类
    public void remove(String cid, String sid) {
        try {
            String sql = "DELETE FROM T_EK_GAME_STRATEGY_CATEGORY" +
                    " WHERE C_CID = ? AND C_SID = ?";
            jdbcTemplate.update(sql, new Object[]{cid, sid});
        } catch (Exception e) {
            LOGGER.error("EKGameCateDaoImpl.remove failed, e : " + e);
        }
    }
    
    
}
