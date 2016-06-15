package com.org.mokey.analyse.action;

import java.util.Calendar;
import java.util.List;

import org.json.JSONException;

import com.org.mokey.analyse.entiy.NewsAppInfoBean;
import com.org.mokey.analyse.service.NewsAppUseInfoService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.highcharts.HighchartsUtil;

public class NewsAppUseInfoAction extends AbstractAction{
	
	private NewsAppUseInfoService newsAppUseInfoService;
	
	public String NewsAppUseInfo() throws Exception {
		//String method = (String) getRequest().getParameter("method");
    	String startdate =(String) getRequest().getParameter("startdate");
    	String enddate =(String) getRequest().getParameter("enddate");
    	String top =(String) getRequest().getParameter("top");
    	String startyear="";
    	String startmonth = "";
    	String endyear = "";
    	String endmonth = "";
    	Calendar c = Calendar.getInstance();
		
       if("null".equals(startdate)||"".equals(startdate)||null==startdate){
	       startyear = String.valueOf(c.get(Calendar.YEAR));
	       endyear = String.valueOf(c.get(Calendar.YEAR));
	       startmonth = "01";
	       endmonth = String.valueOf(c.get(Calendar.MONTH) + 1 + 100).substring(1);	
	       top="5";
       }else{
	       startyear=startdate.substring(0,4);
	       startmonth=startdate.substring(5);
	       endyear=enddate.substring(0,4);
	       endmonth=enddate.substring(5);
       }
	   getRequest().setAttribute("top", top);

	   List result = newsAppUseInfoService.queryNewsAppUseInfo(startyear, startmonth, endyear, endmonth, top);
	   List<String> nowDays = ApDateTime.getMonthBetween(startyear + "-" + startmonth, endyear + "-" + endmonth);
	   
	   	String appname[]= new String[result.size()];
	   	Long appcount[]= new Long[result.size()];
	   	for(int j = 0 ; j < result.size(); j++){
	   		NewsAppInfoBean indexBean = (NewsAppInfoBean)result.get(j);
   	  		String indexAppname= indexBean.getAppname()==null?" ":indexBean.getAppname();
   	     	Long indexCount = Long.valueOf(indexBean.getCount()==null?"0":indexBean.getCount());
   	        appname[j]=indexAppname;
   	        appcount[j]=indexCount;
	   	}
	   	String chartData = HighchartsUtil.getBar(startyear + "-" + startmonth+"到"+endyear + "-" + endmonth+" 2号键单击使用情况",new String[] {"app"},appname,appcount,"container");
	   getRequest().setAttribute("nowDays", nowDays);
	   getRequest().setAttribute("result", result);
	   getRequest().setAttribute("chartData", chartData);
	   getRequest().setAttribute("startdate", startyear+"-"+startmonth);
	   getRequest().setAttribute("enddate", endyear+"-"+endmonth);

	   return "success";
	}

	public NewsAppUseInfoService getNewsAppUseInfoService() {
		return newsAppUseInfoService;
	}
	
	public void setNewsAppUseInfoService(NewsAppUseInfoService newsAppUseInfoService) {
		this.newsAppUseInfoService = newsAppUseInfoService;
	}

}
