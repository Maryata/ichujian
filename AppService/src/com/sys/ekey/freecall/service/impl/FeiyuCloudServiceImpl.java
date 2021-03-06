package com.sys.ekey.freecall.service.impl;

import com.sys.ekey.freecall.service.FeiyuCloudService;
import com.sys.ekey.rebate.service.impl.RebateServiceImpl;
import com.sys.util.ApDateTime;
import com.sys.util.StrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Maryn on 2016/4/12.
 */
@Service
@Transactional
public class FeiyuCloudServiceImpl extends SqlMapClientDaoSupport implements FeiyuCloudService {

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RebateServiceImpl rebateServiceImpl;

    private static final Log LOGGER = LogFactory.getLog(FeiyuCloudServiceImpl.class);

    @Override
    public int addAccount(String regid, String phonenum, Map<String, Object> jsonObject, String fc) {
        try {
            Map<String, Object> param = new HashMap<>();
            Map<String, Object> result = (Map) (jsonObject.get("result"));
            param.put("C_APPACCOUNTID", regid);
            param.put("C_FYACCOUNTID", result.get("fyAccountId"));
            param.put("C_FYACCOUNTPWD", result.get("fyAccountPwd"));
            param.put("C_CREATEDATE", result.get("addDate"));
            param.put("C_STATUS", result.get("status"));
            param.put("C_GLOBALMOBILEPHONE", phonenum);
            param.put("C_FC", fc);
            // 查询飞语账号是否存在
            Integer i = (Integer) getSqlMapClientTemplate().queryForObject("ek_freeCall.existsAccount", param);
            if (i <= 0) {
                // 不存在则新增
                getSqlMapClientTemplate().update("ek_freeCall.addAccount", param);
            } else {
                // 存在则更新
                getSqlMapClientTemplate().update("ek_freeCall.updateAccount", param);
            }
            return i;
        } catch (DataAccessException e) {
            LOGGER.error("FeiyuCloudServiceImpl.addAccount failed ! e : ", e);
        }
        return -1;
    }

    @Override
    // 给用户增加通话时间
    public void grantUserCallTime(Object fyAccountId, String supcode) {
        try {

            /** 2016-05-31 查询供应商的临时通话时长 */
            Integer tempTime = (Integer) getSqlMapClientTemplate().queryForObject("ek_freeCall.getTempTimeBySupCode", supcode);

            Map<String, Object> param = new HashMap<>();

            param.put("C_FYACCOUNTID", fyAccountId);
            // 临时时间
            param.put("C_TIME_TEMP", StrUtils.isEmpty(tempTime) ? 200 : tempTime);
            param.put("C_TIME_PERPETUAL", 0);
            param.put("C_VALIDITY", ApDateTime.getTheEndSecOfCurrMonth());
            getSqlMapClientTemplate().update("ek_freeCall.grantUserCallTime", param);

            // 永久时间
            param.put("C_TIME_TEMP", 0);
            param.put("C_TIME_PERPETUAL", 0);
            param.put("C_VALIDITY", ApDateTime.getTheEndOfCurrCentury());
            getSqlMapClientTemplate().update("ek_freeCall.grantUserCallTime", param);
        } catch (DataAccessException e) {
            LOGGER.error("FeiyuCloudServiceImpl.grantUserCallTime failed ! e : ", e);
        }
    }

