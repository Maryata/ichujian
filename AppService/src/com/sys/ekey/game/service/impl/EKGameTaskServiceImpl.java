package com.sys.ekey.game.service.impl;

import com.sys.ekey.game.service.EKGameBaseService;
import com.sys.ekey.game.service.EKGameTaskService;
import com.sys.game.service.GameBaseService;
import com.sys.game.service.GameTaskService;
import com.sys.util.ApDateTime;
import com.sys.util.StrUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

//@Service
@SuppressWarnings("deprecation")
public class EKGameTaskServiceImpl extends EKGameBaseService implements EKGameTaskService {

    private Logger LOGGER = Logger.getLogger(EKGameTaskServiceImpl.class);

    @Override
    public void reward(String id, String uid, String type) {
        try {
            String strDate = "";
            long timeMillis = System.currentTimeMillis();
            try {
                strDate = ApDateTime.getDateTime(ApDateTime.DATE_TIME_YMD, timeMillis);
            } catch (Exception e) {
            }
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("ID", id);
            // 查询任务对应的奖励
            String score = (String) this.getSqlMapClientTemplate().queryForObject("ek_gameTask.getScoreById", paramMap);
            paramMap.put("UID", uid);
            paramMap.put("SCORE", score);
            paramMap.put("CHANGING", "1");
            paramMap.put("DATE", new Date());
            paramMap.put("STRDATE", strDate);
            try {
                // 任务记录
                this.getSqlMapClientTemplate().insert("ek_gameTask.reward", paramMap);
            } catch (DataAccessException e1) {
                LOGGER.error("task has done, task id >>> [" + id + "], task uid" +
                        " >>> [" + uid + "], time >>> [" + strDate + "], e : ", e1);
                // 数据库添加uid-tid-strdate组成的唯一约束，如果不满足，直接return，不用加积分
                return;
            }
            if ("1".equals(type)) {// 一次性任务
                // 修改用户积分
                this.updateUserScore(paramMap);
            } else if ("2".equals(type)) {// 签到
                SimpleDateFormat ymFormat = new SimpleDateFormat("yyyy-MM");
                Date today = new Date();
                paramMap.clear();
                paramMap.put("UID", uid);
                paramMap.put("DATE", ApDateTime.getDateTime(ApDateTime.DATE_TIME_YM, timeMillis));
                // 1.查询用户本月所有签到日期（时间倒序排列）
                List<Map<String, Object>> dates = this.getSqlMapClientTemplate().queryForList("ek_gameTask.signedDates", paramMap);
                Integer seq = 1;
                if (StrUtils.isNotEmpty(dates)) {
                    Map<String, Object> latestDate = dates.get(0);// 上一次签到
                    Object seqObj = latestDate.get("C_SEQ");
                    Object dateObj = latestDate.get("C_DATE");
                    String c_seq = StrUtils.emptyOrString(seqObj);
                    String c_date = StrUtils.emptyOrString(dateObj);
                    Integer i_seq = Integer.valueOf(c_seq);
                    // 获取今天
                    Calendar c = Calendar.getInstance();
                    int day = c.get(Calendar.DAY_OF_MONTH);// 日期
                    if (1 == day) {
                        seq = 1;
                    } else {
                        if (StrUtils.isNotEmpty(seqObj)) {
                            seq = i_seq;
                            // 昨天
                            /*String yesterday = ApDateTime.dateAdd("d", -1, new Date(), ApDateTime.DATE_TIME_YMD);
                            if (yesterday.equals(c_date)) {
                                // 如果昨天签到了，seq + 1
                                seq = i_seq + 1;
                            }*/
                        } else {
                            LOGGER.error("WRONG SEQ IN [T_GAME_SIGN], UID = " + uid + ", DATE = " + strDate);
                        }
                    }
                }
                // 查询连续天数对应的奖励
                paramMap.clear();
                paramMap.put("SEQ", seq);
                String reward = (String) this.getSqlMapClientTemplate().queryForObject("ek_gameTask.successions", paramMap);
                // 修改用户积分
                paramMap.clear();
                paramMap.put("UID", uid);
                paramMap.put("SCORE", Integer.valueOf(score) + Integer.valueOf(StrUtils.zeroOrInt(reward)));
                paramMap.put("CHANGING", "1");
                this.updateUserScore(paramMap);
            } else {// 每日随机任务
                this.updateUserScore(paramMap);
            }

            }catch(Exception e){
                LOGGER.error("EKGameTaskServiceImpl.reward failed, e : ", e);
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<Map<String, Object>> getRandomTask (String uid){
            Map<String, Object> parameterObject = new HashMap<String, Object>();
            parameterObject.put("uid", Long.valueOf(uid));
            return getSqlMapClientTemplate().queryForList("ek_gameTask.randomTask", parameterObject);
        }

        // 更新用户积分

    public void updateUserScore(Map<String, Object> paramMap) {
        try {
            // 查询用户积分
//			List<Map<String, Object>> userScore = this
//					.getSqlMapClientTemplate().queryForList("ek_gameTask.userScore", paramMap);
//			if(StrUtils.isNotEmpty(userScore)){
//				// 更新用户积分
//				if("1".equals(paramMap.get("CHANGING").toString())){// 增加
//					this.getSqlMapClientTemplate().update("ek_gameTask.plusUserScore", paramMap);
//				}
//				if("0".equals(paramMap.get("CHANGING").toString()))){// 减少
//					this.getSqlMapClientTemplate().update("ek_gameTask.reduceUserScore", paramMap);
//				}
//			}else{
//				// 新增用户积分
//				this.getSqlMapClientTemplate().insert("ek_gameTask.insertUserScore", paramMap);
//			}
            this.getSqlMapClientTemplate().update("ek_gameTask.changeUserScore", paramMap);
        } catch (Exception e) {
            LOGGER.error("EKGameTaskServiceImpl.updateUserScore failed, e : ", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    // 查询用户当天的签到记录
    public List<Map<String, Object>> signRecord(String uid) {
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("UID", uid);
            paramMap.put("DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            return this.getSqlMapClientTemplate().queryForList("ek_gameTask.signRecord", paramMap);
        } catch (Exception e) {
            LOGGER.error("EKGameTaskServiceImpl.signRecord failed, e : ", e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    // 每日签到
    public Map<String, Object> signIn(String uid, String signRecord) {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            // 获取今天
            Calendar c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_MONTH);// 日期
            Date today = new Date();
            SimpleDateFormat ymFormat = new SimpleDateFormat("yyyy-MM");
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
            paramMap.put("UID", uid);
            paramMap.put("DATE", ymFormat.format(today));
            // 1.查询用户本月所有签到日期
            List<Map<String, Object>> dates = this.getSqlMapClientTemplate()
                    .queryForList("ek_gameTask.signedDates", paramMap);

            // 2.连续签到天数
            Integer seq = 1;
            if ("0".equals(signRecord)) {
                if (StrUtils.isNotEmpty(dates)) {
                    Map<String, Object> latestDate = dates.get(0);// 上一次签到
                    Object seqObj = latestDate.get("C_SEQ");
                    Object dateObj = latestDate.get("C_DATE");
                    String c_seq = StrUtils.emptyOrString(seqObj);
                    String c_date = StrUtils.emptyOrString(dateObj);
                    Integer i_seq = Integer.valueOf(c_seq);
                    if (1 == day) {
                        // 如果今天是1号，seq = 1
                        reqMap.put("seq", day);
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
                            LOGGER.error("WRONG SEQ IN [T_GAME_SIGN], UID = "
                                    + uid + ", DATE = " + ymdFormat.format(today));
                        }
                    }
                }
            }

            String reward = "";
            if ("0".equals(signRecord)) {
                paramMap.put("DATE", today);
                paramMap.put("SEQ", seq);
                // 签到
                this.getSqlMapClientTemplate().insert("ek_gameTask.signIn", paramMap);

                // 将今天的签到放入集合
                Map<String, Object> firstDay = new HashMap<String, Object>();
                firstDay.put("C_SEQ", seq);
                firstDay.put("C_DATE", ymdFormat.format(today));
                dates.add(firstDay);

                reward = (String) this.getSqlMapClientTemplate().queryForObject("ek_gameTask.successions", paramMap);
            } else {
                if (StrUtils.isNotEmpty(dates)) {
                    Map<String, Object> latestDate = dates.get(0);// 上一次签到
                    String c_seq = latestDate.get("C_SEQ").toString();
                    Integer i_seq = Integer.valueOf(c_seq);
                    seq = i_seq;
                }
            }

            reqMap.put("dates", dates);// 所有签到日期
            reqMap.put("seq", seq);// 连续签到天数
            reqMap.put("reward", StrUtils.emptyOrString(reward));// 连续签到奖励
            reqMap.put("total", StrUtils.isEmpty(dates) ? "1" : dates.size());// 总计签到天数
        } catch (Exception e) {
            LOGGER.error("EKGameTaskServiceImpl.signIn failed, e : ", e);
        }
        return reqMap;
    }

    @Override
    // 查询用户是否已经做过一次性任务（上传头像、修改昵称、修改性别、修改手机号、绑定第三方）
    public int haveCompleted(String tid, String uid) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("UID", uid);
        paramMap.put("TID", tid);
        return (Integer) getSqlMapClientTemplate().queryForObject("ek_gameTask.haveCompleted", paramMap);
    }

    @Override
    public int haveBound(String uid, String loginType) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("UID", uid);
        paramMap.put("LOGINTYPE", loginType);
        return (Integer) getSqlMapClientTemplate().queryForObject("ek_gameTask.haveBound", paramMap);
    }

}
