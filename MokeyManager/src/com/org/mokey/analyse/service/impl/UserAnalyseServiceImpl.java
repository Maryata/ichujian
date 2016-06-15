package com.org.mokey.analyse.service.impl;

import com.org.mokey.analyse.dao.EkeyKeyUsingDao;
import com.org.mokey.analyse.dao.UserAnalyseDao;
import com.org.mokey.analyse.service.EkeyKeyUsingService;
import com.org.mokey.analyse.service.UserAnalyseService;
import com.org.mokey.analyse.vo.SupVo;
import com.org.mokey.analyse.vo.TimeVo;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.util.StrUtils;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Maryn on 2016/1/18.
 */
@Service
public class UserAnalyseServiceImpl implements UserAnalyseService {

    private static final Log LOGGER = LogFactory.getLog(UserAnalyseServiceImpl.class);

    @Autowired
    private EkeyKeyUsingService ekeyKeyUsingService;
    @Autowired
    private EkeyKeyUsingDao ekeyKeyUsingDao;
    @Autowired
    private UserAnalyseDao userAnalyseDao;

    @Override
    public Map<String, Object> activeUser(String userId, String sup,
                                          String sDate, String eDate,
                                          String timeType, String year, String dataType) {
        Map<String, Object> reqMap = new HashMap<>();
        List<Map<String, Object>> suppliers = new ArrayList<>();// 供应商
        Map<String, Object> timeMap = new HashMap<>();// 当前时间和上一期的时间
        List<String> timeList = new ArrayList<>();// 表头时间
        List<Map<String, Object>> dataList = new ArrayList<>();// 数据
        try {
            // 可见的供应商
            suppliers = userAnalyseDao.visibleSupplier(userId, sup);

            // 时间

            timeMap = getTimeList(sDate, eDate, timeType, year, dataType);

            // 数据
            if (!"2".equals(timeType)) {
                dataList = activeUserData(suppliers, timeMap, timeType, dataType);
                timeList = (List<String>) timeMap.get("now");
                timeList.add(0, "供应商");
            } else {
                dataList = userDataOfWeek(suppliers, timeMap, dataType);
            }


        } catch (Exception e) {
            LOGGER.error("UserAnalyseOfDayServiceImpl.activeUser failed ! e : ", e);
        }
        reqMap.put("suppliers", suppliers);
        reqMap.put("timeList", "2".equals(timeType) ? timeMap.get("weekList") : timeList);
        reqMap.put("dataMap", dataList);
        return reqMap;
    }

    /**
     * 除“留存”以外的周用户数据
     *
     * @param suppliers 供应商
     * @param timeMap   时间
     * @param dataType  数据类型
     * @return
     */
    private List<Map<String, Object>> userDataOfWeek(
            List<Map<String, Object>> suppliers, Map<String, Object> timeMap, String dataType) {

        List<String> sup = getSupCodeList(suppliers);

        List<String> nowList = (List<String>) timeMap.get("now");
        List<String> befList = (List<String>) timeMap.get("last");
        Map<String, List<String>> weekMap = (Map<String, List<String>>) timeMap.get("weekMap");

        // 查询
        List<Map<String, Object>> nowData = new ArrayList<>();
        List<Map<String, Object>> lastData = new ArrayList<>();

        if ("1".equals(dataType)) {// 下载
            nowData = userAnalyseDao.downloadUserData(sup, nowList.get(0), nowList.get(1), "2");
            lastData = userAnalyseDao.downloadUserData(sup, befList.get(0), befList.get(1), "2");
        }
        if ("2".equals(dataType)) {// 激活
            nowData = userAnalyseDao.activationUserData(sup, nowList.get(0), nowList.get(1), "2");
            lastData = userAnalyseDao.activationUserData(sup, befList.get(0), befList.get(1), "2");
        }
        if ("3".equals(dataType)) {// 注册
            nowData = userAnalyseDao.registerUserData(sup, nowList.get(0), nowList.get(1), "2");
            lastData = userAnalyseDao.registerUserData(sup, befList.get(0), befList.get(1), "2");
        }
        if ("4".equals(dataType)) {// 活跃
            nowData = userAnalyseDao.activeUserData(sup, nowList.get(0), nowList.get(1), "2");
            lastData = userAnalyseDao.activeUserData(sup, befList.get(0), befList.get(1), "2");
        }

        // [{供应商 : [数量1, 数量2, ...]}]
        List<Map<String, Object>> nowTotal = checkTimeWeek(suppliers, weekMap, nowData);
        List<Map<String, Object>> befTotal = checkTimeWeek(suppliers, weekMap, lastData);

        // 合并同一供应商的不同时间的数据
        return mergeDataOfWeek(nowTotal, befTotal);
    }

    /**
     * 合并同一供应商的不同时间的数据
     *
     * @param nowTotal 查询时间段每周的数量
     * @param befTotal 去年同期每周的数量
     * @return
     */
    private List<Map<String, Object>> mergeDataOfWeek(List<Map<String, Object>> nowTotal,
                                                      List<Map<String, Object>> befTotal) {

        for (int i = 0; i < nowTotal.size(); i++) {
            Map<String, Object> nowMap = nowTotal.get(i);
            String nowName = nowMap.get("sup").toString();
            List<String> nowData = (List<String>) nowMap.get("data");
            for (int j = 0; j < befTotal.size(); j++) {
                Map<String, Object> befMap = befTotal.get(j);
                String befName = befMap.get("sup").toString();
                if (nowName.equals(befName)) {
                    List<String> befData = (List<String>) befMap.get("data");
                    for (int k = 1; k <= befData.size(); k++) {
                        nowData.add(k * 2 - 1, befData.get(k - 1));
                    }
                }

            }
        }
        return nowTotal;
    }

    @Override
    public Map<String, Object> stayingUser(String userId, String sup,
                                           String sDate, String eDate,
                                           String timeType, String year, String dataType) {
        Map<String, Object> reqMap = new HashMap<>();
        List<Map<String, Object>> suppliers = new ArrayList<>();// 供应商
        List<String> timeList = new ArrayList<>();// 表头时间
        List<String> now_timeList;
        List<String> bef_timeList;
        Map<String, Object> timeMap;// 当前时间和上一期的时间
        Map<String, Map<String, Integer>> now_dataMap;// 数据
        Map<String, Map<String, Integer>> before_dataMap;// 数据
        List<Map<String, Object>> dataList = new ArrayList<>();// 数据
        try {
            // 可见的供应商
            suppliers = userAnalyseDao.visibleSupplier(userId, sup);

            // 时间
            timeMap = getTimeList(sDate, eDate, timeType, year, dataType);
            now_timeList = (List<String>) timeMap.get("now");
            bef_timeList = (List<String>) timeMap.get("last");

            if (!"2".equals(timeType)) {
                // 数据
                now_dataMap = stayingUserData(suppliers, now_timeList.get(0), now_timeList.get(now_timeList.size() - 1), timeType);
                before_dataMap = stayingUserData(suppliers, bef_timeList.get(0), bef_timeList.get(bef_timeList.size() - 1), timeType);

                dataList = reqStayingData(dataList, suppliers, timeMap, now_dataMap, before_dataMap);
            } else {
                dataList = stayingUserDataOfWeek(suppliers, timeMap);
            }


            if ("1".equals(timeType)) {
                now_timeList.remove(0);
                now_timeList.add(0, "供应商");
                reqMap.put("timeList", now_timeList);
            }
            if ("2".equals(timeType)) {
                now_timeList = (List<String>) timeMap.get("weekList");
                now_timeList.remove(0);
                now_timeList.add(0, "供应商");
                reqMap.put("timeList", now_timeList);
            }
            // 月-截取“yyyy-MM”
            if ("3".equals(timeType)) {
                for (int i = 1; i < now_timeList.size(); i++) {
                    timeList.add(now_timeList.get(i).substring(0, 7));
                }
                timeList.add(0, "供应商");
                reqMap.put("timeList", timeList);
            }

        } catch (Exception e) {
            LOGGER.error("UserAnalyseOfDayServiceImpl.stayingUser failed ! e : ", e);
        }
        reqMap.put("suppliers", suppliers);
        reqMap.put("dataMap", dataList);
        return reqMap;
    }

    /**
     * 封装返回到签到的活跃用户数据
     *
     * @param dataList       用于存储返回到签到的数据
     * @param suppliers      可见的所有供应商
     * @param timeMap        查询时间的集合（包括第一天的前一天）
     * @param now_dataMap    按“查询时间”查询到的数据
     * @param before_dataMap 按“查询时间”的去年同期查询到的数据
     * @return
     */
    public List<Map<String, Object>> reqStayingData(
            List<Map<String, Object>> dataList, List<Map<String, Object>> suppliers,
            Map<String, Object> timeMap, Map<String, Map<String, Integer>> now_dataMap,
            Map<String, Map<String, Integer>> before_dataMap) {

        List<String> nowTime = (List<String>) timeMap.get("now");
        List<String> befTime = null;
        if (StrUtils.isNotEmpty(before_dataMap)) {
            befTime = (List<String>) timeMap.get("last");
        }
        if (StrUtils.isNotEmpty(suppliers)) {
            for (Map<String, Object> supplier : suppliers) {
                String name = supplier.get("C_SUPPLIER_NAME").toString();// 供应商名称
                String code = supplier.get("C_SUPPLIER_CODE").toString();// 供应商code
                Map<String, Integer> now_map = now_dataMap.get(code);
                Map<String, Integer> bef_map = null;
                if (StrUtils.isNotEmpty(before_dataMap)) {
                    bef_map = before_dataMap.get(code);
                }
                Map<String, Object> dataMap = new HashMap<>();
                List<String> list = new ArrayList<>();
                for (int i = 1; i < nowTime.size(); i++) {
                    list.add(String.valueOf(now_map.get(nowTime.get(i))));
                    if (StrUtils.isNotEmpty(before_dataMap)) {
                        list.add(String.valueOf(bef_map.get(befTime.get(i))));
                    }
                }
                dataMap.put("sup", name);
                dataMap.put("data", list);
                dataList.add(dataMap);
            }
        }


        return dataList;
    }

    /**
     * 根据时间类型获取不同的时间集合
     *
     * @param sDate    开始时间
     * @param eDate    结束时间
     * @param timeType 时间类型 1:日、2:周、3:月
     * @param year     年份
     * @param dataType 数据类型：1:下载情况、2:激活情况、3:注册情况、4:活跃情况、5:留存
     * @return
     */
    public Map<String, Object> getTimeList(String sDate, String eDate,
                                           String timeType, String year, String dataType) {
        Map<String, Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        List<String> lastList = new ArrayList<>();
        if ("1".equals(timeType)) {
            if ("5".equals(dataType)) {
                // 留存数据
                list = ApDateTime.getDayBetween(sDate, eDate);
                // 获取第一天的前一天
                String dayBefore = ApDateTime.dateAdd("d", -1, sDate, ApDateTime.DATE_TIME_YMD);
                list.add(0, dayBefore);

                for (String day : list) {
                    // 获取去年同期的每一天
                    String dayOfYearBefore = ApDateTime.dateAdd("y", -1, day, ApDateTime.DATE_TIME_YMD);
                    lastList.add(dayOfYearBefore);
                }
                map.put("now", list);
                map.put("last", lastList);
            } else {
                // 获取上个月的相同日期
                list = ApDateTime.getDayBetween(sDate, eDate);
                lastTimeList(map, list, lastList, "m");
            }
        }
        if ("2".equals(timeType)) {
            map = daysOnBoundaryOfWeek(year, sDate, eDate, list, lastList, dataType);
        }
        if ("3".equals(timeType)) {
            if ("5".equals(dataType)) {
                // 留存数据
                list = ApDateTime.getMonthBetween(sDate, eDate);
                // 获取第一天的前一天
                String monthBefore = ApDateTime.dateAdd("m", -1, sDate, ApDateTime.DATE_TIME_YM);
                list.add(0, monthBefore);

                for (String day : list) {
                    // 获取去年同期的每一天
                    String monthOfYearBefore = ApDateTime.dateAdd("y", -1, day, ApDateTime.DATE_TIME_YM);
                    lastList.add(monthOfYearBefore);
                }
                map.put("now", list);
                map.put("last", lastList);
            } else {
                // 获取去年同月
                list = ApDateTime.getMonthBetween(sDate, eDate);
                lastTimeList(map, list, lastList, "y");
            }
        }

        return map;
    }