    @Override
    public void addAccountLogError(String regid, Map<String, Object> jsonObject, String type) {
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("C_APPACCOUNTID", regid);
            param.put("C_RESULTCODE", jsonObject.get("resultCode"));
            param.put("C_RESULTMSG", jsonObject.get("resultMsg"));
            param.put("C_DATE", new Date());
            param.put("C_TYPE", type);
            getSqlMapClientTemplate().update("ek_freeCall.addAccountLogError", param);
        } catch (DataAccessException e) {
            LOGGER.error("FeiyuCloudServiceImpl.addAccountLogError failed ! e : ", e);
        }
    }

    @Override
    // 修改绑定手机
    public void modifyPhone(String regId, String fyAccountId, String phonenum) {
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("C_APPACCOUNTID", regId);
            param.put("C_FYACCOUNTID", fyAccountId);
            param.put("C_GLOBALMOBILEPHONE", phonenum);
            getSqlMapClientTemplate().update("ek_freeCall.modifyPhone", param);
        } catch (DataAccessException e) {
            LOGGER.error("FeiyuCloudServiceImpl.modifyPhone failed ! e : ", e);
        }
    }

    @Override
    public boolean isEnableAccount(String fyAccountId) {
        try {
            String status = (String) getSqlMapClientTemplate().queryForObject("ek_freeCall.getStatusByFyAccountId", fyAccountId);

            if ("1".equals(status)) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("查询飞语用户状态出错！", e);
        }

        return false;
    }

    @Override
    public Map<String, String> updatePayStatus(Map<String, String> parameterMap, String payType) {
        Map<String, String> result = new HashMap<String, String>();
        String return_code = "FAIL";
        String return_msg = "系统错误";
        // 订单号
        String out_trade_no = parameterMap.get("out_trade_no");
        final String sql = "SELECT T.C_PAY_STATUS, T.C_FYACCOUNTID,T.C_MINUTE,C_AMOUNT,C_TYPE FROM T_FY_ORDER T WHERE T.C_TRADE_NO = ?";
        final String updateSql = "UPDATE T_FY_ORDER SET C_PAY_STATUS = '1' WHERE C_TRADE_NO = ?";
        String addVipUserTimeSql = "INSERT INTO T_FY_USER_TIME_VIP (C_ID, C_FYACCOUNTID) VALUES (SEQ_FY_USER_TIME_VIP.NEXTVAL, ?)";
        final String updateUserTimeSql = "UPDATE T_FY_USER_TIME SET C_TIME_PERPETUAL = C_TIME_PERPETUAL + ? WHERE C_FYACCOUNTID=? AND TO_CHAR(C_VALIDITY,'yyyy') = '9999'";
        final String selectUidSql = "SELECT T.C_ID FROM T_EK_MEMBER_INFO T" +
                " WHERE T.C_PHONENUM=(SELECT U.C_GLOBALMOBILEPHONE  FROM T_FY_USER U WHERE U.C_FYACCOUNTID=?)";
        final String insertRecord = "INSERT INTO T_FC_RECORD(C_ID,C_UID,C_OUT_TRADE_NO,C_FCID,C_TIME,C_DATE) VALUES (SEQ_FC_RECORD.nextval,?,?,?,?,trunc(sysdate,'MI')+30*?) ";
       /* final String _insertRecord = "INSERT INTO T_FC_RECORD(C_ID,C_UID,C_OUT_TRADE_NO,C_FCID,C_TIME,C_DATE) VALUES (SEQ_FC_RECORD.nextval,?,?,?,?,?+30*?) ";
        final String selectRecord = "SELECT C_ID,C_UID,C_DATE,C_SYSDATE,C_FCID,C_OUT_TRADE_NO,C_TIME FROM (" +
                " SELECT T.C_ID,T.C_UID,T.C_DATE,T.C_SYSDATE,T.C_FCID,T.C_OUT_TRADE_NO,T.C_TIME FROM T_FC_RECORD T " +
                " WHERE  T.C_UID=? ORDER BY T.C_SYSDATE DESC" +
                " ) WHERE ROWNUM=1";*/
        final String selectVip = "SELECT * FROM T_FY_USER_TIME_VIP WHERE C_FYACCOUNTID=? AND C_ISLIVE=1";
        final String updateVip_12 = "UPDATE T_FY_USER_TIME_VIP SET C_VALIDATE_DATE=ADD_MONTHS(C_VALIDATE_DATE, 12) WHERE C_FYACCOUNTID=?";
        final String updateVip_6 = "UPDATE T_FY_USER_TIME_VIP SET C_VALIDATE_DATE=ADD_MONTHS(C_VALIDATE_DATE, 6) WHERE C_FYACCOUNTID=?";
        final String insertVip_12 = "INSERT INTO T_FY_USER_TIME_VIP(C_ID,C_FYACCOUNTID) VALUES (SEQ_FY_USER_TIME_VIP.nextval,?) ";
        final String insertVip_6 = "INSERT INTO T_FY_USER_TIME_VIP(C_ID,C_FYACCOUNTID,C_VALIDATE_DATE) VALUES (SEQ_FY_USER_TIME_VIP.nextval,?,ADD_MONTHS(SYSDATE, 6)) ";
        synchronized (jdbcTemplate) {
            List<Map<String, Object>> objectMap = jdbcTemplate.queryForList(sql, new Object[]{out_trade_no});
            // 订单支付已成功
            if ("1".equals(objectMap.get(0).get("C_PAY_STATUS"))) {
                return_code = "SUCCESS";
                return_msg = "OK";
            } else {
                String minute = StrUtils.emptyOrString(objectMap.get(0).get("C_MINUTE"));
                String fyAccountId = StrUtils.emptyOrString(objectMap.get(0).get("C_FYACCOUNTID"));
                // 更新订单状态
                List<Map<String, Object>> vipMap = jdbcTemplate.queryForList(selectVip, new Object[]{fyAccountId});//查询是否有vip充值
                jdbcTemplate.update(updateSql, new Object[]{out_trade_no});
                if ("525600".equals(minute)) {// 如果是“170升级套餐”，不需要更新T_FY_USER_TIME表
                    jdbcTemplate.update(addVipUserTimeSql, new Object[]{fyAccountId});
                }else if("262800".equals(minute)){//半年包打
                    if(StrUtils.isEmpty(vipMap)){//添加
                        jdbcTemplate.update(insertVip_6, new Object[]{fyAccountId});
                    }else{//更新
                        jdbcTemplate.update(updateVip_6, new Object[]{fyAccountId});
                    }
                } else if("365000".equals(minute)){//一年包打
                    if(StrUtils.isEmpty(vipMap)){//添加
                        jdbcTemplate.update(insertVip_12, new Object[]{fyAccountId});
                    }else{//更新
                        jdbcTemplate.update(updateVip_12, new Object[]{fyAccountId});
                    }
                }else {
                    jdbcTemplate.update(updateUserTimeSql, new Object[]{minute, fyAccountId});
                }
                //查询用户id
                List<Map<String, Object>> uidMap = jdbcTemplate.queryForList(selectUidSql, new Object[]{fyAccountId});

               /* //查询记录
                List<Map<String, Object>> ListMap = null;
                try {
                    ListMap = jdbcTemplate.queryForList(selectRecord, new Object[]{uidMap.get("C_ID")});
                } catch (DataAccessException e) {
                    e.printStackTrace();
                }
                if (StrUtils.isEmpty(ListMap)) {
                    //添加到期时间
                    jdbcTemplate.update(insertRecord, new Object[]{uidMap.get("C_ID"), out_trade_no, objectMap.get("C_FYACCOUNTID"), objectMap.get("C_MINUTE"), objectMap.get("C_MINUTE")});
                } else {
                    for(Map map : ListMap){
                        jdbcTemplate.update(_insertRecord, new Object[]{uidMap.get("C_ID"), out_trade_no, objectMap.get("C_FYACCOUNTID"), objectMap.get("C_MINUTE"), map.get("C_DATE"), objectMap.get("C_MINUTE")});
                    }
                }*/

                if ("0".equals(payType)) {
                    logWeChartPay(parameterMap);
                } else if ("1".equals(payType)) {
                    logAlipayNotify(parameterMap);
                }
                /*************返利************/
                rebateServiceImpl.selectUid(StrUtils.emptyOrString(uidMap.get(0).get("C_ID")), StrUtils.emptyOrString(objectMap.get(0).get("C_AMOUNT")), StrUtils.emptyOrString(objectMap.get(0).get("C_TYPE")), "02");//uid,金额,0:不操作本身 1：操作本身,01购膜 02免费通话充值

                return_code = "SUCCESS";
                return_msg = "OK";
            }
        }

        result.put("return_code", return_code);
        result.put("return_msg", return_msg);

        return result;
    }

    @Override
    public void logWeChartPay(Map<String, String> parameterMap) {
        String err_code = parameterMap.get("err_code");
        String out_trade_no = parameterMap.get("out_trade_no");
        getSqlMapClientTemplate().insert("ek_freeCall.insertWeChartPayLog", parameterMap);

        if (!StringUtils.isEmpty(err_code)) {
            final String updateSql = "UPDATE T_FY_ORDER SET C_PAY_STATUS = '2' WHERE C_TRADE_NO = ?";
            jdbcTemplate.update(updateSql, new Object[]{out_trade_no});
        }
    }

    @Override
    public List<Map<String, Object>> callHistory(String fyAccountId, int pageNumber, int pageSize) {
        int start = pageSize * (pageNumber - 1) + 1;
        int maxRows = start + pageSize - 1;

        Map<String, Object> parameterObject = new HashMap<String, Object>();

        parameterObject.put("fyAccountId", fyAccountId);
        parameterObject.put("startingIndex", start);
        parameterObject.put("maxRows", maxRows);

        return getSqlMapClientTemplate().queryForList("ek_freeCall.callHistory", parameterObject);
    }

    @Override
    public int getMaxCallMinuteByFyAccountId(String fyAccountId, Map<String, Object> parameterMap) {
        try {
            // 查询帐户授权是否锁定
            Integer count = (Integer) getSqlMapClientTemplate().queryForObject("ek_freeCall.isLocked", fyAccountId);

            if (count != 0) {
                return -1;
            }

            /** 2016-7-26 查询用户是否购买了“一年”套餐 */
            List<Map<String, Object>> vip = getSqlMapClientTemplate().queryForList("ek_freeCall.isVipUser", fyAccountId);
            /** 2016-7-26 查询用户是否购买了“一年”套餐 */
            Integer maxCallMinute = 0;
            if (StrUtils.isEmpty(vip)) {
                maxCallMinute = (Integer) getSqlMapClientTemplate().queryForObject("ek_freeCall.getMaxCallMinuteByFyAccountId", fyAccountId);
            } else {
                // 用户的临时时长和永久时长
//                maxCallMinute = (Integer) getSqlMapClientTemplate().queryForObject("ek_freeCall.getMaxCallMinuteByFyAccountId", fyAccountId);

                // 用户的VIP时长
                String validateDate = StrUtils.emptyOrString(vip.get(0).get("C_VALIDATE_DATE"));
                long currentTimeMillis = System.currentTimeMillis();// 当前时间毫秒值
                if (StrUtils.isNotEmpty(validateDate)) {
                    long validateTimeMillis = ApDateTime.getDateTimeS(validateDate);// 有效通话截止日期毫秒值
                    if (validateTimeMillis > currentTimeMillis) {
                        maxCallMinute += (int) Math.ceil((float) (validateTimeMillis - currentTimeMillis) / 1000 / 60);
                    }
                }
            }
            Long ti = Long.valueOf((String) parameterMap.get("ti"));
            parameterMap.put("time_millis", ti);
            parameterMap.put("time", new Date(ti));

            if (maxCallMinute > 0) {
                getSqlMapClientTemplate().insert("ek_freeCall.insertAuth", parameterMap);
            }

            return maxCallMinute;
        } catch (Exception e) {
            LOGGER.error("查询飞语用户可用时间出错！", e);
        }

        return 0;
    }

    @Override
    public void insertCallHis(Map<String, Object> map) {
        String fyAccountId;
        final String updateSql1 = "update t_fy_user_time set c_time_temp = ? where c_id = ?";
        final String updateSql2 = "update t_fy_user_time set c_time_perpetual = ? where c_id = ?";
        try {
            Long ti = Long.valueOf((String) map.get("ti"));
            //String	是	飞语产生的呼叫ID
            String fyCallId = (String) map.get("fyCallId");
            //	long	是	通话的开始时间、回拨第二路的开始时间或者直拨的开始时间
            Long callStartTime = Long.valueOf((String) map.get("callStartTime"));
            // 	long	是	通话的结束时间、回拨第二路结束通话时间或者直拨的结束时间
            Long callEndTime = Long.valueOf((String) map.get("callEndTime"));

            map.put("sTime", new Date(callStartTime));
            map.put("eTime", new Date(callEndTime));
            // 更新用户剩余时长

            // 持久化话单数据
            getSqlMapClientTemplate().insert("ek_freeCall.insertCallHis", map);

            // 未进行有效通话
            if (callStartTime == callEndTime) {
                // 更新授权状态，标识本次授权通话完成，可允许下次授权
                getSqlMapClientTemplate().update("ek_freeCall.updateAuth", fyCallId);

                return;
            }

            // 更新用户剩余时长

            // 用户时长列表
            List<Map<String, Object>> list = getSqlMapClientTemplate().queryForList("ek_freeCall.getUserTimeByFyCallId", map);

            if (StrUtils.isNotEmpty(list)) {
                /** 2016-7-12 查询当前账号是否易宝用户 */
//                String fyaccountid = StrUtils.emptyOrString(list.get(0).get("C_FYACCOUNTID").toString());
//                String fc = (String) getSqlMapClientTemplate().queryForObject("ek_freeCall.isFCAccount", fyaccountid);
//                if ("1".equals(fc)) {
//                    return;
//                } else {}
                // t1.c_time_temp, t1.c_time_perpetual,t1.c_fyaccountid, t1.c_id
                // 如果list长度为0，理论上应该抛出异常
                // list 理论长度是2，第二条数据是永久时长
                // 当跨月通话时，长度为3，第三条数据是永久时长
                Map<String, Object> objectMap = list.get(0);
                fyAccountId = String.valueOf(objectMap.get("C_FYACCOUNTID"));
                // 通话时长
                int minute = (int) Math.ceil((float) (callEndTime - callStartTime) / 1000 / 60);

                /** 2016-7-26 查询是否购买过VIP套餐，如果买过。先计算通过时长超过期限。如果超过，再减去临时时长和永久时长 */
                List<Map<String, Object>> vip = getSqlMapClientTemplate().queryForList("ek_freeCall.isVipUser", fyAccountId);
                // 用户的VIP时长
                long validateTimeMillis = 0l;
                if (StrUtils.isNotEmpty(vip)) {
                    String validateDate = StrUtils.emptyOrString(vip.get(0).get("C_VALIDATE_DATE"));
                    if (StrUtils.isNotEmpty(validateDate)) {
                        validateTimeMillis = ApDateTime.getDateTimeS(validateDate);// 有效通话截止日期毫秒值
                    }
                }
                // 如果通话时长超过VIP套餐时长，则需要减掉的时间为（结束时间-VIP有效时间）
                if (0l != validateTimeMillis && callEndTime > validateTimeMillis) {
                    minute = (int) Math.ceil((float) ((callEndTime - validateTimeMillis) / 1000 / 60));
                }


                int len = list.size();

                // 用户剩余通话时间表主键
                Long id = ((BigDecimal) objectMap.get("C_ID")).longValue();
                // 有效时长的ID
                Long perpetualId = 0l;
                Long timeTemp = ((BigDecimal) objectMap.get("C_TIME_TEMP")).longValue();
                Long timePerpetual = 0L;
                if (len == 2) {
                    timePerpetual = ((BigDecimal) list.get(1).get("C_TIME_PERPETUAL")).longValue();
                    perpetualId = ((BigDecimal) list.get(1).get("C_ID")).longValue();
                } else if (len == 3) {
                    timePerpetual = ((BigDecimal) list.get(2).get("C_TIME_PERPETUAL")).longValue();
                    perpetualId = ((BigDecimal) list.get(2).get("C_ID")).longValue();
                } // 其它情况都是程序出错了

                if (minute > timeTemp) {
                    // 通话时长如果大于临时时长，不足部分从永久时长里面去减
                    timeTemp = 0l;
                    jdbcTemplate.update(updateSql1, new Object[]{timeTemp, id});
                    timePerpetual = timePerpetual - minute + timeTemp;
                    jdbcTemplate.update(updateSql2, new Object[]{timePerpetual, perpetualId});
                } else {
                    // 临时时长有剩余的情况
                    timeTemp -= minute;
                    jdbcTemplate.update(updateSql1, new Object[]{timeTemp, id});
                }

                // 更新授权状态，标识本次授权通话完成，可允许下次授权
                getSqlMapClientTemplate().update("ek_freeCall.updateAuth", fyCallId);
            }

        } catch (Exception e) {
            LOGGER.error("接收话单错误", e);
        }
    }

    @Override
    public List<Map<String, Object>> combo() {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            list = getSqlMapClientTemplate().queryForList("ek_freeCall.combo");
        } catch (DataAccessException e) {
            LOGGER.error("FeiyuCloudServiceImpl.combo failed, e : ", e);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> userInfo(String uid) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("UID", uid);
            List<Map<String, Object>> result = getSqlMapClientTemplate().queryForList("ek_freeCall.userInfo", paramMap);
            if (null != result && result.size() == 2) {
                Map<String, Object> temp = result.get(0);// 临时时长
                Map<String, Object> perpetual = result.get(1);// 固定时长
                Map<String, Object> map = new HashMap<>();
                map.put("C_FYACCOUNTID", temp.get("C_FYACCOUNTID"));
                map.put("C_FYACCOUNTPWD", temp.get("C_FYACCOUNTPWD"));
                map.put("C_GLOBALMOBILEPHONE", temp.get("C_GLOBALMOBILEPHONE"));
                map.put("C_HEADIMAGE", temp.get("C_HEADIMAGE"));
                map.put("C_TIME_TEMP", temp.get("C_TIME_TEMP"));
                map.put("C_TIME_PERPETUAL", perpetual.get("C_TIME_PERPETUAL"));
                list.add(map);
            }
        } catch (DataAccessException e) {
            LOGGER.error("FeiyuCloudServiceImpl.userInfo failed, e : ", e);
        }
        return list;
    }

    @Override
    // 查询没有飞语云账号的老用户
    public List<Map<String, Object>> getExistsUserIsNotFyAcc() {
        return getSqlMapClientTemplate().queryForList("ek_freeCall.getExistsUserIsNotFyAcc");
    }

    @Override
    // 根据商户订单号查询订单
    public List<Map<String, Object>> getOrderByTradeNo(String out_trade_no) {
        return null;
    }

    @Override
    // 查询套餐详情
    public List<Map<String, Object>> getComboById(String id) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("ID", id);
            list = getSqlMapClientTemplate().queryForList("ek_freeCall.getComboById", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("FeiyuCloudServiceImpl.getComboById failed, e : ", e);
        }
        return list;
    }

    @Override
    // 生成订单
    public void insertOrder(String out_trade_no, String fyAccountId, String total_fee, String c_time, String payType, String c_type) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("C_TRADE_NO", out_trade_no);
            paramMap.put("C_FYACCOUNTID", fyAccountId);
            paramMap.put("C_AMOUNT", total_fee);
            paramMap.put("C_MINUTE", c_time);
            paramMap.put("C_PAY_TYPE", payType);
            paramMap.put("C_TYPE", c_type);
            getSqlMapClientTemplate().update("ek_freeCall.insertOrder", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("FeiyuCloudServiceImpl.insertOrder failed, e : ", e);
        }
    }

    @Override
    // 更新订单支付状态
    public void updatePayStatus(String out_trade_no) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("C_TRADE_NO", out_trade_no);
            getSqlMapClientTemplate().update("ek_freeCall.updatePayStatus", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("FeiyuCloudServiceImpl.updatePayStatus failed, e : ", e);
        }
    }

    @Override
    // 记录支付宝回调日志
    public void logAlipayNotify(Map<String, String> params) {
        try {
            getSqlMapClientTemplate().update("ek_freeCall.logAlipayNotify", params);
        } catch (DataAccessException e) {
            LOGGER.error("FeiyuCloudServiceImpl.logAlipayNotify failed, e : ", e);
        }
    }


    @Override
    public List<Map<String, Object>> feiyuAccHasntTime() {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            list = getSqlMapClientTemplate().queryForList("ek_freeCall.feiyuAccHasntTime");
        } catch (DataAccessException e) {
            LOGGER.error("FeiyuCloudServiceImpl.feiyuAccHasntTime failed, e : ", e);
        }
        return list;
    }

    @Override
    // 获取手机号归属地
    public String phoneArea(String phone) {
        String phoneArea = "";
        try {
            List<Map<String, Object>> list = getSqlMapClientTemplate().queryForList("ek_freeCall.phoneArea", phone.substring(0, 7));
            if (StrUtils.isNotEmpty(list)) {
                String province = StrUtils.emptyOrString(list.get(0).get("C_PROVINCE"));
                String city = StrUtils.emptyOrString(list.get(0).get("C_CITY"));
                if (!"".equals(province) && !"".equals(city)) {
                    // 如果省和市相同，则只返回省；否则返回“省市”
                    phoneArea = province + (city.equals(province) ? "" : city);
                }
            }
        } catch (DataAccessException e) {
            LOGGER.error("FeiyuCloudServiceImpl.phoneArea failed, e : ", e);
        }
        return phoneArea;
    }

    @Override
    // 记录用户点击“免费电话”按钮的行为
    public void callingRec(String uid, String imei, String fyAccId, String flag, String sup, String callee) {
        Map<String, Object> paramMap = new HashMap<>();
        try {
            // 根据飞语账号查询手机号
            String caller = (String) getSqlMapClientTemplate().queryForObject("ek_freeCall.getFyAccInfo", fyAccId);

            // 保存
            paramMap.put("UID", uid);
            paramMap.put("IMEI", imei);
            paramMap.put("FYACCID", fyAccId);
            paramMap.put("DATE", new Date(Long.valueOf(flag)));
            paramMap.put("FLAG", flag);
            paramMap.put("SUP", sup);
            paramMap.put("CALLER", caller);
            paramMap.put("CALLEE", callee);
            getSqlMapClientTemplate().update("ek_freeCall.callingRec", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("FeiyuCloudServiceImpl.callingRec failed, e : ", e);
        }
    }

    @Override
    // 更新通话记录
    public void updateCallingRec(String etime, String elapse, String flag) {
        Map<String, Object> paramMap = new HashMap<>();
        try {
            paramMap.put("STIME", Long.valueOf(etime) - Long.valueOf(elapse));
            paramMap.put("ETIME", etime);
            paramMap.put("FLAG", flag);
            getSqlMapClientTemplate().update("ek_freeCall.updateCallingRec", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("FeiyuCloudServiceImpl.updateCallingRec failed, e : ", e);
        }
    }

    /**
     * 获取免费通话App 套餐
     *
     * @param id
     * @return
     */
    @Override
    public List<Map<String, Object>> getAppComboById(String id) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("ID", id);
            list = getSqlMapClientTemplate().queryForList("ek_freeCall.getAppComboById", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("FeiyuCloudServiceImpl.getAppComboById failed, e : ", e);
        }
        return list;
    }

    @Override
    // 免费通话错误日志
    public void recodeErrorCode(String caller, String callee, String code, String errorMsg) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("CALLER", caller);
            paramMap.put("CALLEE", callee);
            paramMap.put("CODE", code);
            paramMap.put("MSG", errorMsg);
            getSqlMapClientTemplate().update("ek_freeCall.recodeErrorCode", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("FeiyuCloudServiceImpl.recodeErrorCode failed, e : ", e);
        }
    }

}
