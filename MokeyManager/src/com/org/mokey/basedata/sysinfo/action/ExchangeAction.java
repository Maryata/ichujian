package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.ExchangeService;
import com.org.mokey.common.AbstractAction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/1/13.
 */
public class ExchangeAction extends AbstractAction {

    private String out;

    private ExchangeService exchangeService;

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public ExchangeService getExchangeService() {
        return exchangeService;
    }

    public void setExchangeService(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    // 跳转到申请兑换审核列表页面
    public String toHandle(){
        return "toHandle";
    }

    public String uncompletedExchange(){
        Map<String, Object> reqmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 分页页码
        String pname = getParameter("pname");// 用户id
        String state = getParameter("state");// 供应商code
        log.info("into ExchangeAction.uncompletedExchange");
        log.info("pname = " + pname + ", state = " + state);
        try{
            // 查询申请记录
            reqmap = exchangeService.uncompletedExchange(page, pname, state);
            reqmap.put("status", "Y");
            // 回写
            this.writeJSONToResponse(reqmap);
        }catch(Exception e){
            reqmap.put("status", "N");
            log.error("ExchangeAction.uncompletedExchange failed, e : ", e);
        }
        return NONE;
    }

    // 审核通过
    public String complete(){
        String id = getParameter("c_id");
        String uid = getParameter("c_uid");
        String pid = getParameter("c_pid");// 产品id
        String cost = getParameter("c_cost");// 产品花费积分
        String userName = getSessionLoginUser().getUserName();// 审核人
        log.info("into ExchangeAction.complete");
        log.info("id = " + id + ", uid = " + uid + ", pid = " + pid
                + ", cost = " + cost + ", userName = " + userName);
        try{
            exchangeService.complete(id, uid, pid, cost, userName);
        } catch(Exception e){
            log.error("ExchangeAction.complete failed, e : ", e);
        }
        return "toHandle";
    }
}
