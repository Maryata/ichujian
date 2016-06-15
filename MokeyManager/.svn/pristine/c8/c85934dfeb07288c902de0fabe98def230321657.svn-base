package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKGameGiftCateService;
import com.org.mokey.basedata.sysinfo.service.GameGiftCateService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * 游戏帮：游戏礼包分类
 */
@Controller("eKGameGiftCateAction")
public class EKGameGiftCateAction extends AbstractAction {

    @Autowired
    private EKGameGiftCateService eKGameGiftCateService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 跳转到礼包分类列表首页
    public String toGameGiftCateList() {
        return "toGameGiftCateList";
    }

    // 分页显示礼包分类
    public String gameGiftCateList() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        log.info("into EKGameGiftCateAction.gameGiftCateList");
        log.info("page=" + page);
        try {
            // 分页显示礼包分类
            retmap = eKGameGiftCateService.gameGiftCateList(page);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKGameGiftCateAction.gameGiftCateList failed, e : " + e);
        }
        return NONE;
    }

    // 跳转到添加页面
    public String toCateAdd() {
        log.info("into EKGameGiftCateAction.toCateAdd");
        try {
            // 获取主键
            String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_EK_GAME_APP_CATEGORY");
            getRequest().setAttribute("addFlag", "1");// “新增”操作的标记
            getRequest().setAttribute("cid", newId);
        } catch (Exception e) {
            log.error("EKGameGiftCateAction.toCateAdd failed,", e);
        }
        return "toUpdate";
    }

    // 新增分类
    public String addGameGiftCate() {
        String id = getParameter("cid");// 获取cid
        String name = getParameter("cname");// 分类名称
        String order = getParameter("order");// 排序
        String logo = getParameter("logo");// logo
        log.info("into EKGameGiftCateAction.addGameGiftCate");
        log.info("id=" + id + ", name=" + name + ", order=" + order + ", logo=" + logo);
        try {
            // 获取当前登录人
            String modifier = super.getSessionLoginUser().getUserName();
            // 新增
            eKGameGiftCateService.addGameGiftCate(id, name, logo, order, modifier);
        } catch (Exception e) {
            log.error("EKGameGiftCateAction.addGameGiftCate failed,", e);
        }
        return "reload";
    }

    // 删除分类
    public String delGameGiftCate() {
        // 获取cid
        String id = getParameter("cid");
        log.info("into EKGameGiftCateAction.delGameGiftCate");
        log.info("id=" + id);
        try {
            // 删除
            eKGameGiftCateService.delGameGiftCate(id);
        } catch (Exception e) {
            log.error("EKGameGiftCateAction.delGameGiftCate failed,", e);
        }
        return NONE;
    }

    // 跳转到分类修改页面
    public String toUpdate() {
        // 获取请求参数
        String id = getParameter("editId");
        String name = getParameter("editName");
        String order = getParameter("editOrder");
        String logo = getParameter("editLogo");
        log.info("into EKGameGiftCateAction.AddGameGiftCate");
        log.info("id=" + id + ", name=" + name + ", order=" + order + ", logo=" + logo);
        try {
            getRequest().setAttribute("cid", id);
            getRequest().setAttribute("cname", name);
            getRequest().setAttribute("order", order);
            getRequest().setAttribute("logo", logo);
        } catch (Exception e) {
            log.error("EKGameGiftCateAction.toUpdate failed,", e);
        }
        return "toUpdate";
    }

    // 更新分类
    public String updateGameGiftCate() {
        // 获取请求参数
        String id = getParameter("cid");
        String name = getParameter("cname");
        String order = getParameter("order");
        String logo = getParameter("logo");
        log.info("into EKGameGiftCateAction.updateGameGiftCate");
        log.info("id=" + id + ", name=" + name + ", order=" + order + ", logo=" + logo);
        try {
            // 获取当前登录人
            String modifier = super.getSessionLoginUser().getUserName();
            // 更新
            eKGameGiftCateService.updateGameGiftCate(id, name, logo, order, modifier);
        } catch (Exception e) {
            log.error("EKGameGiftCateAction.toUpdate failed,", e);
        }
        return "reload";
    }

    // 跳转到分类修改页面
    public String toHandle() {
        // 获取请求参数
        String id = getParameter("editId");// 礼包id
        String name = getParameter("editName");// 礼包名称
        log.info("into EKGameGiftCateAction.AddGameGiftCate");
        log.info("id=" + id + ", name=" + name);
        try {
            getRequest().setAttribute("cid", id);// 分类id
            getRequest().setAttribute("cname", name);
            // 所有礼包分类
            getRequest().setAttribute("giftCate", eKGameGiftCateService.getGiftCateList());
        } catch (Exception e) {
            log.error("EKGameGiftCateAction.toUpdate failed,", e);
        }
        return "toHandle";
    }

    // 查询非当前礼包分类的其他所有礼包
    public String getAllGameGift() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        String cid = getParameter("cid");// 当前分类id
        String name = getParameter("name");// 礼包名称
        String giftCate = getParameter("giftCate");// 礼包初始分类
        log.info("into EKGameGiftCateAction.getAllGameGift");
        log.info("page=" + page + ", cid=" + cid + ", name="
                + name + ", giftCate=" + giftCate);
        try {
            // 分页查询
            retmap = eKGameGiftCateService.getAllGameGift(page, cid, name, giftCate);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKGameGiftCateAction.getAllGameGift failed, e : " + e);
        }
        return NONE;
    }

    // 查询当前礼包分类的礼包
    public String getCurrCateGift() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        String cid = getParameter("cid");// 当前分类id
        String name = getParameter("name");// 礼包名称
        log.info("into EKGameGiftCateAction.getCurrCateGift");
        log.info("page=" + page + ", cid=" + cid + ", name=" + name);
        try {
            // 分页查询
            retmap = eKGameGiftCateService.getCurrCateGift(page, cid, name);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKGameGiftCateAction.getCurrCateGift failed, e : " + e);
        }
        return NONE;
    }

    // 分类维护
    public String handleCate() {
        // 获取请求参数
        String cid = getParameter("cid");// 获取分类id
        String gid = getRequest().getParameter("gid");// 获取应用id
        String removeGid = getRequest().getParameter("removeGid");// 获取要移除出当前分类的应用id
        String order = getRequest().getParameter("order");// 获取顺序
        log.info("into EKGameGiftCateAction.handleCate");
        log.info("cid=" + cid + " ---- gid=" + gid + " ---- removeGid=" + removeGid + " ---- order=" + order);
        try {
            // 维护
            eKGameGiftCateService.handleCate(cid, gid, removeGid, order);
        } catch (Exception e) {
            log.error("EKGameGiftCateAction.handleCate failed,", e);
        }
        return NONE;
    }

}
