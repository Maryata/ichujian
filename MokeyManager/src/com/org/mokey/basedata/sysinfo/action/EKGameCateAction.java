package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.common.AbstractAction;
import org.springframework.stereotype.Controller;

/**
 * Created by vpc on 2016/3/14.
 * e键 ： 游戏 ： 游戏攻略分类
 */
@Controller("eKGameCateAction")
public class EKGameCateAction extends AbstractAction {
/*
   @Autowired
    private EKGameCateService eKGameCateService1;

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
   public String getGameateList() {
       Map<String, Object> retMap = new HashMap<String, Object>();
       log.info("into EKGameCateAction.getGameCateList");
       try {
           //List<Map<String, Object>> list = eKGameCateService.getGameCateList();

           //retMap.put("list", list);
           retMap.put("status", "Y");
       } catch (Exception e) {
           retMap.put("status", "N");
           retMap.put("info", "getAppIfoListByType failed");
           log.error("getGameCateList failed,", e);
       }
       out = JSONObject.fromObject(retMap).toString();
       log.info("out EKGameCateAction.getGameCateList");
       return SUCCESS;
   }

    // 跳转到游戏攻略分类首页
    public String toGameCateList() {
        return "toGameCateList";
    }

    // 首页显示的列表信息
    public String gameCateList() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        log.info("into EKGameCateAction.gameCateList");
        log.info("page=" + page);
        try {
            // 分页显示游戏攻略分类
            retmap = eKGameCateService.gameCateList(page);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKGameCateAction.gameCateList failed, e : " + e);
        }
        return NONE;
    }

    // 跳转到添加页面
    public String toCateAdd() {
        log.info("into EKGameCateAction.toCateAdd");
        try {
            // 获取主键
            String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_GAME_CATEGORY");
            getRequest().setAttribute("addFlag", "1");// “新增”操作的标记
            getRequest().setAttribute("cid", newId);
        } catch (Exception e) {
            log.error("EKGameCateAction.toCateAdd failed,", e);
        }
        return "toUpdate";
    }

    // 新增分类
    public String addGameCate() {
        String id = getParameter("cid");// 获取cid
        String name = getParameter("cname");// 分类名称
        String order = getParameter("order");// 排序
        String logo = getParameter("logo");// logo
        log.info("into EKGameCateAction.addGameCate");
        log.info("id=" + id + ", name=" + name + ", order=" + order + ", logo=" + logo);
        try {
            // 获取当前登录人
            String modifier = super.getSessionLoginUser().getUserName();
            // 新增
            eKGameCateService.addGameCate(id, name, logo, order, modifier);
        } catch (Exception e) {
            log.error("EKGameCateAction.addGameCate failed,", e);
        }
        return "reload";
    }

    // 删除分类
    public String delGameCate() {
        // 获取cid
        String id = getParameter("cid");
        log.info("into EKGameCateAction.delGameCate");
        log.info("id=" + id);
        try {
            // 删除
            eKGameCateService.delGameCate(id);
        } catch (Exception e) {
            log.error("EKGameCateAction.delGameCate failed,", e);
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
        log.info("into EKGameCateAction.AddGameCate");
        log.info("id=" + id + ", name=" + name + ", order=" + order + ", logo=" + logo);
        try {
            getRequest().setAttribute("cid", id);
            getRequest().setAttribute("cname", name);
            getRequest().setAttribute("order", order);
            getRequest().setAttribute("logo", logo);
        } catch (Exception e) {
            log.error("EKGameCateAction.toUpdate failed,", e);
        }
        return "toUpdate";
    }

    // 更新分类
    public String updateGameCate() {
        // 获取请求参数
        String id = getParameter("cid");
        String name = getParameter("cname");
        String order = getParameter("order");
        String logo = getParameter("logo");
        log.info("into EKGameCateAction.updateGameCate");
        log.info("id=" + id + ", name=" + name + ", order=" + order + ", logo=" + logo);
        try {
            // 获取当前登录人
            String modifier = super.getSessionLoginUser().getUserName();
            // 更新
            eKGameCateService.updateGameCate(id, name, logo, order, modifier);
        } catch (Exception e) {
            log.error("EKGameCateAction.toUpdate failed,", e);
        }
        return "reload";
    }

    // 跳转到分类修改页面
    public String toHandle() {
        // 获取请求参数
        String id = getParameter("editId");
        String name = getParameter("editName");
        log.info("into EKGameCateAction.AddGameCate");
        log.info("id=" + id + ", name=" + name);
        try {
            getRequest().setAttribute("cid", id);// 分类id
            getRequest().setAttribute("cname", name);
            // 所有游戏攻略分类
            getRequest().setAttribute("gameCate", eKGameCateService.getGameCateList());
        } catch (Exception e) {
            log.error("EKGameCateAction.toUpdate failed,", e);
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
        log.info("into EKGameCateAction.getAllGame");
        log.info("page=" + page + ", cid=" + cid + ", name="
                + name + ", gameCate=" + gameCate);
        try {
            // 分页查询
            retmap = eKGameCateService.getAllGame(page, cid, name, gameCate);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKGameCateAction.getAllGame failed, e : " + e);
        }
        return NONE;
    }

    // 查询当前游戏攻略分类的游戏
    public String getCurrCateGame() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        String cid = getParameter("cid");// 当前分类id
        String name = getParameter("name");// 游戏名称
        log.info("into EKGameCateAction.getCurrCateGame");
        log.info("page=" + page + ", cid=" + cid + ", name=" + name);
        try {
            // 分页查询
            retmap = eKGameCateService.getCurrCateGame(page, cid, name);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKGameCateAction.getCurrCateGame failed, e : " + e);
        }
        return NONE;
    }

    // 分类维护
    public String handleCate() {
        // 获取请求参数
        String cid = getParameter("cid");// 获取分类id
        String sid = getRequest().getParameter("sid");// 获取应用id
        String removeGid = getRequest().getParameter("removeGid");// 获取要移除出当前分类的应用id
        String order = getRequest().getParameter("order");// 获取顺序
        log.info("into EKGameCateAction.handleCate");
        log.info("cid=" + cid + " ---- sid=" + sid + " ---- removeGid=" + removeGid + " ---- order=" + order);
        try {
            // 维护
            eKGameCateService.handleCate(cid, sid, removeGid, order);
        } catch (Exception e) {
            log.error("EKGameCateAction.handleCate failed,", e);
        }
        return NONE;
    }*/

}
