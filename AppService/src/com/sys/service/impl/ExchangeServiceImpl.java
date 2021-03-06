package com.sys.service.impl;

import com.sys.action.ErpInfo;
import com.sys.ekey.rebate.service.impl.RebateServiceImpl;
import com.sys.ekey.task.service.EKTaskService;
import com.sys.service.ExchangeService;
import com.sys.util.StrUtils;
import com.sys.vo.OrderVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by vpc on 2016/3/2.
 */
@Service
public class ExchangeServiceImpl implements ExchangeService {

    private static final Log LOGGER = LogFactory.getLog(ExchangeServiceImpl.class);

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private EKTaskService ekTaskService;
    @Autowired
    private RebateServiceImpl rebateServiceImpl;
    @Autowired
    private ErpInfo erpInfo;

    @Override
    public int save(String phone, String express, String address, String consignee, String telNum, String imei, String pCode, String expressCode, String out_trade_no, String uid, String payType, String total_fee, String c_type) {
        final String sql = "insert into t_order" +
                " (c_id, c_phone, c_address, c_consignee, c_telnum,c_date,c_pCode,c_expressCode,c_outTradeNo,c_status,C_UID,C_PAY_TYPE,C_ERP,C_PRICE,C_TYPE)" +
                " values (seq_order.nextVal, ?, ?, ?, ?, ?, ?, ?, ?, 0, ?, ?, 0,?,?)";
        return jdbcTemplate.update(sql, new Object[]{phone, address, consignee, telNum, new Date(), pCode, expressCode, out_trade_no, uid, payType, total_fee, c_type});
    }

    @Override
    public Map<String, Object> record(String imei) {
        if (StringUtils.isEmpty(imei)) {
            imei = "-1";
        }

        Map<String, Object> result = new HashMap<String, Object>();
        final String sql = "select c_id, c_phone, c_address, c_express, c_consignee, c_telnum, c_imei, c_date from t_order where c_uid=? and c_status=1 order by c_date desc";

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, imei);
        if (list == null) {
            list = new ArrayList<Map<String, Object>>();
        }

        result.put("list", list);

