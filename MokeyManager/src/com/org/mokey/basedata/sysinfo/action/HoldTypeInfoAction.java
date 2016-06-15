package com.org.mokey.basedata.sysinfo.action;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import com.org.mokey.basedata.sysinfo.service.HoldTypeInfoService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.file.FileServices;
import com.org.mokey.common.util.file.FileUtil;
import com.org.mokey.util.JSONUtil;
import com.org.mokey.util.StrUtils;

/**
 * 长按类型管理
 * @author zhangtong
 *
 */
public class HoldTypeInfoAction extends AbstractAction {

	private HoldTypeInfoService holdTypeService;
	
	/**输出内容*/
	private String out;
	
	/**
	 * 列表查询
	 * @return
	 */
	public String getHoldTypeInfoList() {
		Map<String,Object> retMap = new HashMap<String,Object>();

		String c_type = getParameter("c_type");//????
		String c_name = getParameter("c_name");
		
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		try{
			retMap = holdTypeService.getHoldtypeInfoListMap(c_name,start,limit);
			
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "getHoldTypeInfoList failed");
			log.error("getHoldTypeList failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	
	/**
	 * 验证ID
	 * @return
	 */
	public String checkHoldTypeID(){
		log.debug("into checkHoldTypeID");
		Map<String,Object> retMap = new HashMap<String,Object>();
		String id=getParameter("id");
		try {
			retMap=holdTypeService.checkHoldTypeID(id);
			retMap.put("status", "Y");
		} catch (Exception e) {
			// TODO: handle exception
			retMap.put("status", "N");
			log.error("checkHoldTypeID failed",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	
	
	/**
	 *验证类型 
	 * @return
	 */
	public String checkHoldTypeInfo() {
		log.debug("init checkHoldTypeInfo");
		Map<String,Object> retMap = new HashMap<String,Object>();
		String name = getParameter("name");
		String id = getParameter("id");
		try{
			retMap = holdTypeService.checkHoldtypeInfo(name,id);
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "checkHoldTypeInfo failed");
			log.error("checkHoldTypeInfo failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.debug("out checkHoldTypeInfo:"+retMap);
		return SUCCESS;
	}
	
	/**
	 * 保存数据
	 * @return
	 */
	public String saveHoldTypeInfo() {
		log.debug("init saveHoldTypeInfo");
		Map<String,Object> retMap = new HashMap<String,Object>();

		String saveParam = getParameter("saveParam");
		
		Map<String,Object> saveMap = (Map) JSONUtil.JSONString2Bean(saveParam, Map.class);
		
		try{	
			holdTypeService.saveHoldtypeInfo(saveMap);
			retMap.put("status", "Y");
			retMap.put("success", true);
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "saveHoldTypeInfo failed");
			log.error("saveHoldTypeInfo failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.debug("out saveHoldTypeInfo");
		return SUCCESS;
	}
	
	/**
	 * 删除数据
	 * @return
	 */
	public String deleteHoldTypeInfo() {
		Map<String,Object> retMap = new HashMap<String,Object>();
		String c_id = getParameter("c_id");
		try{
			
			holdTypeService.deleteHoldtypeInfo(c_id);
			
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "删除失败,"+e.getMessage());
			log.error("deleteHoldTypeInfo failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}



	


	public HoldTypeInfoService getHoldTypeService() {
		return holdTypeService;
	}

	public void setHoldTypeService(HoldTypeInfoService holdTypeService) {
		this.holdTypeService = holdTypeService;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
}
