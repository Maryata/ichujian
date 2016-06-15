package com.org.mokey.basedata.sysinfo.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.sysinfo.service.PushService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.util.JSONUtil;

/**
 * 消息推送
 * @author giles.wu
 */
public class PushAction extends AbstractAction {

	private PushService pushService;
	
	/**
	 * 发送消息查询
	 * @return
	 */
	public String getPushInfoList() {
		Map<String,Object> retMap = new HashMap<String,Object>();

		String queryParam = getParameter("queryParam");
		@SuppressWarnings("unchecked")
		Map<String,Object> queryMap = (Map<String,Object>) JSONUtil.JSONString2Bean(queryParam, Map.class);
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		try{
			retMap = pushService.getPushInfoListMap(queryMap,start,limit);
			
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "getPushInfoList failed");
			log.error("getPushInfoList failed,",e);
		}finally{
			try {
				writeToResponse(JSONObject.fromObject(retMap).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return NONE;
	}
	
	
	/**
	 * 保存数据
	 * @return
	 */
	public String savePushInfo() {
		//log.debug("init savePushInfo");
		Map<String,Object> retMap = new HashMap<String,Object>();
		String saveParam = getParameter("saveParam");
		Map<String,Object> saveMap = (Map) JSONUtil.JSONString2Bean(saveParam, Map.class);
		try{
			
			String cId = pushService.savePushInfo(saveMap);
			
			retMap.put("cId", cId);
			retMap.put("status", "Y");
			retMap.put("success", true);
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "savePushInfo failed");
			log.error("savePushInfo failed,",e);
		}finally{
			try {
				writeToResponse(JSONObject.fromObject(retMap).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return NONE;
	}
	
	/**
	 * 推送消息
	 * @return
	 */
	public String sendMessages() {
		//log.debug("init savePushInfo");
		Map<String,Object> retMap = new HashMap<String,Object>();
		String saveParam = getParameter("saveParam");
		Map<String,Object> messageMap = (Map) JSONUtil.JSONString2Bean(saveParam, Map.class);
		try{
			
			String cId = pushService.sendMessages(messageMap);
			
			retMap.put("cId", cId);
			retMap.put("status", "Y");
			retMap.put("success", true);
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "sendMessages failed");
			log.error("sendMessages failed,",e);
		}finally{
			try {
				writeToResponse(JSONObject.fromObject(retMap).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return NONE;
	}


	public PushService getPushService() {
		return pushService;
	}
	public void setPushService(PushService pushService) {
		this.pushService = pushService;
	}

}
