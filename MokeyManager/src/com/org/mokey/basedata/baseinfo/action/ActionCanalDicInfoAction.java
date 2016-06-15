package com.org.mokey.basedata.baseinfo.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.baseinfo.service.ActionCanalDicInfoService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.util.JSONUtil;

public class ActionCanalDicInfoAction extends AbstractAction{
	
	private ActionCanalDicInfoService actionCanalDicInfoService;
	
	
	public ActionCanalDicInfoService getActionCanalDicInfoService() {
		return actionCanalDicInfoService;
	}
	public void setActionCanalDicInfoService(
			ActionCanalDicInfoService actionCanalDicInfoService) {
		this.actionCanalDicInfoService = actionCanalDicInfoService;
	}
	//获取数据
	public String getActionCanalDicInfoList(){
		log.debug("init getActionPackageInfo");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String c_name=getParameter("c_name");
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		try {
			retMap=this.actionCanalDicInfoService.getActionCanalDicInfoListMap(c_name, start, limit);
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("getActionPackageInfoList",e);
		}
		log.debug("out getActionPackageInfoList");
		return NONE;
	}
	
	//添加
	public String saveActionCanalDicInfo(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String saveParam =getParameter("saveParam");
		Map<String, Object> saveMap =(Map<String, Object>) JSONUtil.JSONString2Bean(saveParam, Map.class);

		int count =this.actionCanalDicInfoService.checkActionCanalDicInfo(saveMap.get("c_canalid").toString(),saveMap.get("c_name").toString(),"");
		
		if(count>0){
			retMap.put("status", "表名和渠道唯一！！");
		}else{
			String eareinfoId = this.actionCanalDicInfoService.savaActionCanalDicInfo(saveMap);
			retMap.put("status", "Y");
			retMap.put("c_id", eareinfoId);
			retMap.put("success", true);			
		}
		
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			log.error("saveActionEareInfo failed,",e);
		}
		return NONE;
	}
	//删除
	public String deleteActionCanalDicInfo(){
		Map<String, Object> retMap= new HashMap<String, Object>();
		String c_id = getParameter("c_id");

		try {
			this.actionCanalDicInfoService.deleteActionCanalDicInfo(c_id);
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retMap.put("status", "N");
			retMap.put("info", "删除失败,"+e.getMessage());
			log.error("deleteActionPackageInfo failed,",e);
		}
		return NONE;
	}

}
