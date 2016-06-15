package com.org.mokey.report.action;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import net.sf.json.JSONObject;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.file.FileServices;
import com.org.mokey.common.util.file.FileUtil;
import com.org.mokey.report.service.ReportDownloadService;
import com.org.mokey.util.Cn2Spell;
import com.org.mokey.util.JSONUtil;
import com.org.mokey.util.StrUtils;

public class ReportDownload extends AbstractAction {

	ReportDownloadService reportDownloadService;
	private String out;
	/**
	 * 查询列表
	 * @return
	 */
	public String getReportDownload(){
		log.debug("into getReportDownload");
		Map<String, Object> ret=new HashMap<String, Object>();
		String time_s = getParameter("time_s");
		String time_e = getParameter("time_e");
		
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		if(StrUtils.isNotEmpty(time_e)){
			time_e=ApDateTime.dateAdd("d", +1, time_e, ApDateTime.DATE_TIME_YMD);
		}
		
		try {
			ret=reportDownloadService.getReportDownload(time_s, time_e, start, limit);
			ret.put("status", "Y"); 
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("status", "N");
			ret.put("info", "getReportDownload failed");
			log.error("getReportDownload failed",e);
		}
		out = JSONObject.fromObject(ret).toString();
		return SUCCESS;
	}
	
	public  String deleteReportDownload() {
		log.debug("into deleteReportDownload");
		Map<String, Object> ret=new HashMap<String, Object>();
		String id=getParameter("c_id");
		try {
			reportDownloadService.deleteReportDownload(id);
			ret.put("status", "Y");	
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("status", "N");
			log.error("deleteReportDownload failed",e);
		}
		out = JSONObject.fromObject(ret).toString();
		return SUCCESS;
	}
	
	public String updateReport(){
		log.info("into updateReport");
		Map<String, Object> ret=new HashMap<String, Object>();
		String saveParam=getParameter("saveParam");
		Map<String,Object> saveMap = (Map) JSONUtil.JSONString2Bean(saveParam, Map.class);
		try {
			MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper)getRequest();
			if(multiWrapper.getFileNames("c_reportFile")!=null){
				String[] filename=multiWrapper.getFileNames("c_reportFile");
				File[] files=multiWrapper.getFiles("c_reportFile");//c_reportFile
				for(int i=0;i<filename.length;i++){
					saveMap.put("C_NAME", filename[i]);
					saveMap.put("C_URL",FileServices.saveFile(files[i], "reportFile/"+Cn2Spell.converterToSpellTrim(filename[i])));
				}
				Date actionDate=new Date();
				saveMap.put("C_ISLIVE", 1);
				saveMap.put("C_ACTIONDATE", actionDate);
			}
			reportDownloadService.saveReport(saveMap);
			ret.put("status", "Y");
			ret.put("success", true);
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("status", "N");
			log.error("updateReport failed");
		}
		out = JSONObject.fromObject(ret).toString();
		return SUCCESS;
	}
	
	
	
	public ReportDownloadService getReportDownloadService() {
		return reportDownloadService;
	}
	public void setReportDownloadService(ReportDownloadService reportDownloadService) {
		this.reportDownloadService = reportDownloadService;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
	
}
