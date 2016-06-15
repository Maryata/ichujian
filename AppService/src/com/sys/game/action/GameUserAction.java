package com.sys.game.action;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cyberoller.sms.zt.MessagePostSender;
import com.sys.game.service.GameUserService;
import com.sys.game.util.IGameConst;
import com.sys.util.ApDateTime;
import com.sys.util.FileUtil;
import com.sys.util.MD5;
import com.sys.util.RandNumber;
import com.sys.util.StrUtils;
import com.sys.util.file.FileServices;

/**
 * 趣游戏-用户
 */
@Component
public class GameUserAction extends GameBaseAction {

    private static final long serialVersionUID = 8702729777036149388L;

    @Autowired
    private GameUserService gameUserService;

    // 个人信息查询
    public String userInfo() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        // 获取请求参数
        String uid = this.getParameter("uid");
        log.info("into GameUserAction.userInfo");
        log.info("uid = " + uid);
        try {
            if (StrUtils.isEmpty(uid)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                // 查询用户信息
                List<Map<String, Object>> user = gameUserService.getUserInfo(uid);
                List<Map<String, Object>> bindingInfo = new ArrayList<Map<String, Object>>();
                if (null != user && user.size() > 0) {
                    Map<String, Object> map = user.get(0);
                    if ("".equals(map.get("C_NICKNAME"))
                            || null == map.get("C_NICKNAME")) {
                        map.put("C_NICKNAME", "");
                    }
                    // 查询绑定信息--只有当用户id在数据库中有数据时，才查询绑定信息。
                    bindingInfo = gameUserService.getBindingInfo(uid);
                }
                // 将用户信息存入reqMap
                reqMap.put("status", "Y");
                reqMap.put("userInfo", user);
                reqMap.put("bindingInfo", bindingInfo);
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("GameUserAction.userInfo failed,e : " + e);
        }
        // 转成JSON
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 是否显示“完善个人资料”
    public String showComplete() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        // 获取请求参数
        String uid = this.getParameter("uid");
        log.info("into GameUserAction.showComplete");
        log.info("uid = " + uid);
        try {
            if (StrUtils.isEmpty(uid)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                int score = 0;
                // 查询用户是否已经做过一次性任务（上传头像、修改昵称、修改性别、修改手机号、绑定第三方）
                List<Map<String, Object>> tids = gameUserService.haveCompleted(uid);
				
                List<String> tasks = new ArrayList<String>();
                if(StrUtils.isNotEmpty(tids)){
                	for (int i = 0; i < tids.size(); i++) {
                		String tid = tids.get(i).get("C_TID").toString();
                		tasks.add(tid);
					}
                }
                
                // 根据用户id查询第三方绑定信息
                List<Map<String, Object>> bindingInfo = gameUserService.getBindingInfo(uid);
                if (StrUtils.isNotEmpty(bindingInfo)) {
                    Map<String, Object> map = bindingInfo.get(0);
                    if ("0".equals(map.get("chujian"))) {
                    	if (!tasks.contains("19")) {
                    		score += 20;
                    	}
                    }
                    if ("0".equals(map.get("qq"))) {
                    	if (!tasks.contains("15")) {
                    		score += 20;
                    	}
                    }
                    if ("0".equals(map.get("weixin"))) {
                    	if (!tasks.contains("16")) {
                    		score += 20;
                    	}
                    }
                    if ("0".equals(map.get("weibo"))) {
                    	if (!tasks.contains("17")) {
                    		score += 20;
                    	}
                    }
                }
                // 查询state=1的用户信息
                List<Map<String, Object>> registerInfo = gameUserService.getRegisterInfo(uid);
                if (StrUtils.isNotEmpty(registerInfo)) {
                    Map<String, Object> map = registerInfo.get(0);
                    if (StrUtils.isEmpty(map.get("C_NICKNAME")) || ("ICJ"+uid).equals(map.get("C_NICKNAME"))) {
                    	if (!tasks.contains("14")) {
                    		score += 20;
                    	}
                    }
                    if (StrUtils.isEmpty(map.get("C_HEADIMAGE"))) {
                    	if (!tasks.contains("1")) {
                    		score += 20;
                    	}
                    }
                    /*if (StrUtils.isEmpty(map.get("C_SEX"))) {
                    	if (!tasks.contains("18")) {
                    		score += 20;
                    	}
                    }*/
                    /*if ("0".equals(map.get("C_LOGINTYPE"))) {
                    	if (!tasks.contains("19")) {
                    		score += 20;
                    	}
                    }
                    if ("1".equals(map.get("C_LOGINTYPE"))) {
                    	if (!tasks.contains("15")) {
                    		score += 20;
                    	}
                    }
                    if ("2".equals(map.get("C_LOGINTYPE"))) {
                    	if (!tasks.contains("16")) {
                    		score += 20;
                    	}
                    }
                    if ("3".equals(map.get("C_LOGINTYPE"))) {
                    	if (!tasks.contains("17")) {
                    		score += 20;
                    	}
                    }*/
                }
                
                reqMap.put("status", "Y");
                reqMap.put("score", score);
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("GameUserAction.showComplete failed,e : " + e);
        }

        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // "完善资料"中的选项是否显示加积分
    public String showGetScore() {
    	Map<String, Object> reqMap = new HashMap<String, Object>();
        // 获取请求参数
        String uid = this.getParameter("uid");// 用户id
        log.info("into GameUserAction.showGetScore");
        log.info("uid = " + uid);
        try {
            if (StrUtils.isEmpty(uid)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                // 查询是否做过一次性任务的完成情况
                reqMap = gameUserService.showGetScore(uid);

                // 根据用户id查询第三方绑定信息
                List<Map<String, Object>> bindingInfo = gameUserService.getBindingInfo(uid);
                if (StrUtils.isNotEmpty(bindingInfo)) {
                    Map<String, Object> map = bindingInfo.get(0);
                    if ("1".equals(map.get("chujian"))) {
                        reqMap.put("boundPhone", "0");
                    }
                    if ("1".equals(map.get("qq"))) {
                        reqMap.put("bindQq", "0");
                    }

                    if ("1".equals(map.get("weixin"))) {
                        reqMap.put("bindWeChat", "0");
                    }
                    if ("1".equals(map.get("weibo"))) {
                        reqMap.put("bindMcroBlog", "0");
                    }
                }

                reqMap.put("status", "Y");
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("GameUserAction.showGetScore failed,e : " + e);
        }

        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    /*public String showGetScore() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        // 获取请求参数
        String uid = this.getParameter("uid");// 用户id
        String tid = this.getParameter("tid");// 任务id
        log.info("into GameUserAction.showGetScore");
        log.info("uid = " + uid + ", tid = " + tid);
        try {
            if (StrUtils.isEmpty(uid) || StrUtils.isEmpty(tid)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                String showable = "0";
                // 查询是否做过该任务
                int i = gameUserService.showGetScore(uid, tid);
                if (i == 0) {
                    showable = "1";
                }
                // 15-QQ 16-微信  17-微博  19-手机
                if ("15".equals(tid) || "16".equals(tid)  || "17".equals(tid)  || "19".equals(tid) ) {
                    List<Map<String, Object>> registerInfo = gameUserService.getRegisterInfo(uid);
                    String loginType = registerInfo.get(0).get("C_LOGINTYPE").toString();
                    if ("19".equals(tid) && "0".equals(loginType)) {
                        showable = "0";
                    } else if ("15".equals(tid) && "1".equals(loginType)) {
                        showable = "0";
                    } else if ("16".equals(tid) && "2".equals(loginType)) {
                        showable = "0";
                    } else if ("17".equals(tid) && "3".equals(loginType)) {
                        showable = "0";
                    }
                }

                reqMap.put("status", "Y");
                reqMap.put("showable", showable);
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("GameUserAction.showGetScore failed,e : " + e);
        }

        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }*/

    // 第三方登录
    public String external() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        // 获取请求参数
        String imei = this.getParameter("imei");
        String regid = this.getParameter("regid");
        String nickname = this.getParameter("nickname");
        String logintype = this.getParameter("logintype");
        String headimage = this.getParameter("headimage");
        String sex = this.getParameter("sex");
        String age = this.getParameter("age");
        log.info("into GameUserAction.external");
        log.info("imei = " + imei + ",regid = " + regid + ",nickname = "
                + nickname + ",logintype = " + logintype + ", headimage = "
                + headimage + ",sex = " + sex + ",age = " + age);
        try {
            if (StrUtils.isEmpty(regid) || StrUtils.isEmpty(nickname)
                    || StrUtils.isEmpty(logintype) || StrUtils.isEmpty(imei)
                    || StrUtils.isEmpty(headimage)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                // 根据imei和regid查询是否通过当前第三方登录过
                List<Map<String, Object>> user = gameUserService.findExternal(
                        regid, logintype);
                // 存在，则新增当前第三方的|登录记录|，并修改其他第三方登录状态
                String uid = null;
                if (null != user && user.size() > 0) {
                    reqMap.put("isFirstLogin", "N");// 非首次登录
                    // 获取用户id
                    Map<String, Object> map = user.get(0);
                    uid = map.get("C_ID").toString();
                    // 根据regid和imei查询登录与手机的对应关系信息
                    Integer info = gameUserService.findInfoByRegidNImei(regid,
                            imei);
                    if (info > 0) {
                        // 如果有记录，将当前第三方状态改为最新,修改"首次登录"为0（如果需要）
                        gameUserService.updateIsLogin(regid, imei);
                    } else {
                        // 如果没有记录，将当前第三方信息存入
                        // 新增当前第三方登录记录
                        gameUserService.insertExternalNImei(uid, regid, imei);
                    }
                    // 修改其他第三方登录状态
                    gameUserService.updateIsNotLogin(regid, imei);
                    // 不存在，则新增第三方的|登录信息|和|登录记录|
                } else {
                    reqMap.put("isFirstLogin", "Y");// 首次登录
                    // 第三方信息
                    uid = gameUserService.insertExternal(regid, nickname,
                            logintype, headimage, sex, age);
                    // 第三方与IMEI对应信息（登录记录）
                    gameUserService.insertExternalNImei(uid, regid, imei);
                }
                reqMap.put("status", "Y");
                reqMap.put("uid", uid);
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("GameUserAction.External failed,e : " + e);
        }

        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 绑定第三方账号
    public String bindExternal() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        // 获取请求参数
        String uid = this.getParameter("uid");
        String regid = this.getParameter("regid");
        String nickname = this.getParameter("nickname");
        String logintype = this.getParameter("logintype");
        String headimage = this.getParameter("headimage");
        String sex = this.getParameter("sex");
        String age = this.getParameter("age");
        log.info("into GameUserAction.bindExternal");
        log.info("uid = " + uid + ",regid = " + regid + ",logintype = "
                + logintype);
        try {
            if (StrUtils.isEmpty(regid) || StrUtils.isEmpty(logintype)
                    || StrUtils.isEmpty(uid)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                // 查询是否已经绑定过第三方账号
                List<Map<String, Object>> list1 = gameUserService.isBinding(regid,
                        logintype);
                if (null != list1 && list1.size() > 0) {
                    reqMap.put("info", "1017");
                } else {
                    // 获取用户通过触键注册时的手机号--0表示触键注册
                    List<Map<String, Object>> list2 = gameUserService.isRegistered(
                            uid, "0");
                    String phonenum = null;
                    if (null != list2 && list2.size() > 0) {
                        phonenum = list2.get(0).get("C_PHONENUM")
                                .toString();
                    }
                    // 保存第三方数据
                    gameUserService.bindExternal(uid, regid, nickname, logintype,
                            phonenum, sex, age, headimage);
                    reqMap.put("info", "1016");
                }
                reqMap.put("status", "Y");
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("GameUserAction.bindExternal failed,e : " + e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 解绑第三方账号
    public String unBindExternal() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        // 获取请求参数
        String uid = this.getParameter("uid");
        String logintype = this.getParameter("logintype");
        log.info("into GameUserAction.unBindExternal");
        log.info("uid = " + uid + " ,logintype = " + logintype);
        try {
            if (StrUtils.isEmpty(uid) || StrUtils.isEmpty(logintype)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                // 查询当前第三方账号是否是第一次登录的账号
                String state = gameUserService.isFirstInfo(uid, logintype);
                if ("1".equals(state)) {
                    reqMap.put("status", "N");
                    reqMap.put("info", "1018");
                } else {
                    // 解除绑定
                    gameUserService.unBindExternal(uid, logintype);
                    reqMap.put("status", "Y");
                    reqMap.put("info", "1016");
                }
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("GameUserAction.unBindExternal failed,e : " + e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 用户注册
    public String register() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        // 获取请求参数
        String imei = this.getParameter("imei");
        String phonenum = this.getParameter("phonenum");
        String pwd = this.getParameter("pwd");
        String valicode = this.getParameter("valicode");
        String type = this.getParameter("type");
        log.info("into GameUserAction.register");
        log.info("imei = " + imei + ",phonenum = " + phonenum + ",pwd = "
                + pwd + ",valicode = " + valicode + ",type = " + type);
        try {
            if (StrUtils.isEmpty(imei) || StrUtils.isEmpty(phonenum)
                    || StrUtils.isEmpty(pwd) || StrUtils.isEmpty(valicode)
                    || StrUtils.isEmpty(type)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
                out = JSONObject.fromObject(reqMap).toString();
                return "success";
            } else {
                // 获取30min之前的时间
                String s_date = ApDateTime.dateAdd("mm", -30,
                        new java.util.Date(), ApDateTime.DATE_TIME_Sec);
                // 校验验证码是否可用
                List<Map<String, Object>> list = gameUserService.isUsableCode(
                        phonenum, valicode, s_date, type);
                if (list.isEmpty() || list.size() < 1) {
                    reqMap.put("status", "N");
                    reqMap.put("info", "1011");
                    out = JSONObject.fromObject(reqMap).toString();
                    return "success";
                }
                // 注册
                /** 2015-5-20 修改 ： 注册不上传头像 */
                /** 2015-8-11 修改 ： 保存密码之前进行MD5加密 */
                String regid = gameUserService.register(phonenum,
                        MD5.md5Code(pwd));
                // 拼接完整的注册id
                regid = "ICJ" + regid;
                // 获取用户id
                String uid = gameUserService.getUid(regid, "0");
                // 生成默认昵称
                gameUserService.defaultNickname("ICJ" + uid, regid);

                // 新增注册用户IMEI信息
                gameUserService.regImei(regid, imei, valicode, new Date(), uid);


                /** 2015-12-21 新增：通过手机注册成为趣游戏用户，同时将数据同步到触键用户 begin */
                String regidCj = gameUserService.registerCj(phonenum, MD5.md5Code(pwd));
                String uidCj = gameUserService.getUidCj(regidCj, "0");
                gameUserService.defaultNicknameCj("ICJ" + uidCj, regidCj);
                gameUserService.regImeiCj(regidCj, imei, valicode, uidCj);
                /** 2015-12-21 新增：通过手机注册成为趣游戏用户，同时将数据同步到触键用户 end */

                reqMap.put("status", "Y");
                reqMap.put("regid", regid);
                reqMap.put("uid", uid);
                reqMap.put("info", "1012");
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("GameUserAction.register failed,e : " + e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 手机号登录
    public String login() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        String phonenum = getParameter("phonenum");
        String imei = getParameter("imei");
        String pwd = getParameter("pwd");
        log.info("into GameUserAction.login");
        log.info("phonenum=" + phonenum + ", imei=" + imei + ", pwd=" + pwd);
        try {
            if (StrUtils.isEmpty(phonenum) || StrUtils.isEmpty(pwd)
                    || StrUtils.isEmpty(imei)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                /** 2015-8-11 修改 ： 密码进行MD5加密 */
                List<Map<String, Object>> user = gameUserService.login(phonenum,
                        MD5.md5Code(pwd));
                if (user.size() > 0) {
                    reqMap.put("status", "Y");
                    user.get(0).put("C_PASSWORD", pwd);// 返回前台的密码不使用加密
                    reqMap.put("userinfo", user);
                    Map<String, Object> m = user.get(0);
                    // 获取触键注册用户与Imei对应关系记录
                    List<Map<String, Object>> userImei = gameUserService
                            .getMemberImei(m.get("C_REGID").toString(), imei);
                    if (userImei.size() > 0) {
                        gameUserService.updateIsLogin(m.get("C_REGID")
                                .toString(), imei); // 当前为最新
                    } else {
                        // 新增触键注册用户登录记录（IMEI）
                        gameUserService.insertImei(m.get("C_ID").toString(), m
                                .get("C_REGID").toString(), imei);
                    }
                    gameUserService.updateIsNotLogin(
                            m.get("C_REGID").toString(), imei); // 修改其他不是最新
                } else {
                    reqMap.put("status", "N");
                    reqMap.put("info", "1008");
                }
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("GameUserAction.login failed,", e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 修改密码
    public String modifyPwd() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        String uid = getParameter("uid");
        String opwd = getParameter("opwd");
        String npwd = getParameter("npwd");
        log.info("into GameUserAction.modifyPwd");
        log.info("uid=" + uid + ", opwd=" + opwd + ", npwd=" + npwd);
        try {
            if (StrUtils.isEmpty(uid) || StrUtils.isEmpty(opwd)
                    || StrUtils.isEmpty(npwd)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                /** 2015-8-11 修改 ： 密码进行MD5加密 */
                opwd = MD5.md5Code(opwd);
                npwd = MD5.md5Code(npwd);
                // 根据用户id和原密码查询用户
                List<Map<String, Object>> list = gameUserService.findUserByUidNPwd(
                        uid, opwd);
                if (null != list && list.size() > 0) {
                    // 如果正确，修改新密码
                    gameUserService.modifyPwd(uid, opwd, npwd);
                    reqMap.put("status", "Y");
                } else {
                    reqMap.put("status", "N");
                    reqMap.put("info", "1009");
                }
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("GameUserAction.login failed,", e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 编辑用户信息（只修改昵称和性别）
    public String editUserInfo() {
        Map<String, Object> retMap = new HashMap<String, Object>();
        String uid = getParameter("uid");
        String nickname = getParameter("nickname");
        String sex = getParameter("sex");
        log.info("into GameUserAction.editUserInfo");
        log.info("uid=" + uid + ", nickname=" + nickname + ", sex=" + sex);
        try {
            if (StrUtils.isEmpty(uid) || StrUtils.isEmpty(nickname)
                    || StrUtils.isEmpty(sex)) {
                retMap.put("status", "N");
                retMap.put("info", "1001");
            } else {
                // 修改
                gameUserService.editUserInfo(uid, nickname, sex);
                retMap.put("status", "Y");
            }
        } catch (Exception e) {
            retMap.put("status", "N");
            retMap.put("info", "1003, " + e.getMessage());
            log.error("GameUserAction.editUserInfo failed,", e);
        }
        out = JSONObject.fromObject(retMap).toString();
        return "success";
    }

    // 绑定手机
    public String bindPhone() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        String uid = getParameter("uid");
        String logintype = getParameter("logintype");
        String type = getParameter("type");
        String phonenum = getParameter("phonenum");
        String valicode = getParameter("valicode");
        String pwd = getParameter("pwd");
        log.info("into GameUserAction.bindPhone");
        log.info("uid=" + uid + ", logintype=" + logintype + ", type=" + type
                + ", phonenum=" + phonenum + ", valicode=" + valicode
                + " ,pwd=" + pwd);
        try {
            if (StrUtils.isEmpty(uid) || StrUtils.isEmpty(logintype)
                    || StrUtils.isEmpty(type) || StrUtils.isEmpty(phonenum)
                    || StrUtils.isEmpty(valicode)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                // 注册过的手机无法绑定
                List<Map<String, Object>> reglist = gameUserService
                        .findByPhone(phonenum);
                if (null != reglist && reglist.size() > 0) {
                    reqMap.put("status", "N");
                    reqMap.put("info", "1010");
                    out = JSONObject.fromObject(reqMap).toString();
                    return "success";
                }
                // 获取30min之前的时间
                String s_date = ApDateTime.dateAdd("mm", -30,
                        new java.util.Date(), ApDateTime.DATE_TIME_Sec);
                // 校验验证码是否可用
                List<Map<String, Object>> list = gameUserService.isUsableCode(
                        phonenum, valicode, s_date, type);
                if (list.isEmpty() && list.size() < 1) {
                    reqMap.put("status", "N");
                    reqMap.put("info", "1011");
                    out = JSONObject.fromObject(reqMap).toString();
                    return "success";
                }
                if (StrUtils.isNotEmpty(pwd)) {// 密码不为空，绑定手机
                    // 第三方绑定手机号
                    /** 2015-8-11 修改 ： 密码进行MD5加密 */
                    pwd = MD5.md5Code(pwd);
                    gameUserService.bindPhone(uid, logintype, phonenum, pwd);
                    // 生成logintype=0的信息（类似触键注册信息）
                    gameUserService.chujianUser(uid, "0", phonenum, pwd, "ICJ"
                            + uid);
                } else {// 密码为空，修改手机
                    gameUserService.updatePhonenum(uid, phonenum);
                }
                reqMap.put("status", "Y");

            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("GameUserAction.bindPhone failed,", e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 获取验证码
    public String getValiCode() {
        Map<String, Object> retMap = new HashMap<String, Object>();
        String imei = getParameter("imei");
        String phonenum = getParameter("phonenum");
        String type = getParameter("type");
        log.info("into GameUserAction.getValiCode");
        log.info("imei=" + imei + ", phonenum=" + phonenum + ", type=" + type);
        try {
            if (StrUtils.isEmpty(imei) || StrUtils.isEmpty(phonenum)
                    || StrUtils.isEmpty(type)) {
                retMap.put("status", "N");
                retMap.put("info", "1001");
            } else {
                if (type.equals("2")) { // 修改密码 没有注册 则不发送验证码
                    List<Map<String, Object>> list = gameUserService
                            .findByPhone(phonenum);
                    if (list.size() < 1) {
                        retMap.put("status", "N");
                        retMap.put("info", "1013");
                        out = JSONObject.fromObject(retMap).toString();
                        return "success";
                    }
                }
                if (type.equals("1")) { // 注册 手机号是否已经注册过
                    List<Map<String, Object>> list = gameUserService
                            .findByPhone(phonenum);
                    if (list.size() > 0) {
                        retMap.put("status", "N");
                        retMap.put("info", "1010");
                        out = JSONObject.fromObject(retMap).toString();
                        return "success";
                    }
                }
                // 格式化日期："年-月-日"
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                // 获取当天发送的验证码
                List<Map<String, Object>> list = gameUserService.getValiCode(
                        phonenum, type, sdf.format(new Date()));
                if (list.size() >= 5) {
                    retMap.put("status", "N");
                    retMap.put("info", "1015");
                    out = JSONObject.fromObject(retMap).toString();
                    return "success";
                } else {
                    String host = FileUtil.getSmspath("sms.zt.sender.host");
                    String username = FileUtil
                            .getSmspath("sms.zt.sender.username");
                    String password = FileUtil
                            .getSmspath("sms.zt.sender.password");
                    String productid = FileUtil
                            .getSmspath("sms.zt.sender.productid");
                    String sendnumber = FileUtil
                            .getSmspath("sms.zt.sender.sendnumber");
                    // 获取6位随机数验证码
                    String code = RandNumber.getRandNumber(6);
                    com.cyberoller.sms.MessageSender messageSender = new MessagePostSender(
                            host, username, password, productid);
                    String message = "亲爱的触键用户：手机验证码为" + code
                            + "，请提交完成验证。如非本人操作，请忽略本短信。【触键】";
                    messageSender.send(phonenum, message); // 发送验证码
                    gameUserService.invalidatedOther(phonenum, type); // 修改其他验证码状态
                    gameUserService.insertValicode(imei, phonenum, type,
                            new Date(), code);// 保存本次发送的验证码
                    retMap.put("status", "Y");
                    retMap.put("sendnumber", sendnumber);
                }
            }
        } catch (Exception e) {
            retMap.put("status", "N");
            retMap.put("info", "1003, " + e.getMessage());
            log.error("GameUserAction.getValiCode failed,", e);
        }
        out = JSONObject.fromObject(retMap).toString();
        return "success";
    }

    // 忘记密码-判断验证码的有效性
    public String isValidCode() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        String phonenum = getParameter("phonenum");
        String valicode = getParameter("valicode");
        String type = getParameter("type");
        String imei = getParameter("imei");
        log.info("into GameUserAction.isValidCode");
        log.info("phonenum=" + phonenum + ", valicode=" + valicode + ", type="
                + type + ", imei=" + imei);
        try {
            if (StrUtils.isEmpty(valicode) || StrUtils.isEmpty(phonenum)
                    || StrUtils.isEmpty(type) || StrUtils.isEmpty(imei)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                // 获取30min之前的时间
                String s_date = ApDateTime.dateAdd("mm", -30,
                        new java.util.Date(), ApDateTime.DATE_TIME_Sec);
                // 校验验证码是否可用
                List<Map<String, Object>> code = gameUserService.isUsableCode(
                        phonenum, valicode, s_date, type);
                if (code.isEmpty() && code.size() < 1) {
                    reqMap.put("status", "N");
                    reqMap.put("info", "1011");
                    out = JSONObject.fromObject(reqMap).toString();
                    return "success";
                }
                // 查询手机号是否存在
                List<Map<String, Object>> user = gameUserService
                        .findByPhone(phonenum);
                if (user.size() > 0) {
                    reqMap.put("status", "Y");
                } else {
                    reqMap.put("status", "N");
                    reqMap.put("info", "1013");
                }
                // 修改验证码状态为0
                gameUserService.invalidatedOther(phonenum, type);
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("GameUserAction.isValidCode failed,", e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 忘记密码-修改密码
    public String setPwdOfFgt() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        String phonenum = getParameter("phonenum");
        String pwd = getParameter("pwd");
        log.info("into GameUserAction.setPwdOfFgt");
        log.info("phonenum=" + phonenum + ", pwd=" + pwd);
        try {
            if (StrUtils.isEmpty(phonenum) || StrUtils.isEmpty(pwd)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                // 判断手机号是否已注册
                List<Map<String, Object>> list = gameUserService
                        .findByPhone(phonenum);
                if (list.size() > 0) {
                    /** 2015-8-11 修改 ： 密码进行MD5加密 */
                    pwd = MD5.md5Code(pwd);
                    // 设置新密码
                    gameUserService.setNewPwd(phonenum, pwd);
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
            log.error("GameUserAction.setPwdOfFgt failed,", e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 修改用户头像
    public String userHead() {
        Map<String, Object> retMap = new HashMap<String, Object>();
        String uid = getParameter("uid");
        InputStream userhead = getParameter3("userhead");// 将请求参数中的userhead读到流中
        log.info("into GameUserAction.userHead");
        log.info("uid=" + uid);
        try {
            if (StrUtils.isEmpty(uid)) {
                retMap.put("status", "N");
                retMap.put("info", "1001");
            } else {
                // String timestamp = "_" + System.currentTimeMillis();
                // 上传图片，并返回图片路径
                String fileurl = FileServices.saveHeadFile(userhead,
                        "game/image/userHead/" + uid + ".png");
                // 将头像路径保存到
                gameUserService.updateHead(uid, fileurl);
                retMap.put("status", "Y");
                retMap.put("userhead", fileurl);
            }
        } catch (Exception e) {
            retMap.put("status", "N");
            retMap.put("info", "1003, " + e.getMessage());
            log.error("GameUserAction.userHead failed,", e);
        }
        out = JSONObject.fromObject(retMap).toString();
        return "success";
    }

    // 校验手机号是否可用
    public String validatePhone() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        // 获取请求参数
        String phoneNum = this.getParameter("phoneNum");
        log.info("into GameUserAction.validatePhone");
        log.info("phoneNum = " + phoneNum);
        try {
            if (StrUtils.isEmpty(phoneNum)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                // 校验
                List<Map<String, Object>> list = gameUserService.findByPhone(phoneNum);
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
            log.error("GameUserAction.validatePhone failed,e : " + e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 获取游戏提示语
    public String gameCue() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        // 获取用户id
        String uid = getParameter("uid");
        // 获取登陆状态
        String loginType = getParameter("loginType");
        log.info("into GameUserAction.gameCue");
        log.info("uid=" + uid);
        try {
            if (StrUtils.isEmpty(uid)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            }
            Map<String, Object> item = new HashMap<String, Object>();
            // 获取用户头像、昵称
            List<Map<String, Object>> headImg = gameUserService.userHeadImg(uid,
                    loginType);
            if (null != headImg && headImg.size() > 0) {
                Map<String, Object> map = headImg.get(0);
                if (StrUtils.isNotEmpty(map.get("C_NICKNAME"))
                        && !"null".equalsIgnoreCase(map.get("C_NICKNAME")
                        .toString())) {
                    item.put("C_NICKNAME", map.get("C_NICKNAME"));
                } else if (StrUtils.isNotEmpty(map.get("C_REGID"))) {
                    item.put("C_NICKNAME", map.get("C_REGID"));
                } else if (StrUtils.isNotEmpty(map.get("C_PHONENUM"))) {
                    item.put("C_NICKNAME", map.get("C_PHONENUM"));
                } else {
                    item.put("C_NICKNAME", "");
                }
                item.put("C_HEADIMAGE", map.get("C_HEADIMAGE"));
            } else {
                item.put("C_NICKNAME", "");
                item.put("C_HEADIMAGE", "");
            }
            // 趣币总数
            Integer u = Integer.valueOf(uid);
            Integer totalScore = (Integer) gameUserService.getTotalScore(u);
 			// 礼包总数
 			Integer totalGift = gameUserService.getTotalGift(uid);
 			// 消息总数
 			Integer totalMess = gameUserService.getTotalMess(uid);
 			item.put("totalScore", StrUtils.zeroOrString(totalScore));
 			item.put("totalGift", StrUtils.zeroOrString(totalGift));
 			item.put("totalMess", StrUtils.zeroOrString(totalMess));
            reqMap.put("gameCue", item);
            reqMap.put("status", "Y");

        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("GameUserAction.editUserInfo failed,", e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    /**
     * 我的趣币
     *
     * @return
     */
    public String score() {
        if (log.isDebugEnabled()) {
            log.debug("into score ...");
        }

        // 用户ID
        String sUid = getParameter("uid");
        // 页码，每页显示数量
        String sPageNumber = getParameter("pageNumber"),
                sPageSize = getParameter("pageSize");
        int pageNumber = 1, pageSize = 20; // 默认第一页，每页20条数据
        Map<String, Object> result;
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (StringUtils.isEmpty(sUid)) {
            retMap.put(IGameConst.STATUS, IGameConst.NO);
            retMap.put(IGameConst.INFO, IGameConst._1001);
        } else {
            try {
                if (!isEmpty(sPageNumber)) {
                    pageNumber = Integer.parseInt(sPageNumber);
                }
                if (!isEmpty(sPageSize)) {
                    pageSize = Integer.parseInt(sPageSize);
                }

                result = gameUserService.score(Integer.parseInt(sUid), pageNumber, pageSize);

                retMap.put("score", result);
                retMap.put(IGameConst.STATUS, IGameConst.YES);
                retMap.put(IGameConst.INFO, IGameConst._1002);
            } catch (Exception e) {
                log.error(e);
                retMap.put(IGameConst.STATUS, IGameConst.NO);
                retMap.put(IGameConst.INFO, IGameConst._1003);
            }
        }


        try {
            writeToResponse(JSONObject.fromObject(retMap).toString());
        } catch (IOException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
        }

        return NONE;
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

		Map<String, Object> retMap = new HashMap<String, Object>();

		if (StringUtils.isEmpty(sUid)) {
			retMap.put(IGameConst.STATUS, IGameConst.NO);
			retMap.put(IGameConst.INFO, IGameConst._1001);
		} else {
			try {
				gameUserService.contactServiceRoute(sUid, message, imgArr);

				retMap.put(IGameConst.STATUS, IGameConst.YES);
				retMap.put(IGameConst.INFO, IGameConst._1002);
			} catch (Exception e) {
				log.error(e);
				retMap.put(IGameConst.STATUS, IGameConst.NO);
				retMap.put(IGameConst.INFO, IGameConst._1003);
			}
		}

		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
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
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (StringUtils.isEmpty(sUid)) {
            retMap.put(IGameConst.STATUS, IGameConst.NO);
            retMap.put(IGameConst.INFO, IGameConst._1001);
        } else {
            try {
                if (!isEmpty(sPageNumber)) {
                    pageNumber = Integer.parseInt(sPageNumber);
                }
                if (!isEmpty(sPageSize)) {
                    pageSize = Integer.parseInt(sPageSize);
                }

                result = gameUserService.getMessage(Integer.parseInt(sUid), pageNumber, pageSize);

                retMap.put("message", result);
                retMap.put(IGameConst.STATUS, IGameConst.YES);
                retMap.put(IGameConst.INFO, IGameConst._1002);
            } catch (Exception e) {
                log.error(e);
                retMap.put(IGameConst.STATUS, IGameConst.NO);
                retMap.put(IGameConst.INFO, IGameConst._1003);
            }
        }

        try {
            writeToResponse(JSONObject.fromObject(retMap).toString());
        } catch (IOException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
        }

        return NONE;
    }
}
