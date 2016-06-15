package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKMallAdvertService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 广告位维护
 * Created by vpc on 2016/4/20.
 */
@Controller("eKMallAdvertAction")
public class EKMallAdvertAction extends AbstractAction {
    @Autowired
    private EKMallAdvertService eKMallAdvertService;

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
     * 加载列表页面
     *
     * @return
     */
    public String list() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        log.info("into EKMallAdvertAction.list");
        log.info("page=" + page);
        try {
            retmap = eKMallAdvertService.list(page);
            retmap.put("status", "Y");
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKMallAdvertAction.list failed, e : " + e);
        }
        return NONE;
    }

    /**
     * 跳转到添加页面
     *
     * @return
     */
    public String toAdd() {
        log.info("into EKMallAdvertAction.toAdd");
        try {
            // 获取主键
            String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_EK_MALL_ADVERT");
            //获取 商品 列表
            List<Map<String, Object>> list = eKMallAdvertService.getList();
            getRequest().setAttribute("addFlag", "1");// “新增”操作的标记
            getRequest().setAttribute("cid", newId);
            getRequest().setAttribute("list", list);
        } catch (Exception e) {
            log.error("EKMallAdvertAction.toAdd failed,", e);
        }
        return "toUpdate";
    }

    /**
     * 添加广告
     *
     * @return
     */
    public String addAdvert() {
        String id = getParameter("cid");// 获取cid
        String name = getParameter("name");
        String logo = getParameter("logo");
        String pid = getParameter("pid");
        String order = getParameter("order");

        log.info("into EKMallAdvertAction.addAdvert");
        log.info("id=" + id + ", name=" + name + ", logo=" + logo);
        try {
            eKMallAdvertService.addAdvert(id, name, logo, pid, order);
        } catch (Exception e) {
            log.error("EKMallAdvertAction.addAdvert failed,", e);
        }
        return "reload";
    }

    /**
     * 删除 广告
     *
     * @return
     */
    public String toDel() {
        // 获取cid
        String id = getParameter("cid");
        log.info("into EKMallAdvertAction.toDel");
        log.info("id=" + id);
        try {
            eKMallAdvertService.toDel(id);
        } catch (Exception e) {
            log.error("EKMallAdvertAction.toDel failed,", e);
        }
        return NONE;
    }

    /**
     * 跳转到更新页面
     *
     * @return
     */
    public String toUpdate() {
        // 获取请求参数
        String id = getParameter("editId");
        String name = getParameter("editName");
        String logo = getParameter("editLogo");
        String pid = getParameter("editPid");
        String order = getParameter("editOrder");
        //获取 app 应用
        List<Map<String, Object>> list = eKMallAdvertService.getList();
        log.info("into EKMallAdvertAction.toUpdate");
        log.info("id=" + id + ", name=" + name + ", logo=" + logo);
        try {
            getRequest().setAttribute("cid", id);
            getRequest().setAttribute("name", name);
            getRequest().setAttribute("logo", logo);
            getRequest().setAttribute("pid", pid);
            getRequest().setAttribute("order", order);
            getRequest().setAttribute("list", list);
        } catch (Exception e) {
            log.error("EKMallAdvertAction.toUpdate failed,", e);
        }
        return "toUpdate";
    }

    /**
     * 更新广告位
     *
     * @return
     */
    public String updateAdvert() {
        // 获取请求参数
        String id = getParameter("cid");
        String name = getParameter("name");
        String logo = getParameter("logo");
        String pid = getParameter("pid");
        String order = getParameter("order");
        log.info("into EKMallAdvertAction.updateAdvert");
        log.info("id=" + id + ", name=" + name + ", logo=" + logo);
        try {
            eKMallAdvertService.updateAdvert(id, name, logo, pid, order);
        } catch (Exception e) {
            log.error("EKMallAdvertAction.updateAdvert failed,", e);
        }
        return "reload";
    }


}

