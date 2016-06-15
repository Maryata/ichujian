package com.org.mokey.basedata.sysinfo.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.org.mokey.basedata.sysinfo.service.AdvertInfoService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.file.FileServices;
import com.org.mokey.util.Cn2Spell;
import com.org.mokey.util.JSONUtil;
import com.org.mokey.util.StrUtils;

/**
 * 广告位管理
 * @author giles
 *
 */
public class AdvertInfoAction extends AbstractAction {

	private static final long serialVersionUID = 4992589763898373742L;

	private AdvertInfoService advertInfoService;
	
	/**输出内容*/
	private String out;
	
	/**
	 * app列表查询
	 * @return
	 */
	public String getAdvertIfoList() {
		Map<String,Object> retMap = new HashMap<String,Object>();
		
		String c_type = getParameter("c_type");
		String c_name = getParameter("c_name");
		
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		try{
			retMap = advertInfoService.getAdvertIfoListMap(c_type, c_name,start,limit);
			
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "getAdvertIfoList failed");
			log.error("getAppIfoList failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	
	/**
	 * 查询类型对应的数据（游戏/商品/礼包等）
	 * @return
	 */
	public String getListByType() {
		Map<String,Object> retMap = new HashMap<String,Object>();
		
		String type = getParameter("type");
		
		try{
			List<Map<String, Object>> list = advertInfoService.getListByType(type);
			
			retMap.put("list", list);
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "getListByType failed");
			log.error("getListByType failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	
	public String checkAdvertIfo() {
		log.debug("init checkAdvertIfo");
		Map<String,Object> retMap = new HashMap<String,Object>();

		String checkType = getParameter("checkType");
		String value = getParameter("value");
		String idVal = getParameter("idVal");
		try{
			if(checkType.equalsIgnoreCase("c_picurl")){
				//2个类别的app分开保存，所以分2类目录
//				String appType = FileServices.getAppType(getParameter("appType"));
				String appType = "game";
				
				value = FileServices.getHttpRoot( "appinfo/"+appType+"advertpic/"+Cn2Spell.converterToSpellTrim(value));
			}
			retMap = advertInfoService.checkAdvertIfo(checkType, value, idVal);
			
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "checkAdvertIfo failed");
			log.error("checkAdvertIfo failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.debug("out checkAdvertIfo:"+retMap);
		return SUCCESS;
	}
	
	/**
	 * 保存数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String saveAdvertIfo() {
		log.debug("init saveAdvertIfo");
		Map<String,Object> retMap = new HashMap<String,Object>();

		String saveParam = getParameter("saveParam");
		
		Map<String,Object> saveMap = (Map<String,Object>) JSONUtil.JSONString2Bean(saveParam, Map.class);
		
		try{
			MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper)getRequest();
			if(multiWrapper.getFileNames("picurl")!=null){
				//2个类别的app分开保存，所以分2类目录
//				String appType = FileServices.getAppType(saveMap.get("c_type"));
				String appType = "game";
				String[] fileNames = multiWrapper.getFileNames("picurl");
				File[] files = multiWrapper.getFiles("picurl");
				String picNames = "";
				for(int i=0;i<files.length;i++){
					picNames += FileServices.saveFile(files[i], "appinfo/"+appType+"advertpic/"+Cn2Spell.converterToSpellTrim(fileNames[i]))+",";
				}
				
				if(StrUtils.isNotEmpty(picNames)){
					picNames = picNames.substring(0, picNames.length()-1);
				}
				saveMap.put("C_PICURL", picNames);
			}
			String cId = advertInfoService.saveAdvertInfo(saveMap);
			
			retMap.put("cId", cId);
			retMap.put("status", "Y");
			retMap.put("success", true);
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "saveAdvertIfo failed");
			log.error("saveAdvertIfo failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.debug("out saveAdvertIfo");
		return SUCCESS;
	}
	
	/**
	 * 删除数据
	 * @return
	 */
	public String deleteAdvertIfo() {
		Map<String,Object> retMap = new HashMap<String,Object>();
		String c_id = getParameter("c_id");
		try{
			advertInfoService.deleteAdvertIfo(c_id);
			
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "删除失败,"+e.getMessage());
			log.error("deleteAdvertIfo failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}

	public AdvertInfoService getAdvertInfoService() {
		return advertInfoService;
	}

	public void setAdvertInfoService(AdvertInfoService advertInfoService) {
		this.advertInfoService = advertInfoService;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
}
