package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKIndexModelService;
import com.org.mokey.common.AbstractAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vpc on 2016/3/14.
 * e键 ： 首页  ： 系统应用模板维护
 */
@Controller("eKIndexModelAction")
public class EKIndexModelAction extends AbstractAction {
    @Autowired
    private EKIndexModelService eKIndexModelService;

    private String out;

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String toHandle() {

        return "toHandle";
    }

    // 查询所有的app应用
    public String getAllApp() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        String name = getParameter("name");// 游戏名称
        log.info("into EKIndexModelAction.getAllApp");
        log.info("page=" + page + ", name="
                + name );
        try {
            // 分页查询
            retmap = eKIndexModelService.getAllApp(page, name);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKGameCateAction.getAllGame failed, e : " + e);
        }
        return NONE;
    }

    // 查询当前app应用
    public String getCurrApp() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        String name = getParameter("name");// 游戏名称
        log.info("into EKIndexModelAction.getCurrApp");
        log.info("page=" + page + ", name=" + name);
        try {
            // 分页查询
            retmap = eKIndexModelService.getCurrApp(page, name);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKIndexModelAction.getCurrApp failed, e : " + e);
        }
        return NONE;
    }

    // 分类维护
    public String handleApp() {
        // 获取请求参数
        String cid = getRequest().getParameter("cid");// 获取应用id
        String removeGid = getRequest().getParameter("removeGid");// 获取要移除出当前分类的应用id
        String order = getRequest().getParameter("order");// 获取顺序
        String isEdit = getRequest().getParameter("isEdit");// 是否可编辑
        log.info("into EKGameCateAction.handleCate");
        log.info("cid=" + cid + " ---- isEdit=" + isEdit + " ---- removeGid=" + removeGid + " ---- order=" + order);
        try {
            // 维护
            eKIndexModelService.handleCate(cid, isEdit, removeGid, order);
        } catch (Exception e) {
            log.error("EKIndexModelAction.handleCate failed,", e);
        }
        return NONE;
    }

}
