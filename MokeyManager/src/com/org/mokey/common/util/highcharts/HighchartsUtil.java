package com.org.mokey.common.util.highcharts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HighchartsUtil {
	
	/**
	 * 3D饼图
	 * @param title
	 * @param xtitle
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	public static String getPie3d(String title, String[] xtitle, Long[] data)
			throws JSONException {
		String name = " ";
		JSONObject json = new JSONObject();
		//-------------chart
		JSONObject chart = new JSONObject();
    	chart.put("type", "pie");
    	
    	JSONObject options3d = new JSONObject();
    	options3d.put("enabled", true);
    	options3d.put("alpha", 45);
    	options3d.put("beta", 0);
    	chart.put("options3d", options3d);
    	
    	json.put("chart", chart);//set chart

    	//-------------chart
		json.put("title", getTitle(title));//set title
		json.put("tooltip", getToolTipPie3d());
		
		json.put("plotOptions", getPlotOptionsPie3d());
		json.put("series", getScalxPie3d(xtitle,data,name));
		getFilename(json , title);
		return json.toString();
	}
	
	public static String getLine(String title, Object[] xtitle,
			String[] names, String[][] data) throws JSONException {
		return getLine(title, xtitle, names, data,false);
	}
	
	public static String getLine(String title, String[] xtitle,
			String[] names, String[][] data) throws JSONException {
		return getLine(title, xtitle, names, data,false);
	}
	
	/**
	 * 折线图
	 * @param title
	 * @param xtitle
	 * @param names
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	public static String getLine(String title, Object[] xtitle,
			String[] names, String[][] data, boolean isDate) throws JSONException {
		String [] xTitleS = new String [xtitle.length];
		for(int i=0;i<xtitle.length;i++){
			xTitleS[i] = (String) xtitle[i];
		}
		return getLine(title, xTitleS, names, data,isDate);
	}
	
	/**
	 * 折线图
	 * @param title
	 * @param xtitle
	 * @param names
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	public static String getLine(String title, String[] xtitle,
			String[] names, String[][] data, boolean isDate) throws JSONException {
		String yname = " "; String valueSuffix = "";
		JSONObject json = new JSONObject();
		json.put("title", getTitleLine(title));//set title
		//json.put("subtitle", );
		json.put("xAxis", getXAxisLine(xtitle,isDate));//set xAxis
		json.put("yAxis", getYAxisLine(yname));//yAxis
		json.put("tooltip", getToolTipLine(valueSuffix));
		//json.put("plotOptions", getPlotOptionsLine());
		json.put("legend", getLegend());//legend
		json.put("series", getSeriesLine(names, data));
		getFilename(json , title);
		return json.toString();
	}
	
	/**
	 * 饼状图
	 * @param title
	 * @param type
	 * @param xtitle
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String getBar(String title,String[] type,String[] xtitle, Long[] data)
			throws Exception {
		return HighchartsUtil.getBar(title, type, xtitle, data,null);
	}
	
	/**
	 * 柱状图
	 * @param title
	 * @param xtitle
	 * @param data
	 * @param renderTo 渲染的对象
	 * @return
	 * @throws JSONException
	 */
	public static String getBar(String title, String[] type,String[] xtitle, Long[] data,String renderTo)
			throws Exception {
		JSONObject json = new JSONObject();
		
		
		JSONObject chart = new JSONObject();
		JSONObject options3d = new JSONObject();
		
		if(null!=renderTo && !"".equals(renderTo)){
			chart.put("renderTo", renderTo);//"container"
		}
		chart.put("type", "column");
		chart.put("margin", 75);
		chart.put("options3d", options3d);
		
		options3d.put("enabled", true);
		options3d.put("alpha", 15);
		options3d.put("beta", 15);
		options3d.put("depth", 50);
		options3d.put("viewDistance", 25);
		
		JSONObject xAxis = new JSONObject();
		JSONArray categories = new JSONArray();
		categories.put(type);
		xAxis.put("categories", categories);
		
		json.put("chart", chart);
		json.put("title", getTitle(title));//set title
		json.put("xAxis", xAxis);//set title
		json.put("plotOptions", getPlotOptionsBar());//set title		
		json.put("series", getSeriesBar(xtitle, data));
		//json.put("tooltip", getTooltipBar());
		//------------
		//System.out.println(json.toString());
		getFilename(json , title);
		return json.toString();
	}

	public static String getBar(String title, String[] categories,String[] nameArr, Long[][] dataArr)
			throws Exception {
		JSONObject json = new JSONObject();

		JSONObject chart = new JSONObject();
		JSONObject options3d = new JSONObject();

		chart.put("type", "column");
		chart.put("margin", 75);
		chart.put("options3d", options3d);

		options3d.put("enabled", true);
		options3d.put("alpha", 15);
		options3d.put("beta", 15);
		options3d.put("depth", 50);
		options3d.put("viewDistance", 25);

		JSONObject xAxis = new JSONObject();
		JSONArray _categories = new JSONArray();
		for(int i = 0; i <categories.length; ++i) {
			_categories.put(categories[i]);
		}
		xAxis.put("categories", _categories);

		json.put("chart", chart);
		json.put("title", getDefaultTitle(title));//set title
		json.put("xAxis", xAxis);//set xAxis
		json.put("plotOptions", getPlotOptionsBar());//set plotOptions
		json.put("series", getSeriesBar(nameArr, dataArr));

		getFilename(json , "");
		return json.toString().replace("\"","'");
	}
	
	private static void getFilename(JSONObject json,String filename) {
		try {
			if(null != filename && !"".equals(filename)){
				JSONObject f = new JSONObject();
				f.put("filename", new String(filename.getBytes("UTF-8"),"iso-8859-1") );
				json.put("exporting", f);
			}
			//设置默认参数;
			JSONObject credits = new JSONObject();
			credits.put("enabled", false);
			//credits.put("href", "http://www.hcharts.cn/index.php");
			//credits.put("text", "charts");
			
			json.put("credits", credits);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*private static JSONObject getTooltipBar() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("pointFormat", "\"Population in 2008: <b>{point.y:.1f} millions</b>\"");
		return json;
	}*/

	private static JSONArray getSeriesBar(String[] xtitle, Long[] data) throws JSONException {
		JSONArray json = new JSONArray();
		for (int i = 0; i < xtitle.length; i++) {
			JSONObject item = new JSONObject();
			item.put("name", xtitle[i]);
			
			JSONArray values = new JSONArray();
			item.put("data", values);
			values.put(data[i]);
			json.put(item);
		}
		return json;
	}

	private static JSONArray getSeriesBar(String[] nameArr, Long[][] data) throws JSONException {
		JSONArray json = new JSONArray();
		for (int i = 0; i < nameArr.length; i++) {
			JSONObject item = new JSONObject();
			item.put("name", nameArr[i]);

			JSONArray values = new JSONArray();
			for(int j = 0; j < data[i].length; ++j) {
				values.put(data[i][j]);
			}
			item.put("data", values);
			json.put(item);
		}
		return json;
	}

	private static JSONObject getPlotOptionsBar() throws JSONException {
		JSONObject json = new JSONObject();
		
		JSONObject column = new JSONObject();
		column.put("depth", 25);
		
		json.put("column", column);
		return json;
	}

	private static JSONArray getSeriesLine(String[] names, String[][] data) throws JSONException {
		JSONArray json = new JSONArray();

/*		JSONObject dataLabels = new JSONObject();
		dataLabels.put("enabled", true);
		dataLabels.put("rotation", -90);
		dataLabels.put("color", "#FFFFFF");
		dataLabels.put("align", "right");
		dataLabels.put("x", 4);
		dataLabels.put("y", 10);
		
		JSONObject style = new JSONObject();
		dataLabels.put("style", style);
		style.put("fontSize", "13px");
		style.put("fontFamily", "Verdana, sans-serif");
		style.put("textShadow", "0 0 3px black");
		*/
		for (int i = 0; i < names.length; i++) {
			JSONObject item = new JSONObject();
			item.put("name", names[i]);
			
			JSONArray values = new JSONArray();
			item.put("data", values);
			String [] dataItem = data[i];
			for (int j = 0; j < dataItem.length; j++) {
				values.put(Double.valueOf(dataItem[j]));
			}
			json.put(item);
		}
		return json;
	}

	private static JSONObject getLegend() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("layout", "vertical");
		json.put("align", "right");
		json.put("verticalAlign", "middle");
		json.put("borderWidth", 0);
		return json;
	}
	
	private static JSONObject getPlotOptionsLine() throws JSONException {
		JSONObject json = new JSONObject();
		
		JSONObject dataLabels = new JSONObject();
		dataLabels.put("enabled", true);
		
		JSONObject line = new JSONObject();
		line.put("dataLabels", dataLabels);
		//line.put("enableMouseTracking", false);
		
		json.put("line", line);
		return json;
	}

	private static JSONObject getToolTipLine(String valueSuffix) throws JSONException {
		JSONObject json = new JSONObject();
		if(null!=valueSuffix && !"".equals(valueSuffix)){
			json.put("valueSuffix", valueSuffix);
		}
		return json;
	}

	private static JSONObject getYAxisLine(String yname) throws JSONException {
		JSONObject json = new JSONObject();
		
		if(null!=yname && !"".equals(yname)){
			JSONObject title = new JSONObject();
			json.put("title", title);
			title.put("text", yname);
		}
		
		JSONObject plotLine = new JSONObject();
		plotLine.put("value", 0);
		plotLine.put("width", 1);
		plotLine.put("color", "#808080");
		
		JSONArray plotLines = new JSONArray();
		plotLines.put(plotLine);
		json.put("plotLines", plotLines);
		
		return json;
	}

	private static JSONObject getXAxisLine(String[] xtitle,boolean isDate) throws JSONException {
		JSONArray datas = new JSONArray();
		for (int i = 0; i < xtitle.length; i++) {
			datas.put(xtitle[i]);
		}
		
		JSONObject labels = new JSONObject();
		labels.put("rotation", -45);
		labels.put("align", "right");
		if(isDate){//日期处理
			labels.put("step", Math.ceil(xtitle.length/10));//3
			labels.put("staggerLines", 1);
		}
		
		JSONObject style = new JSONObject();
		labels.put("style", style);
		style.put("fontSize", "10px");
		style.put("fontFamily", "Verdana, sans-serif");
		
		JSONObject json = new JSONObject();
		json.put("categories", datas);
		json.put("labels", labels);		
		return json;
	}

	private static JSONArray getScalxPie3d(String[] xtitle, Long[] data,String name) throws JSONException {
		JSONObject json = new JSONObject();
		
		JSONArray datas = new JSONArray();
		for (int i = 0; i < data.length; i++) {
			JSONArray values = new JSONArray();
			//如何最后一条数据为0，临时解决方案
			if (data[i] == null || "0".equals(data[i]) || 0 == Double.valueOf(data[i]) ) {
				continue;
			} else {
				//data[data.length-1].get
				values.put(xtitle[i]);
				values.put(Double.valueOf(data[i]));
			}
			datas.put(values);
		}
		
		json.put("type", "pie");
		json.put("name", name);
		json.put("data", datas);
		
		return new JSONArray().put(json);
	}

	private static JSONObject getPlotOptionsPie3d() throws JSONException {
		JSONObject json = new JSONObject();
		
		JSONObject dataLabels = new JSONObject();
		dataLabels.put("enabled", true);
		dataLabels.put("format", "{point.name}");
		
		JSONObject pie = new JSONObject();
		pie.put("allowPointSelect", true);
		pie.put("cursor", "pointer");
		pie.put("depth", 35);
		pie.put("dataLabels", dataLabels);
		
		pie.put("showInLegend", true);
		
		json.put("pie", pie);
		return json;
	}

	private static JSONObject getToolTipPie3d() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("pointFormat", "{series.name}: <b>{point.percentage:.1f}%</b>");
		return json;
	}

	private static JSONObject getTitle(String title) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("text", title);
		
		JSONObject style = new JSONObject();
		json.put("style", style);
		style.put("font-family", "微软雅黑");
		style.put("font-color", "#000000");
		style.put("font-size", "16px");
		style.put("font-weight", "bold");
		style.put("font-style", "arial");
		
		return json;
	}

	private static JSONObject getDefaultTitle(String title) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("text", title);

		JSONObject style = new JSONObject();
		json.put("style", style);

		return json;
	}
	
	private static JSONObject getTitleLine(String title) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("text", title);
		//json.put("x", -10);
		
		JSONObject style = new JSONObject();
		json.put("style", style);
		style.put("font-family", "微软雅黑");
		style.put("font-color", "#000000");
		style.put("font-size", "16px");
		style.put("font-weight", "bold");
		/** 2016-02-01 取消字体的设置 */
		//style.put("font-style", "arial");
		
		return json;
	}
	

}
