package com.sys.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.sys.commons.AbstractAction;
import com.sys.service.WebActionService;
import com.sys.util.StrUtils;

public class WebAction extends AbstractAction {

	WebActionService webActionService;

	/**输出内容*/
	private String out;
	
	public String actInfo() {
		Map<String,Object> retMap=new HashMap<String, Object>();
		String id=getParameter("id");
		log.info("into actInfo -----id="+id);
		try {
			if(StrUtils.isEmpty(id)){
				retMap.put("status", "N");
				retMap.put("info", "1001");
			}else{
				int activity=webActionService.getActivityInfo(id);
				int brand=webActionService.getBrandInfo(id);
				retMap.put("status", "Y");
				retMap.put("actLook", activity);
				retMap.put("brandFocus", brand);
			}
		} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("actInfo failed,",e);
		}
		out=JSONObject.fromObject(retMap).toString();
		return "success";
	}
		
	
	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public WebActionService getWebActionService() {
		return webActionService;
	}

	public void setWebActionService(WebActionService webActionService) {
		this.webActionService = webActionService;
	}	
	
}
