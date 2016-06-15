package com.org.mokey.analyse.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.org.mokey.analyse.entiy.UserGrowthBean;
import com.org.mokey.analyse.service.UserGrowthService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.highcharts.HighchartsUtil;
import com.org.mokey.common.util.zingchart.ChartUtils;
import com.org.mokey.util.StrUtils;

/**
 * 运营分析-日用户增长情况
 * @author giles
 *
 */
@SuppressWarnings("serial")
public class UserGrowthAction extends AbstractAction {

	private UserGrowthService userGrowthService;
	
	public String getUserGrowthList() {
		HttpServletRequest request = getRequest();
		String startdate = getParameter("startdate");
		String enddate = getParameter("enddate");
		String supplierCode = getParameter("supplierCode");
		
		if(StrUtils.isEmpty(startdate)){
			startdate = ApDateTime.dateAdd("d", -7, new java.util.Date(), ApDateTime.DATE_TIME_YMD);
		}
		if(StrUtils.isEmpty(enddate)){
			enddate = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_YMD);
		}
		
		request.setAttribute("startdate", startdate);
		request.setAttribute("enddate", enddate);
		try{
			Map<String,Object> userMap = userGrowthService.getUserGrowthList(startdate, enddate,supplierCode);
			
			//转换chart数据;
			List<String> nowDays = (List<String>) userMap.get("nowDays");
			Map<String,UserGrowthBean> nowList = (Map<String, UserGrowthBean>) userMap.get("nowList");
			int max = nowDays.size();
			
			String[] names = new String[2] ;
			names[0]="安装情况";
			names[1]="激活情况";
	    	
			String [] xtitle = new String [max-1];
			String[][] data = new String[2][max-1];
			UserGrowthBean nowBean=null;
			int flag = 0;
			for(String nowDay:nowDays){
				nowBean = nowList.get(nowDay);
				if("合计".equals(nowDay)){
					continue;
				}
				xtitle[flag] = nowDay;
				data[0][flag] = nowBean.getStartCn()+"";
				data[1][flag] = nowBean.getActiveCn()+"";
				flag ++;
			}
	    	//查询数据
			//Long starttime = ApDateTime.getTime(nowDays.get(0), ApDateTime.DATE_TIME_YMD);
	    	//String chartData=ChartUtils.getDateTimeData(nowDays.get(0)+"到"+nowDays.get(nowDays.size()-2)+"日用户增长情况",xtitle,starttime,ChartUtils.TIME_DAY, data);
			String chartData = HighchartsUtil.getLine(nowDays.get(0)+"到"+nowDays.get(nowDays.size()-2)+"日用户增长情况", xtitle, names, data);
					//ChartUtils.getDateTimeData(,xtitle,starttime,ChartUtils.TIME_DAY, data);
	    	request.setAttribute("chartData", chartData);
	    	//log.debug(chartData);
	    	
			request.setAttribute("nowDays", userMap.get("nowDays"));
			request.setAttribute("nowList", userMap.get("nowList"));
			request.setAttribute("lastList", userMap.get("lastList"));
			//------
		}catch(Exception e){
			log.error("getUserGrowthList failed,",e);
		}
		return "success";
	}
	
	public String getUserMonthGrowthList() {
		HttpServletRequest request = getRequest();
		String startdate = getParameter("startdate");
		String enddate = getParameter("enddate");
		String supplierCode = getParameter("supplierCode");
		
		if(StrUtils.isEmpty(startdate)){
			startdate = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_Y)+"-01";
		}
		if(StrUtils.isEmpty(enddate)){
			enddate = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_YM);
		}
		
		request.setAttribute("startdate", startdate);
		request.setAttribute("enddate", enddate);
		try{
			Map<String,Object> userMap = userGrowthService.getUserMonthGrowthList(startdate, enddate,supplierCode);
			
			//转换chart数据;
			List<String> nowDays = (List<String>) userMap.get("nowDays");
			Map<String,UserGrowthBean> nowList = (Map<String, UserGrowthBean>) userMap.get("nowList");
			int max = nowDays.size();
			
			String[] names = new String[2] ;
	    	names[0]="安装情况";
	    	names[1]="激活情况";
	    	String [] xtitle = new String [max-1];
			String[][] data = new String[2][max-1];
			UserGrowthBean nowBean=null;
			int flag = 0;
			for(String nowDay:nowDays){
				nowBean = nowList.get(nowDay);
				if("合计".equals(nowDay)){
					continue;
				}
				xtitle[flag] = nowDay;
				data[0][flag] = nowBean.getStartCn()+"";
				data[1][flag] = nowBean.getActiveCn()+"";
				flag ++;
			}
	    	//查询数据
			
			String chartData = HighchartsUtil.getLine(nowDays.get(0)+"到"+nowDays.get(nowDays.size()-2)+"月用户增长情况", xtitle, names, data);
		//	Long starttime = ApDateTime.getTime(nowDays.get(0)+"-20", ApDateTime.DATE_TIME_YMD);
	    //	String chartData=ChartUtils.getDateTimeDataMonth(nowDays.get(0)+"到"+nowDays.get(nowDays.size()-2)+"月用户增长情况",xtitle,starttime,ChartUtils.TIME_MONTH, data);
	    	request.setAttribute("chartData", chartData);
	    	
			request.setAttribute("nowDays", userMap.get("nowDays"));
			request.setAttribute("nowList", userMap.get("nowList"));
			request.setAttribute("lastList", userMap.get("lastList"));
			//------
		}catch(Exception e){
			log.error("getUserMonthGrowthList failed,",e);
		}
		return "successMonth";
	}	
	
	public UserGrowthService getUserGrowthService() {
		return userGrowthService;
	}

	public void setUserGrowthService(UserGrowthService userGrowthService) {
		this.userGrowthService = userGrowthService;
	}
}
