package com.org.mokey.basedata.sysinfo.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.org.mokey.basedata.sysinfo.service.AppInfoService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.file.FileServices;
import com.org.mokey.util.Cn2Spell;
import com.org.mokey.util.JSONUtil;
import com.org.mokey.util.StrUtils;

/**
 * 新闻、阅读 APP管理
 * @author lenovo
 * 
 */
public class AppInfoAction extends AbstractAction {

	private AppInfoService appInfoService;
	
	/**输出内容*/
	private String out;
	
	/**
	 * app列表查询
	 * @return
	 */
	public String getAppIfoList() {
		Map<String,Object> retMap = new HashMap<String,Object>();

		String c_category = getParameter("c_category");
		String c_type = getParameter("c_type");
		String c_name = getParameter("c_name");
		
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		log.info("into AppInfoAction.getAppIfoList");
		log.info("c_name=" + c_name);
		try{
			retMap = appInfoService.getAppIfoListMap(c_category, c_type, c_name,start,limit);
			
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "getAppIfoList failed");
			log.error("getAppIfoList failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.info("out AppInfoAction.getAppIfoList:");
		return SUCCESS;
	}
	
	public String checkAppIfo() {
		log.debug("init checkAppIfo");
		Map<String,Object> retMap = new HashMap<String,Object>();

		String checkType = getParameter("checkType");
		String value = getParameter("value");
		String idVal = getParameter("idVal");
		log.info("into AppInfoAction.checkAppIfo");
		log.info("checkType=" + checkType + ", value=" + value + ", idVal=" + idVal);
		//2个类别的app分开保存，所以分2类目录
//		String appType = FileServices.getAppType(getParameter("appType"));
		String appType = "game";
		try{ 
			log.debug("checkType："+checkType + " , value:"+value);
			if(checkType.equalsIgnoreCase("c_logourl")){
				value = FileServices.getHttpRoot( "appinfo/"+appType+"logourl/"+Cn2Spell.converterToSpellTrim(value));
			}else if(checkType.equalsIgnoreCase("c_appurl")){
				value = FileServices.getHttpRoot( "appinfo/"+appType+"appurl/"+Cn2Spell.converterToSpellTrim(value));
			}else if(checkType.equalsIgnoreCase("c_picurl")){
				value = FileServices.getHttpRoot( "appinfo/"+appType+"pic/"+Cn2Spell.converterToSpellTrim(value));
			}
			
			retMap = appInfoService.checkAppIfo(checkType, value, idVal);
			
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "checkAppIfo failed");
			log.error("checkAppIfo failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.info("out checkAppIfo:");
		return SUCCESS;
	}
	
	
	/*public String getAppIfoListByType() {
		Map<String,Object> retMap = new HashMap<String,Object>();

		String c_category = getParameter("c_category");
		try{
			List list = appInfoService.getAppIfoListByType(c_category);
			
			retMap.put("list", list);
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "getAppIfoListByType failed");
			log.error("getAppIfoListByType failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}*/
	
	
	// 获取游戏APP列表
	@SuppressWarnings("rawtypes")
	public String getGameAppInfoList() {
		Map<String,Object> retMap = new HashMap<String,Object>();
		log.info("into AppInfoAction.getGameAppInfoList");
		try{
			List list = appInfoService.getGameAppInfoList();
			
			retMap.put("list", list);
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "getAppIfoListByType failed");
			log.error("getAppIfoListByType failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.info("out AppInfoAction.getGameAppInfoList");
		return SUCCESS;
	}
	
	/**
	 * 保存数据
	 * @return
	 */
	public String saveAppIfo() {
		log.debug("init saveAppIfo");
		Map<String,Object> retMap = new HashMap<String,Object>();

		String saveParam = getParameter("saveParam");
		
		Map<String,Object> saveMap = (Map) JSONUtil.JSONString2Bean(saveParam, Map.class);
		log.info("into AppInfoAction.saveAppIfo");
		log.info("saveMap=" + saveMap);
		try{
			//{key:'0',value:'新闻app'},{key:'1',value:'阅读app'}
			//2个类别的app分开保存，所以分2类目录
//			String appType = FileServices.getAppType(saveMap.get("c_category"));
			String appType = "game";
			String cName = saveMap.get("c_name").toString();// 游戏名
			cName = Cn2Spell.converterToSpellTrim(cName);// 中文转拼音
			
			MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper)getRequest();
			
			/** 2015-6-29 修改：保存文件的文件名添加时间戳  */
			String timestamp = "_" + System.currentTimeMillis();
			
			if(multiWrapper.getFileNames("logourl")!=null){
				String[] fileNames = multiWrapper.getFileNames("logourl");
				File[] files = multiWrapper.getFiles("logourl");
				for(int i=0;i<files.length;i++){//
					// 获取后缀
					String suffix = fileNames[i].substring(fileNames[i].lastIndexOf("."));
					// 拼接文件名，例（大主宰）：dazhuzai_1_1437363259162.jpg
					String name = cName + "_" +i + timestamp + suffix;
					saveMap.put("C_LOGOURL",  FileServices.saveFile(files[i],  "appinfo/"+appType+"logourl/"+name));
				}
			}
			if(multiWrapper.getFileNames("appurl")!=null){
				String[] fileNames = multiWrapper.getFileNames("appurl");
				File[] files = multiWrapper.getFiles("appurl");
				for(int i=0;i<files.length;i++){
					saveMap.put("C_APPURL", FileServices.saveFile(files[i], "appinfo/" + appType + "appurl/" + Cn2Spell.converterToSpellTrim(fileNames[i])));
				}		
			}
//			else{
//				saveMap.put("C_APPURL", "");
//			}
			String picNames = getParameter2("oldPicurl");
			
			String [] picArr = picNames.split(",");
			//List picList =  Arrays.asList(picArr);
			List<String> picList = new ArrayList<String>();
			for(int i=0;i<picArr.length;i++){
				if(StrUtils.isEmpty(picArr[i])){
					continue;
				}
				picList.add(picArr[i]);
			}
			
			if(multiWrapper.getFileNames("picurl")!=null){
				String[] fileNames = multiWrapper.getFileNames("picurl");
				File[] files = multiWrapper.getFiles("picurl");
				
				String picName = "";
				for(int i=0;i<files.length;i++){
					// 获取后缀
					String suffix = fileNames[i].substring(fileNames[i].lastIndexOf("."));
					String name = cName + "_" +i + timestamp + suffix;
//					picName = FileServices.saveFile(files[i],"appinfo/"+appType+"pic/"+Cn2Spell.converterToSpellTrim(name));
					picName = FileServices.saveFile(files[i],"appinfo/"+appType+"pic/"+name);
					//.getHttpPath( "APP_"+appType+"pic",fileNames[i]);
					if(!picList.contains(picName)){//不存在中;添加
						picList.add(picName);
					}
					//save file;
					//FileServices.saveLocFile(files[i], fileNames[i], "APP_"+appType+"pic");
				}
			}
			picNames = "";
			for(int i=0;i<picList.size();i++){
				//if(i>0){
					//picNames +=",";
				//}
				if(StrUtils.isNotEmpty(picNames) && StrUtils.isNotEmpty(picList.get(i))){
					picNames +=",";
				}
				picNames += picList.get(i);
				
			}
			//log.debug("picList:"+picList.toString());
			if(null != picNames){
				//log.debug("picNames:"+picNames);
				if(picNames.startsWith(",")){
					picNames = picNames.substring(1);
				}
				saveMap.put("C_PICURL", picNames);
			}
			String cId = appInfoService.saveAppInfo(saveMap);
			
			retMap.put("cId", cId);
			retMap.put("picNames", picNames);
			retMap.put("status", "Y");
			retMap.put("success", true);
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "saveAppIfo failed");
			log.error("saveAppIfo failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.info("out AppInfoAction.saveAppIfo:");
		return SUCCESS;
	}
	
	// 文件名后添加时间戳后缀
	private String timeSuffix(String timestamp, String name){
		String s1 = name.substring(0,name.lastIndexOf("."));
		String s2 = name.substring(s1.length());
		name = s1 + timestamp + s2;
		return name;
	}
	
	/**
	 * 删除数据
	 * @return
	 */
	public String deleteAppIfo() {
		Map<String,Object> retMap = new HashMap<String,Object>();
		String c_id = getParameter("c_id");
		log.info("into AppInfoAction.deleteAppIfo");
		log.info("c_id=" + c_id);
		try{
			
			appInfoService.deleteAppIfo(c_id);
			
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "删除失败,"+e.getMessage());
			log.error("deleteAppIfo failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.info("out AppInfoAction.deleteAppIfo:");
		return SUCCESS;
	}

	public AppInfoService getAppInfoService() {
		return appInfoService;
	}

	public void setAppInfoService(AppInfoService appInfoService) {
		this.appInfoService = appInfoService;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

}
