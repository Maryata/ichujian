package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKGameCollectionService;
import com.org.mokey.basedata.sysinfo.service.GameCollectionService;
import com.org.mokey.common.AbstractAction;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏合集
 */
@Controller("eKGameCollectionAction")
public class EKGameCollectionAction extends AbstractAction {

    @Autowired
    private EKGameCollectionService eKGameCollectionService;

    // 获取合集列表
    public String gameCol() {
        // 获取cid
        String cid = getParameter("cid");
        log.info("into EKGameCollectionAction.gameCol");
        log.info("cid=" + cid);
        try {
            // 所有合集
            List<Map<String, Object>> list = this.getById(null);
            // 存入值栈
            getValueStack().set("gameCol", list);
        } catch (Exception e) {
            log.error("EKGameCollectionAction.gameCol failed,", e);
        }
        return "gameCol";
    }

    // 添加合集
    public String addCol() {
        // 获取合集名
        String name = getParameter("addName");
        String type = getParameter("addType");
        log.info("into EKGameCollectionAction.addCol");
        log.info("name = " + name + ", addType = " + type);
        try {
            // 添加
            eKGameCollectionService.addCol(name, type);
        } catch (Exception e) {
            log.error("EKGameCollectionAction.addCol failed,", e);
        }
        return NONE;
    }

    // 删除合集
    public String deleteCol() {
        // 获取合集id
        String cid = getParameter("cid");
        log.info("into EKGameCollectionAction.deleteCol");
        log.info("cid=" + cid);
        try {
            // 删除
            eKGameCollectionService.deleteCol(cid);
        } catch (Exception e) {
            log.error("EKGameCollectionAction.deleteCol failed,", e);
        }
        return "deleteCol";
    }

    // 跳转到修改页面
    public String toModify() {
        // 获取合集id
        String cid = getParameter("cid");
        String cname = getParameter("cname");
        log.info("into EKGameCollectionAction.toModify");
        log.info("cid=" + cid + ", cname=" + cname);
        try {
            // 存入值栈
            getRequest().setAttribute("cid", cid);
            getRequest().setAttribute("cname", cname);
        } catch (Exception e) {
            log.error("EKGameCollectionAction.toModify failed,", e);
        }
        return "toModify";
    }

    // 修改合集
    public String modifyCol() {
        String cid = getParameter("cid");// 获取合集id
        String cname = getParameter("cname"); // 获取合集名称
        String islive = getParameter("islive"); // 是否有效
        log.info("into EKGameCollectionAction.modifyCol");
        log.info("cid=" + cid + ", cname=" + cname + ", islive=" + islive);
        try {
            // 修改
            eKGameCollectionService.modifyCol(cid, cname, islive);
        } catch (Exception e) {
            log.error("EKGameCollectionAction.modifyCol failed,", e);
        }
        return NONE;
    }

    // 使合集不可用/可用
    public String isvalid() {
        // 获取合集id
        String cid = getParameter("cid");
        // 获取有效性
        String islive = getParameter("islive");
        log.info("into EKGameCollectionAction.isvalid");
        log.info("cid=" + cid + ", islive=" + islive);
        try {
            // 失效
            eKGameCollectionService.isvalid(cid, islive);
        } catch (Exception e) {
            log.error("EKGameCollectionAction.isvalid failed, ", e);
        }
        return "isvalid";
    }

    // 跳转到维护页面
    public String toHandle() {
        // 获取合集id
        String cid = getParameter("addCid");
        // 获取合集名称
        String cname = getParameter("addCname");
        // 获取类型
        String type = getParameter("type");
        log.info("into EKGameCollectionAction.toHandle");
        log.info("cid = " + cid + ", cname = " + cname + ", type = " + type);
        try {
            getRequest().setAttribute("cid", cid);
            getRequest().setAttribute("cname", cname);
            getRequest().setAttribute("type", type);
        } catch (Exception e) {
            log.error("EKGameCollectionAction.toHandle failed, ", e);
        }
        return "toHandle";
    }

