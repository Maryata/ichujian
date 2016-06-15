package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ：活动详情
 */
public interface EKActivityService {
    /**
     * e键 ： 活动详情  ： 获取列表
     *
     * @param
     * @return
     */
    Map<String, Object> ekActivityList(String title, String ccid, String status, int page);

    /**
     * 删除  活动详情
     *
     * @param id
     */
    void toDel(String id);

    /**
     * 更新 ： 活动详情
     *  @param id
     * @param logo
     * @param sdate
     * @param edate
     * @param title
     * @param subTitle
     * @param url
     * @param urlShare
     * @param webViewUrl
     * @param view
     * @param vote
     * @param favorite
     * @param reason
     * @param tip
     * @param ccid
     * @param detail
     * @param modifier
     * @param notices
     */
    void updateActivity(String id, String logo, String sdate, String edate, String title, String subTitle, String url, String urlShare, String webViewUrl, String view, String vote, String favorite, String reason, String tip, String ccid, String detail, String modifier, String fullDetail, String longImg, String thinImg,String indexImg,String publisher, String[] notices, String[] orders,String halfImg,String titleImg);

    /**
     * 添加 ： 活动详情
     *
     * @param id
     * @param logo
     * @param sdate
     * @param edate
     * @param title
     * @param subTitle
     * @param url
     * @param urlShare
     * @param webViewUrl
     * @param view
     * @param vote
     * @param favorite
     * @param reason
     * @param tip
     * @param ccid
     * @param detail
     * @param publisher
     */
    void addActivity(String id, String logo, String sdate, String edate, String title, String subTitle, String url, String urlShare, String webViewUrl, String view, String vote, String favorite, String reason, String tip, String ccid, String detail, String publisher, String fullDetail, String longImg, String thinImg,String indexImg,String modifier, String[] notices, String[] orders,String halfImg,String titleImg);

    /**
     * 审核  ：  活动详情
     *
     * @param id
     */
    void auditActivity(String id,String modifier,String value);

    /**
     * 活动首页 ：  活动详情
     *
     * @param title
     * @param page
     * @return
     */
    Map<String, Object> ekActivityInfoList(String title, int page, List ids);

    /**
     * 活动首页 ：  活动详情    推荐  最新
     *
     * @param title
     * @param page
     * @return
     */
    Map<String, Object> ekActivityLists(String title, int page, List ids);

    /**
     * 查询 完整活动详情
     *
     * @param id
     * @return
     */
    List<Map<String, Object>> getFullDetail(String id);

    /**
     * 查询当前登陆人的角色
     *
     * @param userId 用户id
     * @return
     */
    int getAuthority(String userId);

    /**
     * 查询所有“注意事项”
     *
     * @return
     */
    List<Map<String, Object>> getNoticeList();

    /**
     * 查询已有的注意事项
     *
     * @return
     * @param id
     */
    String[] getNoticeBef(String id);
}