        return result;
    }

    @Override
    public Map<String, String> update(String out_trade_no) {
        Map<String, String> result = new HashMap<String, String>();
        String return_code = "FAIL";
        String return_msg = "系统错误";

        final String selectOrderUid = "SELECT C_ID, C_UID, C_PHONE, C_ADDRESS, C_EXPRESS, C_CONSIGNEE, C_TELNUM, C_IMEI, C_DATE, C_PCODE, C_EXPRESSCODE, C_OUTTRADENO, C_STATUS, C_PAY_TYPE,C_PRICE,C_TYPE FROM T_ORDER T WHERE T.C_OUTTRADENO = ?";
        //修改订单状态
        final String sql = "UPDATE t_order SET c_status=1 WHERE c_outTradeNo=?";

        final String countSql = "SELECT  COUNT(1) FROM T_EK_MEMBER_TASK WHERE C_UID=? AND C_TID=?";

        final String inviteUserInfo = "SELECT  TEMI.C_ID,TEMI.C_REGID,(SELECT COUNT(1) FROM T_EK_MEMBER_INFO WHERE C_INVITECODE=TEMI.C_REGID) AS NUM" +
                " FROM T_EK_MEMBER_INFO TEMI WHERE TEMI.C_REGID =(SELECT T.C_INVITECODE FROM T_EK_MEMBER_INFO T WHERE T.C_ID = ?)";

        final String gmSucess = "SELECT  COUNT(TEMI.C_ID) FROM T_EK_MEMBER_INFO TEMI" +
                " LEFT JOIN T_EK_MEMBER_TASK TEMT ON TEMT.C_UID = TEMI.C_ID" +
                " WHERE TEMI.C_INVITECODE =? AND TEMT.C_TID ='4'";

        final String selectCode = "SELECT T.C_INVITECODE FROM T_EK_MEMBER_INFO T WHERE T.C_ID = ?";

        synchronized (jdbcTemplate) {
            List<Map<String, Object>> objectMap = jdbcTemplate.queryForList(selectOrderUid, new Object[]{out_trade_no});//查询订单信息

            /** 2016-04-29 增加功能：erp推送 begin */
            try {
                if ("0".equals(StrUtils.emptyOrString(objectMap.get(0).get("C_STATUS")))) {
                    OrderVo orderVo = new OrderVo();
                    orderVo.setSerialId("E" + out_trade_no);// 订单序列号
                    orderVo.setAddress(StrUtils.emptyOrString(objectMap.get(0).get("C_ADDRESS")));// 收货地址
                    orderVo.setConsignee(StrUtils.emptyOrString(objectMap.get(0).get("C_CONSIGNEE")));// 收货人
                    try {
                        orderVo.setCreateDate(StrUtils.emptyOrString(objectMap.get(0).get("C_DATE")));// 订单创建时间
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    orderVo.setExpressCode(ErpInfo.EXPRESS_CODE_ZTO);// 默认发中通
                    orderVo.setpCode(StrUtils.emptyOrString(objectMap.get(0).get("C_PCODE")));// 产品代码
                    orderVo.setPhone(StrUtils.emptyOrString(objectMap.get(0).get("C_PHONE")));// 手机品牌
                    orderVo.setTelNum(StrUtils.emptyOrString(objectMap.get(0).get("C_TELNUM")));// 收货人电话
                    orderVo.setPayType(StrUtils.emptyOrString(objectMap.get(0).get("C_PAY_TYPE")));// 支付类型
                    erpInfo.addOrder(orderVo);
                }
            } catch (Exception e) {
                LOGGER.error("push to ERP failed ! [orderId : ]" + StrUtils.emptyOrString(objectMap.get(0).get("C_ID")));
            }
            /** 2016-04-29 增加功能：erp推送 end */

            String uid = StrUtils.emptyOrString(objectMap.get(0).get("C_UID"));
            int row = jdbcTemplate.update(sql, new Object[]{out_trade_no});//更新订单状态
            /*************返利************/
            rebateServiceImpl.selectUid(StrUtils.emptyOrString(objectMap.get(0).get("C_UID")), StrUtils.emptyOrString(objectMap.get(0).get("C_PRICE")), StrUtils.emptyOrString(objectMap.get(0).get("C_TYPE")), "01");//uid,金额,299套餐 199套餐 0,01购膜 02免费通话充值
            if (row > 0) {
                int count = jdbcTemplate.queryForObject(countSql, new Object[]{objectMap.get(0).get("C_UID"), 4}, Integer.class); //查询是否首次换膜
                if (count == 0) {
                    /** 购膜人员 首次购膜添加积分*/
                    ekTaskService.reward(uid, "1", "4", null);
                    /** 查询邀请人 邀请的人中（当前邀请人是不是首次购膜）*/
                    /**查询邀请人的code*/
                    Map<String, Object> codeMap = jdbcTemplate.queryForMap(selectCode, new Object[]{uid});
                    if (StrUtils.isNotEmpty(StrUtils.emptyOrString(codeMap.get("C_INVITECODE")))) {//存在邀请人的code
                        /** 1：查询 邀请人id */
                        //查询邀请人uid 邀请码  邀请的数量
                        Map<String, Object> inviteUserMap = jdbcTemplate.queryForMap(inviteUserInfo, new Object[]{uid});
                        String inviteUid = StrUtils.emptyOrString(inviteUserMap.get("C_ID"));//邀请人id

                        int num = jdbcTemplate.queryForObject(gmSucess, new Object[]{codeMap.get("C_INVITECODE")}, Integer.class);
                        if (num == 1) {
                            ekTaskService.reward(inviteUid, "1", "5", null);
                        }
                    }

                    return_code = "SUCCESS";
                    return_msg = "OK";
                } else {
                    return_code = "SUCCESS";
                    return_msg = "OK";
                }
            } else {
                return_code = "FAIL";
                return_msg = "NO";
            }
        }
        result.put("return_code", return_code);
        result.put("return_msg", return_msg);

        return result;
    }


    @Override
    // ERP推送成功之后，修改订单的erp状态
    public void toErp(String platform_code, String orderType) {
        try {
            if (StrUtils.isNotEmpty(platform_code)) {
                platform_code = platform_code.substring(1, platform_code.length());
                LOGGER.info("[Exchange] into [update erp status], out_trade_no is [" + platform_code + "]");
                String sql;
                if ("1".equals(orderType)) {// 免费换膜
                    sql = "UPDATE T_ORDER T SET T.C_ERP = 1 WHERE T.C_OUTTRADENO = ?";
                } else {// 积分商城
                    sql = "UPDATE T_EK_MALL_ORDER T SET T.C_ERP = 1 WHERE T.C_TRADE_NO = ?";
                }
                int result = jdbcTemplate.update(sql, new Object[]{platform_code});
                if (result > 0) {
                    LOGGER.info("[Exchange] UPDATE ERP STATUS SUCCESS ! out_no is [" + platform_code + "]");
                } else {
                    LOGGER.info("[Exchange] UPDATE ERP STATUS FAILED ! out_no is [" + platform_code + "]");
                }
            }
        } catch (DataAccessException e) {
            LOGGER.error("[Exchange] update erp status failed ! out_no is [" + platform_code + "]");
        }
    }

    /**
     * 订单信息查询
     *
     * @param out_trade_no
     * @return
     */
    @Override
    public List<Map<String, Object>> selectOrder(String out_trade_no) {
        final String selectOrderUid = "SELECT C_ID, C_UID, C_PHONE, C_ADDRESS, C_EXPRESS, C_CONSIGNEE, C_TELNUM, C_IMEI, C_DATE, C_PCODE, C_EXPRESSCODE, C_OUTTRADENO, C_STATUS, C_PAY_TYPE,C_PRICE,C_TYPE FROM T_ORDER T WHERE T.C_OUTTRADENO = ?";
        return jdbcTemplate.queryForList(selectOrderUid, new Object[]{out_trade_no});
    }

}
