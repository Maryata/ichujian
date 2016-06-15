package com.org.mokey.analyse.service.impl;

import com.org.mokey.analyse.dao.EkeyUserAnalyseDao;
import com.org.mokey.analyse.dao.UserAnalyseDao;
import com.org.mokey.analyse.service.EkeyUserAnalyseService;
import com.org.mokey.analyse.service.UserAnalyseService;
import com.org.mokey.analyse.vo.EkeyComplexBean;
import com.org.mokey.analyse.vo.SupVo;
import com.org.mokey.analyse.vo.TimeVo;
import com.org.mokey.common.util.ApDateTime;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by Maryn on 2016/4/5.
 */
@Service
public class EkeyUserAnalyseServiceImpl implements EkeyUserAnalyseService {
    private static final Log LOGGER = LogFactory.getLog(EkeyUserAnalyseServiceImpl.class);
    @Autowired
    private UserAnalyseService userAnalyseService;

    @Autowired
    private UserAnalyseDao userAnalyseDao;

    // 数据库供应商代码字段名
    private final String supCodeColKey = "C_SUPPLIER_CODE";

    @Override
    public Map<String, Object> userAnalyse(String userId, String sup, String sDate, String eDate, String timeType,String year) throws Exception {
        // 方法返回结果
        Map<String, Object> result = new HashMap<String, Object>();
        Calendar calendar = Calendar.getInstance();
        // 当前日期 用于默认查询
        Date currentDate = new Date();
        int _year;
        int _sDate = 1;
        int _eDate = 2;

        //默认所有供应商
        String defaultSup = "0";
        // 可见供应商集合
        List<Map<String, Object>> suppliers;
        // 时间区间
        List<String> l_time = new ArrayList<String>();
        // 可见的供应商代码集合
        List<String> l_supCode = new ArrayList<String>();
        // 报表数据展示数据传输对象集合
        List<EkeyComplexBean> l_eKey = new ArrayList<EkeyComplexBean>();
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
            // 只在周统计用到
            _year = Integer.parseInt(year);
        }

        // 月统计初次查询 开始时间
        if (StringUtils.isEmpty(sDate) && isMonth(timeType)) {
            // 过去6个月
            sDate = ApDateTime.dateAdd("m",-6,currentDate,ApDateTime.DATE_TIME_YM);
        } else if(StringUtils.isEmpty(sDate) && isWeek(timeType)) { // 周统计初次查询
            // 默认第一周
            _sDate = 1;
        } else if(StringUtils.isEmpty(sDate) && isDay(timeType)) { // 日统计初次查询
            // 默认查询30天数据
            sDate = ApDateTime.dateAdd("d", -30, currentDate,ApDateTime.DATE_TIME_YMD);
        }

