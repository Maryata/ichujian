package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKActivityHeadLineService;
import com.org.mokey.common.AbstractAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ： 活动头条
 */
@Controller("eKActivityHeadLineAction")
public class EKActivityHeadLineAction extends AbstractAction {
    @Autowired
    private EKActivityHeadLineService ekActivityHeadLineService;
    private String out;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 跳转到活动头条维护页面
     *
     * @return
     */
    public String toHandle() {

        return "toHandle";
    }

    /**
     * 活动头条   查询活动分类   所有信息
     *
     * @return
     */
    public String getAllInfo() {
        String title = getParameter("title");
        String type = getParameter("type");
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        log.info("into eKActivityHeadLineAction.getAllInfo");
        log.info("page=" + page + ", title=" + title);
        try {
            if ("0".equals(type)) {
                retmap = ekActivityHeadLineService.getAllInfo(page, title);// 分页查询  活动分类
            } else {
                retmap = ekActivityHeadLineService.getAllActivityInfo(page, title);// 分页查询  活动
            }
            retmap.put("status", "Y");
            this.writeJSONToResponse(retmap);// 回写查询结果
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("eKActivityHeadLineAction.getAllInfo failed, e : " + e);
        }
        return NONE;
    }

    /**
     * 活动头条   查询活动头条中间表   所有活动分类信息
     *
     * @return
     */
    public String getCurrInfo() {
        String title = getParameter("title");
        String type = getParameter("type");
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        log.info("into eKActivityHeadLineAction.getCurrInfo");
        log.info("page=" + page + ", title=" + title);
        try {
            retmap = ekActivityHeadLineService.getCurrInfo(page, title, type);// 分页查询
            retmap.put("status", "Y");
            this.writeJSONToResponse(retmap);// 回写查询结果
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("eKActivityHeadLineAction.getCurrInfo failed, e : " + e);
        }
        return NONE;
    }

    /**
     * 活动头条 信息维护
     *
     * @return
     */
    public String handleActivityHeadLine() {
        String sid = getRequest().getParameter("sid");// 获取应用id
        String removeGid = getRequest().getParameter("removeGid");// 获取要移除出当前分类的应用id
        String order = getRequest().getParameter("order");// 获取顺序
        String name = getParameter("name");//活动名称
        String img = getParameter("img");//活动图片
        String type = getParameter("type");
        log.info("into EKActivityIndexCategoryAction.handleIndexCategory");
        log.info(" ---- sid=" + sid + " ---- removeGid=" + removeGid + " ---- order=" + order);
        try {
            ekActivityHeadLineService.handleActivityHeadLine(sid, name, img, removeGid, order,type);//维护
        } catch (Exception e) {
            log.error("EKActivityIndexCategoryAction.handleActivityHeadLine failed,", e);
        }
        return NONE;
    }


}
