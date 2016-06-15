package com.org.mokey.analyse.action;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.org.mokey.analyse.entiy.SetAppInfoBean;
import com.org.mokey.analyse.service.SetAppInfoService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.highcharts.HighchartsUtil;
import com.org.mokey.util.StrUtils;

public class SetAppInfo extends AbstractAction {

	private SetAppInfoService setAppInfoService;

	public String SetAppList() throws JSONException {
		//String method = (String) getRequest().getParameter("method");
		String startdate = (String) getRequest().getParameter("startdate");
		String enddate = (String) getRequest().getParameter("enddate");
		String top = (String) getRequest().getParameter("top");
		String startyear = "";
		String startmonth = "";
		String endyear = "";
		String endmonth = "";
		Calendar c = Calendar.getInstance();
		if (StrUtils.isEmpty(startdate)) {
			startyear = String.valueOf(c.get(Calendar.YEAR));
			endyear = String.valueOf(c.get(Calendar.YEAR));
			startmonth = "01";
			endmonth = String.valueOf(c.get(Calendar.MONTH) + 1 + 100).substring(1);
			top = "5";
			startdate = startyear + "-" + startmonth;
			enddate = endyear + "-" + endmonth;
			
		} else {
			startyear = startdate.substring(0, 4);
			startmonth = startdate.substring(5);
			endyear = enddate.substring(0, 4);
			endmonth = enddate.substring(5);
		}
		getRequest().setAttribute("top", top);

		List<Object> result = setAppInfoService.setList(startyear, startmonth, endyear,endmonth, top);
		List<String> nowDays = ApDateTime.getMonthBetween(startyear + "-" + startmonth, endyear + "-" + endmonth);
			
		
		int totle =setAppInfoService.GetTotle(startyear, startmonth, endyear, endmonth);
		Map monthtotle = setAppInfoService.getManthTotle(startyear, startmonth, endyear, endmonth);
		
		//设置图表数据
		String appname[] = new String[result.size()];
		Long appcount[] = new Long[result.size()];
		for (int j = 0; j < result.size(); j++) {
			SetAppInfoBean indexBean = (SetAppInfoBean) result.get(j);
			String indexAppname = indexBean.getAppname() == null ? " ": indexBean.getAppname();
			Long indexCount = Long.valueOf(indexBean.getCount() == null ? "0" : indexBean.getCount());
			appname[j] = indexAppname;
			appcount[j] = indexCount;
		}
		//ChartUtils
		String chartData = HighchartsUtil.getPie3d(startdate+"到"+enddate+" 1号键单击设置情况", appname, appcount);
		//log.debug(chartData);
		
		getRequest().setAttribute("nowDays", nowDays);
		getRequest().setAttribute("result", result);
		getRequest().setAttribute("startdate", startdate);
		getRequest().setAttribute("enddate", enddate);
		
		getRequest().setAttribute("sum", Double.valueOf(totle));  //合计占比计算
		getRequest().setAttribute("monthtotle", monthtotle);      //每月合计占比计算
		
		getRequest().setAttribute("chartData", chartData);
		
		return "success";
	}

	public SetAppInfoService getSetAppInfoService() {
		return setAppInfoService;
	}

	public void setSetAppInfoService(SetAppInfoService setAppInfoService) {
		this.setAppInfoService = setAppInfoService;
	}

}
