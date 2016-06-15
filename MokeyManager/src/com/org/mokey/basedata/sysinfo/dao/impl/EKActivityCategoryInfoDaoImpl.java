package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKActivityCategoryInfoDao;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/3.
 * e键 ：活动分类
 */
public class EKActivityCategoryInfoDaoImpl implements EKActivityCategoryInfoDao {

    private static final Logger LOGGER = Logger.getLogger(EKActivityCategoryInfoDaoImpl.class);
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<String, Object> ekActivityCategoryInfoList(int page) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1)" + " FROM T_EK_ACTIVITY_CATEGORY_INFO WHERE C_ISLIVE = 1");
            List<Object> argsList = new ArrayList<Object>();
            int count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);

            sql.append(" ORDER BY C_ID ");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList).toString().replace("COUNT(1)", "C_ID,C_NAME,C_IMG,C_TYPE,CASE WHEN C_TYPE = '0' THEN '合集' ELSE '分类' END AS NAME,C_IMG_HEADLINE");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.ekActivityCategoryInfoList failed, e : " + e);
        }
        return ret;
    }

    @Override
    public void addInfo(String id, String name, String logo, String type, String headLineImg) {
        try {
            String sql = "INSERT INTO T_EK_ACTIVITY_CATEGORY_INFO" +
                    " (C_ID,C_NAME,C_ISLIVE,C_IMG,C_TYPE,C_IMG_HEADLINE)" +
                    " VALUES(?, ?, '1', ?, ?,?)";
            this.jdbcTemplate.update(sql, new Object[]{id, name, logo, type, headLineImg});
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.addGameGiftCate failed, e : " + e);
        }
    }

    @Override
    public void toDel(String id) {
        try {
            String sql = "DELETE FROM T_EK_ACTIVITY_CATEGORY_INFO WHERE C_ID = ?";
            this.jdbcTemplate.update(sql, new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.toDel failed, e : " + e);
        }
    }

    @Override
    public void updateInfo(String id, String name, String logo, String type, String headLineImg) {
        try {
            String sql = "UPDATE T_EK_ACTIVITY_CATEGORY_INFO SET C_NAME = ?, C_IMG = ?, C_TYPE = ?,C_IMG_HEADLINE=? WHERE C_ID = ?";
            this.jdbcTemplate.update(sql, new Object[]{name, logo, type, headLineImg, id});
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.updateInfo failed, e : " + e);
        }
    }

    @Override
    public List<Map<String, Object>> getAllCaList() {
        try {
            String sql = "SELECT C_ID, C_NAME ,C_TYPE  FROM T_EK_ACTIVITY_CATEGORY_INFO " + " WHERE C_ISLIVE = 1";
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.getAllCaList failed, e : " + e);
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> activityCategoryList(String type) {
        List<Object> argsList = new ArrayList<Object>();
        try {
            String sql = "SELECT C_ID,C_NAME  FROM T_EK_ACTIVITY_CATEGORY_INFO WHERE C_ISLIVE = 1 "; // AND C_TYPE=?
            //argsList.add(type);
            return jdbcTemplate.queryForList(sql);
//            return jdbcTemplate.queryForList(sql,argsList.toArray());
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.activityCategoryList failed, e : " + e);
        }
        return null;

    }

    @Override

    public Map<String, Object> getAllActivityCategoryInfo(int page, String cid, String title, String ccid, String imgType) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
                    " FROM T_EK_ACTIVITY_INFO T WHERE T.C_ISLIVE = 1 AND T.C_AUDIT_STATUS = 1" +
                    " AND NOT EXISTS(SELECT C.C_AID FROM T_EK_ACTIVITY_CATEGORY_ACTIVIT C WHERE T.C_ID = C.C_AID AND C.C_CID=?)");
            List<Object> argsList = new ArrayList<Object>();
            argsList.add(cid);
            if (StrUtils.isNotEmpty(title)) {
                sql.append(" AND T.C_TITLE" + " LIKE ?");
                argsList.add("%" + title + "%");
            }

            if (StrUtils.isNotEmpty(ccid)) {
                sql.append(" AND T.C_CID =?");
                argsList.add(ccid);
            }

            if (StrUtils.isNotEmpty(imgType)) {
                if ("1".equals(imgType)) {
                    sql.append(" AND T.C_IMG_LONG IS NOT NULL");
                } else if ("2".equals(imgType)) {
                    sql.append(" AND T.C_IMG_THIN IS NOT NULL");
                } else if ("3".equals(imgType)) {
                    sql.append(" AND T.C_IMG_INDEX_LONG IS NOT NULL");
                }
            }

            // 查询总数
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

            sql.append(" ORDER BY T.C_ID DESC");
            String sql1 = DaoUtil.addfy_oracle( sql, (page-1)*5, 5, argsList )
                    .toString().replace( "COUNT(1)", "T.C_ID ,T.C_CID,T.C_TITLE,T.C_IMG_LONG,T.C_IMG_THIN, T.C_EDATE, T.C_IMG_INDEX_LONG" );
            // 查询数据
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            overOrNot(list);
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.getAllGame failed, e : " + e);
        }
        return ret;
    }

    @Override
    public Map<String, Object> getCurrActivityCaInfo(int page, String cid, String title) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
                    " FROM T_EK_ACTIVITY_CATEGORY_ACTIVIT T,T_EK_ACTIVITY_INFO TC" +
                    " WHERE TC.C_ISLIVE = 1 AND TC.C_AUDIT_STATUS = 1 AND T.C_CID=? AND TC.C_ID = T.C_AID");
            List<Object> argsList = new ArrayList<Object>();
            argsList.add(cid);
            if (StrUtils.isNotEmpty(title)) {
                sql.append(" AND TC.C_TITLE LIKE ?");
                argsList.add("%" + title + "%");
            }
            // 查询总数
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

            sql.append(" ORDER BY T.C_ORDER, T.C_CTRLORDER, T.C_AID");

            String sql1 = DaoUtil.addfy_oracle( sql, (page-1)*5, 5, argsList ).toString().replace( "COUNT(1)",
                    "T.C_ID,T.C_AID ,TC.C_TITLE ,T.C_ORDER,T.C_CID,T.C_TAGID, TC.C_IMG_LONG, TC.C_IMG_THIN," +
                            "T.C_TITLE CTITLE, T.C_SUBTITLE CSUBTITLE, TC.C_EDATE, TC.C_IMG_INDEX_LONG" );
            // 查询数据
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            overOrNot(list);
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.getCurrActivityCaInfo failed, e : " + e);
        }
        return ret;
    }

    private void overOrNot(List<Map<String, Object>> list) {
        if (StrUtils.isNotEmpty(list)) {
            for (Map<String, Object> map : list) {
                String edate = StrUtils.emptyOrString(map.get("C_EDATE"));
                long endTimeLong = 0L;
                try {
                    endTimeLong = ApDateTime.getDateTimeS(edate.substring(0, 19));
                } catch (Exception e) {
                    LOGGER.error("transform [time] from String to Long failed, [time] : [" + edate + "]");
                }
                long nowTimeLong = System.currentTimeMillis();
                long oneDayMillisSec = 24 * 60 * 60 * 1000L;
                long timesDifferent = endTimeLong - nowTimeLong;
                String color, content;
                if (timesDifferent <= 0) {
                    color = "red";
                    content = "是";
                } else if (timesDifferent <= oneDayMillisSec) {
                    color = "red";
                    content = "即将过期";
                } else {
                    color = "green";
                    content = "否";
                }
                String show = "<div style='color : " + color + "'>" + content + "</div>";
                map.put("isOver", show);
            }
        }
    }

    @Override
    public void addIndex(String cid, String aid, String order, String tagid, String title, String subtitle) {
        try {
            String sql = "INSERT INTO T_EK_ACTIVITY_CATEGORY_ACTIVIT" +
                    " (C_ID, C_AID, C_CID, C_ORDER,C_TAGID,C_TITLE,C_SUBTITLE) VALUES" +
                    " (SEQ_EK_ACTIVITY_CATE_ACT.NEXTVAL,?,?,?,?,?,?)";
            jdbcTemplate.update(sql, new Object[]{aid, cid, order, tagid, title, subtitle});
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.add failed, e : " + e);
        }
    }

    @Override
    public void updateIndex(String cid, String aid, String order, String tagid, String title, String subtitle) {
        try {
            String sql = "UPDATE T_EK_ACTIVITY_CATEGORY_ACTIVIT SET  C_ORDER = ?," +
                    " C_TAGID = ?, C_TITLE = ?, C_SUBTITLE = ? WHERE C_CID = ? AND C_AID = ?";
            jdbcTemplate.update(sql, new Object[]{order, tagid, title, subtitle, cid, aid});
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.update failed, e : " + e);
        }
    }

    @Override
    public void removeIndex(String cid, String id) {
        try {
            String sql = "DELETE FROM T_EK_ACTIVITY_CATEGORY_ACTIVIT" + " WHERE C_CID = ? AND C_AID = ?";
            jdbcTemplate.update(sql, new Object[]{cid, id});
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.remove failed, e : " + e);
        }
    }

    @Override
    public int isExitActivity(String cid, String aid) {
        try {
            String sql = "SELECT COUNT(1) FROM T_EK_ACTIVITY_CATEGORY_ACTIVIT " + " WHERE C_CID = ? AND C_AID = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{cid, aid}, Integer.class);
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.isExist failed, e : " + e);
        }
        return 0;
    }

    @Override
    public Map<String, Object> ekActivityCategoryList(int page, List ids) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM T_EK_ACTIVITY_CATEGORY_INFO T" +
                    " WHERE T.C_ISLIVE = 1 AND T.C_TYPE = '1'" +
                    " AND NOT EXISTS(SELECT TC.C_EID FROM T_EK_ACTIVITY_HEADLINE TC WHERE  TC.C_EID = T.C_ID AND TC.C_TYPE='0')");
            List<Object> argsList = new ArrayList<Object>();
            if (StrUtils.isNotEmpty(ids)) {
                sql.append(" AND  T.C_ID NOT IN" + DaoUtil.buildInCaseAsQ(ids, true));
                argsList.addAll(ids);
            }
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

            sql.append(" ORDER BY T.C_ID DESC");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList).toString().replace("COUNT(1)", "T.C_ID,T.C_NAME,T.C_IMG,T.C_TYPE");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.ekActivityCategoryInfoList failed, e : " + e);
        }
        return ret;
    }

    @Override
    public Map<String, Object> ekActivityInfoList(int page, List ids) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM T_EK_ACTIVITY_CATEGORY_INFO T" +
                    " WHERE T.C_ISLIVE = 1 AND T.C_TYPE = '1'" +
                    " AND NOT EXISTS(SELECT TC.C_CID FROM T_EK_ACTIVITY_INDEX_CATEGORY TC WHERE  TC.C_CID = T.C_ID)");
            List<Object> argsList = new ArrayList<Object>();
            if (StrUtils.isNotEmpty(ids)) {
                sql.append(" AND  T.C_ID NOT IN" + DaoUtil.buildInCaseAsQ(ids, true));
                argsList.addAll(ids);
            }
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

            sql.append(" ORDER BY T.C_ID DESC");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, argsList).toString().replace("COUNT(1)", "T.C_ID,T.C_NAME,T.C_IMG,T.C_TYPE");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.ekActivityInfoList failed, e : " + e);
        }
        return ret;
    }

    /**
     * 删除活动分类  ：    查询活动头条中是否存在
     *
     * @param id
     * @return
     */
    @Override
    public int isExitHeadLine(String id) {
        try {
            String sql = "SELECT COUNT(1) FROM T_EK_ACTIVITY_HEADLINE " + " WHERE C_EID = ? AND C_TYPE = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id, 0}, Integer.class);
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.isExitHeadLine failed, e : " + e);
        }
        return 0;
    }

    /**
     * 删除活动头条中相关的信息
     *
     * @param id
     */
    @Override
    public void removeHeadLine(String id) {
        try {
            String sql = "DELETE FROM T_EK_ACTIVITY_HEADLINE" + " WHERE C_EID = ? AND C_TYPE = ?";
            jdbcTemplate.update(sql, new Object[]{id, 0});
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.removeHeadLine failed, e : " + e);
        }
    }

    /**
     * 删除首页活动分类  ：    查询首页活动分类中是否存在
     *
     * @param id
     * @return
     */
    @Override
    public int isExitActivityIndexCate(String id) {
        try {
            String sql = "SELECT COUNT(1) FROM T_EK_ACTIVITY_INDEX_CATEGORY " + " WHERE C_CID = ? ";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.isExitActivityIndexCate failed, e : " + e);
        }
        return 0;
    }

    /**
     * 删除首页活动分类中相关的信息
     *
     * @param id
     */
    @Override
    public void removeActivityIndexCate(String id) {
        try {
            String sql = "DELETE FROM T_EK_ACTIVITY_INDEX_CATEGORY" + " WHERE C_CID = ?";
            jdbcTemplate.update(sql, new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.removeActivityIndexCate failed, e : " + e);
        }
    }

    @Override
    // 删除“分类活动中间表”T_EK_ACTIVITY_CATEGORY_ACTIVIT表中当前分类的所有数据
    public void removeActByCid(String id) {
        try {
            String sql = "DELETE FROM T_EK_ACTIVITY_CATEGORY_ACTIVIT WHERE C_CID = ?";
            jdbcTemplate.update(sql, new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKActivityCategoryInfoDaoImpl.removeActByCid failed, e : " + e);
        }
    }


}
