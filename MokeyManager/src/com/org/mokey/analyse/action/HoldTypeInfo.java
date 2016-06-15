package com.org.mokey.analyse.action;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.org.mokey.analyse.entiy.HoldTypeInfoBean;
import com.org.mokey.analyse.service.HoldTypeInfoService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.highcharts.HighchartsUtil;
import com.org.mokey.common.util.zingchart.ChartUtils;

public class HoldTypeInfo extends AbstractAction{
	
	private HoldTypeInfoService holdTypeInfoService;
	/**输出内容*/
	private String out;
	
	public String HpldType() throws JSONException {
		//String method = (String) getRequest().getParameter("method");
    	String startdate =(String) getRequest().getParameter("startdate");
    	String enddate =(String) getRequest().getParameter("enddate");
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
       }else{
	       startyear=startdate.substring(0,4);
	       startmonth=startdate.substring(5);
	       endyear=enddate.substring(0,4);
	       endmonth=enddate.substring(5);
       } 		   
		   
/*		   if(method!=null&&!"".equals(method)){
				List result=(List) getRequest().getSession().getAttribute(CHART_DATA);
			   	String appname[]= new String[result.size()];
			   	Long appcount[]= new Long[result.size()];
			   	for(int j = 0 ; j < result.size(); j++){
			   		HoldTypeInfoBean indexBean = (HoldTypeInfoBean)result.get(j);
		   	  		String indexAppname= indexBean.getAppname()==null?" ":indexBean.getAppname();
		   	     	Long indexCount = Long.valueOf(indexBean.getCount()==null?"0":indexBean.getCount());
		   	        appname[j]=indexAppname;
		   	        appcount[j]=indexCount;
			   	}
			   	
			   	String chartData=HighchartsUtil.getPie3d(startdate+"到"+enddate+" 1号键长按设置情况",appname,appcount);
		    	//String chartData=ChartUtils.getPie3d(startdate+"到"+enddate+" 1号键长按设置情况",appname,appcount);
		    	try {
					writeJson(getResponse(), chartData);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return NONE;
	       }else{*/
			   List result = holdTypeInfoService.HoldTypeList(startyear, startmonth, endyear, endmonth);
			   List<String> nowDays = ApDateTime.getMonthBetween(startyear + "-" + startmonth, endyear + "-" + endmonth);
			   getRequest().setAttribute("nowDays", nowDays);
			   getRequest().setAttribute("result", result);
			   getRequest().getSession().setAttribute("chartData", result);
			   getRequest().setAttribute("startdate", startyear+"-"+startmonth);
			   getRequest().setAttribute("enddate", endyear+"-"+endmonth);
			   
			   Double sum=0.0;            //占比计算
			   	for(int j = 0 ; j < result.size(); j++){
			   		HoldTypeInfoBean indexBean = (HoldTypeInfoBean)result.get(j);
		   	  		String indexCount = indexBean.getCount()==null?"0":indexBean.getCount();
		   	  	    sum+=Double.valueOf(indexCount);
			   	}
				getRequest().setAttribute("sum", sum);
				
				String appname[]= new String[result.size()];
			   	Long appcount[]= new Long[result.size()];
			   	for(int j = 0 ; j < result.size(); j++){
			   		HoldTypeInfoBean indexBean = (HoldTypeInfoBean)result.get(j);
		   	  		String indexAppname= indexBean.getAppname()==null?" ":indexBean.getAppname();
		   	     	Long indexCount = Long.valueOf(indexBean.getCount()==null?"0":indexBean.getCount());
		   	        appname[j]=indexAppname;
		   	        appcount[j]=indexCount;
			   	}
			   	
				startdate=getRequest().getAttribute("startdate").toString();
			    enddate=getRequest().getAttribute("enddate").toString();
			   	String chartData=HighchartsUtil.getPie3d(startdate+"到"+enddate+" 1号键长按设置情况",appname,appcount);	
	//		   String chartData = HighchartsUtil.getPie3d(startyear+"-"+startmonth+"到"+endyear+"-"+endmonth+" 1号键长按设置情况",appname,appcount);
			   //log.debug(chartData);
			   getRequest().setAttribute("chartData", chartData);
	       //}
		   return "success";
	}
/*	
public String getFlashChartData(HttpServletRequest request,String[] appname,Long[] appcount) throws JSONException {

    	String chartData=ChartUtils.getPie3d(startdate+"到"+enddate+" 1号键长按设置情况",appname,appcount);
    	try {
			writeJson(getResponse(), chartData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	return chartData;
    }*/
public void writeJson(HttpServletResponse response, String... msg)
	throws IOException {
	write(response, "application/json", msg);
}

public void writeXml(HttpServletResponse response, String... msg)
	throws IOException {
	write(response, "text/xml", msg);
}

public void write(HttpServletResponse response, String type, String... msg)
	throws IOException {
	response.setContentType(type + ";charset=UTF-8");
// response.setCharacterEncoding("GBK");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "no-cache");

	for (String m : msg) {
		response.getWriter().write(m);
	}
	response.getWriter().flush();
	response.getWriter().close();
}

public HoldTypeInfoService getHoldTypeInfoService() {
	return holdTypeInfoService;
}

public void setHoldTypeInfoService(HoldTypeInfoService holdTypeInfoService) {
	this.holdTypeInfoService = holdTypeInfoService;
}

}
