package com.org.mokey.basedata.sysinfo.action;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.zingchart.ChartUtils;

public class ZingChartDemo extends AbstractAction {
	
	
    public String demo() throws JSONException{
    	String method = (String) getRequest().getParameter("method");
	   //String chartData = this.getFlashChartData(getRequest());
	   String path =getRequest().getContextPath()+"/basedata/zingchartDemo.action?method=getFlashChartData";
	   getRequest().setAttribute("dataurl", path);
	   if(method==null){
		   return "success";
	   }else{
		   this.getFlashChartData(getRequest());
	   }
		return null;
    }
    
public String getFlashChartData(HttpServletRequest request) throws JSONException {
    	
     	//String method = (String) request.getParameter("method");
    	//String type = (String) request.getParameter("type");
    	//LogUtils.debug("==:" + method + type);
    	//获取查询数据的sql语句组,每一组是一个数据表项
    	//数据相
    	String[][] data = new String[2][20];
    	data[0][0]="9.12";
    	data[0][1]="10.21";
    	data[0][2]="11.52";
    	data[0][3]="8.72";
    	data[0][4]="10.66";
    	data[0][5]="13.27";
    	data[0][6]="12.12";
    	data[0][7]="15.72";
    	data[0][8]="13.12";
    	data[0][9]="13.66";
    	data[0][10]="9.12";
    	data[0][11]="10.21";
    	data[0][12]="11.52";
    	data[0][13]="8.72";
    	data[0][14]="10.66";
    	data[0][15]="13.27";
    	data[0][16]="12.12";
    	data[0][17]="15.72";
    	data[0][18]="13.12";
    	data[0][19]="13.66";
    	
    	
    	data[1][0]="9.12";
    	data[1][1]="10.21";
    	data[1][2]="11.52";
    	data[1][3]="8.72";
    	data[1][4]="10.66";
    	data[1][5]="13.27";
    	data[1][6]="12.12";
    	data[1][7]="15.72";
    	data[1][8]="13.12";
    	data[1][9]="13.66";
    	data[1][10]="9.12";
    	data[1][11]="16.21";
    	data[1][12]="12.52";
    	data[1][13]="18.72";
    	data[1][14]="11.66";
    	data[1][15]="11.27";
    	data[1][16]="12.12";
    	data[1][17]="15.72";
    	data[1][18]="13.12";
    	data[1][19]="13.66";
    	//查询数据
    	Calendar calendar =Calendar.getInstance();
    	String[] aa = new String[2] ;
    	aa[0]="激活情况";
    	aa[1]="下载情况";
    	String chartData=ChartUtils.getDateTimeData("用户增长情况",aa,calendar.getTimeInMillis(),900000l, data);
    	
    	
    	//-------------
    	JSONObject chart = new JSONObject();
    	chart.put("type", "column");
    	//-------------
    	JSONObject title = new JSONObject();
    	title.put("text", "My first Highcharts chart");
    	
    	//-----X
    	JSONObject xAxisItem = new JSONObject();
    	xAxisItem.put("categories", new String []{"my", "first", "chart"});
    	JSONObject xAxis = new JSONObject();
    	xAxis.put("title", xAxisItem);
    	
    	//-----Y
    	JSONObject ytitle = new JSONObject();
    	ytitle.put("text", "something");
    	JSONObject yAxis = new JSONObject();
    	yAxis.put("title", ytitle);
    	
    	//-- series
    	JSONArray series = new JSONArray();
    	
    	JSONObject series1 = new JSONObject();
    	series1.put("name", "Jane");
    	series1.put("data", new int []{1, 0, 4});
    	
    	JSONObject series2 = new JSONObject();
    	series2.put("name", "John");
    	series2.put("data", new int []{5, 7, 3});
    	
    	series.add(series1.toString()); series.add(series2.toString());
    	
    	JSONObject hiCHart = new JSONObject();
    	hiCHart.put("chart", chart);
    	hiCHart.put("title", title);
    	hiCHart.put("xAxis", xAxis);
    	hiCHart.put("yAxis", yAxis);
    	hiCHart.put("series", series);
    	
    	/*String aaa =             
       " {chart: {"+
           " type: 'column'    "+                    
       " },"+
        "title: {"+
            "text: 'My first Highcharts chart'      "+
       " },"+
       " xAxis: {"+
            "categories: ['my', 'first', 'chart']   "+
      "  },"+
       " yAxis: {"+
          "  title: {"+
          "      text: 'something'     "+             
          "  }"+
        "},"+
       " series: [{     "+                           
         "   name: 'Jane',    "+                     
          "  data: [1, 0, 4]  "+                     
       " }, {"+
          "  name: 'John',"+
          "  data: [5, 7, 3]"+
       " }]}" ;
*/
    	String chartStr = hiCHart.toString();
    	log.debug(chartStr);
    	try {
			writeJson(getResponse(), chartStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    	return NONE;
    }

}

	
    
	
	
	
	
    
