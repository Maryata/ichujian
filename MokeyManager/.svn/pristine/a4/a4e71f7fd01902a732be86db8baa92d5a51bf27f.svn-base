package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKRewardService;
import com.org.mokey.common.AbstractAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签到奖励
 * Created by vpc on 2016/4/20.
 */
@Controller("eKRewardAction")
public class EKRewardAction extends AbstractAction {

    @Autowired
    private EKRewardService eKRewardService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 跳转列表页面
     *
     * @return
     */
    public String toList() {

        return "toList";
    }

    /**
     * 加载 列表页面
     *
     * @return
     */
    public String list() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        String sPage = getParameter("page");// 获取page
        String type = getParameter("type");
        log.info("into EKRewardAction.list");
        log.info("sPage=" + sPage);
        try {
            int page = 1;// 默认第一页
            if (null != sPage && sPage.matches("\\d+")) {
                page = Integer.parseInt(sPage);
            } else {
                log.info(sPage);
            }
            retmap = eKRewardService.list(page, type);
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error(" EKRewardAction.list failed, e : " + e);
        }
        return NONE;
    }

    /**
     * 跳转到修改页面
     *
     * @return
     */
    public String toUpdate() {
        String cid = getParameter("editId");
        String day = getParameter("editDay");
        String score = getParameter("editScore");
        String type = getParameter("editType");
        getRequest().setAttribute("cid", cid);
        getRequest().setAttribute("day", day);
        getRequest().setAttribute("score", score);
        getRequest().setAttribute("type", type);
        return "toUpdate";
    }

    /**
     * 修改
     *
     * @return
     */
    public String updateReward() {
        String id = getParameter("cid");
        String day = getParameter("day");
        String score = getParameter("score");
        String type = getParameter("type");
        List<Object> args = new ArrayList<Object>();
        args.add(day);
        args.add(score);
        args.add(type);
        args.add(id);
        eKRewardService.updateReward(args);
        return "reload";
    }


}
