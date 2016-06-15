package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKGameStrategyCateService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vpc on 2016/3/14.
 * e键 ： 游戏 ： 游戏攻略分类
 */
@Controller("eKGameStrategyCateAction")
public class EKGameStrategyCateAction extends AbstractAction {

   @Autowired
    private EKGameStrategyCateService eKGameStrategyCateService;

    private String out;

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

   // 游戏攻略分类（下拉菜单）
   public String getGameStrategyCateList() {
       Map<String, Object> retMap = new HashMap<String, Object>();
       log.info("into EKGameStrategyCateAction.getGameStrategyCateList");
       try {
           //List<Map<String, Object>> list = eKGameStrategyCateService.getGameStrategyCateList();

           //retMap.put("list", list);
           retMap.put("status", "Y");
       } catch (Exception e) {
           retMap.put("status", "N");
           retMap.put("info", "getAppIfoListByType failed");
           log.error("getGameStrategyCateList failed,", e);
       }
       out = JSONObject.fromObject(retMap).toString();
       log.info("out EKGameStrategyCateAction.getGameStrategyCateList");
       return SUCCESS;
   }

    // 跳转到游戏攻略分类首页
    public String toGameStrategyCateList() {
        return "toGameStrategyCateList";
    }

    // 首页显示的列表信息
    public String gameStrategyCateList() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        log.info("into EKGameStrategyCateAction.gameStrategyCateList");
        log.info("page=" + page);
        try {
            // 分页显示游戏攻略分类
            retmap = eKGameStrategyCateService.gameStrategyCateList(page);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKGameStrategyCateAction.gameStrategyCateList failed, e : " + e);
        }
        return NONE;
    }

    // 跳转到添加页面
    public String toStrategyCateAdd() {
        log.info("into EKGameStrategyCateAction.toStrategyCateAdd");
        try {
            // 获取主键
            String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_GAME_CATEGORY");
            getRequest().setAttribute("addFlag", "1");// “新增”操作的标记
            getRequest().setAttribute("cid", newId);
        } catch (Exception e) {
            log.error("EKGameStrategyCateAction.toStrategyCateAdd failed,", e);
        }
        return "toUpdate";
    }

    // 新增分类
    public String addGameStrategyCate() {
        String id = getParameter("cid");// 获取cid
        String name = getParameter("cname");// 分类名称
        String order = getParameter("order");// 排序
        String logo = getParameter("logo");// logo
        log.info("into EKGameStrategyCateAction.addGameStrategyCate");
        log.info("id=" + id + ", name=" + name + ", order=" + order + ", logo=" + logo);
        try {
            // 获取当前登录人
            String modifier = super.getSessionLoginUser().getUserName();
            // 新增
            eKGameStrategyCateService.addGameStrategyCate(id, name, logo, order, modifier);
        } catch (Exception e) {
            log.error("EKGameStrategyCateAction.addGameStrategyCate failed,", e);
        }
        return "reload";
    }

    // 删除分类
    public String delGameStrategyCate() {
        // 获取cid
        String id = getParameter("cid");
        log.info("into EKGameStrategyCateAction.delGameStrategyCate");
        log.info("id=" + id);
        try {
            // 删除
            eKGameStrategyCateService.delGameStrategyCate(id);
        } catch (Exception e) {
            log.error("EKGameStrategyCateAction.delGameStrategyCate failed,", e);
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
        log.info("into EKGameStrategyCateAction.AddGameStrategyCate");
        log.info("id=" + id + ", name=" + name + ", order=" + order + ", logo=" + logo);
        try {
            getRequest().setAttribute("cid", id);
            getRequest().setAttribute("cname", name);
            getRequest().setAttribute("order", order);
            getRequest().setAttribute("logo", logo);
        } catch (Exception e) {
            log.error("EKGameStrategyCateAction.toUpdate failed,", e);
        }
        return "toUpdate";
    }

    // 更新分类
    public String updateGameStrategyCate() {
        // 获取请求参数
        String id = getParameter("cid");
        String name = getParameter("cname");
        String order = getParameter("order");
        String logo = getParameter("logo");
        log.info("into EKGameStrategyCateAction.updateGameStrategyCate");
        log.info("id=" + id + ", name=" + name + ", order=" + order + ", logo=" + logo);
        try {
            // 获取当前登录人
            String modifier = super.getSessionLoginUser().getUserName();
            // 更新
            eKGameStrategyCateService.updateGameStrategyCate(id, name, logo, order, modifier);
        } catch (Exception e) {
            log.error("EKGameStrategyCateAction.toUpdate failed,", e);
        }
        return "reload";
    }

    // 跳转到分类修改页面
    public String toHandle() {
        // 获取请求参数
        String id = getParameter("editId");
        String name = getParameter("editName");
        log.info("into EKGameStrategyCateAction.AddGameStrategyCate");
        log.info("id=" + id + ", name=" + name);
        try {
            getRequest().setAttribute("cid", id);// 分类id
            getRequest().setAttribute("cname", name);
            // 所有游戏攻略分类
            getRequest().setAttribute("gameCate", eKGameStrategyCateService.getGameStrategyCateList());
        } catch (Exception e) {
            log.error("EKGameStrategyCateAction.toUpdate failed,", e);
        }
        return "toHandle";
    }

    // 查询非当前游戏攻略分类的其他所有游戏
    public String getAllGame() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        String cid = getParameter("cid");// 当前分类id
        String name = getParameter("name");// 游戏名称
        String gameCate = getParameter("gameCate");// 游戏初始分类
        log.info("into EKGameStrategyCateAction.getAllGame");
        log.info("page=" + page + ", cid=" + cid + ", name="
                + name + ", gameCate=" + gameCate);
        try {
            // 分页查询
            retmap = eKGameStrategyCateService.getAllGame(page, cid, name, gameCate);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKGameStrategyCateAction.getAllGame failed, e : " + e);
        }
        return NONE;
    }

    // 查询当前游戏攻略分类的游戏
    public String getCurrStrategyCateGame() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        String cid = getParameter("cid");// 当前分类id
        String name = getParameter("name");// 游戏名称
        log.info("into EKGameStrategyCateAction.getCurrStrategyCateGame");
        log.info("page=" + page + ", cid=" + cid + ", name=" + name);
        try {
            // 分页查询
            retmap = eKGameStrategyCateService.getCurrStrategyCateGame(page, cid, name);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKGameStrategyCateAction.getCurrStrategyCateGame failed, e : " + e);
        }
        return NONE;
    }

    // 分类维护
    public String handleStrategyCate() {
        // 获取请求参数
        String cid = getParameter("cid");// 获取分类id
        String sid = getRequest().getParameter("sid");// 获取应用id
        String removeGid = getRequest().getParameter("removeGid");// 获取要移除出当前分类的应用id
        String order = getRequest().getParameter("order");// 获取顺序
        log.info("into EKGameStrategyCateAction.handleStrategyCate");
        log.info("cid=" + cid + " ---- sid=" + sid + " ---- removeGid=" + removeGid + " ---- order=" + order);
        try {
            // 维护
            eKGameStrategyCateService.handleStrategyCate(cid, sid, removeGid, order);
        } catch (Exception e) {
            log.error("EKGameStrategyCateAction.handleStrategyCate failed,", e);
        }
        return NONE;
    }

}
