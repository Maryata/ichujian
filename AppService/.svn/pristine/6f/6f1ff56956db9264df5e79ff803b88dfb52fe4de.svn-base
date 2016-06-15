package com.sys.ekey.activity.action;

import com.sys.commons.AbstractAction;
import com.sys.ekey.activity.service.ActivityService;
import com.sys.game.util.IGameConst;
import com.sys.util.StrUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/3/3.
 */
@Component
public class ActivityAction extends AbstractAction {

    private static final long serialVersionUID = -127824725217243365L;

    public static final String JOININREC = "T_EK_ACTIVITY_USER_JOIN";// 参与记录相关表名

    public static final String COLLECTEDACT = "T_EK_ACTIVITY_USER_COLLECT";// 活动收藏相关表名

    @Autowired
    private ActivityService activityService;

    public String activityDetail() {
        log.info( "into ActivityAction activityDetail ..." );
        Map<String, Object> retMap = new HashMap<String, Object>();

        String aid = getParameter("aid");
        String uid = getParameter("uid");
        String imei = getParameter("imei");

        log.info(" aid -->" + aid);

        if(StringUtils.isEmpty(aid) || StringUtils.isEmpty(imei)) {
            retMap.put(IGameConst.STATUS, IGameConst._1001);
        } else {
            try {
                Map<String,Object> activity = activityService.activityDetail(aid,uid,imei);

                retMap.put("activity",activity);

                retMap.put( IGameConst.STATUS, IGameConst.YES );
                retMap.put( IGameConst.INFO, IGameConst._1002 );
            } catch ( Exception e ) {
                log.error( "获取活动详情出错！", e );
                retMap.put( IGameConst.STATUS, IGameConst.NO );
                retMap.put( IGameConst.INFO, IGameConst._1003 );
            }
        }

        try {
            writeToResponse( JSONObject.fromObject( retMap ).toString() );
        } catch ( Exception ex ) {
            log.error( "数据写出错误！",ex );
        }

        return NONE;
    }

    public String userAction() {
        log.info( "into ActivityAction userAction ..." );
        Map<String, Object> retMap = new HashMap<String, Object>();

        String uid = getParameter("uid");
        // 活动ID活专题ID
        String aid = getParameter("aid");
        // 操作类型 00：点赞 01：收藏 02：浏览活动 03：打开专题 04：参与活动 05 ：取消收藏 06：取消点赞
        String type = getParameter("type");
        String imei = getParameter("imei");

        log.info("uid -->" + uid + " aid -->" + aid + " type -->" +type + " imei -->" + imei);

        if(StringUtils.isEmpty(aid) || StringUtils.isEmpty(type) || (StringUtils.isEmpty(imei) && StringUtils.isEmpty(uid))) {
            retMap.put(IGameConst.STATUS, IGameConst._1001);
        } else {
            try {
                boolean flag = activityService.userAction(uid,aid,type,imei);

                retMap.put("flag", flag);

                retMap.put( IGameConst.STATUS, IGameConst.YES );
                retMap.put( IGameConst.INFO, IGameConst._1002 );
            } catch ( Exception e ) {
                log.error( "用户行为记录出错！", e );
                retMap.put( IGameConst.STATUS, IGameConst.NO );
                retMap.put( IGameConst.INFO, IGameConst._1003 );
            }
        }

        try {
            writeToResponse( JSONObject.fromObject( retMap ).toString() );
        } catch ( Exception ex ) {
            log.error( "数据写出错误！",ex );
        }

        return NONE;
    }

    // 活动头条接口
    public String headLine() {
        Map<String, Object> reqMap = new HashMap();
        log.info("into ActivityAction.headLine");
        try {
            List<Map<String, Object>> headLine = activityService.headLine();
            reqMap.put("status", "Y");
            reqMap.put("headLine", headLine);
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("ActivityAction.headLine failed,e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (IOException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
        }
        return NONE;
    }

    // 首页分类接口
    public String indexCategory() {
        Map<String, Object> reqMap = new HashMap();
        log.info("into ActivityAction.indexCategory");
        try {
            List<Map<String, Object>> categories = activityService.indexCategory();
            reqMap.put("status", "Y");
            reqMap.put("categories", categories);
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("ActivityAction.indexCategory failed,e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (IOException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
        }
        return NONE;
    }

    // 分类详情
    public String categoryDetail() {
        Map<String, Object> reqMap = new HashMap();
        String cid = this.getParameter("cid");
        String pageIndex = this.getParameter("pageIndex");// 页码
        String pSize = this.getParameter("pSize");// 每页显示数量
        String isIndex = this.getParameter("isIndex");// 是否首页的分类
        log.info("into ActivityAction.categoryDetail");
        log.info("cid = " + cid + ", pageIndex = " + pageIndex + ", pSize = " + pSize + ", isIndex = " + isIndex);
        try {
            List<Map<String, Object>> detail = activityService.categoryDetail(cid, pageIndex, pSize, isIndex);
            reqMap.put("status", "Y");
            reqMap.put("detail", detail);
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("ActivityAction.categoryDetail failed,e : ", e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (IOException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
        }
        return NONE;
    }

    // 活动参与记录
    public String joinInRec() {
        Map<String, Object> reqMap = new HashMap();
        String uid = this.getParameter("uid");
        String pageIndex = this.getParameter("pageIndex");// 页码
        String pSize = this.getParameter("pSize");// 每页显示数量
        log.info("into ActivityAction.joinInRec");
        log.info("uid = " + uid + ", pageIndex = " + pageIndex + ", pSize = " + pSize);
        try {
            List<Map<String, Object>> rec = activityService.listAboutAct(uid, pageIndex, pSize, JOININREC);
            reqMap.put("status", "Y");
            reqMap.put("rec", rec);
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("ActivityAction.joinInRec failed,e : ", e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (IOException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
        }
        return NONE;
    }

    // 活动收藏列表
    public String collectedAct() {
        Map<String, Object> reqMap = new HashMap();
        String uid = this.getParameter("uid");
        String pageIndex = this.getParameter("pageIndex");// 页码
        String pSize = this.getParameter("pSize");// 每页显示数量
        log.info("into ActivityAction.collectedAct");
        log.info("uid = " + uid + ", pageIndex = " + pageIndex + ", pSize = " + pSize);
        try {
            List<Map<String, Object>> rec = activityService.listAboutAct(uid, pageIndex, pSize, COLLECTEDACT);
            reqMap.put("status", "Y");
            reqMap.put("rec", rec);
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("ActivityAction.collectedAct failed,e : ", e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (IOException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
        }
        return NONE;
    }

    // 用户删除其参与的活动
    public String delActAttended() {
        Map<String, Object> reqMap = new HashMap();
        String uid = this.getParameter("uid");
        String aid = this.getParameter("aid");
        log.info("into ActivityAction.delActAttended");
        log.info("uid = " + uid + ", aid = " + aid);
        try {
            activityService.delActAttended(uid, aid);
            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("ActivityAction.delActAttended failed,e : ", e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (IOException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
        }
        return NONE;
    }
}
