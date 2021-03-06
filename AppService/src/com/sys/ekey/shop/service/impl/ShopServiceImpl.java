package com.sys.ekey.shop.service.impl;

import com.sys.action.ErpInfo;
import com.sys.ekey.shop.common.*;
import com.sys.ekey.shop.service.ShopService;
import com.sys.util.StrUtils;
import com.sys.vo.EKMallOrder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/4/15.
 */
@Service
@Transactional
public class ShopServiceImpl extends SqlMapClientDaoSupport implements ShopService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private static Logger LOGGER = Logger.getLogger(ShopServiceImpl.class);

    @Autowired
    private ErpInfo erpInfo;

    /**
     * 签到奖励
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> signAward(String uid) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("uid", uid);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_shop.signAward",paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.signAward failed, e : " + e.getMessage());
        }
        return list;
    }

    /**
     * 签到 详情
     *
     * @param uid
     * @return
     */
    @Override
    public List<Map<String, Object>> signDettail(String uid) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("uid", uid);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_shop.signDettail", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.signDettail failed, e : " + e.getMessage());
        }
        return list;
    }

    /**
     * 本月累计签到天数
     *
     * @param uid
     * @return
     */
    @Override
    public int signCountDay(String uid) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("uid", uid);
        int count = 0;
        try {
            count = (int) this.getSqlMapClientTemplate().queryForObject("ek_shop.signCountDay", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.signCountDay failed, e : " + e.getMessage());
        }
        return count;
    }

    /**
     * 连续签到奖励
     *
     * @return
     *//*
    @Override
    public List<Map<String, Object>> signAwardSeries() {
        List<Map<String, Object>> list = new ArrayList();
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_shop.signAwardSeries");
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.signAwardSeries failed, e : " + e.getMessage());
        }
        return list;
    }*/

    /**
     * 可用积分
     *
     * @param uid
     * @return
     */
    @Override
    public int availableIntegral(String uid) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("uid", uid);
        int kyScore = 0;
        try {
            kyScore = (int) this.getSqlMapClientTemplate().queryForObject("ek_shop.availableIntegral", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.availableIntegral failed, e : " + e.getMessage());
        }
        return kyScore;
    }

    /**
     * 可获得积分
     *
     * @param uid
     * @return
     */
    @Override
    public int securableIntegral(String uid) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("uid", uid);
        int hdScore = 0;
        try {
            hdScore = (int) this.getSqlMapClientTemplate().queryForObject("ek_shop.securableIntegral", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.securableIntegral failed, e : " + e.getMessage());
        }
        return hdScore;
    }

    /**
     * 任务列表
     *
     * @param uid
     * @return
     */
    @Override
    public List<Map<String, Object>> task(String uid) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("uid", uid);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_shop.task", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.task failed, e : " + e.getMessage());
        }
        return list;
    }

    /**
     * 邀请人数
     *
     * @param uid
     * @return
     */
    @Override
    public int inviteNumber(String uid) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("uid", uid);
        int count = 0;
        try {
            count = (int) this.getSqlMapClientTemplate().queryForObject("ek_shop.inviteNumber", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.inviteNumber failed, e : " + e.getMessage());
        }
        return count;
    }

    /**
     * 我的邀请码
     *
     * @param uid
     * @return
     */
    @Override
    public String inviteCode(String uid) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("uid", uid);
        String inviteCode = "";
        try {
            inviteCode = (String) this.getSqlMapClientTemplate().queryForObject("ek_shop.inviteCode", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.inviteCode failed, e : " + e.getMessage());
        }
        return inviteCode;
    }

    /**
     * 邀请奖励积分
     *
     * @param uid
     * @return
     */
    @Override
    public int inviteAward(String uid) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("uid", uid);
        int count = 0;
        try {
            count = (int) this.getSqlMapClientTemplate().queryForObject("ek_shop.inviteAward", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.inviteAward failed, e : " + e.getMessage());
        }
        return count;
    }

    /**
     * 邀请详情信息
     *
     * @param uid
     * @return
     */
    @Override
    public List<Map<String, Object>> inviteDetail(String uid) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("uid", uid);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_shop.inviteDetail", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.inviteDetail failed, e : " + e.getMessage());
        }
        return list;
    }

    /**
     * 商品列表
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> commodity(String cid ,int pageNumber, int pageSize) {
        int start = pageSize * (pageNumber - 1) + 1;
        int maxRows = start + pageSize - 1;
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("cid", cid);
        paramMap.put("startingIndex", start);
        paramMap.put("maxRows", maxRows);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_shop.commodity", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.commodity failed, e : " + e.getMessage());
        }
        return list;
    }

    /**
     * 头像查询
     *
     * @param uid
     * @return
     */
    @Override
    public String logo(String uid) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("uid", uid);
        String inviteCode = "";
        try {
            inviteCode = (String) this.getSqlMapClientTemplate().queryForObject("ek_shop.logo", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.logo failed, e : " + e.getMessage());
        }
        return inviteCode;
    }

    /**
     * 商品详情
     *
     * @param pid
     * @return
     */
    @Override
    public List<Map<String, Object>> commodityDetail(String pid) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("pid", pid);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_shop.commodityDetail", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.commodityDetail failed, e : " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> gainRecords(String uid, int pageNumber, int pageSize) {
        int start = pageSize * (pageNumber - 1) + 1;
        int maxRows = start + pageSize - 1;
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("uid", uid);
        paramMap.put("startingIndex", start);
        paramMap.put("maxRows", maxRows);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_shop.gainRecords", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.gainRecords failed, e : " + e.getMessage());
        }
        return list;
    }

    /**
     * 积分使用记录
     *
     * @param uid
     * @return
     */
    @Override
    public List<Map<String, Object>> useRecords(String uid, int pageNumber, int pageSize) {
        int start = pageSize * (pageNumber - 1) + 1;
        int maxRows = start + pageSize - 1;
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("uid", uid);
        paramMap.put("startingIndex", start);
        paramMap.put("maxRows", maxRows);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_shop.useRecords", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.useRecords failed, e : " + e.getMessage());
        }
        return list;
    }

    @Override
    // 商品兑换
    public List<Map<String, Object>> exchangeList(String uid, String pageIndex, String pSize) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("UID", uid);
        paramMap.put("PAGEINDEX", StrUtils.isEmpty(pageIndex) ? "1" : pageIndex);
        paramMap.put("PSIZE", StrUtils.isEmpty(pSize) ? "20" : pSize);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_shop.exchangeList", paramMap);

        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.exchangeList failed, e : " + e.getMessage());
        }
        return list;
    }

    @Override
    // 商品首页广告
    public List<Map<String, Object>> advert() {
        List<Map<String, Object>> list = new ArrayList();
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_shop.advert");
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.advert failed, e : " + e.getMessage());
        }
        return list;
    }

    /**
     * 跳转到事物订单页
     *
     * @param pid
     * @return
     */
    @Override
    public List<Map<String, Object>> matterToOrder(String uid,String pid) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("pid", pid);
        paramMap.put("uid", uid);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_shop.matterToOrder", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.matterToOrder failed, e : " + e.getMessage());
        }
        return list;
    }

    /**
     * 根据商户订单号查询订单
     *
     * @param out_trade_no 商户订单号
     * @return
     */
    @Override
    public List<Map<String, Object>> getOrderByTradeNo(String out_trade_no) {
        return null;
    }

    /**
     * 查询商品详情
     *
     * @param pid
     * @return
     */
    @Override
    public List<Map<String, Object>> getComboById(String uid,String pid) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("pid", pid);
        paramMap.put("uid", uid);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_shop.matterToOrder", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.getComboById failed, e : " + e.getMessage());
        }
        return list;
    }

    /**
     * 生成新订单
     *  @param out_trade_no
     * @param c_score_cost
     * @param c_ctime
     * @param c_pay_status
     * @param c_pay_type
     * @param phone
     * @param account
     * @param address
     * @param c_pid
     * @param c_type
     * @param pCode
     * @param pName
     */
    @Override
    public void insertOrder(String out_trade_no, String price, String c_score_cost, String c_ctime, String c_pay_status, String c_pay_type, String phone, String account, String address, String c_pid, String c_type, String uid, String num, String pCode, String pName) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("C_TRADE_NO", out_trade_no);
            paramMap.put("C_SCORE_COST", c_score_cost);
            paramMap.put("C_CTIME", c_ctime);
            paramMap.put("C_PAY_STATUS", c_pay_status);
            paramMap.put("C_PAY_TYPE", c_pay_type);
            paramMap.put("C_RECV_NAME", account);
            paramMap.put("C_RECV_PHONE", phone);
            paramMap.put("C_RECV_ADDR", address);
            paramMap.put("C_TYPE", c_type);
            paramMap.put("C_UID", uid);
            paramMap.put("C_PID", c_pid);
            paramMap.put("C_AMOUNT", num);
            paramMap.put("C_CARRIAGE", price);
            paramMap.put("C_PCODE", pCode);
            paramMap.put("C_PNAME", pName);
            getSqlMapClientTemplate().update("ek_shop.insertOrder", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.insertOrder failed, e : ", e);
        }
    }

    /**
     * 修改支付状态
     */
    @Override
    public Map<String, String> updatePayStatus(Map<String, String> parameterMap, String payType, String payStatus) {
        Map<String, String> result = new HashMap<String, String>();
        String return_code = "FAIL";
        String return_msg = "系统错误";
        // 订单号
        String out_trade_no = parameterMap.get("out_trade_no");
        final String sql = "SELECT T.C_PAY_STATUS, T.C_SCORE_COST,T.C_UID,T.C_PID,T.C_TYPE,C_RECV_NAME,C_RECV_PHONE,C_RECV_ADDR,C_CTIME,C_PCODE FROM T_EK_MALL_ORDER T WHERE T.C_TRADE_NO = ?";

        final String updateSql = "UPDATE T_EK_MALL_ORDER SET C_PAY_STATUS = ? WHERE C_TRADE_NO = ?";

        final String updateNum = " UPDATE T_EK_MALL_PRODUCT SET C_TOTAL=C_TOTAL+1 WHERE  C_ID=?";

        final String updateScroe = "UPDATE T_EK_MEMBER_SCORE T SET T.C_SCORE=T.C_SCORE + ? WHERE  T.C_UID = ?";
        synchronized (jdbcTemplate) {
            try {
                Map<String, Object> objectMap = jdbcTemplate.queryForMap(sql, new Object[]{out_trade_no});
                // 订单支付已成功
                if ("1".equals(objectMap.get("C_PAY_STATUS"))) {
                    return_code = "SUCCESS";
                    return_msg = "OK";
                } else {
                    // 更新订单状态
                    jdbcTemplate.update(updateSql, new Object[]{payStatus,out_trade_no});
                    //状态修改成功
//                if ("1".equals(payType)) {
//                    logWeChartPay(parameterMap);
//                } else if ("2".equals(payType)) {
//                    logAlipayNotify(parameterMap);
//                }
                    if ("1".equals(payStatus)) {//支付状态  1
                        //添加兑换记录表
                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("C_PID", objectMap.get("C_PID"));
                        paramMap.put("C_UID", objectMap.get("C_UID"));
                        paramMap.put("C_TRADE_NO", out_trade_no);
                        paramMap.put("C_SCORE", objectMap.get("C_SCORE_COST"));
                        paramMap.put("C_TYPE", objectMap.get("C_TYPE"));
                        getSqlMapClientTemplate().update("ek_shop.insertExchangeOrder", paramMap);
                        /**
                         *  如果商品类型是3（手机膜），则推送至ERP
                         */
                        try {
                            EKMallOrder order = new EKMallOrder();
                            order.setTrade_no("M" + out_trade_no);
                            order.setRecv_phone(StrUtils.emptyOrString(objectMap.get("C_RECV_PHONE")));
                            order.setRecv_name(StrUtils.emptyOrString(objectMap.get("C_RECV_NAME")));
                            order.setRecv_addr(StrUtils.emptyOrString(objectMap.get("C_RECV_ADDR")));
                            order.setExpress_code("ZTO");
                            order.setCtime(StrUtils.emptyOrString(objectMap.get("C_CTIME")));
                            order.setPcode(StrUtils.emptyOrString(objectMap.get("C_PCODE")));
                            erpInfo.addMallOrder(order);
                        } catch (Exception e) {
                            LOGGER.error("推送ERP失败！订单号：" + out_trade_no);
                        }

                        return_code = "SUCCESS";
                        return_msg = "OK";
                    } else {
                        //修改 商品数量
                        //修改积分
                        jdbcTemplate.update(updateScroe, new Object[]{objectMap.get("C_SCORE_COST"), objectMap.get("C_UID")});
                        jdbcTemplate.update(updateNum, new Object[]{objectMap.get("C_PID")});
                        return_code = "FAIL";
                        return_msg = "支付失败";
                    }

                }
            }catch (Exception e){
                LOGGER.error(e.getMessage());
            }

        }

        result.put("return_code", return_code);
        result.put("return_msg", return_msg);

        return result;
    }

    /**
     * 支付宝回调记录
     *
     * @param params
     */
    private void logAlipayNotify(Map<String, String> params) {
        try {
            getSqlMapClientTemplate().update("ek_shop.logAlipayNotify", params);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.logAlipayNotify failed, e : ", e);
        }
    }

    /**
     * 记录微信通知记录
     *
     * @param parameterMap
     */
    @Override
    public void logWeChartPay(Map<String, String> parameterMap) {
        String err_code = parameterMap.get("err_code");
        String out_trade_no = parameterMap.get("out_trade_no");
        getSqlMapClientTemplate().insert("ek_shop.insertWeChartPayLog", parameterMap);

        /*if (!StringUtils.isEmpty(err_code)) {
            final String updateSql = "UPDATE T_EK_MALL_ORDER SET C_PAY_STATUS = '1' WHERE C_TRADE_NO = ?";
            jdbcTemplate.update(updateSql, new Object[]{out_trade_no});
        }*/
    }

    /**
     * 查询会员的默认地址信息
     *
     * @param uid
     * @return
     */
    @Override
    public List<Map<String, Object>> address(String uid) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("uid", uid);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_shop.address", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.address failed, e : " + e.getMessage());
        }
        return list;
    }

    /**
     * 修改商品数量
     *
     * @param pid
     * @return
     */
    @Override
    public int updateProductNum(String pid) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("pid", pid);
        return getSqlMapClientTemplate().update("ek_shop.updateProductNum", paramMap);
    }

    /**
     * 可用的商品兑换码个数
     *
     * @param pid
     * @return
     */
    @Override
    public int getCount(String pid) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("pid", pid);
        int count = 0;
        try {
            count = (int) this.getSqlMapClientTemplate().queryForObject("ek_shop.codeCount", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.inviteAward failed, e : " + e.getMessage());
        }
        return count;
    }

    /**
     * 查询 用户总积分
     *
     * @param uid
     * @return
     */
    @Override
    public String selectUserScore(String uid) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("uid", uid);
        String score = "";
        try {
            score = (String) this.getSqlMapClientTemplate().queryForObject("ek_shop.selectUserScore", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.selectUserScore failed, e : " + e.getMessage());
        }
        return score;
    }


    @Override
    @Transactional(rollbackFor = EKShopException.class)// 如果抛出自定义异常，则回滚
    // 生成虚拟商品订单
    public boolean virtualOrder(String uid, List<Map<String, Object>> product, String amount)
            throws ProductOutOfStockException, OrderException, UserScoreException {
        if (StrUtils.isEmpty(product)) {
            return false;
        }
        String tradeNo = OrderUtil.outTradeNo();// 订单号
        Integer intAmount = Integer.valueOf(amount);
        Map<String, Object> pro = product.get(0);
        String pid = StrUtils.emptyOrString(pro.get("C_ID"));
        String cost = StrUtils.emptyOrString(pro.get("C_COST"));

        List<Object[]> codeList = new ArrayList<>();
        List<Object[]> exchangeArgs = new ArrayList<>();

        // 查询总数
        String countSql = "SELECT COUNT(1) FROM T_EK_MALL_PRO_CODE WHERE C_PID = ? AND C_STATE = 0 AND C_ISLIVE = 1";
        // 查询所有兑换码
        String allCodeSql = "SELECT * FROM(SELECT ROWNUM RN, TEMP.* FROM" +
                " (SELECT * FROM T_EK_MALL_PRO_CODE WHERE C_PID = ? AND C_STATE = 0 AND C_ISLIVE = 1 ORDER BY C_CODE)" +
                " TEMP WHERE ROWNUM < ?) WHERE RN >= ?";

        // 更新一条兑换码状态为“已兑换”
        String updateSql = "UPDATE T_EK_MALL_PRO_CODE T SET T.C_STATE = 1 WHERE T.C_STATE = 0 AND T.C_CODE = ?";

        // 查询可用激活码总数
        Integer total = jdbcTemplate.queryForObject(countSql, new Object[]{pid}, Integer.class);
        //
        int len = 1;
        boolean whileFlag = true;
        while (whileFlag && len < total) {
            // 每次查询50条兑换码
            List<Map<String, Object>> codes = jdbcTemplate.queryForList(allCodeSql, new Object[]{pid, len + 50, len});
            // 循环查询结果
            // 更新n条数据
            for (int j = 0; whileFlag && j < codes.size(); j++) {
                Map<String, Object> codeMap = codes.get(j);
                String code = StrUtils.emptyOrString(codeMap.get("C_CODE"));
                int changedRow = jdbcTemplate.update(updateSql, new Object[]{code});
                if (changedRow == 1) {
                    // 如果更新成功，记录这条兑换码
                    codeList.add(new Object[]{code});
                    exchangeArgs.add(new Object[]{pid, uid, tradeNo, code, cost});
                }
                if (codeList.size() == intAmount) {
                    whileFlag = false;// 退出while循环
                }
            }
            len += ((total - len) > 50) ? 50 : (total - len);
        }
        if (codeList.size() < intAmount) {
            // 如果更新成功的数量比要兑换的数量少，回滚
            throw new ProductOutOfStockException(EKShopExceptionConst.Ex_2001);
        } else {
            Object[] orderArgs = {tradeNo, StrUtils.zeroOrInt(cost) * intAmount, uid, pid, intAmount};
            Object[] userScoreArgs = {StrUtils.zeroOrInt(cost) * intAmount, uid, StrUtils.zeroOrInt(cost) * intAmount};
            // 生成订单
            String orderSql = "INSERT INTO T_EK_MALL_ORDER " +
                    " (C_ID, C_TRADE_NO, C_SCORE_COST, C_CTIME, C_PAY_STATUS, C_PAY_TYPE, C_TYPE, C_UID, C_PID, C_AMOUNT)" +
                    " VALUES (SEQ_EK_MALL_ORDER.NEXTVAL, ?, ?, SYSDATE, 1, 3, 1, ?, ?, ?)";

            // 兑换记录
            String exchangeSql = "INSERT INTO T_EK_MALL_PRO_EXCHANGE" +
                    " (C_ID, C_PID, C_UID, C_TRADE_NO, C_PCODE, C_TIME, C_SCORE, C_TYPE)" +
                    " VALUES (SEQ_EK_MALL_PRO_EXCHANGE.NEXTVAL, ?, ?, ?, ?, SYSDATE, ?, 1)";

            // 更新用户积分
            String userScoreSql = "UPDATE T_EK_MEMBER_SCORE T SET" +
                    " T.C_SCORE = T.C_SCORE - ?, T.C_DATE = SYSDATE WHERE T.C_UID = ? AND T.C_SCORE >= ?";
            try {
                // 如果有一个没有执行成功，回滚

                int effectRow1 = jdbcTemplate.update(orderSql, orderArgs);
                if (effectRow1 != 1) {
                    throw new OrderException(EKShopExceptionConst.Ex_2002);
                }
                // 新增兑换记录
                int[] effectRow2 = jdbcTemplate.batchUpdate(exchangeSql, exchangeArgs);
                if (effectRow2.length != exchangeArgs.size()) {
                    throw new OrderException(EKShopExceptionConst.Ex_2002);
                }
                // 更新积分
                int effectRow3 = jdbcTemplate.update(userScoreSql, userScoreArgs);
                if (effectRow3 != 1) {
                    throw new UserScoreException(EKShopExceptionConst.Ex_2003);
                }
            } catch (DataAccessException e) {
                throw e;
            }
            return true;
        }
    }

    /**
     * 修改用户积分
     *
     * @param uid
     * @param score
     */
    @Override
    public int updateUserScore(String uid, String score) {
        String updateSql = "UPDATE T_EK_MEMBER_SCORE T SET T.C_SCORE=T.C_SCORE -? WHERE  T.C_UID = ? AND T.C_SCORE>=?";
        int changedRow = jdbcTemplate.update(updateSql, new Object[]{score, uid,score});
        return changedRow;
    }

    /**
     * 订单表查询收货信息
     *
     * @param uid
     * @return
     */
    @Override
    public List<Map<String, Object>> isExit(String uid) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("uid", uid);
        try {
            return   this.getSqlMapClientTemplate().queryForList("ek_shop.isExit", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.isExit failed, e : " + e.getMessage());
        }
        return null;
    }

    /**
     * 兑换记录
     *
     * @param out_trade_no
     * @return
     */
    @Override
    public List<Map<String, Object>> changeResult(String out_trade_no) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("out_trade_no", out_trade_no);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_shop.changeResult", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("ShopServiceImpl.changeResult failed, e : " + e.getMessage());
        }
        return list;
    }
}
