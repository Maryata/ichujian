package com.org.mokey.basedata.baseinfo.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.net.aso.e;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.baseinfo.service.ActionActiveService;
import com.org.mokey.common.AbstractAction;

/**
 * 基础信息-激活详细
 * @author giles
 *
 */
public class ActionActiveAction extends AbstractAction {

	private ActionActiveService actionActiveService;
	
	/**输出内容*/
	private String out;
	
	/**
	 * 激活详细列表查询
	 * @return
	 */
	public String getActiveList() {
		log.debug("getActiveList init");
		Map<String,Object> retMap = new HashMap<String,Object>();
		String code=getParameter("id");   //获得提供商 6 7 位编码
		String time_s = getParameter("time_s");
		String time_e = getParameter("time_e");
		String c_imei = getParameter("c_imei");
		 
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		try{
			retMap = actionActiveService.getActiveListMap(time_s, time_e,start,limit,c_imei,code);
			
			retMap.put("status", "Y");
			//writeToResponse(JSONObject.fromObject(retMap).toString());
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "getActiveList failed");
			log.error("getActiveList failed,",e);
		}
		log.debug("getActiveList out");
		out = JSONObject.fromObject(retMap).toString();
		return "success";
	}
	
	public String GetDeviceByImei() {
		log.info("into GetDeviceByImei");
		Map<String,Object> retMap = new HashMap<String,Object>();
		String imei=getParameter("c_imei");
		try {
			List list=actionActiveService.GetDeviceByImei(imei);
			retMap.put("status", "Y");
			if(list.size()>0){
			   Map map=(Map) list.get(0);
			   retMap.put("deviceinfo", map);
			}else{
				retMap.put("deviceinfo", null);
			}
		} catch (Exception e) {
			// TODO: handle exception
			retMap.put("status", "N");
			retMap.put("info", "GetDeviceByImei failed");
			log.error("GetDeviceByImei failed", e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return "success";
	}

	public String GetDeviceInfoByImei() {   //设备详细信息
		log.info("into GetDeviceByImei");
		Map<String,Object> retMap = new HashMap<String,Object>();
		String imei=getParameter("c_imei");
		try {
			List list=actionActiveService.GetDeviceByImei(imei);   //设备信息
			//设置信息
			String onekey="";
			String onekeyvalue="";
			for(int i=1;i<=4;i++){
				if(i==3){
					continue;
				}
				List setlist=new ArrayList();
				setlist=actionActiveService.GetDeviceInfoByImei(imei, i+"");  //1、2、4个键单击设置
                for (int j = 0; j < setlist.size(); j++) {
					Map map=(Map) setlist.get(j);
					String clicktype=map.get("C_CLICKTYPE").toString()==null?"":map.get("C_CLICKTYPE").toString();
					String appname=map.get("C_APP_NAME").toString()==null?"":map.get("C_APP_NAME").toString();
					if(i==1){
						if(clicktype.equals("0")){
							onekey="一键启动";
						}else if (clicktype.equals("1")) {
							onekey="一键切换";
						}else if (clicktype.equals("2")) {
							onekey="启动常用";
						}
						onekeyvalue=onekeyvalue+","+appname;
						retMap.put("onekeyclick", onekey+":"+onekeyvalue);
					}else if(i==2){
						retMap.put("twokeyclick", appname);
					}else if (i==4) {
						retMap.put("fourkeyclick", appname);
					}
				}
			}
			List threekeyclick=actionActiveService.GetThreekeyInss(imei);   //三键单击  显示安装的游戏
			String threevalue="";
			for (int i = 0; i < threekeyclick.size(); i++) {
				Map map=(Map) threekeyclick.get(i);
				threevalue=map.get("C_APP_NAME").toString()==null?"":map.get("C_APP_NAME").toString();
				threevalue+=",";
				retMap.put("threekeyclick", threevalue);
			}
			
			List onekeyhold=actionActiveService.GetOnekeyhold(imei);   //1号键长按
			if(onekeyhold.size()>0){
				Map map=(Map) onekeyhold.get(0);
				String onekeyholdvalue=map.get("C_NAME").toString()==null?"":map.get("C_NAME").toString();
				retMap.put("onekeyhold", onekeyholdvalue);
			}else {
				retMap.put("onekeyhold", "");
			}
			
				
			List twokeyinss=actionActiveService.GetTwokeyInss(imei);   //二号键长按  显示安装新闻 阅读APP
			String twokeyvalue="";
			for (int i = 0; i < twokeyinss.size(); i++) {
				Map map2=(Map) twokeyinss.get(i);
				twokeyvalue=map2.get("C_APP_NAME").toString()==null?"":map2.get("C_APP_NAME").toString();
				twokeyvalue+=",";
				retMap.put("twokeyhold", twokeyvalue);
			}
			
			//--------------使用数据
			String day="次/日";
			String totle =actionActiveService.GetUseKeyTotle(imei);  //总数
			String remark=actionActiveService.GetRemark(imei);    //备注
			retMap.put("totle", totle+day);
			retMap.put("remark", remark);
			List list3=actionActiveService.GetClickcount(imei);
			if(list3.size()>0){
				for (int i = 0; i < list3.size(); i++) {
					Map map=(Map) list3.get(i);
					String key = map.get("C_KEY").toString();
				    String type = map.get("C_TYPE").toString();
				    String uavg=map.get("UAVG").toString();
				    if(key.equals("1")&&type.equals("0")){
				    	retMap.put("c_onekeycount", uavg+day);
				    }else if (key.equals("1")&&type.equals("1")) {
				    	retMap.put("c_onekeyhold", uavg+day);
					}else if (key.equals("2")&&type.equals("0")) {
						retMap.put("c_twokeycount", uavg+day);
					}else if (key.equals("2")&&type.equals("1")) {
						retMap.put("c_twokeyhold", uavg+day);
					}else if (key.equals("3")&&type.equals("0")) {
						retMap.put("c_threekeycount", uavg+day);
					}else if (key.equals("3")&&type.equals("1")) {
						retMap.put("c_threekeyhold", uavg+day);
					}else if (key.equals("4")&&type.equals("0")) {
						retMap.put("c_fourkeycount", uavg+day);
					}else if (key.equals("4")&&type.equals("1")) {
						retMap.put("c_fourkeyhold", uavg+day);
					}
				}
			}else {
		    	retMap.put("c_onekeycount", "");
		    	retMap.put("c_onekeyhold", "");
				retMap.put("c_twokeycount", "");
				retMap.put("c_twokeyhold", "");
				retMap.put("c_threekeycount", "");
				retMap.put("c_threekeyhold", "");
				retMap.put("c_fourkeycount", "");
				retMap.put("c_fourkeyhold", "");
			}
			
			retMap.put("status", "Y");
			if(list.size()>0){
			   Map map1=(Map) list.get(0);
			   retMap.put("deviceinfo", map1);
			}else{
				retMap.put("deviceinfo", null);
			}
		} catch (Exception e) {
			// TODO: handle exception
			retMap.put("status", "N");
			retMap.put("info", "GetDeviceByImei failed");
			log.error("GetDeviceByImei failed", e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return "success";
	}

	public ActionActiveService getActionActiveService() {
		return actionActiveService;
	}


	public void setActionActiveService(ActionActiveService actionActiveService) {
		this.actionActiveService = actionActiveService;
	}


	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
}