    /**
     * 获取指定年和前一年中第sDate周的第一天和第eDate周的最后一天
     *
     * @param year     年
     * @param sDate    起始周
     * @param eDate    中止周
     * @param list     存放当前年的第sDate周的第一天和第eDate周的最后一天
     * @param lastList 存放前一年第sDate周的第一天和第eDate周的最后一天
     * @param dataType 数据类型
     * @return
     */
    private Map<String, Object> daysOnBoundaryOfWeek(
            String year, String sDate, String eDate, List<String> list, List<String> lastList, String dataType) {
        Map<String, Object> map = new HashMap<>();
        Integer sWeek = Integer.valueOf(sDate);
        Integer eWeek = Integer.valueOf(eDate);
        // 获取前一年
        String yearBef = ApDateTime.dateAdd("y", -1, year, ApDateTime.DATE_TIME_Y);
        // 第一周和最后一周的首尾两天
        Date[] sWeekArr = ApDateTime.dayOfWeekOfYear(Integer.valueOf(year), sWeek);
        Date[] eWeekArr = ApDateTime.dayOfWeekOfYear(Integer.valueOf(year), eWeek);
        // 前一年的第一周和最后一周的首尾两天
        Date[] sWeekArrBef = ApDateTime.dayOfWeekOfYear(Integer.valueOf(yearBef), sWeek);
        Date[] eWeekArrBef = ApDateTime.dayOfWeekOfYear(Integer.valueOf(yearBef), eWeek);
        // 获取第一周的第一天和最后一周的最后一天
        try {
            sDate = ApDateTime.getDateTime(ApDateTime.DATE_TIME_YMD, sWeekArr[0].getTime());
            eDate = ApDateTime.getDateTime(ApDateTime.DATE_TIME_YMD, eWeekArr[1].getTime());
            list.add(sDate);
            list.add(eDate);
            sDate = ApDateTime.getDateTime(ApDateTime.DATE_TIME_YMD, sWeekArrBef[0].getTime());
            eDate = ApDateTime.getDateTime(ApDateTime.DATE_TIME_YMD, eWeekArrBef[1].getTime());
            lastList.add(sDate);
            lastList.add(eDate);
        } catch (Exception e) {
            LOGGER.error("[daysOnBoundaryOfWeek] Date to String failed ! e : ", e);
        }
        List<String> weekList = new ArrayList<>();

        Map<String, List<String>> weekMap = new LinkedHashMap<>();
        Map<String, List<String>> befWeekMap = new LinkedHashMap<>();
        if ("5".equals(dataType)) {
            // 获取第一周的前一周的第一天和最后一天
            String sevenDaysBef_now = ApDateTime.dateAdd("d", -7, sWeekArr[0], ApDateTime.DATE_TIME_YMD);
            String oneDayBef_now = ApDateTime.dateAdd("d", -1, sWeekArr[0], ApDateTime.DATE_TIME_YMD);
            String sevenDaysBef_bef = ApDateTime.dateAdd("d", -7, sWeekArrBef[0], ApDateTime.DATE_TIME_YMD);
            String oneDayBef_bef = ApDateTime.dateAdd("d", -1, sWeekArrBef[0], ApDateTime.DATE_TIME_YMD);
            // 获取第一周的前一周的每一天
            List<String> weekBef_now = ApDateTime.getDayBetween(sevenDaysBef_now, oneDayBef_now);
            List<String> weekBef_bef = ApDateTime.getDayBetween(sevenDaysBef_bef, oneDayBef_bef);
            weekMap.put("0", weekBef_now);
            befWeekMap.put("0", weekBef_bef);
        }

        weekList.add("供应商");
        for (int i = sWeek; i <= eWeek; i++) {
            Date[] week_i = ApDateTime.dayOfWeekOfYear(Integer.valueOf(year), i);
            Date[] week_i_bef = ApDateTime.dayOfWeekOfYear(Integer.valueOf(yearBef), i);
            List<String> days_i = ApDateTime.getDayBetween(week_i[0], week_i[1]);
            List<String> days_i_bef = ApDateTime.getDayBetween(week_i_bef[0], week_i_bef[1]);
            weekMap.put(String.valueOf(i), days_i);
            befWeekMap.put(String.valueOf(i), days_i_bef);
            String sDay = days_i.get(0);
            String eDay = days_i.get(days_i.size() - 1);
            weekList.add("第" + i + "周<br>(" + sDay + "至" + eDay + ")");
        }
        map.put("now", list);
        map.put("last", lastList);
        map.put("weekList", weekList);// 页面表头的时间
        map.put("weekMap", weekMap);
        map.put("befWeekMap", befWeekMap);
        return map;
    }

    /**
     * 封装上一期时间段
     *
     * @param map      时间map
     * @param list     当前时间集合
     * @param lastList 上一期的时间集合
     * @param type     时间类型 m:月，y:年
     */
    private void lastTimeList(Map<String, Object> map, List<String> list,
                              List<String> lastList, String type) {
        for (String day : list) {
            String lastDay = "";
            if ("m".equals(type)) {
                lastDay = ApDateTime.dateAdd(type, -1, day, ApDateTime.DATE_TIME_YMD);
            } else if ("y".equals(type)) {
                lastDay = ApDateTime.dateAdd(type, -1, day, ApDateTime.DATE_TIME_YM);
            }
            lastList.add(lastDay);
        }
        map.put("now", list);
        map.put("last", lastList);
    }

    /**
     * 获取指定时间段，指定供应商的统计数据
     *
     * @param suppliers 供应商
     * @param timeMap   时间
     * @param timeType  时间类型
     * @param dataType  数据类型
     * @return
     */
    private List<Map<String, Object>> activeUserData(List<Map<String, Object>> suppliers,
                                                     Map<String, Object> timeMap,
                                                     String timeType, String dataType) {
        // 获取指定时间段的开始和结束时间
        List<String> now = (List<String>) timeMap.get("now");
        String sNow = now.get(0);
        String eNow = now.get(now.size() - 1);
        // 获取“now”的上一期的开始和结束时间
        List<String> last = (List<String>) timeMap.get("last");
        String sLast = last.get(0);
        String eLast = last.get(last.size() - 1);

        List<String> sup = getSupCodeList(suppliers);

        // 查询
        List<Map<String, Object>> nowData = new ArrayList<>();
        List<Map<String, Object>> lastData = new ArrayList<>();

        if ("1".equals(dataType)) {// 下载
            nowData = userAnalyseDao.downloadUserData(sup, sNow, eNow, timeType);
            lastData = userAnalyseDao.downloadUserData(sup, sLast, eLast, timeType);
        }
        if ("2".equals(dataType)) {// 激活
            nowData = userAnalyseDao.activationUserData(sup, sNow, eNow, timeType);
            lastData = userAnalyseDao.activationUserData(sup, sLast, eLast, timeType);
        }
        if ("3".equals(dataType)) {// 注册
            nowData = userAnalyseDao.registerUserData(sup, sNow, eNow, timeType);
            lastData = userAnalyseDao.registerUserData(sup, sLast, eLast, timeType);
        }
        if ("4".equals(dataType)) {// 活跃
            nowData = userAnalyseDao.activeUserData(sup, sNow, eNow, timeType);
            lastData = userAnalyseDao.activeUserData(sup, sLast, eLast, timeType);
        }


        Map<String, Object> nowDataMap = splitData(sup, nowData);
        Map<String, Object> lastDataMap = splitData(sup, lastData);

        // 将结果集中没有的日期的数量设置为0
        nowDataMap = checkTime(now, nowDataMap);
        lastDataMap = checkTime(last, lastDataMap);

        // 合并同一供应商的不同时间的数据
        return supplierList(suppliers, nowDataMap, lastDataMap);
    }

    /**
     * 获取指定时间段每天和每天的前一天启动触键APP的用户数
     *
     * @param suppliers 供应商
     * @param sDate     开始时间
     * @param eDate     结束时间
     * @return {供应商 : {日期 : 数量, 日期 : 数量}}
     */
    public Map<String, Map<String, Integer>> stayingUserData(List<Map<String, Object>> suppliers,
                                                             String sDate, String eDate, String timeType) {
        Map<String, Map<String, Integer>> reqMap = new HashMap<>();
        if ("1".equals(timeType)) {// 日
            reqMap = stayingUserDataOfDay(reqMap, suppliers, sDate, eDate);
        }
        if ("3".equals(timeType)) {// 月
            reqMap = stayingUserDataOfMonth(reqMap, suppliers, sDate, eDate);
        }
        return reqMap;
    }

    // 留存用户数据-日
    public Map<String, Map<String, Integer>> stayingUserDataOfDay(
            Map<String, Map<String, Integer>> reqMap, List<Map<String, Object>> suppliers,
            String sDate, String eDate) {

        List<String> sup = getSupCodeList(suppliers);

        // 获取指定时间段的开始和结束时间
        List<String> daysBetween = ApDateTime.getDayBetween(sDate, eDate);
        // 第一天的前一天
//        String dayBefore = ApDateTime.dateAdd("d", -1, sDate, ApDateTime.DATE_TIME_YMD);
//        daysBetween.add(0, dayBefore);

        // TODO
        // 查询数据统计表最后一天的留存数据
        // TODO


        List<Map<String, Object>> eachResult = new ArrayList<>();
        for (int i = 0; i < daysBetween.size(); i++) {
            String eachDay = daysBetween.get(i);
            eachResult.addAll(userAnalyseDao.stayingData(sup, eachDay));
        }

        // { 供应商 : {日期1:{数据},日期2:{数据},日期3:{数据}} }
        Map<String, Map<String, List<Map<String, Object>>>> allData = initDataMap(suppliers, daysBetween);
        for (int i = 0; i < eachResult.size(); i++) {
            String code = eachResult.get(i).get("CODE").toString();// 查询结果中的供应商
            String day = eachResult.get(i).get("DAY").toString();// 查询结果中的日期
            for (String supCode : sup) {
                Map<String, List<Map<String, Object>>> map = allData.get(supCode);
                List<Map<String, Object>> list = map.get(day);
                if (code.equals(supCode)) {
                    list.add(eachResult.get(i));
                    map.put(day, list);
                    allData.put(code, map);
                }
            }
        }

        for (Map<String, Object> supplier : suppliers) {
            String supplier_code = supplier.get("C_SUPPLIER_CODE").toString();
            // 获取供应商对应的数据  { 供应商 : {日期1:{数据},日期2:{数据},日期3:{数据}} }
            Map<String, List<Map<String, Object>>> supData = allData.get(supplier_code);
            Map<String, Integer> dayMap = new LinkedHashMap<>();
            for (int i = 1; i < daysBetween.size(); i++) {
                String before = daysBetween.get(i - 1);// 第i天
                String after = daysBetween.get(i);// 第i+1天
                List<Map<String, Object>> beforeDayData = supData.get(before);
                List<Map<String, Object>> afterDayData = supData.get(after);

                dayMap.put(after, 0);// 每一天的日期作为key，value默认为0
                List<String> imeiList = new ArrayList<>();// 用于存放留存用户的imei

                for (int j = 0; j < afterDayData.size(); j++) {
                    String after_imei = afterDayData.get(j).get("C_IMEI").toString();
                    for (int k = 0; k < beforeDayData.size(); k++) {
                        String before_imei = beforeDayData.get(k).get("C_IMEI").toString();
                        // 如果连续两天都启动过，就是留存用户，存入imeiList
                        if (before_imei.equals(after_imei)) {
                            imeiList.add(after_imei);
                            break;
                        }
                    }
                }
                dayMap.put(after, imeiList.size());
            }
            reqMap.put(supplier_code, dayMap);
        }
        return reqMap;
    }

    // 封装{ 供应商 : {日期1:{数据},日期2:{数据},日期3:{数据}} }结构的map
    private Map<String, Map<String, List<Map<String, Object>>>> initDataMap(
            List<Map<String, Object>> suppliers, List<String> timesBetween) {

        Map<String, Map<String, List<Map<String, Object>>>> allData = new HashMap<>();
        for (Map<String, Object> supplier : suppliers) {
            String supplier_code = supplier.get("C_SUPPLIER_CODE").toString();
            Map<String, List<Map<String, Object>>> timeMap = new HashMap<>();
            for (int i = 0; i < timesBetween.size(); i++) {
                timeMap.put(timesBetween.get(i), new ArrayList<Map<String, Object>>());
            }
            allData.put(supplier_code, timeMap);
        }
        return allData;
    }

    // 留存用户数据-周
    public List<Map<String, Object>> stayingUserDataOfWeek(
            List<Map<String, Object>> suppliers, Map<String, Object> timeMap) {

        List<String> sup = getSupCodeList(suppliers);

        // 获取时间集合
        List<String> nowList = (List<String>) timeMap.get("now");
        Object obj_bef = timeMap.get("last");
        List<String> befList = null;
        if (StrUtils.isNotEmpty(obj_bef)) {
            befList = (List<String>) timeMap.get("last");
            Map<String, List<String>> weekMap = (Map<String, List<String>>) timeMap.get("weekMap");
            Map<String, List<String>> befWeekMap = (Map<String, List<String>>) timeMap.get("befWeekMap");

            String sevenDaysBef_now = weekMap.get("0").get(0);
            String sevenDaysBef_bef = befWeekMap.get("0").get(0);

            // 查询
            List<Map<String, Object>> nowData = userAnalyseDao.allStayingData(sup, sevenDaysBef_now, nowList.get(1));
            List<Map<String, Object>> befData = userAnalyseDao.allStayingData(sup, sevenDaysBef_bef, befList.get(1));

            // 按照 {供应商 : {imei : [日期,日期]}}封装map
            Map<String, Map<String, List<String>>> nowImeiMap = getEmeiDayMap(sup, nowData);
            Map<String, Map<String, List<String>>> befImeiMap = getEmeiDayMap(sup, befData);


            // 计算每个供应商每周的活跃用户数
            return getStayingDataOfWeek(suppliers, nowImeiMap, befImeiMap, weekMap, befWeekMap);
        } else {
            Map<String, List<String>> weekMap = (Map<String, List<String>>) timeMap.get("weekMap");

            String sevenDaysBef_now = weekMap.get("0").get(0);

            // 查询
            List<Map<String, Object>> nowData = userAnalyseDao.allStayingData(sup, sevenDaysBef_now, nowList.get(1));

            // 按照 {供应商 : {imei : [日期,日期]}}封装map
            Map<String, Map<String, List<String>>> nowImeiMap = getEmeiDayMap(sup, nowData);

            // 计算每个供应商每周的活跃用户数
            return getStayingDataOfWeek(suppliers, nowImeiMap, null, weekMap, null);
        }
    }

