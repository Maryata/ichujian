package com.sys.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyberoller.sms.zt.MessagePostSender;
import com.sys.commons.AbstractAction;
import com.sys.service.MemberService;
import com.sys.util.ApDateTime;
import com.sys.util.FileUtil;
import com.sys.util.MD5;
import com.sys.util.RandNumber;
import com.sys.util.StrUtils;

/**
 * 触键用户
 * 
 * @author Maryn
 * 
 */
@Component
public class MemberAction extends AbstractAction {

	private static final long serialVersionUID = -6165445129747337644L;

	@Autowired
	private MemberService memberService;

	/** 输出内容 */
	private String out;

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
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
		String supcode = this.getParameter("supcode");// 激活码
		log.info("into MemberAction.register");
		log.info("imei = " + imei + ", phonenum = " + phonenum + ", pwd = " + pwd
				+ ", valicode = " + valicode + ", supcode = " + supcode);
		try {
			if (StrUtils.isEmpty(imei) || StrUtils.isEmpty(phonenum)
					|| StrUtils.isEmpty(pwd) || StrUtils.isEmpty(valicode) 
					|| StrUtils.isEmpty(type) || StrUtils.isEmpty(supcode)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
				out = JSONObject.fromObject(reqMap).toString();
				return "success";
			} else {
				// 获取30min之前的时间
				String s_date = ApDateTime.dateAdd("mm", -30,
						new java.util.Date(), ApDateTime.DATE_TIME_Sec);
				// 校验验证码是否可用
				List<Map<String, Object>> list = memberService.isUsableCode(
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
				String regid = memberService.register(phonenum, MD5.md5Code(pwd), supcode);
				// 拼接完整的注册id
				regid = "ICJ" + regid;
				// 获取用户id
				String uid = memberService.getUid(regid);
				// 生成默认昵称
				memberService.defaultNickname("ICJ" + uid, regid);

				// 新增注册用户IMEI信息
				memberService.insertImei(regid, imei, valicode, uid);

				reqMap.put("status", "Y");
				reqMap.put("regid", regid);
				reqMap.put("uid", uid);
				reqMap.put("info", "1012");
			}
		} catch (Exception e) {
			reqMap.put("status", "N");
			reqMap.put("info", "1003, " + e.getMessage());
			log.error("MemberAction.register failed,e : " + e);
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
		log.info("into MemberAction.login");
		log.info("phonenum=" + phonenum + ", imei=" + imei + ", pwd=" + pwd);
		try {
			if (StrUtils.isEmpty(phonenum) || StrUtils.isEmpty(pwd)
					|| StrUtils.isEmpty(imei)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
				/** 2015-8-11 修改 ： 密码进行MD5加密 */
				List<Map<String, Object>> user = memberService.login(phonenum,
						MD5.md5Code(pwd));
				if (user.size() > 0) {
					Map<String, Object> m = user.get(0);
					reqMap.put("status", "Y");
					m.put("C_PASSWORD", pwd);// 返回前台的密码不使用加密
					reqMap.put("userinfo", user);
					// 获取触键注册用户与Imei对应关系记录
					List<Map<String, Object>> userImei = memberService
							.getMemberImei(m.get("C_REGID").toString(), imei);
					if (userImei.size() > 0) {
						memberService.updateIsLogin(m.get("C_REGID").toString(), imei); // 当前为最新
					} else {
						// 新增触键注册用户登录记录（IMEI）
						memberService.insertImei(m.get("C_REGID").toString(), imei, "", m.get("C_ID").toString());
					}
					memberService.updateIsNotLogin(m.get("C_REGID").toString(), imei); // 修改其他不是最新
				} else {
					reqMap.put("status", "N");
					reqMap.put("info", "1008");
				}
			}
		} catch (Exception e) {
			reqMap.put("status", "N");
			reqMap.put("info", "1003, " + e.getMessage());
			log.error("MemberAction.login failed,", e);
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
		log.info("into MemberAction.modifyPwd");
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
				List<Map<String, Object>> list = memberService.findUserByUidNPwd(uid, opwd);
				if (null != list && list.size() > 0) {
					// 如果正确，修改新密码
					memberService.modifyPwd(uid, opwd, npwd);
					reqMap.put("status", "Y");
				} else {
					reqMap.put("status", "N");
					reqMap.put("info", "1009");
				}
			}
		} catch (Exception e) {
			reqMap.put("status", "N");
			reqMap.put("info", "1003, " + e.getMessage());
			log.error("MemberAction.login failed,", e);
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
		log.info("into MemberAction.getValiCode");
		log.info("imei=" + imei + ", phonenum=" + phonenum + ", type=" + type);
		try {
			if (StrUtils.isEmpty(imei) || StrUtils.isEmpty(phonenum)
					|| StrUtils.isEmpty(type)) {
				retMap.put("status", "N");
				retMap.put("info", "1001");
			} else {
				if (type.equals("2")) { // 修改密码 没有注册 则不发送验证码
					List<Map<String, Object>> list = memberService.findByPhone(phonenum);
					if (list.size() < 1) {
						retMap.put("status", "N");
						retMap.put("info", "1013");
						out = JSONObject.fromObject(retMap).toString();
						return "success";
					}
				}
				if (type.equals("1")) { // 注册 手机号是否已经注册过
					List<Map<String, Object>> list = memberService.findByPhone(phonenum);
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
				List<Map<String, Object>> list = memberService.getValiCode(phonenum, type, sdf.format(new Date()));
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
					memberService.invalidatedOther(phonenum, type); // 修改其他验证码状态
					memberService.insertValicode(imei, phonenum, type, new Date(), code);// 保存本次发送的验证码
					retMap.put("status", "Y");
					retMap.put("sendnumber", sendnumber);
				}
			}
		} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003, " + e.getMessage());
			log.error("MemberAction.getValiCode failed,", e);
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
		log.info("into MemberAction.isValidCode");
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
				List<Map<String, Object>> code = memberService.isUsableCode(
						phonenum, valicode, s_date, type);
				if (code.isEmpty() && code.size() < 1) {
					reqMap.put("status", "N");
					reqMap.put("info", "1011");
					out = JSONObject.fromObject(reqMap).toString();
					return "success";
				}
				// 查询手机号是否存在
				List<Map<String, Object>> user = memberService.findByPhone(phonenum);
				if (user.size() > 0) {
					reqMap.put("status", "Y");
				} else {
					reqMap.put("status", "N");
					reqMap.put("info", "1013");
				}
				// 修改验证码状态为0
				memberService.invalidatedOther(phonenum, type);
			}
		} catch (Exception e) {
			reqMap.put("status", "N");
			reqMap.put("info", "1003, " + e.getMessage());
			log.error("MemberAction.isValidCode failed,", e);
		}
		out = JSONObject.fromObject(reqMap).toString();
		return "success";
	}

	// 忘记密码-修改密码
	public String setPwdOfFgt() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		String phonenum = getParameter("phonenum");
		String pwd = getParameter("pwd");
		log.info("into MemberAction.setPwdOfFgt");
		log.info("phonenum=" + phonenum + ", pwd=" + pwd);
		try {
			if (StrUtils.isEmpty(phonenum) || StrUtils.isEmpty(pwd)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
				// 判断手机号是否已注册
				List<Map<String, Object>> list = memberService.findByPhone(phonenum);
				if (list.size() > 0) {
					/** 2015-8-11 修改 ： 密码进行MD5加密 */
					pwd = MD5.md5Code(pwd);
					// 设置新密码
					memberService.setNewPwd(phonenum, pwd);
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
			log.error("MemberAction.setPwdOfFgt failed,", e);
		}
		out = JSONObject.fromObject(reqMap).toString();
		return "success";
	}
	
	// 校验手机号是否可用
 	public String validatePhone(){
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		// 获取请求参数
 		String phoneNum = this.getParameter("phoneNum");
 		log.info("into MemberAction.validatePhone");
 		log.info("phoneNum = " + phoneNum);
 		try {
 			if (StrUtils.isEmpty(phoneNum)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
	 			// 校验
				List<Map<String,Object>> list = memberService.findByPhone(phoneNum);
	 			if(null!=list && list.size()>0){
	 				reqMap.put("info", "1010");// 不可用
	 				reqMap.put("status", "N");
	 			}else{
	 				reqMap.put("status", "Y");
	 			}
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("MemberAction.validatePhone failed,e : " + e);
 		}
 		out = JSONObject.fromObject(reqMap).toString();
 		return "success";
 	}
}
