package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKActivityCategoryInfoService;
import com.org.mokey.basedata.sysinfo.service.EKActivityService;
import com.org.mokey.basedata.sysinfo.util.JsonTools;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.system.entiy.TSysUser;
import com.org.mokey.util.StrUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ： 活动详情
 */
public class EKActivityAction extends AbstractAction {

    public EKActivityService getEkActivityService() {
        return ekActivityService;
    }

    public void setEkActivityService(EKActivityService ekActivityService) {
        this.ekActivityService = ekActivityService;
    }

    private EKActivityService ekActivityService;

    public EKActivityCategoryInfoService getEkActivityCategoryInfoService() {
        return ekActivityCategoryInfoService;
    }

    public void setEkActivityCategoryInfoService(EKActivityCategoryInfoService ekActivityCategoryInfoService) {
        this.ekActivityCategoryInfoService = ekActivityCategoryInfoService;
    }

    private EKActivityCategoryInfoService ekActivityCategoryInfoService;
    private String out;
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }


    // 跳转到活动详情列表首页
    public String toEKActivityList() {
        String type = "";
        List<Map<String, Object>> activityCategoryList = ekActivityCategoryInfoService.activityCategoryList(type);
        getRequest().setAttribute("activityCategoryList", activityCategoryList);
        return "ekActivityList";
    }

    /**
     * 获取 e键 ： 活动详情   ： 列表
     *
     * @return
     */

    public String ekActivityList() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        String title = getParameter("title");
        String ccid = getParameter("ccid");
        String status = getParameter("status");
        String sPage = getParameter("page");// 获取page
        log.info("into EKActivityAction.ekActivityList");
        log.info("sPage=" + sPage);
        try {
            int page = 1;// 默认第一页
            if (null != sPage && sPage.matches("\\d+")) {
                page = Integer.parseInt(sPage);
            } else {
                log.info(sPage);
            }
            // 分页显示活动详情
            retmap = ekActivityService.ekActivityList(title, ccid, status, page);

            // 查询当前登陆人的角色是否是“审核人员”
            String userId = getSessionLoginUser().getUserId();
            int role = ekActivityService.getAuthority(userId);
            retmap.put("authButt", role == 0 ? "0" : "1");

            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("GameCategoryAction.gameCategoryList failed, e : " + e);
        }
        return NONE;
    }

    // 跳转到添加页面
    public String toAdd() {
        log.info("into EKActivityvAction.toAdd");
        try {
            // 获取主键
            String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_EK_ACTIVITY_INFO");
            getRequest().setAttribute("addFlag", "1");// “新增”操作的标记
            getRequest().setAttribute("cid", newId);
            String type = "1";
            List<Map<String, Object>> activityCategoryList = ekActivityCategoryInfoService.activityCategoryList(type);
            getRequest().setAttribute("activityCategoryList", activityCategoryList);
            getRequest().setAttribute("noticeList", getNoticeList());
        } catch (Exception e) {
            log.error("EKActivityAction.toAdd failed,", e);
        }
        return "toAdd";
    }

    // 新增分类
    public String addActivity() {
        String id = getParameter("cid");// 获取cid
        String sdate = getParameter("sdate");//开始时间
        String edate = getParameter("edate");//结束时间
        String title = getParameter("title");//标题
        String subTitle = getParameter("subTitle");//子标题
        String url = getParameter("url");//活动链接
        String urlShare = getParameter("urlShare");//分享URL
        String webViewUrl = getParameter("webViewUrl");//活动内容webview路径
        String view = getParameter("view");//默认浏览数
        String vote = getParameter("vote");//默认点赞数
        String favorite = getParameter("favorite");//默认收藏数
        String reason = getParameter("reason");//推荐理由
        String tip = getParameter("tip");//小编提示
        String ccid = getParameter("ccid");//活动初始分类ID
        String logo = getParameter("logo");// logo
        String publisher = getParameter("publisher");
        String noticeArr = getRequest().getParameter("noticeArr");
        String[] notices = null;
        if (StrUtils.isNotEmpty(noticeArr)) {
            notices = getRequest().getParameter("noticeArr").split(",");
        }
        String orderArr = getRequest().getParameter("noticeArr");
        String[] orders = null;
        if (StrUtils.isNotEmpty(orderArr)) {
            orders = getRequest().getParameter("orderArr").split(",");
        }
        String longImg = getParameter("longImg");
        String thinImg = getParameter("thinImg");
        String indexImg = getParameter("indexImg");
        String fullDetail = getParameter("fullDetail");// fullDetail
        String halfImg = getParameter("halfImg");
        String titleImg = getParameter("titleImg");
        log.info("into EKActivityAction.addActivity");
        log.info("id=" + id + ", logo=" + logo);
        try {
            String detail = getParameter("detail");// 活动详情
            // 获取当前登录人
            String modifier = super.getSessionLoginUser().getUserName();//发布人
            // 新增
            ekActivityService.addActivity(id, logo, sdate, edate, title, subTitle, url,
                     urlShare, webViewUrl, view, vote, favorite, reason, tip, ccid, detail,
                    publisher, fullDetail, longImg, thinImg,indexImg, modifier, notices, orders,halfImg,titleImg);
        } catch (Exception e) {
            log.error("EKActivityAction.addActivity failed,", e);
        }
        return "reload";
    }

    // 删除分类
    public String toDel() {
        // 获取cid
        String id = getParameter("cid");
        log.info("into EKActivityAction.toDel");
        log.info("id=" + id);
        try {
            // 删除
            ekActivityService.toDel(id);
        } catch (Exception e) {
            log.error("EKActivityction.toDel failed,", e);
        }
        return NONE;
    }

    // 跳转到分类修改页面
    public String toUpdate() {
        // 获取请求参数
        String id = getParameter("editId");// 获取cid
        log.info("into EKActivityAction.toUpdate");
        log.info("id=" + id);
        try {
            //获取  活动分类  list
            String type = "1";
            List<Map<String, Object>> activityCategoryList = ekActivityCategoryInfoService.activityCategoryList(type);
            getRequest().setAttribute("activityCategoryList", activityCategoryList);
            List<Map<String, Object>> list = ekActivityService.getFullDetail(id);
            for (Map item : list) {
                if (item.size() > 0) {
                    String sdate = StrUtils.emptyOrString(item.get("C_SDATE"));//开始时间
                    String edate = StrUtils.emptyOrString(item.get("C_EDATE"));//结束时间
                    String title = StrUtils.emptyOrString(item.get("C_TITLE"));//标题
                    String subTitle = StrUtils.emptyOrString(item.get("C_SUBTITLE"));
                    String url = StrUtils.emptyOrString(item.get("C_URL"));//活动链接
                    String urlShare = StrUtils.emptyOrString(item.get("C_URL_SHARE"));//分享URL
                    String webViewUrl = StrUtils.emptyOrString(item.get("C_WEBVIEWURL"));//活动内容webview路径
                    String view = StrUtils.emptyOrString(item.get("C_VIEW"));//默认浏览数
                    String vote = StrUtils.emptyOrString(item.get("C_VOTE"));//默认点赞数
                    String favorite = StrUtils.emptyOrString(item.get("C_FAVORITE"));//默认收藏数
                    String reason = StrUtils.emptyOrString(item.get("C_REASON"));//推荐理由
                    String tip = StrUtils.emptyOrString(item.get("C_TIP"));//小编提示
                    String ccid = StrUtils.emptyOrString(item.get("C_CID"));//活动初始分类ID
                    String logo = StrUtils.emptyOrString(item.get("C_IMG"));// logo
                    String detail = StrUtils.emptyOrString(item.get("C_DETAIL"));// 活动详情
                    String publisher = StrUtils.emptyOrString(item.get("C_PUBLISHER"));
                    String longImg = StrUtils.emptyOrString(item.get("C_IMG_LONG"));
                    String thinImg = StrUtils.emptyOrString(item.get("C_IMG_THIN"));
                    String fullDetail = StrUtils.emptyOrString(item.get("C_FULL_DETAIL"));
                    String indexImg = StrUtils.emptyOrString(item.get("C_IMG_INDEX_LONG"));
                    getRequest().setAttribute("fullDetail", fullDetail);
                    getRequest().setAttribute("sdate", sdate);
                    getRequest().setAttribute("logo", logo);
                    getRequest().setAttribute("edate", edate);
                    getRequest().setAttribute("view", view);
                    getRequest().setAttribute("title", title);
                    getRequest().setAttribute("vote", vote);
                    getRequest().setAttribute("subTitle", subTitle);
                    getRequest().setAttribute("url", url);
                    getRequest().setAttribute("urlShare", urlShare);
                    getRequest().setAttribute("webViewUrl", webViewUrl);
                    getRequest().setAttribute("favorite", favorite);
                    getRequest().setAttribute("reason", reason);
                    getRequest().setAttribute("tip", tip);
                    getRequest().setAttribute("ccid", ccid);
                    getRequest().setAttribute("detail", detail);
                    getRequest().setAttribute("publisher", publisher);
                    getRequest().setAttribute("longImg", longImg);
                    getRequest().setAttribute("thinImg", thinImg);
                    getRequest().setAttribute("indexImg", indexImg);
                }
            }
            getRequest().setAttribute("activityCategoryList", activityCategoryList);
            getRequest().setAttribute("cid", id);
            String[] noticeBef = getNoticeBef(id);
            getRequest().setAttribute("orderBef", noticeBef[0]);// 原有的“注意事项”的排序
            getRequest().setAttribute("noticeBef", noticeBef[1]);// 原有的“注意事项”
            getRequest().setAttribute("noticeList", getNoticeList());// 所有“注意事项”
           /* getRequest().setAttribute("fullDetail", fullDetail);*/
        } catch (Exception e) {
            log.error("EKActivityAction.toUpdate failed,", e);
        }
        return "toUpdate";
    }

    // 更新分类
    public String updateActivity() {
        // 获取请求参数
        String id = getParameter("cid");// 获取cid
        String sdate = getParameter("sdate");//开始时间
        String edate = getParameter("edate");//结束时间
        String title = getParameter("title");//标题
        String subTitle = getParameter("subTitle");
        String url = getParameter("url");//活动链接
        String urlShare = getParameter("urlShare");//分享URL
        String webViewUrl = getParameter("webViewUrl");//活动内容webview路径
        String view = getParameter("view");//默认浏览数
        String vote = getParameter("vote");//默认点赞数
        String favorite = getParameter("favorite");//默认收藏数
        String reason = getParameter("reason");//推荐理由
        String tip = getParameter("tip");//小编提示
        String ccid = getParameter("ccid");//活动初始分类ID
        String logo = getParameter("logo");// logo
        String[] notices = getRequest().getParameter("noticeArr").split(",");
        String[] orders = getRequest().getParameter("orderArr").split(",");
        String longImg = getParameter("longImg");
        String thinImg = getParameter("thinImg");
        String indexImg = getParameter("indexImg");
        String publisher = getParameter("publisher");
        log.info("into EKActivityAction.updateActivity");
        log.info("id=" + id);
        try {
            // 更新
            String detail = getParameter("detail");// 活动详情
            String fullDetail = getParameter("fullDetail");// 活动详情
            String halfImg = getParameter("halfImg");
            String titleImg = getParameter("titleImg");
            // 获取当前登录人
            String modifier = super.getSessionLoginUser().getUserName();

            ekActivityService.updateActivity(id, logo, sdate, edate, title,
                    subTitle, url, urlShare, webViewUrl, view, vote,
                    favorite, reason, tip, ccid, detail, modifier, fullDetail,
                    longImg, thinImg,indexImg, publisher, notices, orders,halfImg,titleImg);
        } catch (Exception e) {
            log.error("EKActivityAction.updateActivity failed,", e);
        }
        return "reload";
    }

    //审核
    public String auditActivity() {
        // 获取请求参数
        String id = getParameter("editId");
        String value = getParameter("status");
        log.info("into EKActivityAction.auditActivity");
        log.info("id=" + id);
        try {
            TSysUser user = super.getSessionLoginUser();
            int role = ekActivityService.getAuthority(user.getUserId());
            if (role > 0) {
                // 更新
                // 获取当前登录人
                String modifier = user.getUserName();
                ekActivityService.auditActivity(id, modifier, value);
            }
        } catch (Exception e) {
            log.error("EKActivityAction.auditActivity failed,", e);
        }
        return "reload";
    }

    /**
     * 活动首页  ：  活动详情
     *
     * @return
     */
    public String ekActivityInfoList() {
        String headLines = getParameter("headLine");
        List<String> eidList = new ArrayList();
        if (!"".equals(headLines)) {
            List<Map<String, Object>> infoLists = new ArrayList();
            infoLists = JsonTools.parseJSON2List(headLines);
            for (Map<String, Object> item : infoLists) {
                if (!"0".equals(item.get("type"))) {
                    eidList.add(StrUtils.emptyOrString(item.get("eid")));
                }
            }
        }
        Map<String, Object> retmap = new HashMap<String, Object>();
        String title = getParameter("title");
        String sPage = getParameter("page");// 获取page
        log.info("into EKActivityAction.ekActivityInfoList");
        log.info("sPage=" + sPage);
        try {
            int page = 1;// 默认第一页
            if (null != sPage && sPage.matches("\\d+")) {
                page = Integer.parseInt(sPage);
            } else {
                log.info(sPage);
            }
            // 分页显示活动详情
            retmap = ekActivityService.ekActivityInfoList(title, page, eidList);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKActivityAction.ekActivityInfoList failed, e : " + e);
        }
        return NONE;
    }

    /**
     * 活动首页  ：  活动详情   推荐 最新
     *
     * @return
     */
    public String ekActivityLists() {
        String activity = getParameter("activity");
        List<String> eidList = new ArrayList();
        if (!"".equals(activity)) {
            List<Map<String, Object>> infoLists = new ArrayList();
            infoLists = JsonTools.parseJSON2List(activity);
            for (Map<String, Object> item : infoLists) {
                eidList.add(StrUtils.emptyOrString(item.get("aid")));
            }
        }
        Map<String, Object> retmap = new HashMap<String, Object>();
        String title = getParameter("title");
        String sPage = getParameter("page");// 获取page
        log.info("into EKActivityAction.ekActivityInfoList");
        log.info("sPage=" + sPage);
        try {
            int page = 1;// 默认第一页
            if (null != sPage && sPage.matches("\\d+")) {
                page = Integer.parseInt(sPage);
            } else {
                log.info(sPage);
            }
            // 分页显示活动详情
            retmap = ekActivityService.ekActivityLists(title, page, eidList);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKActivityAction.ekActivityLists failed, e : " + e);
        }
        return NONE;
    }

    // 查询所有“注意事项”
    private List<Map<String, Object>> getNoticeList() {
        return ekActivityService.getNoticeList();
    }

    // 查询已有的注意事项
    private String[] getNoticeBef(String id) {
        return ekActivityService.getNoticeBef(id);
    }
}
