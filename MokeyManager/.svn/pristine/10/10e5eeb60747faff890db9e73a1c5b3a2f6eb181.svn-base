package com.org.mokey.common.util.zingchart;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChartUtils {
	
	public static Long TIME_DAY = (1000*60*60*24L);//900000l*96;
	public static Long TIME_MONTH = (1000*60*60*24L*30);//900000l*96;

	public static String getMixedData(String title, String[] scalename,
			String[] xtitle, String[] xtitle2, Long starttime, Long step,
			String[][] data, String[][] data2) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("type", "line");
		json.put("alpha", 1);
		json.put("background-color", "#ffffff");
		json.put("background-color-2", "#ffffff");
		json.put("plotarea", getPlotarea());
		json.put("title", getTitle(title));
		json.put("legend", getLegend());
		// json.put("preview", getPreview());
		json.put("scale-x", getScalx(starttime, step, true));
		json.put("scale-y", getScaly(scalename[0]));
		json.put("scale-y-2", getScaly(scalename[1]));
		json.put("crosshair-x", getCrosshairX());
		json.put("plot", getPlot());
		json.put("series", getSeries(xtitle, data, xtitle2, data2));
		return json.toString();
	}

	public static String getMixedData(String title, String[] scalename,
			List xtitle, Long starttime, Long step, List scaleys, List data)
			throws JSONException {
		JSONObject json = new JSONObject();
		json.put("type", "line");
		json.put("alpha", 1);
		json.put("background-color", "#ffffff");
		json.put("background-color-2", "#ffffff");
		json.put("plotarea", getPlotarea());
		json.put("title", getTitle(title));
		json.put("legend", getLegend());
		// json.put("preview", getPreview());
		json.put("scale-x", getScalx(starttime, step, true));
		json.put("scale-y", getScaly(scalename[0]));
		if (scalename.length > 1)
			json.put("scale-y-2", getScaly(scalename[1]));
		json.put("crosshair-x", getCrosshairX());
		json.put("plot", getPlot());
		json.put("series", getSeries(xtitle, scaleys, data));
		return json.toString();
	}

	/**
	 * 
	 * @param title
	 * @param scalename
	 * @param xtitle
	 * @param xtitle2
	 * @param starttime
	 * @param step
	 * @param data
	 * @param data2
	 * @return
	 * @throws JSONException
	 */
	public static String getMulMixedData(String title, String[] scalename,
			String[] xtitle, Long starttime, Long step, String[][] data)
			throws JSONException {
		JSONObject json = new JSONObject();
		json.put("type", "line");
		json.put("alpha", 1);
		json.put("background-color", "#ffffff");
		json.put("background-color-2", "#ffffff");
		json.put("plotarea", getPlotarea());
		json.put("title", getTitle(title));
		json.put("legend", getLegend());
		// json.put("preview", getPreview());
		json.put("scale-x", getScalx(starttime, step, true));
		for (int i = 0; i < scalename.length; i++) {
			if (i == 0) {
				json.put("scale-y", getScaly(scalename[i]));
			} else {
				json.put("scale-y-" + (i + 1), getScaly(scalename[i]));
			}

		}

		json.put("crosshair-x", getCrosshairX());
		json.put("plot", getPlot());
		json.put("series", getScaleSeries(xtitle, data));
		return json.toString();
	}

	/**
	 * 
	 * 
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	public static String getDateTimeData(String title, String[] xtitle,
			Long starttime, Long step, String[][] data) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("type", "line");
		json.put("alpha", 1);
		json.put("background-color", "#ffffff");
		json.put("background-color-2", "#ffffff");

		json.put("plotarea", getPlotarea());
		json.put("title", getTitle(title));
		json.put("legend", getLegend());
		// json.put("preview", getPreview());
		json.put("scale-x", getScalx(starttime, step, false));
		json.put("scale-y", getScaly(null));
		json.put("crosshair-x", getCrosshairX());
		json.put("plot", getPlot());
		json.put("series", getSeries(xtitle, data));

		return json.toString();
	}
	
	/**
	 * 
	 * 
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	public static String getDateTimeDataMonth(String title, String[] xtitle,
			Long starttime, Long step,String[][] data) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("type", "line");
		json.put("alpha", 1);
		json.put("background-color", "#ffffff");
		json.put("background-color-2", "#ffffff");

		json.put("plotarea", getPlotarea());
		json.put("title", getTitle(title));
		json.put("legend", getLegend());
		// json.put("preview", getPreview());
		json.put("scale-x", getScalxMonth(starttime, step, false));
		json.put("scale-y", getScaly(null));
		json.put("crosshair-x", getCrosshairX());
		json.put("plot", getPlot());
		json.put("series", getSeries(xtitle, data));

		return json.toString();
	}

	public static String getPie3d(String title, String[] xtitle, Long[] data)
			throws JSONException {
		JSONObject json = new JSONObject();
		json.put("type", "pie3d");
		json.put("alpha", 1);
		json.put("background-color", "#ffffff");
		json.put("background-color-2", "#ffffff");

		json.put("title", getTitle(title));
		json.put("legend", getPie3dLegend());
		json.put("tooltip", getToolTip());
		json.put("plot", getPie3dPlot());
		json.put("series", getPie3dScalx(data, xtitle));

		// json.put("series", getSeries(xtitle, data));

		return json.toString();
	}
	
	
	public static String getBar(String title, String[] xtitle, Long[] data)
	throws JSONException {
        JSONObject json = new JSONObject();
        json.put("type", "bar");
        json.put("alpha", 1);
        json.put("background-color", "#ffffff");
        json.put("background-color-2", "#ffffff");
        json.put("plotarea", getPlotarea());
        if(data.length<=5){
        	json.put("plotarea", getPlotarea10());
        }
        else if(data.length==10){
        	json.put("plotarea", getPlotarea10());
        }else{
        	json.put("plotarea", getPlotarea());
        }
        json.put("title", getTitle(title));
        json.put("tooltip", getToolTip());
        json.put("plot", getBarPlot());
        json.put("scale-x", getBarx(xtitle));  //x
       // json.put("scale-y", getBary(data));    //y
        json.put("series", getBarScalx(data));
        return json.toString();
        }
	public static JSONObject getPlotarea5() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("margin", "90px 390px 90px 390px");
		return json;
	}
	public static JSONObject getPlotarea10() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("margin", "90px 290px 90px 290px");
		return json;
	}
	public static JSONObject getBarx(String[] xtitle) throws JSONException {
		JSONObject json = new JSONObject();
		JSONArray array=new JSONArray();
		for(int i=0;i<xtitle.length;i++){
			if(xtitle[i]!=null){
				array.put(xtitle[i]);
			}
		}
		json.put("values", array);
		return json;
	}
	
	public static JSONObject getBary(Long[] data) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("values", "0:5:1");
		json.put("format", "%v");
		return json;
	}

	public static JSONObject getToolTip() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("visible", true);
		json.put("font-color", "#000000");
		return json;
	}

	public static JSONObject getLegend() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("visible", true);
		json.put("draggable", false);
		json.put("minimize", true);
		return json;
	}

	public static JSONObject getPie3dLegend() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("layout", "vertical");
		json.put("draggable", true);
		json.put("drag-handler", "header");
		// json.put("margin", "15 340 5 5");
		json.put("width", "95px");
		json.put("visible", true);
		json.put("font-family", "arial");
		json.put("font-size", "10px");
		json.put("background-color", "#121212");
		json.put("font-size", "10px");
		json.put("border-width", "1px");
		json.put("border-color", "#ffffff");
		json.put("header", getHeader());
		json.put("item", getItem());
		return json;
	}

	public static JSONObject getHeader() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("text", "数据维度");
		json.put("align", "center");
		json.put("font-family", "arial");
		json.put("font-size", "10px");
		json.put("border-color", "#ffffff");
		json.put("background-color", "#121212");
		return json;
	}

	public static JSONObject getItem() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("font-color", "#ffffff");
		json.put("marker-style", "square");
		json.put("border-width", "0px");
		return json;
	}

	/**
	 * "plotarea":{ "margin":"50px 40px 90px 70px" },
	 * 
	 * @throws JSONException
	 */
	public static JSONObject getPlotarea() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("margin", "90px 90px 90px 90px");
		return json;
	}

	public static JSONObject getTitle(String titlename) throws JSONException {
		JSONObject json = new JSONObject();
		if (titlename == null)
			titlename = "";
		json.put("text", titlename);
		json.put("background-color", "#ffffff");
		json.put("font-family", "微软雅黑");
		json.put("font-color", "#000000");
		json.put("font-size", "16px");
		json.put("font-weight", "bold");
		json.put("font-style", "arial");
		json.put("margin-top", "10px");
		json.put("margin-left", "4px");
		json.put("margin-bottom", "10px");
		json.put("text-align", "center");
		return json;
	}

	public static JSONObject getPreview() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("alpha", "0.3");
		json.put("height", "40px");
		json.put("margin", "400px 40px 5px 40px");
		json.put("visible", true);
		return json;
	}

	public static JSONObject getScalx(Long starttime, Long step,
			boolean iszooming) throws JSONException {
		JSONObject json = new JSONObject();

		json.put("max-items", 20);
		if (iszooming)
			json.put("zooming", 1);
		if (starttime != null)
			json.put("min-value", starttime);
		else
			json.put("min-value", 1274313600000l);
		if (step != null)
			json.put("step", step);
		else
			json.put("step", 600000);
		JSONObject transform = new JSONObject();
		json.put("transform", transform);
		transform.put("type", "date");
		transform.put("all", "%y-%m-%d");// %H:%i
		// transform.put("all", "%D, %d %M<br />%h:%i %A");
		JSONObject transformguide = new JSONObject();
		transformguide.put("visible", true);
		transformguide.put("line-color", "#CCCCCC");

		transformguide.put("line-style", "solid");
		json.put("guide", transformguide);
		JSONObject item = new JSONObject();
		item.put("visible", true);
		transform.put("item", item);

		return json;
	}
	
	public static JSONObject getScalxMonth(Long starttime, Long step,
			boolean iszooming) throws JSONException {
		JSONObject json = new JSONObject();

		json.put("max-items", 20);
		if (iszooming)
			json.put("zooming", 1);
		if (starttime != null)
			json.put("min-value", starttime);
		else
			json.put("min-value", 1274313600000l);
		if (step != null)
			json.put("step", step);
		else
			json.put("step", 600000);
		JSONObject transform = new JSONObject();
		json.put("transform", transform);
		transform.put("type", "date");
		transform.put("all", "%y-%m");// %H:%i
		// transform.put("all", "%D, %d %M<br />%h:%i %A");
		JSONObject transformguide = new JSONObject();
		transformguide.put("visible", true);
		transformguide.put("line-color", "#CCCCCC");

		transformguide.put("line-style", "solid");
		json.put("guide", transformguide);
		JSONObject item = new JSONObject();
		item.put("visible", true);
		transform.put("item", item);

		return json;
	}


	public static JSONArray getPie3dScalx(Long data[], String titles[])
			throws JSONException {

		JSONArray array = new JSONArray();
		for (int i = 0; i < data.length; i++) {
			JSONObject json = new JSONObject();
			array.put(json);
			JSONArray values = new JSONArray();
			JSONArray texts = new JSONArray();
			if (data[i] != null) {
				values.put(Double.valueOf(data[i]));
				texts.put(titles[i]);
			} else {
				values.put("null");
				texts.put("null");
			}
			json.put("values", values);
			json.put("text", texts);
		}
		return array;
	}
	
	public static JSONArray getBarScalx(Long data[])
	throws JSONException {
		JSONArray array = new JSONArray();
		JSONObject json = new JSONObject();
		JSONArray values = new JSONArray();
		array.put(json);
		for (int i = 0; i < data.length; i++) {
			if (data[i] != null) {
				values.put(data[i]);
			} 
		}
		json.put("values", values);
		return array;
	}

	public static JSONObject getScaly(String textname) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("decimals", 0);
		json.put("decimals-separator", ".");
		json.put("thousands-separator", ",");
		json.put("zooming", 0);
		if (StringUtils.isNotEmpty(textname))
			json.put("text", textname);
		JSONObject transformguide = new JSONObject();
		transformguide.put("visible", false);
		transformguide.put("line-color", "#CCCCCC");
		transformguide.put("line-style", "solid");
		json.put("guide", transformguide);

		return json;
	}

	public static JSONObject getCrosshairX() throws JSONException {
		JSONObject json = new JSONObject();
		JSONObject marker = new JSONObject();
		marker.put("visible", false);
		JSONObject valuelabel = new JSONObject();
		valuelabel.put("decimals", 2);
		valuelabel.put("decimals-separator", ".");
		valuelabel.put("thousands-separator", ",");
		JSONObject scalelabel = new JSONObject();
		scalelabel.put("visible", true);
		json.put("marker", marker);
		json.put("shadow", false);
		json.put("value-label", valuelabel);
		json.put("scale-label", scalelabel);
		json.put("background-transparent", false);
		return json;
	}

	public static JSONObject getPlot() throws JSONException {
		JSONObject json = new JSONObject();
		JSONObject tooltip = new JSONObject();
		json.put("tooltip", tooltip);
		tooltip.put("visible", false);
		return json;
	}

	public static JSONObject getPie3dPlot() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("thickness", "30");
		json.put("border-width", "0px");
		json.put("font-family", "arial");
		json.put("tooltip-text", " Value: %v Per: %npv %");
		json.put("detach", true);
		json.put("value-box", getPie3dValueBox());
		return json;
	}
	
	public static JSONObject getBarPlot() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("alpha", "1");
		//json.put("speed", "0.8");
		json.put("bar-color", "#CCCCCC");
		//json.put("border-width", "3px");
		json.put("tooltip-text", "%k: %v ");
		return json;
	}

	public static JSONObject getPie3dValueBox() throws JSONException {
		JSONObject json = new JSONObject();
		/*
		 * json.put("placement", "out"); json.put("connected", true);
		 * json.put("font-color", "#ffffff");
		 * json.put("tooltip-text","%t:  %v"); json.put("detach", true);
		 */
		json.put("visible", true);
		return json;
	}

	public static JSONObject getPie3dRules() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("placement", "out");
		json.put("connected", true);
		json.put("font-color", "#ffffff");
		json.put("tooltip-text", "%t:  %v");
		json.put("detach", true);
		return json;
	}

	public static JSONArray getSeries(String[] titles, String[][] series)
			throws JSONException {
		JSONArray array = new JSONArray();
		if (series != null)
			for (int j = 0; j < series.length; j++) {
				JSONObject json = new JSONObject();
				array.put(json);
				json.put("line-width", "1px");
				json.put("text", titles[j]);
				JSONArray values = new JSONArray();
				for (int i = 0; i < series[j].length; i++) {
					if (series[j][i] != null && !series[j][i].equals(""))
						values.put(Double.valueOf(series[j][i]));
					else
						values.put("null");
				}
				json.put("values", values);
			}
		return array;
	}

	public static JSONArray getSeries(String[] titles, String[][] series,
			String[] titles2, String[][] series2) throws JSONException {
		JSONArray array = new JSONArray();
		if (series != null)
			for (int j = 0; j < series.length; j++) {
				JSONObject json = new JSONObject();
				array.put(json);
				json.put("line-width", "1px");
				json.put("text", titles[j]);
				JSONArray values = new JSONArray();
				for (int i = 0; i < series[j].length; i++) {
					if (series[j][i] != null && !series[j][i].equals(""))
						values.put(Double.valueOf(series[j][i]));
					else
						values.put("null");
				}
				json.put("values", values);
			}
		for (int j = 0; j < series2.length; j++) {
			JSONObject json = new JSONObject();
			array.put(json);
			json.put("line-width", "1px");
			json.put("scales", "scale-x,scale-y-2");
			json.put("text", titles2[j]);
			JSONArray values = new JSONArray();
			for (int i = 0; i < series2[j].length; i++) {
				if (series2[j][i] != null && !series2[j][i].equals(""))
					values.put(Double.valueOf(series2[j][i]));
				else
					values.put("null");
			}
			json.put("values", values);
		}
		return array;
	}

	public static JSONArray getSeries(List titles, List scales, List data)
			throws JSONException {
		JSONArray array = new JSONArray();
		if (titles != null)
			for (int j = 0; j < titles.size(); j++) {
				JSONObject json = new JSONObject();
				array.put(json);
				json.put("line-width", "1px");
				String scale = (String) scales.get(j);
				if (scale.equals("")) {
					json.put("scales", "scale-x,scale-y");
				} else {
					json.put("scales", "scale-x,scale-y-2");
				}
				json.put("text", titles.get(j));
				JSONArray values = new JSONArray();
				String[] series = (String[]) data.get(j);
				for (int i = 0; i < series.length; i++) {
					if (series[i] != null && !series[i].equals(""))
						values.put(Double.valueOf(series[i]));
					else
						values.put("null");
				}
				json.put("values", values);
			}

		return array;
	}

	public static JSONArray getScaleSeries(String[] titles, String[][] series)
			throws JSONException {
		JSONArray array = new JSONArray();
		if (series != null)
			for (int j = 0; j < series.length; j++) {
				JSONObject json = new JSONObject();
				array.put(json);
				json.put("line-width", "1px");
				if (j == 0) {
					json.put("scales", "scale-x,scale-y");
				} else {
					json.put("scales", "scale-x,scale-y-" + (j + 1));
				}
				json.put("text", titles[j]);
				JSONArray values = new JSONArray();
				for (int i = 0; i < series[j].length; i++) {
					if (series[j][i] != null && !series[j][i].equals(""))
						values.put(Double.valueOf(series[j][i]));
					else
						values.put("null");
				}
				json.put("values", values);
			}
		return array;
	}

}
