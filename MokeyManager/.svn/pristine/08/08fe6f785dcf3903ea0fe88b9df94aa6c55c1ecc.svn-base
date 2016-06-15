package com.org.mokey.analyse.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.org.mokey.analyse.entiy.AKeyControlBean;
import com.org.mokey.analyse.service.AKeyControlService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.highcharts.HighchartsUtil;
import com.org.mokey.common.util.zingchart.ChartUtils;
import com.org.mokey.util.StrUtils;

/**
 * 运营分析-一键操控方式统计
 * @author giles
 *
 */
public class AKeyControlAction extends AbstractAction {

	private AKeyControlService aKeyControlService; 
	Map<String,String> keyMaps = null;
	
	public String getUseKeyList() {
		HttpServletRequest request = getRequest();
		
		String startdate = getParameter("startdate");
		String enddate = getParameter("enddate");
		
		if(StrUtils.isEmpty(startdate)){
			startdate = ApDateTime.dateAdd("m", -1, new java.util.Date(), ApDateTime.DATE_TIME_YM);
		}
		if(StrUtils.isEmpty(enddate)){
			enddate = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_YM);
		}
		
		request.setAttribute("startdate", startdate);
		request.setAttribute("enddate", enddate);
		try{
			
			if(keyMaps==null){//需要统计的按键类别
				keyMaps = new HashMap<String,String>();
				keyMaps.put("0", "一键启动");
				keyMaps.put("1", "一键切换");
				keyMaps.put("2", "一键常用");
			}
			
			Map<String,Object> userMap = aKeyControlService.getUseKeyList(keyMaps,startdate, enddate);
			
			Map<String,AKeyControlBean> totalMaps  = (Map<String, AKeyControlBean>) userMap.get("totalMaps");
			List<String> typeList = (List<String>) userMap.get("typeList");
			
			String[] xtitle = new String[typeList.size()] ;
			Long[] data = new Long[typeList.size()];
			int i=0;
	    	for(String type: typeList){
	    		xtitle[i] = keyMaps.get(type);
	    		data[i] = Long.valueOf(totalMaps.get(type).getStartCn());
	    		i++;
	    	}
	    	List<String> nowDays = (List<String>) userMap.get("nowDays");
	    	String chartData = HighchartsUtil.getPie3d(startdate+"到"+enddate+" 1号键单击设置情况", xtitle, data);
	    	//String chartData = ChartUtils.getPie3d(nowDays.get(0)+"到"+nowDays.get(nowDays.size()-1)+" 1号键操控方式统计",xtitle,data);
	    	request.setAttribute("chartData", chartData);
	    	
	    	request.setAttribute("keyDatas", userMap.get("keyDatas"));
			request.setAttribute("totalMaps", userMap.get("totalMaps"));
			request.setAttribute("nowDays", userMap.get("nowDays"));
			request.setAttribute("typeList", userMap.get("typeList"));
			request.setAttribute("keyMaps", keyMaps);
			//------
		}catch(Exception e){
			log.error("getUseKeyList failed,",e);
		}
		return "success";
	}
	
	/**
	 * 按键使用率次统计
	 * @return
	 */
	public String getKeyUsageStatList() {
		HttpServletRequest request = getRequest();
		//String method = request.getParameter("method");
		String startdate = getParameter("startdate");
		String enddate = getParameter("enddate");
		
		if(StrUtils.isEmpty(startdate)){
			startdate = ApDateTime.dateAdd("m", -1, new java.util.Date(), ApDateTime.DATE_TIME_YM);
		}
		if(StrUtils.isEmpty(enddate)){
			enddate = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_YM);
		}
		
		request.setAttribute("startdate", startdate);
		request.setAttribute("enddate", enddate);
		try{
			//load pic
			/*String chartData = (String) request.getSession().getAttribute(CHART_DATA);
	    	try {
				writeJson(getResponse(), chartData);
			} catch (IOException e) {
				log.error("getFlashChartData failed,", e);
			}
			return NONE;*/
			
			Map<String,Object> userMap = aKeyControlService.getKeyUsageStatList(startdate, enddate);
			//设置数据
			@SuppressWarnings("unchecked")
			Map<String,Long> totalDatas = (Map<String, Long>) userMap.get("totalDatas");
			List<String> nowDays = (List<String>) userMap.get("nowDays");
			
			String[] xtitle = new String[]{"一键","二键","三键","四键"} ;
			Long[] data = new Long[4];
			data[0] = totalDatas.get("1") ==null ? 0L : totalDatas.get("1");
			data[1] = totalDatas.get("2") ==null ? 0L : totalDatas.get("2");
			data[2] = totalDatas.get("3") ==null ? 0L : totalDatas.get("3");
			data[3] = totalDatas.get("4") ==null ? 0L : totalDatas.get("4");
			String chartData = HighchartsUtil.getPie3d(startdate+"到"+enddate+" 1号键单击设置情况", xtitle, data);
	    	//String chartData = ChartUtils.getPie3d(nowDays.get(0)+"到"+nowDays.get(nowDays.size()-2)+" 按键使用率统计",xtitle,data);
			//request.getSession().setAttribute(CHART_DATA, chartData);
			
			request.setAttribute("userMap", userMap);
			request.setAttribute("chartData", chartData);
			//------
		}catch(Exception e){
			log.error("getKeyUsageStatList failed,",e);
		}
		return "successUsageStat";
	}

	public AKeyControlService getaKeyControlService() {
		return aKeyControlService;
	}

	public void setaKeyControlService(AKeyControlService aKeyControlService) {
		this.aKeyControlService = aKeyControlService;
	}

}
