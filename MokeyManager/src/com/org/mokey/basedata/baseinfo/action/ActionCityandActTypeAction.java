package com.org.mokey.basedata.baseinfo.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.baseinfo.service.impl.ActionCityandActTypeServiceImpl;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.util.JSONUtil;

/**
 * 基础信息-激活详细
 * @author giles
 *
 */
public class ActionCityandActTypeAction extends AbstractAction {

	private ActionCityandActTypeServiceImpl actionCityandActType;
	
	/**输出内容*/
	private String out;
	
	/**
	 * 激活详细列表查询
	 * @return
	 */
	
    public String getActType(){
//    	actionCityandActType
    	log.debug("into getCities:");
		Map<String, Object> retMap = new HashMap<String, Object>();
		List list = new ArrayList();

		list = this.actionCityandActType.getActTypeList(); 
		retMap.put("list", list);
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return NONE;
    }
    
    public  String  getFirstGrid(){
    	String cityID = getParameter("cityid");
		String MenuID = getParameter("actid");
		int start = getParameter2Int("start", 10);
		int limit = getParameter2Int("limit", 10);
		
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		List list = new ArrayList();
		Map map = new HashMap();
		map = this.actionCityandActType.getSummaryMessage(cityID, MenuID,
				start, limit);
		retMap.put("list", map.get("list"));
		retMap.put("count", map.get("count"));
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return NONE;
    }
    public String getInnerSummaryMessage() {
		log.debug("into getInnerSummaryMessage:");
		String cityid = getParameter("cityid");
		//String industyid = getParameter("industyid");
		String activityid = getParameter("activityid");
		String title = getParameter("title");
		int start = getParameter2Int("start", 0);
		int limit = getParameter2Int("limit", 5);

		Map<String, Object> retMap = new HashMap<String, Object>();
		Map listMap = new HashMap();
		listMap = this.actionCityandActType.getInnerSummaryMessage(cityid,
				null, activityid, title, start, limit);
		retMap.put("list", listMap.get("list"));
		retMap.put("count", listMap.get("count"));
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}
    //点击修改的时候  底部grid里面的数据,根据 第一张表里面的ID  查到相关联里面的数据
    public String getUpdateInnerSummaryMessage() {
    	log.debug("into getInnerSummaryMessage:");
    	String   id  =getParameter("id");
    	int start = getParameter2Int("start", 0);
    	int limit = getParameter2Int("limit", 5);
    	List  endList=new ArrayList();
    	Map<String, Object> retMap = new HashMap<String, Object>();
    	Map listMap = new HashMap();
    	List<String>  ids=this.actionCityandActType.getIDs(id);
    	for (String str : ids) {
    		listMap = this.actionCityandActType.getUpdateInnerSummaryMessage(str, start, limit);
    	endList.add(listMap.get("item"));
		}
    	retMap.put("list", endList);
    	retMap.put("count", endList.size());
    	try {
    		writeToResponse(JSONObject.fromObject(retMap).toString());
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	return NONE;
    }
    public String isExist(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String  savecityid=getParameter("savecityid");
		String  name=getParameter("contentid");
		//根据 name 查询ID 然后进行查询 ,现在要把name传进来
		String  id=actionCityandActType.getID(name);
		try {
			int count=0;
			if(!"".equals(id)){
				  count=actionCityandActType.isExist(savecityid,id); 
			}
				retMap.put("success", true);
			if(count>0){
				retMap.put("status", "N");
				}else{
				retMap.put("status", "Y");
				}	
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			log.error("saveAll failed,",e);
		}
		return NONE; 
	}
    
    public String saveAll(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String  savecityid=getParameter("savecityid");//城市的ID  //0
		String  contentid=getParameter("contentid");//专题的ID	//240
		String  data=getParameter("data");
		StringBuffer sb=new StringBuffer();
		try {
			Map<String, Object> [] datas = (Map<String, Object>[]) JSONUtil.JSONString2ObjectArray(data, HashMap.class);
			int c_schemeid=	actionCityandActType.addCourse(savecityid,contentid);
			for (Map item : datas) {
				sb.append(actionCityandActType.addLast(String.valueOf(contentid),String.valueOf(item.get("id")),item.get("number"))+",");
			}
			String idss=sb.toString();
			idss.toString().substring(0,idss.length()); 
			retMap.put("success", true);
			retMap.put("idss", idss);
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			log.error("saveAll failed,",e);
		}
		return NONE; 
	}
    public String updateSaveAll(){
    	Map<String, Object> retMap = new HashMap<String, Object>();
    	String  savecityid=getParameter("id"); //获得要改变状态的ID  //237 
    	System.out.println(savecityid);
    	
    	
    	
    	String  data=getParameter("data");//获得 要添加的数据
    	try {
    		Map<String, Object> [] datas = (Map<String, Object>[]) JSONUtil.JSONString2ObjectArray(data, HashMap.class);
    		//改变最终表里面 含有这个ID 的 状态为0
    		actionCityandActType.deleteLastById(savecityid);
    		for (Map item : datas) {
    			actionCityandActType.addLast(savecityid,String.valueOf(item.get("id")),item.get("number"));
    		}
    		writeToResponse(JSONObject.fromObject(retMap).toString());
    		retMap.put("success", true); 
    	} catch (IOException e) {
    		log.error("saveAll failed,",e);
    	}
    	return NONE; 
    }
    public String deleteById(){
    	Map<String, Object> retMap = new HashMap<String, Object>();
    	
    	String  id=getParameter("id");
    	try {
    			actionCityandActType.deleteById(id);//
    			actionCityandActType.deleteLastById(id);//删除的时候 将 第一张表里面的数据状态变成0  以及相关的最终表里面数据变成0  这样查询的时候 就不会 出现多的数据
    			retMap.put("success", true);
    			retMap.put("status", "Y");
    			writeToResponse(JSONObject.fromObject(retMap).toString());
    		}
    	 catch (IOException e) {
    		log.error("saveAll failed,",e);
    	}
    	return NONE; 
    }
   

	public ActionCityandActTypeServiceImpl getActionCityandActType() {
		return actionCityandActType;
	}

	public void setActionCityandActType(
			ActionCityandActTypeServiceImpl actionCityandActType) {
		this.actionCityandActType = actionCityandActType;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
}
