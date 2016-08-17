package com.sys.ekey.rebate.service.impl;

import com.sys.ekey.rebate.service.RebateService;
import com.sys.util.MD5;
import com.sys.util.StrUtils;
import com.sys.util.file.FileServices;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/7/25.
 */
@Service
public class RebateServiceImpl extends SqlMapClientDaoSupport implements RebateService {

    private static Logger LOGGER = Logger.getLogger(RebateServiceImpl.class);
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    // 我的财富
    public List<Map<String, Object>> myWealth(String uid) {
        List<Map<String, Object>> list = new ArrayList();
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_rebate.myWealth", uid);
        } catch (DataAccessException e) {
            LOGGER.error("RebateServiceImpl.myWealth failed, e : " + e.getMessage());
        }
        return list;
    }

    @Override
    // 财富明细
    public List<Map<String, Object>> wealthDetail(String uid) {
        List<Map<String, Object>> list = new ArrayList();
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_rebate.wealthDetail", uid);
        } catch (DataAccessException e) {
            LOGGER.error("RebateServiceImpl.wealthDetail failed, e : " + e.getMessage());
        }
        return list;
    }

    @Override
    // 财富明细（按月统计）
    public List<Map<String, Object>> wealthDetailByMonth(String uid, String type) {
        List<Map<String, Object>> list = new ArrayList();
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("UID", uid);
            param.put("TYPE", type);
            list = this.getSqlMapClientTemplate().queryForList("ek_rebate.wealthDetailByMonth", param);
        } catch (DataAccessException e) {
            LOGGER.error("RebateServiceImpl.wealthDetailByMonth failed, e : " + e.getMessage());
        }
        return list;
    }

    @Override
    // 我的人脉
    public List<Map<String, Object>> myReference(String uid) {
        List<Map<String, Object>> list = new ArrayList();
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_rebate.myReference", uid);
        } catch (DataAccessException e) {
            LOGGER.error("RebateServiceImpl.myReference failed, e : " + e.getMessage());
        }
        return list;
    }

    @Override
    // 龙虎榜
    public List<Map<String, Object>> rankList(String type) {
        List<Map<String, Object>> list = new ArrayList();
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("TYPE", type);
            list = this.getSqlMapClientTemplate().queryForList("ek_rebate.rankList", param);
        } catch (DataAccessException e) {
            LOGGER.error("RebateServiceImpl.rankList failed, e : " + e.getMessage());
        }
        return list;
    }

    @Override
    // 提现明细
    public List<Map<String, Object>> withdrawDetail(String uid) {
        List<Map<String, Object>> list = new ArrayList();
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("UID", uid);
            list = this.getSqlMapClientTemplate().queryForList("ek_rebate.withdrawDetail", param);
        } catch (DataAccessException e) {
            LOGGER.error("RebateServiceImpl.withdrawDetail failed, e : " + e.getMessage());
        }
        return list;
    }

    @Override
    // 申请提现
    public String withdraw(String uid, String amount, String pay_type, String pay_account, String withdraw_pwd) {
        String result = "";
        try {

            Map<String, Object> param = new HashMap<>();
            param.put("UID", uid);
            param.put("WITHDRAW_PWD", MD5.md5Code(withdraw_pwd));


            // 校验密码是否正确
            String queryResult = (String) this.getSqlMapClientTemplate().queryForObject("ek_rebate.correctWithdrawPwd", param);
            if (StrUtils.isEmpty(queryResult)) {
                result = "3";
            } else if (queryResult.equals(MD5.md5Code(withdraw_pwd))) {
                param.put("C_NUMBER", System.currentTimeMillis());
                param.put("AMOUNT", amount);
                param.put("PAY_TYPE", pay_type);
                param.put("PAY_ACCOUNT", pay_account);
                param.put("C_STATUS", "0");
                int insertResult = this.getSqlMapClientTemplate().update("ek_rebate.withdraw", param);
                if (insertResult > 0) {
                    result = "1";
                } else {
                    result = "2";
                }
            } else {
                result = "0";
            }

        } catch (DataAccessException e) {
            LOGGER.error("RebateServiceImpl.withdraw failed, e : " + e.getMessage());
        }
        return result;
    }

    @Override
    public String authId(String uid, String id_num) {
        String result = "";
        try {

            Map<String, Object> param = new HashMap<>();
            param.put("UID", uid);
            param.put("ID_NUM", id_num);


            // 校验密码是否正确
            Integer queryResult = (Integer) this.getSqlMapClientTemplate().queryForObject("ek_rebate.authId", param);
            if (queryResult == 0) {
                result = "0";
            } else {
                result = "1";
            }

        } catch (DataAccessException e) {
            LOGGER.error("RebateServiceImpl.authId failed, e : " + e.getMessage());
        }
        return result;
    }

    @Override
    // 保存商户信息
    public String saveBusinessInfo(Map<String, Object> param, String[] license_img, String[] shop_img) {
        String result = "";
        try {

            String dest_license = "ek-user/business-img/" + param.get("C_ID") + "/license/" + System.currentTimeMillis() + ".png";
            String s_license = new String(Base64.decodeBase64(license_img[0].getBytes())).replaceAll(" ", "").replaceAll("%3D", "=");
            String path_license = FileServices.saveFile(new ByteArrayInputStream(Base64.decodeBase64(s_license)), dest_license);
            param.put("C_LICENSE_IMG", path_license);

            String dest_shop = "ek-user/business-img/" + param.get("C_ID") + "/shop/" + System.currentTimeMillis() + ".png";
            String s_shop = new String(Base64.decodeBase64(shop_img[0].getBytes())).replaceAll(" ", "").replaceAll("%3D", "=");
            String path_shop = FileServices.saveFile(new ByteArrayInputStream(Base64.decodeBase64(s_shop)), dest_shop);
            param.put("C_SHOP_IMG", path_shop);

            Integer updateResult = this.getSqlMapClientTemplate().update("ek_rebate.saveBusinessInfo", param);
            if (updateResult == 0) {
                result = "0";
            } else {
                result = "1";
            }

        } catch (DataAccessException e) {
            LOGGER.error("RebateServiceImpl.saveBusinessInfo failed, e : ", e);
        }
        return result;
    }

    @Override
    // 获取商户信息
    public List<Map<String, Object>> businessInfo(String uid) {
        List<Map<String, Object>> list = new ArrayList();
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_rebate.businessInfo", uid);
        } catch (DataAccessException e) {
            LOGGER.error("RebateServiceImpl.businessInfo failed, e : " + e.getMessage());
        }
        return list;
    }

    @Override
    // 申请售后屏保
    public String activeInsurance(Map<String, Object> param, String[] img) {
        String result = "";
        try {

            String dest = "ek-user/insurance/" + param.get("C_UID") + "/active/" + System.currentTimeMillis() + ".png";
            String s = new String(Base64.decodeBase64(img[0].getBytes())).replaceAll(" ", "").replaceAll("%3D", "=");
            String path = FileServices.saveFile(new ByteArrayInputStream(Base64.decodeBase64(s)), dest);

            param.put("C_IMG", path);

            Integer updateResult = this.getSqlMapClientTemplate().update("ek_rebate.activeInsurance", param);
            if (updateResult == 0) {
                result = "0";
            } else {
                result = "1";
            }
            // 如果type为1，则说明用户已经收到碎屏险保险金。再次申请屏保，则用户的激活码需要更新
            if ("1".equals(param.get("type"))) {
                // 查询激活码是否可用
                List<Map<String, Object>> list = this.getSqlMapClientTemplate().queryForList("ek_rebate.findProductSerial", param);
                // 如果可用，更新用户的新激活码
                if (StrUtils.isNotEmpty(list)) {
                    // 查询是否激活过
                    list = this.getSqlMapClientTemplate().queryForList("soft_sql1.FindCode", param);
                    if (StrUtils.isEmpty(list)) {
                        int update = this.getSqlMapClientTemplate().update("ek_rebate.updateUsersActiveCode", param);
                        if (1 == update) {
                            result = String.valueOf(this.getSqlMapClientTemplate().update("ek_rebate.updateActiveCodeStatus", param));
                        }
                    } else {
                        result = "2";
                    }
                } else {
                    result = "2";
                }
            }
        } catch (DataAccessException e) {
            LOGGER.error("RebateServiceImpl.applyForInsurance failed, e : ", e);
        }
        return result;
    }

    @Override
    // 查询是否已激活售后屏保
    public Map<String, Object> isActed(Map<String, Object> reqMap, String uid, String code) {

        String result = "";
        try {

            Map<String, Object> param = new HashMap<>();
            param.put("UID", uid);
            param.put("CODE", code);

            // 查询激活售后表主键
            List<Map<String, Object>> list = this.getSqlMapClientTemplate().queryForList("ek_rebate.isActed", param);
            if (StrUtils.isEmpty(list)) {
                result = "4";// 如果为空，表示没有申请过
            } else {
                result = StrUtils.emptyOrString(list.get(0).get("C_STATUS"));// 0.申请中 1.审核成功 2.审核失败 3.已发放保险金
                reqMap.put("validity", list.get(0).get("C_VALIDITY"));
            }

        } catch (DataAccessException e) {
            LOGGER.error("RebateServiceImpl.isActed failed, e : " + e.getMessage());
        }
        reqMap.put("result", result);
        return reqMap;
    }

    @Override
    // 申请维修
    public String applyForRepair(String uid, String code, String[] img) {
        String result = "";
        try {

            Map<String, Object> param = new HashMap<>();
            param.put("UID", uid);
            param.put("CODE", code);

            // 查询激活码对应的“售后屏保”的主键
            List<Map<String, Object>> list = this.getSqlMapClientTemplate().queryForList("ek_rebate.isActed", param);

            if (StrUtils.isNotEmpty(list)) {
                param.put("C_ACT_ID", list.get(0).get("C_ID"));

                String dest = "ek-user/insurance/" + uid + "/repair/" + System.currentTimeMillis() + ".png";
                String s = new String(Base64.decodeBase64(img[0].getBytes())).replaceAll(" ", "").replaceAll("%3D", "=");
                String path = FileServices.saveFile(new ByteArrayInputStream(Base64.decodeBase64(s)), dest);

                param.put("C_IMG", path);

                Integer updateResult = this.getSqlMapClientTemplate().update("ek_rebate.applyForRepair", param);
                if (updateResult == 0) {
                    result = "0";// 申请失败
                } else {
                    result = "1";// 申请成
                }
            } else {
                result = "2";// 未激活“售后屏保”
            }


        } catch (DataAccessException e) {
            LOGGER.error("RebateServiceImpl.applyForInsurance failed, e : ", e);
        }
        return result;
    }

    @Override
    // 申请明细
    public Map<String, Object> applyDetail(String uid, String code) {
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        List<Map<String, Object>> repair = new ArrayList<>();
        List<Map<String, Object>> active;
        param.put("C_UID", uid);
        param.put("C_CODE", code);


        // 查询是否有正在“审核中的”申请
        param.put("C_STATUS", "0");
        active = this.getSqlMapClientTemplate().queryForList("ek_rebate.activeDetail", param);

        // 如果active不为空，说明正在申请，不可能有“申请维修”的数据；如果不为空，则查询所有"申请激活"的数据
        if (StrUtils.isEmpty(active)) {
            param.put("C_STATUS", "1");
            active = this.getSqlMapClientTemplate().queryForList("ek_rebate.activeDetail", param);
            if (StrUtils.isNotEmpty(active)) {
                String c_id = StrUtils.emptyOrString(active.get(0).get("C_ID"));
                // 查询"申请维修"的明细
                param.put("C_ACT_ID", c_id);
                repair = this.getSqlMapClientTemplate().queryForList("ek_rebate.repairDetail", param);
            }
        }

        reqMap.put("active", active);
        reqMap.put("repair", repair);
        return reqMap;
    }

    /**
     * @param uid
     * @param price
     * @param state    299,199,0
     * @param C_SOURCE
     */
    public void selectUid(String uid, String price, String state, String C_SOURCE) {
        //查询1/2/3级邀请人
        final String sql = " SELECT T.C_REFER_ID,T.STATUS,TEMP.C_RANK" +
                " FROM (" +
                " SELECT C_REFER_ID,CASE WHEN C_REFER_ID IS NULL THEN NULL ELSE '1' END AS STATUS " +
                " FROM T_EK_MEMBER_INFO T WHERE C_ID=?" +
                " UNION" +
                " SELECT C_REFER_ID, CASE WHEN C_REFER_ID IS NULL THEN NULL ELSE '2' END AS STATUS" +
                " FROM T_EK_MEMBER_INFO WHERE C_ID = (SELECT C_REFER_ID FROM T_EK_MEMBER_INFO WHERE C_ID=?)" +
                " UNION" +
                " SELECT C_REFER_ID,CASE WHEN C_REFER_ID IS NULL THEN NULL ELSE '3'  END AS STATUS" +
                " FROM T_EK_MEMBER_INFO WHERE C_ID = ( SELECT C_REFER_ID FROM T_EK_MEMBER_INFO" +
                " WHERE C_ID = (SELECT C_REFER_ID FROM T_EK_MEMBER_INFO WHERE C_ID=?)) " +
                " ) T LEFT JOIN T_EK_MEMBER_PREVILEGE TEMP ON TEMP.C_UID=T.C_REFER_ID" +
                " WHERE T.C_REFER_ID IS NOT NULL AND T.STATUS IS NOT NULL AND TEMP.C_RANK IS NOT NULL";
        final String ownSql="SELECT T.C_REFER_ID,T.STATUS,TEMP.C_RANK FROM (" +
                " SELECT '"+uid+"' AS C_REFER_ID,'0' AS STATUS FROM DUAL" +
                " ) T" +
                " LEFT JOIN T_EK_MEMBER_PREVILEGE TEMP ON TEMP.C_UID=T.C_REFER_ID" +
                " WHERE T.C_REFER_ID IS NOT NULL AND T.STATUS IS NOT NULL AND TEMP.C_RANK IS NOT NULL";
        //查询不同级别返利率
        final String selectRate = "SELECT C_RATE,C_RANK,C_RATE_FY,C_RATE_TBK FROM T_EK_MEMBER_RATE WHERE C_ISLIVE=1";
        final String selectAuth = "SELECT C_RANK FROM T_EK_MEMBER_PREVILEGE WHERE C_UID =?";
        final String insertAuth = "INSERT INTO T_EK_MEMBER_PREVILEGE(C_ID,C_UID,C_RANK) VALUES (SEQ_EK_MEMBER_PREVILEGE.nextval,?,?) ";
        final String updateAuth = "UPDATE T_EK_MEMBER_PREVILEGE SET C_RANK=? WHERE  C_UID=?";
        final String selectFyId = "SELECT C_FYACCOUNTID FROM T_FY_USER " +
                " WHERE C_GLOBALMOBILEPHONE=" +
                "(SELECT C_PHONENUM FROM T_EK_MEMBER_INFO WHERE C_ID=?)";
        final String insertVip = "INSERT INTO T_FY_USER_TIME_VIP(C_ID,C_FYACCOUNTID) VALUES (SEQ_FY_USER_TIME_VIP.nextval,?) ";
        final String updateFyTime = "UPDATE T_FY_USER_TIME SET C_TIME_TEMP=C_TIME_TEMP+1290 WHERE  EXTRACT(YEAR FROM C_VALIDITY)='9999' AND C_FYACCOUNTID=?";
        final String selectVip = "SELECT * FROM T_FY_USER_TIME_VIP WHERE C_FYACCOUNTID=? AND C_ISLIVE=1";
        final String updateVip = "UPDATE T_FY_USER_TIME_VIP SET C_VALIDATE_DATE=ADD_MONTHS(C_VALIDATE_DATE, 12) WHERE C_FYACCOUNTID=?";
        List<Map<String, Object>> reteList = jdbcTemplate.queryForList(selectRate);//利率表查询

        if (StrUtils.isNotEmpty(uid) && StrUtils.isNotEmpty(C_SOURCE)) {
            List<Map<String, Object>> userList = jdbcTemplate.queryForList(sql, new Object[]{uid, uid, uid});//返利用户表
            List<Map<String,Object>> ownList=jdbcTemplate.queryForList(ownSql);
            Map _map=new HashMap();
            if (StrUtils.isNotEmpty(ownList)){
                for(Map map:ownList){
                    _map =map;
                        }
            }
            if(StrUtils.isNotEmpty(_map)){
                userList.add(_map);
            }
            List<Map<String, Object>> authMap = jdbcTemplate.queryForList(selectAuth, new Object[]{uid});//自己返利权限
            /******************添加权限,添加免费通话时长********************/
            /*if (!"0".equals(state)) {
                //List<Map<String, Object>> fyMap = jdbcTemplate.queryForList(selectFyId, new Object[]{uid});//飞语id
                if (StrUtils.isEmpty(authMap)) {
                    //没权限添加
                    int c_rank = 3;
                    //if ("2".equals(state)) {//添加vip
                    //  c_rank = 3;
                    //  jdbcTemplate.update(insertVip, new Object[]{fyMap.get(0).get("C_FYACCOUNTID")});//添加299 vip
                    // } else if ("1".equals(state)) {//添加1000通话时长
                    *//*if ("1".equals(state)) {//三级返利 1000通话时长
                        c_rank = 3;
                        //jdbcTemplate.update(updateFyTime, new Object[]{fyMap.get(0).get("C_FYACCOUNTID")});//修改飞语永久时长
                    }*//*
                    jdbcTemplate.update(insertAuth, new Object[]{uid, c_rank});//添加个人权限
                } else {//修改永久时长
                   // jdbcTemplate.update(updateFyTime, new Object[]{fyMap.get(0).get("C_FYACCOUNTID")});//修改飞语永久时长
                   *//* List<Map<String, Object>> vipMap = jdbcTemplate.queryForList(selectVip, new Object[]{fyMap.get(0).get("C_FYACCOUNTID")});//查询是否有vip充值
                    int C_RANK = StrUtils.str2Int(StrUtils.emptyOrString(authMap.get(0).get("C_RANK")));
                    if (C_RANK == 1) {
                        jdbcTemplate.update(updateAuth, new Object[]{3, uid});//更新权限
                    }
                    if ("2".equals(state)) {
                        if (StrUtils.isEmpty(vipMap)) {//添加vip时长
                            jdbcTemplate.update(insertVip, new Object[]{fyMap.get(0).get("C_FYACCOUNTID")});//添加299 vip
                        } else {//更新vip时长
                            jdbcTemplate.update(updateVip, new Object[]{fyMap.get(0).get("C_FYACCOUNTID")});//更新299 vip
                        }
                    } else if ("1".equals(state)) {//修改1000分钟的永久时长
                        jdbcTemplate.update(updateFyTime, new Object[]{fyMap.get(0).get("C_FYACCOUNTID")});//修改飞语永久时长
                    }*//*

                }
            }*/

            if ("01".equals(C_SOURCE)) {//H5购膜/
                // /*添加权限*//*
                int c_rank = 3;
                if (StrUtils.isEmpty(authMap)) {
                    jdbcTemplate.update(insertAuth, new Object[]{uid, c_rank});//添加个人3级权限
                }
            }
            //查询返利权限，开始返利
            selectRate(userList, reteList, price, C_SOURCE, uid);
        }
    }

    public void selectRate(List<Map<String, Object>> userList, List<Map<String, Object>> reteList, String price, String C_SOURCE, String uid) {
        for (Map user_map : userList) {//用户的返利列表
            if ("1".equals(StrUtils.emptyOrString(user_map.get("STATUS")))) {//用户一级返利
                if ("3".equals(StrUtils.emptyOrString(user_map.get("C_RANK")))) {//一级用户拥有返利权限
                    for (Map rate_map : reteList) {
                        if (user_map.get("STATUS").equals(rate_map.get("C_RANK"))) {
                            checkUserMess(user_map, rate_map, price, C_SOURCE, uid);
                        }
                    }
                }
            } else if ("2".equals(StrUtils.emptyOrString(user_map.get("STATUS")))) {//用户二级返利
                if ("3".equals(StrUtils.emptyOrString(user_map.get("C_RANK")))) {//二级用户拥有返利权限
                    for (Map rate_map : reteList) {
                        if (user_map.get("STATUS").equals(rate_map.get("C_RANK"))) {
                            checkUserMess(user_map, rate_map, price, C_SOURCE, uid);
                        }
                    }
                }
            } else if ("3".equals(StrUtils.emptyOrString(user_map.get("STATUS")))) {//用户三级返利
                if ("3".equals(StrUtils.emptyOrString(user_map.get("C_RANK")))) {//三级用户拥有返利权限
                    for (Map rate_map : reteList) {
                        if (user_map.get("STATUS").equals(rate_map.get("C_RANK"))) {
                            checkUserMess(user_map, rate_map, price, C_SOURCE, uid);
                        }
                    }
                }
            } else if ("0".equals(StrUtils.emptyOrString(user_map.get("STATUS")))) {//自己返利    只有淘宝客执行
                if ("03".equals(C_SOURCE)) {
                    if ("3".equals(StrUtils.emptyOrString(user_map.get("C_RANK")))) {//三级用户拥有返利权限
                        for (Map rate_map : reteList) {
                            if (user_map.get("STATUS").equals(rate_map.get("C_RANK"))) {
                                checkUserMess(user_map, rate_map, price, C_SOURCE, uid);
                            }
                        }
                    }
                }
            }
        }
        List<Map<String, Object>> agentList = selectNextAgent(uid);
        if (StrUtils.isNotEmpty(agentList.get(0).get("C_AGENT_ID"))) {
            //总代理返现
            String C_AGENT_ID = StrUtils.emptyOrString(agentList.get(0).get("C_AGENT_ID"));
            /*总代理查询*/
            List<Map<String, Object>> topAgentList = selectTopAgent(C_AGENT_ID);
            String C_LOGIN_ID = StrUtils.emptyOrString(topAgentList.get(0).get("C_LOGIN_ID"));
            if(StrUtils.isNotEmpty(C_LOGIN_ID)){
                insertTopAgent(reteList, uid, C_LOGIN_ID, C_SOURCE, price,"4");//顶级添加返现
            }
            if(StrUtils.isNotEmpty(C_AGENT_ID)){
                //次级代理返现
                insertTopAgent(reteList, uid, C_AGENT_ID, C_SOURCE, price,"5");//次级代理商返现
            }
        }
        //指电后台返现
        insertTopAgent(reteList, uid, "ZD", C_SOURCE, price,"6");
    }

    /**
     * 顶级代理返现
     */
    public void insertTopAgent(List<Map<String,Object>> reteList,String uid,String C_LOGIN_ID,String C_SOURCE,String price,String state) {
        String sql="INSERT INTO T_EK_MEMBER_TOP_WEALTH(C_ID,C_UID,C_AMOUNT,C_CHANGE,C_REFER_ID,C_LEVEL,C_SOURCE) VALUES (SEQ_EK_MEMBER_TOP_WEALTH.nextval,?,?,?,?,?,?) ";
        String sql1="INSERT INTO T_EK_MEMBER_WEALTH(C_ID,C_UID,C_AMOUNT,C_CHANGE,C_REFER_ID,C_LEVEL,C_SOURCE) VALUES (SEQ_EK_MEMBER_WEALTH.nextval,?,?,?,?,?,?) ";
        String sql2="INSERT INTO T_EK_MEMBER_ZD_WEDLTH(C_ID,C_UID,C_AMOUNT,C_CHANGE,C_REFER_ID,C_LEVEL,C_SOURCE) VALUES (SEQ_EK_MEMBER_ZD_WEDLTH.nextval,?,?,?,?,?,?) ";
        double C_RATE = 0;
        String C_AMOUNT = "0";
        for(Map rate_map :reteList){
        if(state.equals(StrUtils.emptyOrString(rate_map.get("C_RANK")))){
            if ("01".equals(C_SOURCE)) {//免费换膜
                C_RATE = StrUtils.str2Double(StrUtils.emptyOrString(rate_map.get("C_RATE")));
                DecimalFormat df = new DecimalFormat("0.00");// 订单金额
                C_AMOUNT = df.format(C_RATE);
            } else if ("02".equals(C_SOURCE)) {//免费通话充值
               C_RATE = StrUtils.str2Double(StrUtils.emptyOrString(rate_map.get("C_RATE_FY")));
                DecimalFormat df = new DecimalFormat("0.00");// 订单金额
                C_AMOUNT = df.format(Double.valueOf(price) * C_RATE);
            } else if ("03".equals(C_SOURCE)) {//淘宝客
                C_RATE = StrUtils.str2Double(StrUtils.emptyOrString(rate_map.get("C_RATE_TBK")));
                DecimalFormat df = new DecimalFormat("0.00");// 订单金额
                C_AMOUNT = df.format(Double.valueOf(price) * C_RATE);
            }

        }
     }
     if("4".equals(state)){//添加顶级代理返现
         jdbcTemplate.update(sql, new Object[]{C_LOGIN_ID, C_AMOUNT, 1, uid, 3, C_SOURCE});
     }else if("5".equals(state)){//添加次级代理返现
         jdbcTemplate.update(sql1, new Object[]{C_LOGIN_ID, C_AMOUNT, 1, uid, 3, C_SOURCE});
     }else if("6".equals(state)){//添加指电后台返现
         jdbcTemplate.update(sql2, new Object[]{C_LOGIN_ID, C_AMOUNT, 1, uid, 3, C_SOURCE});
     }

}

    /**
     * 查询次级代理商
     *
     * @param uid
     * @return
     */
    public List<Map<String, Object>> selectNextAgent(String uid) {
        final String sql = "SELECT T.C_AGENT_ID FROM T_EK_MEMBER_INFO T WHERE C_ID=?";
        return jdbcTemplate.queryForList(sql, new Object[]{uid});
    }

    /**
     * 查询顶级代理商
     *
     * @param C_AGENT_ID
     * @return
     */
    public List<Map<String, Object>> selectTopAgent(String C_AGENT_ID) {
        final String sql = "SELECT C_LOGIN_ID FROM T_EK_MEMBER_AGENT WHERE C_UID=?";
        return jdbcTemplate.queryForList(sql, new Object[]{C_AGENT_ID});
    }


    public void checkUserMess(Map user_map, Map rate_map, String price, String C_SOURCE, String uid) {
        double C_RATE = 0;
        String C_AMOUNT = "0";
        if ("01".equals(C_SOURCE)) {
            C_RATE = StrUtils.str2Double(StrUtils.emptyOrString(rate_map.get("C_RATE")));
            DecimalFormat df = new DecimalFormat("0.00");// 订单金额
            C_AMOUNT = df.format(C_RATE);
        } else if ("02".equals(C_SOURCE)) {
            C_RATE = StrUtils.str2Double(StrUtils.emptyOrString(rate_map.get("C_RATE_FY")));
            DecimalFormat df = new DecimalFormat("0.00");// 订单金额
            C_AMOUNT = df.format(Double.valueOf(price) * C_RATE);
        } else if ("03".equals(C_SOURCE)) {
            C_RATE = StrUtils.str2Double(StrUtils.emptyOrString(rate_map.get("C_RATE_TBK")));
            DecimalFormat df = new DecimalFormat("0.00");// 订单金额
            C_AMOUNT = df.format(Double.valueOf(price) * C_RATE);
        }
        String C_UID = StrUtils.emptyOrString(user_map.get("C_REFER_ID"));//用户id
        String C_REFER_ID = "";
        if ("03".equals(C_SOURCE)) {//自己返利
            C_REFER_ID = C_UID;
        } else {
            C_REFER_ID = uid;//下线人脉ID
        }
        String C_LEVEL = StrUtils.emptyOrString(user_map.get("STATUS"));
        InsertRate(C_UID, C_AMOUNT, C_REFER_ID, C_LEVEL, C_SOURCE, "1");
    }


    /**
     * 添加返利信息
     */
    public void InsertRate(String C_UID, String C_AMOUNT, String C_REFER_ID, String C_LEVEL, String C_SOURCE, String C_CHANGE) {
        final String insertWealth = "INSERT INTO T_EK_MEMBER_WEALTH(C_ID,C_UID,C_AMOUNT,C_CHANGE,C_REFER_ID,C_LEVEL,C_SOURCE) VALUES (SEQ_EK_MEMBER_WEALTH.nextval,?,?,?,?,?,?) ";
        jdbcTemplate.update(insertWealth, new Object[]{C_UID, C_AMOUNT, C_CHANGE, C_REFER_ID, C_LEVEL, C_SOURCE});
    }
}
