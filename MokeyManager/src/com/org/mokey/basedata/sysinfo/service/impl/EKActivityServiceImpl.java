package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.baseinfo.common.HtmlUtil;
import com.org.mokey.basedata.sysinfo.dao.EKActivityDao;
import com.org.mokey.basedata.sysinfo.dao.EKActivityNoticeDao;
import com.org.mokey.basedata.sysinfo.service.EKActivityService;
import com.org.mokey.util.StrUtils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by vpc on 2016/3/2.
 * e键 ：活动详情
 */
public class EKActivityServiceImpl implements EKActivityService {

    private static final Logger LOGGER = Logger.getLogger(EKActivityServiceImpl.class);

    public EKActivityDao getEkActivityDao() {
        return ekActivityDao;
    }

    public void setEkActivityDao(EKActivityDao ekActivityDao) {
        this.ekActivityDao = ekActivityDao;
    }

    private EKActivityDao ekActivityDao;


    public Map<String, Object> ekActivityList(String title, String ccid, String status, int page) {
        return ekActivityDao.ekActivityList(title, ccid, status, page);
    }

    public EKActivityNoticeDao getEkActivityNoticeDao() {
        return ekActivityNoticeDao;
    }

    public void setEkActivityNoticeDao(EKActivityNoticeDao ekActivityNoticeDao) {
        this.ekActivityNoticeDao = ekActivityNoticeDao;
    }

    private EKActivityNoticeDao ekActivityNoticeDao;

    @Override
    public void toDel(String id) {
        ekActivityDao.toDel(id);
    }

    @Override
    public void updateActivity(String id, String logo, String sdate, String edate, String title, String subTitle, String url, String urlShare,
                               String webViewUrl, String view, String vote, String favorite, String reason, String tip,
                               String ccid, String detail, String modifier, String fullDetail,
                               String longImg, String thinImg,String indexImg,String publisher, String[] notices, String[] orders,String halfImg,String titleImg) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            Map<String, List> mapList = new HashMap<String, List>();
            map.put("C_ID", id);
            map.put("C_TITLE", title);
            map.put("C_SDATE", sdate);
            map.put("C_EDATE", edate);
            map.put("C_IMG", logo);
            map.put("C_VIEW", view);
            map.put("C_VOTE", vote);
            map.put("C_FAVORITE", favorite);
            map.put("C_DETAIL", detail);
            map.put("C_TIP", tip);
            map.put("C_REASON", reason);
            map.put("C_URL", url);
            map.put("C_PUBLISHER", publisher);
            map.put("C_FULL_DETAIL", fullDetail);
            // 生成完整详情HTML
            HtmlUtil htmlUtil = new HtmlUtil();
            urlShare = htmlUtil.createEkHtml("ek-activityShare", map);//创建完整活动详情页面
            if(StrUtils.isEmpty(fullDetail.trim())){
                webViewUrl = urlShare="";
            }else{
                webViewUrl = urlShare;
            }
            //生成详情html
            map.put("C_URL_SHARE", urlShare);

            if (StrUtils.isNotEmpty(notices)) {
                //查询注意事项列表
                List<Map<String,Object>> noticeList = ekActivityNoticeDao.noticeList();
                List logoList = new ArrayList();
                List nameList = new ArrayList();
                for (Map item : noticeList){
                    if( Arrays.asList(notices).contains(String.valueOf(item.get("C_ID")))){
                        logoList.add(item.get("C_IMG"));
                        nameList.add(item.get("C_NAME"));
                    }
                }
                mapList.put("logoList", logoList);
                mapList.put("nameList", nameList);
            }
            String href = htmlUtil.createActivityHtml("ek-activity",map,mapList);

            ekActivityDao.updateActivity(id, logo, sdate, edate, title, subTitle, url, urlShare, webViewUrl, view, vote, favorite, reason,
                    tip, ccid, detail, publisher, fullDetail, longImg, thinImg,indexImg,modifier,href,halfImg,titleImg);

