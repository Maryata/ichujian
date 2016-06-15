package com.sys.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.sys.commons.AbstractAction;
import com.sys.service.PhoneRecordService;
import com.sys.util.StrUtils;

/**
 * 手机号记录接口
 * @author Maryn
 *
 */
@SuppressWarnings("serial")
public class PhoneRecordAction extends AbstractAction{
	
	private PhoneRecordService phoneRecordService;
	
	/**输出内容*/
	private String out;
	
	public PhoneRecordService getPhoneRecordService() {
		return phoneRecordService;
	}
	public void setPhoneRecordService(PhoneRecordService phoneRecordService) {
		this.phoneRecordService = phoneRecordService;
	}
	public String getOut() {
		return out;
	}
	public void setOut(String out) {
		this.out = out;
	}
	
	public String recordPhone(){
		Map<String, Object> reqMap = new HashMap<String, Object>();
		// 获取请求参数
		String imei = this.getParameter("imei");
		String uEmail = this.getParameter("uEmail");
		log.info("into PhoneRecordAction.recordPhone");
		log.info("imei=" + imei + " ,uEmail=" + uEmail);
		try {
			if (StrUtils.isEmpty(imei) || StrUtils.isEmpty(uEmail)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
				// 记录手机号
				phoneRecordService.recordPhone(imei,uEmail);
				reqMap.put("status", "Y");
			}
		} catch (Exception e) {
			reqMap.put("status", "N");
			reqMap.put("info", "1003," + e.getMessage());
			log.error("GameAction.recordPhone failed,e : " + e);
		}
		// 转成JSON
		out = JSONObject.fromObject(reqMap).toString();
		return "success";
		
	}
	
	// 记录IMEI（内测）
	public String recordImei(){
		Map<String, Object> reqMap = new HashMap<String, Object>();
		// 获取请求参数
		String imei = this.getParameter("imei");
		log.info("into PhoneRecordAction.recordImei");
		log.info("imei=" + imei);
		try {
			if (StrUtils.isEmpty(imei)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
				// 记录手机imei
				phoneRecordService.recordImei(imei);
				reqMap.put("status", "Y");
			}
		} catch (Exception e) {
			reqMap.put("status", "N");
			reqMap.put("info", "1003," + e.getMessage());
			log.error("GameAction.recordImei failed,e : " + e);
		}
		// 转成JSON
		out = JSONObject.fromObject(reqMap).toString();
		return "success";
	}
}
