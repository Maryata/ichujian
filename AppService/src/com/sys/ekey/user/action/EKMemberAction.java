package com.sys.ekey.user.action;

import com.cyberoller.sms.zt.MessagePostSender;
import com.sys.commons.AbstractAction;
import com.sys.ekey.freecall.action.FeiyuCloudAction;
import com.sys.ekey.freecall.service.FeiyuCloudService;
import com.sys.ekey.task.service.EKTaskService;
import com.sys.ekey.user.service.EKMemberService;
import com.sys.game.util.IGameConst;
import com.sys.util.*;
import com.sys.util.file.FileServices;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 触键用户
 *
 * @author Maryn
 */
@Component("eKMemberAction")
public class EKMemberAction extends AbstractAction {

    private static final long serialVersionUID = -6165445129747337644L;

    @Autowired
    private EKMemberService eKMemberService;

    @Autowired
    private FeiyuCloudAction feiyuCloudAction;

    @Autowired
    private FeiyuCloudService seiyuCloudService;

    @Autowired
    private EKTaskService ekTaskService;

    /**
     * 输出内容
     */
    private String out;

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    // 用户注册
    public String register() {
        Map<String, Object> reqMap = new HashMap<>();
        // 获取请求参数
        String imei = this.getParameter("imei");
        String phonenum = this.getParameter("phonenum");
        String pwd = this.getParameter("pwd");
        String valicode = this.getParameter("valicode");
        String type = this.getParameter("type");//1
        String supcode = this.getParameter("supcode");// 激活码
        String inviteCode = this.getParameter("inviteCode");// 邀请码
        String C_REFER_ID = this.getParameter("C_REFER_ID");// 推荐人Id
        String fc = StrUtils.isEmpty(this.getParameter("fc")) ? "0" : "1";// 2016-7-12 免费通话APP标记
        log.info("into EKMemberAction.register");
        log.info("imei = " + imei + ", phonenum = " + phonenum + ", pwd = " + pwd
                + ", valicode = " + valicode + ", supcode = " + supcode + ",C_REFER_ID=" + C_REFER_ID);
        try {
            if (StrUtils.isEmpty(phonenum)
                    || StrUtils.isEmpty(pwd) || StrUtils.isEmpty(valicode)
                    || StrUtils.isEmpty(type) || StrUtils.isEmpty(supcode)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
                out = JSONObject.fromObject(reqMap).toString();
                return "success";
            } else {
                // 查询手机号时候已经被注册过
                List<Map<String, Object>> registeredPhone = eKMemberService.findByPhone(phonenum);
                if (registeredPhone.size() > 0) {
                    reqMap.put("status", "N");
                    reqMap.put("info", "1010");
                    out = JSONObject.fromObject(reqMap).toString();
                    return "success";
                }
                // 获取30min之前的时间
                String s_date = ApDateTime.dateAdd("mm", -30, new Date(), ApDateTime.DATE_TIME_Sec);
                // 校验验证码是否可用
                List<Map<String, Object>> list = eKMemberService.isUsableCode(phonenum, valicode, s_date, type);
                if (list.isEmpty() || list.size() < 1) {
                    reqMap.put("status", "N");
                    reqMap.put("info", "1011");
                    out = JSONObject.fromObject(reqMap).toString();
                    return "success";
                }
                // 注册
                /** 2015-5-20 修改 ： 注册不上传头像 */
                /** 2015-8-11 修改 ： 保存密码之前进行MD5加密 */
                /** 2016-7-12 修改 ： 增加参数-免费通话APP标记fc */
                String C_AGENT_ID = "";
                String C_ISAGENT="0";
                String regid = eKMemberService.register(phonenum, MD5.md5Code(pwd), supcode, inviteCode, fc, C_REFER_ID, C_AGENT_ID,C_ISAGENT);
                // 拼接完整的注册id
                regid = "ICJ" + regid;
                // 获取用户id
                String uid = eKMemberService.getUid(regid);
                // 生成默认昵称
                eKMemberService.defaultNickname("ICJ" + uid, regid);
                if (StrUtils.isNotEmpty(imei)) {
                    // 新增注册用户IMEI信息
                    eKMemberService.insertImei(regid, imei, valicode, uid);
                }

                /** 2016-03-17 用户注册成功之后，将“e键”首页模板APP和当前用户id存入“用户应用表” */
                eKMemberService.setIndexApp4NewUser(uid);

                /** 2016-04-12 e键v2.1 增加飞语云账号 begin */
                List<Map<String, Object>> fyAccInfo = new ArrayList<>();
                try {
                    // 增加飞语云账号
                    fyAccInfo = feiyuCloudAction.addCloudAccount(phonenum, regid, supcode, fc);
                } catch (Exception e) {
                    log.error("add FeiyuCloudAccount failed, e : ", e);
                }
                /** 2016-04-12 e键v2.1 增加飞语云账号 end */

                /** 2016-7-12 修改：如果不是“免费通话APP”用户，不执行任务奖励 */
                if ("0".equals(fc)) {
                    /** 2016-04-14 e键v2.1 任务奖励 begin */
                    if (StrUtils.isNotEmpty(inviteCode)) {// 邀请注册奖励（奖励邀请人和受邀人双方）
                        // 查询邀请码是否有效
                        boolean isValid = eKMemberService.validInviteCode(inviteCode);
                        if (isValid) {
                            // 奖励注册人
                            ekTaskService.reward(uid, "1", "27", null);
                            // 根据邀请码查询邀请人已邀请的次数
                            Integer countOfInvite = eKMemberService.countOfInvite(inviteCode);
                            if (countOfInvite < 30) {// 邀请奖励人数上限是30
                                // 人数少于30，奖励邀请人
                                ekTaskService.reward(eKMemberService.getUid(inviteCode), "4", "25", null);
                            }
                        } else {
                            ekTaskService.reward(uid, "1", "1", null);
                        }
                    } else {// 注册奖励，奖励注册人
                        ekTaskService.reward(uid, "1", "1", null);
                    }
                    /** 2016-04-14 e键v2.1 任务奖励 end */
                }

                reqMap.put("status", "Y");
                reqMap.put("regid", regid);
                reqMap.put("uid", uid);
                reqMap.put("fyAccInfo", fyAccInfo);
                reqMap.put("info", "1012");

            }
//            feiyuCloudAction.addCloudAccount("15002124206", "ICJ100233333300");
//            ekTaskService.reward("256", "3", "20", "41");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("EKMemberAction.register failed,e : " + e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    /**
     * 手机H5注册
     *
     * @return
     */
    public String register_H5() {
        Map<String, Object> reqMap = new HashMap<>();
        // 获取请求参数
        String imei = this.getParameter("imei");
        String phonenum = this.getParameter("phonenum");
        String pwd = this.getParameter("pwd");
        String valicode = this.getParameter("valicode");
        String type = this.getParameter("type");
        String supcode = this.getParameter("supcode");// 激活码
        String inviteCode = this.getParameter("inviteCode");// 邀请码
        String C_REFER_ID = this.getParameter("C_REFER_ID");// 推荐人Id
        String callback = getParameter("callback");
        String loginId = getParameter("loginId");//顶级代理商id
        String fc = StrUtils.isEmpty(this.getParameter("fc")) ? "0" : "1";// 2016-7-12 免费通话APP标记
        log.info("into EKMemberAction.register");
        log.info("imei = " + imei + ", phonenum = " + phonenum + ", pwd = " + pwd
                + ", valicode = " + valicode + ", supcode = " + supcode + ",C_REFER_ID=" + C_REFER_ID);
        try {
            if (StrUtils.isEmpty(phonenum)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
                out = Base64.encode(callback) + "(" + JSONObject.fromObject(reqMap).toString() + ")";
                return "success";
            }else {
                if ("123".equals(callback)) {
                    if (StrUtils.isEmpty(phonenum)) {
                        reqMap.put("status", "N");
                        reqMap.put("info", "1001");
                        out = Base64.encode(callback) + "(" + JSONObject.fromObject(reqMap).toString() + ")";
                        return "success";
                    } else {
                        // 查询手机号时候已经被注册过
                        List<Map<String, Object>> registeredPhone = eKMemberService.findByPhone(phonenum);
                        List<Map<String, Object>> agentInfo = eKMemberService.selectAgentId(C_REFER_ID);
                        if (registeredPhone.size() > 0) {
                            //修改密码
                            if (StrUtils.isEmpty(pwd)) {
                                pwd = phonenum.substring(phonenum.length() - 6);
                            }
                            int row = eKMemberService.updatePwd(MD5.md5Code(pwd), registeredPhone.get(0).get("C_ID"));
                            //查询
                            if (row > 0) {
                                /***2016-07-26 二维码登录添加邀请人注册id 和顶级供应商***/
                                if (StrUtils.isEmpty(registeredPhone.get(0).get("C_REFER_ID"))) {//判断是否存在C_REFER_ID
                                    if (StrUtils.isNotEmpty(C_REFER_ID)) {
                                        eKMemberService.updateinviteId(registeredPhone.get(0).get("C_ID").toString(), C_REFER_ID);
                                    }
                                }
                                /****顶级代理商添加****/
                                String _isAgent = "0";
                                if (StrUtils.isNotEmpty(loginId)) {
                                    List<Map<String, Object>> agentList = eKMemberService.selectAgent(loginId, StrUtils.emptyOrString(registeredPhone.get(0).get("C_ID")));
                                    if (StrUtils.isEmpty(agentList)) {
                                        _isAgent = "1";
                                        this.insertAgent(loginId, StrUtils.emptyOrString(registeredPhone.get(0).get("C_ID")));
                                    }
                                }
                                /************* 添加次级代理商id*************/
                                if (StrUtils.isEmpty(registeredPhone.get(0).get("C_AGENT_ID"))) {
                                    if (StrUtils.isNotEmpty(agentInfo)) {
                                        String _type = StrUtils.emptyOrString(agentInfo.get(0).get("C_ISAGENT"));
                                        String agentId = StrUtils.emptyOrString(agentInfo.get(0).get("C_AGENT_ID"));
                                        String _agentId = "";
                                        if ("0".equals(_type)) {
                                            _agentId = agentId;
                                        } else {
                                            _agentId = C_REFER_ID;
                                        }
                                        eKMemberService.updateAgent(StrUtils.emptyOrString(registeredPhone.get(0).get("C_ID")), _agentId, _isAgent);
                                    }

                                }
                                reqMap.put("status", "Y");
                                reqMap.put("userinfo", registeredPhone);
                            } else {
                                reqMap.put("status", "N");
                                reqMap.put("info", "1003");
                            }
                            out = Base64.encode(callback) + "(" + JSONObject.fromObject(reqMap).toString() + ")";
                            return "success";
                        }
                        type = "1";
                        // 获取30min之前的时间
                        String s_date = ApDateTime.dateAdd("mm", -30, new Date(), ApDateTime.DATE_TIME_Sec);
                        // 校验验证码是否可用
                        if (StrUtils.isNotEmpty(valicode)) {
                            List<Map<String, Object>> list = eKMemberService.isUsableCode(phonenum, valicode, s_date, type);
                            if (list.isEmpty() || list.size() < 1) {
                                reqMap.put("status", "N");
                                reqMap.put("info", "1011");
                                out = Base64.encode(callback) + "(" + JSONObject.fromObject(reqMap).toString() + ")";
                                return "success";
                            }
                        }
                        // 注册
                        /** 2015-5-20 修改 ： 注册不上传头像 */
                        /** 2015-8-11 修改 ： 保存密码之前进行MD5加密 */
                        /** 2016-7-12 修改 ： 增加参数-免费通话APP标记fc */
                        String C_AGENT_ID = "";
                        String C_ISAGENT = "0";
                        if (StrUtils.isNotEmpty(C_REFER_ID)) {//添加上级
                            if (StrUtils.isNotEmpty(agentInfo)) {
                                String _type = StrUtils.emptyOrString(agentInfo.get(0).get("C_ISAGENT"));
                                String agentId = StrUtils.emptyOrString(agentInfo.get(0).get("C_AGENT_ID"));
                                if ("0".equals(_type)) {
                                    C_AGENT_ID = agentId;
                                } else {
                                    C_AGENT_ID = C_REFER_ID;
                                }
                            }
                        }
                        if (StrUtils.isNotEmpty(loginId)) {
                            C_ISAGENT = "1";
                        }
                        String regid = eKMemberService.register(phonenum, MD5.md5Code(pwd), supcode, inviteCode, fc, C_REFER_ID, C_AGENT_ID, C_ISAGENT);
                        // 拼接完整的注册id
                        regid = "ICJ" + regid;
                        // 获取用户id
                        String uid = eKMemberService.getUid(regid);
                        // 生成默认昵称
                        /*** 顶级代理商和次级代理商配置*/
                        if (StrUtils.isNotEmpty(loginId)) {
                            this.insertAgent(loginId, uid);
                        }
                        if (StrUtils.isNotEmpty(C_REFER_ID)) {
                            eKMemberService.updateTime(uid);
                        }
                        eKMemberService.defaultNickname("ICJ" + uid, regid);
                        if (StrUtils.isNotEmpty(imei)) {
                            // 新增注册用户IMEI信息
                            eKMemberService.insertImei(regid, imei, valicode, uid);
                        }

                        /** 2016-03-17 用户注册成功之后，将“e键”首页模板APP和当前用户id存入“用户应用表” */
                        eKMemberService.setIndexApp4NewUser(uid);

                        /** 2016-04-12 e键v2.1 增加飞语云账号 begin */
                        List<Map<String, Object>> fyAccInfo = new ArrayList<>();
                        try {
                            // 增加飞语云账号
                            fyAccInfo = feiyuCloudAction.addCloudAccount(phonenum, regid, supcode, fc);
                        } catch (Exception e) {
                            log.error("add FeiyuCloudAccount failed, e : ", e);
                        }
                        /** 2016-04-12 e键v2.1 增加飞语云账号 end */

                        /** 2016-7-12 修改：如果不是“免费通话APP”用户，不执行任务奖励 */
                        if ("0".equals(fc)) {
                            /** 2016-04-14 e键v2.1 任务奖励 begin */
                            if (StrUtils.isNotEmpty(inviteCode)) {// 邀请注册奖励（奖励邀请人和受邀人双方）
                                // 查询邀请码是否有效
                                boolean isValid = eKMemberService.validInviteCode(inviteCode);
                                if (isValid) {
                                    // 奖励注册人
                                    ekTaskService.reward(uid, "1", "27", null);
                                    // 根据邀请码查询邀请人已邀请的次数
                                    Integer countOfInvite = eKMemberService.countOfInvite(inviteCode);
                                    if (countOfInvite < 30) {// 邀请奖励人数上限是30
                                        // 人数少于30，奖励邀请人
                                        ekTaskService.reward(eKMemberService.getUid(inviteCode), "4", "25", null);
                                    }
                                } else {
                                    ekTaskService.reward(uid, "1", "1", null);
                                }
                            } else {// 注册奖励，奖励注册人
                                ekTaskService.reward(uid, "1", "1", null);
                            }
                            /** 2016-04-14 e键v2.1 任务奖励 end */
                        }
                        List<Map<String, Object>> user = eKMemberService.findByPhone(phonenum);
                        reqMap.put("status", "Y");
                        reqMap.put("regid", regid);
                        reqMap.put("uid", uid);
                        reqMap.put("fyAccInfo", fyAccInfo);
                        reqMap.put("info", "1012");
                        reqMap.put("userinfo", user);

                    }
//            feiyuCloudAction.addCloudAccount("15002124206", "ICJ100233333300");
//            ekTaskService.reward("256", "3", "20", "41");
                } else {
                    reqMap.put("status", "N");
                    reqMap.put("info", "2001");
                }
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("EKMemberAction.register failed,e : " + e);
        }
        out = Base64.encode(callback) + "(" + JSONObject.fromObject(reqMap).toString() + ")";
        return "success";
    }

    /**
     * 顶级代理商和次级代理商配置
     *
     * @param loginId
     * @param uid
     */
    private void insertAgent(String loginId, String uid) {
        eKMemberService.insertAgent(loginId, uid);
    }


    // 手机号登录
    public String login() {
        Map<String, Object> reqMap = new HashMap<>();
        String phonenum = getParameter("phonenum");
        String imei = getParameter("imei");
        String pwd = getParameter("pwd");
        String C_REFER_ID = getParameter("C_REFER_ID");// 推荐人Id
        String fc = StrUtils.isEmpty(this.getParameter("fc")) ? "0" : "1";// 2016-7-12 免费通话APP标记
        log.info("into EKMemberAction.login");
        log.info("phonenum = " + phonenum + ", imei = " + imei + ", pwd = " + pwd + ", C_REFER_ID = " + C_REFER_ID);
        try {
            if (StrUtils.isEmpty(phonenum) || StrUtils.isEmpty(pwd)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                /** 2015-8-11 修改 ： 密码进行MD5加密 */
                List<Map<String, Object>> user = eKMemberService.login(phonenum, MD5.md5Code(pwd));
                if (user.size() > 0) {
                    Map<String, Object> m = user.get(0);
                    reqMap.put("status", "Y");
                    m.put("C_PASSWORD", pwd);// 返回前台的密码不使用加密
                    reqMap.put("userinfo", user);
                    /** 2016-04-19 e键v2.1 查询飞语账号密码 begin */
                    List<Map<String, Object>> fyAccInfo = seiyuCloudService.userInfo(m.get("C_ID").toString());
                    reqMap.put("fyAccInfo", fyAccInfo);
                    /** 2016-04-19 e键v2.1 查询飞语账号密码 end */
                    // 获取触键注册用户与Imei对应关系记录
                    if (StrUtils.isNotEmpty(imei)) {
                        List<Map<String, Object>> userImei = eKMemberService.getMemberImei(m.get("C_REGID").toString(), imei);
                        if (userImei.size() > 0) {
                            eKMemberService.updateIsLogin(m.get("C_REGID").toString(), imei); // 当前为最新
                        } else {
                            // 新增触键注册用户登录记录（IMEI）
                            eKMemberService.insertImei(m.get("C_REGID").toString(), imei, "", m.get("C_ID").toString());
                        }
                        eKMemberService.updateIsNotLogin(m.get("C_REGID").toString(), imei); // 修改其他不是最新
                    }
                    /***2016-07-26 二维码登录添加邀请人注册id***/
                    //eKMemberService.updateinviteId(user.get(0).get("C_ID").toString(), C_REFER_ID);
                    /** 2016-7-12 修改：如果不是“免费通话APP”用户，不执行任务奖励 */
                    if ("0".equals(fc)) {
                        /** 2016-04-21 e键v2.1 登录自动签到 begin */
                        String uid = user.get(0).get("C_ID").toString();
                        try {
                            // 如果未签到，签到，奖励积分
                            ekTaskService.reward(uid, "4", "26", null);
                        } catch (Exception e) {
                            log.error("签到失败！ uid : [" + uid + "]");
                        }
                        /** 2016-04-21 e键v2.1 登录自动签到 end */
                    }
                } else {
                    reqMap.put("status", "N");
                    reqMap.put("info", "1008");
                }
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("EKMemberAction.login failed,", e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    /**
     * h5登录
     *
     * @return
     */
    public String login_H5() {
        Map<String, Object> reqMap = new HashMap<>();
        String phonenum = getParameter("phonenum");
        String imei = getParameter("imei");
        String pwd = getParameter("pwd");
        String C_REFER_ID = getParameter("C_REFER_ID");// 推荐人Id
        String callback = getParameter("callback");
        String fc = StrUtils.isEmpty(this.getParameter("fc")) ? "0" : "1";// 2016-7-12 免费通话APP标记
        log.info("into EKMemberAction.login");
        log.info("phonenum = " + phonenum + ", imei = " + imei + ", pwd = " + pwd + ", C_REFER_ID = " + C_REFER_ID);
        try {
            if (StrUtils.isEmpty(phonenum) || StrUtils.isEmpty(pwd)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                if ("123".equals(callback)) {
                    /** 2015-8-11 修改 ： 密码进行MD5加密 */
                    List<Map<String, Object>> user = eKMemberService.login(phonenum, MD5.md5Code(pwd));
                    if (user.size() > 0) {
                        Map<String, Object> m = user.get(0);
                        reqMap.put("status", "Y");
                        m.put("C_PASSWORD", pwd);// 返回前台的密码不使用加密
                        reqMap.put("userinfo", user);
                        /** 2016-04-19 e键v2.1 查询飞语账号密码 begin */
                        List<Map<String, Object>> fyAccInfo = seiyuCloudService.userInfo(m.get("C_ID").toString());
                        reqMap.put("fyAccInfo", fyAccInfo);
                        /** 2016-04-19 e键v2.1 查询飞语账号密码 end */
                        // 获取触键注册用户与Imei对应关系记录
                        if (StrUtils.isNotEmpty(imei)) {
                            List<Map<String, Object>> userImei = eKMemberService.getMemberImei(m.get("C_REGID").toString(), imei);
                            if (userImei.size() > 0) {
                                eKMemberService.updateIsLogin(m.get("C_REGID").toString(), imei); // 当前为最新
                            } else {
                                // 新增触键注册用户登录记录（IMEI）
                                eKMemberService.insertImei(m.get("C_REGID").toString(), imei, "", m.get("C_ID").toString());
                            }
                            eKMemberService.updateIsNotLogin(m.get("C_REGID").toString(), imei); // 修改其他不是最新
                        }

                        /***2016-07-26 二维码登录添加邀请人注册id***/
                        if (StrUtils.isEmpty(user.get(0).get("C_REFER_ID"))) {
                            //eKMemberService.updateinviteId(user.get(0).get("C_ID").toString(), C_REFER_ID);
                        }
                        /** 2016-7-12 修改：如果不是“免费通话APP”用户，不执行任务奖励 */
                        if ("0".equals(fc)) {
                            /** 2016-04-21 e键v2.1 登录自动签到 begin */
                            String uid = user.get(0).get("C_ID").toString();
                            try {
                                // 如果未签到，签到，奖励积分
                                ekTaskService.reward(uid, "4", "26", null);
                            } catch (Exception e) {
                                log.error("签到失败！ uid : [" + uid + "]");
                            }
                            /** 2016-04-21 e键v2.1 登录自动签到 end */
                        }
                    } else {
                        reqMap.put("status", "N");
                        reqMap.put("info", "1008");
                    }
                } else {
                    reqMap.put("status", "N");
                    reqMap.put("info", "2001");//非法请求
                }
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("EKMemberAction.login failed,", e);
        }
        out = Base64.encode(callback) + "(" + JSONObject.fromObject(reqMap).toString() + ")";
        return "success";

    }

    // 修改密码
    public String modifyPwd() {
        Map<String, Object> reqMap = new HashMap<>();
        String uid = getParameter("uid");
        String opwd = getParameter("opwd");
        String npwd = getParameter("npwd");
        log.info("into EKMemberAction.modifyPwd");
        log.info("uid = " + uid + ", opwd = " + opwd + ", npwd = " + npwd);
        try {
            if (StrUtils.isEmpty(uid) || StrUtils.isEmpty(opwd) || StrUtils.isEmpty(npwd)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                /** 2015-8-11 修改 ： 密码进行MD5加密 */
                opwd = MD5.md5Code(opwd);
                npwd = MD5.md5Code(npwd);
                // 根据用户id和原密码查询用户
                List<Map<String, Object>> list = eKMemberService.findUserByUidNPwd(uid, opwd);
                if (StrUtils.isNotEmpty(list)) {
                    // 如果正确，修改新密码
                    eKMemberService.modifyPwd(uid, opwd, npwd);
                    reqMap.put("status", "Y");
                } else {
                    reqMap.put("status", "N");
                    reqMap.put("info", "1009");
                }
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("EKMemberAction.login failed,", e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 获取验证码
    public String getValiCode() {
        Map<String, Object> retMap = new HashMap<>();
        String imei = getParameter("imei");
        String phonenum = getParameter("phonenum");
        String type = getParameter("type");
        String sup = getParameter("sup");// 2016-05-06  获取供应商代码，根据不同供应商修改短信内容
        log.info("into EKMemberAction.getValiCode");
        log.info("imei=" + imei + ", phonenum=" + phonenum + ", type=" + type + ", sup=" + sup);
        try {
            if (StrUtils.isEmpty(imei) || StrUtils.isEmpty(phonenum)
                    || StrUtils.isEmpty(type)) {
                retMap.put("status", "N");
                retMap.put("info", "1001");
            } else {
                if (type.equals("2")) { // 修改密码 没有注册 则不发送验证码
                    List<Map<String, Object>> list = eKMemberService.findByPhone(phonenum);
                    if (list.size() < 1) {
                        retMap.put("status", "N");
                        retMap.put("info", "1013");
                        out = JSONObject.fromObject(retMap).toString();
                        return "success";
                    }
                }
                /*if (type.equals("1")) { // 注册 手机号是否已经注册过
                    List<Map<String, Object>> list = eKMemberService.findByPhone(phonenum);
                    if (list.size() > 0) {
                        retMap.put("status", "N");
                        retMap.put("info", "1010");
                        out = JSONObject.fromObject(retMap).toString();
                        return "success";
                    }
                }*/
                // 格式化日期："年-月-日"
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                // 获取当天发送的验证码
                List<Map<String, Object>> list = eKMemberService.getValiCode(phonenum, type, sdf.format(new Date()));
                if (list.size() >= 5) {
                    retMap.put("status", "N");
                    retMap.put("info", "1015");
                    out = JSONObject.fromObject(retMap).toString();
                    return "success";
                } else {
                    /** 2016-05-06 e键v2.1 根据供应商代码查询供应商名称，替换短信内容 begin */
                    String supName = "易有";
                    if (StrUtils.isNotEmpty(sup)) {
                        if ("yiyou".equals(sup)) {
                            supName = "易有";
                        } else {
                            supName = eKMemberService.getNameBySupCode(sup);
                            if (StrUtils.isEmpty(supName)) {
                                supName = "易有";
                            }
                        }
                    }

                    /** 2016-05-06 e键v2.1 根据供应商代码查询供应商名称，替换短信内容 end */
                    String host = FileUtil.getSmspath("sms.zt.sender.host");
                    String username = FileUtil.getSmspath("sms.zt.sender.username");
                    String password = FileUtil.getSmspath("sms.zt.sender.password");
                    String productid = FileUtil.getSmspath("sms.zt.sender.productid");
                    String sendnumber = FileUtil.getSmspath("sms.zt.sender.sendnumber");
                    // 获取6位随机数验证码
                    String code = RandNumber.getRandNumber(6);
                    com.cyberoller.sms.MessageSender messageSender = new MessagePostSender(
                            host, username, password, productid);
                    String message = "亲爱的" + supName + "用户：手机验证码为" + code + "，请提交完成验证。如非本人操作，请忽略本短信。【" + supName + "】";
                    messageSender.send(phonenum, message); // 发送验证码
                    eKMemberService.invalidatedOther(phonenum, type); // 修改其他验证码状态
                    eKMemberService.insertValicode(imei, phonenum, type, new Date(), code);// 保存本次发送的验证码
                    retMap.put("status", "Y");
                    retMap.put("sendnumber", sendnumber);
                }
            }
        } catch (Exception e) {
            retMap.put("status", "N");
            retMap.put("info", "1003, " + e.getMessage());
            log.error("EKMemberAction.getValiCode failed,", e);
        }
        out = JSONObject.fromObject(retMap).toString();
        return "success";
    }

    /****
     * 获取验证码
     *
     * @return
     */
    public String getValiCode_H5() {
        Map<String, Object> retMap = new HashMap<>();
        String imei = getParameter("imei");
        String phonenum = getParameter("phonenum");
        String type = getParameter("type");//1
        String callback = getParameter("callback");
        String sup = getParameter("sup");// 2016-05-06  获取供应商代码，根据不同供应商修改短信内容//yiyou
        String fc = StrUtils.isEmpty(this.getParameter("fc")) ? "0" : "1";// 2016-7-12 免费通话APP标记
        log.info("into EKMemberAction.getValiCode");
        log.info("imei=" + imei + ", phonenum=" + phonenum + ", type=" + type + ", sup=" + sup);
        try {
            if (StrUtils.isEmpty(phonenum)
                    || StrUtils.isEmpty(type)) {
                retMap.put("status", "N");
                retMap.put("info", "1001");
            } else {
                if ("123".equals(callback)) {
                    if (type.equals("2")) { // 修改密码 没有注册 则不发送验证码
                        List<Map<String, Object>> list = eKMemberService.findByPhone(phonenum);
                        if (list.size() < 1) {
                            retMap.put("status", "N");
                            retMap.put("info", "1013");
                            out = Base64.encode(callback) + "(" + JSONObject.fromObject(retMap).toString() + ")";
                            return "success";
                        }
                    }
                /*if (type.equals("1")) { // 注册 手机号是否已经注册过
                    List<Map<String, Object>> list = eKMemberService.findByPhone(phonenum);
                    if (list.size() > 0) {
                        retMap.put("status", "N");
                        retMap.put("info", "1010");
                        out = JSONObject.fromObject(retMap).toString();
                        return "success";
                    }
                }*/
                    // 格式化日期："年-月-日"
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    // 获取当天发送的验证码
                    List<Map<String, Object>> list = eKMemberService.getValiCode(phonenum, type, sdf.format(new Date()));
                    if (list.size() >= 5) {
                        retMap.put("status", "N");
                        retMap.put("info", "1015");
                        out = Base64.encode(callback) + "(" + JSONObject.fromObject(retMap).toString() + ")";
                        return "success";
                    } else {
                        /** 2016-05-06 e键v2.1 根据供应商代码查询供应商名称，替换短信内容 begin */
                        String supName = "e键";
                        if (StrUtils.isNotEmpty(imei)) {
                            if (StrUtils.isNotEmpty(sup)) {
                                supName = eKMemberService.getNameBySupCode(sup);
                                if (StrUtils.isEmpty(supName)) {
                                    supName = "易有";
                                }
                            }
                        } else {
                            supName = "易有";
                        }

                        /** 2016-05-06 e键v2.1 根据供应商代码查询供应商名称，替换短信内容 end */
                        String host = FileUtil.getSmspath("sms.zt.sender.host");
                        String username = FileUtil.getSmspath("sms.zt.sender.username");
                        String password = FileUtil.getSmspath("sms.zt.sender.password");
                        String productid = FileUtil.getSmspath("sms.zt.sender.productid");
                        String sendnumber = FileUtil.getSmspath("sms.zt.sender.sendnumber");
                        // 获取6位随机数验证码
                        String code = RandNumber.getRandNumber(6);
                        com.cyberoller.sms.MessageSender messageSender = new MessagePostSender(
                                host, username, password, productid);
                        String message = "亲爱的" + supName + "用户：手机验证码为" + code + "，请提交完成验证。如非本人操作，请忽略本短信。【" + supName + "】";
                        messageSender.send(phonenum, message); // 发送验证码
                        eKMemberService.invalidatedOther(phonenum, type); // 修改其他验证码状态
                        eKMemberService.insertValicode(imei, phonenum, type, new Date(), code);// 保存本次发送的验证码
                        retMap.put("status", "Y");
                        retMap.put("sendnumber", sendnumber);
                    }
                } else {
                    retMap.put("status", "N");
                    retMap.put("info", "2001");
                }
            }


        } catch (Exception e) {
            retMap.put("status", "N");
            retMap.put("info", "1003, " + e.getMessage());
            log.error("EKMemberAction.getValiCode failed,", e);
        }
        out = Base64.encode(callback) + "(" + JSONObject.fromObject(retMap).toString() + ")";
        return "success";
    }

    // 忘记密码-判断验证码的有效性
    public String isValidCode() {
        Map<String, Object> reqMap = new HashMap<>();
        String phonenum = getParameter("phonenum");
        String valicode = getParameter("valicode");
        String type = getParameter("type");
        String imei = getParameter("imei");
        log.info("into EKMemberAction.isValidCode");
        log.info("phonenum=" + phonenum + ", valicode=" + valicode + ", type="
                + type + ", imei=" + imei);
        try {
            if (StrUtils.isEmpty(valicode) || StrUtils.isEmpty(phonenum)
                    || StrUtils.isEmpty(type) || StrUtils.isEmpty(imei)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                // 获取30min之前的时间
                String s_date = ApDateTime.dateAdd("mm", -30, new Date(), ApDateTime.DATE_TIME_Sec);
                // 校验验证码是否可用
                List<Map<String, Object>> code = eKMemberService.isUsableCode(phonenum, valicode, s_date, type);
                if (code.isEmpty() && code.size() < 1) {
                    reqMap.put("status", "N");
                    reqMap.put("info", "1011");
                    out = JSONObject.fromObject(reqMap).toString();
                    return "success";
                } else {
                    // 修改验证码状态为0
                    //eKMemberService.invalidatedOther(phonenum, type);

                    reqMap.put("status", "Y");
                    out = JSONObject.fromObject(reqMap).toString();
                    return "success";
                }
                // 查询手机号是否存在
//                List<Map<String, Object>> user = eKMemberService.findByPhone(phonenum);
//                if (user.size() > 0) {
//                    reqMap.put("status", "Y");
//                } else {
//                    reqMap.put("status", "N");
//                    reqMap.put("info", "1013");
//                }

            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("EKMemberAction.isValidCode failed,", e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 忘记密码-修改密码
    public String setPwdOfFgt() {
        Map<String, Object> reqMap = new HashMap<>();
        String phonenum = getParameter("phonenum");
        String pwd = getParameter("pwd");
        String valicode = getParameter("valicode");
        String type = getParameter("type");
        log.info("into EKMemberAction.setPwdOfFgt");
        log.info("phonenum = " + phonenum + ", pwd = " + pwd + ", valicode = " + valicode + ", type = " + type);
        try {
            if (StrUtils.isEmpty(phonenum) || StrUtils.isEmpty(pwd) || StrUtils.isEmpty(valicode) || StrUtils.isEmpty(type)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                // 获取30min之前的时间
                String s_date = ApDateTime.dateAdd("mm", -30, new Date(), ApDateTime.DATE_TIME_Sec);
                // 校验验证码是否可用
                List<Map<String, Object>> code = eKMemberService.isUsableCode(phonenum, valicode, s_date, type);
                if (code.isEmpty() && code.size() < 1) {
                    reqMap.put("status", "N");
                    reqMap.put("info", "1011");
                    out = JSONObject.fromObject(reqMap).toString();
                    return "success";
                }
                // 判断手机号是否已注册
                List<Map<String, Object>> list = eKMemberService.findByPhone(phonenum);
                if (list.size() > 0) {
                    /** 2015-8-11 修改 ： 密码进行MD5加密 */
                    pwd = MD5.md5Code(pwd);
                    // 设置新密码
                    eKMemberService.setNewPwd(phonenum, pwd);
                    reqMap.put("status", "Y");
                    reqMap.put("info", "1014");
                } else {
                    reqMap.put("status", "Y");
                    reqMap.put("info", "1013");
                }
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("EKMemberAction.setPwdOfFgt failed,", e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 校验手机号是否可用
    public String validatePhone() {
        Map<String, Object> reqMap = new HashMap<>();
        // 获取请求参数
        String phoneNum = this.getParameter("phoneNum");
        log.info("into EKMemberAction.validatePhone");
        log.info("phoneNum = " + phoneNum);
        try {
            if (StrUtils.isEmpty(phoneNum)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                // 校验
                List<Map<String, Object>> list = eKMemberService.findByPhone(phoneNum);
                if (null != list && list.size() > 0) {
                    reqMap.put("info", "1010");// 不可用
                    reqMap.put("status", "N");
                } else {
                    reqMap.put("status", "Y");
                }
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("EKMemberAction.validatePhone failed,e : " + e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 获取游戏提示语
    public String gameCue() {
        Map<String, Object> reqMap = new HashMap<>();
        // 获取用户id
        String uid = getParameter("uid");
        // 获取登陆状态
        String loginType = getParameter("loginType");
        log.info("into EKGameUserAction.gameCue");
        log.info("uid = " + uid);
        try {
            if (StrUtils.isEmpty(uid)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            }
            // 获取用户头像、昵称
            List<Map<String, Object>> headImg = eKMemberService.userHeadImg(uid, loginType);
            reqMap.put("gameCue", headImg);
            reqMap.put("status", "Y");

        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("EKGameUserAction.editUserInfo failed,", e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 个人信息查询
    public String userInfo() {
        Map<String, Object> reqMap = new HashMap<>();
        // 获取请求参数
        String uid = this.getParameter("uid");
        log.info("into EKGameUserAction.userInfo");
        log.info("uid = " + uid);
        try {
            if (StrUtils.isEmpty(uid)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                // 查询用户信息
                List<Map<String, Object>> user = eKMemberService.getUserInfo(uid);
                reqMap.put("status", "Y");
                reqMap.put("userInfo", user);
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("EKGameUserAction.userInfo failed,e : " + e);
        }
        // 转成JSON
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 修改用户头像
    public String userHead() {
        Map<String, Object> retMap = new HashMap<>();
        String uid = getParameter("uid");
        InputStream userhead = getParameter3("userhead");// 将请求参数中的userhead读到流中
        //String userhead = getRequest().getParameter("userhead");
        log.info("into EKGameUserAction.userHead");
        log.info("uid=" + uid);
        try {
            if (StrUtils.isEmpty(uid)) {
                retMap.put("status", "N");
                retMap.put("info", "1001");
            } else {
                //String s = new String(org.apache.commons.codec.binary.Base64.decodeBase64(userhead.getBytes())).replaceAll(" ","").replaceAll("%3D","=");
                //String fileurl = FileServices.saveFile(new ByteArrayInputStream(org.apache.commons.codec.binary.Base64.decodeBase64(s)), "ek-user/userHead/" + uid + ".png");

                // 上传图片，并返回图片路径
                String fileurl = FileServices.saveHeadFile(userhead, "ek-user/userHead/" + uid + ".png");

                /** 2016-04-14 e键v2.1 任务奖励--首次上传头像 begin */
                ekTaskService.reward(uid, "1", "2", null);
                /** 2016-04-14 e键v2.1 任务奖励--首次上传头像 end */
                // 将头像路径保存到
                eKMemberService.updateHead(uid, fileurl);
                retMap.put("status", "Y");
                retMap.put("userhead", fileurl);
            }
        } catch (Exception e) {
            retMap.put("status", "N");
            retMap.put("info", "1003, " + e.getMessage());
            log.error("EKGameUserAction.userHead failed,", e);
        }
        out = JSONObject.fromObject(retMap).toString();
        return "success";
    }

    // 修改用户信息
    public String modify() {
        Map<String, Object> retMap = new HashMap<>();
        String uid = getParameter("uid");
        String type = getParameter("type");// 修改的项：1.昵称，2.性别，3.地区
        String info = getParameter("info");// 修改的内容
        log.info("into EKGameUserAction.modify");
        log.info("uid = " + uid + ", type = " + type + ", info = " + info);
        try {
            if (StrUtils.isEmpty(uid)) {
                retMap.put("status", "N");
                retMap.put("info", "1001");
            } else {
                /** 2016-04-18 e键v2.1 任务奖励--首次设置地区 begin */
                if ("3".equals(type)) {
                    ekTaskService.reward(uid, "1", "3", null);
                }
                /** 2016-04-18 e键v2.1 任务奖励--首次设置地区 end */

                // 修改用户信息
                eKMemberService.modify(uid, type, info);
                retMap.put("status", "Y");
            }
        } catch (Exception e) {
            retMap.put("status", "N");
            retMap.put("info", "1003, " + e.getMessage());
            log.error("EKGameUserAction.modify failed,", e);
        }
        out = JSONObject.fromObject(retMap).toString();
        return "success";
    }


    // 新增/修改用户地址
    public String usersAddress() {
        Map<String, Object> retMap = new HashMap<>();
        String uid = getParameter("uid");
        String name = getParameter("name");// 收货人姓名
        String phone = getParameter("phone");// 手机
        String addr = getParameter("addr");// 详细地址
        String postCode = getParameter("postCode");// 邮编
        log.info("into EKGameUserAction.usersAddress");
        log.info("uid = " + uid + ", name = " + name + ", phone = " + phone + ", addr = "
                + addr + ", postCode = " + postCode);
        try {
            if (StrUtils.isEmpty(uid)) {
                retMap.put("status", "N");
                retMap.put("info", "1001");
            } else {
                // 新增/修改用户地址
                eKMemberService.usersAddress(uid, name, phone, addr, postCode);
                retMap.put("status", "Y");
            }
        } catch (Exception e) {
            retMap.put("status", "N");
            retMap.put("info", "1003, " + e.getMessage());
            log.error("EKGameUserAction.usersAddress failed,", e);
        }
        out = JSONObject.fromObject(retMap).toString();
        return "success";
    }

    /**
     * 联系客服,接收客户发送消息
     *
     * @return
     */
    public String contactServiceRoute() {
        // c_uid, c_mess, c_img
        if (log.isDebugEnabled()) {
            log.debug("into contactServiceRoute ...");
        }

        // 用户ID
        String sUid = getParameter("uid");
        // 页码，每页显示数量
        String message = getParameter("message");
        String[] imgArr = getRequest().getParameterValues("imgArr");

        Map<String, Object> retMap = new HashMap<>();

        if (StringUtils.isEmpty(sUid)) {
            retMap.put(IGameConst.STATUS, IGameConst.NO);
            retMap.put(IGameConst.INFO, IGameConst._1001);
        } else {
            try {
                eKMemberService.contactServiceRoute(sUid, message, imgArr);

                retMap.put(IGameConst.STATUS, IGameConst.YES);
                retMap.put(IGameConst.INFO, IGameConst._1002);
            } catch (Exception e) {
                log.error("联系客服出错", e);
                retMap.put(IGameConst.STATUS, IGameConst.NO);
                retMap.put(IGameConst.INFO, IGameConst._1003);
            }
        }

        try {
            writeToResponse(JSONObject.fromObject(retMap).toString());
        } catch (Exception e) {
            log.error("回写联系客服数据标识出错！", e);
        }

        return NONE;
    }

    /**
     * 获取消息
     *
     * @return
     */
    public String getMessage() {
        if (log.isDebugEnabled()) {
            log.debug("into getMessage ...");
        }

        // 用户ID
        String sUid = getParameter("uid");
        // 页码，每页显示数量
        String sPageNumber = getParameter("pageNumber"),
                sPageSize = getParameter("pageSize");
        int pageNumber = 1, pageSize = 20; // 默认第一页，每页20条数据
        List<Map<String, Object>> result;
        Map<String, Object> retMap = new HashMap<>();

        if (StringUtils.isEmpty(sUid)) {
            retMap.put(IGameConst.STATUS, IGameConst.NO);
            retMap.put(IGameConst.INFO, IGameConst._1001);
        } else {
            try {
                if (!StringUtils.isEmpty(sPageNumber)) {
                    pageNumber = Integer.parseInt(sPageNumber);
                }
                if (!StringUtils.isEmpty(sPageSize)) {
                    pageSize = Integer.parseInt(sPageSize);
                }

                result = eKMemberService.getMessage(Integer.parseInt(sUid), pageNumber, pageSize);

                retMap.put("message", result);
                retMap.put(IGameConst.STATUS, IGameConst.YES);
                retMap.put(IGameConst.INFO, IGameConst._1002);
            } catch (Exception e) {
                log.error("获取客服消息出错", e);
                retMap.put(IGameConst.STATUS, IGameConst.NO);
                retMap.put(IGameConst.INFO, IGameConst._1003);
            }
        }

        try {
            writeToResponse(JSONObject.fromObject(retMap).toString());
        } catch (Exception e) {
            log.error("回写客服消息出错", e);
        }

        return NONE;
    }

    // 设置身份信息
    public String setIDInfo() {
        Map<String, Object> reqMap = new HashMap<>();
        String uid = getParameter("uid");// 用户id
        String id_num = getParameter("id_num");// 身份证号码
        String name = getParameter("name");// 姓名
        String[] id_img = getRequest().getParameterValues("id_img");
        log.info("into EKMemberAction.setIDInfo...");
        log.info("uid = " + uid + ", id_num = " + id_num
                + ", name = " + name + ", id_img = " + id_img);
        try {
            int row = eKMemberService.setIDInfo(uid, id_num, name, id_img);
            if (row > 0) {
                reqMap.put("result", "1");
            } else {
                reqMap.put("result", "0");
            }
            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("EKMemberAction.setIDInfo failed, e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }

        return NONE;
    }

    // 设置提现密码
    public String setWithdrawPwd() {
        Map<String, Object> reqMap = new HashMap<>();
        String uid = getParameter("uid");// 用户id
        String withdraw = getParameter("withdraw");// 提现密码
        log.info("into EKMemberAction.setWithdrawPwd...");
        log.info("uid = " + uid + ", withdraw = " + withdraw);
        try {
            int row = eKMemberService.setWithdrawPwd(uid, withdraw);
            if (row > 0) {
                reqMap.put("result", "1");
            } else {
                reqMap.put("result", "0");
            }
            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("EKMemberAction.setWithdrawPwd failed, e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }

        return NONE;
    }


    // 重置提现密码
    public String resetWithdrawPwd() {
        Map<String, Object> reqMap = new HashMap<>();
        String uid = getParameter("uid");// 用户id
        String id_num = getParameter("id_num");// 身份证号码
        String withdraw = getParameter("withdraw");// 提现密码
        log.info("into EKMemberAction.resetWithdrawPwd...");
        log.info("uid = " + uid + ", id_num = " + id_num + ", withdraw = " + withdraw);
        try {
            int row = eKMemberService.resetWithdrawPwd(uid, id_num, withdraw);
            if (row > 0) {
                reqMap.put("result", "1");
            } else {
                reqMap.put("result", "0");
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("EKMemberAction.resetWithdrawPwd failed, e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }

        return NONE;
    }

}
