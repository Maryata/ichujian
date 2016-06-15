package com.sys.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import oracle.net.aso.a;
import oracle.net.aso.i;

import com.sys.commons.AbstractAction;
import com.sys.service.UseActivityService;
import com.sys.util.StrUtils;

public class UseActivityAction extends AbstractAction{
	
	/**输出内容*/
	private String out;
	
	UseActivityService useActivityService;
	
	//选择行为记录
	public String ChooseInfo(){
		Map<String,Object> retMap = new HashMap<String,Object>();
		String uid=getParameter("uid");
		String type=getParameter("type");
		String indexid=getParameter("indexid");
		log.info("into ChooseInfo");
		log.info("--uid--"+uid+"--type--"+type+"--indexid--"+indexid);
		try {
			if(StrUtils.isEmpty(type)||StrUtils.isEmpty(indexid)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    			out=JSONObject.fromObject(retMap).toString();
    			return "success";
			}
			useActivityService.ChooseInfo(uid, type, indexid);
			retMap.put("status", "Y");
		} catch (Exception e) {
			// TODO: handle exception
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("ChooseInfo failed",e);
		}
		out=JSONObject.fromObject(retMap).toString();
		return "success";
	}
	
	//查看行为记录
	public String SelectInfo(){
		Map<String,Object> retMap = new HashMap<String,Object>();
		String uid=getParameter("uid");
		String type=getParameter("type");
		String action=getParameter("action");
		String indexid=getParameter("indexid");
		log.info("into ChooseInfo");
		log.info("--uid--"+uid+"--type--"+type+"--action--"+action+"--indexid--"+indexid);
		try {
			if(StrUtils.isEmpty(uid)||StrUtils.isEmpty(type)||StrUtils.isEmpty(indexid)||StrUtils.isEmpty(action)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    			out=JSONObject.fromObject(retMap).toString();
    			return "success";
			}
			useActivityService.SelectInfo(uid, type, action,indexid);
			retMap.put("status", "Y");
		} catch (Exception e) {
			// TODO: handle exception
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("ChooseInfo failed",e);
		}
		out=JSONObject.fromObject(retMap).toString();
		return "success";
	}

	public UseActivityService getUseActivityService() {
		return useActivityService;
	}

	public void setUseActivityService(UseActivityService useActivityService) {
		this.useActivityService = useActivityService;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
}
