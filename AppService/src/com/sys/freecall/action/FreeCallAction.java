package com.sys.freecall.action;

import com.sys.commons.AbstractAction;
import com.sys.ekey.freecall.service.FeiyuCloudService;
import com.sys.freecall.service.FreeCallService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/7/8.
 */
@Controller
public class FreeCallAction extends AbstractAction {

    @Autowired
    private FreeCallService freeCallService;
    @Autowired
    private FeiyuCloudService feiyuCloudService;

    // 走马灯广告
    public String marqueeBanner() {
        Map<String, Object> reqMap = new HashMap();
        log.info("into FreeCallAction.marqueeBanner");
        try {
            List<Map<String, Object>> banner = freeCallService.marqueeBanner();
            reqMap.put("status", "Y");
            reqMap.put("banner", banner);
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("FreeCallAction.marqueeBanner failed, e : ", e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }
        return NONE;
    }

    // 轮播广告
    public String advert() {
        Map<String, Object> reqMap = new HashMap();
        log.info("into FreeCallAction.advert");
        try {
            List<Map<String, Object>> advert = freeCallService.advert();
            reqMap.put("status", "Y");
            reqMap.put("advert", advert);
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("FreeCallAction.advert failed, e : ", e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }
        return NONE;
    }

    //套餐列表
    public String meal(){
        Map<String, Object> reqMap = new HashMap();
        log.info("into FreeCallAction.meal");
        try {
            List<Map<String, Object>> meal = freeCallService.meal();
            reqMap.put("status", "Y");
            reqMap.put("meal", meal);
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("FreeCallAction.meal failed, e : ", e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }
        return NONE;

    }

    //免费通话到期时间
    public String term(){
        Map<String, Object> reqMap = new HashMap();
        log.info("into FreeCallAction.term");
        String uid = getParameter("uid");
        log.info("uid -->" + uid);
        try {
            String term = freeCallService.term(uid);
            reqMap.put("status", "Y");
            reqMap.put("term", term);
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("FreeCallAction.term failed, e : ", e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }
        return NONE;

    }

    public String test(){
        Map<String,String> params = new HashMap<>();
        params.put("out_trade_no","2016071288CE1RERRPS03820455");
        feiyuCloudService.updatePayStatus(params,"1");

        return NONE;
    }

}
