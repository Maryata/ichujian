package com.sys.ekey.shop.action;

import com.sys.commons.AbstractAction;
import com.sys.ekey.shop.service.ShopService;
import com.sys.util.ApDateTime;
import com.sys.util.StrUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 商城 接口
 * Created by vpc on 2016/4/15.
 */
@Component("shopAction")
public class ShopAction extends AbstractAction {

    @Autowired
    private ShopService shopService;

    /**
     * 1.签到奖励列表
     * 2.连续签到天数
     * 3.签到详情
     * 4.本月累计签到天数
     * 5.可用积分
     * 6.可领取的积分
     * <p/>
     * 参数  用户uid
     *
     * @return
     */
    public String shopSign() {
        log.info("into ShopAction shopSign ...");
        Map<String, Object> retMap = new HashMap<String, Object>();
        String uid = getParameter("uid"); //getParameter("uid");
        log.info("uid -->" + uid);
        if (StringUtils.isEmpty(uid)) {//用户未登录  签到奖励
            List<Map<String, Object>> signAwardList = shopService.signAward();//签到奖励
            retMap.put("signAward", signAwardList);
        } else {
            List<Map<String, Object>> signAwardList = shopService.signAward();//签到奖励
            retMap.put("signAward", signAwardList);

            List<Map<String, Object>> signAwardSeriesList = shopService.signAwardSeries();//累计签到奖励
            retMap.put("signAwardSeries", signAwardSeriesList);

            List<Map<String, Object>> signDetailList = shopService.signDettail(uid);//签到详情
            retMap.put("signDettail", signDetailList);
            // 2.连续签到天数
            Integer seq = 1;
            // 获取今天
            Calendar c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_MONTH);// 日期
            Date today = new Date();
            SimpleDateFormat ymFormat = new SimpleDateFormat("yyyy-MM");
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (StrUtils.isNotEmpty(signDetailList)) {
                Map<String, Object> latestDate = signDetailList.get(0);// 上一次签到
                Object seqObj = latestDate.get("C_SEQ");
                Object dateObj = latestDate.get("C_DATE");
                String c_seq = StrUtils.emptyOrString(seqObj);
                String c_date = StrUtils.emptyOrString(dateObj);
                Integer i_seq = Integer.valueOf(c_seq);
                if (1 == day) {
                    // 如果今天是1号，seq = 1
                    retMap.put("seq", day);
                    seq = 1;
                } else {
                    if (StrUtils.isNotEmpty(seqObj)) {
                        // 昨天
                        String yesterday = ApDateTime.dateAdd("d", -1, new Date(), ApDateTime.DATE_TIME_YMD);
                        if (yesterday.equals(c_date)) {
                            // 如果昨天签到了，seq + 1
                            seq = i_seq + 1;
                        }
                    } else {
                        log.error("WRONG SEQ IN [T_EK_MEMBER_SIGN], UID = "
                                + uid + ", DATE = " + ymdFormat.format(today));
                    }
                }
            }
            retMap.put("ContiAttendance", seq);//连续签到天数

            int signCountDay = shopService.signCountDay(uid);//本月累计签到天数
            retMap.put("signCountDay", signCountDay);

            int kyScore = shopService.availableIntegral(uid);//可用积分
            retMap.put("kyScore", kyScore);

            int hdScore = shopService.securableIntegral(uid);//可获得积分
            retMap.put("hdScore", hdScore);
        }

        try {
            writeToResponse(JSONObject.fromObject(retMap).toString());
        } catch (Exception ex) {
            log.error("积分信息数据写出错误！", ex);
        }
        return NONE;
    }

    /**
     * 我的任务列表
     *
     * @return
     */
    public String task() {
        log.info("into ShopAction task ...");
        Map<String, Object> retMap = new HashMap<String, Object>();
        String uid = getParameter("uid"); //getParameter("uid");
        log.info("uid -->" + uid);

        List<Map<String, Object>> taskList = shopService.task(uid);//任务列表
        retMap.put("tasks", taskList);

        try {
            writeToResponse(JSONObject.fromObject(retMap).toString());
        } catch (Exception ex) {
            log.error("我的积分信息数据写出错误！", ex);
        }
        return NONE;
    }

    /**
     * 分享赢好礼
     *
     * @return
     */
    public String share() {
        log.info("into ShopAction share ...");
        Map<String, Object> retMap = new HashMap<String, Object>();
        String uid = getParameter("uid"); //getParameter("uid");
        log.info("uid -->" + uid);

        int inviteNumber = shopService.inviteNumber(uid);//邀请人数
        retMap.put("inviteNumber", inviteNumber);

        String inviteCode = shopService.inviteCode(uid);//邀请码
        retMap.put("inviteCode", inviteCode);

        try {
            writeToResponse(JSONObject.fromObject(retMap).toString());
        } catch (Exception ex) {
            log.error("分享赢豪礼信息数据写出错误！", ex);
        }
        return NONE;
    }

    /**
     * 邀请详情
     *
     * @return
     */
    public String inviteDetail() {
        log.info("into ShopAction inviteDetail ...");
        Map<String, Object> retMap = new HashMap<String, Object>();
        String uid = getParameter("uid"); //getParameter("uid");
        log.info("uid -->" + uid);

        int inviteNumber = shopService.inviteNumber(uid);//邀请人数
        retMap.put("inviteNumber", inviteNumber);

        int inviteAward = shopService.inviteAward(uid);//邀请奖励积分
        retMap.put("inviteAward", inviteAward);

        List<Map<String, Object>> inviteDetailList = shopService.inviteDetail(uid);//邀请详情
        retMap.put("inviteDetails", inviteDetailList);

        try {
            writeToResponse(JSONObject.fromObject(retMap).toString());
        } catch (Exception ex) {
            log.error("邀请详情数据写出错误！", ex);
        }
        return NONE;
    }

    // 商品兑换
    public String exchangeList() {
        log.info("into ShopAction exchangeList ...");
        Map<String, Object> retMap = new HashMap<>();
        String uid = getParameter("uid");
        String pageIndex = getParameter("pageIndex");
        String pSize = getParameter("pSize");
        log.info("uid = " + uid + ", pageIndex = " + pageIndex + ", pSize = " + pSize);

        try {
            List<Map<String, Object>> exchangeList = shopService.exchangeList(uid, pageIndex, pSize);
            retMap.put("list", exchangeList);
            retMap.put("status", "Y");
        } catch (Exception e) {
            retMap.put("status", "N");
            retMap.put("info", "1003, e :" + e.getMessage());
        }

        try {
            writeToResponse(JSONObject.fromObject(retMap).toString());
        } catch (Exception ex) {
            log.error("虚拟商品兑换数据写出错误！", ex);
        }
        return NONE;
    }

    // 商品首页广告
    public String advert() {
        log.info("into ShopAction advert ...");
        Map<String, Object> retMap = new HashMap<>();

        try {
            List<Map<String, Object>> advert = shopService.advert();
            retMap.put("list", advert);
            retMap.put("status", "Y");
        } catch (Exception e) {
            retMap.put("status", "N");
            retMap.put("info", "1003, e :" + e.getMessage());
        }

        try {
            writeToResponse(JSONObject.fromObject(retMap).toString());
        } catch (Exception ex) {
            log.error("虚拟商品兑换数据写出错误！", ex);
        }
        return NONE;
    }


}
