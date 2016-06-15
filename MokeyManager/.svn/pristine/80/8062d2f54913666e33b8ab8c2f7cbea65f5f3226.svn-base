package com.org.mokey.basedata.baseinfo.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.baseinfo.service.ActionActiveService;
import com.org.mokey.basedata.baseinfo.service.ActionFeedbackService;
import com.org.mokey.common.AbstractAction;

/**
 * 基础信息-反馈信息查询
 * @author giles
 *
 */
public class ActionFeedbackAction extends AbstractAction {

	private ActionFeedbackService actionFeedbackService;
	
	/**输出内容*/
	private String out;
	
	/**
	 * 激活详细列表查询
	 * @return
	 */
	public String getFeedbackList() {
		log.debug("getFeedbackList init");
		Map<String,Object> retMap = new HashMap<String,Object>();

		String time_s = getParameter("time_s");
		String time_e = getParameter("time_e");
		
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		try{
			retMap = actionFeedbackService.getFeedbackListMap(time_s, time_e,start,limit);
			
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


	public ActionFeedbackService getActionFeedbackService() {
		return actionFeedbackService;
	}

	public void setActionFeedbackService(ActionFeedbackService actionFeedbackService) {
		this.actionFeedbackService = actionFeedbackService;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
}
