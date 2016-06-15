package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKGameGiftCateDao;
import com.org.mokey.basedata.sysinfo.dao.GameGiftCateDao;
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
 * 游戏帮：游戏礼包分类
 */
@Repository
public class EKGameGiftCateDaoImpl implements EKGameGiftCateDao {

    private static final Logger LOGGER = Logger.getLogger(EKGameGiftCateDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    // 分页显示礼包分类
    public Map<String, Object> gameGiftCateList(int page) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
                    " FROM T_EK_GAME_CATEGORY WHERE C_ISLIVE = 1 AND C_TYPE = '03'");
            List<Object> argsList = new ArrayList<Object>();
            int count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);

            sql.append(" ORDER BY C_ORDER ");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList)
                    .toString().replace("COUNT(1)", "C_ID,C_NAME,C_LOGO,C_ORDER,C_MODIFIER");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKGameGiftCateDaoImpl.gameGiftCateList failed, e : " + e);
        }
        return ret;
    }

    @Override
    // 新增游戏礼包分类
    public void addGameGiftCate(String id, String name, String logo,
                                String order, String modifier) {
        try {
            String sql = "INSERT INTO T_EK_GAME_CATEGORY" +
                    " (C_ID,C_NAME,C_ISLIVE,C_LOGO,C_ORDER,C_MODIFIER,C_TYPE)" +
                    " VALUES(?, ?, '1', ?, ?, ?, '03')";
            this.jdbcTemplate.update(sql, new Object[]{id, name, logo, order, modifier});
        } catch (Exception e) {
            LOGGER.error("EKGameGiftCateDaoImpl.addGameGiftCate failed, e : " + e);
        }
    }

    @Override
    // 删除分类
    public void delGameGiftCate(String id) {
        try {
            String sql = "DELETE FROM T_EK_GAME_CATEGORY WHERE C_ID = ?";
            this.jdbcTemplate.update(sql, new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKGameGiftCateDaoImpl.delGameGiftCate failed, e : " + e);
        }
    }

    @Override
    // 更新游戏礼包分类
    public void updateGameGiftCate(String id, String name, String logo,
                                   String order, String modifier) {
        try {
            String sql = "UPDATE T_EK_GAME_CATEGORY SET C_NAME = ?, C_LOGO = ?," +
                    " C_ORDER = ?, C_MODIFIER = ? WHERE C_ID = ?";
            this.jdbcTemplate.update(sql, new Object[]{name, logo, order, modifier, id});
        } catch (Exception e) {
            LOGGER.error("EKGameGiftCateDaoImpl.delGameGiftCate failed, e : " + e);
        }
    }

    @Override
    // 获取非当前分类的所有礼包
    public Map<String, Object> getAllGameGift(int page, String cid, String name, String giftCate) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
                    " FROM T_EK_GAME_GIFTS_INFO T WHERE T.C_ISLIVE = 1" +
                    " AND SYSDATE < T.C_EDATE AND NOT EXISTS" +
                    " (SELECT C.C_GID FROM T_EK_GAME_GIFTS_CATEGORY C WHERE" +
                    " T.C_ID = C.C_GID AND C.C_CID = ?)");
            List<Object> argsList = new ArrayList<Object>();
            argsList.add(cid);
            if (StrUtils.isNotEmpty(name)) {
                sql.append(" AND T.C_NAME LIKE ?");
                argsList.add("%" + name + "%");
            }
            if (StrUtils.isNotEmpty(giftCate)) {
                sql.append(" AND T.C_TYPE = ?");
                argsList.add(giftCate);
            }
            // 查询总数
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

            sql.append(" ORDER BY C_ORDER ");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList)
                    .toString().replace("COUNT(1)", "C_ID, C_NAME");
            // 查询数据
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKGameGiftCateDaoImpl.getAllGameGift failed, e : " + e);
        }
        return ret;
    }

    @Override
    // 查询当前礼包分类的礼包
    public Map<String, Object> getCurrCateGift(int page, String cid, String name) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
                    " FROM T_EK_GAME_GIFTS_CATEGORY T,T_EK_GAME_GIFTS_INFO G WHERE " +
                    " G.C_ISLIVE = 1 AND T.C_GID = G.C_ID AND T.C_CID = ?");
            List<Object> argsList = new ArrayList<Object>();
            argsList.add(cid);
            if (StrUtils.isNotEmpty(name)) {
                sql.append(" AND C_NAME LIKE ?");
                argsList.add("%" + name + "%");
            }
            // 查询总数
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

            sql.append(" ORDER BY T.C_ORDER");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList)
                    .toString().replace("COUNT(1)", "G.C_ID, G.C_NAME, T.C_ORDER ");
            // 查询数据
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKGameGiftCateDaoImpl.getCurrCateGift failed, e : " + e);
        }
        return ret;
    }

    @Override
    // 所有礼包分类
    public List<Map<String, Object>> getGiftCateList() {
        try {
            String sql = "SELECT C_ID, C_NAME FROM T_EK_GAME_CATEGORY " +
                    " WHERE C_ISLIVE = 1 AND C_TYPE = '03'";
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            LOGGER.error("EKGameGiftCateDaoImpl.getGiftCateList failed, e : " + e);
        }
        return null;
    }

    @Override
    // 查询礼包是否已经存在于当前分类
    public int isExist(String cid, String gid) {
        try {
            String sql = "SELECT COUNT(1) FROM T_EK_GAME_GIFTS_CATEGORY " +
                    " WHERE C_CID = ? AND C_GID = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{cid, gid}, Integer.class);
        } catch (Exception e) {
            LOGGER.error("EKGameGiftCateDaoImpl.isExist failed, e : " + e);
        }
        return 0;
    }

    @Override
    // 新增礼包到当前分类
    public void add(String cid, String gid, String order) {
        try {
            String sql = "INSERT INTO T_EK_GAME_GIFTS_CATEGORY" +
                    " (C_ID, C_GID, C_CID, C_ORDER) VALUES" +
                    " (SEQ_EK_GAME_GIFTS_CATEGORY.NEXTVAL,?,?,?)";
            jdbcTemplate.update(sql, new Object[]{gid, cid, order});
        } catch (Exception e) {
            LOGGER.error("EKGameGiftCateDaoImpl.add failed, e : " + e);
        }
    }

    @Override
    // 更新当前分类中的礼包
    public void update(String cid, String gid, String order) {
        try {
            String sql = "UPDATE T_EK_GAME_GIFTS_CATEGORY SET" +
                    " C_ORDER = ? WHERE C_CID = ? AND C_GID = ?";
            jdbcTemplate.update(sql, new Object[]{order, cid, gid});
        } catch (Exception e) {
            LOGGER.error("EKGameGiftCateDaoImpl.update failed, e : " + e);
        }
    }

    @Override
    // 将指定礼包移除出当前分类
    public void remove(String cid, String gid) {
        try {
            String sql = "DELETE FROM T_EK_GAME_GIFTS_CATEGORY" +
                    " WHERE C_CID = ? AND C_GID = ?";
            jdbcTemplate.update(sql, new Object[]{cid, gid});
        } catch (Exception e) {
            LOGGER.error("EKGameGiftCateDaoImpl.remove failed, e : " + e);
        }
    }


}
