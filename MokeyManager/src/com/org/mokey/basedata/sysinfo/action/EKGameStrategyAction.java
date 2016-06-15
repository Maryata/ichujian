package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.AppInfoService;
import com.org.mokey.basedata.sysinfo.service.EKGameStrategyService;
import com.org.mokey.basedata.sysinfo.service.GameStrategyService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏攻略
 */
@Controller("eKGameStrategyAction")
public class EKGameStrategyAction extends AbstractAction {

    // 游戏攻略
    @Autowired
    private EKGameStrategyService eKGameStrategyService;

    // 游戏
    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 跳转到攻略列表页面
    public String toStrategyList() {
        log.info("into EKGameStrategyAction.toActList");
        try {
            // 查询所有游戏
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> games = appInfoService.getGameAppInfoList();
            getRequest().setAttribute("games", games);
        } catch (Exception e) {
            log.error("EKGameStrategyAction.toActList failed, e : " + e);
        }
        return "gameStrategyList";
    }

    // 查询攻略列表
    public String gameStrategyList() {
        String gid = getParameter("gid");// 游戏id
        String name = getParameter("name");// 攻略名
        String gName = getParameter("gName");// 游戏名
        String sPage = getParameter("page");// 获取page
        log.info("into EKGameStrategyAction.gameStrategyList");
        log.info("gid = " + gid + ", name = " + name + ", page = " + sPage);
        try {
            int page = 1;// 默认第一页
            if (null != sPage && sPage.matches("\\d+")) {
                page = Integer.parseInt(sPage);
            } else {
                log.info(sPage);
            }
            getRequest().getSession().setAttribute("isFromEdit", "");
            List<Map<String, Object>> list = eKGameStrategyService.gameStrategyList(gid, name, page, gName);
            super.writeJSONToResponse(list);
        } catch (Exception e) {
            log.error("EKGameStrategyAction.gameStrategyList failed, e : " + e);
        }
        return NONE;
    }

    // 获取攻略总数
    public String getTotal() {
        String gid = getParameter("gid");// 游戏id
        String gName = getParameter("gName");// 游戏名
        String name = getParameter("name");// 攻略名
        log.info("into EKGameStrategyAction.getTotalCol");
        log.info("gid = " + gid + ", name = " + name);
        try {
            // 总条数
            Integer total = eKGameStrategyService.getTotal(gid, name, gName);
            // 计算总页数
            Integer rows = 10;// 每页10条
            Integer totalPage = (total - 1) / rows + 1;
            // 回写查询结果
            super.writeJSONToResponse(totalPage);
        } catch (Exception e) {
            log.error("EKGameStrategyAction.getTotal failed,", e);
        }
        return NONE;
    }

    // 根据id删除游戏攻略
    public String deleteGameStrategy() {
        String id = getParameter("id");// 攻略id
        log.info("into EKGameStrategyAction.GameStrategyAction");
        log.info("id = " + id);
        try {
            // 删除
            eKGameStrategyService.deleteGameStrategy(id);
        } catch (Exception e) {
            log.error("GameCollectionAction.getTotal failed,", e);
        }
        return NONE;
    }

    // 跳转到攻略添加页面
    public String toGameStrategyAdd() {
        log.info("into EKGameStrategyAction.toGameStrategyAdd");
        try {
            // 查询所有游戏
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> games = appInfoService.getGameAppInfoList();
            String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_EK_GAME_STRATEGY_INFO");
            getRequest().setAttribute("games", games);
            getRequest().setAttribute("newId", newId);
            getRequest().setAttribute("flag", 1);
        } catch (Exception e) {
            log.error("EKGameStrategyAction.toGameStrategyAdd failed, e : " + e);
        }
        return "toGameStrategyAdd";
    }

    // 跳转到攻略编辑页面
    public String toGameStrategyEdit() {
        String editId = getParameter("editId");
        log.info("into EKGameStrategyAction.toGameStrategyEdit");
        log.info("editId = " + editId);
        try {
            // 查询所有游戏
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> games = appInfoService.getGameAppInfoList();
            getRequest().setAttribute("games", games);
            // 根据id查询攻略
            List<Map<String, Object>> list = eKGameStrategyService.queryStrategyById(editId);
            String editGid = "";
            String editOrder = "";
            String editName = "";
            String editDepict = "";
            String editLogourl = "";
            if (StrUtils.isNotEmpty(list)) {
                editGid = (list.get(0).get("C_GID")) == null ? "" : list.get(0).get("C_GID").toString();
                editOrder = (list.get(0).get("C_ORDER")) == null ? "" : list.get(0).get("C_ORDER").toString();
                editName = (list.get(0).get("C_NAME")) == null ? "" : list.get(0).get("C_NAME").toString();
                editDepict = (list.get(0).get("C_DEPICT")) == null ? "" : list.get(0).get("C_DEPICT").toString();
                editLogourl = (list.get(0).get("C_LOGOURL")) == null ? "" : list.get(0).get("C_LOGOURL").toString();
            }
            getRequest().setAttribute("newId", editId);
            getRequest().setAttribute("editGid", editGid);
            getRequest().setAttribute("editOrder", editOrder);
            getRequest().setAttribute("editName", editName);
            getRequest().setAttribute("editDepict", editDepict);
            getRequest().setAttribute("logourl", editLogourl);
            getRequest().getSession().setAttribute("pageNum2Edit", getParameter("pageNum2Edit"));
        } catch (Exception e) {
            log.error("EKGameStrategyAction.toGameStrategyEdit failed, e : " + e);
        }
        return "toGameStrategyAdd";
    }

    // 保存攻略
    public String saveGameStrategy() {
        String id = getParameter("id");// 攻略id
        String gid = getParameter("gid");// 游戏id
        String order = getParameter("order");// 游戏id
        String name = getParameter("name");// 攻略标题
        String depict = getParameter("depict");// 攻略详情
        String logourl = getParameter("logourlScan");// 攻略LOGO
        log.info("into EKGameStrategyAction.saveGameStrategy");
        log.info("id = " + id + ", gid = " + gid + ", order = " + order + ", name = "
                + name + ", depict = " + depict + ", logourl = " + logourl);
        try {
            eKGameStrategyService.saveGameStrategy(id, gid, order, name, depict, logourl);
            getRequest().getSession().setAttribute("isFromEdit", "1");
        } catch (Exception e) {
            log.error("EKGameStrategyAction.saveGameStrategy failed, e : " + e);
        }
        return "saveGameStrategy";
    }

    // 查询所有攻略id和其对应的攻略内容（仅用于给所有图片设置尺寸）
    public String selectDepict() {
        try {
            List<Map<String, Object>> list = eKGameStrategyService.selectDepict();
            super.writeJSONToResponse(list);
        } catch (Exception e) {
            log.error("EKGameStrategyAction.selectDepict failed, e : " + e);
        }
        return NONE;
    }

    // 给所有图片设置尺寸
    public String setImgSize() {
        String id = getRequest().getParameter("id");
        String depict = getRequest().getParameter("depict");
        String flag = getRequest().getParameter("flag");
        try {
            int result = eKGameStrategyService.setImgSize(id, depict, flag);
            super.writeJSONToResponse(result);
            log.info("res:" + result);
        } catch (Exception e) {
            log.error("EKGameStrategyAction.setImgSize failed, e : " + e);
        }
        return NONE;
    }
}
