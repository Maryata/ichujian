package com.org.mokey.formdesign.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.Object2XMLUtil;
import com.org.mokey.formdesign.service.FormCtrlService;

public class FormCtrlAction extends AbstractAction{
	
	private FormCtrlService formctrlService;
	
	
	public FormCtrlService getFormctrlService() {
		return formctrlService;
	}

	public void setFormctrlService(FormCtrlService formctrlService) {
		this.formctrlService = formctrlService;
	}
	
	public String getCtrlDataList() throws Exception {		
		String sql = getRequest().getParameter("sql");
		String displayfield=getRequest().getParameter("fielddisplay");
		String valuefield=getRequest().getParameter("fieldvalue");	
		String params = getRequest().getParameter("sqlparams");
		log.debug(sql);
		log.debug("displayfield: " + displayfield);
		log.debug("valuefield: " + valuefield);
		
		Object[] paramesArray = JSONArray.fromObject(params).toArray();
		
		log.debug("sqlparams: " + params);
		
		Object[] paramsArr= new Object[paramesArray.length];
		
		for(int i=0;i<paramesArray.length ; i++)
		{
			paramsArr[i]= ((JSONObject)paramesArray[i]).get("params");
		}
		//sql= " Select * from T_FM_CTRLDATA ";
		//displayfield ="name";
		//valuefield ="value";
		
		String jsonStr = null;
		try {		

			if (sql != null && sql.trim().length()>0) {
				// 拼成json对象
				List results;
				List num;
				
				results = formctrlService.findDataList(sql, paramsArr ,displayfield,valuefield);
				jsonStr = buildJsonReString(results,results.size());			
				
			} else {
				jsonStr = "{\"rows\":10,"
						+ "\"mydata\":"
						+ "["
						//+ "{\"uid\":1,\"uname\":\"Kevin\",\"address\":\"南京软件园\"},"
						//+ "{\"uid\":2,\"uname\":\"Jason\",\"address\":\"江苏软件园\"}"
						+ "],\"loginuser\":[{\"btns\":\"F09910,F09920,F09930\",\"uname\":\"Kevin\"}]}";
			}

			writeToResponse(jsonStr);
		} catch (Exception e) {
			log.error(" error happened", e);
			// TODO 需要进行统一的业务处理，比如到一个统一的错误处理页面。
			// 按照现有js的页面，这部分较好的方法是发送一个error信息了，交由页面处理
			writeToResponse(Object2XMLUtil.getFailureJson());
		}
		log.debug("  leave listJsonString ." + jsonStr);

		return NONE;
	}

	//获得表格数据项
	public String getCtrlGridDataList() throws Exception {		
		String sql = getRequest().getParameter("sql");		
		String params = getRequest().getParameter("sqlparams");	
		String jdbc = getRequest().getParameter("jdbc");	
		
		int currentRow = this.getParameter2Int("start", 0);
		int PageSize = this.getParameter2Int("limit", 10);
		
		Object[] paramesArray = JSONArray.fromObject(params).toArray();
		
		//log.debug("sqlparams: " + params);
		Object[] paramsArr= new Object[paramesArray.length];
		
		for(int i=0;i<paramesArray.length ; i++)
		{
			paramsArr[i]= ((JSONObject)paramesArray[i]).get("params");
		}
		String jsonStr = null;
		try {		

			if (sql!=null && sql.trim().length()>0) {
				// 拼成json对象
				List results;
				long num;
				JSONObject cols = new JSONObject();
				
				num = formctrlService.findDataList(sql, paramsArr ,jdbc);
				results = formctrlService.findDataList(sql, paramsArr , cols , currentRow ,PageSize, jdbc );
				
				jsonStr = buildJsonReString(results,num,cols);
				log.debug(JSONObject.fromObject(cols).toString());
				
			} else {
					jsonStr = "{\"rows\":0,"
						+ " metaData: {"
						+ "\"idProperty\": \"uid\","
						+ "\"root\": \"mydata\","
						+ "\"fields\": ["
						+ "    {\"name\": \"uname\"},"
						+ "    {\"name\": \"job\", \"mapping\": \"address\"}"
						+ "]"				       
						+"},"
						+ "\"mydata\":"
						+ "["
						+ "],\"loginuser\":[{\"btns\":\"F09910,F09920,F09930\",\"uname\":\"Kevin\"}]}";
			}

			writeToResponse(jsonStr);
		} catch (Exception e) {
			log.error(" error happened", e);
			writeToResponse(Object2XMLUtil.getFailureJson());
		}
		log.debug("  leave listJsonString ." + (jsonStr!=null && jsonStr.length()>500 ? jsonStr.substring(0,500)+" ... ..." : jsonStr ));
		return NONE;
	}
		
	private String buildJsonReString(List<?> results,long nums,JSONObject cols) {
		JSONObject metaData = new JSONObject();
		List<JSONObject> fields = new ArrayList<JSONObject>();
		
		for(Object key : cols.keySet())
		{
			JSONObject field= new JSONObject();
			
			field.put("name",cols.get(key));
			
			fields.add(field);
			
		}
		metaData.put("root", "mydata");
		metaData.put("totalProperty", "totalProperty");	
		metaData.put("fields", fields);
		
		Map<String,Object> rm = new HashMap<String,Object>();
		rm.put("totalProperty", nums);
		rm.put("mydata", results);
		rm.put("metaData", metaData );
		return JSONObject.fromObject(rm).toString();
	}
	
	private String buildJsonReString(List<?> results,int nums) {
		Map<String,Object> rm = new HashMap<String,Object>();
		rm.put("totalProperty", nums);
		rm.put("mydata", results);
		return JSONObject.fromObject(rm).toString();
	}

}
