/**
 *
 */
package com.org.mokey.task;

import com.org.mokey.analyse.service.UserAnalyseService;
import com.org.mokey.basedata.baseinfo.common.HtmlUtil;
import com.org.mokey.basedata.sysinfo.entiy.FreeCall;
import com.org.mokey.basedata.sysinfo.service.EKFreeCallService;
import com.org.mokey.basedata.sysinfo.util.DateUtils;
import com.org.mokey.basedata.sysinfo.util.HttpUtil;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.util.HttpRequestProxy;
import com.org.mokey.util.StrUtils;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author vpc
 */
@Component
public class ScheduledTasks {
    @Resource
    private JdbcTemplate jdbcTemplate;
    private Logger log = (Logger.getLogger(getClass()));

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "HH:mm:ss");

    private static JdbcTemplate _jdJdbcTemplate;

    @PostConstruct
    public void init() {
        _jdJdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private UserAnalyseService userAnalyseService;
    @Autowired
    private EKFreeCallService ekFreeCallService;

    @Scheduled(cron = "59 59 23 * * ?")
//    @Scheduled(fixedDelay = 10000)
    public void gameRandomTask() {
        try {
            jdbcTemplate.execute(" CALL PKG_GAME.PROC_RANDOM_TASK() ");

            if (log.isInfoEnabled()) {
                log.info("random task ...");
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * 每日任务   维护
     */
    @Scheduled(cron = "59 59 23 * * ?")
    //@Scheduled(fixedDelay = 10000)
    public void shopRandomTask() {
        try {
            jdbcTemplate.execute(" CALL PKG_GAME.PROC_RANDOM_EK_TASK() ");
            if (log.isInfoEnabled()) {
                log.info("random task ...");
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Scheduled(cron = "0 5 0 1 * ?")
    public void freeCall() {
        try {
            jdbcTemplate.execute("call pkg_ek_freecall.INIT_MINUTE() ");

            if(log.isInfoEnabled()) {
                log.info("每月免费电话时长添加");
            }
        } catch (Exception e) {
            log.error("添加免费通话时长错误！" + e);
        }
    }

    @Scheduled(cron = "0 2 0 * * ?")
    public void taskToSelectDataYesterday() {
        String now = null;
        try {
            now = ApDateTime.getDateTime(ApDateTime.DATE_TIME_SSS, System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            userAnalyseService.timedTask();
        } catch (Exception e) {
            log.error("日期 ： [ " + now + " ] 查询并保存前一天的数据出错！" + e);
        }
    }

    @Scheduled(cron = "0 35 9 13 6 ?")
    public void taskToSelectOldData() {
        String now = null;
        try {
            now = ApDateTime.getDateTime(ApDateTime.DATE_TIME_SSS, System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            userAnalyseService.timedTask();
        } catch (Exception e) {
            log.error("日期 ： [ " + now + " ] 查询并保存前一天的数据出错！" + e);
        }
    }

    @Scheduled(cron = "0 2 0 * * ?")
    public void checkExceptionalAccount() {
        String now = null;
        try {
            now = ApDateTime.getDateTime(ApDateTime.DATE_TIME_SSS, System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ekFreeCallService.checkExceptionalAccount();
        } catch (Exception e) {
            log.error("日期 ： [ " + now + " ] 处理异常飞语账号出错！" + e);
        }
    }

    //@Scheduled(cron = "0 0 2 * * ?")
    //@Scheduled(fixedDelay=4000)
    public void sort() {
        if (jdbcTemplate == null) jdbcTemplate = _jdJdbcTemplate;

        try {
            jdbcTemplate.execute("CALL  PKG_GAME.PROC_APP_INFO_LATEST()");
            jdbcTemplate.execute("CALL  PKG_GAME.PROC_APP_INFO_RANKING()");
            jdbcTemplate.execute("CALL  PKG_GAME.PROC_APP_INFO_TOP()");
            jdbcTemplate.execute("CALL PKG_GAME.PROC_H5_INFO_LATEST()");
            jdbcTemplate.execute("CALL PKG_GAME.PROC_H5_INFO_RANKING()");
            jdbcTemplate.execute("CALL PKG_GAME.PROC_H5_INFO_TOP()");

            if (log.isInfoEnabled()) {
                log.info("sort : The time is now " + dateFormat.format(new Date()));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void mcrappSort() {
        if (jdbcTemplate == null) jdbcTemplate = _jdJdbcTemplate;

        try {
            jdbcTemplate.execute(" call pkg_mcrapp.proc_app_recommendation() ");
            jdbcTemplate.execute(" call pkg_mcrapp.proc_app_ranking()");

            if (log.isInfoEnabled()) {
                log.info("mcrappSort : The time is now " + dateFormat.format(new Date()));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Scheduled(cron = "0 0 3 * * ?")
    //@Scheduled(fixedDelay=1000 * 60)
    public void generator() {
        final String charset = "UTF-8";
        HtmlUtil htmlUtil = new HtmlUtil();
        //String targetURL = "http://192.168.8.221/AppService/game/h5Gamecollection.action";
        //String targetURL2 = "http://192.168.8.221/AppService/game/h5AdvertInfo.action?sign=1ADC62A70DFCA81C3E85FDEE4AA2E712";
        //String targetURL = "http://localhost/AppService/game/h5Gamecollection.action";
        //String targetURL2 = "http://localhost/AppService/game/h5AdvertInfo.action?sign=1ADC62A70DFCA81C3E85FDEE4AA2E712";
        String targetURL = "http://www.ichujian.com/AppService/game/h5Gamecollection.action";
        String targetURL2 = "http://www.ichujian.com/AppService/game/h5AdvertInfo.action?sign=1ADC62A70DFCA81C3E85FDEE4AA2E712";
        String[] cids = new String[]{"1", "2", "3"}; // 1:热门，2：最新，3：排行
        String result = null;
        JSONObject o = new JSONObject();

        result = HttpRequestProxy.doGet(targetURL2, charset);

        o.put("advert", JSONObject.fromObject(result)); // 广告信息

        for (int i = 0; i < cids.length; ++i) {
            Map<String, String> parameters = new HashMap<String, String>();
            try {
                parameters.put("cid", com.alibaba.druid.util.Base64
                        .byteArrayToBase64(cids[i].getBytes("UTF-8")));
                parameters.put("pageindex", com.alibaba.druid.util.Base64
                        .byteArrayToBase64("1".getBytes("UTF-8")));
            } catch (UnsupportedEncodingException e1) {
                log.error(e1.getMessage());
            }
            result = HttpRequestProxy.doPost(targetURL, parameters, charset);

            JSONObject json = JSONObject.fromObject(result);

            o.put("collection-" + i, json);
        }

        if (log.isDebugEnabled()) {
            log.debug(o.toString());
        }

        htmlUtil.createH5GameHtml(o);

        if (log.isInfoEnabled()) {
            log.info("generator : The time is now " + dateFormat.format(new Date()));
        }
    }

    /**
     * 1.授权表 查询状态为0  ----》唯一标示  和   账户
     * 2.通过唯一标示  调用接口  ----》通话开始时间  通话结束时间
     * 3.计算时间  修改  剩余时间
     */
    @Scheduled(cron = "0 0 23 * * ?")
    public void updateCallHis() {
        Map<String, String> paramMap = new HashMap<String, String>();
        FreeCall e = new FreeCall();
        FreeCall freeCall = null;
        String data = "";
        //授权表查询值
        String sql = new String("SELECT T.C_FYCALLID,T.C_FYACCOUNTID FROM  T_FY_AUTH T WHERE T.C_STATUS='0'");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if (StrUtils.isNotEmpty(list)) {
            for (Map<String, Object> item : list) {
                String fyCallId = StrUtils.emptyOrString(item.get("C_FYCALLID"));
                String fyAccountId = StrUtils.emptyOrString(item.get("C_FYACCOUNTID"));
                long time = freeCall.GetTime();
                paramMap.put("appId", freeCall.GetAppId());//appId
                paramMap.put("fyCallId", fyCallId);//账号id
                paramMap.put("ti", String.valueOf(time));//时间
                paramMap.put("au", freeCall.getAuValue(time));//AU

                data = HttpUtil.sendPost(e.freecall_fetchCallHistory, paramMap, "UTF-8");

                if (StrUtils.isNotEmpty(data)) {
                    JSONObject json = JSONObject.fromObject(data);
                    String resultCode = json.getString("resultCode");
                    String result = json.getString("result");
                    //Map map = (Map) json;
                    if ("0".equals(resultCode)) {//----------------------!

                        if (StrUtils.isNotEmpty(result)) {//-----------------Not
                            //有效期判断
                            judgmentValidity(result, fyCallId, fyAccountId);
                        }
                    }
                }

            }
        }
    }

    /**
     * 有效期 判断
     */
    public void judgmentValidity(String result, String fyCallId, String fyAccountId) {
        JSONObject ret = JSONObject.fromObject(result);
        long callStartTime = Long.parseLong(ret.getString("callStartTime"));//开始时间ret.getString("callStartTime")
        long callEndTime = Long.parseLong(ret.getString("callEndTime"));//结束时间ret.getString("callEndTime")
        double minite = Math.ceil((callEndTime - callStartTime) / 1000);//向上取整 通话时间
        List<Map<String, Object>> timeList = selectFyTimeById(fyAccountId);
        if (timeList.size() == 2) {
            String timeTemp = "";
            String timePerpetual = "";
            String validity = "";
            String gdvalidity = "";
            long residueTempTime = 0;//有效时长剩余时间
            long residuePerpetualTime = 0;//固定时长剩余时间
            for (int i = 0; i < timeList.size(); i++) {//Map<String, Object> map : timeList
                Map<String, Object> map = timeList.get(i);
                if (i == 0) {
                    timePerpetual = StrUtils.emptyOrString(map.get("C_TIME_PERPETUAL"));//固定时长
                    gdvalidity = StrUtils.emptyOrString(map.get("C_VALIDITY"));//固定有效
                }
                if (i == 1) {
                    timeTemp = StrUtils.emptyOrString(map.get("C_TIME_TEMP"));//有效时长
                    validity = StrUtils.emptyOrString(map.get("C_VALIDITY"));//有效期
                }
            }
            long oldtimeTemp = Long.parseLong(timeTemp);
            long oldtimePerpetual = Long.parseLong(timePerpetual);
            int syTime_int = (int) minite;
            long syTime = (long) syTime_int;
            //判断是否在有效期  是否等于0 或  1
            int count = DateUtils.compare_date(validity, ApDateTime.getNowDateTime("yyyy-MM-dd hh:mm:ss"));
            if (count == 1) {
                //有效时长 >  通话时长
                if (oldtimeTemp >= syTime) {
                    residueTempTime = oldtimeTemp - syTime;
                    residuePerpetualTime = oldtimePerpetual;
                } else { //有效时长 <  通话时长
                    residueTempTime = 0;
                    residuePerpetualTime = oldtimePerpetual - oldtimeTemp;
                }
            } else if (count == -1) {//判断是否在有效期  是否大于等于-1
                residueTempTime = oldtimeTemp;
                residuePerpetualTime = oldtimePerpetual - syTime;
            }
            updateFyTime(residueTempTime, residuePerpetualTime, fyAccountId,validity,gdvalidity);
            updateFyStatus(fyCallId);

        }


    }

    /**
     * 查询 飞语账户  通话时间信息
     *
     * @param fyAccountId
     * @return
     */
    public List<Map<String, Object>> selectFyTimeById(String fyAccountId) {
        List<Map<String, Object>> list = null;
        try {
            String sql = "SELECT C_ID,C_FYACCOUNTID,C_TIME_TEMP,C_TIME_PERPETUAL,C_VALIDITY FROM T_FY_USER_TIME WHERE C_FYACCOUNTID=? AND C_VALIDITY >=SYSDATE ORDER BY C_VALIDITY DESC";
            List<Object> args = new ArrayList<Object>();
            args.add(fyAccountId);
            list = jdbcTemplate.queryForList(sql, args.toArray());
        } catch (Exception e) {
            log.error("ScheduledTasks.selectFyTimeById"+e);
        }
        return list;
    }

    /**
     * 修改 通话时间
     *
     * @param timeTemp
     * @param timePerpetual
     * @param fyAccountId
     */
    public void updateFyTime(long timeTemp, long timePerpetual, String fyAccountId,String validity,String gdvalidity) {
        try {
            String sql = "UPDATE T_FY_USER_TIME SET C_TIME_TEMP = ? WHERE C_FYACCOUNTID = ? AND C_VALIDITY=?";
            String sql1 = "UPDATE T_FY_USER_TIME SET C_TIME_PERPETUAL=? WHERE C_FYACCOUNTID = ? AND C_VALIDITY=?";
            List<Object> args = new ArrayList<Object>();
            List<Object> args1 = new ArrayList<Object>();
            args.add(timeTemp);
            args.add(fyAccountId);
            args.add(ApDateTime.getDate(validity, ApDateTime.DATE_TIME_Sec));

            args1.add(timePerpetual);
            args1.add(fyAccountId);
            args1.add(ApDateTime.getDate(gdvalidity, ApDateTime.DATE_TIME_Sec));
            this.jdbcTemplate.update(sql, args.toArray());
            this.jdbcTemplate.update(sql1, args1.toArray());
        } catch (Exception e) {
            log.error("ScheduledTasks.updateFyTime"+e);
        }
    }

    /**
     * 更新 授权 状态
     *
     * @param fyCallId
     */
    public void updateFyStatus(String fyCallId) {
        try {
            String sql = "UPDATE T_FY_AUTH SET C_STATUS = ? WHERE C_FYCALLID = ?";
            List<Object> args = new ArrayList<Object>();
            String status = "1";
            args.add(status);
            args.add(fyCallId);
            this.jdbcTemplate.update(sql, args.toArray());
        } catch (Exception e) {
            log.error("ScheduledTasks.updateFyStatus"+e);
        }
    }


}