        // 月统计初次查询 结束时间
        if (StringUtils.isEmpty(eDate) && isMonth(timeType)) {
            // 当前月
            eDate = ApDateTime.getDateTime(ApDateTime.DATE_TIME_YM,currentDate.getTime());
        } else if(StringUtils.isEmpty(eDate) && isWeek(timeType)) { // 周统计初次查询
            // 当前周
            _eDate = calendar.get(Calendar.WEEK_OF_YEAR);
        } else if(StringUtils.isEmpty(eDate) && isDay(timeType)) { // 日统计初次查询
            // 当前日期
            eDate = ApDateTime.getDateTime(ApDateTime.DATE_TIME_YMD,currentDate.getTime());
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
                }
            } else {
                // 没有供应商，查询没有意义，这边不考虑有权限查询全部
                return null;
            }

            // 月间隔集合
            if (isMonth(timeType)) {
                l_time = ApDateTime.getMonthBetween(sDate, eDate);
            } else if(isWeek(timeType)) {
                Date[] sWeekArr = ApDateTime.dayOfWeekOfYear(_year, _sDate);
                Date[] eWeekArr = ApDateTime.dayOfWeekOfYear(_year, _eDate);

                sDate = ApDateTime.getDateTime(ApDateTime.DATE_TIME_YMD, sWeekArr[0].getTime());
                eDate = ApDateTime.getDateTime(ApDateTime.DATE_TIME_YMD, eWeekArr[1].getTime());
            } else {
                //查询日统计
                l_time = ApDateTime.getDayBetween(sDate,eDate);
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
            if (isMonth(timeType)) {
                // 月留存
                m_stayingUser = userAnalyseService.stayingUserData(suppliers, sDate, eDate, timeType);
                l_stayingUser = userAnalyseService.processStayingUserData(m_stayingUser);
            } else if(isWeek(timeType)) {
                // 周留存
                sDate = ApDateTime.dateAdd("d", -7, sDate, ApDateTime.DATE_TIME_YMD);
                List<Map<String, Object>> nowData = userAnalyseDao.allStayingData(l_supCode, sDate, eDate);
                Map<String, Map<String, List<String>>> nowImeiMap = userAnalyseService.getEmeiDayMap(l_supCode, nowData);
                Map<String, List<String>> weekMap = new LinkedHashMap<String, List<String>>();
                String _0 = ApDateTime.dateAdd("d", -1, sDate, ApDateTime.DATE_TIME_YMD);
                List<String> _l0 = ApDateTime.getDayBetween(sDate, _0);
                weekMap.put("0", _l0);
                for (int i = _sDate; i <= _eDate; ++i) {
                    Date[] weekArr = ApDateTime.dayOfWeekOfYear(_year, i);

                    String _s = ApDateTime.getDateTime(ApDateTime.DATE_TIME_YMD, weekArr[0].getTime());
                    String _e = ApDateTime.getDateTime(ApDateTime.DATE_TIME_YMD, weekArr[1].getTime());
                    weekMap.put(String.valueOf(i), ApDateTime.getDayBetween(_s, _e));
                }

                Map<String, List<Integer>> nowDataMap = userAnalyseService.countListOfSup(suppliers, weekMap, nowImeiMap);
                m_stayingUser = new LinkedHashMap<String, Map<String, Integer>>();
                for (Iterator<Map.Entry<String, List<Integer>>> iterator = nowDataMap.entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<String, List<Integer>> entry = iterator.next();
                    Map<String, Integer> _m = new HashMap<String, Integer>();
                    for (int w = _sDate, index = 0; w <= _eDate; ++w, ++index) {
                        _m.put(String.valueOf(w), entry.getValue().get(index));
                    }
                    m_stayingUser.put(entry.getKey(), _m);
                }

                l_stayingUser = userAnalyseService.processStayingUserData(m_stayingUser);
            } else { //if(isDay(timeType))
                //  月处理和日留存处理一致
                m_stayingUser = userAnalyseService.stayingUserData(suppliers, sDate, eDate, timeType);
                l_stayingUser = userAnalyseService.processStayingUserData(m_stayingUser);
                // 日留存
            }

            // 时间对应的统计bean，
            // 这边因为会有多个供应商的情况，需要进行累计,所以先放到一个map中
            // 通过时间键值，把同一时间的数量相加
            Map<String,EkeyComplexBean> complexBeanMap = new LinkedHashMap<String,EkeyComplexBean>();
            for (int i = 0; i < l_supCode.size(); ++i) {

                String supCode = l_supCode.get(i);

                fill(l_time, complexBeanMap, l_activationUser, l_activeUser, l_downloadUser, l_registerUser, l_stayingUser, timeType, supCode, _year, _sDate, _eDate);
            }

            if(!complexBeanMap.isEmpty()) {
                for(Iterator<EkeyComplexBean> iterator = complexBeanMap.values().iterator(); iterator.hasNext();) {
                    l_eKey.add(iterator.next());
                }
            }
        } catch (Exception e) {
            LOGGER.error("综合统计错误", e);
        }

        result.put("eKey", l_eKey);

        return result;
    }

    /**
     * 填充时间值对象
     *
     * @param l_time           时间区间集合
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
    private void fill(List<String> l_time,Map<String, EkeyComplexBean> eKeyComplexBeanMap,List<Map<String, Object>> l_activationUser, List<Map<String, Object>> l_activeUser, List<Map<String, Object>> l_downloadUser, List<Map<String, Object>> l_registerUser, List<Map<String, Object>> l_stayingUser, String timeType, String supCode, int _year, int _sDate, int _eDate) {
        Long defaultCount = 0l;
        // 标识某日期是否有数据
        Boolean flag = false;
        if (isMonth(timeType) || isDay(timeType)) {
            for (int i = 0; i <= l_time.size() - 1; ++i) {
                EkeyComplexBean ekeyComplexBean;
                String time = l_time.get(i);
                if(eKeyComplexBeanMap.containsKey(time)) {
                    ekeyComplexBean = eKeyComplexBeanMap.get(time);
                } else {
                    ekeyComplexBean = new EkeyComplexBean();

                    eKeyComplexBeanMap.put(time,ekeyComplexBean);
                }

                // 设置显示的时间
                ekeyComplexBean.setTime(time);
                defaultCount = userAnalyseService._processUserCountByTimeAndSupCode(l_activationUser, null, supCode, time);
                ekeyComplexBean.setCount_activation(defaultCount);
                defaultCount = userAnalyseService._processUserCountByTimeAndSupCode(l_activeUser, null, supCode, time);
                ekeyComplexBean.setCount_active(defaultCount);
                defaultCount = userAnalyseService._processUserCountByTimeAndSupCode(l_downloadUser, null, supCode, time);
                ekeyComplexBean.setCount_download(defaultCount);
                defaultCount = userAnalyseService._processUserCountByTimeAndSupCode(l_registerUser, null, supCode, time);
                ekeyComplexBean.setCount_register(defaultCount);
                defaultCount = userAnalyseService._processUserCountByTimeAndSupCode(l_stayingUser, null, supCode, time);
                ekeyComplexBean.setCount_staying(defaultCount);
            }
        } else if(isWeek(timeType)) {
            // 周实现
            Map<Integer, List<String>> days = new LinkedHashMap<Integer, List<String>>();

            for (int i = _sDate; i <= _eDate; ++i) {
                Date[] dates = ApDateTime.dayOfWeekOfYear(_year, i);
                days.put(i, ApDateTime.getDayBetween(dates[0], dates[1]));
            }

            for (Iterator<Map.Entry<Integer, List<String>>> i = days.entrySet().iterator(); i.hasNext(); ) {
                EkeyComplexBean ekeyComplexBean;
                Map.Entry<Integer, List<String>> entry = i.next();
                StringBuilder timeString = new StringBuilder("第" + entry.getKey() + "周");
                List<String> _timeList = entry.getValue();
                timeString.append("(").append(_timeList.get(0)).append("至").append(_timeList.get(_timeList.size() - 1)).append(")");

                if(eKeyComplexBeanMap.containsKey(timeString)) {
                    ekeyComplexBean = eKeyComplexBeanMap.get(timeString);
                } else {
                    ekeyComplexBean = new EkeyComplexBean();

                    eKeyComplexBeanMap.put(timeString.toString(), ekeyComplexBean);
                }

                // 设置显示的时间
                ekeyComplexBean.setTime(timeString.toString());

                Long downloadSum = 0l;
                Long activationSum = 0l;
                Long registerSum = 0l;
                Long activeSum = 0l;
                Long stayingSum = 0l;
                // 一周
                for (int j = 0; j < _timeList.size(); ++j) {
                    String time = _timeList.get(j);
                    // 处理激活用户数据
                    activationSum = userAnalyseService._processUserCountByTimeAndSupCode(l_activationUser, activationSum, supCode, time);
                    // 处理活跃用户数据
                    activeSum = userAnalyseService._processUserCountByTimeAndSupCode(l_activeUser, activeSum, supCode, time);
                    // 处理下载用户数据
                    downloadSum = userAnalyseService._processUserCountByTimeAndSupCode(l_downloadUser, downloadSum, supCode, time);
                    // 处理注册用户数据
                    registerSum = userAnalyseService._processUserCountByTimeAndSupCode(l_registerUser, registerSum, supCode, time);
                }
                stayingSum = userAnalyseService._processUserCountByTimeAndSupCode(l_stayingUser, null, supCode, entry.getKey().toString());

                ekeyComplexBean.setCount_activation(activationSum);
                ekeyComplexBean.setCount_staying(stayingSum);
                ekeyComplexBean.setCount_register(registerSum);
                ekeyComplexBean.setCount_active(activeSum);
                ekeyComplexBean.setCount_download(downloadSum);
            }
        }
    }

    private boolean isMonth(String type) {
        return "3".equals(type);
    }

    private boolean isWeek(String type) {
        return "2".equals(type);
    }

    private boolean isDay(String type) {
        return "1".equals(type);
    }
}
