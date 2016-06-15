package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKIndexSuppIndexAppService;
import com.org.mokey.common.AbstractAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.util.*;

/**
 * Created by vpc on 2016/3/22.
 * 首页 ： 定制app管理
 */
@Controller("eKIndexSuppIndexAppAction")
public class EKIndexSuppIndexAppAction extends AbstractAction {
    @Autowired
    private EKIndexSuppIndexAppService eKIndexSuppIndexAppService;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 首页 ：  定制App管理
     *
     * @return
     */
    public String toList() {
        List<Map<String, Object>> suppList = eKIndexSuppIndexAppService.getSuppList();
        List<Map<String, Object>> appList = eKIndexSuppIndexAppService.getAppLsit();
        getRequest().setAttribute("suppList", suppList);
        getRequest().setAttribute("appList", appList);
        return "toList";
    }

    /**
     * 首页 ：  定制APP  list
     *
     * @return
     */
    public String list() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        String code = getParameter("code");
        String aid = getParameter("aid");
        log.info("into EKIndexSuppIndexAppAction.list");
        log.info("page=" + page);
        try {
            retmap = eKIndexSuppIndexAppService.list(page, code, aid);// 分页查询
            retmap.put("status", "Y");
            this.writeJSONToResponse(retmap); // 回写查询结果
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKIndexSuppIndexAppAction.list failed, e : " + e);
        }
        return NONE;
    }

    public String toAdd() {
        List<Map<String, Object>> suppList = eKIndexSuppIndexAppService.getSuppList();
        List<Map<String, Object>> appList = eKIndexSuppIndexAppService.getAppLsit();
        getRequest().setAttribute("suppList", suppList);
        getRequest().setAttribute("appList", appList);
        return "toAdd";
    }

    /**
     * 首页 ：  定制APP  添加list
     *
     * @return
     */
    public String addList() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        String code = getParameter("code");
        log.info("into EKIndexSuppIndexAppAction.list");
        log.info("page=" + page);
        try {
            retmap = eKIndexSuppIndexAppService.addList(page, code);// 分页查询
            retmap.put("status", "Y");
            this.writeJSONToResponse(retmap); // 回写查询结果
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKIndexSuppIndexAppAction.addList failed, e : " + e);
        }
        return NONE;
    }

    /**
     * 首页 ：  定制APP  添加
     *
     * @return
     */
    public String add() {
        String aid = getParameter("aid");
        String code = getParameter("code");
        String order = getParameter("order");
        log.info("into EKIndexKeyAppAction.add");
        try {
            eKIndexSuppIndexAppService.add(aid, code, order); // 新增
        } catch (Exception e) {
            log.error("EKIndexSuppIndexAppAction.add failed,", e);
        }
        return "reload";
    }

    /**
     * 首页 ：  定制APP  删除
     *
     * @return
     */
    public String toDel() {
        String id = getParameter("cid");
        log.info("into EKIndexKeyAppAction.toDel");
        log.info("id=" + id);
        try {
            eKIndexSuppIndexAppService.toDel(id); // 删除
        } catch (Exception e) {
            log.error("EKIndexSuppIndexAppAction.toDel failed,", e);
        }
        return NONE;
    }

    /**
     * 首页 ：  定制APP  更新页面
     *
     * @return
     */
    public String toUpdate() {
        String id = getParameter("editId");
        List<Map<String, Object>> appList = eKIndexSuppIndexAppService.getAppLsit();
        List<Map<String, Object>> selectOne = eKIndexSuppIndexAppService.selectOne(id);
        if (selectOne.size() == 1) {
            for (Map item : selectOne) {
                getRequest().setAttribute("act", item);
            }
        }
        getRequest().setAttribute("appList", appList);
        return "toUpdate";
    }

    /**
     * 首页 ：  定制APP  更新
     *
     * @return
     */
    public String update() {
        String id = getParameter("cid");
        String order = getParameter("order");
        String aid = getParameter("aid");
        String islive = getParameter("islive");
        try {
            List<Object> args = new ArrayList<Object>();
            args.add(aid);
            args.add(order);
            args.add(islive);
            args.add(id);
            eKIndexSuppIndexAppService.update(args);
        } catch (Exception e) {
            log.error("EKIndexSuppIndexAppAction.update failed,", e);
        }
        return "reload";
    }


}