    /**
     * 计算每个供应商每周的活跃用户数
     *
     * @param suppliers  供应商
     * @param nowImeiMap 查询时间段的数据
     * @param befImeiMap 上期同期的数据
     * @param weekMap    每周对应的7个日期
     * @param befWeekMap 去年同期
     * @return [{sup:触键,data:[数量,数量]},{sup:阿智,data:[数量,数量]}]
     */
    private List<Map<String, Object>> getStayingDataOfWeek(
            List<Map<String, Object>> suppliers, Map<String, Map<String, List<String>>> nowImeiMap,
            Map<String, Map<String, List<String>>> befImeiMap, Map<String, List<String>> weekMap,
            Map<String, List<String>> befWeekMap) {
        List<Map<String, Object>> reqList = new ArrayList<>();
        Map<String, List<Integer>> nowDataMap;
        if (StrUtils.isNotEmpty(befImeiMap)) {
            nowDataMap = countListOfSup(suppliers, weekMap, nowImeiMap);
        } else {
            nowDataMap = countListOfSupRewrite(suppliers, weekMap, nowImeiMap);
        }
        Map<String, List<Integer>> befDataMap = null;
        if (StrUtils.isNotEmpty(befImeiMap)) {
            befDataMap = countListOfSup(suppliers, befWeekMap, befImeiMap);
        }
        for (Map<String, Object> supplier : suppliers) {
            Map<String, Object> map = new HashMap<>();
            List<Integer> list = new ArrayList<>();
            String code = supplier.get("C_SUPPLIER_CODE").toString();// 供应商代码
            String name = supplier.get("C_SUPPLIER_NAME").toString();// 供应商名称
            for (int i = 0; i < weekMap.size() - 1; i++) {
                List<Integer> nowCountList = nowDataMap.get(code);
                list.add(nowCountList.get(i));
                if (StrUtils.isNotEmpty(befImeiMap)) {
                    List<Integer> befCountList = befDataMap.get(code);
                    list.add(befCountList.get(i));
                }
            }
            map.put("sup", name);
            map.put("data", list);
            reqList.add(map);
        }

        return reqList;
    }

    /**
     * 封装每个供应商对应的数量的集合
     *
     * @param suppliers 供应商
     * @param timeMap   时间
     * @param dataMap   查询时间对应的数据
     * @return
     */
    public Map<String, List<Integer>> countListOfSup(
            List<Map<String, Object>> suppliers, Map<String, List<String>> timeMap, Map<String,
            Map<String, List<String>>> dataMap) {
        Map<String, List<Integer>> reqMap = new HashMap<>();
        // 遍历map，取出每个IMEI的日期
        for (Map<String, Object> supplier : suppliers) {
            String code = supplier.get("C_SUPPLIER_CODE").toString();// 供应商代码
            List<Integer> countList = new ArrayList<>();// 存放每一周的数量
            // 获取供应商对应的数据  { 供应商 : {日期1:{数据},日期2:{数据},日期3:{数据}} }
            Map<String, List<String>> imeiMap = dataMap.get(code);
            Set<String> imeiList = new HashSet<>();// 存放留存用户的IMEI

            List<String> temp_1 = new ArrayList<>();
            List<String> temp_2 = new ArrayList<>();
            List<String> temp_3 = new ArrayList<>();
            boolean flag = true;
            for (Iterator<Map.Entry<String, List<String>>> iterator = timeMap.entrySet().iterator(); iterator.hasNext(); ) {
                if (flag) {// 第一轮循环，获取前两个元素
                    temp_1 = iterator.next().getValue();
                    if (iterator.hasNext()) {
                        temp_2 = iterator.next().getValue();
                        temp_3.addAll(temp_2);
                    }
                } else {// 后面的循环每次取一个元素
                    temp_3 = iterator.next().getValue();
                }

                if (StrUtils.isNotEmpty(imeiMap)) {
                    for (Map.Entry<String, List<String>> entry_imei : imeiMap.entrySet()) {
                        String imei = entry_imei.getKey();// 用户IMEI
                        List<String> imeiDays = entry_imei.getValue();// 用户启动过的时间
                        // 如果用户在这7天和上一个7天都启动过，就是这一周的活跃用户
                        if (flag) {// 第一轮循环拿temp_1和temp_2与imeiDays比较
                            if (imeiDays.containsAll(temp_1) && imeiDays.containsAll(temp_2)) {
                                imeiList.add(imei);
                            }
                        } else {// 其他轮拿temp_2和temp_3与imeiDays比较
                            if (imeiDays.containsAll(temp_2) && imeiDays.containsAll(temp_3)) {
                                imeiList.add(imei);
                            }
                        }
                    }
                }
                flag = false;
                temp_2.clear();
                temp_2.addAll(temp_3);// 跳出循环前，将temp_3赋值给temp_2，用于下一轮使用
                countList.add(imeiList.size());
            }

            reqMap.put(code, countList);
        }
        return reqMap;
    }

    public Map<String, List<Integer>> countListOfSupRewrite(
            List<Map<String, Object>> suppliers, Map<String, List<String>> timeMap,
            Map<String, Map<String, List<String>>> dataMap) {
        Map<String, List<Integer>> reqMap = new HashMap<>();
        // 遍历map，取出每个IMEI的日期
        for (Map<String, Object> supplier : suppliers) {
            String code = supplier.get("C_SUPPLIER_CODE").toString();// 供应商代码
            List<Integer> countList = new ArrayList<>();// 存放每一周的数量
            // 获取供应商对应的数据  { 供应商 : {日期1:{数据},日期2:{数据},日期3:{数据}} }
            Map<String, List<String>> imeiMap = dataMap.get(code);
            Set<String> imeiList = new HashSet<>();// 存放留存用户的IMEI

            List<String> temp_1 = new ArrayList<>();
            List<String> temp_2 = new ArrayList<>();
            List<String> temp_3 = new ArrayList<>();
            boolean flag = true;
            for (Iterator<Map.Entry<String, List<String>>> iterator = timeMap.entrySet().iterator(); iterator.hasNext(); ) {
                if (flag) {// 第一轮循环，获取前两个元素
                    temp_1 = iterator.next().getValue();
                    if (iterator.hasNext()) {
                        temp_2 = iterator.next().getValue();
                        temp_3.addAll(temp_2);
                    }
                } else {// 后面的循环每次取一个元素
                    temp_3 = iterator.next().getValue();
                }

                if (StrUtils.isNotEmpty(imeiMap)) {
                    for (Map.Entry<String, List<String>> entry_imei : imeiMap.entrySet()) {
                        String imei = entry_imei.getKey();// 用户IMEI
                        List<String> imeiDays = entry_imei.getValue();// 用户启动过的时间
                        // 如果用户在这7天和上一个7天至少启动过两天，就是这一周的活跃用户
                        if (flag) {// 第一轮循环拿temp_1和temp_2与imeiDays比较
                            int breakFlag_1 = 0;
                            boolean containsFlag_1 = false;
                            int breakFlag_2 = 0;
                            boolean containsFlag_2 = false;
                            for (int i = 0; i < temp_1.size(); i++) {
                                String eachDay = temp_1.get(i);
                                if (imeiDays.contains(eachDay)) {
                                    ++breakFlag_1;
                                }
                                if (breakFlag_1 == 2) {
                                    containsFlag_1 = true;
                                    break;
                                }
                            }
                            for (int i = 0; i < temp_2.size(); i++) {
                                String eachDay = temp_2.get(i);
                                if (imeiDays.contains(eachDay)) {
                                    ++breakFlag_2;
                                }
                                if (breakFlag_2 == 2) {
                                    containsFlag_2 = true;
                                    break;
                                }
                            }
                            if (containsFlag_1 && containsFlag_2) {
                                imeiList.add(imei);
                            }

                        } else {// 其他轮拿temp_2和temp_3与imeiDays比较
                            int breakFlag_1 = 0;
                            boolean containsFlag_1 = false;
                            int breakFlag_2 = 0;
                            boolean containsFlag_2 = false;
                            for (int i = 0; i < temp_2.size(); i++) {
                                String eachDay = temp_2.get(i);
                                if (imeiDays.contains(eachDay)) {
                                    ++breakFlag_1;
                                }
                                if (breakFlag_1 == 2) {
                                    containsFlag_1 = true;
                                    break;
                                }
                            }
                            for (int i = 0; i < temp_3.size(); i++) {
                                String eachDay = temp_3.get(i);
                                if (imeiDays.contains(eachDay)) {
                                    ++breakFlag_2;
                                }
                                if (breakFlag_2 == 2) {
                                    containsFlag_2 = true;
                                    break;
                                }
                            }
                            if (containsFlag_1 && containsFlag_2) {
                                imeiList.add(imei);
                            }
                        }
                    }
                }
                flag = false;
                temp_2.clear();
                temp_2.addAll(temp_3);// 跳出循环前，将temp_3赋值给temp_2，用于下一轮使用
                countList.add(imeiList.size());
            }

            reqMap.put(code, countList);
        }
        return reqMap;
    }

    /**
     * 按照 {imei : [日期,日期]}封装map
     *
     * @param sup  供应商
     * @param data 原始数据 list是数据库查询的结果，每一行数据是CODE,IMEI,DAY
     * @return
     */
    public Map<String, Map<String, List<String>>> getEmeiDayMap(List<String> sup, List<Map<String, Object>> data) {
        Map<String, Map<String, List<String>>> supMap = new HashMap<>();
        for (String s : sup) {
            Map<String, List<String>> imeiMap = new HashMap<>();
            if (StrUtils.isNotEmpty(data)) {
                for (int i = 0; i < data.size(); i++) {
                    Map<String, Object> map = data.get(i);
                    String code = map.get("CODE").toString();
                    String imei = map.get("C_IMEI").toString();
                    String day = map.get("DAY").toString();
                    if (code.equals(s)) {
                        List<String> dayList = imeiMap.get(imei);
                        if (StrUtils.isEmpty(dayList)) {
                            dayList = new ArrayList<>();
                        }
                        dayList.add(day);
                        imeiMap.put(imei, dayList);
                    }
                }
            }
            supMap.put(s, imeiMap);
        }
        return supMap;
    }

