package com.sys.ekey.freecall.service.impl;

import com.sys.ekey.freecall.service.FeiyuCloudService;
import com.sys.util.ApDateTime;
import com.sys.util.StrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    private static final Log LOGGER = LogFactory.getLog(FeiyuCloudServiceImpl.class);

    @Override
    public int addAccount(String regid, String phonenum, Map<String, Object> jsonObject) {
        try {
            Map<String, Object> param = new HashMap<>();
            Map<String, Object> result = (Map) (jsonObject.get("result"));
            param.put("C_APPACCOUNTID", regid);
            param.put("C_FYACCOUNTID", result.get("fyAccountId"));
            param.put("C_FYACCOUNTPWD", result.get("fyAccountPwd"));
            param.put("C_CREATEDATE", result.get("addDate"));
            param.put("C_STATUS", result.get("status"));
            param.put("C_GLOBALMOBILEPHONE", phonenum);
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
        final String sql = "SELECT T.C_PAY_STATUS, T.C_FYACCOUNTID,T.C_MINUTE FROM T_FY_ORDER T WHERE T.C_TRADE_NO = ?";
        final String updateSql = "UPDATE T_FY_ORDER SET C_PAY_STATUS = '1' WHERE C_TRADE_NO = ?";

        final String updateUserTimeSql = "UPDATE T_FY_USER_TIME SET C_TIME_PERPETUAL = C_TIME_PERPETUAL + ? WHERE C_FYACCOUNTID=? AND TO_CHAR(C_VALIDITY,'yyyy') = '9999'";
        synchronized (jdbcTemplate) {
            Map<String,Object> objectMap = jdbcTemplate.queryForMap(sql, new Object[]{out_trade_no});
            // 订单支付已成功
            if ("1".equals(objectMap.get("C_PAY_STATUS"))) {
                return_code = "SUCCESS";
                return_msg = "OK";
            } else {
                // 更新订单状态
                jdbcTemplate.update(updateSql, new Object[]{out_trade_no});
                jdbcTemplate.update(updateUserTimeSql, new Object[]{objectMap.get("C_MINUTE"),objectMap.get("C_FYACCOUNTID")});
                if ("0".equals(payType)) {
                    logWeChartPay(parameterMap);
                } else if ("1".equals(payType)) {
                    logAlipayNotify(parameterMap);
                }

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

        if(!StringUtils.isEmpty(err_code)){
            final String updateSql = "UPDATE T_FY_ORDER SET C_PAY_STATUS = '2' WHERE C_TRADE_NO = ?";
            jdbcTemplate.update(updateSql, new Object[]{out_trade_no});
        }
    }

    @Override
    public List<Map<String, Object>> callHistory(String fyAccountId, int pageNumber, int pageSize) {
        int start = pageSize * (pageNumber - 1) + 1;
        int maxRows = start + pageSize - 1;

        Map<String, Object> parameterObject = new HashMap<String, Object>();

        parameterObject.put( "fyAccountId", fyAccountId );
        parameterObject.put( "startingIndex", start );
        parameterObject.put( "maxRows", maxRows );

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

            Integer maxCallMinute = (Integer) getSqlMapClientTemplate().queryForObject("ek_freeCall.getMaxCallMinuteByFyAccountId", fyAccountId);
            Long ti = Long.valueOf((String) parameterMap.get("ti"));
            parameterMap.put("time_millis", ti);
            parameterMap.put("time", new Date(ti));

            if(maxCallMinute > 0) {
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

            // t1.c_time_temp, t1.c_time_perpetual,t1.c_fyaccountid, t1.c_id
            // 如果list长度为0，理论上应该抛出异常
            // list 理论长度是2，第二条数据是永久时长
            // 当跨月通话时，长度为3，第三条数据是永久时长
            int len = list.size();

            Map<String, Object> objectMap = list.get(0);
            fyAccountId = String.valueOf(objectMap.get("C_FYACCOUNTID"));
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
            // 通话时长
            int minute = (int) Math.ceil((float) (callEndTime - callStartTime) / 1000 / 60);

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
    public void insertOrder(String out_trade_no, String fyAccountId, String total_fee, String c_time, String payType) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("C_TRADE_NO", out_trade_no);
            paramMap.put("C_FYACCOUNTID", fyAccountId);
            paramMap.put("C_AMOUNT", total_fee);
            paramMap.put("C_MINUTE", c_time);
            paramMap.put("C_PAY_TYPE", payType);
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
}
