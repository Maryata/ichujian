package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.AppInfoService;
import com.org.mokey.basedata.sysinfo.service.EKGameInfoService;
import com.org.mokey.basedata.sysinfo.service.GameInfoService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏资讯
 */
@Controller("eKGameInfoAction")
public class EKGameInfoAction extends AbstractAction {

    // 游戏资讯
    @Autowired
    private EKGameInfoService eKGameInfoService;

    // 游戏
    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 跳转到资讯列表页面
    public String toInfoList() {
        log.info("into EKGameInfoAction.toInfoList");
        try {
            // 查询所有游戏
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> games = appInfoService.getGameAppInfoList();
            getRequest().setAttribute("games", games);
        } catch (Exception e) {
            log.error("EKGameInfoAction.toInfoList failed, e : " + e);
        }
        return "gameInfoList";
    }

    // 查询资讯列表
    public String gameInfoList() {
        String gid = getParameter("gid");// 游戏id
        String gName = getParameter("gName");// 游戏名
        String name = getParameter("name");// 资讯名
        String sPage = getParameter("page");// 获取page
        log.info("into EKGameInfoAction.gameInfoList");
        log.info("gid = " + gid + ", name = " + name + ", page = " + sPage);
        try {
            int page = 1;// 默认第一页
            if (null != sPage && sPage.matches("\\d+")) {
                page = Integer.parseInt(sPage);
            } else {
                log.info(sPage);
            }
            List<Map<String, Object>> list = eKGameInfoService.gameInfoList(gid, name, page, gName);
            super.writeJSONToResponse(list);
        } catch (Exception e) {
            log.error("EKGameInfoAction.gameInfoList failed, e : " + e);
        }
        return NONE;
    }

    // 获取资讯总数
    public String getTotal() {
        String gid = getParameter("gid");// 游戏id
        String gName = getParameter("gName");// 游戏名
        String name = getParameter("name");// 资讯名
        log.info("into EKGameInfoAction.getTotalCol");
        log.info("gid = " + gid + ", name = " + name);
        try {
            // 总条数
            Integer total = eKGameInfoService.getTotal(gid, name, gName);
            // 计算总页数
            Integer rows = 10;// 每页10条
            Integer totalPage = (total - 1) / rows + 1;
            // 回写查询结果
            super.writeJSONToResponse(totalPage);
        } catch (Exception e) {
            log.error("EKGameInfoAction.getTotal failed,", e);
        }
        return NONE;
    }

    // 根据id删除游戏资讯
    public String deleteGameInfo() {
        String id = getParameter("id");// 资讯id
        log.info("into EKGameInfoAction.GameInfoAction");
        log.info("id = " + id);
        try {
            // 删除
            eKGameInfoService.deleteGameInfo(id);
        } catch (Exception e) {
            log.error("GameCollectionAction.getTotal failed,", e);
        }
        return NONE;
    }

    // 跳转到资讯添加页面
    public String toGameInfoAdd() {
        log.info("into EKGameInfoAction.toGameInfoAdd");
        try {
            // 查询所有游戏
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> games = appInfoService.getGameAppInfoList();
            String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_EK_GAME_INFORMATION_INFO");
            getRequest().setAttribute("games", games);
            getRequest().setAttribute("newId", newId);
            getRequest().setAttribute("flag", 1);
        } catch (Exception e) {
            log.error("EKGameInfoAction.toGameInfoAdd failed, e : " + e);
        }
        return "toGameInfoAdd";
    }

    // 跳转到资讯编辑页面
    public String toGameInfoEdit() {
        String editId = getParameter("editId");
        log.info("into EKGameInfoAction.toGameInfoEdit");
        log.info("editId = " + editId);
        try {
            // 查询所有游戏
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> games = appInfoService.getGameAppInfoList();
            getRequest().setAttribute("games", games);
            // 根据id查询资讯
            List<Map<String, Object>> list = eKGameInfoService.queryInfoById(editId);
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
        } catch (Exception e) {
            log.error("EKGameInfoAction.toGameInfoEdit failed, e : " + e);
        }
        return "toGameInfoAdd";
    }

    // 保存资讯
    public String saveGameInfo() {
        String id = getParameter("id");// 资讯id
        String gid = getParameter("gid");// 游戏id
        String order = getParameter("order");// 游戏id
        String name = getParameter("name");// 资讯标题
        String depict = getParameter("depict");// 资讯详情
        String logourl = getParameter("logourlScan");// 资讯LOGO
        log.info("into EKGameInfoAction.saveGameInfo");
        log.info("id = " + id + ", gid = " + gid + ", order = " + order + ", name = "
                + name + ", depict = " + depict + ", logourl = " + logourl);
        try {
            eKGameInfoService.saveGameInfo(id, gid, order, name, depict, logourl);
        } catch (Exception e) {
            log.error("EKGameInfoAction.saveGameInfo failed, e : " + e);
        }
        return "saveGameInfo";
    }
}