    // 留存用户数据-月
    private Map<String, Map<String, Integer>> stayingUserDataOfMonth(
            Map<String, Map<String, Integer>> reqMap, List<Map<String, Object>> suppliers,
            String sDate, String eDate) {

        try {
            // 从查询的首月到末月的所有月的集合
            List<String> monthsBetween = ApDateTime.getMonthBetween(sDate, eDate);
            // 第一个月的前一个月
            String beforeMonth = ApDateTime.dateAdd("m", -1, sDate + "-01", ApDateTime.DATE_TIME_YMD);
            monthsBetween.add(0, beforeMonth.substring(0, 7));

            List<String> sup = getSupCodeList(suppliers);

            String firstMonth = monthsBetween.get(0);// 第一个月
            String lastMonth = monthsBetween.get(monthsBetween.size() - 1);// 最后一个月
            String first = "-";
            String last = "-";
            try {
                //first += "-01";
                first += ApDateTime.getFirstDayOfMonth(firstMonth, "yyyy-MM");// 首月月初
                last += ApDateTime.getLastDayOfMonth(lastMonth, "yyyy-MM");// 末月月末
            } catch (ParseException e) {
                LOGGER.error("[stayingUserDataOfWeek] getFirst&last day failed ! e : ", e);
            }
            List<Map<String, Object>> allData =
                    userAnalyseDao.allStayingData(sup, firstMonth + first, lastMonth + last);

            String timeFlag = String.valueOf(System.currentTimeMillis());// 当前时间表为标记

            // 全部存入临时表中
            userAnalyseDao.intoTempTable(allData, timeFlag);

            // 查询临时表的数据
            List<Map<String, Object>> tempResult = userAnalyseDao.getTempData(timeFlag);

            // 初始化数据集合，存入“供应商”、“月”、“每月留存用户数量（初始设置为0）”
            for (Map<String, Object> supplier : suppliers) {
                String supplier_code = supplier.get("C_SUPPLIER_CODE").toString();
                HashMap<String, Integer> supMap = new HashMap<>();
                // 从脚标1开始循环，不放入首月的前一个月
                for (int i = 1; i < monthsBetween.size(); i++) {
                    supMap.put(monthsBetween.get(i), 0);
                }
                reqMap.put(supplier_code, supMap);
            }

            // 从结果中统计留存用户数量
            for (int i = 0; i < tempResult.size(); i++) {
                String codeBefore = tempResult.get(i).get("CODE").toString();
                String imeiBefore = tempResult.get(i).get("IMEI").toString();
                String monthBefore = tempResult.get(i).get("MONTH").toString();
                Integer totalBefore = Integer.valueOf(tempResult.get(i).get("TOTAL").toString());
                int totalDayBefore = 0;
                try {
                    // 获取本月总天数
                    totalDayBefore = ApDateTime.getCountOfMonth(monthBefore, "yyyy-MM");
                } catch (ParseException e) {
                    LOGGER.error("[stayingUserDataOfWeek] getCountOfMonth failed ! e : ", e);
                }
                if (totalDayBefore != 0 && totalBefore != 0 && totalBefore == totalDayBefore) {
                    for (int j = 0; j < tempResult.size(); j++) {
                        String codeAfter = tempResult.get(j).get("CODE").toString();
                        String imeiAfter = tempResult.get(j).get("IMEI").toString();
                        String monthAfter = tempResult.get(j).get("MONTH").toString();
                        Integer totalAfter = Integer.valueOf(tempResult.get(j).get("TOTAL").toString());
                        if (codeBefore.equals(codeAfter) && imeiBefore.equals(imeiAfter)) {
                            // 前一月的月份
                            Integer dayBefore = Integer.valueOf(monthBefore.substring(monthBefore.length() - 1));
                            // 后一月的月份
                            Integer dayAfter = Integer.valueOf(monthAfter.substring(monthAfter.length() - 1));
                            if ((dayAfter - dayBefore) == 1) {// 如果两个月相邻
                                int totalDayAfter = 0;
                                try {
                                    // 获取本月总天数
                                    totalDayAfter = ApDateTime.getCountOfMonth(monthAfter, "yyyy-MM");
                                } catch (ParseException e) {
                                    LOGGER.error("[stayingUserDataOfWeek] getCountOfMonth failed ! e : ", e);
                                }
                                if (totalDayAfter != 0 && totalAfter != 0 && totalDayAfter == totalAfter) {
                                    Map<String, Integer> supMap = reqMap.get(codeBefore);
                                    supMap.put(monthAfter, supMap.get(monthAfter) + 1);
                                }
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            LOGGER.error("stayingUserDataOfMonth failed ! e : ", e);
        }
        return reqMap;
    }

    /**
     * 按照供应商将结果拆分
     *
     * @param sup  供应商code
     * @param data 查询结果集
     */
    private Map<String, Object> splitData(List<String> sup, List<Map<String, Object>> data) {
        Map<String, Object> dataMap = new HashMap<>();
        // 将每个供应商作为key存入dataMap
        for (String s : sup) {
            dataMap.put(s, new ArrayList<Map<String, Object>>());
        }
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            List<Map<String, Object>> value = (List<Map<String, Object>>) entry.getValue();
            if (StrUtils.isNotEmpty(data)) {
                for (int i = 0; i < data.size(); i++) {
                    String code = data.get(i).get("CODE").toString();// 供应商code
                    if (key.equals(code)) {
                        value.add(data.get(i));
                        //data.remove(i);
                    }
                }
            }

        }

        return dataMap;
    }

    /**
     * 补全查询结果集中的时间。没有的时间，其对应的数量为0
     *
     * @param time    查询条件中的时间集合
     * @param dataMap 结果集
     */
    private Map<String, Object> checkTime(List<String> time, Map<String, Object> dataMap) {
        // 初始化一个临时Map, 键是code，值是该code对应的所有查询日期的MAP，该MAP的“TOTAL”初始值为0
        Map<String, Object> tempDataMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String code = entry.getKey();
            List<Map<String, Object>> list = new ArrayList<>();
            for (int i = 0; i < time.size(); i++) {
                String t = time.get(i);
                Map<String, Object> data = new LinkedMap();
                data.put("DAY", t);
                data.put("TOTAL", 0);
                data.put("CODE", code);
                list.add(data);
            }
            tempDataMap.put(code, list);
        }
        // 遍历数据集合，如果改集合与临时MAP的code和日期相同，替换真是的TOTAL值
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String code = entry.getKey();
            List<Map<String, Object>> tempList = (List<Map<String, Object>>) tempDataMap.get(code);
            List<Map<String, Object>> value = (List<Map<String, Object>>) entry.getValue();
            for (int j = 0; j < tempList.size(); j++) {
                Map<String, Object> tempMap = tempList.get(j);
                String tempDay = tempMap.get("DAY").toString();
                for (int i = 0; i < value.size(); i++) {
                    Map<String, Object> map = value.get(i);
                    String day = map.get("DAY").toString();
                    if (tempDay.equals(day)) {
                        tempMap.put("TOTAL", map.get("TOTAL"));
                        break;
                    }
                }
            }
        }
        return tempDataMap;
    }

    /**
     * 查询结果中没有的日期数量用0补齐
     *
     * @param tempList 存放与查询结果集结构相同的集合
     * @param key      供应商代码
     * @param time     时间
     * @param i        时间集合的脚标
     */
    private void setZero2MissedTime(
            List<Map<String, Object>> tempList, String key, List<String> time, int i) {
        Map<String, Object> map = new HashMap<>();
        map.put("CODE", key);
        map.put("DAY", time.get(i));
        map.put("TOTAL", 0);
        tempList.add(map);
    }

    /**
     * 获取供应商代码集合
     *
     * @param suppliers 查询的供应商结果集
     * @return
     */
    public List<String> getSupCodeList(List<Map<String, Object>> suppliers) {
        List<String> sup = new ArrayList<>();
        if (StrUtils.isNotEmpty(suppliers)) {
            for (Map<String, Object> supplier : suppliers) {
                sup.add(supplier.get("C_SUPPLIER_CODE").toString());
            }
        }
        return sup;
    }

    /**
     * 补全查询结果集中的时间。没有的时间，其对应的数量为0
     *
     * @param suppliers 供应商
     * @param weekMap   周的集合
     * @param dataList  数据
     */
    private List<Map<String, Object>> checkTimeWeek(
            List<Map<String, Object>> suppliers, Map<String, List<String>> weekMap, List<Map<String, Object>> dataList) {

        List<Map<String, Object>> reqList = new ArrayList<>();
        for (Map<String, Object> sup : suppliers) {
            Map<String, Object> lineMap = new HashMap<>();
            String c_code = sup.get("C_SUPPLIER_CODE").toString();
            String c_name = sup.get("C_SUPPLIER_NAME").toString();
            List<String> supList = new ArrayList<>();
            for (Map.Entry<String, List<String>> entry : weekMap.entrySet()) {
                List<String> daysInWeek = entry.getValue();// 每周的7天
                int weekCnt = 0;
                if (StrUtils.isNotEmpty(dataList)) {
                    for (int j = 0; j < dataList.size(); j++) {
                        Map<String, Object> map = dataList.get(j);
                        String day = map.get("DAY").toString();
                        String code = map.get("CODE").toString();
                        Integer total = StrUtils.zeroOrInt(map.get("TOTAL"));
                        if (c_code.equals(code) && daysInWeek.contains(day)) {
                            weekCnt = weekCnt + total;
                        }
                    }
                }
                supList.add(String.valueOf(weekCnt));
            }
            lineMap.put("sup", c_name);
            lineMap.put("data", supList);
            reqList.add(lineMap);
        }
        return reqList;
    }

    private List<Map<String, Object>> supplierList(List<Map<String, Object>> suppliers,
                                                   Map<String, Object> nowDataMap,
                                                   Map<String, Object> lastDataMap) {
        List<Map<String, Object>> data = new ArrayList<>();
        for (Map<String, Object> supplier : suppliers) {
            Map<String, Object> map = new HashMap<>();
            List<Object> list = new ArrayList<>();
            String code = supplier.get("C_SUPPLIER_CODE").toString();// 供应商代码
            String name = supplier.get("C_SUPPLIER_NAME").toString();// 供应商名称

            // 指定时间段的查询结果
            List<Map<String, Object>> nowData = (List<Map<String, Object>>) nowDataMap.get(code);
            // 指定时间段上一期同期的查询结果
            List<Map<String, Object>> lastData = (List<Map<String, Object>>) lastDataMap.get(code);
            for (int i = 0; i < nowData.size(); i++) {
                // 分别获取两期的数量，以当前时间在前，上一期同期时间在后的顺序交叉存入list
                String nowTotal = nowData.get(i).get("TOTAL").toString();
                String lastTotal = lastData.get(i).get("TOTAL").toString();
                list.add(nowTotal);
                list.add(lastTotal);
            }
            map.put("sup", name);
            map.put("data", list);
            data.add(map);
        }

        return data;
    }

    @Override
    public Map<String, Object> downloadUser(String userId, String sup,
                                            String sDate, String eDate, String timeType, String year, String dataType) {
        return activeUser(userId, sup, sDate, eDate, timeType, year, dataType);
    }

    @Override
    public Map<String, Object> activationUser(String userId, String sup,
                                              String sDate, String eDate, String timeType, String year, String dataType) {
        return activeUser(userId, sup, sDate, eDate, timeType, year, dataType);
    }

    @Override
    public Map<String, Object> registerUser(String userId, String sup,
                                            String sDate, String eDate, String timeType, String year, String dataType) {
        return activeUser(userId, sup, sDate, eDate, timeType, year, dataType);
    }

    // 数据库供应商代码字段名
    private final String supCodeColKey = "C_SUPPLIER_CODE";
    private final String supNameColKey = "C_SUPPLIER_NAME";
    // 查询结果别名
    // 供应商代码
    private final String supCodeColKey2 = "CODE";
    // 时间
    private final String timeColKey = "DAY";
    // 数
    private final String countColKey = "TOTAL";


    /**
     * 最新综合统计
     *
     * @param userId   当前用户ID
     * @param sup      供应商代码
     * @param sDate    开始时间
     * @param eDate    结束时间
     * @param timeType 时间类型： 1:日、2:周、3:月
     * @return
     */

    @Override
    public List<List<Object>> allcomplexStatistics(String userId, String sup, String sDate, String eDate, String timeType) {
        Calendar calendar = Calendar.getInstance();
        //默认所有供应商
        String defaultSup = "0";
        // 可见供应商集合
        List<Map<String, Object>> suppliers;
        //下载、激活、注册、使用、留存
        List<String> l_supCode = new ArrayList<String>();
        Map<String, String> m_supName = new HashMap<String, String>();
        List<List<Object>> All = new ArrayList<List<Object>>();
        // 时间区间
        List<String> l_time = new ArrayList<String>();
        List<SupVo> l_supVo = new ArrayList<SupVo>();
        // 使用次数集合
        List<Map<String, Object>> l_activeUser;
        // 激活用户集合
        List<Map<String, Object>> l_activationUser;
        // 下载用户集合
        List<Map<String, Object>> l_downloadUser;
        // 注册用户集合
        List<Map<String, Object>> l_registerUser;
        //使用按键数
        List<Map<String, Object>> useKeys;
        //7天使用按键数
        List<Map<String, Object>> useSevenKeys;
        //30天使用按键数
        List<Map<String, Object>> useThirtyKeys;
        //使用用户数
        List<Map<String, Object>> timesOfUsingUser;
        //使用7用户数
        List<Map<String, Object>> timesOfSevenUsingUsers = new ArrayList<>();
        //使用数和任意按键数的合计
        List<Map<String, Object>> useAndKeysAll = new ArrayList<>();
        //使用30用户数
        List<Map<String, Object>> timesOfThirtyUsingUsers = new ArrayList<>();
        //使用7用户数
        List<Map<String, Object>> timesOfSevenUsingUser = new ArrayList<>();
        //使用30用户数
        List<Map<String, Object>> timesOfThirtyUsingUser = new ArrayList<>();
        List<Map<String, Object>> freeCallUserCount = new ArrayList<>();
        List<TimeVo> l_timeVo = new ArrayList<TimeVo>();
        String timePattern = ApDateTime.DATE_TIME_YMD;
        if (StringUtils.isEmpty(sup)) {
            sup = defaultSup;
        }
        try {
            //查询所有供应商
            suppliers = userAnalyseDao.visibleSupplier(userId, sup);
            if (suppliers != null && suppliers.size() >= 1) {
                for (int i = 0; i <= suppliers.size() - 1; ++i) {
                    Map<String, Object> m_sup = suppliers.get(i);
                    String supCode = m_sup.get(supCodeColKey).toString();
                    l_supCode.add(supCode);
                    // 供应商中文名称与代码对应
                    m_supName.put(supCode, m_sup.get(supNameColKey).toString());
                }
            } else {
                // 没有供应商，查询没有意义，这边不考虑有权限查询全部
                return null;
            }

            // 激活用户数
            l_activationUser = userAnalyseDao.activationUserData(l_supCode, sDate, eDate, timeType);
            // 下载用户数
            l_downloadUser = userAnalyseDao.downloadUserData(l_supCode, sDate, eDate, timeType);
            // 注册用户数
            l_registerUser = userAnalyseDao.registerUserData(l_supCode, sDate, eDate, timeType);
            //使用数
            timesOfUsingUser = ekeyKeyUsingDao.countOfAllUsingUser(timePattern, l_supCode, sDate, eDate);
            //按键数
            useKeys = ekeyKeyUsingDao.countOfAllUseKeys(timePattern, l_supCode, sDate, eDate);
            //合计
            useAndKeysAll = ekeyKeyUsingDao.countOfuseAndKeysAll(timePattern, l_supCode, sDate, eDate);

            //免费通话人数总数
            freeCallUserCount = ekeyKeyUsingDao.freeCallUserCount(l_supCode, sDate, eDate);

            l_time = ApDateTime.getDayBetween(sDate, eDate);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            //时间区间集合
            //日期一样的供应商相加
            int AllTotal;
            String supCode = "";
            for (int i = 0; i < l_supCode.size(); ++i) {
                supCode = l_supCode.get(i);
            }

            Long countdownloadUser = 0l;
            Long countactivationUser = 0l;
            Long countregisterUser = 0l;
            Long countUse = 0l;
            Long countUseKeys = 0l;
            Long countUseAndKeysAll = 0l;
            Long allcountdownloadUser = 0l;
            Long allcountactivationUser = 0l;
            Long allcountregisterUser = 0l;
            int allFreeCllUserCount = 0;
            //合计下载数，激活数，激活率，注册数，注册率
            List<Object> complexAll = new ArrayList<Object>();
            // 报表数据展示数据传输对象集合
            for (int i = 0; i < l_time.size(); ++i) {
                String time = l_time.get(i);
                // 报表数据展示数据传输对象集合
                List<Object> complex = new ArrayList<Object>();
                // 设置时间
                complex.add(time);
                //下载数
                countdownloadUser = _processUserCountByTimeAndAll(l_downloadUser, 0l, supCode, time, "1");
                complex.add(countdownloadUser + "");
                allcountdownloadUser += countdownloadUser;

                //激活数
                countactivationUser = _processUserCountByTimeAndAll(l_activationUser, 0l, supCode, time, "1");
                complex.add(countactivationUser + "");
                allcountactivationUser += countactivationUser;

                //激活率
                complex.add(getRate(Integer.parseInt(countactivationUser + ""), Integer.parseInt(countdownloadUser + "")));

                //注册数
                countregisterUser = _processUserCountByTimeAndAll(l_registerUser, 0l, supCode, time, "1");
                complex.add(countregisterUser + "");
                allcountregisterUser += countregisterUser;
                //注册率
                complex.add(getRate(Integer.parseInt(countregisterUser + ""), Integer.parseInt(countactivationUser + "")));
                //免费通话日人数
                for (int t = 0; t < freeCallUserCount.size(); t++) {
                    Map map = freeCallUserCount.get(t);
                    String day = StrUtils.emptyOrString(map.get("DAY"));
                    String count = StrUtils.emptyOrString(map.get("TOTAL"));
                    if (day.equals(time)) {
                        complex.add(count);
                        allFreeCllUserCount += Integer.parseInt(count);
                    }
                }
                //使用数
                countUse = _processUserCountByTimeAndAll(timesOfUsingUser, 0l, supCode, time, "1");
                complex.add(countUse + "");
                //按键数
                countUseKeys = _processUserCountByTimeAndAll(useKeys, 0l, supCode, time, "1");
                complex.add(countUseKeys + "");
                //按键数和使用数的合计
                countUseAndKeysAll = _processUserCountByTimeAndAll(useAndKeysAll, 0l, supCode, time, "1");
                complex.add(countUseAndKeysAll + "");
//                //7天打开图标数
//                Object times=l_time.get(i);
//                Date dates = formatter.parse(StrUtils.emptyOrString(times));
//                String date1= ApDateTime.dateAdd("d", -6, dates, ApDateTime.DATE_TIME_YMD);
//                timesOfSevenUsingUser = ekeyKeyUsingDao.countOfAllUsingUser(timePattern, l_supCode,
//                        date1, StrUtils.emptyOrString(times));
//                defaultCount= _processUserCountByTimeAndAll(timesOfSevenUsingUser, 0l, supCode, time, "2");
//                complex.add(defaultCount + "");
//                //30天打开图标数
//               timesOfThirtyUsingUser = ekeyKeyUsingDao.countOfAllUsingUser(timePattern,
//                       l_supCode, ApDateTime.dateAdd("d", -29, dates, ApDateTime.DATE_TIME_YMD), StrUtils.emptyOrString(times));
//                defaultCount = _processUserCountByTimeAndAll(timesOfThirtyUsingUser, 0l, supCode, time, "3");
//                complex.add(defaultCount + "");
             /*   //7天按键使用数
                useSevenKeys= ekeyKeyUsingDao.countOfAllUseKeys(timePattern, l_supCode,
                        date1, StrUtils.emptyOrString(times));
                defaultCount= _processUserCountByTimeAndAll(useSevenKeys, 0l, supCode, time, "2");
                complex.add(defaultCount + "");
                //30天按键使用数
                useThirtyKeys= ekeyKeyUsingDao.countOfAllUseKeys(timePattern, l_supCode,
                        ApDateTime.dateAdd("d", -29, dates, ApDateTime.DATE_TIME_YMD), StrUtils.emptyOrString(times));
                defaultCount= _processUserCountByTimeAndAll(useThirtyKeys, 0l, supCode, time, "3");
                complex.add(defaultCount + "");
*/
                //使用数和按键数的合计
                All.add(complex);
            }

            //留存总数
            int stayUserAll = 0;
            //激活总数
            int activityUserAll = 0;
            int freeCallUserAll = 0;
            /******查找留存数据1：日留存2：周7天 3 ：周七天内  时间： 2016-5-20 START******/
            List<List<Object>> carryOver = ekeyKeyUsingService.carryOverUser(userId, sup, sDate, eDate);

            for (int j = 0; j < carryOver.size(); ++j) {
                stayUserAll += Integer.parseInt(carryOver.get(j).get(0).toString());
                activityUserAll += Integer.parseInt(carryOver.get(j).get(2).toString());
                freeCallUserAll += Integer.parseInt(carryOver.get(j).get(4).toString());
            }
            for (int j = 0; j < All.size(); ++j) {
                All.get(j).addAll(carryOver.get(j));
            }
            complexAll.add("合计");
            //下载数
            complexAll.add(allcountdownloadUser);
            //激活数
            complexAll.add(allcountactivationUser);
            //激活率
            complexAll.add(getRate(Integer.parseInt(allcountactivationUser + ""), Integer.parseInt(allcountdownloadUser + "")));
            //注册数
            complexAll.add(allcountregisterUser);
            //注册率
            complexAll.add(getRate(Integer.parseInt(allcountregisterUser + ""), Integer.parseInt(allcountactivationUser + "")));
            complexAll.add(allFreeCllUserCount);
            complexAll.add("");
            complexAll.add("");
            complexAll.add("");
            //留存总数
            complexAll.add(stayUserAll);
            complexAll.add(getRate(Integer.parseInt(stayUserAll + ""), Integer.parseInt(allcountregisterUser + "")));
            //激活总数
            complexAll.add(activityUserAll);
            complexAll.add(getRate(Integer.parseInt(activityUserAll + ""), Integer.parseInt(allcountactivationUser + "")));
            //免费通话总数
            complexAll.add(freeCallUserAll);
            complexAll.add("");
            complexAll.add(getRate(freeCallUserAll, allFreeCllUserCount));
            All.add(complexAll);
        } catch (Exception e) {
            LOGGER.error("综合统计错误", e);
        }

        return All;
    }


    /**
     * 根据日期相同数量相加
     *
     * @param mapList
     * @param initCount
     * @param supCode
     * @param time
     * @return
     */


    public Long _processUserCountByTimeAndAll(List<Map<String, Object>> mapList,
                                              Long initCount, String supCode, String time, String day) {
        Long defaultCount = 0l;
        for (int k = 0; k <= mapList.size() - 1; ++k) {
            // 集合内，和这个时间相等的数量加起来
            Map<String, Object> map = mapList.get(k);
            Object _supCode = map.get(supCodeColKey2);

            Object _time = map.get(timeColKey);

            Object count = map.get(countColKey);

            if (day.equals("1")) {
                if (_time.toString().equalsIgnoreCase(time)) {
                    {

                        initCount += (count == null ? defaultCount : Long.valueOf(count.toString()));

                    }

                }
            } else if (day.equals("2")) {

                String d = ApDateTime.dateAdd("d", -6, time, ApDateTime.DATE_TIME_YMD);

                List<String> dayBetween = ApDateTime.getDayBetween(d, time);


                if (dayBetween.contains(_time.toString())) {
                    initCount += (count == null ? defaultCount : Long.valueOf(count.toString()));


                }
            } else if (day.equals("3")) {

                String d = ApDateTime.dateAdd("d", -29, time, ApDateTime.DATE_TIME_YMD);

                List<String> dayBetween = ApDateTime.getDayBetween(d, time);

                if (dayBetween.contains(_time.toString())) {
                    initCount += (count == null ? defaultCount : Long.valueOf(count.toString()));

                }
            }
        }


        return initCount;
    }


    @Override
    public Map<String, Object> complexStatistics(String userId, String sup, String sDate, String eDate, String timeType, String year) {
        // 方法返回结果
        Map<String, Object> result = new HashMap<String, Object>();
        Calendar calendar = Calendar.getInstance();
        int _year;
        int _sDate;
        int _eDate;
        //默认按月纬度统计
        String defaultTimeType = "3";
        //默认所有供应商
        String defaultSup = "0";
        // 可见供应商集合
        List<Map<String, Object>> suppliers;
        //下载、激活、注册、活跃、留存
        List<String> l_supCode = new ArrayList<String>();
        Map<String, String> m_supName = new HashMap<String, String>();
        // 时间区间
        List<String> l_time = new ArrayList<String>();

        // 报表数据展示数据传输对象集合
        List<SupVo> l_supVo = new ArrayList<SupVo>();
        // 活跃用户集合
        List<Map<String, Object>> l_activeUser;
        // 激活用户集合
        List<Map<String, Object>> l_activationUser;
        // 下载用户集合
        List<Map<String, Object>> l_downloadUser;
        // 注册用户集合
        List<Map<String, Object>> l_registerUser;
        // 留存用户集合（二次处理）
        List<Map<String, Object>> l_stayingUser = null;
        // 留存用户对象，数据需要处理
        Map<String, Map<String, Integer>> m_stayingUser;

        // 默认当前年
        if (StringUtils.isEmpty(year)) {
            _year = calendar.get(Calendar.YEAR);
        } else {
            _year = Integer.parseInt(year);
        }
        //默认当前月
        if (StringUtils.isEmpty(sDate)) {
            _sDate = calendar.get(Calendar.MONTH) + 1;
        } else {
            // 可以是周（1-52），也可以是月（1-12）
            _sDate = Integer.parseInt(sDate);
        }

        if (StringUtils.isEmpty(eDate)) {
            _eDate = calendar.get(Calendar.MONTH) + 1;
        } else {
            _eDate = Integer.parseInt(eDate);
        }

        if (StringUtils.isEmpty(timeType)) {
            timeType = defaultTimeType;
        }

        if (StringUtils.isEmpty(sup)) {
            sup = defaultSup;
        }

        try {
            suppliers = userAnalyseDao.visibleSupplier(userId, sup);

            if (suppliers != null && suppliers.size() >= 1) {
                for (int i = 0; i <= suppliers.size() - 1; ++i) {
                    Map<String, Object> m_sup = suppliers.get(i);
                    String supCode = m_sup.get(supCodeColKey).toString();
                    l_supCode.add(supCode);

                    // 供应商中文名称与代码对应
                    m_supName.put(supCode, m_sup.get(supNameColKey).toString());
                }
            } else {
                // 没有供应商，查询没有意义，这边不考虑有权限查询全部
                return null;
            }

            // 月间隔集合
            if (defaultTimeType.equalsIgnoreCase(timeType)) {
                sDate = _year + "-" + _sDate;
                eDate = _year + "-" + _eDate;
                l_time = ApDateTime.getMonthBetween(sDate, eDate);
            } else {
                Date[] sWeekArr = ApDateTime.dayOfWeekOfYear(_year, _sDate);
                Date[] eWeekArr = ApDateTime.dayOfWeekOfYear(_year, _eDate);

                sDate = ApDateTime.getDateTime(ApDateTime.DATE_TIME_YMD, sWeekArr[0].getTime());
                eDate = ApDateTime.getDateTime(ApDateTime.DATE_TIME_YMD, eWeekArr[1].getTime());
            }

            // 活跃用户数
            l_activeUser = userAnalyseDao.activeUserData(l_supCode, sDate, eDate, timeType);

            // 激活用户数
            l_activationUser = userAnalyseDao.activationUserData(l_supCode, sDate, eDate, timeType);

            // 下载用户数
            l_downloadUser = userAnalyseDao.downloadUserData(l_supCode, sDate, eDate, timeType);

            // 注册用户数
            l_registerUser = userAnalyseDao.registerUserData(l_supCode, sDate, eDate, timeType);

            // 留存用户数
            if (defaultTimeType.equalsIgnoreCase(timeType)) {
                m_stayingUser = stayingUserData(suppliers, sDate, eDate, timeType);
                l_stayingUser = processStayingUserData(m_stayingUser);
            } else {
                sDate = ApDateTime.dateAdd("d", -7, sDate, "yyyy-MM-dd");
                List<Map<String, Object>> nowData = userAnalyseDao.allStayingData(l_supCode, sDate, eDate);
                Map<String, Map<String, List<String>>> nowImeiMap = getEmeiDayMap(l_supCode, nowData);
                Map<String, List<String>> weekMap = new LinkedHashMap<String, List<String>>();
                String _0 = ApDateTime.dateAdd("d", -1, sDate, "yyyy-MM-dd");
                List<String> _l0 = ApDateTime.getDayBetween(sDate, _0);
                weekMap.put("0", _l0);
                for (int i = _sDate; i <= _eDate; ++i) {
                    Date[] weekArr = ApDateTime.dayOfWeekOfYear(_year, i);
                    String _s = ApDateTime.getDateTime(ApDateTime.DATE_TIME_YMD, weekArr[0].getTime());
                    String _e = ApDateTime.getDateTime(ApDateTime.DATE_TIME_YMD, weekArr[1].getTime());
                    weekMap.put(String.valueOf(i), ApDateTime.getDayBetween(_s, _e));
                }

                Map<String, List<Integer>> nowDataMap = countListOfSup(suppliers, weekMap, nowImeiMap);
                m_stayingUser = new LinkedHashMap<String, Map<String, Integer>>();
                for (Iterator<Map.Entry<String, List<Integer>>> iterator = nowDataMap.entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<String, List<Integer>> entry = iterator.next();
                    Map<String, Integer> _m = new HashMap<String, Integer>();
                    for (int w = _sDate, index = 0; w <= _eDate; ++w, ++index) {
                        _m.put(String.valueOf(w), entry.getValue().get(index));
                    }
                    m_stayingUser.put(entry.getKey(), _m);
                }

                l_stayingUser = processStayingUserData(m_stayingUser);
            }

            for (int i = 0; i < l_supCode.size(); ++i) {
                SupVo supVo = new SupVo();
                String supCode = l_supCode.get(i);
                supVo.setSupCode(supCode);
                supVo.setSupName(m_supName.get(supCode));
                List<TimeVo> l_timeVo = new ArrayList<TimeVo>();

                l_time = fillTimeVo(l_time, l_timeVo, l_activationUser, l_activeUser, l_downloadUser, l_registerUser, l_stayingUser, timeType, supCode, _year, _sDate, _eDate);

                supVo.setL_timeVo(l_timeVo);

                l_supVo.add(supVo);
            }
        } catch (Exception e) {
            LOGGER.error("综合统计错误", e);
        }

        result.put("supVoList", l_supVo);
        result.put("timeStringList", l_time);

        return result;
    }

    /**
     * 处理留存用户数据，让数据格式和正常数据库查询出来的数据保持一致
     *
     * @param m_stayingUser 计算出来的留存用户数据
     * @return
     */
    public List<Map<String, Object>> processStayingUserData(Map<String, Map<String, Integer>> m_stayingUser) {
        List<Map<String, Object>> l_stayingUser = new ArrayList<Map<String, Object>>();
        // 循环供应商
        for (Iterator<Map.Entry<String, Map<String, Integer>>> iterator = m_stayingUser.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, Map<String, Integer>> entry = iterator.next();
            Map<String, Integer> value = entry.getValue();
            String supCode = entry.getKey();
            // 循环日期
            for (Iterator<Map.Entry<String, Integer>> iterator1 = value.entrySet().iterator(); iterator1.hasNext(); ) {
                Map<String, Object> m = new HashMap<String, Object>();
                Map.Entry<String, Integer> entry1 = iterator1.next();
                m.put(supCodeColKey2, supCode);
                m.put(timeColKey, entry1.getKey()); // 时间是键
                m.put(countColKey, entry1.getValue()); // 值是数量

                l_stayingUser.add(m);
            }
        }

        return l_stayingUser;
    }

    @Override
    public List<Map<String, Object>> visibleSupplier(String userId, String sup) {
        if (StringUtils.isEmpty(sup)) {
            sup = "0";
        }
        return userAnalyseDao.visibleSupplier(userId, sup);
    }

    /**
     * 填充时间值对象
     *
     * @param l_time           时间区间集合
     * @param l_timeVo         需要填充时间集合
     * @param l_activationUser 激活用户数集合
     * @param l_activeUser     活跃用户数集合
     * @param l_downloadUser   下载用户数集合
     * @param l_registerUser   注册用户数集合
     * @param l_stayingUser    留存用户数集合
     * @param timeType         维度，2：周，3：月
     * @param supCode          供应商代码
     * @param _year
     * @param _sDate
     * @param _eDate
     */
    private List<String> fillTimeVo(List<String> l_time, List<TimeVo> l_timeVo, List<Map<String, Object>> l_activationUser, List<Map<String, Object>> l_activeUser, List<Map<String, Object>> l_downloadUser, List<Map<String, Object>> l_registerUser, List<Map<String, Object>> l_stayingUser, String timeType, String supCode, int _year, int _sDate, int _eDate) {
        Long defaultCount = 0l;
        // 标识某日期是否有数据
        Boolean flag = false;
        if ("3".equals(timeType)) {
            // 主要是效率上，已经被赋值的，可以不用再次循环，减少循环次数，预留
            //List<Map<String,Object>> tempActivationUser = l_activationUser;
            for (int i = 0; i <= l_time.size() - 1; ++i) {
                TimeVo timeVo = new TimeVo();
                String time = l_time.get(i);

                // 设置显示的时间
                timeVo.setTimeString(time);
                defaultCount = _processUserCountByTimeAndSupCode(l_activationUser, null, supCode, time);
                timeVo.setActivationNum(defaultCount);
                defaultCount = _processUserCountByTimeAndSupCode(l_activeUser, null, supCode, time);
                timeVo.setActiveNum(defaultCount);
                defaultCount = _processUserCountByTimeAndSupCode(l_downloadUser, null, supCode, time);
                timeVo.setDownloadNum(defaultCount);
                defaultCount = _processUserCountByTimeAndSupCode(l_registerUser, null, supCode, time);
                timeVo.setRegisterNum(defaultCount);
                defaultCount = _processUserCountByTimeAndSupCode(l_stayingUser, null, supCode, time);
                timeVo.setStayingNum(defaultCount);
                l_timeVo.add(timeVo);
            }
        } else {
            // 周实现
            List<String> _tempTimeList = new ArrayList<String>();
            Map<Integer, List<String>> days = new LinkedHashMap<Integer, List<String>>();

            for (int i = _sDate; i <= _eDate; ++i) {
                Date[] dates = ApDateTime.dayOfWeekOfYear(_year, i);
                days.put(i, ApDateTime.getDayBetween(dates[0], dates[1]));
            }

            for (Iterator<Map.Entry<Integer, List<String>>> i = days.entrySet().iterator(); i.hasNext(); ) {
                TimeVo timeVo = new TimeVo();
                Map.Entry<Integer, List<String>> entry = i.next();
                StringBuilder timeString = new StringBuilder("第" + entry.getKey() + "周");
                List<String> _timeList = entry.getValue();
                timeString.append("(").append(_timeList.get(0)).append("至").append(_timeList.get(_timeList.size() - 1)).append(")");
                // 设置显示的时间
                timeVo.setTimeString(timeString.toString());
                _tempTimeList.add(timeString.toString());

                Long downloadSum = 0l;
                Long activationSum = 0l;
                Long registerSum = 0l;
                Long activeSum = 0l;
                Long stayingSum = 0l;
                // 一周
                for (int j = 0; j < _timeList.size(); ++j) {
                    String time = _timeList.get(j);
                    // 处理激活用户数据
                    activationSum = _processUserCountByTimeAndSupCode(l_activationUser, activationSum, supCode, time);
                    // 处理活跃用户数据
                    activeSum = _processUserCountByTimeAndSupCode(l_activeUser, activeSum, supCode, time);
                    // 处理下载用户数据
                    downloadSum = _processUserCountByTimeAndSupCode(l_downloadUser, downloadSum, supCode, time);
                    // 处理注册用户数据
                    registerSum = _processUserCountByTimeAndSupCode(l_registerUser, registerSum, supCode, time);
                }
                stayingSum = _processUserCountByTimeAndSupCode(l_stayingUser, null, supCode, entry.getKey().toString());

                timeVo.setActivationNum(activationSum);
                timeVo.setStayingNum(stayingSum);
                timeVo.setRegisterNum(registerSum);
                timeVo.setActiveNum(activeSum);
                timeVo.setDownloadNum(downloadSum);
                l_timeVo.add(timeVo);
            }

            l_time = _tempTimeList;
        }

        return l_time;
    }

    public Long _processUserCountByTimeAndSupCode(List<Map<String, Object>> mapList,
                                                  Long initCount, String supCode, String time) {
        Long defaultCount = 0l;
        for (int k = 0; k <= mapList.size() - 1; ++k) {
            // 集合内，和这个时间相等的，供应商相等的，数量
            Map<String, Object> map = mapList.get(k);
            Object _supCode = map.get(supCodeColKey2);
            Object _time = map.get(timeColKey);
            Object count = map.get(countColKey);

            if (_supCode.toString().equalsIgnoreCase(supCode) && _time.toString().equalsIgnoreCase(time)) {
                if (initCount == null) {
                    initCount = (count == null ? defaultCount : Long.valueOf(count.toString()));
                } else {
                    initCount += (count == null ? defaultCount : Long.valueOf(count.toString()));
                }
                break;
            }
        }

        if (initCount == null) {
            initCount = defaultCount;
        }

        return initCount;
    }

    @Override
    public List<Map<String, Object>> selectallcomplexStatistics(String userId, String sup, String sDate, String eDate, String timeType, List<String> ids) {
        List<Map<String, Object>> baseDate = new ArrayList<>();
        /******查找留存数据1：日留存2：周7天 3 ：周七天内  时间： 2016-5-20 START******/
        List<Map<String, Object>> carryOver = ekeyKeyUsingService.stayUserList(userId, sup, sDate, eDate, ids);

        Calendar calendar = Calendar.getInstance();
        //默认所有供应商
        String defaultSup = "0";
        // 可见供应商集合
        List<Map<String, Object>> suppliers;
        //下载、激活、注册、使用、留存
        List<String> l_supCode = new ArrayList<String>();
        Map<String, String> m_supName = new HashMap<String, String>();
        List<List<Object>> All = new ArrayList<List<Object>>();
        // 时间区间
        List<String> l_time = new ArrayList<String>();
        List<SupVo> l_supVo = new ArrayList<SupVo>();
        // 使用次数集合
        List<Map<String, Object>> l_activeUser;
        // 激活用户集合
        List<Map<String, Object>> l_activationUser;
        // 下载用户集合
        List<Map<String, Object>> l_downloadUser;
        // 注册用户集合
        List<Map<String, Object>> l_registerUser;
        //使用按键数
        List<Map<String, Object>> useKeys;
        //7天使用按键数
        List<Map<String, Object>> useSevenKeys;
        //30天使用按键数
        List<Map<String, Object>> useThirtyKeys;
        //使用用户数
        List<Map<String, Object>> timesOfUsingUser;
        //使用7用户数
        List<Map<String, Object>> timesOfSevenUsingUsers = new ArrayList<>();
        //使用数和任意按键数的合计
        List<Map<String, Object>> useAndKeysAll = new ArrayList<>();
        //使用30用户数
        List<Map<String, Object>> timesOfThirtyUsingUsers = new ArrayList<>();
        //使用7用户数
        List<Map<String, Object>> timesOfSevenUsingUser = new ArrayList<>();
        //使用30用户数
        List<Map<String, Object>> timesOfThirtyUsingUser = new ArrayList<>();
        //定义免费通话人数
        List<Map<String, Object>> freeCallUserCount = new ArrayList<>();
        List<TimeVo> l_timeVo = new ArrayList<TimeVo>();
        String timePattern = ApDateTime.DATE_TIME_YMD;
        if (StringUtils.isEmpty(sup)) {
            sup = defaultSup;
        }
        try {
            //查询所有供应商
            suppliers = userAnalyseDao.visibleSupplier(userId, sup);

            if (suppliers != null && suppliers.size() >= 1) {
                for (int i = 0; i <= suppliers.size() - 1; ++i) {
                    Map<String, Object> m_sup = suppliers.get(i);
                    String supCode = m_sup.get(supCodeColKey).toString();

                    l_supCode.add(supCode);
                    // 供应商中文名称与代码对应
                    m_supName.put(supCode, m_sup.get(supNameColKey).toString());
                }
            } else {
                // 没有供应商，查询没有意义，这边不考虑有权限查询全部
                return null;
            }

            // 激活用户数
            l_activationUser = userAnalyseDao.activationUserData(l_supCode, sDate, eDate, timeType);
            // 下载用户数
            l_downloadUser = userAnalyseDao.downloadUserData(l_supCode, sDate, eDate, timeType);
            // 注册用户数
            l_registerUser = userAnalyseDao.registerUserData(l_supCode, sDate, eDate, timeType);
            //使用数
            timesOfUsingUser = ekeyKeyUsingDao.countOfAllUsingUser(timePattern, l_supCode, sDate, eDate);
            //按键数
            useKeys = ekeyKeyUsingDao.countOfAllUseKeys(timePattern, l_supCode, sDate, eDate);
            //免费通话人数总数
            freeCallUserCount = ekeyKeyUsingDao.freeCallUserCount(l_supCode, sDate, eDate);
            //合计
            useAndKeysAll = ekeyKeyUsingDao.countOfuseAndKeysAll(timePattern, l_supCode, sDate, eDate);
            l_time = ApDateTime.getDayBetween(sDate, eDate);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            //时间区间集合
            //日期一样的供应商相加
            int AllTotal;
            String supCode = "";
            for (int i = 0; i < l_supCode.size(); ++i) {
                supCode = l_supCode.get(i);
            }

            Long countdownloadUser = 0l;
            Long countactivationUser = 0l;
            Long countregisterUser = 0l;
            Long countUse = 0l;
            Long countUseKeys = 0l;
            Long countUseAndKeysAll = 0l;
            //合计下载数，激活数，激活率，注册数，注册率
            for (int j = 0; j < carryOver.size(); j++) {
                Map baseMap = carryOver.get(j);
                for (int i = 0; i < l_time.size(); ++i) {
                    if (j == i) {
                        String time = l_time.get(i);
                        // 报表数据展示数据传输对象集合
                        //List<Object> complex = new ArrayList<Object>();
                        // 设置时间
                        baseMap.put("time", time);
                        //下载数
                        countdownloadUser = _processUserCountByTimeAndAll(l_downloadUser, 0l, supCode, time, "1");
                        baseMap.put("countdownloadUser", countdownloadUser);
                        //激活数
                        countactivationUser = _processUserCountByTimeAndAll(l_activationUser, 0l, supCode, time, "1");
                        baseMap.put("countactivationUser", countactivationUser);
                        //激活率
                        baseMap.put("countactivationUserRate", getRate(Integer.parseInt(countactivationUser + ""), Integer.parseInt(countdownloadUser + "")));
                        //注册数
                        countregisterUser = _processUserCountByTimeAndAll(l_registerUser, 0l, supCode, time, "1");
                        baseMap.put("countregisterUser", countregisterUser);
                        //注册率
                        baseMap.put("countregisterUserRate", getRate(Integer.parseInt(countregisterUser + ""), Integer.parseInt(countactivationUser + "")));
                        //使用数
                        countUse = _processUserCountByTimeAndAll(timesOfUsingUser, 0l, supCode, time, "1");
                        baseMap.put("countUse", countUse);
                        //按键数
                        countUseKeys = _processUserCountByTimeAndAll(useKeys, 0l, supCode, time, "1");
                        baseMap.put("countUseKeys", countUseKeys);
                        //按键数和使用数的合计
                        countUseAndKeysAll = _processUserCountByTimeAndAll(useAndKeysAll, 0l, supCode, time, "1");
                        baseMap.put("countUseAndKeysAll", countUseAndKeysAll);
                        //免费通话人数
                        if(StrUtils.isEmpty(freeCallUserCount)){
                            baseMap.put("freeCallUserCount", 0);
                        }else{
                            for (int t = 0; t < freeCallUserCount.size(); t++) {
                                Map map = freeCallUserCount.get(t);
                                String day = StrUtils.emptyOrString(map.get("DAY"));
                                String count = StrUtils.emptyOrString(map.get("TOTAL"));
                                if (day.equals(time)) {
                                    baseMap.put("freeCallUserCount", count);
                                }else if(StrUtils.isEmpty(day) && StrUtils.isEmpty(count)){
                                    baseMap.put("freeCallUserCount", 0);
                                }
                            }
                        }
                        baseDate.add(baseMap);
                    }
                }

            }
        } catch (Exception e) {
            LOGGER.error("综合统计错误", e);
        }

        return baseDate;
    }

    /**
     * 查询 综合统计分析数据
     *
     * @param userId
     * @param sup
     * @param sDate
     * @param eDate
     * @param brand
     * @return
     */
    @Override
    public List<Map<String, Object>> selectAnalyseDate(String userId, String sup, String sDate, String eDate, String brand) {
        List<Map<String, Object>> suppliers;// 供应商
        List<String> _sup;
        if ("0".equals(brand)) {
            if ("0".equals(sup)) {
                suppliers = userAnalyseDao.visibleSupplier(userId, sup);// 可见的供应商
                _sup = getSupCodeList(suppliers);
            } else {
                _sup = new ArrayList<>();
                _sup.add(sup);
            }
        } else {
            if ("0".equals(sup)) {
                suppliers = userAnalyseDao.getSuppliersByBrand(brand, userId);
                _sup = getSupCodeList(suppliers);
            } else {
                _sup = new ArrayList<>();
                _sup.add(sup);
            }
        }

        List<Map<String,Object>>  selectAnalyseDateList=new ArrayList<>();
        Map<String,Object> baseMap;
        String sDay = "";
        String eDay = "";
        if("0".equals(sup)){
            /**日留存---》本日启动：前天注册   START*/
            if (StrUtils.isNotEmpty(sDate)) {// 默认起始时间为今天的1天前
                sDay = ApDateTime.dateAdd("d", -1, sDate, ApDateTime.DATE_TIME_YMD);
            }
            if (StrUtils.isNotEmpty(eDate)) {// 默认截止日期为今天
                eDay = ApDateTime.dateAdd("d", -1, eDate, ApDateTime.DATE_TIME_YMD);
            }
            List<Map<String, Object>> dayActivityUserCount = ekeyKeyUsingDao.activityUserCount(_sup, sDay, eDay);//激活数量
            List<Map<String, Object>> dayRegisterCount = ekeyKeyUsingDao.registerCount(_sup, sDay, eDay);//注册数量
            List<Map<String,Object>>  dayFreeCallUserCount = ekeyKeyUsingDao.freeCallUserCount(_sup,sDay, eDay);//前天免费通话总人数
            /**周留存和周内留存---》本日启动：6天前注册   START*/
            if (StrUtils.isNotEmpty(sDate)) {// 默认起始时间为今天的1天前
                sDay = ApDateTime.dateAdd("d", -6, sDate, ApDateTime.DATE_TIME_YMD);
            }
            if (StrUtils.isNotEmpty(eDate)) {// 默认截止日期为今天
                eDay = ApDateTime.dateAdd("d", -6, eDate, ApDateTime.DATE_TIME_YMD);
            }
            List<Map<String, Object>> weekActivityUserCount = ekeyKeyUsingDao.activityUserCount(_sup, sDay, eDay);//激活数量
            List<Map<String, Object>> weekRegisterCount = ekeyKeyUsingDao.registerCount(_sup, sDay, eDay);//注册数量
            List<Map<String, Object>> weekFreeCallUserCount =  ekeyKeyUsingDao.freeCallUserCount(_sup,sDay, eDay);//六天前总人数
            /**月留存---》本日启动：29天前注册   START*/
            if (StrUtils.isNotEmpty(sDate)) {// 默认起始时间为今天的1天前
                sDay = ApDateTime.dateAdd("d", -29, sDate, ApDateTime.DATE_TIME_YMD);
            }
            if (StrUtils.isNotEmpty(eDate)) {// 默认截止日期为今天
                eDay = ApDateTime.dateAdd("d", -29, eDate, ApDateTime.DATE_TIME_YMD);
            }
            List<Map<String, Object>> monthActivityUserCount = ekeyKeyUsingDao.activityUserCount(_sup, sDay, eDay);//激活数量
            List<Map<String, Object>> monthRegisterCount = ekeyKeyUsingDao.registerCount(_sup, sDay, eDay);//注册数量
            List<Map<String, Object>> monthFreeCallUserCount =  ekeyKeyUsingDao.freeCallUserCount(_sup,sDay, eDay);//29天前总人数
            //全部供应商
            List<Map<String,Object>> selectAllAnalyseDateList = userAnalyseDao.selectAllAnalyseDateList(sDate,eDate,_sup);
            /*************启动或使用注册数/注册总数/激活总数 2016-06-14***************/
            List<Map<String,Object>> use_list=userAnalyseDao.selectUserList(_sup,sDate,eDate);//使用或启动 用户列表
            List<Map<String,Object>> reg_list=userAnalyseDao.selectRegList(_sup);//注册用户列表
            List<Map<String,Object>> reg_act_count=userAnalyseDao.selectReg_ActCount(_sup);//注册和激活用户总数
            /** 获取时间集合*/
            List<String> dayBetween = ApDateTime.getDayBetween(sDate, eDate);
            for (String day_ : dayBetween) { //日期循环
                for (int i = 0; i < selectAllAnalyseDateList.size(); i++) {
                    Map map = selectAllAnalyseDateList.get(i);
                    String day =StrUtils.emptyOrString(map.get("C_DATE"));
                    if(day.equals(day_)){
                        baseMap = getDate(map, dayActivityUserCount, dayRegisterCount, dayFreeCallUserCount,
                                weekActivityUserCount, weekRegisterCount, weekFreeCallUserCount, monthActivityUserCount,
                                monthRegisterCount, monthFreeCallUserCount,use_list,reg_list,reg_act_count);
                        selectAnalyseDateList.add(baseMap);
                    }
                }
            }
        }else{
          //单个供应商
            selectAnalyseDateList = userAnalyseDao.selectAnalyseDateList(sDate,eDate,_sup);
        }
        return selectAnalyseDateList;
    }

    public int getUseCount(){

        return 0;
    }

    public Map<String,Object> getDate(Map map, List<Map<String, Object>> dayActivityUserCount, List<Map<String, Object>> dayRegisterCount, List<Map<String, Object>> dayFreeCallUserCount,
                                      List<Map<String, Object>> weekActivityUserCount, List<Map<String, Object>> weekRegisterCount, List<Map<String, Object>> weekFreeCallUserCount,
                                      List<Map<String, Object>> monthActivityUserCount, List<Map<String, Object>> monthRegisterCount, List<Map<String, Object>> monthFreeCallUserCount,
                                      List<Map<String, Object>> use_list,List<Map<String, Object>> reg_list,List<Map<String, Object>> act_count_list
                                      ){
        String day = StrUtils.emptyOrString(map.get("C_DATE"));
        /*****************************************/
        int reg_count = 0;//使用总数中注册数
        int reg_all_count =0;//注册总人数
        int act_count =0;//激活总数
        if(StrUtils.isNotEmpty(use_list) && StrUtils.isNotEmpty(reg_list)){
            reg_all_count=reg_list.size();
            for(Map act_map : act_count_list){
                act_count =Integer.parseInt(act_map.get("ACT_COUNT")+"");
            }
            for(Map user_map : use_list){
              String user_day = StrUtils.emptyOrString(user_map.get("DAY"));
              String C_IMEI = StrUtils.emptyOrString(user_map.get("C_IMEI"));
                if(day.equals(user_day)){
                   for(Map reg_map : reg_list){
                       String _C_IMEI = StrUtils.emptyOrString(reg_map.get("C_IMEI"));
                       if(C_IMEI.equals(_C_IMEI)){
                           reg_count+=1;
                       }
                   }
                }
            }
        }else{
            reg_count=0;
        }
        int use_count = Integer.parseInt(map.get("C_USE")+"");//使用总数
        int n_reg_count =use_count -reg_count;//非注册

        map.put("C_REG_COUNT",reg_count);//注册使用总人数
        String reg_use_rate =getRate(reg_count,reg_all_count);//注册使用率
        map.put("C_REG_COUNT_RATE",reg_use_rate);//注册使用率

        map.put("C_N_REG_COUNT",n_reg_count);//非注册使用总人数
        String reg_n_use_rate =getRate(n_reg_count,act_count-reg_all_count);//
        map.put("C_N_REG_COUNT_RATE",reg_n_use_rate);//非注册使用率
        /*****************************************/
        String C_DOWNLOAD =StrUtils.emptyOrString(map.get("C_DOWNLOAD"));//下载人数
        String C_ACTIVE =StrUtils.emptyOrString(map.get("C_ACTIVE"));//激活人数
        String C_REGISTER =StrUtils.emptyOrString(map.get("C_REGISTER"));//注册人数
        String C_REG_D =StrUtils.emptyOrString(map.get("C_REG_D"));//注册用户-日留存人数
        String C_REG_W_END =StrUtils.emptyOrString(map.get("C_REG_W_END"));//注册用户-第7日留存人数
        String C_REG_W_ALL =StrUtils.emptyOrString(map.get("C_REG_W_ALL"));//注册用户-7日内留存人数
        String C_REG_M_END =StrUtils.emptyOrString(map.get("C_REG_M_END"));//注册用户-第30日留存人数
        String C_REG_M_ALL =StrUtils.emptyOrString(map.get("C_REG_M_ALL"));//注册用户-30日内留存人数
        String C_ACT_D =StrUtils.emptyOrString(map.get("C_ACT_D"));
        String C_ACT_W_END =StrUtils.emptyOrString(map.get("C_ACT_W_END"));
        String C_ACT_W_ALL =StrUtils.emptyOrString(map.get("C_ACT_W_ALL"));
        String C_ACT_M_END =StrUtils.emptyOrString(map.get("C_ACT_M_END"));
        String C_ACT_M_ALL =StrUtils.emptyOrString(map.get("C_ACT_M_ALL"));
        String C_FY_D =StrUtils.emptyOrString(map.get("C_FY_D"));
        String C_FY_W_END =StrUtils.emptyOrString(map.get("C_FY_W_END"));
        String C_FY_W_ALL =StrUtils.emptyOrString(map.get("C_FY_W_ALL"));
        String C_FY_M_END =StrUtils.emptyOrString(map.get("C_FY_M_END"));
        String C_FY_M_ALL =StrUtils.emptyOrString(map.get("C_FY_M_ALL"));


        String C_ACTIVE_RATE =getRate(Integer.parseInt(C_ACTIVE),Integer.parseInt(C_DOWNLOAD));
        map.put("C_ACTIVE_RATE",C_ACTIVE_RATE);//激活率

        String C_REGISTER_RATE =getRate(Integer.parseInt(C_REGISTER),Integer.parseInt(C_ACTIVE));
        map.put("C_REGISTER_RATE",C_REGISTER_RATE);//注册率

        int _C_REG_D_RATE= getToatle(map,dayRegisterCount,day,1);
        String C_REG_D_RATE =  getRate(Integer.parseInt(C_REG_D),_C_REG_D_RATE);
        map.put("C_REG_D_RATE",C_REG_D_RATE);//注册 日留存率
        int _C_REG_W_END= getToatle(map,weekRegisterCount,day,6);
        String C_REG_W_END_RATE =  getRate(Integer.parseInt(C_REG_W_END),_C_REG_W_END);
        map.put("C_REG_W_END_RATE",C_REG_W_END_RATE);//注册 周留存率
        int _C_REG_W_ALL= getToatle(map,weekRegisterCount,day,6);
        String C_REG_W_ALL_RATE =  getRate(Integer.parseInt(C_REG_W_ALL),_C_REG_W_ALL);
        map.put("C_REG_W_ALL_RATE",C_REG_W_ALL_RATE);//注册 周内留存率
        int _C_REG_M_END= getToatle(map,monthRegisterCount,day,29);
        String C_REG_M_END_RATE =  getRate(Integer.parseInt(C_REG_M_END),_C_REG_M_END);
        map.put("C_REG_M_END_RATE",C_REG_M_END_RATE);//注册 月留存率
        int _C_REG_M_ALL= getToatle(map,monthRegisterCount,day,29);
        String C_REG_M_ALL_RATE =  getRate(Integer.parseInt(C_REG_M_ALL),_C_REG_M_ALL);
        map.put("C_REG_M_ALL_RATE",C_REG_M_ALL_RATE);//注册 日留存率


        int _C_ACT_D= getToatle(map,dayActivityUserCount,day,1);
        String C_ACT_D_RATE =  getRate(Integer.parseInt(C_ACT_D),_C_ACT_D);
        map.put("C_ACT_D_RATE",C_ACT_D_RATE);//激活 日留存率
        int _C_ACT_W_END= getToatle(map,weekActivityUserCount,day,6);
        String C_ACT_W_END_RATE =  getRate(Integer.parseInt(C_ACT_W_END),_C_ACT_W_END);
        map.put("C_ACT_W_END_RATE",C_ACT_W_END_RATE);//激活 周留存率
        int _C_ACT_W_ALL= getToatle(map,weekActivityUserCount,day,6);
        String C_ACT_W_ALL_RATE =  getRate(Integer.parseInt(C_ACT_W_ALL),_C_ACT_W_ALL);
        map.put("C_ACT_W_ALL_RATE",C_ACT_W_ALL_RATE);//激活 周内留存率
        int _C_ACT_M_END= getToatle(map,monthActivityUserCount,day,29);
        String C_ACT_M_END_RATE =  getRate(Integer.parseInt(C_ACT_M_END),_C_ACT_M_END);
        map.put("C_ACT_M_END_RATE",C_ACT_M_END_RATE);//激活 月留存率
        int _C_ACT_M_ALL= getToatle(map,monthActivityUserCount,day,29);
        String C_ACT_M_ALL_RATE =  getRate(Integer.parseInt(C_ACT_M_ALL),_C_ACT_M_ALL);
        map.put("C_ACT_M_ALL_RATE",C_ACT_M_ALL_RATE);//激活 月内留存率


        int _C_FY_D= getToatle(map,dayFreeCallUserCount,day,1);
        String C_FY_D_RATE =  getRate(Integer.parseInt(C_FY_D),_C_FY_D);
        map.put("C_FY_D_RATE",C_FY_D_RATE);//免费通话 日留存率
        int _C_FY_W_END= getToatle(map,weekFreeCallUserCount,day,6);
        String C_FY_W_END_RATE =  getRate(Integer.parseInt(C_FY_W_END),_C_FY_W_END);
        map.put("C_FY_W_END_RATE",C_FY_W_END_RATE);//免费通话 周留存率
        int _C_FY_W_ALL= getToatle(map,weekFreeCallUserCount,day,6);
        String C_FY_W_ALL_RATE =  getRate(Integer.parseInt(C_FY_W_ALL),_C_FY_W_ALL);
        map.put("C_FY_W_ALL_RATE",C_FY_W_ALL_RATE);//免费通话 周内留存率
        int _C_FY_M_END= getToatle(map,monthFreeCallUserCount,day,29);
        String C_FY_M_END_RATE =  getRate(Integer.parseInt(C_FY_M_END),_C_FY_M_END);
        map.put("C_FY_M_END_RATE",C_FY_M_END_RATE);//免费通话 月留存率
        int _C_FY_M_ALL= getToatle(map,monthFreeCallUserCount,day,29);
        String C_FY_M_ALL_RATE =  getRate(Integer.parseInt(C_FY_M_ALL),_C_FY_M_ALL);
        map.put("C_FY_M_ALL_RATE",C_FY_M_ALL_RATE);//免费通话 月内留存率
        return map;
    }


    public int getToatle(Map<String,Object> map ,List<Map<String, Object>> userCountList,String day,int type){

        for(Map m : userCountList){
            String _day = StrUtils.emptyOrString(m.get("DAY"));
            String totle = StrUtils.emptyOrString(m.get("TOTAL"));
            if (ApDateTime.dateAdd("d", -type, day, ApDateTime.DATE_TIME_YMD).equals(_day)) {
                if (StrUtils.isEmpty(totle)) {
                    return 0;
                } else {
                    return Integer.parseInt(totle);
                }
            }
        }
        return 0;
    }

    @Override
    public List<Map<String,Object>> getCompleteData(String sup, String sDate, String eDate, List<String> ids) {
        List<Map<String,Object>> reqList = new ArrayList<>();
        // 查询数据库的原始数据
        List<Map<String, Object>> list = userAnalyseDao.getCompleteData(sup, sDate, eDate, ids);
        // 将数据封装成List<List<Object>>结构
        if (StrUtils.isNotEmpty(list)) {
            for (Map<String, Object> map : list) {
                Map<String,Object> rowMap = new HashMap<>();

                // 基础数据
                rowMap.put("time", map.get("C_DATE"));// 时间
                rowMap.put("countdownloadUser", map.get("C_DOWNLOAD"));// 下载数
                rowMap.put("countactivationUser", map.get("C_ACTIVE"));// 激活数
                rowMap.put("countactivationUserRate", map.get("C_ACTIVE_RATE"));// 激活率
                rowMap.put("countregisterUser", map.get("C_REGISTER"));// 注册数
                rowMap.put("countregisterUserRate", map.get("C_REGISTER_RATE"));// 注册率
                rowMap.put("countUse", map.get("C_START"));// app启动数
                rowMap.put("countUseKeys", map.get("C_USEKEY"));// 按键使用数
                rowMap.put("countUseAndKeysAll", map.get("C_USE"));// 启动数+按键使用数

                // 注册用户-留存数据
                if (StrUtils.isNotEmpty(map.get("C_REG_D"))) {rowMap.put("DayCount", map.get("C_REG_D"));}
                if (StrUtils.isNotEmpty(map.get("C_REG_D_RATE"))) {rowMap.put("dayRate", map.get("C_REG_D_RATE"));}
                if (StrUtils.isNotEmpty(map.get("C_REG_W_END"))) {rowMap.put("WeekCount", map.get("C_REG_W_END"));}
                if (StrUtils.isNotEmpty(map.get("C_REG_W_END_RATE"))) {rowMap.put("WeekRate", map.get("C_REG_W_END_RATE"));}
                if (StrUtils.isNotEmpty(map.get("C_REG_W_ALL"))) {rowMap.put("WeekNCount", map.get("C_REG_W_ALL"));}
                if (StrUtils.isNotEmpty(map.get("C_REG_W_ALL_RATE"))) {rowMap.put("WeekNRate", map.get("C_REG_W_ALL_RATE"));}
                if (StrUtils.isNotEmpty(map.get("C_REG_M_END"))) {rowMap.put("MonthCount", map.get("C_REG_M_END"));}
                if (StrUtils.isNotEmpty(map.get("C_REG_M_END_RATE"))) {rowMap.put("MonthRate", map.get("C_REG_M_END_RATE"));}
                if (StrUtils.isNotEmpty(map.get("C_REG_M_ALL"))) {rowMap.put("MonthNCount", map.get("C_REG_M_ALL"));}
                if (StrUtils.isNotEmpty(map.get("C_REG_M_ALL_RATE"))) {rowMap.put("MonthNRate", map.get("C_REG_M_ALL_RATE"));}

                // 激活用户-留存数据
                if (StrUtils.isNotEmpty(map.get("C_ACT_D"))) {rowMap.put("DayActivityCount", map.get("C_ACT_D"));}
                if (StrUtils.isNotEmpty(map.get("C_ACT_D_RATE"))) {rowMap.put("DayActivityRate", map.get("C_ACT_D_RATE"));}
                if (StrUtils.isNotEmpty(map.get("C_ACT_W_END"))) {rowMap.put("WeekActivityCount", map.get("C_ACT_W_END"));}
                if (StrUtils.isNotEmpty(map.get("C_ACT_W_END_RATE"))) {rowMap.put("WeekActivityRate", map.get("C_ACT_W_END_RATE"));}
                if (StrUtils.isNotEmpty(map.get("C_ACT_W_ALL"))) {rowMap.put("WeekActivityNCount", map.get("C_ACT_W_ALL"));}
                if (StrUtils.isNotEmpty(map.get("C_ACT_W_ALL_RATE"))) {rowMap.put("WeekActivityNRate", map.get("C_ACT_W_ALL_RATE"));}
                if (StrUtils.isNotEmpty(map.get("C_ACT_M_END"))) {rowMap.put("MonthActivityCount", map.get("C_ACT_M_END"));}
                if (StrUtils.isNotEmpty(map.get("C_ACT_M_END_RATE"))) {rowMap.put("MonthActivityRate", map.get("C_ACT_M_END_RATE"));}
                if (StrUtils.isNotEmpty(map.get("C_ACT_M_ALL"))) {rowMap.put("MonthActivityNCount", map.get("C_ACT_M_ALL"));}
                if (StrUtils.isNotEmpty(map.get("C_ACT_M_ALL_RATE"))) {rowMap.put("MonthActivityNRate", map.get("C_ACT_M_ALL_RATE"));}

                // 免费通话-留存数据
                if (StrUtils.isNotEmpty(map.get("C_FY_D"))) {rowMap.put("DayFreeCallCount", map.get("C_FY_D"));}
                if (StrUtils.isNotEmpty(map.get("C_FY_D_RATE"))) {rowMap.put("DayFreeCallRate", map.get("C_FY_D_RATE"));}
                if (StrUtils.isNotEmpty(map.get("C_FY_D_TIME"))) {rowMap.put("DayFreeCallTime", map.get("C_FY_D_TIME"));}
                if (StrUtils.isNotEmpty(map.get("C_FY_W_END"))) {rowMap.put("WeekFreeCallCount", map.get("C_FY_W_END"));}
                if (StrUtils.isNotEmpty(map.get("C_FY_W_END_RATE"))) {rowMap.put("WeekFreeCallRate", map.get("C_FY_W_END_RATE"));}
                if (StrUtils.isNotEmpty(map.get("C_FY_W_END_TIME"))) {rowMap.put("WeekFreeCallTime", map.get("C_FY_W_END_TIME"));}
                if (StrUtils.isNotEmpty(map.get("C_FY_W_ALL"))) {rowMap.put("WeekNFreeCallCount", map.get("C_FY_W_ALL"));}
                if (StrUtils.isNotEmpty(map.get("C_FY_W_ALL_RATE"))) {rowMap.put("WeekNFreeCallRate", map.get("C_FY_W_ALL_RATE"));}
                if (StrUtils.isNotEmpty(map.get("C_FY_W_ALL_TIME"))) {rowMap.put("weekNFreeCallTime", map.get("C_FY_W_ALL_TIME"));}
                if (StrUtils.isNotEmpty(map.get("C_FY_M_END"))) {rowMap.put("MonthFreeCallCount", map.get("C_FY_M_END"));}
                if (StrUtils.isNotEmpty(map.get("C_FY_M_END_RATE"))) {rowMap.put("MonthFreeCallRate", map.get("C_FY_M_END_RATE"));}
                if (StrUtils.isNotEmpty(map.get("C_FY_M_END_TIME"))) {rowMap.put("MonthFreeCalltime", map.get("C_FY_M_END_TIME"));}
                if (StrUtils.isNotEmpty(map.get("C_FY_M_ALL"))) {rowMap.put("MonthNFreeCallCount", map.get("C_FY_M_ALL"));}
                if (StrUtils.isNotEmpty(map.get("C_FY_M_ALL_RATE"))) {rowMap.put("MonthNFreeCallRate", map.get("C_FY_M_ALL_RATE"));}
                if (StrUtils.isNotEmpty(map.get("C_FY_M_ALL_TIME"))) {rowMap.put("MonthNFreeCallTime", map.get("C_FY_M_ALL_TIME"));}

                reqList.add(rowMap);

            }
        }
        return reqList;
    }

    private static String getRate(int count, int total) {
        DecimalFormat df = new DecimalFormat("#.00");
        if (count == 0 || total == 0) {
            return "0.00%";
        } else {
            double result = Double.parseDouble(df.format((double) count * 100 / total));
            return result + "%";
        }
    }

    @Override
    // 查询用户可见的所有品牌
    public List<Map<String, Object>> getBrands(String userId) {
        return userAnalyseDao.getBrands(userId);
    }

    @Override
    // 查询指定品牌的所有渠道
    public List<Map<String, Object>> getSuppliersByBrand(String brand, String userId) {
        return userAnalyseDao.getSuppliersByBrand(brand, userId);
    }

    @Override
    // 查询用户可见的供应商
    public List<Map<String, Object>> getSuppliers(String userId) {
        return userAnalyseDao.visibleSupplier(userId, "0");
    }

    @Override
    // 定时查询每日的用户数据，存入T_EK_ANALYSE_DATA表
    public void timedTask() {
//        String s = "2014-11-24";
//        String e = "2016-06-10";
//        List<String> dayBetween = ApDateTime.getDayBetween(s, e);
//        for (int j = 0; j < dayBetween.size(); j ++) {}

        try {
            String userId = "U001";// 默认查询admin的所有数据（原则上admin有查看所有供应商的权限）
            String sDate = null;
            String eDate = null;
            List<String> idList = Arrays.asList(new String[]{"1", "2", "3", "4", "5", "6", "7", "8"});
            long currentTimeMillis = System.currentTimeMillis();
            try {
                // 获取当前时间的前一天
                sDate = ApDateTime.getDateTime(ApDateTime.DATE_TIME_YMD, currentTimeMillis - (1000 * 60 * 60 * 24));
//                sDate = dayBetween.get(j);
                // 结束时间当前时间的前一天
                eDate = sDate;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            List<Object[]> args = new ArrayList<>();
            // 获取所有供应商
            List<Map<String, Object>> suppliers = userAnalyseDao.getAllSuppliers();

            if (StrUtils.isNotEmpty(suppliers)) {
                for (int i = 0; i < suppliers.size(); i++) {
                    String supCode = suppliers.get(i).get("CODE").toString();
                    List<Map<String, Object>> list = selectallcomplexStatistics(userId, supCode, sDate, eDate, "1", idList);
                    if (StrUtils.isNotEmpty(list)) {
                        Map<String, Object> map = list.get(0);
                        List<Object> rowList = new ArrayList<>();
                        rowList.add(supCode);// 供应商
                        // 基础数据
                        rowList.add(map.get("time"));// 时间

                        rowList.add(map.get("countdownloadUser"));// 下载数
                        rowList.add(map.get("countactivationUser"));// 激活数
                        rowList.add(map.get("countactivationUserRate"));// 激活率
                        rowList.add(map.get("countregisterUser"));// 注册数
                        rowList.add(map.get("countregisterUserRate"));// 注册率
                        rowList.add(map.get("countUse"));// app启动数
                        rowList.add(map.get("countUseKeys"));// 按键使用数
                        rowList.add(map.get("countUseAndKeysAll"));// 启动数+按键使用数
                        rowList.add(map.get("freeCallUserCount"));// 免费通话使用人数
                        // 注册用户-留存数据
                        rowList.add(map.get("DayCount"));
                        rowList.add(map.get("dayRate"));
                        rowList.add(map.get("WeekCount"));
                        rowList.add(map.get("WeekRate"));
                        rowList.add(map.get("WeekNCount"));
                        rowList.add(map.get("WeekNRate"));
                        rowList.add(map.get("MonthCount"));
                        rowList.add(map.get("MonthRate"));
                        rowList.add(map.get("MonthNCount"));
                        rowList.add(map.get("MonthNRate"));
                        // 激活用户-留存数据
                        rowList.add(map.get("DayActivityCount"));
                        rowList.add(map.get("DayActivityRate"));
                        rowList.add(map.get("WeekActivityCount"));
                        rowList.add(map.get("WeekActivityRate"));
                        rowList.add(map.get("WeekActivityNCount"));
                        rowList.add(map.get("WeekActivityNRate"));
                        rowList.add(map.get("MonthActivityCount"));
                        rowList.add(map.get("MonthActivityRate"));
                        rowList.add(map.get("MonthActivityNCount"));
                        rowList.add(map.get("MonthActivityNRate"));
                        // 免费通话-留存数据
                        rowList.add(map.get("DayFreeCallCount"));
                        rowList.add(map.get("DayFreeCallRate"));
                        rowList.add(map.get("DayFreeCallTime"));
                        rowList.add(map.get("WeekFreeCallCount"));
                        rowList.add(map.get("WeekFreeCallRate"));
                        rowList.add(map.get("WeekFreeCallTime"));
                        rowList.add(map.get("WeekNFreeCallCount"));
                        rowList.add(map.get("WeekNFreeCallRate"));
                        rowList.add(map.get("weekNFreeCallTime"));
                        rowList.add(map.get("MonthFreeCallCount"));
                        rowList.add(map.get("MonthFreeCallRate"));
                        rowList.add(map.get("MonthFreeCalltime"));
                        rowList.add(map.get("MonthNFreeCallCount"));
                        rowList.add(map.get("MonthNFreeCallRate"));
                        rowList.add(map.get("MonthNFreeCallTime"));

                        args.add(rowList.toArray());
                    }
                }
            }
            userAnalyseDao.saveDataOfYesterday(args);
        } catch (Exception e2) {
            LOGGER.error("UserAnalyseServiceImpl.timedTask failed ! e :", e2);
        }

    }

}