    // 查询不属于当前合集的游戏
    public String getGameList() {
        // 获取page
        String sPage = getParameter("page");
        // 获取合集id
        String cid = getParameter("cid");
        String type = getParameter("type");
        log.info("into EKGameCollectionAction.getGameList");
        log.info("page = " + sPage + ", cid = " + cid + ", type = " + type);
        try {
            int page = 1;// 默认第一页
            if (null != sPage && sPage.matches("\\d+")) {
                page = Integer.parseInt(sPage);
            } else {
                log.info(sPage);
            }
            // 查询不属于当前合集的游戏
            List<Map<String, Object>> gameList = eKGameCollectionService.getGameList(page, cid, type);
            this.writeJSONToResponse(gameList);
        } catch (Exception e) {
            log.error("EKGameCollectionAction.getGameList failed,", e);
        }
        return NONE;
    }

    // 查询不属于当前合集的游戏总页数
    public String getTotal() {
        // 获取合集id
        String cid = getParameter("cid");
        String type = getParameter("type");
        log.info("into EKGameCollectionAction.getTotal");
        log.info("cid = " + cid + ", type = " + type);
        try {
            // 总条数
            Integer total = eKGameCollectionService.getTotal(cid, type);
            // 总页数
            Integer rows = 5;
            Integer totalPage = 0;
            if (total == 0) {
                totalPage = 0;
            } else {
                totalPage = (total - 1) / rows + 1;
            }
            // 回写查询结果
            HttpServletResponse response = getResponse();
            response.setContentType("text/html;charset=UTF-8");
            JSONArray json = JSONArray.fromObject(totalPage);
            response.getWriter().write(json.toString());
        } catch (Exception e) {
            log.error("EKGameCollectionAction.getTotal failed,", e);
        }
        return NONE;
    }

    // 根据合集id查询合集对应的游戏页数
    public String getTotalCol() {
        // 获取合集id
        String cid = getParameter("cid");
        String type = getParameter("type");
        log.info("into EKGameCollectionAction.getTotalCol");
        log.info("cid=" + cid + ", type = " + type);
        try {
            // 总条数
            Integer total = eKGameCollectionService.getTotalCol(cid, type);
            // 总页数
            Integer rows = 5;
            Integer totalPage = 0;
            if (total == 0) {
                totalPage = 0;
            } else {
                totalPage = (total - 1) / rows + 1;
            }
            // 回写查询结果
            HttpServletResponse response = getResponse();
            response.setContentType("text/html;charset=UTF-8");
            JSONArray json = JSONArray.fromObject(totalPage);
            response.getWriter().write(json.toString());
        } catch (Exception e) {
            log.error("EKGameCollectionAction.getTotal failed,", e);
        }
        return NONE;
    }

    // 对游戏进行合集分类
    public String handleCol() {
        // 获取合集id
        String cid = getParameter("cid");
        // 获取游戏id
        String gid = getRequest().getParameter("gid");
        String[] gids = gid.split(",");// 切割
        // 获取要移除出当前合集的游戏id
        String removeGid = getRequest().getParameter("removeGid");
        String[] removeGids = removeGid.split(",");// 切割
        // 获取顺序
        String order = getRequest().getParameter("order");
        String[] orders = order.split(",");// 切割
        log.info("into EKGameCollectionAction.handleCol");
        log.info("cid=" + cid + ", gids=" + gids + ", removeGids=" + removeGids + ", orders=" + orders);
        try {
            // 分类
            eKGameCollectionService.handleCol(cid, gids, removeGids, orders);
        } catch (Exception e) {
            log.error("EKGameCollectionAction.handleCol failed,", e);
        }
        return NONE;
    }

    // 根据合集id查询游戏
    public String getGamePageByColId() {
        // 获取cid
        String cid = getParameter("cid");
        // 获取page
        String sPage = getParameter("page");
        String type = getParameter("type");
        log.info("into EKGameCollectionAction.getGamePageByColId");
        log.info("cid=" + cid + ", page=" + sPage + ", type=" + type);
        try {
            int page = 1;// 默认第一页
            if (null != sPage && sPage.matches("\\d+")) {
                page = Integer.parseInt(sPage);
            } else {
                log.info(sPage);
            }
            List<Map<String, Object>> list = eKGameCollectionService.getGamePageByColId(cid, page, type);
            if (null != list && list.size() > 0) {
                for (Map<String, Object> map : list) {
                    Object obj = map.get("C_ORDER");
                    String order = (obj == null) ? "" : obj.toString();
                    map.put("C_ORDER", order);
                }
            }
            this.writeJSONToResponse(list);
        } catch (Exception e) {
            log.error("EKGameCollectionAction.getGamePageByColId failed,", e);
        }
        return NONE;
    }

