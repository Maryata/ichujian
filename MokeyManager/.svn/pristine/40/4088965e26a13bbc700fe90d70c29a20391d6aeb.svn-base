package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKShopTaskInfoService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商城任务
 * Created by vpc on 2016/4/19.
 */
@Controller("eKShopTaskInfoAction")
public class EKShopTaskInfoAction extends AbstractAction {
    @Autowired
    private EKShopTaskInfoService eKShopTaskInfoService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 跳转到列表页面
     *
     * @return
     */
    public String toList() {

        return "toList";
    }


    /**
     * 查询 商城任务列表
     *
     * @return
     */
    public String list() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        String sPage = getParameter("page");// 获取page
        String type = getParameter("type");
        String subType = getParameter("subType");
        log.info("into EKShopTaskInfoAction.list");
        log.info("sPage=" + sPage);
        try {
            int page = 1;// 默认第一页
            if (null != sPage && sPage.matches("\\d+")) {
                page = Integer.parseInt(sPage);
            } else {
                log.info(sPage);
            }
            // 分页显示活动详情
            retmap = eKShopTaskInfoService.list(page, type, subType);
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error(" EKShopTaskInfoAction.list failed, e : " + e);
        }
        return NONE;
    }


    /**
     * 加载 查询条件数据
     *
     * @return
     */
    public String loadInfo() {
        log.info("into ajaxList");
        Map<String, Object> retmap = new HashMap<String, Object>();
        try {
            retmap = eKShopTaskInfoService.loadInfo();
        } catch (Exception e) {

        }
        try {
            this.writeJSONToResponse(retmap);
        } catch (IOException e) {
            log.error("ajaxList failed", e);
        }

        return NONE;
    }

    public String loadInfos() {
        log.info("into ajaxList");
        String type = getParameter("type");
        Map<String, Object> retmap = new HashMap<String, Object>();
        try {
            retmap = eKShopTaskInfoService.loadInfos(type);
        } catch (Exception e) {

        }
        try {
            this.writeJSONToResponse(retmap);
        } catch (IOException e) {
            log.error("ajaxList failed", e);
        }

        return NONE;
    }

    /**
     * 跳转到更新页面
     *
     * @return
     */
    public String toUpdate() {
        String id = getParameter("editId");
        List<Map<String, Object>> taskList = eKShopTaskInfoService.taskList(id);
        List<Map<String, Object>> subTypeList = eKShopTaskInfoService.subTypeInfo();
        for (Map item : taskList) {
            if (item.size() > 0) {
                String cid = StrUtils.emptyOrString(item.get("C_ID"));
                String type = StrUtils.emptyOrString(item.get("C_TYPE")).trim();
                String subtype = StrUtils.emptyOrString(item.get("C_SUBTYPE")).trim();
                String name = StrUtils.emptyOrString(item.get("C_NAME"));
                String subName = StrUtils.emptyOrString(item.get("C_SUBNAME"));
                String score = StrUtils.emptyOrString(item.get("C_SCORE"));
                String logo = StrUtils.emptyOrString(item.get("C_LOGO"));
                String islive = StrUtils.emptyOrString(item.get("C_ISLIVE"));
                getRequest().setAttribute("cid", cid);
                getRequest().setAttribute("type", type);
                getRequest().setAttribute("subtype", subtype);
                getRequest().setAttribute("name", name);
                getRequest().setAttribute("subName", subName);
                getRequest().setAttribute("score", score);
                getRequest().setAttribute("logo", logo);
                getRequest().setAttribute("islive", islive);
            }
        }
        getRequest().setAttribute("subTypeList", subTypeList);
        return "toUpdate";
    }

    /**
     * 跳转 添加页面
     *
     * @return
     */
    public String toAdd() {
        log.info("into EKShopTaskInfoAction.toAdd");
        try {
            // 获取主键
            String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_EK_TASK");
            getRequest().setAttribute("addFlag", "1");// “新增”操作的标记
            getRequest().setAttribute("cid", newId);
            List<Map<String, Object>> subTypeList = eKShopTaskInfoService.subTypeInfo();
            getRequest().setAttribute("subTypeList", subTypeList);
        } catch (Exception e) {
            log.error("EKShopTaskInfoAction.toAdd failed,", e);
        }
        return "toUpdate";
    }

    /**
     * 添加 方法
     *
     * @return
     */
    public String addTask() {
        String id = getParameter("cid");// 获取cid
        String type = getParameter("type");
        String subtype = getParameter("subtype");
        String islive = getParameter("islive");
        String name = getParameter("name");
        if (StrUtils.isEmpty(name)) {
            List<Map<String, Object>> subTypeList = eKShopTaskInfoService.subTypeInfo();
            for (Map item : subTypeList) {
                if (item.size() > 0) {
                    String subtype_old = StrUtils.emptyOrString(item.get("C_SUBTYPE"));
                    String name_old = StrUtils.emptyOrString(item.get("C_NAME"));
                    if (subtype.equals(subtype_old)) {
                        name = name_old;
                        break;
                    }
                }
            }

        }
        String subName = getParameter("subName");
        String score = getParameter("score");
        String logo = getParameter("logo");// logo
        List<Object> args = new ArrayList<Object>();
        args.add(id);
        args.add(type);
        args.add(subtype);
        args.add(name);
        args.add(subName);
        args.add(score);
        args.add(logo);
        args.add(islive);
        log.info("into EKShopTaskInfoAction.addTask");
        log.info("id=" + id + ", name=" + name + ", logo=" + logo);
        try {
            eKShopTaskInfoService.addTask(args);
        } catch (Exception e) {
            log.error("EKShopTaskInfoAction.addTask failed,", e);
        }
        return "reload";
    }


    public String updateTask() {
        String id = getParameter("cid");// 获取cid
        String type = getParameter("type");
        String subtype = getParameter("subtype");
        String islive = getParameter("islive");
        String name = getParameter("name");
        if (StrUtils.isEmpty(name)) {
            List<Map<String, Object>> subTypeList = eKShopTaskInfoService.subTypeInfo();
            for (Map item : subTypeList) {
                if (item.size() > 0) {
                    String subtype_old = StrUtils.emptyOrString(item.get("C_SUBTYPE"));
                    String name_old = StrUtils.emptyOrString(item.get("C_NAME"));
                    if (subtype.equals(subtype_old)) {
                        name = name_old;
                        break;
                    }
                }
            }

        }
        String subName = getParameter("subName");
        String score = getParameter("score");
        String logo = getParameter("logo");// logo
        List<Object> args = new ArrayList<Object>();
        args.add(type);
        args.add(subtype);
        args.add(name);
        args.add(subName);
        args.add(score);
        args.add(logo);
        args.add(islive);
        args.add(id);
        try {
            eKShopTaskInfoService.updateTask(args);
        } catch (Exception e) {
            log.error("EKActivityAction.updateTask failed,", e);
        }
        return "reload";
    }

    /**
     * 获取特殊任务列表
     *
     * @return
     */
    public String getTSTask() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        String sPage = getParameter("page");// 获取page
        log.info("into EKShopTaskInfoAction.getTSTask");
        log.info("sPage=" + sPage);
        try {
            int page = 1;// 默认第一页
            if (null != sPage && sPage.matches("\\d+")) {
                page = Integer.parseInt(sPage);
            } else {
                log.info(sPage);
            }
            // 分页显示活动详情
            retmap = eKShopTaskInfoService.getTSTask(page);
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error(" EKShopTaskInfoAction.getTSTask failed, e : " + e);
        }
        return NONE;
    }

    /**
     * 跳转到维护页面
     *
     * @return
     */
    public String toHandle() {
        String tid = getParameter("editId");
        String id="";
        List<Map<String, Object>> appList = new ArrayList<>();
        List<Map<String, Object>> actList = new ArrayList<>();
        List<Map<String, Object>> gameList = new ArrayList<>();
        List<Map<String, Object>>  settingList= new ArrayList<>();
        if ("18".equals(tid)) { //e键首页任务 18
            appList = eKShopTaskInfoService.getAppList();
            id ="1";
        } else if ("19".equals(tid)) {//活动任务  19
            actList = eKShopTaskInfoService.getActList();
            id ="2";
        }else if("24".equals(tid)){//指定按键设置
            id ="4";
            settingList=eKShopTaskInfoService.getSettingList();
        }else if("20".equals(tid) || "21".equals(tid)|| "22".equals(tid)||"23".equals(tid)){//游戏任务
            gameList =eKShopTaskInfoService.getGameList();
            id ="3";
        }
        List<Map<String, Object>> taskInfo = eKShopTaskInfoService.selectOne(id,tid);
        Map<String, Object> map = taskInfo.get(0);
        String cid = StrUtils.emptyOrString(map.get("C_ID"));
        String sdate = StrUtils.emptyOrString(map.get("C_SDATE"));
        String edate = StrUtils.emptyOrString(map.get("C_EDATE"));
        String name = StrUtils.emptyOrString(map.get("C_SUBNAME"));
        String gid = StrUtils.emptyOrString(map.get("C_ITEM_ID"));
        //tid = StrUtils.emptyOrString(map.get("C_IDS"));
        getRequest().setAttribute("cid", cid);
        getRequest().setAttribute("tid", tid);
        getRequest().setAttribute("sdate", sdate);
        getRequest().setAttribute("edate", edate);
        getRequest().setAttribute("name", name);
        getRequest().setAttribute("gid", gid);
        getRequest().setAttribute("appList", appList);
        getRequest().setAttribute("actList", actList);
        getRequest().setAttribute("gameList", gameList);
        getRequest().setAttribute("settingList", settingList);
        return "toHandle";
    }

    /**
     * 修改特殊任务
     *
     * @return
     */
    public String updateInfo() {
        String id = getParameter("cid");// 获取cid
        String tid = getParameter("tid");
        String gid = getParameter("gid");
        String sdate = getParameter("sdate");
        String edate = getParameter("edate");
      /*  List<Object> args = new ArrayList<Object>();
        args.add(ApDateTime.getDate(cdate, ApDateTime.DATE_TIME_SSS));
        args.add(ApDateTime.getDate(edate, ApDateTime.DATE_TIME_SSS));
        args.add(gid);
        args.add(id);*/
        try {
            eKShopTaskInfoService.updateInfo(id, sdate, edate, gid,tid);
        } catch (Exception e) {
            log.error("EKActivityAction.updateInfo failed,", e);
        }
        return "reload2";
    }

    /**
     * 跳转特殊任务列表
     *
     * @return
     */
    public String toTSList() {

        return "toTSList";
    }

}
