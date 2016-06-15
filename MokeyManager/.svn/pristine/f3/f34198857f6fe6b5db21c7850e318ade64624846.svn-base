package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKActivityCategoryInfoService;
import com.org.mokey.basedata.sysinfo.service.EKActivityCategoryTagService;
import com.org.mokey.basedata.sysinfo.util.JsonTools;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ： 活动分类
 */
public class EKActivityCategoryInfoAction extends AbstractAction {

    private EKActivityCategoryInfoService ekActivityCategoryInfoService;

    private EKActivityCategoryTagService ekActivityCategoryTagService;
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

    public EKActivityCategoryInfoService getEkActivityCategoryInfoService() {
        return ekActivityCategoryInfoService;
    }

    public void setEkActivityCategoryInfoService(EKActivityCategoryInfoService ekActivityCategoryInfoService) {
        this.ekActivityCategoryInfoService = ekActivityCategoryInfoService;
    }

    public EKActivityCategoryTagService getEkActivityCategoryTagService() {
        return ekActivityCategoryTagService;
    }

    public void setEkActivityCategoryTagService(EKActivityCategoryTagService ekActivityCategoryTagService) {
        this.ekActivityCategoryTagService = ekActivityCategoryTagService;
    }


    // 跳转到活动分类列表首页
    public String toEKActivityCategoryInfoList() {
        return "ekActivityCategoryInfoList";
    }

    /**
     * 获取 e键 ： 活动分类   ： 列表
     *
     * @return
     */
    public String ekActivityCategoryInfoList() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        log.info("into EKActivityCategoryInfoAction.ekActivityCategoryInfoList");
        log.info("page=" + page);
        try {
            // 分页显示活动分类
            retmap = ekActivityCategoryInfoService.ekActivityCategoryInfoList(page);
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
        log.info("into EKActivityCategoryInfoAction.toAdd");
        try {
            // 获取主键
            String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_EK_ACTIVITY_CATE_INFO");
            getRequest().setAttribute("addFlag", "1");// “新增”操作的标记
            getRequest().setAttribute("cid", newId);
        } catch (Exception e) {
            log.error("EKActivityCategoryInfoAction.toAdd failed,", e);
        }
        return "toAdd";
    }

    // 新增分类
    public String addInfo() {
        String id = getParameter("cid");// 获取cid
        String name = getParameter("cname");// 分类名称
        String logo = getParameter("logo");// logo
        String type = getParameter("type");// type
        String headLineImg = getParameter("longImg");//t_img_headling
        log.info("into EKActivityCategoryInfoAction.addInfo");
        log.info("id=" + id + ", name=" + name + ", logo=" + logo + ", type" + type);
        try {
            // 获取当前登录人
           /* String modifier = super.getSessionLoginUser().getUserName();*/
            // 新增
            ekActivityCategoryInfoService.addInfo(id, name, logo, type, headLineImg);
        } catch (Exception e) {
            log.error("EKActivityCategoryInfoAction.addInfo failed,", e);
        }
        return "reload";
    }

    // 删除分类
    public String toDel() {
        // 获取cid
        String id = getParameter("cid");
        log.info("into EKActivityCategoryInfoAction.toDel");
        log.info("id=" + id);
        try {
            // 删除
            ekActivityCategoryInfoService.toDel(id);
        } catch (Exception e) {
            log.error("EKActivityCategoryInfoAction.toDel failed,", e);
        }
        return NONE;
    }

    // 跳转到分类修改页面
    public String toUpdate() {
        // 获取请求参数
        String id = getParameter("editId");
        String name = getParameter("editName");
        String type = getParameter("editType");
        String logo = getParameter("editLogo");
        String headLineImg = getParameter("editHeadLine");
        log.info("into EKActivityCategoryInfoAction.toUpdate");
        log.info("id=" + id + ", name=" + name + ", type=" + type + ", logo=" + logo);
        try {
            getRequest().setAttribute("cid", id);
            getRequest().setAttribute("cname", name);
            getRequest().setAttribute("type", type);
            getRequest().setAttribute("logo", logo);
            getRequest().setAttribute("longImg", headLineImg);
        } catch (Exception e) {
            log.error("EKActivityCategoryInfoAction.toUpdate failed,", e);
        }
        return "toUpdate";
    }

    // 更新分类
    public String updateInfo() {
        // 获取请求参数
        String id = getParameter("cid");
        String name = getParameter("cname");
        String type = getParameter("type");
        String logo = getParameter("logo");
        String headLineImg = getParameter("longImg");//t_img_headling
        log.info("into EKActivityCategoryInfoAction.updateInfo");
        log.info("id=" + id + ", name=" + name + ", type=" + type + ", logo=" + logo);
        try {
            // 更新
            ekActivityCategoryInfoService.updateInfo(id, name, logo, type, headLineImg);
        } catch (Exception e) {
            log.error("EKActivityCategoryInfoAction.updateInfo failed,", e);
        }
        return "reload";
    }

