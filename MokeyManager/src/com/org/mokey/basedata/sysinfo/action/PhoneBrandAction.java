package com.org.mokey.basedata.sysinfo.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.sysinfo.service.PhoneBrandService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.util.JSONUtil;

/**
 * 手机品牌
 * @author lenovo
 * 
 */
public class PhoneBrandAction extends AbstractAction {

	private PhoneBrandService phoneBrandService;
	
	/**输出内容*/
	private String out;
	
	/**
	 * 品牌列表查询
	 * @return
	 */
	public String getBrandInfoList() {
		Map<String,Object> retMap = new HashMap<String,Object>();

		String c_name = getParameter("c_name");
		
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		try{
			retMap = phoneBrandService.getBrandListMap(c_name,start,limit);
			
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "getBrandInfoList failed");
			log.error("getBrandInfoList failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	
	
	/**
	 * 保存数据
	 * @return
	 */
	public String saveBrandInfo() {
		log.debug("init saveBrandInfo");
		Map<String,Object> retMap = new HashMap<String,Object>();

		String saveParam = getParameter("saveParam");
		
		Map<String,Object> saveMap = (Map) JSONUtil.JSONString2Bean(saveParam, Map.class);
		
		try{
			
			String cId = phoneBrandService.saveBrandInfo(saveMap);
			
			retMap.put("cId", cId);
			retMap.put("status", "Y");
			retMap.put("success", true);
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "saveBrandInfo failed");
			log.error("saveBrandInfo failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.debug("out saveBrandInfo");
		return SUCCESS;
	}
	
	/**
	 * 删除数据
	 * @return
	 */
	public String deleteBrandInfo() {
		Map<String,Object> retMap = new HashMap<String,Object>();
		String c_id = getParameter("c_id");
		try{
			
			phoneBrandService.deleteBrandInfo(c_id);
			
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "删除失败,"+e.getMessage());
			log.error("deleteBrandInfo failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public PhoneBrandService getPhoneBrandService() {
		return phoneBrandService;
	}

	public void setPhoneBrandService(PhoneBrandService phoneBrandService) {
		this.phoneBrandService = phoneBrandService;
	}
	

}