            // 删除原有的所有“注意事项”
            ekActivityDao.delNoticeBef(id);
            // 新增新的“注意事项”
            if (StrUtils.isNotEmpty(notices) && StrUtils.isNotEmpty(orders)) {
                for (int i = 0; i < notices.length; i++) {
                    String[] args = {id, notices[i], orders[i]};
                    ekActivityDao.updateNotice(args);
                }
            }
        } catch (Exception e) {
            LOGGER.error("updateActivity failed ! e : ", e);
        }
    }

    @Override
    public void addActivity(String id, String logo, String sdate, String edate, String title, String subTitle, String url,
                            String urlShare, String webViewUrl, String view, String vote, String favorite, String reason,
                            String tip, String ccid, String detail, String publisher, String fullDetail, String longImg,
                            String thinImg,String indexImg,String modifier, String[] notices, String[] orders,String halfImg,String titleImg) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, List> mapList = new HashMap<String, List>();
        map.put("C_ID", id);
        map.put("C_TITLE", title);
        map.put("C_SDATE", sdate);
        map.put("C_EDATE", edate);
        map.put("C_IMG", logo);
        map.put("C_VIEW", view);
        map.put("C_VOTE", vote);
        map.put("C_FAVORITE", favorite);
        map.put("C_DETAIL", detail);
        map.put("C_TIP", tip);
        map.put("C_REASON", reason);
        map.put("C_URL", url);
        map.put("C_PUBLISHER", publisher);
        map.put("C_FULL_DETAIL", fullDetail);
        // 生成完整详情HTML
        HtmlUtil htmlUtil = new HtmlUtil();
        urlShare = htmlUtil.createEkHtml("ek-activityShare", map);//创建完整活动详情页面
        if(StrUtils.isEmpty(fullDetail.trim())){
            webViewUrl = urlShare="";
        }else{
            webViewUrl = urlShare;
        }
        //生成详情html
        map.put("C_URL_SHARE", urlShare);

        if (StrUtils.isNotEmpty(notices)) {
            //查询注意事项列表
            List<Map<String,Object>> noticeList = ekActivityNoticeDao.noticeList();
            List logoList = new ArrayList();
            List nameList = new ArrayList();
            for (Map item : noticeList){
                if( Arrays.asList(notices).contains(String.valueOf(item.get("C_ID")))){
                    logoList.add(item.get("C_IMG"));
                    nameList.add(item.get("C_NAME"));
                }
            }
            mapList.put("logoList", logoList);
            mapList.put("nameList", nameList);
        }
        String href = htmlUtil.createActivityHtml("ek-activity",map,mapList);
        ekActivityDao.addActivity(id, logo, sdate, edate, title, subTitle, url, urlShare, webViewUrl, view, vote, favorite,
                            reason, tip, ccid, detail, publisher, fullDetail, longImg, thinImg,indexImg,modifier,href,halfImg,titleImg);

        // 新增新的“注意事项”
        if (StrUtils.isNotEmpty(notices) && StrUtils.isNotEmpty(orders)) {
            for (int i = 0; i < notices.length; i++) {
                String[] args = {id, notices[i], orders[i]};
                ekActivityDao.updateNotice(args);
            }
        }
    }

    @Override
    public void auditActivity(String id, String modifier, String value) {
        ekActivityDao.auditActivity(id, modifier, value);
    }

    /**
     * 活动首页  ：  活动详情
     *
     * @param title
     * @param page
     * @return
     */
    @Override
    public Map<String, Object> ekActivityInfoList(String title, int page, List ids) {
        return ekActivityDao.ekActivityInfoList(title, page, ids);
    }

    /**
     * 活动首页  ：  活动详情   推荐  最新
     *
     * @param title
     * @param page
     * @return
     */
    @Override
    public Map<String, Object> ekActivityLists(String title, int page, List ids) {
        return ekActivityDao.ekActivityLists(title, page, ids);
    }

    @Override
    public List<Map<String, Object>> getFullDetail(String id) {
        return ekActivityDao.getFullDetail(id);
    }

    @Override
    // 查询当前登陆人的角色
    public int getAuthority(String userId) {
        return ekActivityDao.getAuthority(userId);
    }

    @Override
    // 查询所有“注意事项”
    public List<Map<String, Object>> getNoticeList() {
        return ekActivityDao.getNoticeList();
    }

    @Override
    // 查询已有的注意事项
    public String[] getNoticeBef(String id) {
        String[] noticeBef = new String[2];
        StringBuffer order = new StringBuffer();
        StringBuffer notice = new StringBuffer();
        try {
            List<Map<String, Object>> list = ekActivityDao.getNoticeBef(id);
            if (StrUtils.isNotEmpty(list)) {
                for (Map<String, Object> map : list) {
                    order.append(StrUtils.emptyOrString(map.get("C_ORDER")));
                    order.append(",");
                    notice.append(StrUtils.emptyOrString(map.get("C_NID")));
                    notice.append(",");
                }
            }
        } catch (Exception e) {
            LOGGER.error("getNoticeBef failed ! e : ", e);
        }
        noticeBef[0] = order.length() > 0 ? order.toString().substring(0, order.length() - 1) : "";
        noticeBef[1] = notice.length() > 0 ? notice.toString().substring(0, notice.length() - 1) : "";
        return noticeBef;
    }

}
