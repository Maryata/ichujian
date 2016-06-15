package com.org.mokey.basedata.baseinfo.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.org.mokey.basedata.baseinfo.service.ActiveCodeService;
import com.org.mokey.basedata.baseinfo.service.ActiveCreateCodeService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.ZipManager;
import com.org.mokey.common.util.str.UnicodeReader;
import com.org.mokey.util.JSONUtil;

/**
 * 基础信息-激活码信息
 * 
 * @author zhangtong
 * 
 */
public class ActiveCreateCodeAction extends AbstractAction {

	private ActiveCreateCodeService activeCreateCodeService;

	/** 输出内容 */
	private String out; 

	/**
	 * 详细列表查询
	 * 
	 * @return
	 */
	public String getActiveList() {
		log.debug("getActiveList init");
		Map<String, Object> retMap = new HashMap<String, Object>();
		int start = getParameter2Int("start", 0);
		int limit = getParameter2Int("limit", 10);

		try {
			retMap = activeCreateCodeService.getActiveListMap(start, limit);
			retMap.put("status", "Y");
		} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "getActiveList failed");
			log.error("getActiveList failed,", e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.debug("getActiveList out");
		return SUCCESS;
	}

	public String Create() {
		log.debug("init Create");
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map saveMap = new HashMap();
		//String c_number =getParameter("c_number");
		//String c_supplier =getParameter("c_supplier");
		//String c_count =getParameter("c_count");
		//String c_batch =getParameter("c_batch");
		//String c_type =getParameter("c_type");
		//String c_issample =getParameter("c_issample");
		//String c_remark =getParameter("c_remark");
		
		saveMap.put("c_number", getParameter("c_number"));
		saveMap.put("c_supplier", getParameter("c_supplier"));
		saveMap.put("c_count", getParameter("c_count"));
		saveMap.put("c_batch", getParameter("c_batch"));
		saveMap.put("c_type", getParameter("c_type"));
		saveMap.put("c_issample", getParameter("c_issample"));
		saveMap.put("c_remark", getParameter("c_remark"));
		saveMap.put("c_url", "www.baidu.com");
		log.info("-----------"+saveMap);
		try {
			
			File file=new File("D:\\test");
			if(file.exists()){
				ZipManager zip=new ZipManager();
				zip.createZip("D:\\test", "D:\\test\\test.zip");
				//SwetakeQRCode s=new SwetakeQRCode();
				
			}else{
				file.mkdir();
			}
			
			//activeCreateCodeService.saveActive(saveMap);
			retMap.put("success", true);
			retMap.put("info", "Create failed");
		} catch (Exception e) {
			log.error("Create failed,", e);
		}  
		//writeToResponse(JSONObject.fromObject(retMap).toString());
		out = JSONObject.fromObject(retMap).toString();
		log.debug("out Create");
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String saveActiveRemark() {
		log.debug("init saveActiveRemark");
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			String saveParam = getParameter("saveParam");
			Map<String, Object> saveMap = (Map) JSONUtil.JSONString2Bean(
					saveParam, Map.class);
			String cId = activeCreateCodeService.saveActive(saveMap);

			retMap.put("status", "Y");
			retMap.put("c_id", cId);
			retMap.put("success", true);
		} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "saveActiveRemark failed");
			log.error("saveActiveRemark failed,", e);
		} finally {
			try {
				writeToResponse(JSONObject.fromObject(retMap).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.debug("out saveActiveRemark");
		return NONE;

	}


	public ActiveCreateCodeService getActiveCreateCodeService() {
		return activeCreateCodeService;
	}

	public void setActiveCreateCodeService(
			ActiveCreateCodeService activeCreateCodeService) {
		this.activeCreateCodeService = activeCreateCodeService;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
}
