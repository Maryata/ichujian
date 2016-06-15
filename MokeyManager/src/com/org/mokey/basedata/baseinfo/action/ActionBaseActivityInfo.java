package com.org.mokey.basedata.baseinfo.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.org.mokey.basedata.baseinfo.common.HtmlUtil;
import com.org.mokey.basedata.baseinfo.service.ActionBaseActivityInfoService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.file.FileServices;
import com.org.mokey.system.entiy.TSysUser;
import com.org.mokey.util.JSONUtil;
import com.org.mokey.util.StrUtils;

@SuppressWarnings("serial")
public class ActionBaseActivityInfo extends AbstractAction{
	
	/**输出内容*/
	private String out;
	
	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	HtmlUtil htmlUtil;
	public  ActionBaseActivityInfoService  actionBaseActivityInfoService;  
	
	public ActionBaseActivityInfoService getActionBaseActivityInfoService() {
		return actionBaseActivityInfoService;
	}

	public void setActionBaseActivityInfoService(
			ActionBaseActivityInfoService actionBaseActivityInfoService) {
		this.actionBaseActivityInfoService = actionBaseActivityInfoService;
	}
	//解析EXCEL
	@SuppressWarnings("unchecked")
	public  String  readExcel(){
		MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) getRequest();
		File[] files = multiWrapper.getFiles("c_reportFile");
		Map  retMap=new HashMap();
		InputStream is=null; 
		try {
			is = new FileInputStream(files[0]);
			String str=	actionBaseActivityInfoService.saveData(is); 
			if(str.length()==0){
				retMap.put("success", true);
			}else{
				retMap.put("success", false);
				retMap.put("notice", str);
			}
		} catch (Exception e) {
			log.error("readExcel failed,", e);
			retMap.put("status", "N");
			retMap.put("msg", e.getMessage());
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
//查询	
	public  String  getBaseActivityInfo (){
		int start =getParameter2Int("start", 0);
		int limit =getParameter2Int("limit", 10);
		String  S_time_s=getParameter("S_time_s");
		String  S_time_e=getParameter("S_time_e");
		String  E_time_s=getParameter("E_time_s");
		String  E_time_e=getParameter("E_time_e");
		String  city=getParameter("city");
		String  industy=getParameter("industy");
		String  property=getParameter("property");
		String  brand=getParameter("brand");
		String  title=getParameter("title");
		/** 2015-6-11 修改：增加查询条件islive（审核状态） begin */
		String  islive=getParameter("islive");
		Map retMap=new HashMap();
		try {
			log.debug("into getBaseActivityInfo");
			retMap=actionBaseActivityInfoService.getBaseActivityInfo(start,limit,S_time_s,S_time_e,E_time_s,E_time_e,city,industy,property,brand,title,islive); 
			/** 2015-6-11 修改：增加查询条件islive（审核状态） end */
			//writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (Exception e) {
			log.error("getBaseActivityInfo failed,", e);
			retMap.put("status", "N");
			retMap.put("msg", e.getMessage());
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	//查询行业
	public  String  getIndusty (){
		Map retMap=new HashMap();
		try {
			log.debug("into getBaseActivityInfo");
			retMap=actionBaseActivityInfoService.getIndusties();
			//writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (Exception e) {
			log.error("getIndusty failed,", e);
			retMap.put("status", "N");
			retMap.put("msg", e.getMessage());
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	//查询品牌
	public  String  getBrands (){
		Map retMap=new HashMap();
		try {
			log.debug("into getBaseActivityInfo");
			retMap=actionBaseActivityInfoService.getBrands();
			//writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (Exception e) {
			log.error("getBaseActivityInfo failed", e);
			retMap.put("status", "N");
			retMap.put("msg", e.getMessage());
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	
	//删除
	public  String  deleteByID(){
		log.debug("deleteByID");
		String  id=getParameter("id");
		Map map=new HashMap();
		try {
			actionBaseActivityInfoService.deleteByID(id);
			map.put("success", true);
			map.put("status", "Y");
			//writeToResponse(JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			log.error("deleteByID failed", e);
			map.put("status", "N");
			map.put("msg", e.getMessage());
		}
		out = JSONObject.fromObject(map).toString();
		return SUCCESS;
	}
	
	public String getActDetail(){
		String id=getParameter("id");
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			map.put("data", actionBaseActivityInfoService.getActDetail(id));
			map.put("status", "Y");
		} catch (Exception e) {
			log.error("getActDetail failed", e);
			map.put("status", "N");
			map.put("msg", e.getMessage());
		}
		out = JSONObject.fromObject(map).toString();
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String test(){
		String id=getParameter("id");
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			if(htmlUtil==null){
				htmlUtil = new HtmlUtil();
			}
			if(StrUtils.isNotEmpty(id)){
				log.debug("id:"+id);
				Map<String,Object> detail = actionBaseActivityInfoService.getActDetail(id);
				//webView
				if(StrUtils.isNotEmpty(detail) || StrUtils.isNotEmpty(detail.get("C_ID")) ){
					Map<String,Object> actBrandMap = actionBaseActivityInfoService.getActBrandInfo(String.valueOf(detail.get("C_BRANDID")));
					Map<String,String> ret = htmlUtil.createHtml(detail,actBrandMap);
					if(StrUtils.isEmpty(ret) || StrUtils.isEmpty(ret.get("detailPath")) ){
						Map<String,Object> saveMap = new HashMap<String,Object>();
						saveMap.put("C_ID", detail.get("C_ID"));
						saveMap.put("C_DETAILURL",ret.get("sharePath") );//分享
						saveMap.put("C_WEBVIEWURL",ret.get("detailPath") );//活动详情
						actionBaseActivityInfoService.saveActBaseIfo(saveMap);
						
						map.putAll(ret);
					}
				}
			}else{
				String ids=getParameter("ids");
				log.debug("ids:"+ids);
				if(ids.indexOf("-")>-1){
					String [] idsArr =  ids.split("-");
					int id1 = Integer.valueOf(idsArr[0]);
					int id2 = Integer.valueOf(idsArr[1]);
					List<Object> urls = new ArrayList<Object>();
					Map<String,Object> m = null;
					Map<String,Object> actBrandMap = null;
					Map<String,String> ret = null;
					Map<String,Object> saveMap = new HashMap<String,Object>();
					for(int i=id1;i<=id2;i++){						
						m = actionBaseActivityInfoService.getActDetail(i+"");
						if(StrUtils.isEmpty(m) || StrUtils.isEmpty(m.get("C_ID")) ){
							continue;
						}
						actBrandMap = actionBaseActivityInfoService.getActBrandInfo(String.valueOf(m.get("C_BRANDID")));
						ret = htmlUtil.createHtml(m,actBrandMap);
						if(StrUtils.isEmpty(ret) || StrUtils.isEmpty(ret.get("detailPath")) ){
							continue;
						}
						saveMap.put("C_ID", m.get("C_ID"));
						saveMap.put("C_DETAILURL",ret.get("sharePath") );//分享
						saveMap.put("C_WEBVIEWURL",ret.get("detailPath") );//活动详情
						actionBaseActivityInfoService.saveActBaseIfo(saveMap);
						
						urls.add(ret);
					}
					map.put("urls", urls);
				}
			}
			map.put("status", "Y");
		} catch (Exception e) {
			log.error("test failed", e);
			map.put("status", "N");
			map.put("msg", e.getMessage());
		}
		out = JSONObject.fromObject(map).toString();
		log.debug("out test");
		return SUCCESS;
	}
	public String saveUploadImg(){
		Map<String,Object> retMap = new HashMap<String,Object>();
		try{
			String retPath = "";
			MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper)getRequest();
			if(multiWrapper.getFileNames("img")!=null){
				String[] fileNames = multiWrapper.getFileNames("img");
				File[] files = multiWrapper.getFiles("img");
				String ymd = ApDateTime.getNowDateTime("yyyyMMdd");
				String fileExt = "";
				for(int i=0;i<files.length;i++){
					log.debug("save file: "+fileNames[i]);
					fileExt = fileNames[i].substring(fileNames[i].lastIndexOf(".")).trim().toLowerCase();
					if(!"".equals(retPath)){
						retPath +=",";
					}
					retPath += FileServices.saveFile(files[i], "activity/image/actImgs/"+ymd+"/"+System.currentTimeMillis()+""+i+fileExt);
				}
			}
			retMap.put("imgs", retPath);
			retMap.put("success", true);
			log.debug("saveUploadImg:"+retPath);
		}catch(Exception e){
			retMap.put("success", false);
			retMap.put("msg", "saveUploadImg failed");
			log.error("saveUploadImg failed,",e);
		}
		/*try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		out = JSONObject.fromObject(retMap).toString();
		log.debug("out saveUploadImg");
		return SUCCESS;
	}
	/*public String save(){
		log.debug("into save");
		Map<String, Object> retMap = new HashMap<String, Object>();
		String saveParam =getParameter("saveParam");
		Map<String, Object> saveMap =(Map) JSONUtil.JSONString2Bean(saveParam, Map.class);
		try {
			actionBaseActivityInfoService.save(saveMap);
			retMap.put("status", "Y");
			retMap.put("success", true);
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (Exception e) {
			retMap.put("status", "N");
			log.error("save failed",e);
		}
		return NONE;
	}*/

	public String saveActBaseIfo() {
		log.debug("init saveActBaseIfo");
		Map<String,Object> retMap = new HashMap<String,Object>();
		String saveParam = getParameter("saveParam");
		Map<String,Object> saveMap = (Map) JSONUtil.JSONString2Bean(saveParam, Map.class);
		try{
			TSysUser user = getSessionLoginUser();
			String cId = "";
			if(StrUtils.isEmpty(saveMap.get("C_ID"))){//add 
				cId = actionBaseActivityInfoService.getNextActID();
				saveMap.put("isAdd","Y");
				saveMap.put("C_ID",cId);
				saveMap.put("C_EDITPERSON",user.getUserName());
				
				saveMap.put("C_ADDTIME", new java.util.Date());
				saveMap.remove("C_ACTIONDATE");
			}else{//mo
				cId = String.valueOf(saveMap.get("C_ID"));
				// 如果islive不为空，说明是审核操作
				if(StrUtils.isNotEmpty(saveMap.get("C_ISLIVE"))){
					// islive=1表示“审核成功”
					if("1".equals(saveMap.get("C_ISLIVE"))){
						// 设置首次审核成功的人，以后不会改变
						saveMap.put("C_AUDITPERSON",user.getUserName());
					}else if("2".equals(saveMap.get("C_ISLIVE"))){// islive=2表示“驳回审核”
						// 设置驳回审核的C_AUDITPERSON
						saveMap.put("C_AUDITPERSON",user.getUserName());
					}
					// 设置审核时间
					saveMap.put("C_ACTIONDATE", new java.util.Date());
				}else{
					// 如果islive为空，说明是编辑操作
					saveMap.remove("C_ACTIONDATE");
				}
			}
			if(htmlUtil==null){
				htmlUtil = new HtmlUtil();
			}
			Map<String,Object> actBrandMap = actionBaseActivityInfoService.getActBrandInfo(String.valueOf(saveMap.get("C_BRANDID")));
			//生成文件
			Map<String,String> returl = htmlUtil.createHtml(saveMap,actBrandMap);
			saveMap.put("C_DETAILURL",returl.get("sharePath") );//分享
			saveMap.put("C_WEBVIEWURL",returl.get("detailPath") );//活动详情
			
			if(StrUtils.isNotEmpty(saveMap.get("C_SDATE"))){
				saveMap.put("C_SDATE", ApDateTime.getDate(String.valueOf(saveMap.get("C_SDATE")), ApDateTime.DATE_TIME_Sec));
			}
			if(StrUtils.isNotEmpty(saveMap.get("C_EDATE"))){
				saveMap.put("C_EDATE", ApDateTime.getDate(String.valueOf(saveMap.get("C_EDATE")), ApDateTime.DATE_TIME_Sec));
			}
			
			actionBaseActivityInfoService.saveActBaseIfo(saveMap);
			
			retMap.put("cId", cId);
			retMap.put("status", "Y");
			retMap.put("success", true);
			log.debug(" out retMap:"+retMap);
			retMap.putAll(returl);
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "saveActBaseIfo failed");
			log.error("saveActBaseIfo failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.debug("out saveActBaseIfo");
		return SUCCESS;
	}
}
