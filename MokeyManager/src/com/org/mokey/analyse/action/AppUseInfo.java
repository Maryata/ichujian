package com.org.mokey.analyse.action;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;

import com.org.mokey.analyse.service.AppUseInfoService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.analyse.entiy.AppUseBean;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.highcharts.HighchartsUtil;
import com.org.mokey.common.util.zingchart.ChartUtils;

public class AppUseInfo extends AbstractAction {

	private AppUseInfoService appUseInfoService;
	/** 输出内容 */
	private String out;

	public String AppUseList() throws JSONException {
		String startdate = (String) getRequest().getParameter("startdate");
		String enddate = (String) getRequest().getParameter("enddate");
		String top = (String) getRequest().getParameter("top");
		String startyear = "";
		String startmonth = "";
		String endyear = "";
		String endmonth = "";
		Calendar c = Calendar.getInstance();
		if ("null".equals(startdate) || "".equals(startdate)
				|| null == startdate) {
			startyear = String.valueOf(c.get(Calendar.YEAR));
			endyear = String.valueOf(c.get(Calendar.YEAR));
			startmonth = "01";
			endmonth = String.valueOf(c.get(Calendar.MONTH) + 1 + 100)
					.substring(1);
			top = "5";
		} else {
			startyear = startdate.substring(0, 4);
			startmonth = startdate.substring(5);
			endyear = enddate.substring(0, 4);
			endmonth = enddate.substring(5);
		}
		getRequest().setAttribute("top", top);

			List result = appUseInfoService.appUseList(startyear, startmonth, endyear,endmonth, top);
			List<String> nowDays = ApDateTime.getMonthBetween(startyear + "-" + startmonth, endyear + "-" + endmonth);
			
			String appname[] = new String[result.size()];
			Long appcount[] = new Long[result.size()];
			for (int j = 0; j < result.size(); j++) {
				AppUseBean indexBean = (AppUseBean) result.get(j);
				String indexAppname = indexBean.getAppname() == null ? " ": indexBean.getAppname();
				Long indexCount = Long.valueOf(indexBean.getCount() == null ? "0" : indexBean.getCount());
				appname[j] = indexAppname;
				appcount[j] = indexCount;
			}
			//String chartData = ChartUtils.getPie3d(startyear + "-" + startmonth+"到"+endyear + "-" + endmonth+" 1号键单击使用情况", appname, appcount);
			String chartData = HighchartsUtil.getPie3d(startyear + "-" + startmonth+"到"+endyear + "-" + endmonth+" 1号键单击使用情况", appname, appcount);
			
			getRequest().setAttribute("nowDays", nowDays);
			getRequest().setAttribute("result", result);
			getRequest().setAttribute("chartData", chartData);
			getRequest().setAttribute("startdate", startyear + "-" + startmonth);
			getRequest().setAttribute("enddate", endyear + "-" + endmonth);
		return "success";
	}

	public String getFlashChartData(HttpServletRequest request,
			String[] appname, Long[] appcount,String startdate,String enddate) throws JSONException {
		String chartData = ChartUtils.getPie3d(startdate+"到"+enddate+" 1号键单击使用情况", appname, appcount);
		try {
			writeJson(getResponse(), chartData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chartData;
	}

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

	public AppUseInfoService getAppUseInfoService() {
		return appUseInfoService;
	}

	public void setAppUseInfoService(AppUseInfoService appUseInfoService) {
		this.appUseInfoService = appUseInfoService;
	}

}