    // 跳转到分类修改页面
    public String toHandle() {
        // 获取请求参数
        String id = getParameter("editId");// id
        String name = getParameter("editName");// 名称
        String type = getParameter("editType");
        log.info("into EKActivityCategoryInfoAction.toHandle");
        log.info("id=" + id + ", name=" + name);
        try {
            getRequest().setAttribute("cid", id);// 分类id
            getRequest().setAttribute("cname", name);
            getRequest().setAttribute("type", type);
            //获取  活动分类  list  去除推荐  最新
            List<Map<String, Object>> activityCategoryList = ekActivityCategoryInfoService.activityCategoryList(type);
            getRequest().setAttribute("activityCategoryList", activityCategoryList);
            //角标 list
            List<Map<String, Object>> List = ekActivityCategoryTagService.getList();
            getRequest().setAttribute("List", List);
        } catch (Exception e) {
            log.error("EKActivityCategoryInfoAction.toHandle failed,", e);
        }

        return "toHandle";
    }

    /**
     * 查询非当前活动分类的其他所有活动
     *
     * @return
     */

    public String getAllActivityCategoryInfo() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        String cid = getParameter("cid");// 当前分类id
        String title = getParameter("title");//
        String ccid = getParameter("ccid");//活动分类
        String imgType = getParameter("imgType");// 图片类型（1.长图，2.宽图）

        log.info("into EKActivityCategoryInfoAction.getAllActivityCategoryInfo");
        log.info("page=" + page + ", cid=" + cid + ", title=" + title + ", imgType = " + imgType);
            try {
                // 分页查询
                retmap = ekActivityCategoryInfoService.getAllActivityCategoryInfo(page, cid, title, ccid, imgType);
                retmap.put("status", "Y");
                // 回写查询结果
                this.writeJSONToResponse(retmap);
            } catch (Exception e) {
                retmap.put("status", "N");
                log.error("EKActivityCategoryInfoAction.getAllActivityCategoryInfo failed, e : " + e);
            }
            return NONE;
        }

    // 查询当前活动分类的活动
    public String getCurrActivityCaInfo() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        String cid = getParameter("cid");// 当前分类id
        String title = getParameter("title");// 活动标题
        log.info("into EKActivityCategoryInfoAction.getCurrActivityCaInfo");
        log.info("page=" + page + ", cid=" + cid + ", title=" + title);
        try {
            // 分页查询
            retmap = ekActivityCategoryInfoService.getCurrActivityCaInfo(page, cid, title);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKActivityCategoryInfoAction.getCurrActivityCaInfo failed, e : " + e);
        }
        return NONE;
    }

    // 分类维护
    public String handleActivity() {
        // 获取请求参数
        String cid = getParameter("cid");// 获取分类id
        String aid = getRequest().getParameter("aid");// 获取应用id
        String removeGid = getRequest().getParameter("removeGid");// 获取要移除出当前分类的应用id
        String order = getRequest().getParameter("order");// 获取顺序
        String tagid = getRequest().getParameter("tagid");
        String title = getRequest().getParameter("title");
        String subtitle = getRequest().getParameter("subtitle");
        log.info("into EKActivityCategoryInfoAction.handleActivity");
        log.info("cid=" + cid + " ---- aid=" + aid + " ---- removeGid=" + removeGid + " ---- order=" + order);
        try {
            // 维护
            ekActivityCategoryInfoService.handleActivity(cid, aid, removeGid, order, tagid, title, subtitle);
        } catch (Exception e) {
            log.error("EKActivityCategoryInfoAction.handleActivity failed,", e);
        }
        return NONE;
    }

    //活动首页  --->  活动分类
    public String ekActivityCategoryList() {
        String headLine = getParameter("headLine");
        List<String> eidList = new ArrayList();
        if (!"".equals(headLine)) {
            List<Map<String, Object>> infoList = new ArrayList();
            infoList = JsonTools.parseJSON2List(headLine);
            for (Map<String, Object> item : infoList) {
                if (!"1".equals(item.get("type"))) {
                    eidList.add(StrUtils.emptyOrString(item.get("eid")));
                }
            }
        }

        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        log.info("into EKActivityCategoryInfoAction.ekActivityCategoryList");
        log.info("page=" + page);
        try {
            // 分页显示活动分类
            retmap = ekActivityCategoryInfoService.ekActivityCategoryList(page, eidList);

            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKActivityCategoryInfoAction.ekActivityCategoryList failed, e : " + e);
        }
        return NONE;
    }

    //活动首页  --->  活动分类   中间 四个圆圈
    public String ekActivityInfoList() {
        String activityInfos = getParameter("activityInfos");
        List<String> eidList = new ArrayList();
        if (!"".equals(activityInfos)) {
            List<Map<String, Object>> infoList = new ArrayList();
            infoList = JsonTools.parseJSON2List(activityInfos);
            for (Map<String, Object> item : infoList) {
                eidList.add(StrUtils.emptyOrString(item.get("eid")));
            }
        }

        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        log.info("into EKActivityCategoryInfoAction.ekActivityCategoryInfoList");
        log.info("page=" + page);
        try {
            // 分页显示活动分类
            retmap = ekActivityCategoryInfoService.ekActivityInfoList(page, eidList);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKActivityCategoryInfoAction.ekActivityCategoryInfoList failed, e : " + e);
        }
        return NONE;
    }

    /**
     * 获取角标列表
     *
     * @return
     */
    public String activityCategoryList() {
        List<Map<String, Object>> List = ekActivityCategoryTagService.getList();
        try {
            this.writeJSONToResponse(List);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

}
