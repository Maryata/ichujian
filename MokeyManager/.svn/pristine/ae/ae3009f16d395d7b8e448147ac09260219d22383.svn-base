package com.org.mokey.basedata.baseinfo.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.org.mokey.basedata.baseinfo.service.ActionFourMainTainAdService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.file.FileServices;
import com.org.mokey.util.JSONUtil;

public class ActionMainTainAdAction extends AbstractAction {
	private ActionFourMainTainAdService actionMainTainAdAction;
	/**输出内容*/
	private String out;
	

	public String getOut() {
		return out;
	}
	public void setOut(String out) {
		this.out = out;
	}

	public ActionFourMainTainAdService getActionMainTainAdAction() {
		return actionMainTainAdAction;
	}

	public void setActionMainTainAdAction(
			ActionFourMainTainAdService actionMainTainAdAction) {
		this.actionMainTainAdAction = actionMainTainAdAction;
	}

	// 获得城市的下拉列表
	public String getCities() {
		log.debug("into getCities:");
		Map<String, Object> retMap = new HashMap<String, Object>();
		List list = new ArrayList();

		list = this.actionMainTainAdAction.getCityList();
		retMap.put("list", list);
		out = JSONObject.fromObject(retMap).toString();
		return "success";
	}

	// 获得更新下 城市下拉列表
	public String getUpdateCities() {
		log.debug("into getUpdateCities:");
		String updateID = getParameter("id");
		Map<String, Object> retMap = new HashMap<String, Object>();
		if(!(updateID==null||"".equals(updateID))){
		if(updateID.equals("0")){
		retMap.put("C_CNAME", "默认");	
		retMap.put("C_ID", "0");	
		}else{
		List list = new ArrayList();
		list = this.actionMainTainAdAction.getUpdateCityList(updateID);

		retMap.put("list", list);
		}
		}
		out = JSONObject.fromObject(retMap).toString();
		return "success";
	}
	// 获得更新下 菜单下拉列表
	public String getUpdateMenus() {
		log.debug("into getUpdateMenus:");
		String updateID = getParameter("id");
		Map<String, Object> retMap = new HashMap<String, Object>();
		List list = new ArrayList();
		if(!(updateID==null||updateID.equals(""))){
		list = this.actionMainTainAdAction.getUpdateMenuList(updateID);
		retMap.put("list", list);
		}
		out = JSONObject.fromObject(retMap).toString();
		return "success";
	}

	// 获得行业类别
	public String getIndustryCat() {
		log.debug("into getIndustryCat:");
		Map<String, Object> retMap = new HashMap<String, Object>();
		List list = new ArrayList();

		list = this.actionMainTainAdAction.getIndustryCat();
		retMap.put("list", list);
		out = JSONObject.fromObject(retMap).toString();
		return "success";
	}

	// 获得活动内容类别
	public String getActivityCat() {

		log.debug("into getActivityCat:");
		Map<String, Object> retMap = new HashMap<String, Object>();
		List list = new ArrayList();

		list = this.actionMainTainAdAction.getActivityCat();
		retMap.put("list", list);
		out = JSONObject.fromObject(retMap).toString();
		return "success";
	}

	// 得到菜单项
	public String getMenus() {
		log.debug("into getMenus:");
		Map<String, Object> retMap = new HashMap<String, Object>();
		List list = new ArrayList();

		list = this.actionMainTainAdAction.getMenuList();
		retMap.put("list", list);
		out = JSONObject.fromObject(retMap).toString();
		return "success";
	}

	public String getSummaryMessage() {
		log.debug("into getSummaryMessage:");
		String cityID = getParameter("cityid");
		String MenuID = getParameter("menuid");
		int start = getParameter2Int("start", 10);
		int limit = getParameter2Int("limit", 10);
		Map<String, Object> retMap = new HashMap<String, Object>();
		List list = new ArrayList();
		Map map = new HashMap();
		map = this.actionMainTainAdAction.getSummaryMessage(cityID, MenuID,
				start, limit);
		retMap.put("list", map.get("list"));
		retMap.put("count", map.get("count"));
		out = JSONObject.fromObject(retMap).toString();
		return "success";
	}

