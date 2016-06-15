package com.sys.ekey.activity.service.impl;

import com.sys.ekey.activity.action.ActivityAction;
import com.sys.ekey.activity.service.ActivityService;
import com.sys.util.ApDateTime;
import com.sys.util.StrUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by Maryn on 2016/3/3.
 */
@Service
public class ActivityServiceImpl extends SqlMapClientDaoSupport implements ActivityService {

    private static Logger LOGGER = Logger.getLogger(ActivityServiceImpl.class);

    @Override
    public Map<String, Object> activityDetail(String aid,String uid,String imei) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        Map<String,Object> result = new HashMap<String,Object>();
        uid = StringUtils.isEmpty(uid) ? "-1" : uid;
        parameterMap.put("id",aid);
        parameterMap.put("uid",uid);
        parameterMap.put("imei",imei);

        // 活动信息
        List<Map<String,Object>> mapList = getSqlMapClientTemplate().queryForList("ek_activity.getActivityById",parameterMap);
        // 注意事项
        List<Map<String,Object>> notices = getSqlMapClientTemplate().queryForList("ek_activity.notices",parameterMap);

        if(mapList != null && !mapList.isEmpty()) {
            result.put("baseInfo",mapList.get(0));
            if(notices == null) {
                result.put("notices", new ArrayList<Map<String,Object>>());
            } else {
                result.put("notices", notices);
            }

        } else {
            result.put("baseInfo", new HashMap<String,Object>());
            result.put("notices", new ArrayList<Map<String,Object>>());
        }

