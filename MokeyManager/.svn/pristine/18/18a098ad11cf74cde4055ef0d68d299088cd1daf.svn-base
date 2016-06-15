package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKGameCueService;
import com.org.mokey.basedata.sysinfo.service.GameCueService;
import com.org.mokey.common.AbstractAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏提示语
 */
@Controller("eKGameCueAction")
public class EKGameCueAction extends AbstractAction {

    @Autowired
    private EKGameCueService eKGameCueService;

    // 获取提示语列表
    public String gameCue() {
        log.info("into EKGameCueAction.gameCue");
        try {
            // 所有合集
            List<Map<String, Object>> list = eKGameCueService.gameCue();
            // 存入值栈
            getValueStack().set("gameCue", list);
        } catch (Exception e) {
            log.error("EKGameCueAction.gameCue failed,", e);
        }
        return "gameCue";
    }

    // 新增提示语
    public String addCue() {
        // 获取请求参数
        String addTitle = getParameter("addTitle");
        log.info("into EKGameCueAction.addCue");
        log.info("addTitle=" + addTitle);
        try {
            // 添加提示语
            eKGameCueService.addCue(addTitle);
        } catch (Exception e) {
            log.error("EKGameCueAction.addCue failed,", e);
        }
        return NONE;
    }

    // 修改提示语
    public String modifyCue() {
        // 获取请求参数
        String cid = getParameter("cid");// 获取提示id
        String title = getParameter("title"); // 获取提示内容
        String islive = getParameter("islive"); // 是否有效
        log.info("into EKGameCueAction.modifyCue");
        log.info("cid=" + cid + " ,title=" + title + " ,islive=" + islive);
        try {
            // 修改
            eKGameCueService.modifyCue(cid, title, islive);
        } catch (Exception e) {
            log.error("EKGameCueAction.modifyCue failed,", e);
        }
        return NONE;
    }

    // 使合集不可用/可用
    public String isvalid() {
        // 获取提示id
        String cid = getParameter("cid");
        // 获取有效性
        String islive = getParameter("islive");
        log.info("into EKGameCueAction.isvalid");
        log.info("cid=" + cid + " ,islive=" + islive);
        try {
            // 失效
            eKGameCueService.isvalid(cid, islive);
        } catch (Exception e) {
            log.error("EKGameCueAction.isvalid failed,", e);
        }
        return NONE;
    }

    // 删除提示内容
    public String deleteCue() {
        // 获取提示id
        String cid = getParameter("cid");
        log.info("into EKGameCueAction.deleteCue");
        log.info("cid=" + cid);
        try {
            // 删除
            eKGameCueService.deleteCol(cid);
        } catch (Exception e) {
            log.error("EKGameCueAction.deleteCue failed,", e);
        }
        return NONE;
    }

}
