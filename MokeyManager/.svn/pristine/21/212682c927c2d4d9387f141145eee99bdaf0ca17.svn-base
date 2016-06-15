package com.org.mokey.basedata.baseinfo.action;

import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.Response;

import org.junit.runner.Request;

import net.sf.json.JSONObject;
import com.org.mokey.basedata.baseinfo.service.ActionActiveService;
import com.org.mokey.basedata.baseinfo.service.ActionDeviceInfoService;
import com.org.mokey.basedata.baseinfo.service.ActionFeedbackService;
import com.org.mokey.common.AbstractAction;


public class ActionDeviceInfoAction extends AbstractAction {

	private ActionDeviceInfoService actionDeviceInfoService;
	
	/**输出内容*/
	private String out;
	
	/**
	 * 激活详细列表查询
	 * @return
	 */
	public String getDeviceInfoList() {
		log.debug("getFeedbackList init");
		Map<String,Object> retMap = new HashMap<String,Object>();

		String time_s = getParameter("time_s");//*********
		String time_e = getParameter("time_e");//*******
		
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		try{
			retMap = actionDeviceInfoService.getDeviceInfoListMap(time_s, time_e,start,limit);

			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "getFeedbackListMap failed");
			log.error("getFeedbackListMap failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.debug("getFeedbackList out");
		return SUCCESS;
	}


	public ActionDeviceInfoService getActionDeviceInfoService() {
		return actionDeviceInfoService;
	}

	public void setActionDeviceInfoService(
			ActionDeviceInfoService actionDeviceInfoService) {
		this.actionDeviceInfoService = actionDeviceInfoService;
	}


	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
}
