package com.org.mokey.basedata.baseinfo.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.bcel.verifier.statics.DOUBLE_Upper;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.baseinfo.service.ActionSendDemoQueryService;
import com.org.mokey.basedata.baseinfo.service.ActionSendDemoService;
import com.org.mokey.common.AbstractAction;

public class SendDemoQuery extends AbstractAction {
	private String out;
	private ActionSendDemoQueryService actionSendDemoQueryService;
	private ActionSendDemoService actionSendDemoService;

	public ActionSendDemoService getActionSendDemoService() {
		return actionSendDemoService;
	}

	public void setActionSendDemoService(
			ActionSendDemoService actionSendDemoService) {
		this.actionSendDemoService = actionSendDemoService;
	}

	public ActionSendDemoQueryService getActionSendDemoQueryService() {
		return actionSendDemoQueryService;
	}

	public void setActionSendDemoQueryService(
			ActionSendDemoQueryService actionSendDemoQueryService) {
		this.actionSendDemoQueryService = actionSendDemoQueryService;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public String getSendData() throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		String time_s = getParameter("time_s"); // 页面上面的开始时间
		String time_e = getParameter("time_e"); // 页面上面的结束时间
		String supp = getParameter("supplier"); // 代理商的 core
		String isSendDemo = getParameter("isSendDemo"); // 是否为送样

		int start = getParameter2Int("start", 0); // 分页开始
		int limit = getParameter2Int("limit", 10); // 分页大小

		try {
			retMap = actionSendDemoQueryService.queryForSupplier(time_s,
					time_e, supp, isSendDemo, start, limit);

			retMap.put("status", "Y");
		} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "getSendDemoQuery failed");
			log.error("getSendDemoQuery failed,", e);
		}

		writeToResponse(JSONObject.fromObject(retMap).toString());
		log.debug("getSendDemoQuery out");
		return NONE;

	}

	@SuppressWarnings("unchecked")
	public String getDetail() throws Exception {

		Map<String, Object> retMap = new HashMap<String, Object>();
		String imei = getParameter("id");
		List list = actionSendDemoService.GetDeviceByImei(imei); // 设备信息
		List setlist = new ArrayList();
		String onekey = "";
		String onekeyvalue = "";
		String tip = "";
		for (int i = 1; i <= 4; i++) {
			if (i == 3) {
				continue;
			}

			setlist = actionSendDemoService.GetDeviceInfoByImei(imei, i + ""); // 1、2、4个键单击设置
			for (int j = 0; j < setlist.size(); j++) {
				Map map = (Map) setlist.get(j);
				String clicktype = map.get("C_CLICKTYPE").toString() == null ? ""
						: map.get("C_CLICKTYPE").toString();

				tip = clicktype;
				String appname = map.get("C_APP_NAME").toString() == null ? ""
						: map.get("C_APP_NAME").toString();
				if (i == 1) { // 这里代表手机设置的三种状态
					if (clicktype.equals("0")) {
						onekey = "";
					} else if (clicktype.equals("1")) {
						onekey = "";
					} else if (clicktype.equals("2")) {
						onekey = "";
					}
					onekeyvalue = onekeyvalue + "," + appname;
					retMap.put("onekeyclick", onekey + ":" + onekeyvalue);

				} else if (i == 2) {
					retMap.put("twokeyclick", appname);
				} else if (i == 4) {
					retMap.put("fourkeyclick", appname);
				}
			}
		}
		List threekeyclick = actionSendDemoService.GetThreekeyInss(imei); // 三键单击
		// 显示安装的游戏
		String threevalue = "";
		for (int i = 0; i < threekeyclick.size(); i++) {
			Map map = (Map) threekeyclick.get(i);
			threevalue = map.get("C_APP_NAME").toString() == null ? "" : map
					.get("C_APP_NAME").toString();
			threevalue += ",";
			retMap.put("threekeyclick", threevalue);
		}

		List onekeyhold = actionSendDemoService.GetOnekeyhold(imei); // 1号键长按
		if (onekeyhold.size() > 0) {
			Map map = (Map) onekeyhold.get(0);
			String onekeyholdvalue = map.get("C_NAME").toString() == null ? ""
					: map.get("C_NAME").toString();
			retMap.put("onekeyhold", onekeyholdvalue);
		} else {
			retMap.put("onekeyhold", "");
		}

		List twokeyinss = actionSendDemoService.GetTwokeyInss(imei); // 二号键长按
		// 显示安装新闻
		// 阅读APP
		String twokeyvalue = "";
		for (int i = 0; i < twokeyinss.size(); i++) {
			Map map2 = (Map) twokeyinss.get(i);
			twokeyvalue = map2.get("C_APP_NAME").toString() == null ? "" : map2
					.get("C_APP_NAME").toString();
			twokeyvalue += ",";
			retMap.put("twokeyhold", twokeyvalue);

		}
		setlist = actionSendDemoService.GetDeviceInfoByImei(imei, "1");
		if (setlist.size() > 0) {
			Map tipmap = (Map) setlist.get(0);
			tip = tipmap.get("C_CLICKTYPE").toString();
		}

		// 如果一号键 的clickType 等于0的话 就查询出 不是等于0的数据
		StringBuffer sb0 = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		if ("0".equals(tip)) { // 如果是0的话，查询1和2的

			List one = new ArrayList();
			one = actionSendDemoQueryService.queryForOneMessage(imei, "1");
			Iterator it = one.iterator();

			while (it.hasNext()) {
				Map result = (Map) it.next();
				sb1.append(result.get("c_app_name") + ",");

			}

			List one1 = new ArrayList();
			one1 = actionSendDemoQueryService.queryForOneMessage(imei, "2");
			Iterator it1 = one1.iterator();
			while (it1.hasNext()) {
				Map result = (Map) it1.next();
				sb2.append(result.get("c_app_name") + ",");

			}
		}

		if ("1".equals(tip)) {

			List one = new ArrayList();
			one = actionSendDemoQueryService.queryForOneMessage(imei, "0");
			Iterator it = one.iterator();

			while (it.hasNext()) {
				Map result = (Map) it.next();
				sb0.append(result.get("c_app_name") + ",");

			}

			List one1 = new ArrayList();
			one1 = actionSendDemoQueryService.queryForOneMessage(imei, "2");
			Iterator it1 = one1.iterator();

			while (it1.hasNext()) {
				Map result = (Map) it1.next();
				sb2.append(result.get("c_app_name") + ",");
			}
		}

		if ("2".equals(tip)) {

			List one = new ArrayList();
			one = actionSendDemoQueryService.queryForOneMessage(imei, "0");
			Iterator it = one.iterator();

			while (it.hasNext()) {
				Map result = (Map) it.next();
				sb0.append(result.get("c_app_name") + ",");

			}
			List one1 = new ArrayList();
			one1 = actionSendDemoQueryService.queryForOneMessage(imei, "1");
			Iterator it1 = one1.iterator();

			while (it1.hasNext()) {
				Map result = (Map) it1.next();
				sb1.append(result.get("c_app_name") + ",");

			}
		} 
		List<Map> detailList = new ArrayList<Map>();
		// 构造第一条数据
		Map map = null;
		map = new HashMap();
		map.put("keyIndex", "一号键");
		map.put("oneOrLongClick", "单击");
		map.put("way", "一键启动:"+"\n"+"一键切换:"+"\n"+"调出常用:");
		if ("0".equals(tip)) {
//			map.put("setContent", "√" + retMap.get("onekeyclick") + "\n"
//					+ " 一键切换:" + sb1.toString() + "\n" + " 调出常用:"
//					+ sb2.toString());
			map.put("setContent", "√" + retMap.get("onekeyclick") + "\n"
					+ " " + sb1.toString() + "\n" + ""
					+ sb2.toString());

		}
		if ("1".equals(tip)) {
			map.put("setContent", " " + "" + sb0.toString() + "\n" + "√"
					+ retMap.get("onekeyclick") + "\n" + ""
					+ sb2.toString());
		}
		if ("2".equals(tip)) {
			map.put("setContent", " " + sb0.toString() + "\n" + ""
					+ sb1.toString() + "\n" + "√" + retMap.get("onekeyclick"));
		}
		detailList.add(map); 
		map = new HashMap();
		map.put("keyIndex", "");
		map.put("oneOrLongClick", "长按");
		map.put("setContent", retMap.get("onekeyhold"));
		detailList.add(map);

		map = new HashMap();
		map.put("keyIndex", "二号键");
		map.put("oneOrLongClick", "单击");
		map.put("setContent", retMap.get("twokeyclick"));
		detailList.add(map);
		map = new HashMap();
		map.put("keyIndex", "");
		map.put("oneOrLongClick", "长按");
		map.put("setContent", retMap.get("twokeyhold"));
		detailList.add(map);

		map = new HashMap();
		map.put("keyIndex", "三号键");
		map.put("oneOrLongClick", "单击");
		map.put("setContent", retMap.get("threekeyclick"));
		detailList.add(map);
		map = new HashMap();
		map.put("keyIndex", "");
		map.put("oneOrLongClick", "长按");
		map.put("setContent", "");
		detailList.add(map);
		map = new HashMap();
		map.put("keyIndex", "四号键");
		map.put("oneOrLongClick", "单击");
		map.put("setContent", retMap.get("fourkeyclick"));
		detailList.add(map);
		map = new HashMap();
		map.put("keyIndex", "");
		map.put("oneOrLongClick", "长按");
		map.put("setContent", "");
		detailList.add(map);

		Map detailMap = new HashMap();
		detailMap.put("list", detailList);

		writeToResponse(JSONObject.fromObject(detailMap).toString());
		return NONE;

	}

	@SuppressWarnings("unchecked")
	public String getHitTimes() {
		String imei = getParameter("id");
		String option = getParameter("option");
		String start = getParameter("hitStart");
		String end = getParameter("hitEnd");

		Map retMap = new HashMap();
		// --------------使用数据
		String day = "次/日";
		String day2="次";
		Map mapList1=null;
		
		if (option == null || "true".equals(option)) {
			 mapList1 = actionSendDemoService.getKeyClickCount(imei);
		}else{
			mapList1 = actionSendDemoService.getKeyClickCountByTime(start, end,
					imei);
		}
			
			double sum2 = Double.parseDouble(mapList1.get("sum").toString());

			
			//频率
			double hit1 = 0;
			double hit2 = 0;
			double hit3 = 0;
			double hit4 = 0;
			double hold1 = 0;
			double hold2 = 0;
			double hold3 = 0;
			double hold4 = 0;
			//次数
			int hit1number = 0;
			int  hit2number = 0;
			int  hit3number = 0;
			int  hit4number = 0;
			int  hold1number = 0;
			int  hold2number = 0;
			int  hold3number = 0;
			int  hold4number = 0;

			List list3 = (List) mapList1.get("list");
			if (list3.size() > 0) {
				for (int i = 0; i < list3.size(); i++) { // 循环迭代出，
					// 每个按键在这个时间段内点击了多少次，然后除以上面的天数
					Map map = (Map) list3.get(i);
					String key = map.get("C_KEY").toString();
					String type = map.get("C_TYPE").toString();
					int uavg = Integer.parseInt(map.get("COUNTSS").toString());
					if (key.equals("1") && type.equals("0")) { // type 0代表单击 1代表长按
						hit1number=uavg;
						hit1 = new BigDecimal(uavg / sum2).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
						retMap.put("c_onekeycount", hit1 + day);
					} else if (key.equals("1") && type.equals("1")) {
						hold1number=uavg;
						hold1 = new BigDecimal(uavg / sum2).setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
						retMap.put("c_onekeyhold", hold1 + day);
					} else if (key.equals("2") && type.equals("0")) {
						
						hit2number=uavg;
						hit2 = new BigDecimal(uavg / sum2).setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
						retMap.put("c_twokeycount", hit2 + day);
					} else if (key.equals("2") && type.equals("1")) {
						hold2number=uavg;
						hold2 = new BigDecimal(uavg / sum2).setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
						retMap.put("c_twokeyhold", hold2 + day);

					} else if (key.equals("3") && type.equals("0")) {
						hit3number=uavg;
						hit3 = new BigDecimal(uavg / sum2).setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
						retMap.put("c_threekeycount", hit3 + day);
					} else if (key.equals("3") && type.equals("1")) {
						hold3number=uavg;
						hold3 = new BigDecimal(uavg / sum2).setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
						retMap.put("c_threekeyhold", hold3 + day);
					} else if (key.equals("4") && type.equals("0")) {
						hit4number=uavg;
						hit4 = new BigDecimal(uavg / sum2).setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
						retMap.put("c_fourkeycount", hit4 + day);
					} else if (key.equals("4") && type.equals("1")) {
						hold4number=uavg;
						hold4 = new BigDecimal(uavg / sum2).setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
						retMap.put("c_fourkeyhold", hold4 + day);
					}
				}
			} else {
				retMap.put("c_onekeycount", "");
				retMap.put("c_onekeyhold", "");
				retMap.put("c_twokeycount", "");
				retMap.put("c_twokeyhold", "");
				retMap.put("c_threekeycount", "");
				retMap.put("c_threekeyhold", "");
				retMap.put("c_fourkeycount", "");
				retMap.put("c_fourkeyhold", "");
			}

			List mapList = new ArrayList();
			Map map = null;
			map = new HashMap();
			map.put("keyIndex", "一号键");
			map.put("oneOrLongClick", "单击");
			map.put("hitTimes", retMap.get("c_onekeycount"));
			map.put("oneKeyCount", hit1number + day2);
			
			map.put("sum",new BigDecimal((double)hit1+hold1).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+day);

			mapList.add(map);
			map = new HashMap();
			map.put("keyIndex", "");
			map.put("oneOrLongClick", "长按");
			map.put("hitTimes", retMap.get("c_onekeyhold"));
			map.put("oneKeyCount", hold1number + day2);
			map.put("sum",hit1number+hold1number+day2);
			
			mapList.add(map);

			map = new HashMap();
			map.put("keyIndex", "二号键");
			map.put("oneOrLongClick", "单击");
			map.put("hitTimes", retMap.get("c_twokeycount"));
			map.put("oneKeyCount", hit2number + day2);
			map.put("sum",new BigDecimal((double)hit2+hold2).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+day);
			mapList.add(map);
			map = new HashMap();
			map.put("keyIndex", "");
			map.put("oneOrLongClick", "长按");
			map.put("hitTimes", retMap.get("c_twokeyhold"));
			map.put("oneKeyCount", hold2number + day2); 
			map.put("sum",hit2number+hold2number+day2);
			mapList.add(map);

			map = new HashMap();
			map.put("keyIndex", "三号键");
			map.put("oneOrLongClick", "单击");
			map.put("hitTimes", retMap.get("c_threekeycount"));
			map.put("oneKeyCount", hit3number + day2);
			map.put("sum",new BigDecimal((double)hit3+hold3).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+day);
			mapList.add(map);
			map = new HashMap();
			map.put("keyIndex", "");
			map.put("oneOrLongClick", "长按");
			map.put("hitTimes", retMap.get("c_threekeyhold"));
			map.put("oneKeyCount", hold3number + day2);
			map.put("sum",hit3number+hold3number+day2);
			mapList.add(map);

			map = new HashMap();
			map.put("keyIndex", "四号键");
			map.put("oneOrLongClick", "单击");
			map.put("hitTimes", retMap.get("c_fourkeycount"));
			map.put("oneKeyCount", hit4number + day2);
			map.put("sum",new BigDecimal((double)hit4+hold4).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+day);
			mapList.add(map);  
			map = new HashMap();
			map.put("keyIndex", "");
			map.put("oneOrLongClick", "长按");
			map.put("hitTimes", retMap.get("c_fourkeyhold"));
			map.put("oneKeyCount", hold4number + day2);
			map.put("sum",hit4number+hold4number+day2);

			mapList.add(map);

			Map jsonMap = new HashMap();
			jsonMap.put("list", mapList);
			try {
				writeToResponse(JSONObject.fromObject(jsonMap).toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return NONE;
	}
  
	@SuppressWarnings("unchecked")
	public  String  getHitHistory(){
		
		//在前台显示 历史记录，和表单上面的时间有关，然后往前显示 信息，一次显示一条
		
		//需要
		String option = getParameter("option");
		String start = getParameter("hitStart");
		String end = getParameter("hitEnd");
		String imei = getParameter("id");
		int start1 = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		
	Map  map=	actionSendDemoService.getHitHistory(start, end, imei, start1, limit,option);
		List   list=(List) map.get("list");
		
		Iterator  it=list.iterator();
		Map   dataMap=null;
		List<Map>  mapList=new ArrayList<Map>();
		while(it.hasNext()){
			dataMap=new HashMap();
			Map  listMap=(Map)it.next();
			
//				listMap.get("C_APP_NAME");
//				listMap.get("c_actiondate");
		String  key=listMap.get("c_key").toString();
		String  type=listMap.get("c_type").toString();
		String c_clicktype=listMap.get("c_clicktype").toString();
          // 'keyIndex' 'oneOrLongClick'
    if (key.equals("1")&&type.equals("0")) {
    	dataMap.put("keyIndex", "一号键");
    	dataMap.put("oneOrLongClick", "单击");
    	//在单击里面做判断，如果是 0一键启动 1 一键切换 2 一键常用
    	if (c_clicktype.equals("0")) {
    		dataMap.put("setContent", "一键启动："+listMap.get("C_APP_NAME"));
		}
    	if (c_clicktype.equals("1")) {
    		dataMap.put("setContent", "一键切换："+listMap.get("C_APP_NAME"));
    	}
    	if (c_clicktype.equals("2")) {
    		dataMap.put("setContent", "一键常用："+listMap.get("C_APP_NAME"));
    	}
    	
    	
    	
    	
	}else if(key.equals("1")&&type.equals("1")){
		dataMap.put("keyIndex", "一号键");
    	dataMap.put("oneOrLongClick", "长按");
    	dataMap.put("setContent", listMap.get("C_APP_NAME"));
	}else if(key.equals("2")&&type.equals("0")){
		dataMap.put("keyIndex", "二号键");
    	dataMap.put("oneOrLongClick", "单击");dataMap.put("setContent", listMap.get("C_APP_NAME"));
	}else if(key.equals("2")&&type.equals("1")){
		dataMap.put("keyIndex", "二号键");
    	dataMap.put("oneOrLongClick", "长按");dataMap.put("setContent", listMap.get("C_APP_NAME"));
	}else if(key.equals("3")&&type.equals("0")){
		dataMap.put("keyIndex", "三号键");
    	dataMap.put("oneOrLongClick", "单击");dataMap.put("setContent", listMap.get("C_APP_NAME"));
	}else if(key.equals("3")&&type.equals("1")){
		dataMap.put("keyIndex", "三号键");
    	dataMap.put("oneOrLongClick", "长按");dataMap.put("setContent", listMap.get("C_APP_NAME"));
	}else if(key.equals("4")&&type.equals("0")){
		dataMap.put("keyIndex", "四号键");
    	dataMap.put("oneOrLongClick", "单击");dataMap.put("setContent", listMap.get("C_APP_NAME"));
	}else if(key.equals("4")&&type.equals("1")){
		dataMap.put("keyIndex", "四号键");
    	dataMap.put("oneOrLongClick", "长按");dataMap.put("setContent", listMap.get("C_APP_NAME"));
	}
				
			dataMap.put("times", listMap.get("c_actiondate"));
			
			
			mapList.add(dataMap);
		}
		
		Map jsonMap = new HashMap();
		jsonMap.put("list", mapList);
		jsonMap.put("count", map.get("count"));
		try {
			writeToResponse(JSONObject.fromObject(jsonMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return NONE;
	
	
	
	}
	
	@SuppressWarnings("unchecked")
	public String  getSetHistory(){
		String imei=getParameter("id");
		int  start =getParameter2Int("start", 0);
		int  limit=getParameter2Int("limit", 0);
		Map  map=new HashMap();
		map=actionSendDemoService.getHitHistory(imei, start, limit);
	try {
		writeToResponse(JSONObject.fromObject(map).toString());
	} catch (IOException e) {
		e.printStackTrace();
	}
	return NONE;
	}
	
	public String getActiveList() {  
		Map<String,Object> retMap = new HashMap<String,Object>();
		String code=getParameter("id");   //获得提供商 6 7 位编码
		String time_s = getParameter("time_s");
		String time_e = getParameter("time_e");
		String c_imei = getParameter("c_imei");
		
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		try{
			retMap = actionSendDemoQueryService.getActiveListMap(time_s, time_e,start,limit,c_imei,code);
			writeToResponse(JSONObject.fromObject(retMap).toString());

			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "getActiveList failed");
			log.error("getActiveList failed,",e);
		}
		
		return NONE;
	}
	
	
	public String GetDeviceInfoByImei(){
		Map<String,Object> retMap = new HashMap<String,Object>();
		String imei=getParameter("c_imei");
		
		List list=actionSendDemoService.GetDeviceByImei(imei);
		
		if(list.size()>0){
			   Map map1=(Map) list.get(0);
			   retMap.put("deviceinfo", map1);
			}else{
				retMap.put("deviceinfo", null);
			}
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return  NONE;
	}
	
	
	
	
}
