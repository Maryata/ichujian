package com.org.mokey.basedata.baseinfo.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.org.mokey.basedata.baseinfo.service.ActivityBrandInfoService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.file.FileServices;
import com.org.mokey.util.Cn2Spell;
import com.org.mokey.util.JSONUtil;

public class ActivityBrandInfoAction extends AbstractAction {
	/**输出内容*/
	private String out;
	
	public String getOut() {
		return out;
	}
	public void setOut(String out) {
		this.out = out;
	}
	
	private ActivityBrandInfoService activityBrandInfoService;
	
	public ActivityBrandInfoService getActivityBrandInfoService() {
		return activityBrandInfoService;
	}

	public void setActivityBrandInfoService(
			ActivityBrandInfoService activityBrandInfoService) {
		this.activityBrandInfoService = activityBrandInfoService;
	}

	//查询
	public String getActivityBrandInfoList(){
		log.debug("init getActivityBrandInfoList");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String c_cname=getParameter("c_cname");
		String c_industryid=getParameter2("c_industryid");
		
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		try {
			retMap=this.activityBrandInfoService.getActivityBrandInfoListMap(c_cname,c_industryid, start, limit);
		} catch (Exception e) {
			log.error("getActivityBrandInfoList failed,", e);
			retMap.put("status", "N");
			retMap.put("msg", e.getMessage());
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	
	//删除
	public String deleteActivityBrandInfo(){
		Map<String, Object> retMap= new HashMap<String, Object>();
		String c_id = getParameter("c_id");

		try {
			this.activityBrandInfoService.deleteActivityBrandInfo(c_id);
			retMap.put("status", "Y");
			//writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "删除失败,"+e.getMessage());
			log.error("deleteActivityBrandInfo failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	//增加
	public String saveActivityBrandInfo(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String saveParam =getParameter("saveParam");
		Map<String, Object> saveMap =(Map<String, Object>) JSONUtil.JSONString2Bean(saveParam, Map.class);
		//关键字搜索
		Map<String, Object> searchMap =(Map<String, Object>) JSONUtil.JSONString2Bean(getParameter("searchParam"), Map.class);
		
		/** 2015-6-10 修改：保存文件时，文件名后添加时间戳  begin*/
		String timestamp = "_" + System.currentTimeMillis();
		
		MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper)getRequest();
		if(multiWrapper.getFileNames("c_logourl")!=null){
			String[] fileNames = multiWrapper.getFileNames("c_logourl");
			File[] files = multiWrapper.getFiles("c_logourl");
			for(int i=0;i<files.length;i++){//activity/image/brand/
				//saveMap.put("C_LOGOURL", FileServices.getHttpPath( "act_brand_logo",fileNames[i]));
				String name = fileNames[i];
				String s1 = name.substring(0,name.lastIndexOf("."));
				String s2 = name.substring(s1.length());
				name = s1 + timestamp + s2;
//				saveMap.put("C_LOGOURL",FileServices.saveFile(files[i], "activity/image/brand/"+Cn2Spell.converterToSpellTrim(fileNames[i])));
				saveMap.put("C_LOGOURL",FileServices.saveFile(files[i], "activity/image/brand/"+Cn2Spell.converterToSpellTrim(name)));
				/** 2015-6-10 修改：保存文件时，文件名后添加时间戳  end*/
			}
		}
		
		if(multiWrapper.getFileNames("c_advertisurl")!=null){
			String[] fileNames = multiWrapper.getFileNames("c_advertisurl");
			File[] files = multiWrapper.getFiles("c_advertisurl");
			for(int i=0;i<files.length;i++){//
				//saveMap.put("C_ADVERTISURL", FileServices.getHttpPath( "act_brand_advert",fileNames[i]));
				saveMap.put("C_ADVERTISURL",FileServices.saveFile(files[i], "activity/image/brand/"+Cn2Spell.converterToSpellTrim(fileNames[i])));
			}
		}
		//添加关键字搜索
		String keyId = this.activityBrandInfoService.savaActivityBrandSearchInfo(searchMap);
		saveMap.put("C_KEYID", keyId);
		//
		String eareinfoId = this.activityBrandInfoService.savaActivityBrandInfo(saveMap);

		retMap.put("status", "Y");
		retMap.put("c_id", eareinfoId);
		retMap.put("c_keyId", keyId);
		retMap.put("success", true);
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	//验证类型
	public String checkActivityBrandInfo(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String c_id = getParameter("id");
		String value =getParameter("value");
		String name =getParameter("name");
		
		try {
			retMap=this.activityBrandInfoService.checkActivityBrandInfo(c_id, name,value);
			retMap.put("status", "Y");
		} catch (Exception e) {
			retMap.put("status", "N");
			log.error("checkActivityBrandInfo", e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	
	public String findAllName(){
		log.debug("init findAllName:");
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			retMap=this.activityBrandInfoService.findAllName();
			retMap.put("status", "Y");
		} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "findAllName failed");
			log.error("findAllName", e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	//根据id查询
	public String findById(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		log.debug("init findById:");
		String c_id =getParameter("c_id");
		String[] str =c_id.split(",");
		List list=new ArrayList();
		try{
			for(int i =0;i<str.length;i++){
				List item = this.activityBrandInfoService.findById(str[i]);
				list.addAll(item);
			}
		    retMap.put("list", list);
		}catch(Exception e){
			log.error("findById", e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	
	public String getBrandSearchDetail(){
		String id=getParameter("id");
		Map<String,Object> retMap=new HashMap<String,Object>();
		try {
			retMap.put("data", activityBrandInfoService.getBrandSearchDetail(id));
			retMap.put("status", "Y");
		} catch (Exception e) {
			log.error("getBrandSearchDetail failed", e);
			retMap.put("status", "N");
			retMap.put("msg", e.getMessage());
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
}