        return result;
    }

    @Override
    public boolean userAction(String uid, String aid, String type, String imei) {
        List<Map<String, Object>> result = null;

        Map<String, Object> parameterMap = new HashMap<String, Object>();

        uid = StringUtils.isEmpty(uid) ? "-1" : uid;
        parameterMap.put("uid", uid);
        parameterMap.put("aid", aid);
        parameterMap.put("type", type);
        parameterMap.put("imei", imei);
        //   parameterMap.put("vDate", new Date());
        parameterMap.put("content", "");

        try {
            // 持久化用户行为 00：点赞 01：收藏 02：浏览活动 03：打开专题 04：参与活动 05 ：取消收藏 06：取消点赞
            getSqlMapClientTemplate().insert("ek_activity.userAction", parameterMap);

            if ("01".equals(type)) {
                // 收藏活动
                getSqlMapClientTemplate().insert("ek_activity.userCollectAdd", parameterMap);
            } else if ("05".equals(type)) {
                // 取消收藏活动
                getSqlMapClientTemplate().delete("ek_activity.userCollectDel", parameterMap);
            } else if ("04".equals(type)) {
                // 参加活动
                getSqlMapClientTemplate().insert("ek_activity.userJoin", parameterMap);
            } else if ("00".equals(type)) {
                // 点赞
                getSqlMapClientTemplate().insert("ek_activity.userLikeAdd", parameterMap);
            } else if ("06".equals(type)) {
                //取消点赞
                getSqlMapClientTemplate().delete("ek_activity.userLikeDel", parameterMap);
            }
        } catch (Exception e) {
            logger.error("用户行为记录出错！", e);
            return false;
        }

        return true;
    }

    @Override
    // 查询活动头条
    public List<Map<String, Object>> headLine() {
        List<Map<String, Object>> list = new ArrayList();
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_activity.advertInfo");
        } catch (DataAccessException e) {
            LOGGER.error("ActivityServiceImpl.headLine failed, e : " + e.getMessage());
        }
        return list;
    }

    @Override
    // 首页分类
    public List<Map<String, Object>> indexCategory() {
        List<Map<String, Object>> list = new ArrayList();
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_activity.indexCategory");
        } catch (DataAccessException e) {
            LOGGER.error("ActivityServiceImpl.indexCategory failed, e : " + e.getMessage());
        }
        return list;
    }

    @Override
    // 分类详情
    public List<Map<String, Object>> categoryDetail(
            String cid, String pageIndex, String pSize, String isIndex) {
        List<Map<String, Object>> list = new ArrayList();
        try {
            Map<String, Object> paramMap = new HashMap();
            paramMap.put("CID", cid);
            if ("0".equals(isIndex)) {
                paramMap.put("PAGE", pageIndex);
                paramMap.put("PSIZE", pSize);
                list = this.getSqlMapClientTemplate().queryForList("ek_activity.categoryDetail", paramMap);
            } else {
                list = this.getSqlMapClientTemplate().queryForList("ek_activity.categoryDetailIndex", paramMap);
            }
            if (StrUtils.isNotEmpty(list)) {
                for (Map<String, Object> map : list) {
                    String aId = StrUtils.emptyOrString(map.get("C_ID"));
                    Integer view = StrUtils.zeroOrInt(StrUtils.emptyOrString(map.get("C_VIEW")));
                    String tagId = StrUtils.emptyOrString(map.get("C_TAGID"));
                    // 查询实际浏览数
                    paramMap.clear();
                    paramMap.put("AID", aId);
                    Integer actuallyView = (Integer) this.getSqlMapClientTemplate().queryForObject("ek_activity.actuallyView", paramMap);
                    map.put("C_VIEW", view + actuallyView);
                    // 查询指定活动的标签
                    if (StrUtils.isNotEmpty(tagId)) {
                        paramMap.clear();
                        paramMap.put("ID", tagId);
                        String tag = (String) this.getSqlMapClientTemplate().queryForObject("ek_activity.getTagByAid", paramMap);
                        map.put("C_TAG", tag);
                    } else {
                        map.put("C_TAG", "0");
                    }
                    // 判断是否过期
                    if ("1".equals(isIndex)) {
                        String edate = StrUtils.emptyOrString(map.get("C_EDATE"));
                        long endTimeLong = 0L;
                        try {
                            endTimeLong = ApDateTime.getDateTimeS(edate.substring(0, 19));
                        } catch (Exception e) {
                            LOGGER.error("transform [time] from String to Long failed, [time] : [" + edate + "]");
                        }
                        long nowTimeLong = System.currentTimeMillis();
                        if (endTimeLong > nowTimeLong) {
                            map.put("isOver", 0);
                        } else {
                            map.put("isOver", 1);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("ActivityServiceImpl.indexCategory failed, e : " + e.getMessage());
        }
        return list;
    }


    @Override
    // 活动相关列表（收藏、参与记录）
    public List<Map<String, Object>> listAboutAct(
            String uid, String pageIndex, String pSize, String joininrec) {
        List<Map<String, Object>> list = new ArrayList();
        try {
            Map<String, Object> paramMap = new HashMap();
            paramMap.put("TBNAME", joininrec);
            paramMap.put("UID", uid);
            paramMap.put("PAGE", pageIndex);
            paramMap.put("PSIZE", pSize);
            list = this.getSqlMapClientTemplate().queryForList("ek_activity.listAboutAct", paramMap);
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
                    if (endTimeLong > nowTimeLong) {
                        map.put("isOver", 0);
                    } else {
                        map.put("isOver", 1);
                    }

                    // 查询浏览数或收藏数
                    map.put("count", countOfJoinOrCollecting(map.get("C_ID").toString(), joininrec));
                }
            }
        } catch (Exception e) {
            LOGGER.error("ActivityServiceImpl.listAboutAct failed, e : " + e.getMessage());
        }
        return list;
    }

    // 查询指定活动的浏览数或收藏数
    private Object countOfJoinOrCollecting(String c_id, String tableName) {
        Integer count = 0;
        try {
            String statement = "ek_activity.";
            if (tableName.equals(ActivityAction.COLLECTEDACT)) {// 收藏数
                statement += "countOfCollecting";
            } else if (tableName.equals(ActivityAction.JOININREC)) {// 浏览数
                statement += "countOfViewing";
            }
            Map<String, Object> paramMap = new HashMap();
            paramMap.put("AID", c_id);
            count = (Integer) this.getSqlMapClientTemplate().queryForObject(statement, paramMap);
        } catch (Exception e) {
            LOGGER.error("ActivityServiceImpl.countOfJoinOrCollecting failed, e : " + e.getMessage());
        }
        return count;
    }

    @Override
    // 用户删除其参与的活动
    public void delActAttended(String uid, String aid) {
        try {
            Map<String, Object> paramMap = new HashMap();
            paramMap.put("UID", uid);
            paramMap.put("AID", aid);
            this.getSqlMapClientTemplate().update("ek_activity.delActAttended", paramMap);
        } catch (Exception e) {
            LOGGER.error("ActivityServiceImpl.listAboutAct failed, e : " + e.getMessage());
        }
    }
}
