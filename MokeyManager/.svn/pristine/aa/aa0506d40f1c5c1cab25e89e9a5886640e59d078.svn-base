package com.org.mokey.analyse.action;

import java.util.List;
import java.util.Map;

import com.org.mokey.analyse.service.ActOnlineStatusService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.util.StrUtils;

/**
 * 活动上下线情况
 */
@SuppressWarnings("serial")
public class ActOnlineStatusAction extends AbstractAction {

	private ActOnlineStatusService actOnlineStatusService;

	public ActOnlineStatusService getActOnlineStatusService() {
		return actOnlineStatusService;
	}

	public void setActOnlineStatusService(
			ActOnlineStatusService actOnlineStatusService) {
		this.actOnlineStatusService = actOnlineStatusService;
	}

	// 活动上下线情况
	public String actOnlineStatus(){
		// 获取请求参数
		String sDate = getParameter("sDate");
		String eDate = getParameter("eDate");
		if(StrUtils.isEmpty(sDate)){// 默认起始时间为今天的六天前
			sDate = ApDateTime.dateAdd("d", -6, new java.util.Date(), ApDateTime.DATE_TIME_YMD);
		}
		if(StrUtils.isEmpty(eDate)){// 默认截止日期为今天
			eDate = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_YMD);
		}
		// SQL语句需要的截止日期，需要在截止日期上+1
		String eDateEx = ApDateTime.dateAdd("d", 1, eDate, ApDateTime.DATE_TIME_YMD);
		
		getRequest().setAttribute("sDate", sDate);
		getRequest().setAttribute("eDate", eDate);
		log.info("into ActOnlineStatusAction.actOnlineStatus");
		log.info("date=" + sDate + " ,eDate=" + eDate);
		try{
			// 上线数量
			List<Map<String,Object>> online = actOnlineStatusService.actOnlineStatus("C_ACTIONDATE",sDate,eDateEx);
			// 下线数量
			List<Map<String,Object>> offline = actOnlineStatusService.actOnlineStatus("C_EDATE",sDate,eDateEx);
			// 存入值栈
			getRequest().setAttribute("online", online);
			getRequest().setAttribute("offline", offline);
		}catch(Exception e){
			log.error("ActOnlineStatusAction.actOnlineStatus failed,",e);
		}
		return "success";
	}

}
