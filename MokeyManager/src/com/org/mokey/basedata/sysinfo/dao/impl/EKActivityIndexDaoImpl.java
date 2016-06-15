package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKActivityIndexDao;
import com.org.mokey.common.util.JdbcTemplateUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/7.
 * e键 ：  活动首页
 */
public class EKActivityIndexDaoImpl implements EKActivityIndexDao{
    private static final Logger LOGGER = Logger .getLogger(EKActivityIndexDaoImpl.class);
    private JdbcTemplate jdbcTemplate;

    public static Logger getLOGGER() {
        return LOGGER;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 查询 活动头条  原有数据
     * @return
     */
    @Override
    public List<Map<String, Object>> getoldheadLineList() {
        String sql="";
        try {
            sql = "SELECT C_ID,C_EID,C_TYPE,C_IMG,C_NAME,C_ORDER FROM T_EK_ACTIVITY_HEADLINE ORDER BY C_ORDER";
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            LOGGER.error("EKActivityIndexDaoImpl.getoldheadLineList failed, e : " + e);
        }
        return null;
    }

    /**
     *  活动头条  多余数据
     * @param id
     */
    @Override
    public void deleteHeadLine(String id) {
        try {
            String sql = "DELETE FROM T_EK_ACTIVITY_HEADLINE WHERE C_ID = ?";
            this.jdbcTemplate.update(sql,new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKActivityIndexDaoImpl.deleteHeadLine failed, e : " + e);
        }
    }

    /**
     * 活动头条 ： 添加
     * @param eid
     * @param name
     * @param type
     * @param logo
     * @param order
     */
    @Override
    public void addHeadLine(String eid, String name, String type, String logo, String order) {
        try {
            String sql = "INSERT INTO T_EK_ACTIVITY_HEADLINE (C_ID,C_EID,C_TYPE,C_IMG,C_NAME,C_ORDER) VALUES(?, ? , ? , ? , ? , ?)";
            this.jdbcTemplate.update(sql,new Object[]{JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_EK_ACTIVITY_HEADLINE"),eid,type,logo,name,order});
        } catch (Exception e) {
            LOGGER.error("EKActivityIndexDaoImpl.addHeadLine failed, e : " + e);
        }
    }

    /**
     * 活动头条 ： 更新
     * @param id
     * @param eid
     * @param name
     * @param type
     * @param logo
     * @param order
     */
    @Override
    public void updateHeadLine(String id, String eid, String name, String type, String logo, String order) {
        try {
            String sql = "UPDATE T_EK_ACTIVITY_HEADLINE SET C_EID = ?,C_TYPE=?,C_IMG=?,C_NAME=?,C_ORDER=? WHERE C_ID = ?";
            List<Object> args = new ArrayList<Object>();
            args.add(eid);
            args.add(type);
            args.add(logo);
            args.add(name);
            args.add(order);
            args.add(Integer.parseInt(id));
            this.jdbcTemplate.update(sql,args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKActivityIndexDaoImpl.updateHeadLine failed, e : " + e);
        }
    }

    /**
     * 查询  活动分类中间表 原数据
     * @return
     */
    @Override
    public List<Map<String, Object>> getoldActivityInfoList() {
        String sql="";
        try {
            sql = "SELECT T.C_ID,T.C_CID,T.C_ORDER,TC.C_NAME,TC.C_IMG FROM T_EK_ACTIVITY_INDEX_CATEGORY T LEFT JOIN T_EK_ACTIVITY_CATEGORY_INFO TC ON TC.C_ID = T.C_CID ORDER BY T.C_ORDER";
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            LOGGER.error("EKActivityIndexDaoImpl.getoldActivityInfoList failed, e : " + e);
        }
        return null;
    }

    /**
     * 删除 活动分类中间表  多余数据
     * @param id
     */
    @Override
    public void deleteActivityInfo(String id) {
        try {
            String sql = "DELETE FROM T_EK_ACTIVITY_INDEX_CATEGORY WHERE C_ID = ?";
            this.jdbcTemplate.update(sql,new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKActivityIndexDaoImpl.deleteActivityInfo failed, e : " + e);
        }

    }

    /**
     * 添加 ：  活动分类中间表
     * @param cid
     * @param order
     */
    @Override
    public void addActivityInfo(String cid, String order) {
        try {
            String sql = "INSERT INTO T_EK_ACTIVITY_INDEX_CATEGORY (C_ID,C_CID,C_ORDER) VALUES(? , ? , ?)";
            this.jdbcTemplate.update(sql,new Object[]{JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_EK_ACTIVITY_INDEX_CATE"),cid,order});
        } catch (Exception e) {
            LOGGER.error("EKActivityIndexDaoImpl.addActivityInfo failed, e : " + e);
        }
    }

    /**
     * 更新  ：  活动分类中间表
     * @param id
     * @param cid
     * @param order
     */
    @Override
    public void updateActivityInfo(String id, String cid, String order) {
        try {
            String sql = "UPDATE T_EK_ACTIVITY_INDEX_CATEGORY SET C_CID = ?,C_ORDER=? WHERE C_ID = ?";
            List<Object> args = new ArrayList<Object>();
            args.add(cid);
            args.add(order);
            args.add(Integer.parseInt(id));
            this.jdbcTemplate.update(sql,args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKActivityIndexDaoImpl.updateActivityInfo failed, e : " + e);
        }
    }

    /**
     * 查找 活动详情 中间表   c_cid = 1|| 2
     * @return
     */
    @Override
    public List<Map<String, Object>> getoldActivityList() {
        String sql="";
        try {
            sql = "SELECT T.C_ID,T.C_AID,T.C_CID,T.C_ORDER,T.C_TAGID,TC.C_IMG,TC.C_TITLE,TC.C_VIEW,TB.C_IMG AS C_LOGO" +
                    " FROM T_EK_ACTIVITY_CATEGORY_ACTIVIT T" +
                    " LEFT JOIN T_EK_ACTIVITY_TAG_BASE TB ON TB.C_ID =T.C_TAGID" +
                    " LEFT JOIN T_EK_ACTIVITY_INFO TC ON TC.C_ID = T.C_AID" +
                    " WHERE (T.C_CID='1' OR T.C_CID='2') ORDER BY T.C_ORDER";
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            LOGGER.error("EKActivityIndexDaoImpl.getoldActivityList failed, e : " + e);
        }
        return null;
    }

    /**
     * 删除  活动详情中间表 多余数据
     * @param id
     */
    @Override
    public void deleteActivity(String id) {
        try {
            String sql = "DELETE FROM T_EK_ACTIVITY_CATEGORY_ACTIVIT WHERE C_ID = ?";
            this.jdbcTemplate.update(sql,new Object[]{id});
        } catch (Exception e) {
            LOGGER.error("EKActivityIndexDaoImpl.deleteActivity failed, e : " + e);
        }
    }

    /**
     * 添加  ：  活动详情中间表
     * @param aid
     * @param ccid
     * @param tagid
     * @param order
     */
    @Override
    public void addActivity(String aid, String ccid, String tagid, String order) {
        try {
            String sql = "INSERT INTO T_EK_ACTIVITY_CATEGORY_ACTIVIT (C_ID,C_AID,C_CID,C_ORDER,C_TAGID) VALUES(? , ? , ? , ? , ?)";
            this.jdbcTemplate.update(sql,new Object[]{JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_EK_ACTIVITY_CATE_ACT"),aid,ccid,order,tagid});
        } catch (Exception e) {
            LOGGER.error("EKActivityIndexDaoImpl.addActivity failed, e : " + e);
        }
    }

    @Override
    public void updateActivity(String id, String aid, String ccid, String tagid, String order) {
        try {
            String sql = "UPDATE T_EK_ACTIVITY_CATEGORY_ACTIVIT SET C_AID=?,C_CID=?,C_ORDER=?,C_TAGID=? WHERE C_ID = ?";
            List<Object> args = new ArrayList<Object>();
            args.add(aid);
            args.add(ccid);
            args.add(order);
            args.add(tagid);
            args.add(Integer.parseInt(id));
            this.jdbcTemplate.update(sql,args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKActivityIndexDaoImpl.updateActivity failed, e : " + e);
        }
    }
}
