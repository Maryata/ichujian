package com.org.mokey.analyse.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.org.mokey.analyse.entiy.UserGrowthBean;
import com.org.mokey.analyse.service.UserUseInfoService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.highcharts.HighchartsUtil;
import com.org.mokey.util.StrUtils;

/**
 * 运营分析-日用户使用情况
 * @author giles
 *
 */
public class UserUseInfoAction extends AbstractAction {

	private UserUseInfoService userUseInfoService;
	
	public String getUserUseByDay() {
		HttpServletRequest request = getRequest();
		
		String startdate = getParameter("startdate");
		String enddate = getParameter("enddate");
		
		
		 if(StrUtils.isEmpty(startdate)){
			startdate = ApDateTime.dateAdd("d", -7, new java.util.Date(), ApDateTime.DATE_TIME_YMD);
		}
		if(StrUtils.isEmpty(enddate)){
			enddate = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_YMD);
		}
		 
		
	/*	if(StrUtils.isEmpty(startdate)){
			startdate = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_YM)+"-01";
		}
		if(StrUtils.isEmpty(enddate)){
			enddate = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_YMD);
		}
		*/
		request.setAttribute("startdate", startdate);
		request.setAttribute("enddate", enddate);
		try{
			
			Map<String,Object> userMap = userUseInfoService.getUserUseByDay(startdate, enddate);
			
			Map<String,UserGrowthBean> nowList = (Map<String, UserGrowthBean>) userMap.get("nowList");
			List<String> nowDays = (List<String>) userMap.get("nowDays");
			
			int titleCn = nowDays.size()-1;
			
			String[] name1 = new String[]{"使用次数"} ;
			String[] name2 = new String[]{"平均数"} ;
			//****
			String [] xtitle = new String [titleCn];
			String[][] data1 = new String[1][titleCn];
			String[][] data2 = new String[1][titleCn];
			
			UserGrowthBean nowBean=null;
			int flag = 0;
			for(String nowDay:nowDays){
				nowBean = nowList.get(nowDay);
				if("平均".equals(nowDay)){
					continue;
				}
				xtitle[flag]=nowDay;
				data1[0][flag] = nowBean.getStartCn()+"";
				data2[0][flag] = nowBean.getActiveRate();
				flag ++;
			}
	    	
			//Long starttime = ApDateTime.getTime(nowDays.get(0), ApDateTime.DATE_TIME_YMD);
			String chartData1 = HighchartsUtil.getLine(nowDays.get(0)+"到"+nowDays.get(nowDays.size()-2)+"日用户使用情况", xtitle, name1, data1);
			String chartData2 = HighchartsUtil.getLine(nowDays.get(0)+"到"+nowDays.get(nowDays.size()-2)+"日用户使用情况", xtitle, name2, data2);
			//String chartData1 = ChartUtils.getDateTimeData(nowDays.get(0)+"到"+nowDays.get(nowDays.size()-2)+"使用次数", xtitle1, starttime, ChartUtils.TIME_DAY, data1);
			//String chartData2 = ChartUtils.getDateTimeData(nowDays.get(0)+"到"+nowDays.get(nowDays.size()-2)+"平均数", xtitle2, starttime,  ChartUtils.TIME_DAY, data2);
	    	
	    	request.setAttribute("userMap", userMap);
	    	request.setAttribute("chartData1", chartData1);
	    	request.setAttribute("chartData2", chartData2);
			//------
		}catch(Exception e){
			log.error("getUserUseByDay failed,",e);
		}
		return "successByDay";
	}
	
	public String getUserUseByMonth() {
		HttpServletRequest request = getRequest();
		
		String startdate = getParameter("startdate");
		String enddate = getParameter("enddate");
		
		if(StrUtils.isEmpty(startdate)){
			startdate = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_Y)+"-01";
		}
		if(StrUtils.isEmpty(enddate)){
			enddate = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_YM);
		}
		
		request.setAttribute("startdate", startdate);
		request.setAttribute("enddate", enddate);
		try{
			
			Map<String,Object> userMap = userUseInfoService.getUserUseByMonth(startdate, enddate);
			
			Map<String,UserGrowthBean> nowList = (Map<String, UserGrowthBean>) userMap.get("nowList");
			List<String> nowDays = (List<String>) userMap.get("nowDays");
			
			int titleCn = nowDays.size()-1;
			
			String[] name1 = new String[]{"使用次数"} ;
			String[] name2 = new String[]{"平均数"} ;
			String[][] data1 = new String[1][titleCn];
			String[][] data2 = new String[1][titleCn];
			String [] xtitle = new String [titleCn];
			UserGrowthBean nowBean=null;
			int flag = 0;
			for(String nowDay:nowDays){
				nowBean = nowList.get(nowDay);
				if("平均".equals(nowDay)){
					continue;
				}
				xtitle[flag]=nowDay;
				data1[0][flag] = nowBean.getStartCn()+"";
				data2[0][flag] = nowBean.getActiveRate();
				flag ++;
			}
	    	
		//	Long starttime = ApDateTime.getTime(nowDays.get(0)+"-20", ApDateTime.DATE_TIME_YMD);
			String chartData1 = HighchartsUtil.getLine(nowDays.get(0)+"到"+nowDays.get(nowDays.size()-2)+"月用户使用情况", xtitle, name1, data1);
			String chartData2 = HighchartsUtil.getLine(nowDays.get(0)+"到"+nowDays.get(nowDays.size()-2)+"月用户使用情况", xtitle, name2, data2);
		//	String chartData1 = ChartUtils.getDateTimeDataMonth(nowDays.get(0)+"到"+nowDays.get(nowDays.size()-2)+"使用次数", xtitle1, starttime,  ChartUtils.TIME_MONTH, data1);
		//	String chartData2 = ChartUtils.getDateTimeDataMonth(nowDays.get(0)+"到"+nowDays.get(nowDays.size()-2)+"平均数", xtitle2, starttime,  ChartUtils.TIME_MONTH, data2);
	    	
	    	request.setAttribute("userMap", userMap);
	    	request.setAttribute("chartData1", chartData1);
	    	request.setAttribute("chartData2", chartData2);
			//------
		}catch(Exception e){
			log.error("getUserUseByMonth failed,",e);
		}
		return "successByMonth";
	}
	
	
	public UserUseInfoService getUserUseInfoService() {
		return userUseInfoService;
	}

	public void setUserUseInfoService(UserUseInfoService userUseInfoService) {
		this.userUseInfoService = userUseInfoService;
	}
	
}