    // 条件查询游戏APP
    public String queryGameCondition() {
        // 获取请求参数
        String cid = getParameter("cid");
        String type = getParameter("type");
        String name = getParameter("name");
        String minSize = getParameter("minSize");
        String maxSize = getParameter("maxSize");
        String sPage = getParameter("page");
        log.info("into EKGameCollectionAction.queryGameCondition");
        log.info("cid=" + cid + ", name=" + name + ", type=" + type + ", minSize="
                + minSize + ", maxSize=" + maxSize + ", page=" + sPage);
        try {
            int page = 1;// 默认第一页
            if (null != sPage && sPage.matches("\\d+")) {
                page = Integer.parseInt(sPage);
            } else {
                log.info(sPage);
            }
            // 条件查询
            List<Map<String, Object>> list = eKGameCollectionService.queryGameCondition(cid, name, type, minSize, maxSize, page);
            // 返回json
            this.writeJSONToResponse(list);
        } catch (Exception e) {
            log.error("EKGameCollectionAction.queryGameCondition failed,", e);
        }
        return NONE;
    }

    // 条件查询游戏总数
    public String getTotalCondition() {
        // 获取请求参数
        String cid = getParameter("cid");
        String type = getParameter("type");
        String name = getParameter("name");
        String minSize = getParameter("minSize");
        String maxSize = getParameter("maxSize");
        log.info("into EKGameCollectionAction.getTotalCondition");
        log.info("cid=" + cid + ", name=" + name + ", type=" + type
                + ", minSize=" + minSize + ", maxSize=" + maxSize);
        try {
            // 总条数
            Integer total = eKGameCollectionService.getTotalCondition(cid, name, type, minSize, maxSize);
            // 总页数
            Integer rows = 5;
            Integer totalPage = 0;
            if (total == 0) {
                totalPage = 0;
            } else {
                totalPage = (total - 1) / rows + 1;
            }
            // 回写查询结果
            HttpServletResponse response = getResponse();
            response.setContentType("text/html;charset=UTF-8");
            JSONArray json = JSONArray.fromObject(totalPage);
            response.getWriter().write(json.toString());
        } catch (Exception e) {
            log.error("EKGameCollectionAction.getTotalCondition failed,", e);
        }
        return NONE;
    }

    // 条件查询合集游戏总数
    public String getColTotalCondition() {
        // 获取请求参数
        String cid = getParameter("cid");
        String name = getParameter("name");
        String type = getParameter("type");
        log.info("into EKGameCollectionAction.getColTotalCondition");
        log.info("cid=" + cid + ", name=" + name + ", type=" + type);
        try {
            // 总条数
            Integer total = eKGameCollectionService.getColTotalCondition(cid, name, type);
            // 总页数
            Integer rows = 5;
            Integer totalPage = 0;
            if (total == 0) {
                totalPage = 0;
            } else {
                totalPage = (total - 1) / rows + 1;
            }
            // 回写查询结果
            this.writeJSONToResponse(totalPage);
        } catch (Exception e) {
            log.error("EKGameCollectionAction.getColTotalCondition failed,", e);
        }
        return NONE;
    }

    // 条件查询游戏APP
    public String queryColGameCondition() {
        // 获取请求参数
        String cid = getParameter("cid");
        String name = getParameter("name");
        String type = getParameter("type");
        String sPage = getParameter("page");
        log.info("into EKGameCollectionAction.queryColGameCondition");
        log.info("cid=" + cid + ", name=" + name + ", page=" + sPage + ", type=" + type);
        try {
            int page = 1;// 默认第一页
            if (null != sPage && sPage.matches("\\d+")) {
                page = Integer.parseInt(sPage);
            } else {
                log.info(sPage);
            }
            // 条件查询
            List<Map<String, Object>> list = eKGameCollectionService.queryColGameCondition(cid, name, page, type);
            // 返回json
            this.writeJSONToResponse(list);
        } catch (Exception e) {
            log.error("EKGameCollectionAction.queryColGameCondition failed,", e);
        }
        return NONE;
    }

    // 根据合集id获取合集
    private List<Map<String, Object>> getById(String cid) {
        return eKGameCollectionService.gameCol(cid);
    }

}
