package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKActivityIndexCategoryService;
import com.org.mokey.common.AbstractAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ： 首页分类管理
 */
@Controller("eKActivityIndexCategoryAction")
public class EKActivityIndexCategoryAction extends AbstractAction {
    @Autowired
    private EKActivityIndexCategoryService ekActivityIndexCategoryService;
    private String out;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 跳转到分类修改页面
    public String toHandle() {

        return "toHandle";
    }

    /**
     * 活动首页分类 管理   获取所有活动分类
     *
     * @return
     */
    public String getAllInfo() {
        String title = getParameter("title");
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        log.info("into EKActivityIndexCategoryAction.getAllInfo");
        log.info("page=" + page + ", title=" + title);
        try {
            retmap = ekActivityIndexCategoryService.getAllInfo(page, title);// 分页查询
            retmap.put("status", "Y");
            this.writeJSONToResponse(retmap);// 回写查询结果
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKActivityIndexCategoryAction.getAllInfo failed, e : " + e);
        }
        return NONE;
    }

    /**
     * 活动首页分类 管理  获取当前已添加的活动分类
     *
     * @return
     */
    public String getCurrInfo() {
        String title = getParameter("title");
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        log.info("into EKActivityIndexCategoryAction.getCurrInfo");
        log.info("page=" + page + ", title=" + title);
        try {
            retmap = ekActivityIndexCategoryService.getCurrInfo(page, title);// 分页查询
            retmap.put("status", "Y");
            this.writeJSONToResponse(retmap);// 回写查询结果
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKActivityIndexCategoryAction.getCurrInfo failed, e : " + e);
        }
        return NONE;
    }

    /**
     * 维护  首页分类管理
     *
     * @return
     */
    public String handleIndexCategory() {
        String sid = getRequest().getParameter("sid");// 获取应用id
        String removeGid = getRequest().getParameter("removeGid");// 获取要移除出当前分类的应用id
        String order = getRequest().getParameter("order");// 获取顺序
        log.info("into EKActivityIndexCategoryAction.handleIndexCategory");
        log.info(" ---- sid=" + sid + " ---- removeGid=" + removeGid + " ---- order=" + order);
        try {
            ekActivityIndexCategoryService.handleIndexCategory(sid, removeGid, order);//维护
        } catch (Exception e) {
            log.error("EKActivityIndexCategoryAction.handleIndexCategory failed,", e);
        }
        return NONE;
    }


}