	public String deleteByID() {

		String c_id = getParameter("c_id");
		String menuid = getParameter("MID");

		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			actionMainTainAdAction.deleteById(c_id,menuid);
			retMap.put("status", "Y");
		} catch (Exception e) {
			retMap.put("status", "N");
		}
		out = JSONObject.fromObject(retMap).toString();
		return "success";
	}

	public String upLoadImage() {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) getRequest();
			if (multiWrapper.getFileNames("file") != null) {
				String[] fileNames = multiWrapper.getFileNames("file");
				File[] files = multiWrapper.getFiles("file");

				String picName = "";
				String ymd = ApDateTime.getNowDateTime("yyyyMMdd");
		 		for (int i = 0; i < files.length; i++) {
					//activity/image/subject/@date@yyyyMMdd/
					String fileExt = fileNames[i].substring(fileNames[i].lastIndexOf(".")).trim().toLowerCase();
					picName = FileServices.saveFile(files[i], "activity/image/subject/"+ymd+"/"+System.currentTimeMillis()+""+i+fileExt);
				} 
				retMap.put("picName", picName);
			}
			retMap.put("success", true);

		} catch (Exception e) {
			retMap.put("status", "N");
			log.error("getActiveList failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return "success";
	}


	public String getInnerSummaryMessage() {
		log.debug("into getInnerSummaryMessage:");
		String cityid = getParameter("cityid");
		String industyid = getParameter("industyid");
		String activityid = getParameter("activityid");
		String title = getParameter("title");
		int start = getParameter2Int("start", 10); 
		int limit = getParameter2Int("limit", 10);

		Map<String, Object> retMap = new HashMap<String, Object>();
		Map listMap = new HashMap();
		listMap = this.actionMainTainAdAction.getInnerSummaryMessage(cityid,
				industyid, activityid, title, start, limit);
		retMap.put("list", listMap.get("list"));
		retMap.put("count", listMap.get("count"));
		out = JSONObject.fromObject(retMap).toString();
		return "success";
	}



	public String saveAll() {// 指定之后 将数据放在静态池里面

		String cityID = getParameter("cityID");//将这里的数据保存到 T_ACTIVITY_CITY_ADVERT
		String menuID = getParameter("menuID");//
		String data=getParameter("data");//将这里的数据保存到T_ACTIVITY_ADVERT_INFO   //将保存到这张表里面的数据返回的ID  保存 到上一张表里面
		Map<String, Object> [] datas = (Map<String, Object>[]) JSONUtil.JSONString2ObjectArray(data, HashMap.class);
		Map<String, Object> retMap = null;
		retMap = new HashMap<String, Object>();
		String pics="";
		String ids="";
		try {
		int number =actionMainTainAdAction.isExist(cityID,menuID);
		if(number==0){
			for (int i = 0; i < datas.length; i++) {
				if(i==0){
					pics=(String)datas[i].get("PICS");
					ids=(String)datas[i].get("ACTIDS");
				}else{
					pics=pics+","+((String) datas[i].get("PICS"));
					ids=ids+","+(String)datas[i].get("ACTIDS");
				}
			}
			actionMainTainAdAction.saveAll(null, null, pics, new Date(), ids);
			String  nextID=actionMainTainAdAction.getNextID();
			actionMainTainAdAction.saveNewTable(cityID,menuID,nextID);
			retMap.put("success", true);
		}else{
			retMap.put("status", "N");
		}
		} catch (Exception e) {
			retMap.put("success", false);
			e.printStackTrace();
		}
		out = JSONObject.fromObject(retMap).toString();
		return "success";
	}
	public String Update() {
		Map<String, Object> retMap = null;
		try {
		String ID = getParameter("saveID");
		String data=getParameter("data");
		Map<String, Object> [] datas = (Map<String, Object>[]) JSONUtil.JSONString2ObjectArray(data, HashMap.class);
		String pics="";
		String ids="";
		for (int i = 0; i < datas.length; i++) {
			if(i==0){
				pics=(String)datas[i].get("PICS");
				ids=(String)datas[i].get("ACTIDS");
			}else{
				pics=pics+","+((String) datas[i].get("PICS"));
				ids=ids+","+(String)datas[i].get("ACTIDS");
			}
		}
		
		
		
			// 城市ID 菜单ID 图片url 活动ID
			
			actionMainTainAdAction.update(ID,pics, ids);
			retMap = new HashMap<String, Object>();
			retMap.put("success", true);
		} catch (Exception e) {
			retMap.put("success", false);
			log.error("failed", e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return "success";
	}

	public String getPicsAndIds() {// 指定之后 将数据放在静态池里面
        String  id=getParameter("id");// 根据这个ID  将 里面的图片和 IDs取出来  添加到页面上
		Map<String, Object> retMap = null;
		try {
			retMap = new HashMap<String, Object>();
			Map  map=actionMainTainAdAction.getPicsAndIds(id);
			retMap.put("PICS", map.get("c_picurl"));
			retMap.put("IDS", map.get("c_activityid"));
			String   ids="";
			String  titles="";
			String[] items=null;
			//获得活动的标题
			if(map.get("c_activityid")==null){
				retMap.put("LENGTH", 0);
				retMap.put("TITLES","请指定活动..."); 
			}else{
				ids=map.get("c_activityid").toString();
				items=ids.split(",");
				for (int i = 0; i < items.length; i++) {
					
					if(actionMainTainAdAction.getTitle(items[i])!=null){
						if(i==0){
							titles=(String) actionMainTainAdAction.getTitle(items[i]).get("c_title");
						}else{
							titles=titles+"|"+(String) actionMainTainAdAction.getTitle(items[i]).get("c_title");
						}}else{
							titles=titles+"|"+" ";
						}
					retMap.put("LENGTH", items.length);
					retMap.put("TITLES",titles); 
				}
				
			}
			
			retMap.put("success", true);
		} catch (Exception e) {
			retMap.put("failure", false);
			log.error("failed", e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return "success";
	}

}
